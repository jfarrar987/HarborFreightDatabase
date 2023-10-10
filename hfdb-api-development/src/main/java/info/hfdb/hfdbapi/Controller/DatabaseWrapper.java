package info.hfdb.hfdbapi.Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import info.hfdb.hfdbapi.HfdbApiApplication;

/**
 * This provides access to the postgres database by wrapping the various
 * operations needed to be performed in corresponding java methods
 */
public class DatabaseWrapper {

    /**
     * this nameSearch function takes the name, a minimum price and a maximum price
     * and returns a list of SKUs fitting those parameters.
     *
     * @param name The name value to serach by (currently is case sensitive)
     * @param min  The minimum price value to search by (can be circumvented by
     *             setting to '-1' in the URL call)
     * @param max  The maximum price value to search by (can be circumvented by
     *             setting to '-1' in the URL call)
     */
    public static List<ProductSKU> nameSearch(String name, int min, int max) {

        createView();
        String filter = "";

        if (max != -1)
            filter = "AND price <= " + max + " ";
        if (min != -1)
            filter += "AND price >= " + min;

        String sql = "SELECT products.sku FROM products, latestprice "
                + "WHERE products.sku = latestprice.sku AND "
                + "LOWER(name) LIKE LOWER(?) " + filter + ";";

        ProductSearchRowMapper map = new ProductSearchRowMapper();
        List<ProductSKU> a = HfdbApiApplication.getJdbcTemplate().query(sql, map, name);
        return a;
    }

    /**
     * this grabProductDetails function returns the product details associated with
     * a given SKU
     *
     * @param sku The sku that data is being returned for
     * @return a product object wrapped in an optional object
     */
    public static Optional<Product> grabProductDetails(int sku) {

        createView();
        String sql = """
                SELECT products.sku, name, price, ts, imgurl, canonicalurl
                FROM products, latestprice WHERE products.sku=latestprice.sku AND products.sku = ?
                ORDER BY products;
                    """;
        ProductRowMapper map = new ProductRowMapper();
        List<Product> a = HfdbApiApplication.getJdbcTemplate().query(sql, map, sku);
        assert (a.size() <= 1);
        map = null;
        Optional<Product> b = a.stream().findFirst();
        return b;

    }

    /**
     * This grabRetailPriceHistory function returns the price and timestamp the
     * price was gathered by the webscraper associated with a specific sku. This can
     * be further filtered with the optional lower and upper bounds.
     *
     * @param sku   the sku that data is being returned for
     * @param lower optional lower bound filter, circumvented with -1
     * @param upper optional upper bound filter, circumvented with -1
     * @return a list of prices and corresponding timestamps associated with
     *         specified sku
     * @throws SQLException           when SQL Error is provoked
     * @throws DateTImeParseException when upper or lower bound is improperly
     *                                formatted
     */
    public static List<PriceHistory> grabRetailPriceHistory(int sku, String lower, String upper)
            throws SQLException, DateTimeParseException {

        ZonedDateTime dtUpper = ZonedDateTime.parse(upper);
        Timestamp tsUpper = Timestamp.from(dtUpper.toInstant());
        ZonedDateTime dtLower = ZonedDateTime.parse(lower);
        Timestamp tsLower = Timestamp.from(dtLower.toInstant());

        String sql = "SELECT price, ts FROM retailprices WHERE sku = ? AND ts <= ? AND ts >= ? ORDER BY ts;";

        PreparedStatement ps = HfdbApiApplication.getConnection().prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, sku);
        ps.setTimestamp(2, tsUpper);
        ps.setTimestamp(3, tsLower);

        ResultSet rs = ps.executeQuery();
        List<PriceHistory> a = new LinkedList<>();

        while (rs.next()) {
            int price = rs.getInt("price");
            Timestamp ts = rs.getTimestamp("ts");
            ZonedDateTime dt = ZonedDateTime.of(ts.toLocalDateTime(), ZoneId.systemDefault());
            PriceHistory p = new PriceHistory(price,
                    dt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            a.add(p);
        }
        return a;
    }

    /**
     * Checks if a view exists, if not it creates one.
     * The view returns a list of skus, prices, and the latest
     * timestamp associated with said skus and prices
     */
    public static void createView() {

        String query = """
                SELECT schemaname, viewname
                FROM pg_catalog.pg_views WHERE schemaname
                NOT IN('pg_catalog', 'information_schema') AND viewname = 'latestprice'
                ORDER BY schemaname, viewname;
                        """;
        CatalogRowMapper map = new CatalogRowMapper();
        List<PgCatalog> a = HfdbApiApplication.getJdbcTemplate().query(query, map);
        int count = a.size();

        if (count == 0) {
            count++;
            String viewCall = """
                    create view latestPrice as
                    select retailprices.sku as sku,price,ts from (
                        retailprices inner join (
                            select sku, max(ts) as maxTS from retailprices group by sku
                        ) as latestPrice on latestprice.sku=retailprices.sku and ts=maxTS
                    );
                    """;
            HfdbApiApplication.getJdbcTemplate().execute(viewCall);
        }
    }
}

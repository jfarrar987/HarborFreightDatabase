package info.hfdb.hfdbapi.Controller;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is the rowMapper for the products table that accepts the ResultSet rs
 * (the tabular data returned by the SQL statement) and the int rowNum (for each
 * row returned by a query, the rownum returns a number indicating the order
 * that Oracle selects the row from a table)
 *
 * @return ProductsSKU with the specified columns as listed by the Constructor
 *         associated with this rowMapper
 */
public class ProductSearchRowMapper implements RowMapper<ProductSKU> {

    @Override
    /**
     * @return the column to be returned by the name search function
     */
    public ProductSKU mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProductSKU(
                rs.getInt("sku"));
    }
}

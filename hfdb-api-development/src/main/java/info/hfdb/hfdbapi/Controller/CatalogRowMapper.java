package info.hfdb.hfdbapi.Controller;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogRowMapper implements RowMapper<PgCatalog> {

    public PgCatalog mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new PgCatalog(
                rs.getString("schemaname"),
                rs.getString("viewname"));
    }
}

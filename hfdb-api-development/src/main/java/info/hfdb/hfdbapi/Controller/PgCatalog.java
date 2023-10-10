package info.hfdb.hfdbapi.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PgCatalog {
    @JsonProperty("schemaname")
    private String schemaname;
    @JsonProperty("viewname")
    private String viewname;

    public PgCatalog(String schemaname, String viewname) {
        this.schemaname = schemaname;
        this.viewname = viewname;
    }

    public String getSchemaname() {
        return schemaname;
    }

    public String getViewname() {
        return viewname;
    }
}

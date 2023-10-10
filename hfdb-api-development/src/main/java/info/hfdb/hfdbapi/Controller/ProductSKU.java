package info.hfdb.hfdbapi.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is the class that builds the Product Object used by the HelperDao and
 * the HelperDaoImpl
 */
public class ProductSKU {
    @JsonProperty("sku")
    private int sku;

    /**
     * This is the constructor for ProductsSKU
     *
     * @param sku the sku to be set
     */
    public ProductSKU(int sku) {
        this.sku = sku;
    }

    /**
     * this returns the sku
     *
     * @return sku to be returned
     */
    public int getSku() {
        return sku;
    }

}

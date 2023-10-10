package info.hfdb.hfdbapi.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is the class that builds the Product Object used by the HelperDao and
 * the HelperDaoImpl
 */
public class Product {
    @JsonProperty("sku")
    private int sku;
    @JsonProperty("name")
    private String name;
    @JsonProperty("imgURL")
    private String imgURL;
    @JsonProperty("canonicalURL")
    private String canonicalURL;
    @JsonProperty("price")
    private int price;
    @JsonProperty("ts")
    private String ts;

    /**
     * This is the Constructor for the Product Object
     *
     * @param sku          the product sku set
     * @param name         the name set
     * @param price        the price set
     * @param ts           the time stamp set
     * @param imgURL       the image URL set
     * @param canonicalURL the canonical URL set
     */
    public Product(int sku, String name, int price, String ts, String imgURL, String canonicalURL) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.ts = ts;
        this.imgURL = imgURL;
        this.canonicalURL = canonicalURL;
    }

    /**
     * This returns the SKU
     *
     * @return sku
     */
    public int getSKU() {
        return sku;
    }

    /**
     * This returns the name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * This returns the price
     *
     * @return price
     */
    public int getPrice() {
        return price;
    }

    /**
     * The returns the timestamp (ts)
     *
     * @return ts
     */
    public String getTs() {
        return ts;
    }

    /**
     * This returns the imgURL
     *
     * @return imgURL
     */
    public String getImgURL() {
        return imgURL;
    }

    /**
     * This returns the canonicalURL
     *
     * @return canonicalURL
     */
    public String getCanonicalURL() {
        return canonicalURL;
    }

}

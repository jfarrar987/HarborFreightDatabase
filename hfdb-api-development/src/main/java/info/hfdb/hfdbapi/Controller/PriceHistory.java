package info.hfdb.hfdbapi.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is the class that builds the timestampList Object used by the HelperDao
 * and
 * the HelperDaoImpl
 */
public class PriceHistory {
    @JsonProperty("price")
    private int price;
    @JsonProperty("ts")
    private String ts;

    /**
     * This is the constructor for the timestampList Object
     *
     * @param sku the product sku set
     * @param ts  the timestamp set
     */
    public PriceHistory(int price, String ts) {
        this.price = price;
        this.ts = ts;
    }

    /**
     * This returns the price
     *
     * @return
     */
    public int getPrice() {
        return price;
    }

    /**
     * This returns the timestamp (ts)
     *
     * @return ts
     */
    public String getTs() {
        return ts;
    }
}

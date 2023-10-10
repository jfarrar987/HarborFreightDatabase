package info.hfdb.hfdbapi.Controller;

import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.hfdb.hfdbapi.Controller.Status.StatusCode;

@RestController
public class HFDBAPI {

    /**
     * This returns the status of the connection and is mapped to '/status'
     *
     * @return status
     */
    @RequestMapping("/status")
    public Status getStatus() {
        return new Status(StatusCode.OK);
    }

    /**
     * This pings and returns a pong and is mapped to '/ping'
     *
     * @return 'pong'
     */
    @RequestMapping("/ping")
    public String ping() {
        return "pong";
    }

    /**
     * this is the nameSearch funtion and is mapped to
     * '/searchName/{name}/{min}/{max}', where the curly braces denote the terms to
     * be searched
     *
     * @param name the name search term
     * @param min  the minimum price search term
     * @param max  the maximum price search term
     * @return a list of ProductSKUs that fit the given parameters
     */
    @GetMapping("/nameSearch/{name}/{min}/{max}")
    public List<ProductSKU> nameSearch(@PathVariable("name") String name, @PathVariable("min") int min,
            @PathVariable("max") int max) {
        List<ProductSKU> a = DatabaseWrapper.nameSearch("%" + name + "%", min, max);
        return a;
    }

    /**
     * This is the grabProductDetails function and is mapped to
     * '/grabProductDetails/{sku}', where the curly brace denotes the terms to be
     * searched
     *
     * @param sku the deisred sku to be returned
     * @return a ResponseEntity object that reflects the outcome of the operation
     */
    @GetMapping("/grabProductDetails/{sku}")
    public ResponseEntity<Product> grabDetails(@PathVariable("sku") int sku) {
        Optional<Product> a = DatabaseWrapper.grabProductDetails(sku);
        if (a.isEmpty())
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<Product>(a.get(), HttpStatus.OK);
    }

    /**
     * This is the grabRetailPriceHistory function and is mapped to
     * '/grabRetailPriceHistory/{sku}/{lower}/{upper}'
     *
     * @param sku   The desired sku to be searched
     * @param lower the lower bound for the timestamps, can be circumvented by
     *              inputting -1
     * @param upper the upper bound for the timestamps, can be circumvented by
     *              inputting -1
     * @return a list of products prices and the timestamps those prices were
     *         obtained by the webscrapper, associated with a specific product sku
     */
    @GetMapping("/grabRetailPriceHistory/{sku}/{lower}/{upper}")
    public ResponseEntity<List<PriceHistory>> grabRetailPriceHistory(@PathVariable("sku") int sku,
            @PathVariable("lower") String lower,
            @PathVariable("upper") String upper) {
        List<PriceHistory> a;
        try {
            a = DatabaseWrapper.grabRetailPriceHistory(sku, lower, upper);
        } catch (SQLException e) {
            return new ResponseEntity<List<PriceHistory>>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<List<PriceHistory>>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<PriceHistory>>(a, HttpStatus.OK);
    }
}

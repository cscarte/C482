package model;

/**This class provides the model for an outsourced part
 * It uses the provided Part class to construct a template for an outsourced part.
 */

/**
 * Author: Christopher Carter
 * Email: ccar491@wgu.edu
 * Student ID: 001356206
 */

public class Outsourced extends Part {

    // Sets variable for Outsourced parts.
    // Could not use finale as needed to assign value to String variable companyName
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

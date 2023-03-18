package model;

/**This class provides the model for an in house part
 * It uses the provided Part class to construct a template for an in house part.
 */

/**
 * Author: Christopher Carter
 * Email: ccar491@wgu.edu
 * Student ID: 001356206
 */

public class InHouse extends Part {

    // Sets variable for In House parts.
    // Could not use finale as needed to assign value to int variable inHouseMachineID
    private int inHouseMachineID;

    public InHouse(int id, String name, double price, int stock, int min, int max, int inHouseMachineID) {
        super(id, name, price, stock, min, max);
        this.inHouseMachineID = inHouseMachineID;
    }

    public int getInHouseMachineID() {
        return inHouseMachineID;
    }

    public void setInHousePartID(int inHouseMachineID){
        this.inHouseMachineID = inHouseMachineID;
    }

}

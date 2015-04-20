/** @author Clara MCTC Java Programming Class */


public class Laptop extends Item {

    public Laptop (String make, String model, String staff) {
        super(make, model, staff);
    }

    public Laptop (int id, String make, String model, String staff) {
        super(id, make, model, staff);
    }

    @Override
    public String toString() {

        String idData = (this.id == this.NOID) ? "<No ID assigned>" : Integer.toString(this.id) ;

        return  "Laptop ID: " +  idData + " Make, Model: " + this.make + ", " + this.model + " Assigned to " + this.staff;
    }

}

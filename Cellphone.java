/**
 * Created by DayDay on 4/20/2015.
 */
public class Cellphone extends Item {

    public Cellphone (String make, String model, String staff) {
        super(make, model, staff);
    }

    public Cellphone (int id, String make, String model, String staff) {
        super(id, make, model, staff);
    }

    @Override
    public String toString() {

        String idData = (this.id == this.NOID) ? "<No ID assigned>" : Integer.toString(this.id) ;

        return  "Cellphone ID: " +  idData + " Make, Model: " + this.make + ", " + this.model + " Assigned to " + this.staff;
    }
}

/** @author Clara MCTC Java Programming Class */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


public class InventoryModel {

    // JDBC driver name, protocol, used to create a connection to the DB
    private static String protocol = "jdbc:derby:";
    private static String dbName = "laptopInventoryDB";



    //  Database credentials - for embedded, usually defaults. A client-server DB would need to authenticate connections
    private static final String USER = "temp";
    private static final String PASS = "password";


    InventoryController myController;

    Statement statement = null;

    Connection conn = null;

    ResultSet rs = null;

    LinkedList<Statement> allStatements = new LinkedList<Statement>();

    PreparedStatement psAddLaptop = null;
    PreparedStatement psDeleteLaptop = null;
    PreparedStatement psUpdateLaptop = null;
    PreparedStatement psFindLaptop = null;

    PreparedStatement psAddCellphone = null;
    PreparedStatement psDeleteCellphone = null;
    PreparedStatement psUpdateCellphone = null;
    PreparedStatement psFindCellphone = null;

    PreparedStatement psFetchItems = null;


    public InventoryModel(InventoryController controller) {

        this.myController = controller;

    }


    public boolean setupDatabase() {
        return setupDatabase(false);
    }

    public boolean setupDatabase(boolean deleteAndRecreate) {
        // TODO Auto-generated method stub

        try {
            createConnection();
        } catch (Exception e) {

            System.err.println("Unable to connect to database. Error message and stack trace follow");
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

        //create laptops table
        try {
            createLaptopsTable(deleteAndRecreate);
        } catch (SQLException sqle) {
            System.err.println("Unable to create laptops table. Error message and stack trace follow");
            System.err.println(sqle.getMessage() + " " + sqle.getErrorCode());
            sqle.printStackTrace();
            return false;
        }

        //create cellphones table
        try {
            createCellphonesTable(deleteAndRecreate);
        } catch (SQLException sqle) {
            System.err.println("Unable to create cellphones table. Error message and stack trace follow");
            System.err.println(sqle.getMessage() + " " + sqle.getErrorCode());
            sqle.printStackTrace();
            return false;
        }

        //Remove the test data for real program
//        try {
//            addTestData();
//        }
//        catch (Exception sqle) {
//
//            System.err.println("Unable to add test data to database. Error message and stack trace follow");
//            System.err.println(sqle.getMessage());
//            sqle.printStackTrace();
//            return false;
//        }

        //At this point, it seems like everything worked.

        return true;
    }



    private void createLaptopsTable(boolean deleteAndRecreate) throws SQLException {


        String createLaptopTableSQL = "CREATE TABLE laptops (id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY, make varchar(30), model varchar(30), staff varchar(50))";
        String deleteTableSQL = "DROP TABLE laptops";

        try {
            statement.executeUpdate(createLaptopTableSQL);
            System.out.println("Created laptop table");

        } catch (SQLException sqle) {
            //Seems the table already exists, or some other error has occurred.
            //Let's try to check if the DB exists already by checking the error code returned. If so, delete it and re-create it


            if (sqle.getSQLState().startsWith("X0") ) {    //Error code for table already existing starts with XO
                if (deleteAndRecreate == true) {

                    System.out.println("laptops table appears to exist already, delete and recreate");
                    try {
                        statement.executeUpdate(deleteTableSQL);
                        statement.executeUpdate(createLaptopTableSQL);
                    } catch (SQLException e) {
                        //Still doesn't work. Throw the exception.
                        throw e;
                    }
                } else {
                    //do nothing - if the table exists, leave it be.
                }

            } else {
                //Something else went wrong. If we can't create the table, no point attempting
                //to run the rest of the code. Throw the exception again to be handled elsewhere. of the program.
                throw sqle;
            }
        }
    }

    private void createCellphonesTable(boolean deleteAndRecreate) throws SQLException {


        String createLaptopTableSQL = "CREATE TABLE cellphones (id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY, make varchar(30), model varchar(30), staff varchar(50))";
        String deleteTableSQL = "DROP TABLE cellphones";

        try {
            statement.executeUpdate(createLaptopTableSQL);
            System.out.println("Created cellphones table");

        } catch (SQLException sqle) {
            //Seems the table already exists, or some other error has occurred.
            //Let's try to check if the DB exists already by checking the error code returned. If so, delete it and re-create it


            if (sqle.getSQLState().startsWith("X0") ) {    //Error code for table already existing starts with XO
                if (deleteAndRecreate == true) {

                    System.out.println("cellphones table appears to exist already, delete and recreate");
                    try {
                        statement.executeUpdate(deleteTableSQL);
                        statement.executeUpdate(createLaptopTableSQL);
                    } catch (SQLException e) {
                        //Still doesn't work. Throw the exception.
                        throw e;
                    }
                } else {
                    //do nothing - if the table exists, leave it be.
                }

            } else {
                //Something else went wrong. If we can't create the table, no point attempting
                //to run the rest of the code. Throw the exception again to be handled elsewhere. of the program.
                throw sqle;
            }
        }
    }

    private void createConnection() throws Exception {

        try {
            conn = DriverManager.getConnection(protocol + dbName + ";create=true", USER, PASS);
            statement = conn.createStatement();
            allStatements.add(statement);
        } catch (Exception e) {
            //There are a lot of things that could go wrong here. Should probably handle them all separately but have not done so here.
            //Should put something more helpful here...
            throw e;
        }

    }


    private void addTestData() throws Exception {
        // Test data.
        if (statement == null) {
            //This isn't going to work
            throw new Exception("Statement not initialized");
        }

        //Laptop test data
        try {
            String addRecord1 = "INSERT INTO laptops (make, model, staff) VALUES ('Toshiba', 'XQ-45', 'Ryan' )" ;
            statement.executeUpdate(addRecord1);
            String addRecord2 = "INSERT INTO laptops (make, model, staff) VALUES ('Sony', '1234', 'Jane' )" ;
            statement.executeUpdate(addRecord2);
            String addRecord3 = "INSERT INTO laptops (make, model, staff) VALUES ('Apple', 'Air', 'Alex' )" ;
            statement.executeUpdate(addRecord3);
        }
        catch (SQLException sqle) {
            System.err.println("Unable to laptop add test data, check validity of SQL statements?");
            System.err.println("Unable to create database. Error message and stack trace follow");
            System.err.println(sqle.getMessage() + " " + sqle.getErrorCode());
            sqle.printStackTrace();

            throw sqle;
        }

        //Cellphone test data
        try {
            String addRecord1 = "INSERT INTO cellphones (make, model, staff) VALUES ('Samsung', 'Galaxy S5', 'Ryan' )" ;
            statement.executeUpdate(addRecord1);
            String addRecord2 = "INSERT INTO cellphones (make, model, staff) VALUES ('LG', 'G2', 'Scott' )" ;
            statement.executeUpdate(addRecord2);
            String addRecord3 = "INSERT INTO cellphones (make, model, staff) VALUES ('Apple', 'iPhone 6', 'Sara' )" ;
            statement.executeUpdate(addRecord3);
        }
        catch (SQLException sqle) {
            System.err.println("Unable to add cellphone test data, check validity of SQL statements?");
            System.err.println("Unable to create database. Error message and stack trace follow");
            System.err.println(sqle.getMessage() + " " + sqle.getErrorCode());
            sqle.printStackTrace();

            throw sqle;
        }
    }




    public void cleanup() {
        // TODO Auto-generated method stub
        try {
            if (rs != null) {
                rs.close();  //Close result set
                System.out.println("ResultSet closed");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        //Close all of the statements. Stored a reference to each statement in allStatements so we can loop over all of them and close them all.
        for (Statement s : allStatements) {

            if (s != null) {
                try {
                    s.close();
                    System.out.println("Statement closed");
                } catch (SQLException se) {
                    System.out.println("Error closing statement");
                    se.printStackTrace();
                }
            }
        }

        try {
            if (conn != null) {
                conn.close();  //Close connection to database
                System.out.println("Database connection closed");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }



    public boolean addLaptop(Laptop laptop) {


        //Create SQL query to add this laptop info to DB

        String addLaptopSQLps = "INSERT INTO laptops (make, model, staff) VALUES ( ? , ? , ?)" ;
        try {
            psAddLaptop = conn.prepareStatement(addLaptopSQLps);
            allStatements.add(psAddLaptop);
            psAddLaptop.setString(1, laptop.getMake());
            psAddLaptop.setString(2, laptop.getModel());
            psAddLaptop.setString(3, laptop.getStaff());

            psAddLaptop.execute();
        }
        catch (SQLException sqle) {
            System.err.println("Error preparing statement or executing prepared statement to add laptop");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addCellphone(Cellphone cellphone) {
        //Create SQL query to add this cellphone info to DB

        String addCellphoneSQLps = "INSERT INTO cellphones (make, model, staff) VALUES ( ? , ? , ?)" ;
        try {
            psAddCellphone = conn.prepareStatement(addCellphoneSQLps);
            allStatements.add(psAddCellphone);
            psAddCellphone.setString(1, cellphone.getMake());
            psAddCellphone.setString(2, cellphone.getModel());
            psAddCellphone.setString(3, cellphone.getStaff());

            psAddCellphone.execute();
        }
        catch (SQLException sqle) {
            System.err.println("Error preparing statement or executing prepared statement to add cellphone");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteLaptop(int laptopID) {
        //SQL query to find laptop in DB
        String findLaptopSQLps = "SELECT * FROM laptops WHERE id=?";

        try {
            psFindLaptop = conn.prepareStatement(findLaptopSQLps);
            allStatements.add(psFindLaptop);
            psFindLaptop.setInt(1, laptopID);

            rs = psFindLaptop.executeQuery();
            if (!rs.next()) {
                System.out.println("Could not find laptop with ID #" + laptopID);
                return false;
            }
        } catch (SQLException sqle) {
            System.err.println("Error preparing statement or executing prepared statement to find laptop.");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return false;
        }

        //SQL query to delete laptop from DB
        String deleteLaptopSQLps = "DELETE FROM laptops WHERE id=?";

        try {
            psDeleteLaptop = conn.prepareStatement(deleteLaptopSQLps);
            allStatements.add(psDeleteLaptop);
            psDeleteLaptop.setInt(1, laptopID);

            psDeleteLaptop.execute();       //works fine
        } catch (SQLException sqle) {
            System.err.println("Error preparing statement or executing prepared statement to delete laptop");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteCellphone(int cellphoneID) {
        //SQL query to find laptop in DB
        String findCellphonesSQLps = "SELECT * FROM cellphones WHERE id=?";

        try {
            psFindCellphone = conn.prepareStatement(findCellphonesSQLps);
            allStatements.add(psFindCellphone);
            psFindCellphone.setInt(1, cellphoneID);

            rs = psFindCellphone.executeQuery();
            if (!rs.next()) {
                System.out.println("Could not find cellphone with ID #" + cellphoneID);
                return false;
            }
        } catch (SQLException sqle) {
            System.err.println("Error preparing statement or executing prepared statement to find cellphone.");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return false;
        }

        //SQL query to delete laptop from DB
        String deleteLaptopSQLps = "DELETE FROM cellphones WHERE id=?";

        try {
            psDeleteCellphone = conn.prepareStatement(deleteLaptopSQLps);
            allStatements.add(psDeleteCellphone);
            psDeleteCellphone.setInt(1, cellphoneID);

            psDeleteCellphone.execute();       //works fine
        } catch (SQLException sqle) {
            System.err.println("Error preparing statement or executing prepared statement to delete cellphone");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean reassignLaptop(int laptopID, String assignTo) {
        //SQL query to find laptop in DB
        String findLaptopSQLps = "SELECT * FROM laptops WHERE id=?";

        try {
            psFindLaptop = conn.prepareStatement(findLaptopSQLps);
            allStatements.add(psFindLaptop);
            psFindLaptop.setInt(1, laptopID);

            rs = psFindLaptop.executeQuery();
            if (!rs.next()) {
                System.out.println("Could not find laptop with ID #" + laptopID);
                return false;
            }
        } catch (SQLException sqle) {
            System.err.println("Error preparing statement or executing prepared statement to find laptop.");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return false;
        }

        //SQL query to update record with specified laptop ID
        String reassignLaptopSQLps = "UPDATE laptops SET staff=? WHERE id=?";

        try {
            psUpdateLaptop = conn.prepareStatement(reassignLaptopSQLps);
            allStatements.add(psUpdateLaptop);
            psUpdateLaptop.setString(1, assignTo);
            psUpdateLaptop.setInt(2, laptopID);

            psUpdateLaptop.execute();       //Throws exception:
                                            // error code:  30000
                                            // message:     Table/View 'LAPTOP' does not exist.
                                            // ***FIXED***
                                            // table name was spelt wrong, also 'SET' and 'WHERE' args were flipped
        } catch (SQLException sqle) {
            System.err.println("Error preparing statement or executing prepared statement to reassign laptop");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean reassignCellphone(int cellphoneID, String assignTo) {
        //SQL query to find cellphone in DB
        String findCellphoneSQLps = "SELECT * FROM cellphones WHERE id=?";

        try {
            psFindCellphone = conn.prepareStatement(findCellphoneSQLps);
            allStatements.add(psFindCellphone);
            psFindCellphone.setInt(1, cellphoneID);

            rs = psFindCellphone.executeQuery();
            if (!rs.next()) {
                System.out.println("Could not find cellphone with ID #" + cellphoneID);
                return false;
            }
        } catch (SQLException sqle) {
            System.err.println("Error preparing statement or executing prepared statement to find cellphone.");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return false;
        }

        //SQL query to update record with specified cellphone ID
        String reassignCellphoneSQLps = "UPDATE cellphones SET staff=? WHERE id=?";

        try {
            psUpdateCellphone = conn.prepareStatement(reassignCellphoneSQLps);
            allStatements.add(psUpdateCellphone);
            psUpdateCellphone.setString(1, assignTo);
            psUpdateCellphone.setInt(2, cellphoneID);

            psUpdateCellphone.execute();
        } catch (SQLException sqle) {
            System.err.println("Error preparing statement or executing prepared statement to reassign cellphone");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return false;
        }
        return true;
    }

    public LinkedList<Item> getItemsByStaff(String employee) {

        LinkedList<Item> items = new LinkedList<Item>();

        //SQL query to fetch records from both table where staff member name matches specified name
        String fetchItemsSQL = "SELECT * FROM laptops WHERE staff='" + employee + "' UNION ALL SELECT * FROM cellphones WHERE staff='" + employee + "'";
        //This will create a result set with four columns: id, make, model, and staff
        //each record is a separate item, whereas using a join would return records with 7 columns:
        //one staff column and two of each of the other columns (one frm laptops table and one from cellphones)

        try {
            rs = statement.executeQuery(fetchItemsSQL);
        } catch (SQLException sqle) {
            System.err.println("Error fetching item data from database");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
        }

        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String staff = rs.getString("staff");
                Item i = new Item(id, make, model, staff);
                items.add(i);
            }
        } catch (SQLException sqle) {
            System.err.println("Error reading from result set after fetching item data");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return null;
        }

        return items;
    }

    /** Returns null if any errors in fetching laptops
     *  Returnd empty list if no laptops in DB
     *
     */
    public LinkedList<Laptop> displayAllLaptops() {

        LinkedList<Laptop> allLaptops = new LinkedList<Laptop>();

        String displayAll = "SELECT * FROM laptops";
        try {
            rs = statement.executeQuery(displayAll);
        }
        catch (SQLException sqle) {
            System.err.println("Error fetching all laptops");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return null;
        }


        try {
            while (rs.next()) {

                int id = rs.getInt("id");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String staff = rs.getString("staff");
                Laptop l = new Laptop(id, make, model, staff);
                allLaptops.add(l);

            }
        } catch (SQLException sqle) {
            System.err.println("Error reading from result set after fetching all laptop data");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return null;

        }

        //if we get here, everything should have worked...
        //Return the list of laptops, which will be empty if there is no data in the database
        return allLaptops;
    }

    public LinkedList<Cellphone> displayAllCellphones() {

        LinkedList<Cellphone> allCellphones = new LinkedList<Cellphone>();

        String displayAll = "SELECT * FROM cellphones";
        try {
            rs = statement.executeQuery(displayAll);
        }
        catch (SQLException sqle) {
            System.err.println("Error fetching all cellphones");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return null;
        }


        try {
            while (rs.next()) {

                int id = rs.getInt("id");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String staff = rs.getString("staff");
                Cellphone cell = new Cellphone(id, make, model, staff);
                allCellphones.add(cell);

            }
        } catch (SQLException sqle) {
            System.err.println("Error reading from result set after fetching all cellphone data");
            System.out.println(sqle.getErrorCode() + " " + sqle.getMessage());
            sqle.printStackTrace();
            return null;

        }

        //if we get here, everything should have worked...
        //Return the list of laptops, which will be empty if there is no data in the database
        return allCellphones;
    }
}




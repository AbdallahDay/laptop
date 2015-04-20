/** @author Clara MCTC Java Programming Class */

import java.util.LinkedList;
import java.util.Scanner;

public class InventoryView {

    private final int QUIT = 10;   //Modify if you add more menu items.
    //Can you think of a more robust way of handling menu optons which would be easy to modify with a varying number of menu choices?

    InventoryController myController;
    Scanner s;

    InventoryView(InventoryController c) {
        myController = c;
        s = new Scanner(System.in);
    }


    public void launchUI() {
        //This is a text-based UI. Probably a GUI in a real program

        while (true) {

            int userChoice = displayMenuGetUserChoice();
            if (userChoice == QUIT ) {
                break;
            }

            doTask(userChoice);
        }

    }

    private void doTask(int userChoice) {

        switch (userChoice) {

            case 1:  {
                displayLaptopInventory();
                break;
            }
            case 2: {
                addNewLaptop();
                break;
            }
            case 3: {
                displayLaptopInventory();       //show inventory so user can find IDs easily
                reassignLaptop();
                break;
            }
            case 4: {
                displayLaptopInventory();       //show inventory so user can find IDs easily
                retireLaptop();
                break;
            }
            case 5: {
                displayCellphoneInventory();
                break;
            }
            case 6: {
                addNewCellphone();
                break;
            }
            case 7: {
                displayCellphoneInventory();    //show inventory so user can find IDs easily
                reassignCellphone();
                break;
            }
            case 8: {
                displayCellphoneInventory();    //show inventory so user can find IDs easily
                retireCellphone();
                break;
            }
            case 9: {
                displayItemsByEmployee();
                break;
            }
        }

    }


    private void addNewLaptop() {

        //Get data about new laptop from user

        System.out.println("Please enter make of laptop (e.g. Toshiba, Sony) : ");
        String make = s.nextLine();

        System.out.println("Please enter make of laptop (e.g. Macbook, X-123) : ");
        String model = s.nextLine();

        System.out.println("Please enter name of staff member laptop is assigned to : ");
        String staff = s.nextLine();

        Laptop l = new Laptop(make, model, staff);


        String errorMessage = myController.requestAddLaptop(l);

        if (errorMessage == null ) {
            System.out.println("New laptop added to database");
        } else {
            System.out.println("New laptop could not be added to database");
            System.out.println(errorMessage);
        }

    }

    private void addNewCellphone() {

        System.out.println();
        //Get data about new cellphone from user

        System.out.println("Please enter make of cellphone (e.g. Samsung, Apple) : ");
        String make = s.nextLine();

        System.out.println("Please enter make of cellphone (e.g. S5, iPhone 5) : ");
        String model = s.nextLine();

        System.out.println("Please enter name of staff member cellphone is assigned to : ");
        String staff = s.nextLine();

        Cellphone cell = new Cellphone(make, model, staff);


        String errorMessage = myController.requestAddCellphone(cell);

        if (errorMessage == null ) {
            System.out.println("New cellphone added to database");
        } else {
            System.out.println("New cellphone could not be added to database");
            System.out.println(errorMessage);
        }

    }

    private void retireLaptop() {

        boolean inputOK = false;
        int laptopID = 0;

        //Get laptop ID from user

        while (!inputOK) {
            System.out.println();
            System.out.println("Please enter the ID of the laptop to be retired:");
            String input = s.nextLine();

            try {
                laptopID = Integer.parseInt(input);

                if (laptopID < 1) {
                    System.out.println("Please enter a number greater than or equal to 1");
                    continue;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a whole number");
                continue;
            }
            inputOK = true;
        }

        String errorMessage = myController.requestDeleteLaptop(laptopID);

        if (errorMessage == null) {
            System.out.println("Laptop successfully deleted from database");
        } else {
            System.out.println("Could not retire laptop");
            System.out.println(errorMessage);
        }
    }

    private void retireCellphone() {

        boolean inputOK = false;
        int cellphoneID = 0;

        //Get cellphone ID from user

        while (!inputOK) {
            System.out.println();
            System.out.println("Please enter the ID of the cellphone to be retired:");
            String input = s.nextLine();

            try {
                cellphoneID = Integer.parseInt(input);

                if (cellphoneID < 1) {
                    System.out.println("Please enter a number greater than or equal to 1");
                    continue;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a whole number");
                continue;
            }
            inputOK = true;
        }

        String errorMessage = myController.requestDeleteCellphone(cellphoneID);

        if (errorMessage == null) {
            System.out.println("Cellphone successfully deleted from database");
        } else {
            System.out.println("Could not retire cellphone");
            System.out.println(errorMessage);
        }
    }

    private void reassignLaptop() {

        //Get data from user

        boolean inputOK = false;
        int laptopID = 0;

        //Get laptop ID

        while (!inputOK) {
            System.out.println();
            System.out.println("Please enter the ID of the laptop to be reassigned:");
            String input = s.nextLine();

            try {
                laptopID = Integer.parseInt(input);

                if (laptopID < 1) {
                    System.out.println("Please enter a number greater than or equal to 1");
                    continue;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a whole number");
                continue;
            }
            inputOK = true;
        }

        String staff = "";
        inputOK = false;

        //Get staff member name

        while (!inputOK) {
            System.out.println();
            System.out.println("Who would like to reassign this laptop to?");
            staff = s.nextLine();

            if (staff.isEmpty()) {
                System.err.println("Employee name required");
                continue;
            }
            inputOK = true;
        }

        String errorMessage = myController.requestReassignLaptop(laptopID, staff);

        if (errorMessage == null) {
            System.out.println("Laptop successfully reassigned to " + staff);
        } else {
            System.out.println("Could not reassign laptop to " + staff);
            System.out.println(errorMessage);
        }
    }

    private void reassignCellphone() {

        //Get data from user

        boolean inputOK = false;
        int cellphoneID = 0;

        //Get cellphone ID

        while (!inputOK) {
            System.out.println();
            System.out.println("Please enter the ID of the cellphone to be reassigned:");
            String input = s.nextLine();

            try {
                cellphoneID = Integer.parseInt(input);

                if (cellphoneID < 1) {
                    System.out.println("Please enter a number greater than or equal to 1");
                    continue;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a whole number");
                continue;
            }
            inputOK = true;
        }

        String staff = "";
        inputOK = false;

        //Get staff member name

        while (!inputOK) {
            System.out.println();
            System.out.println("Who would like to reassign this cellphone to?");
            staff = s.nextLine();

            if (staff.isEmpty()) {
                System.err.println("Employee name required");
                continue;
            }
            inputOK = true;
        }

        String errorMessage = myController.requestReassignCellphone(cellphoneID, staff);

        if (errorMessage == null) {
            System.out.println("Cellphone successfully reassigned to " + staff);
        } else {
            System.out.println("Could not reassign cellphone to " + staff);
            System.out.println(errorMessage);
        }
    }

    private void displayLaptopInventory() {

        LinkedList<Laptop> allLaptops = myController.requestLaptopInventory();

        System.out.println();
        if (allLaptops == null) {
            System.out.println("Error fetching all laptops from the database");
        } else if (allLaptops.isEmpty()) {
            System.out.println("No laptops found in database");
        } else {
            for (Laptop l : allLaptops) {
                System.out.println(l);   //Call the toString method in Laptop
            }
        }
    }

    private void displayCellphoneInventory() {

        LinkedList<Cellphone> allCellphones = myController.requestCellphoneInventory();

        System.out.println();
        if (allCellphones == null) {
            System.out.println("Error fetching all cellphones from the database");
        } else if (allCellphones.isEmpty()) {
            System.out.println("No cellphones found in database");
        } else {
            for (Cellphone cell : allCellphones) {
                System.out.println(cell);   //Call the toString method in Cellphone
            }
        }
    }

    private void displayItemsByEmployee() {

        LinkedList<Item> items = new LinkedList<Item>();
        String employee;

        while (true) {
            System.out.println();
            System.out.println("Please enter employee name:");
            employee = s.nextLine();

            if (employee.isEmpty()) {
                System.err.println("Employee name required");
                continue;
            }

            break;
        }

        items = myController.requestItemsByEmployee(employee);

        System.out.println();
        if (items == null) {
            System.out.println("Error fetching item data from the database");
        } else if (items.isEmpty()) {
            System.out.println("No items assigned to " + employee);
        } else {
            for (Item item : items) {
                System.out.println(item);
            }
        }
    }

    private int displayMenuGetUserChoice() {

        boolean inputOK = false;
        int userChoice = -1;

        while (!inputOK) {

            System.out.println();
            System.out.println("Laptop inventory:");
            System.out.println("1. View laptop inventory");
            System.out.println("2. Add a new laptop");
            System.out.println("3. Reassign a laptop to another staff member");
            System.out.println("4. Retire a laptop");

            System.out.println();
            System.out.println("Cellphone inventory:");
            System.out.println("5. View cellphone inventory");
            System.out.println("6. Add a new cellphone");
            System.out.println("7. Reassign a cellphone to another staff member");
            System.out.println("8. Retire a cellphone");

            System.out.println();
            System.out.println("9. Find all laptops and cellphones assigned to an employee");
            System.out.println(QUIT + ". Quit program");

            System.out.println();
            System.out.println("Please enter your selection");

            String userChoiceStr = s.nextLine();
            try {
                userChoice = Integer.parseInt(userChoiceStr);
                if (userChoice < 1  ||  userChoice > QUIT) {
                    System.out.println("Please enter a number between 1 and " + QUIT);
                    continue;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a number");
                continue;
            }
            inputOK = true;
            System.out.println();
        }

        return userChoice;

    }
}
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

// Developed by:
// @Mahmoud_Al-Qataa
//
/*

 scurety

 */

public class Main {
    static ATMSystem atmSystem = new ATMSystem();
    static fileHandler fileHandler = new fileHandler();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        atmSystem.LoadFromFile();
        while (true) {
            userAccountCreationAndLogin();
        }
    }

    public static void userAccountCreationAndLogin() throws IOException {
        System.out.println("----------------------------------------");
        System.out.println("Wellcome to our ATM System \n1-Login\n2-Create account\n3-exit");
        int a = scanner.nextInt();
        switch (a) {
            case 1:
                atmSystem.Login();
                if (atmSystem.tempUser == null) {
                    userAccountCreationAndLogin();
                } else if (atmSystem.tempUser.getFullName().equals("admin 1")) {
                    MainOperationsforAdmin();
                } else {
                    MainOperations();
                }
                break;
            case 2:
                atmSystem.createAccount();
                userAccountCreationAndLogin();
                break;
            case 3:
                System.out.println("Thank You and Good Bay");
                System.exit(0);
                break;
        }
    }

    static void MainOperationsforAdmin() throws IOException {
        System.out.println("Wellcome " + atmSystem.tempUser.getFullName() + " " + atmSystem.tempUser.getAccountNum());
        while (true) {
            System.out.println("------------------------");
            System.out.println("1-Perform Transaction\n2-Find User\n3-File Handling\n4-Logout");
            int a = scanner.nextInt();
            switch (a) {
                case 1: // Perform Transaction
                    atmSystem.performTransactions();
                    break;
                case 2: // Find User
                    atmSystem.findUserr();
                    break;
                case 3: // file handel
                    fileHandel();
                    break;
                case 4:
                    userAccountCreationAndLogin();
                    break;
            }
        }
    }

    static void MainOperations() throws IOException {
        System.out.println("-----------------------------");
        System.out.println("Wellcome " + atmSystem.tempUser.getFullName() + " " + atmSystem.tempUser.getAccountNum());
        System.out.println("1-Perform Transaction\n2-Logout");
        int a = scanner.nextInt();
        switch (a) {
            case 1:
                atmSystem.performTransactions();
                break;
            case 2:
                userAccountCreationAndLogin();
                break;
        }
    }

    static void fileHandel() throws IOException {
        while (true) {
            System.out.println("-----------------------------");
            System.out.println("\n1-Show User Data\n2-Show All Transaction History\n3-Edit File\n4-Back");
            int a = scanner.nextInt();
            switch (a) {
                case 1: // Show User Data
                    fileHandler.readFile(new File("UserData.txt"));
                    break;
                case 2: // Show All Transaction History
                    atmSystem.tempUser.viewAllTransactionHistory();
//                    fileHandler.readFile(new File("TransactionHistory.txt"));
                    break;
                case 3: // Edit File
                    System.out.println("Enter the New Line : ");
                    scanner.nextLine();
                    String newLine = scanner.nextLine();
                    System.out.println("Enter the Place : ");
                    int cindex = scanner.nextInt();
                    fileHandler.EditUsersFile(newLine, cindex);
                    break;
                case 4: // Back
                    MainOperationsforAdmin();
                    break;
            }
        }
    }

}
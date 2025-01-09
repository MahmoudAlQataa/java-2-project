import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ATMSystem {
    ArrayList<userAccount> userAccounts = new ArrayList<>();
    public userAccount tempUser;
    fileHandler fileHandler = new fileHandler();
    int index = 1; // User index
    int attempts = 0; // Login attempts
    boolean isLocked = false; // The Lock
    long lockTime = 0; //

    public void LoadFromFile() throws FileNotFoundException { // ###DONE###
        // ------- to load the Users Data from the file --------
        Scanner reader = new Scanner(new File("UserData.txt"));
        try {
            while (reader.hasNext()) {
                String accountNum = reader.next();
                String firstName = reader.next();
                String lastName = reader.next();
                String fullName = firstName + " " + lastName;
                String phoneNum = reader.next();
                String password = reader.next();
                String balance = reader.nextLine();

                userAccount user = new userAccount(accountNum, fullName, phoneNum, password, Double.parseDouble(balance));
                userAccounts.add(user);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void Login() throws IOException { // ###DONE###
        tempUser = null;
        security();
        Scanner k = new Scanner(System.in);
        System.out.println("Enter your Account Number : ");
        String accountNum = k.nextLine();
        System.out.println("Enter your Password : ");
        String password = k.nextLine();
        index = 1;
        for (userAccount user : userAccounts) {
            if (user.getAccountNum().equals(accountNum) && user.getPassword().equals(password)) {
                tempUser = user;
                attempts = 0;
                break;
            } else {
                index++;
            }
        }
        if (tempUser == null) {
            System.out.println("Invalid Account Num or Password, Try Again");
            attempts++;
        }
        if (attempts == 3) {
            lockTime = System.currentTimeMillis() + 10000;
        }
    }

    public boolean isLocked() {
        if (attempts == 3 && lockTime>=System.currentTimeMillis()) {
            isLocked = true;
        } else {
            isLocked = false;
        }
        return isLocked;
    }

    public void security() throws IOException {
        if (isLocked()) {
            System.out.println("The account is locked for 10s try later ");

            Main main = new Main();
            Main.userAccountCreationAndLogin();
        } else if (attempts == 3) {
            System.out.println("The account is unlocked you can try again ");
            attempts = 0;
        }
    }

    public void createAccount() throws IOException { // ###DONE###
        java.util.Scanner k = new Scanner(System.in);
        System.out.println("Enter your Full Name : ");
        String fullName = k.nextLine();
        System.out.println("Entrer your Phone Number : ");
        String phoneNum = k.nextLine();
        boolean iscontainspace = true;
        String password;
        do {
            System.out.println("Set a password (NOTE\\ Password should not have SPACE): ");
            password = k.nextLine();
            if (password.contains(" ")) {
                System.out.println("(NOTE\\ Password should not have SPACE) try again ");
            } else {
                iscontainspace = false;
            }
        } while (iscontainspace);
        System.out.println("Set your Balance : ");
        double balance = k.nextDouble();
        userAccount user = new userAccount(fullName, phoneNum, password, balance);
        userAccounts.add(user);
        String newLine = user.getAccountNum() + " " + user.getFullName() + " " + user.getPhoneNum() + " " + user.getPassword() + " " + user.getBalance();
        fileHandler.wrriteFile(new File("UserData.txt"), newLine);
    }

    public void findUserr() { // ###DONE###
        index = 1;
        System.out.println("Enter his User Name : ");
        String fullName = new Scanner(System.in).nextLine();
        for (userAccount user : userAccounts) {
            if (user.getFullName().equals(fullName)) {
                System.out.println(user.getAccountNum() + " " + user.getFullName() + " " + user.getPhoneNum() + " " + user.getPassword() + " " + user.getBalance());
            } else {
                index++;
            }
        }
    }

    public void performTransactions() throws IOException { //(Deposit , Withdraw , View Transaction History) ###DONE###
        Scanner s = new Scanner(System.in);
        boolean b = true;
        while (b) {
            System.out.println("-----------------------------");
            System.out.println("1-Deposit\n2-Withdraw\n3-Check Balance\n4-Change Password\n5-View My Transaction History\n6-Back");
            int a = s.nextInt();
            switch (a) {
                case 1: // Deposit
                    System.out.println("How much do you want to Deposit");
                    double d = s.nextDouble();
                    tempUser.deposit(d);
                    Date date = new Date();
                    String newLine = tempUser.getAccountNum() + " " + "-" + d + " " + date.toString();
                    updateTransFile(newLine); // =======NEW LINE===========
                    String newLine1 = tempUser.getAccountNum() + " " + tempUser.getFullName() + " " + tempUser.getPhoneNum() + " " + tempUser.getPassword() + " " + tempUser.getBalance();
                    fileHandler.EditUsersFile(newLine1, index);
                    break;
                case 2: // Withdraw
                    System.out.println("How much do you want to Withdraw");
                    double w = s.nextDouble();
                    tempUser.withdraw(w);
                    Date date1 = new Date();
                    String newLine2 = tempUser.getAccountNum() + " " + "+" + w + " " + date1.toString();
                    updateTransFile(newLine2); // =======NEW LINE===========
                    String newLine3 = tempUser.getAccountNum() + " " + tempUser.getFullName() + " " + tempUser.getPhoneNum() + " " + tempUser.getPassword() + " " + tempUser.getBalance();
                    fileHandler.EditUsersFile(newLine3, index);
                    break;
                case 3: // Show Balance
                    tempUser.checkBalance();
                    break;
                case 4: // Change Password
                    changePassword();
                    break;
                case 5: // View My Transaction History
                    tempUser.viewTransactionHistory(tempUser.getAccountNum());
                    break;
                case 6: // Back
                    b = false;
                    break;
            }
        }
    }

    public void updateTransFile(String newLine) throws IOException { //(after Transaction)   ###DONE###
        fileHandler.wrriteFile(new File("TransactionHistory.txt"), newLine);
    }

    public void changePassword() throws IOException { // have not done yet !!!!!!!!
        Scanner k = new Scanner(System.in);
        System.out.println("Enter the current Password : ");
        String currentPassword = k.nextLine();
        if (tempUser.getPassword().equals(currentPassword)) {
            System.out.println("Enter the new Password (\\NOTE : the password should not contaen SPACE ): ");
            String newPassword;
            boolean isContaenSpace = true;
            do {
                newPassword = k.nextLine();
                if (newPassword.contains(" ")) {
                    System.out.println("(\\NOTE : the password should not contaen SPACE )");
                } else {
                    isContaenSpace = false;
                }
            } while (isContaenSpace);
            tempUser.setPassword(newPassword);
        } else {
            System.out.println("The Password is incorrict!");
        }
        String newLine = tempUser.getAccountNum() + " " + tempUser.getFullName() + " " + tempUser.getPhoneNum() + " " + tempUser.getPassword() + " " + tempUser.getBalance();
        fileHandler.EditUsersFile(newLine, index);
    }

}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

public class userAccount {
    private String accountNum;
    private String fullName;
    private String phoneNum;
    private String password;
    private double balance = 0;
    fileHandler fileHandler = new fileHandler();

    userAccount(String accountNum1,String fullName1, String phoneNum1, String password1, double balance1){
        // For the USERS that will be Loaded from the FIle
        this.accountNum = accountNum1;
        this.fullName = fullName1;
        this.phoneNum = phoneNum1;
        this.password = password1;
        this.balance = balance1;
    }

    userAccount(String fullName, String phoneNum, String password, double balance) {
        // For the USERS that will be createn manual
        this.fullName = fullName;
        this.phoneNum = phoneNum;
        this.password = password;
        this.balance = balance;
        setAccountNum();
    }

    public void deposit(double howmuch) {
        this.balance += howmuch;
        System.out.println("your Balance is : " + balance);
    }

    public void withdraw(double howmuch) {
        if (howmuch > balance) {
            System.out.println("Your balance is : "+ balance);
        }else {
            this.balance -= howmuch;
            System.out.println("Your balance is : "+ balance);
        }
    }

    public void checkBalance() {
        System.out.println("Your Balance is : " + balance);
    }

    public void viewTransactionHistory(String s) throws FileNotFoundException { // ###DONE###
        fileHandler.readInFile(new File("TransactionHistory.txt"),s);
    }

    public void viewAllTransactionHistory() throws FileNotFoundException { // ###DONE###
        fileHandler.readFile(new File("TransactionHistory.txt"));
    }



    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum() {
                   Random random = new Random();
        int a = random.nextInt();
        if (a < 0) {
            a *= -1;
        }
        String s = String.valueOf(a);
        this.accountNum = s.substring(0, 7);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

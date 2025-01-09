import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class fileHandler {

    public void readFile(File file) throws FileNotFoundException {
        //Handles reading All Transaction History (only admin can use it)
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) {
            String accountNum = reader.next();
            String firstName = reader.next();
            String lastName = reader.next();
            String phoneNum = reader.next();
            String password = reader.next();
            String balance = reader.nextLine();
            String newLine = accountNum + " " + firstName + " " + lastName + " " + phoneNum + " " + password + " " + balance;
            System.out.println(newLine);
        }
        reader.close();
    }

    public void readInFile(File file, String accountNum) throws FileNotFoundException {
        //Handles reading a specific user Transaction History
        Scanner reader = new Scanner(file);
        try {
            while (reader.hasNextLine()) {
                if (accountNum.equals(reader.next())) {
                    System.out.println(reader.nextLine());
                } else {
                    reader.nextLine();
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void wrriteFile(File file, String newLine) throws IOException {//Manges transaction history in a seprate file
        File tempfile = new File("temp.txt");
        // -------------------------------from file to temp file------------------------
        FileWriter fileWriter = new FileWriter(tempfile);
        Scanner scanner = new Scanner(file);
        try {
            while (scanner.hasNextLine()) {
                fileWriter.write(scanner.nextLine() + "\n");
            }
            fileWriter.append(newLine + "\n");
            scanner.close();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //--------------------------------from temp file to file---------------------------
        fileWriter = new FileWriter(file);
        scanner = new Scanner(tempfile);
        try {
            while (scanner.hasNextLine()) {
                fileWriter.write(scanner.nextLine() + "\n");
            }
            scanner.close();
            fileWriter.close();
            tempfile.delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void EditUsersFile(String newLine, int index) throws IOException {//Manges transaction history in a seprate file
        File tempfile = new File("temp.txt");
        File file = new File("UserData.txt");
        Scanner scanner = new Scanner(file);
        FileWriter fileWriter = new FileWriter(tempfile);
        int cindex = 1;
        try {
            while (scanner.hasNextLine()) {
                if (cindex == index) {
                    fileWriter.write(newLine + "\n");
                    scanner.nextLine();
                } else {
                    fileWriter.write(scanner.nextLine() + "\n");
                }
                cindex++;
            }
            scanner.close();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        fileWriter = new FileWriter(file);
        scanner = new Scanner(tempfile);
        try {
            while (scanner.hasNextLine()) {
                fileWriter.write(scanner.nextLine() + "\n");
            }
            scanner.close();
            fileWriter.close();
            tempfile.delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

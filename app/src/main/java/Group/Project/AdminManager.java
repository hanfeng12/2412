package Group.Project;

import java.io.*;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class AdminManager {

    private static final String UserPath = "src/main/resources/accounts.txt";

    private static final String AdminPath = "src/main/resources/AdminAccounts.txt";

    public void view_all_users (){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(UserPath));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add_a_user(){
        UserManager userManager = new UserManager();
        userManager.register();
    }

    public void delete_a_user(){
        List<String> allLines = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        try (BufferedReader reader = new BufferedReader(new FileReader(UserPath))) {
            String curLine;
            while ((curLine = reader.readLine()) != null) {
                allLines.add(curLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < allLines.size(); i++) {
            System.out.println(allLines.get(i));
        }

        System.out.println("Enter the username you want to delete: ");
        String username = scanner.nextLine();

        List<String> newFile = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(UserPath))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.contains("Username: " + username + ",")) {
                    newFile.add(currentLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(UserPath))) {
            for (String line : newFile) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path folderPath = Paths.get("src/main/resources/" + username);
        try {
            Files.walk(folderPath, FileVisitOption.FOLLOW_LINKS)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Delete complete");
    }

    public void view_stats(){

    }

    public void add_a_admin(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        System.out.println("Enter password: ");
        String passwd = scanner.nextLine();

        User user = new User(username, passwd);

        try {
            FileWriter writer = new FileWriter(AdminPath, true);
            writer.write(user.AdmintoString() + '\n');
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scanner.close();
    }

}



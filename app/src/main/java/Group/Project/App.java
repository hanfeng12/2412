package Group.Project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void createUserDirectories(String username) {
        try {
            Files.createDirectories(Paths.get("src/main/resources/" + username + "/download"));
            Files.createDirectories(Paths.get("src/main/resources/" + username + "/upload"));
            Files.createDirectories(Paths.get("src/main/resources/" + username + "/library"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void AddScrolls(String username) {
        Path sourceDirectory = Paths.get("src/main/resources/" + username + "/upload");
        Path targetSharedLibrary = Paths.get("src/main/resources/library");
        Path targetUserLibrary = Paths.get("src/main/resources/" + username + "/library");

        File uploadDir = new File(sourceDirectory.toString());
        File[] files = uploadDir.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files available for upload.");
            return;
        }

        System.out.println("Files available for upload:");
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i].getName());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of the file you want to upload:");
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= files.length) {
            File selectedFile = files[choice - 1];

            String uniqueFilename = generateUniqueFilename(targetSharedLibrary, selectedFile.getName());
            Path targetSharedFilePath = targetSharedLibrary.resolve(uniqueFilename);

            try {
                Files.copy(selectedFile.toPath(), targetSharedFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Error uploading the file to shared library: " + e.getMessage());
                return;
            }

            Path targetUserFilePath = targetUserLibrary.resolve(uniqueFilename);
            try {
                Files.copy(targetSharedFilePath, targetUserFilePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File uploaded and backed up successfully!");
            } catch (IOException e) {
                System.out.println("Error copying the file to user's library: " + e.getMessage());
            }

        } else {
            System.out.println("Invalid choice.");
        }
    }


    public static void EditScrolls(String username) {

    }



    public static void DownloadScrolls(String username) {
        Path sourceDirectory = Paths.get("src/main/resources/library");
        Path targetDirectory = Paths.get("src/main/resources/" + username + "/download");

        File libraryDir = new File(sourceDirectory.toString());
        File[] files = libraryDir.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files available for download.");
            return;
        }

        System.out.println("Files available for download:");
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i].getName());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of the file you want to preview:");
        int choice = scanner.nextInt();

        List<String> allLines = null; // Move the definition here

        if (choice > 0 && choice <= files.length) {
            File selectedFile = files[choice - 1];

            // Preview the file content
            try {
                allLines = Files.readAllLines(selectedFile.toPath()); // Update here
                System.out.println("\nFile Preview:");
                for (String line : allLines) {
                    System.out.println(line);
                }
                System.out.println("\nEnd of File Preview\n");
            } catch (IOException e) {
                System.out.println("Error previewing the file: " + e.getMessage());
                return;
            }

            System.out.println("Do you want to download this file? (yes/no)");
            String downloadChoice = scanner.next();

            if (downloadChoice.equalsIgnoreCase("yes")) {
                System.out.println("Enter the start line number:");
                int startLine = scanner.nextInt();

                System.out.println("Enter the end line number:");
                int endLine = scanner.nextInt();

                try {
                    List<String> selectedLines = allLines.subList(startLine - 1, endLine);
                    String originalFilename = selectedFile.getName();
                    Path targetFilePath = targetDirectory.resolve(originalFilename);

                    // Check if file already exists
                    if (Files.exists(targetFilePath)) {
                        System.out.println("File already exists. Do you want to overwrite it? (yes/no)");
                        String overwriteChoice = scanner.next();
                        if (!overwriteChoice.equalsIgnoreCase("yes")) {
                            // Add a suffix to generate a new filename
                            String newFilename = generateUniqueFilename(targetDirectory, originalFilename);
                            targetFilePath = targetDirectory.resolve(newFilename);
                        }
                    }

                    Files.write(targetFilePath, selectedLines);
                    System.out.println("File downloaded successfully!");
                } catch (IOException e) {
                    System.out.println("Error downloading the file: " + e.getMessage());
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Invalid line numbers provided.");
                }
            } else {
                System.out.println("File download cancelled.");
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }




    public static void RemoveScrolls(String username) {
        Path userLibraryDirectory = Paths.get("src/main/resources/" + username + "/library");
        Path sharedLibraryDirectory = Paths.get("src/main/resources/library");

        File userLibraryDir = new File(userLibraryDirectory.toString());
        File[] files = userLibraryDir.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files available for deletion.");
            return;
        }

        System.out.println("Files available for deletion:");
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i].getName());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of the file you want to delete:");
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= files.length) {
            File selectedFile = files[choice - 1];

            try {
                Files.delete(userLibraryDirectory.resolve(selectedFile.getName()));
            } catch (IOException e) {
                System.out.println("Error deleting the file from user's library: " + e.getMessage());
                return;
            }

            try {
                Files.delete(sharedLibraryDirectory.resolve(selectedFile.getName()));
                System.out.println("File deleted successfully!");
            } catch (IOException e) {
                System.out.println("Error deleting the file from shared library: " + e.getMessage());
            }

        } else {
            System.out.println("Invalid choice.");
        }
    }

    public static void ViewScrolls() {

    }

    public static void SearchScrolls() {

    }




    private static String generateUniqueFilename(Path directory, String originalFilename) {
        int counter = 1;
        String filenameWithoutExtension = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFilename = filenameWithoutExtension + "_" + counter + extension;

        while (Files.exists(directory.resolve(newFilename))) {
            counter++;
            newFilename = filenameWithoutExtension + "_" + counter + extension;
        }

        return newFilename;
    }



    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        AdminManager adminManager = new AdminManager();
        GuestUser guestUser = new GuestUser();

        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Register\n2. Login\n3. Use as a guest user\n");
        System.out.println("Enter your choice (1/2/3):");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            userManager.register();
            createUserDirectories(userManager.getLatestRegisteredUsername());

        } else if (choice.equals("2")) {
            User user = userManager.login();


//            if (user != null)
            if (userManager.getAdminCheckLogin()) {
                System.out.println("1. View all the users\n2. Add a user\n3. Delete a user\n4. View stats\n5. Add a new admin\n");
                System.out.println("Enter your choice (1/2/3/4/5):");
                String choice2 = scanner.nextLine();

                switch (choice2) {
                    case "1":
                        adminManager.view_all_users();
                        break;
                    case "2":
                        adminManager.add_a_user();
                        break;
                    case "3":
                        adminManager.delete_a_user();
                        break;
                    case "4":
                        adminManager.view_stats();
                        break;
                    case "5":
                        adminManager.add_a_admin();
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        return;
                }


            } else if (userManager.getCheckLogin()) {

                System.out.println("1. Update user profiles\n2. Add new scrolls\n3. Edit scrolls\n4. Remove scrolls\n5. View scrolls\n6. Download scrolls\n7. Search scrolls");
                System.out.println("Enter your choice (1/2/3/4/5/6/7):");
                String choice3 = scanner.nextLine();

                switch (choice3) {
                    case "1":
                        userManager.updateUserObject(user);
                        break;
                    case "2":
                        AddScrolls(user.getUsername());
                        break;
                    case "3":
                        EditScrolls(user.getUsername());
                        break;
                    case "4":
                        RemoveScrolls(user.getUsername());
                        break;
                    case "5":
                        ViewScrolls();
                        break;
                    case "6":
                        DownloadScrolls(user.getUsername());
                        break;
                    case "7":
                        SearchScrolls();
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        return;
                }


            } else {
                System.out.println("Invalid input.");
            }

        } else if (choice.equals("3")) {
            System.out.println("Guest user login successful");
            System.out.println("1. View scrolls");
            System.out.println("Enter your choice (1):");
            String choice4 = scanner.nextLine();
            if (choice4.equals("1")){
                ViewScrolls();
            }

        } else {
            System.out.println("Invalid input.");
        }

    }
}
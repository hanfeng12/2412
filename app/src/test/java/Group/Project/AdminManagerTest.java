package Group.Project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminManagerTest {

    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream capturedOutput;

    // Create a temporary directory to store our test file
    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUpOutput() {
        capturedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutput));
    }

    @AfterEach
    public void restoreSystemOutput() {
        System.setOut(originalSystemOut);
    }

    @Test
    public void testView() throws IOException {
        // Mock the FILE_PATH to our test file path
        Path testFilePath = tempDir.resolve("test_accounts.txt");
        AdminManager.FILE_PATH = testFilePath.toString();

        // Write some test data to the test file
        String testContent = "Phone number: 1234567890, Email address: test1@example.com, Full name: Test User1, ID keys: 12345, Username: user1, Password: pass1\n" +
                "Phone number: 0987654321, Email address: test2@example.com, Full name: Test User2, ID keys: 67890, Username: user2, Password: pass2\n";
        Files.write(testFilePath, testContent.getBytes());

        // Now, run the view method
        AdminManager adminManager = new AdminManager();
        adminManager.view();

        // Assert that the output is as expected
        assertEquals(testContent.trim(), capturedOutput.toString().trim());
    }
}

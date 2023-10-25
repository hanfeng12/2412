package Group.Project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;

public class UserManagerTest {

    private UserManager userManager;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setup() throws Exception {
        userManager = new UserManager();
        File testFile = tempDir.resolve("accounts.txt").toFile();

        // Make the FILE_PATH of UserManager point to our temporary test file.
        TestUtils.setPrivateStaticField(UserManager.class, "FILE_PATH", testFile.getAbsolutePath());

        // Create a test user entry in the file for testing.
        BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
        writer.write("Phone number: 123-456-7890, Email address: test@email.com, Full name: John Doe, ID keys: ID1234, Username: john_doe, Password: password123\n");
        writer.close();
    }

    @Test
    public void testLoginSuccess() {
        User user = userManager.login();

        assertNotNull(user, "Login should be successful for test data.");
        assertEquals("john_doe", user.getUsername(), "Expected username to be 'john_doe'.");
        assertTrue(userManager.getCheckLogin(), "Expected checkLogin to be true after successful login.");
    }

    // Utility class to help modify private static fields.
    static class TestUtils {
        public static void setPrivateStaticField(Class<?> clazz, String fieldName, Object newValue) throws Exception {
            java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            java.lang.reflect.Field modifiersField = java.lang.reflect.Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
            field.set(null, newValue);
        }
    }
}

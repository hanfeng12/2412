package Group.Project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayInputStream mockInput;
    private ByteArrayOutputStream capturedOutput;

    @BeforeEach
    public void setUpOutput() {
        capturedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutput));
    }

    private void provideInput(String data) {
        mockInput = new ByteArrayInputStream(data.getBytes());
        System.setIn(mockInput);
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    @Test
    public void testMainRegisterChoice() {
        provideInput("1\n"); // Mocking user choosing "1. Register"
        App.main(new String[]{});
        assertTrue(capturedOutput.toString().trim().endsWith("Enter password:")); // Assuming userManager.register() is called properly, the last input prompt should be for password
    }

    @Test
    public void testMainLoginChoice() {
        // Here you might mock a successful login, then choice to "Update user profiles"
        // For simplicity, let's just check if it asks for login
        provideInput("2\nsomeusername\nsomepassword\n");
        App.main(new String[]{});
        assertTrue(capturedOutput.toString().trim().endsWith("Enter your passwd:"));
    }

}


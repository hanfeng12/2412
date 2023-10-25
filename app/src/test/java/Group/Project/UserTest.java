package Group.Project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("123-456-7890", "example@example.com", "John Doe", "ID1234", "johndoe", "password123");
    }

    @Test
    public void testPhoneNum() {
        assertEquals("123-456-7890", user.getPhoneNum());
        user.setPhoneNum("987-654-3210");
        assertEquals("987-654-3210", user.getPhoneNum());
    }

    @Test
    public void testEmailAddr() {
        assertEquals("example@example.com", user.getEmail());
        user.setEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    public void testFullName() {
        assertEquals("John Doe", user.getFullName());
        user.setFullName("Jane Doe");
        assertEquals("Jane Doe", user.getFullName());
    }

    @Test
    public void testIdKey() {
        assertEquals("ID1234", user.getIdKey());
        user.setIdKey("ID9876");
        assertEquals("ID9876", user.getIdKey());
    }

    @Test
    public void testUsername() {
        assertEquals("johndoe", user.getUsername());
        user.setUsername("janedoe");
        assertEquals("janedoe", user.getUsername());
    }

    @Test
    public void testPasswd() {
        assertEquals("password123", user.getPasswd());
        user.setPasswd("password456");
        assertEquals("password456", user.getPasswd());
    }

    @Test
    public void testLineNum() {
        user.setLineNumber(5);
        assertEquals(5, user.getLineNumber());
    }

    @Test
    public void testToString() {
        String expectedString = "Phone number: 123-456-7890, Email address: example@example.com, Full name: John Doe, ID keys: ID1234, Username: johndoe, Password: password123";
        assertEquals(expectedString, user.toString());
    }
}
package email;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class EmailNotificationTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private EmailNotification emailNotification;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        emailNotification = new EmailNotification();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        emailNotification.close();
    }

    @Test
    public void testEmailToOne() {
        User user = new User("John", "john@example.com");
        emailNotification.emailTo(user);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String expectedOutput = "Notification John to email john@example.com" + System.lineSeparator()
                + "Add a new event to John" + System.lineSeparator();
        assertThat(outputStream.toString()).isEqualTo(expectedOutput);
    }

    @Test
    public void testEmailToMany() {
        User user1 = new User("John", "john@example.com");
        User user2 = new User("Tom", "tom@example.com");
        User user3 = new User("Sam", "sam@example.com");
        User user4 = new User("Donald", "donald@example.com");
        User user5 = new User("Kris", "kris@example.com");
        User user6 = new User("David", "david@example.com");
        List<User> list = List.of(user1, user2, user3, user4, user5, user6);
        list.forEach(u -> emailNotification.emailTo(u));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String expectedOutput1 = "Notification John to email john@example.com" + System.lineSeparator()
                + "Add a new event to John" + System.lineSeparator();
        String expectedOutput2 = "Notification Tom to email tom@example.com" + System.lineSeparator()
                + "Add a new event to Tom" + System.lineSeparator();
        String expectedOutput3 = "Notification Sam to email sam@example.com" + System.lineSeparator()
                + "Add a new event to Sam" + System.lineSeparator();
        String expectedOutput4 = "Notification Donald to email donald@example.com" + System.lineSeparator()
                + "Add a new event to Donald" + System.lineSeparator();
        String expectedOutput5 = "Notification Kris to email kris@example.com" + System.lineSeparator()
                + "Add a new event to Kris" + System.lineSeparator();
        String expectedOutput6 = "Notification David to email david@example.com" + System.lineSeparator()
                + "Add a new event to David" + System.lineSeparator();
        assertThat(outputStream.toString()).contains(expectedOutput1);
        assertThat(outputStream.toString()).contains(expectedOutput2);
        assertThat(outputStream.toString()).contains(expectedOutput3);
        assertThat(outputStream.toString()).contains(expectedOutput4);
        assertThat(outputStream.toString()).contains(expectedOutput5);
        assertThat(outputStream.toString()).contains(expectedOutput6);
    }
}
package sgu.beo.DAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sgu.beo.model.User;

public class UserDAOTest {
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() {
        // Tạo một instance của UserDAO
        userDAO = UserDAO.getInstance();
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("user_test");
        user.setPassword("password_test");

        boolean isInserted = userDAO.insert(user);
        assertTrue(isInserted, "The user should be inserted successfully.");
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(1); // Giả sử ID đã có trong DB
        user.setUsername("updated_user");
        user.setPassword("updated_password");

        boolean isUpdated = userDAO.update(user);
        assertTrue(isUpdated, "The user should be updated successfully.");
    }

    @Test
    public void testFindAll() {
        List<User> users = userDAO.findAll();
        assertNotNull(users, "The list of users should not be null.");
        assertTrue(users.size() > 0, "There should be at least one user in the list.");
    }

    @Test
    public void testFindById() {
        int id = 1; // Giả sử ID này có trong DB
        User user = userDAO.findById(id);
        assertNotNull(user, "The user should be found by ID.");
        assertEquals(id, user.getId(), "The user ID should match.");
    }

    @Test
    public void testFindByName() {
        String username = "user";
        List<User> users = userDAO.findByName(username);
        users.forEach(user -> {
            System.out.println(user);
            assertTrue(user.getUsername().toLowerCase().contains(username.toLowerCase()),
                    "The username should contain the search term.");
        });
        assertNotNull(users, "The list of users should not be null.");
        assertTrue(users.size() > 0, "There should be at least one user matching the username.");
    }
}

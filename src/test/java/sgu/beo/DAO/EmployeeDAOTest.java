package sgu.beo.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sgu.beo.model.Employee;
import sgu.beo.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDAOTest {

    private EmployeeDAO employeeDAO = EmployeeDAO.getInstance();

    @Test
    public void testInsert() {
        Employee employee = new Employee();
        employee.setName("Nguyen Van Test");
        employee.setPhone("0123456789");
        employee.setAddress("123 Test Street");
        employee.setEmail("test@example.com");
        employee.setRole("Admin");

        boolean isInserted = employeeDAO.insert(employee);
        assertTrue(isInserted, "Employee should be inserted successfully.");
    }

    @Test
    public void testUpdate() {
        Employee employee = new Employee();
        User user = new User();
        user.setId(1); // Giả sử user_id này đã tồn tại trong DB

        employee.setId(1);
        employee.setUser(user);
        employee.setName("Updated Name");
        employee.setPhone("0987654321");
        employee.setAddress("456 Updated Street");
        employee.setEmail("updated@example.com");
        employee.setRole("Manager");

        boolean isUpdated = employeeDAO.update(employee);
        assertTrue(isUpdated, "Employee should be updated successfully.");
    }

    @Test
    public void testFindAll() {
        List<Employee> employees = employeeDAO.findAll();
        assertNotNull(employees, "The list of employees should not be null.");
        assertTrue(employees.size() > 0, "There should be at least one employee in the list.");
    }

    @Test
    public void testFindById() {
        int id = 1; // Giả sử ID này tồn tại
        Employee employee = employeeDAO.findById(id);
        assertNotNull(employee, "Employee should be found by ID.");
        assertEquals(id, employee.getId(), "The employee ID should match.");
    }

    @Test
    public void testFindByName() {
        String name = "name";
        List<Employee> employees = employeeDAO.findByName(name);
        assertNotNull(employees, "The list of employees should not be null.");
        assertTrue(employees.size() > 0, "There should be at least one employee matching the name.");
        employees.forEach(emp -> assertTrue(emp.getName().toLowerCase().contains(name.toLowerCase()),
                "Employee name should contain the search term."));
    }
}

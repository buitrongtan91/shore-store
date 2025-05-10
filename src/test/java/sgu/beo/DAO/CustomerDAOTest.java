package sgu.beo.DAO;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import sgu.beo.model.Customer;

public class CustomerDAOTest {
    @Test
    public void testInsertAndGetId() {
        Customer cus = new Customer();
        cus.setName("test");
        cus.setPhone("test");
        cus.setName("test");
        cus.setAddress("test");

        int rs = CustomerDAO.getInstance().insertAndGetId(cus);
        System.out.println(rs);

        assertTrue(rs > -1);
    }

    @Test
    public void testUpdate() {
        Customer cus = new Customer();
        cus.setId(1);
        cus.setName("test update");
        cus.setPhone("test update");
        cus.setEmail("Test update");
        cus.setAddress("test update");
    }

    @Test
    public void testFindAll() {
        List<Customer> rs = CustomerDAO.getInstance().findAll();

        rs.forEach(cus -> System.out.println(cus));

        assertTrue(rs.size() > 0);
    }

    @Test
    public void testFindById() {
        Customer rs = CustomerDAO.getInstance().findById(1);

        System.out.println(rs);

        assertNotNull(rs);
    }

    @Test
    public void findByName() {
        List<Customer> customers = CustomerDAO.getInstance().findByName("test");
        customers.forEach(cus -> System.out.println(cus));

        assertTrue(customers.size() > 0);
    }

    @Test
    public void testFindByPhone() {
        List<Customer> customers = CustomerDAO.getInstance().findByPhone("test");
        customers.forEach(c -> System.out.println(c));

        assertTrue(customers.size() > 0);
    }

}

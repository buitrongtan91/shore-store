package sgu.beo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import sgu.beo.model.Employee;

public class AuthServiceTest {
    @Test
    public void testLogin() {
        Employee e = AuthService.login("user_test", "password_test");
        System.out.println(e);
        assertNotNull(e);
    }
}

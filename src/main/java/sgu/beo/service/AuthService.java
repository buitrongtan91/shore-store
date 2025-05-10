package sgu.beo.service;

import sgu.beo.DAO.EmployeeDAO;
import sgu.beo.DAO.UserDAO;
import sgu.beo.model.Employee;
import sgu.beo.model.User;

public class AuthService {
    private static final UserDAO userDAO = UserDAO.getInstance();
    private static final EmployeeDAO employeeDAO = EmployeeDAO.getInstance();

    public static Employee login(String username, String password) {
        User user = userDAO.findByUserName(username);
        if (user == null) {
            return null;
        }
        if (!user.getPassword().equals(password)) {
            return null;
        }
        Employee employee = employeeDAO.findByUserId(user.getId());
        return employee;
    }
}

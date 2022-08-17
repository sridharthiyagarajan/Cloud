package com.teamportfolio.it.dao.employee;

import java.util.List;

import com.teamportfolio.it.dao.entity.Employee;

public interface EmployeeDao {

	void addEmployee(Employee role);

	void updateEmployee(Employee role);

	void deleteEmployee(String id);

	List<Employee> retrieveEmployeeByGivenColumn(String column, String value, boolean isExactSearch);

	List<Employee> retrieveAllEmployees();
}
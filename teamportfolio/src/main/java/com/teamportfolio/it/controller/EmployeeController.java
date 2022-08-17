package com.teamportfolio.it.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teamportfolio.it.dto.EmployeeDto;
import com.teamportfolio.it.exception.DataNotFoundException;
import com.teamportfolio.it.exception.DuplicateEntityException;
import com.teamportfolio.it.exception.InvalidInputException;
import com.teamportfolio.it.service.EmployeeService;
import com.teamportfolio.it.util.Constants;

@RestController
public class EmployeeController {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class.getCanonicalName());

	private static final String EMPLOYEES = "employees";

	@Autowired
	private EmployeeService employeeService;

	@GetMapping(value = Constants.VERSION + EMPLOYEES)
	public List<EmployeeDto> retrieveAllEmployees() throws DataNotFoundException {

		return employeeService.retrieveAllEmployees();
	}

	@PostMapping(value = Constants.VERSION + EMPLOYEES)
	public void addEmployee(@Valid @RequestBody EmployeeDto employee) throws DuplicateEntityException, DataNotFoundException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given employee for addition is {}.", employee);
		}

		employeeService.addEmployee(employee);
	}

	@PutMapping(value = Constants.VERSION + EMPLOYEES + "/{id}")
	public void updateEmployee(@Valid @RequestBody EmployeeDto employee, @PathVariable(required = true) String id)
			throws DuplicateEntityException, InvalidInputException, DataNotFoundException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given employee for updation is {}.", employee);
		}

		employeeService.updateEmployee(employee, id);
	}

	@DeleteMapping(value = Constants.VERSION + EMPLOYEES + "/{id}")
	public void deleteEmployee(@PathVariable(required = true) String id) throws DuplicateEntityException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given employee identifier for deletion is {}.", id);
		}

		employeeService.deleteEmployee(id);
	}

	@GetMapping(Constants.VERSION + EMPLOYEES + "/{id}")
	public EmployeeDto retrieveEmployeeById(@PathVariable(required = true) String id) throws DataNotFoundException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given identifier for reading the employee is : id - {}", id);
		}

		return employeeService.retrieveEmployeeById(id);
	}

	@GetMapping(Constants.VERSION + EMPLOYEES + "/searchByTitle")
	public List<EmployeeDto> retrieveEmployeesByJobTitle(@RequestParam(required = true) String title) throws DataNotFoundException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given title for reading the employee is : title - {}", title);
		}

		return employeeService.retrieveEmployeesByJobTitle(title);
	}

}
package com.teamportfolio.it.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.teamportfolio.it.dao.employee.EmployeeDao;
import com.teamportfolio.it.dao.entity.Employee;
import com.teamportfolio.it.dao.entity.Role;
import com.teamportfolio.it.dto.EmployeeDto;
import com.teamportfolio.it.dto.RoleDto;
import com.teamportfolio.it.exception.DataNotFoundException;
import com.teamportfolio.it.exception.DuplicateEntityException;
import com.teamportfolio.it.exception.InvalidInputException;
import com.teamportfolio.it.util.ActionEntity;
import com.teamportfolio.it.util.ColumnConstants;
import com.teamportfolio.it.util.DtoUtility;

@Service
public class EmployeeService {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class.getCanonicalName());

	private final Validator validator;

	private final EmployeeDao employeeDao;

	private final RoleService roleService;

	@Autowired
	public EmployeeService(EmployeeDao employeeDao, Validator validator, RoleService roleService) {

		this.employeeDao = employeeDao;
		this.validator = validator;
		this.roleService = roleService;
	}

	@Transactional
	public void addEmployee(EmployeeDto inputEmployee) throws DuplicateEntityException, DataNotFoundException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addEmployee(), method STARTED and the given input is : inputEmployee - {}.", inputEmployee);
		}

		Role employeeRole = inputEmployee != null
				? DtoUtility.convertRoleDtoToRole(roleService.retrieveRoleById(inputEmployee.getRoleId()))
				: null;

		// Validating given employee and whether given role exists in the application.
		validateEmployee(inputEmployee, employeeRole, ActionEntity.CREATE);

		// Validating whether given id is already present.
		if (retrieveEmployeeByGivenColumn(ColumnConstants.EMPLOYEE_REFERENCE_NUMBER,
				inputEmployee.getEmployeeReferenceNumber(), true) == null) {

			employeeDao.addEmployee(DtoUtility.convertEmployeeDtoToEmployee(inputEmployee, employeeRole));

		} else {

			LOG.error("Given employee {} is already present. Please try again with a valid input.", inputEmployee);

			throw new DuplicateEntityException(
					"Given employee is already present. Please try again with a valid input.");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addEmployee(), method ENDED.");
		}
	}

	@Transactional
	public void updateEmployee(EmployeeDto inputEmployee, String id)
			throws DuplicateEntityException, InvalidInputException, DataNotFoundException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateEmployee(), method STARTED and the given input is : inputEmployee - {}, id - {}.",
					inputEmployee, id);
		}

		if (inputEmployee != null) {

			if (StringUtils.isBlank(inputEmployee.getId())) {

				inputEmployee.setId(id);

			} else if (!inputEmployee.getId().equals(id)) {

				throw new InvalidInputException(
						"Employee identifier present in the URL is different from the request.");
			}
		}

		Role employeeRole = inputEmployee != null
				? DtoUtility.convertRoleDtoToRole(roleService.retrieveRoleById(inputEmployee.getRoleId()))
				: null;

		// Validating given employee.
		validateEmployee(inputEmployee, employeeRole, ActionEntity.UPDATE);

		// Validating whether given id is present and title given for update does not
		// exist.
		List<Employee> outputEmployees = employeeDao.retrieveEmployeeByGivenColumn(ColumnConstants.ID,
				inputEmployee.getId(), true);
		Employee existingEmployee = !CollectionUtils.isEmpty(outputEmployees) ? outputEmployees.get(0) : null;

		if (existingEmployee != null && inputEmployee.getEmployeeReferenceNumber()
				.equalsIgnoreCase(existingEmployee.getEmployeeReferenceNumber())) {

			if (StringUtils.isNotBlank(inputEmployee.getEmail())) {
				existingEmployee.setEmail(inputEmployee.getEmail());
			}

			if (StringUtils.isNotBlank(inputEmployee.getFirstName())) {
				existingEmployee.setFirstName(inputEmployee.getFirstName());
			}

			if (StringUtils.isNotBlank(inputEmployee.getLastName())) {
				existingEmployee.setLastName(inputEmployee.getLastName());
			}

			if (StringUtils.isNotBlank(inputEmployee.getPhone())) {
				existingEmployee.setPhone(inputEmployee.getPhone());
			}

			if (StringUtils.isNotBlank(inputEmployee.getRoleId())) {
				existingEmployee.setRoleId(inputEmployee.getRoleId());
			}

			employeeDao.updateEmployee(existingEmployee);

		} else {

			String errorMsg = String.format(
					"Employee reference number %s of the employee %s cannot be modified. Please try again with a valid input.",
					inputEmployee);

			LOG.error(String.format("In updateEmployee(), %s", errorMsg));
			throw new DuplicateEntityException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateEmployee(), method ENDED.");
		}
	}

	@Transactional
	public void deleteEmployee(String id) throws DuplicateEntityException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteEmployee(), method STARTED and the given input is : id - {}.", id);
		}

		// Validating whether given id is present.
		if (retrieveEmployeeByGivenColumn(ColumnConstants.ID, id, true) != null) {

			employeeDao.deleteEmployee(id);

		} else {

			String errorMsg = String
					.format("Given employee identifier %s does not exist. Please try again with a valid input.", id);

			LOG.error(errorMsg);
			throw new DuplicateEntityException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteEmployee(), method ENDED.");
		}
	}

	@Transactional
	public List<EmployeeDto> retrieveAllEmployees() throws DataNotFoundException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveAllEmployees(), method STARTED.");
		}

		List<EmployeeDto> outputEmployeeDtos = null;

		List<Employee> outputEmployees = employeeDao.retrieveAllEmployees();

		if (!CollectionUtils.isEmpty(outputEmployees)) {

			outputEmployeeDtos = new ArrayList<EmployeeDto>();

			for (Employee employee : outputEmployees) {

				outputEmployeeDtos.add(DtoUtility.convertEmployeeToEmployeeDto(employee));
			}
		} else {

			String errorMsg = "No employee is present in the application.";

			LOG.error(String.format("In retrieveEmployeeById(), %s", errorMsg));
			throw new DataNotFoundException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {

			LOG.debug("In retrieveAllEmployees(), method ENDED and the output employees size is {}.",
					!CollectionUtils.isEmpty(outputEmployees) ? outputEmployees.size() : 0);
		}

		return outputEmployeeDtos;
	}

	@Transactional
	public EmployeeDto retrieveEmployeeById(String id) throws DataNotFoundException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveEmployeeById(), method STARTED and the given input is : id - {}", id);
		}

		EmployeeDto outputEmployee = DtoUtility
				.convertEmployeeToEmployeeDto(retrieveEmployeeByGivenColumn(ColumnConstants.ID, id, true));

		if (outputEmployee == null) {

			String errorMsg = String.format("No employee is present for the given identifier %s.", id);

			LOG.error(String.format("In retrieveEmployeeById(), %s", errorMsg));
			throw new DataNotFoundException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveEmployeeById(), method ENDED and the result is : outputEmployee - {}.",
					outputEmployee);
		}

		return outputEmployee;
	}

	@Transactional
	public List<EmployeeDto> retrieveEmployeesByJobTitle(String jobTitle) throws DataNotFoundException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveEmployeesByJobTitle(), method STARTED and the given input is : jobTitle - {}",
					jobTitle);
		}

		List<EmployeeDto> outputEmployeesContainingGivenJobTitle = null;

		if (StringUtils.isNotBlank(jobTitle)) {

			RoleDto roleHavingGivenJobTitle = roleService.retrieveRoleByTitle(jobTitle);

			if (roleHavingGivenJobTitle != null) {

				List<Employee> outputEmployees = employeeDao.retrieveEmployeeByGivenColumn(ColumnConstants.ROLE_ID,
						roleHavingGivenJobTitle.getId(), true);

				if (!CollectionUtils.isEmpty(outputEmployees)) {

					outputEmployeesContainingGivenJobTitle = new ArrayList<EmployeeDto>();

					for (Employee outputEmployee : outputEmployees) {

						outputEmployeesContainingGivenJobTitle
								.add(DtoUtility.convertEmployeeToEmployeeDto(outputEmployee));
					}
				}
			}
		} else {

			String errorMsg = "No job title is given as input.";

			LOG.error(String.format("In retrieveEmployeesByJobTitle(), %s", errorMsg));
			throw new DataNotFoundException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug(
					"In retrieveEmployeesByJobTitle(), method ENDED and the result is : outputEmployeesContainingGivenJobTitle - {}.",
					!CollectionUtils.isEmpty(outputEmployeesContainingGivenJobTitle)
							? outputEmployeesContainingGivenJobTitle.size()
							: 0);
		}

		return outputEmployeesContainingGivenJobTitle;
	}

	private Employee retrieveEmployeeByGivenColumn(String column, String value, boolean isExactSearch) {

		List<Employee> outputEmployees = employeeDao.retrieveEmployeeByGivenColumn(column, value, isExactSearch);

		return !CollectionUtils.isEmpty(outputEmployees) ? outputEmployees.get(0) : null;
	}

	private void validateEmployee(EmployeeDto inputEmployee, Role employeeRole, ActionEntity action)
			throws DuplicateEntityException, DataNotFoundException {

		String errorMsg = "";

		Set<ConstraintViolation<EmployeeDto>> constraintViolations = validator.validate(inputEmployee);

		if (constraintViolations.size() > 0) {

			for (ConstraintViolation<EmployeeDto> violation : constraintViolations) {
				errorMsg = errorMsg.concat(violation.getMessage());
			}

		} else if (employeeRole == null) {

			errorMsg = "Invalid identifier is missing. Please try again with a valid value.";

		} else if (ActionEntity.UPDATE == action && StringUtils.isBlank(inputEmployee.getId())) {

			errorMsg = "Employee identifier is missing. Please try again with a valid value.";

		} else if (ActionEntity.CREATE == action && StringUtils.isNotBlank(inputEmployee.getId())) {

			errorMsg = "Employee identifier must not be present. Please try again with a valid value.";
		}

		if (StringUtils.isNotBlank(errorMsg)) {
			throw new DuplicateEntityException(errorMsg);
		}
	}

}
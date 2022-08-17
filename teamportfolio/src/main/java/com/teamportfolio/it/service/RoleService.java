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

import com.teamportfolio.it.dao.entity.Role;
import com.teamportfolio.it.dao.role.RoleDao;
import com.teamportfolio.it.dto.RoleDto;
import com.teamportfolio.it.exception.DataNotFoundException;
import com.teamportfolio.it.exception.DuplicateEntityException;
import com.teamportfolio.it.exception.InvalidInputException;
import com.teamportfolio.it.util.ActionEntity;
import com.teamportfolio.it.util.ColumnConstants;
import com.teamportfolio.it.util.DtoUtility;

@Service
public class RoleService {

	private static final Logger LOG = LoggerFactory.getLogger(RoleService.class.getCanonicalName());

	private final Validator validator;

	private final RoleDao roleDao;

	@Autowired
	public RoleService(RoleDao roleDao, Validator validator) {

		this.roleDao = roleDao;
		this.validator = validator;
	}

	@Transactional
	public void addRole(RoleDto inputRole) throws DuplicateEntityException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addRole(), method STARTED and the given input is : inputRole - {}.", inputRole);
		}

		// Validating given role.
		validateRole(inputRole, ActionEntity.CREATE);

		// Validating whether given id is already present.
		if (retrieveRoleByGivenColumn(ColumnConstants.TITLE, inputRole.getTitle(), true) == null) {

			roleDao.addRole(DtoUtility.convertRoleDtoToRole(inputRole));

		} else {

			LOG.error("Given role {} is already present. Please try again with a valid input.", inputRole);

			throw new DuplicateEntityException("Given role is already present. Please try again with a valid input.");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addRole(), method ENDED.");
		}
	}

	@Transactional
	public void updateRole(RoleDto inputRole, String id) throws DuplicateEntityException, InvalidInputException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateRole(), method STARTED and the given input is : inputRole - {}, id - {}.", inputRole,
					id);
		}

		if (inputRole != null) {

			if (StringUtils.isBlank(inputRole.getId())) {

				inputRole.setId(id);

			} else if (!inputRole.getId().equals(id)) {

				throw new InvalidInputException("Role identifier present in the URL is different from the request.");
			}
		}

		// Validating given role.
		validateRole(inputRole, ActionEntity.UPDATE);

		// Validating whether given id is present and title given for update does not
		// exist.
		Role existingRole = roleDao.retrieveRoleByGivenColumn(ColumnConstants.ID, inputRole.getId(), true);

		if (existingRole != null && !inputRole.getTitle().equalsIgnoreCase(existingRole.getTitle())
				|| !inputRole.getDescription().equalsIgnoreCase(existingRole.getDescription())) {

			if (StringUtils.isNotBlank(inputRole.getTitle())) {
				existingRole.setTitle(inputRole.getTitle());
			}

			if (StringUtils.isNotBlank(inputRole.getDescription())) {
				existingRole.setDescription(inputRole.getDescription());
			}

			roleDao.updateRole(existingRole);

		} else {

			String errorMsg = String.format("Given role %s is already present. Please try again with a valid input.",
					inputRole);

			LOG.error(String.format("In updateRole(), %s", errorMsg));
			throw new DuplicateEntityException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateRole(), method ENDED.");
		}
	}

	@Transactional
	public void deleteRole(String id) throws DuplicateEntityException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteRole(), method STARTED and the given input is : id - {}.", id);
		}

		// Validating whether given id is present.
		if (retrieveRoleByGivenColumn(ColumnConstants.ID, id, true) != null) {

			roleDao.deleteRole(id);

		} else {

			String errorMsg = String
					.format("Given role identifier %s does not exist. Please try again with a valid input.", id);

			LOG.error(errorMsg);
			throw new DuplicateEntityException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteRole(), method ENDED.");
		}
	}

	@Transactional
	public List<RoleDto> retrieveAllRoles() throws DataNotFoundException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveAllRoles(), method STARTED.");
		}

		List<RoleDto> outputRoleDtos = null;

		List<Role> outputRoles = roleDao.retrieveAllRoles();

		if (!CollectionUtils.isEmpty(outputRoles)) {

			outputRoleDtos = new ArrayList<RoleDto>();

			for (Role role : outputRoles) {

				outputRoleDtos.add(DtoUtility.convertRoleToRoleDto(role));
			}
		} else {

			String errorMsg = "No role is present in the application.";

			LOG.error(String.format("In retrieveRoleById(), %s", errorMsg));
			throw new DataNotFoundException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {

			LOG.debug("In retrieveAllRoles(), method ENDED and the output roles size is {}.",
					!CollectionUtils.isEmpty(outputRoles) ? outputRoles.size() : 0);
		}

		return outputRoleDtos;
	}

	@Transactional
	public RoleDto retrieveRoleById(String id) throws DataNotFoundException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveRoleById(), method STARTED and the given input is : id - {}", id);
		}

		RoleDto outputRole = DtoUtility.convertRoleToRoleDto(retrieveRoleByGivenColumn(ColumnConstants.ID, id, true));

		if (outputRole == null) {

			String errorMsg = String.format("No role is present for the given identifier %s.", id);

			LOG.error(String.format("In retrieveRoleById(), %s", errorMsg));
			throw new DataNotFoundException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveRoleById(), method ENDED and the result is : outputRole - {}.", outputRole);
		}

		return outputRole;
	}

	@Transactional
	public RoleDto retrieveRoleByTitle(String title) throws DataNotFoundException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveRoleByTitle(), method STARTED and the given input is : title - {}", title);
		}

		RoleDto outputRole = DtoUtility
				.convertRoleToRoleDto(retrieveRoleByGivenColumn(ColumnConstants.TITLE, title, false));

		if (outputRole == null) {

			String errorMsg = String.format("No role is present for the given title %s.", title);

			LOG.error(String.format("In retrieveRoleByTitle(), %s", errorMsg));
			throw new DataNotFoundException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveRoleByTitle(), method ENDED and the result is : outputRole - {}.", outputRole);
		}

		return outputRole;
	}

	private Role retrieveRoleByGivenColumn(String column, String value, boolean isExactSearch) {

		return roleDao.retrieveRoleByGivenColumn(column, value, isExactSearch);
	}

	private void validateRole(RoleDto inputRole, ActionEntity action) throws DuplicateEntityException {

		String errorMsg = "";

		Set<ConstraintViolation<RoleDto>> constraintViolations = validator.validate(inputRole);

		if (constraintViolations.size() > 0) {

			for (ConstraintViolation<RoleDto> violation : constraintViolations) {
				errorMsg = errorMsg.concat(violation.getMessage());
			}

		} else if (ActionEntity.UPDATE == action && StringUtils.isBlank(inputRole.getId())) {

			errorMsg = "Role identifier is missing. Please try again with a valid value.";

		} else if (ActionEntity.CREATE == action && StringUtils.isNotBlank(inputRole.getId())) {

			errorMsg = "Role identifier must not be present. Please try again with a valid value.";
		}

		if (StringUtils.isNotBlank(errorMsg)) {
			throw new DuplicateEntityException(errorMsg);
		}
	}

}
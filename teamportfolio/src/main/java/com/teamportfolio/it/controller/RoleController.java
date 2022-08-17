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

import com.teamportfolio.it.dto.RoleDto;
import com.teamportfolio.it.exception.DataNotFoundException;
import com.teamportfolio.it.exception.DuplicateEntityException;
import com.teamportfolio.it.exception.InvalidInputException;
import com.teamportfolio.it.service.RoleService;
import com.teamportfolio.it.util.Constants;

@RestController
public class RoleController {

	private static final Logger LOG = LoggerFactory.getLogger(RoleController.class.getCanonicalName());

	private static final String ROLES = "roles";

	@Autowired
	private RoleService roleService;

	@GetMapping(value = Constants.VERSION + ROLES)
	public List<RoleDto> retrieveAllRoles() throws DataNotFoundException {

		return roleService.retrieveAllRoles();
	}

	@PostMapping(value = Constants.VERSION + ROLES)
	public void addRole(@Valid @RequestBody RoleDto role) throws DuplicateEntityException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given role for addition is {}.", role);
		}

		roleService.addRole(role);
	}

	@PutMapping(value = Constants.VERSION + ROLES + "/{id}")
	public void updateRole(@Valid @RequestBody RoleDto role, @PathVariable(required = true) String id)
			throws DuplicateEntityException, InvalidInputException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given role for updation is {}.", role);
		}

		roleService.updateRole(role, id);
	}

	@DeleteMapping(value = Constants.VERSION + ROLES + "/{id}")
	public void deleteRole(@PathVariable(required = true) String id) throws DuplicateEntityException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given role identifier for deletion is {}.", id);
		}

		roleService.deleteRole(id);
	}

	@GetMapping(Constants.VERSION + ROLES + "/{id}")
	public RoleDto retrieveRoleById(@PathVariable(required = true) String id) throws DataNotFoundException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given identifier for reading the role is : id - {}", id);
		}

		return roleService.retrieveRoleById(id);
	}

	@GetMapping(Constants.VERSION + ROLES + "/searchByTitle")
	public RoleDto retrieveRoleByTitle(@RequestParam(required = true) String title) throws DataNotFoundException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given title for reading the role is : title - {}", title);
		}

		return roleService.retrieveRoleByTitle(title);
	}

}
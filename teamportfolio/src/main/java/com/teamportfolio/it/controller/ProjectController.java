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

import com.teamportfolio.it.dto.ProjectDto;
import com.teamportfolio.it.exception.DataNotFoundException;
import com.teamportfolio.it.exception.DuplicateEntityException;
import com.teamportfolio.it.exception.InvalidInputException;
import com.teamportfolio.it.service.ProjectService;
import com.teamportfolio.it.util.Constants;

@RestController
public class ProjectController {

	private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class.getCanonicalName());

	private static final String PROJECT = "projects";

	@Autowired
	private ProjectService projectService;

	@GetMapping(value = Constants.VERSION + PROJECT)
	public List<ProjectDto> retrieveAllProjects() throws DataNotFoundException {

		return projectService.retrieveAllProjects();
	}

	@PostMapping(value = Constants.VERSION + PROJECT)
	public void addProject(@Valid @RequestBody ProjectDto project) throws DuplicateEntityException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given project for addition is {}.", project);
		}

		projectService.addProject(project);
	}

	@PutMapping(value = Constants.VERSION + PROJECT + "/{id}")
	public void updateProject(@Valid @RequestBody ProjectDto project, @PathVariable(required = true) String id)
			throws DuplicateEntityException, InvalidInputException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given project for updation is {}.", project);
		}

		projectService.updateProject(project, id);
	}

	@DeleteMapping(value = Constants.VERSION + PROJECT + "/{id}")
	public void deleteProject(@PathVariable(required = true) String id) throws DuplicateEntityException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given project identifier for deletion is {}.", id);
		}

		projectService.deleteProject(id);
	}

	@GetMapping(Constants.VERSION + PROJECT + "/{id}")
	public ProjectDto retrieveProjectById(@PathVariable(required = true) String id) throws DataNotFoundException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given identifier for reading the project is : id - {}", id);
		}

		return projectService.retrieveProjectById(id);
	}

	@GetMapping(Constants.VERSION + PROJECT + "/searchByTitle")
	public ProjectDto retrieveProjectByTitle(@RequestParam(required = true) String title) throws DataNotFoundException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given title for reading the project is : title - {}", title);
		}

		return projectService.retrieveProjectByTitle(title);
	}

}
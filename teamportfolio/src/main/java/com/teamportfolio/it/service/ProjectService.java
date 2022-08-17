package com.teamportfolio.it.service;

import java.time.LocalDate;
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

import com.teamportfolio.it.dao.entity.Project;
import com.teamportfolio.it.dao.project.ProjectDao;
import com.teamportfolio.it.dto.ProjectDto;
import com.teamportfolio.it.exception.DataNotFoundException;
import com.teamportfolio.it.exception.DuplicateEntityException;
import com.teamportfolio.it.exception.InvalidInputException;
import com.teamportfolio.it.util.ActionEntity;
import com.teamportfolio.it.util.ColumnConstants;
import com.teamportfolio.it.util.Constants;
import com.teamportfolio.it.util.DtoUtility;

@Service
public class ProjectService {

	private static final Logger LOG = LoggerFactory.getLogger(ProjectService.class.getCanonicalName());

	private final Validator validator;

	private final ProjectDao projectDao;

	@Autowired
	public ProjectService(ProjectDao projectDao, Validator validator) {

		this.projectDao = projectDao;
		this.validator = validator;
	}

	@Transactional
	public void addProject(ProjectDto inputProject) throws DuplicateEntityException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addProject(), method STARTED and the given input is : inputProject - {}.", inputProject);
		}

		// Validating given project.
		validateProject(inputProject, ActionEntity.CREATE);

		// Validating whether given id is already present.
		if (retrieveProjectByGivenColumn(ColumnConstants.TITLE, inputProject.getTitle(), true) == null) {

			projectDao.addProject(DtoUtility.convertProjectDtoToProject(inputProject));

		} else {

			LOG.error("Given project {} is already present. Please try again with a valid input.", inputProject);

			throw new DuplicateEntityException(
					"Given project is already present. Please try again with a valid input.");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addProject(), method ENDED.");
		}
	}

	@Transactional
	public void updateProject(ProjectDto inputProject, String id)
			throws DuplicateEntityException, InvalidInputException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateProject(), method STARTED and the given input is : inputProject - {}, id - {}.",
					inputProject, id);
		}

		if (inputProject != null) {

			if (StringUtils.isBlank(inputProject.getId())) {

				inputProject.setId(id);

			} else if (!inputProject.getId().equals(id)) {

				throw new InvalidInputException("Project identifier present in the URL is different from the request.");
			}
		}

		// Validating given project.
		validateProject(inputProject, ActionEntity.UPDATE);

		// Validating whether given id is present and title given for update does not
		// exist.
		Project existingProject = projectDao.retrieveProjectByGivenColumn(ColumnConstants.ID, inputProject.getId(),
				true);

		if (existingProject != null && !inputProject.getTitle().equalsIgnoreCase(existingProject.getTitle())
				|| !inputProject.getDescription().equalsIgnoreCase(existingProject.getDescription())) {

			if (StringUtils.isNotBlank(inputProject.getTitle())) {
				existingProject.setTitle(inputProject.getTitle());
			}

			if (StringUtils.isNotBlank(inputProject.getDescription())) {
				existingProject.setDescription(inputProject.getDescription());
			}

			projectDao.updateProject(existingProject);

		} else {

			String errorMsg = String.format("Given project %s is already present. Please try again with a valid input.",
					inputProject);

			LOG.error(String.format("In updateProject(), %s", errorMsg));
			throw new DuplicateEntityException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateProject(), method ENDED.");
		}
	}

	@Transactional
	public void deleteProject(String id) throws DuplicateEntityException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteProject(), method STARTED and the given input is : id - {}.", id);
		}

		// Validating whether given id is present.
		if (retrieveProjectByGivenColumn(ColumnConstants.ID, id, true) != null) {

			projectDao.deleteProject(id);

		} else {

			String errorMsg = String
					.format("Given project identifier %s does not exist. Please try again with a valid input.", id);

			LOG.error(errorMsg);
			throw new DuplicateEntityException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteProject(), method ENDED.");
		}
	}

	@Transactional
	public List<ProjectDto> retrieveAllProjects() throws DataNotFoundException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveAllProjects(), method STARTED.");
		}

		List<ProjectDto> outputProjectDtos = null;

		List<Project> outputProjects = projectDao.retrieveAllProjects();

		if (!CollectionUtils.isEmpty(outputProjects)) {

			outputProjectDtos = new ArrayList<ProjectDto>();

			for (Project project : outputProjects) {

				outputProjectDtos.add(DtoUtility.convertProjectToProjectDto(project));
			}
		} else {

			String errorMsg = "No project is present in the application.";

			LOG.error(String.format("In retrieveProjectById(), %s", errorMsg));
			throw new DataNotFoundException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {

			LOG.debug("In retrieveAllProjects(), method ENDED and the output projects size is {}.",
					!CollectionUtils.isEmpty(outputProjects) ? outputProjects.size() : 0);
		}

		return outputProjectDtos;
	}

	@Transactional
	public ProjectDto retrieveProjectById(String id) throws DataNotFoundException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveProjectById(), method STARTED and the given input is : id - {}", id);
		}

		ProjectDto outputProject = DtoUtility
				.convertProjectToProjectDto(retrieveProjectByGivenColumn(ColumnConstants.ID, id, true));

		if (outputProject == null) {

			String errorMsg = String.format("No project is present for the given identifier %s.", id);

			LOG.error(String.format("In retrieveProjectById(), %s", errorMsg));
			throw new DataNotFoundException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveProjectById(), method ENDED and the result is : outputProject - {}.", outputProject);
		}

		return outputProject;
	}

	@Transactional
	public ProjectDto retrieveProjectByTitle(String title) throws DataNotFoundException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveProjectByTitle(), method STARTED and the given input is : title - {}", title);
		}

		ProjectDto outputProject = DtoUtility
				.convertProjectToProjectDto(retrieveProjectByGivenColumn(ColumnConstants.TITLE, title, false));

		if (outputProject == null) {

			String errorMsg = String.format("No project is present for the given title %s.", title);

			LOG.error(String.format("In retrieveProjectByTitle(), %s", errorMsg));
			throw new DataNotFoundException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveProjectByTitle(), method ENDED and the result is : outputProject - {}.",
					outputProject);
		}

		return outputProject;
	}

	private Project retrieveProjectByGivenColumn(String column, String value, boolean isExactSearch) {

		return projectDao.retrieveProjectByGivenColumn(column, value, isExactSearch);
	}

	private void validateProject(ProjectDto inputProject, ActionEntity action) throws DuplicateEntityException {

		String errorMsg = "";

		Set<ConstraintViolation<ProjectDto>> constraintViolations = validator.validate(inputProject);

		if (constraintViolations.size() > 0) {

			for (ConstraintViolation<ProjectDto> violation : constraintViolations) {
				errorMsg = errorMsg.concat(violation.getMessage());
			}

		} else if (ActionEntity.UPDATE == action && StringUtils.isBlank(inputProject.getId())) {

			errorMsg = "Project identifier is missing. Please try again with a valid value.";

		} else if (ActionEntity.CREATE == action && StringUtils.isNotBlank(inputProject.getId())) {

			errorMsg = "Project identifier must not be present. Please try again with a valid value.";

		} else if (inputProject.getStartDate().isBefore(LocalDate.now(Constants.UTC_ZONE_ID))
				|| inputProject.getStartDate().isAfter(inputProject.getEndDate())) {

			errorMsg = "Project's start or end date is invalid. Please try again with a valid value.";
		}

		if (StringUtils.isNotBlank(errorMsg)) {
			throw new DuplicateEntityException(errorMsg);
		}
	}

}
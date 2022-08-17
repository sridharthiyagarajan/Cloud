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

import com.teamportfolio.it.dao.entity.Skill;
import com.teamportfolio.it.dao.skill.SkillDao;
import com.teamportfolio.it.dto.SkillDto;
import com.teamportfolio.it.exception.DataNotFoundException;
import com.teamportfolio.it.exception.DuplicateEntityException;
import com.teamportfolio.it.exception.InvalidInputException;
import com.teamportfolio.it.util.ActionEntity;
import com.teamportfolio.it.util.ColumnConstants;
import com.teamportfolio.it.util.DtoUtility;

@Service
public class SkillService {

	private static final Logger LOG = LoggerFactory.getLogger(SkillService.class.getCanonicalName());

	private final Validator validator;

	private final SkillDao skillDao;

	@Autowired
	public SkillService(SkillDao skillDao, Validator validator) {

		this.skillDao = skillDao;
		this.validator = validator;
	}

	@Transactional
	public void addSkill(SkillDto inputSkill) throws DuplicateEntityException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addSkill(), method STARTED and the given input is : inputSkill - {}.", inputSkill);
		}

		// Validating given skill.
		validateSkill(inputSkill, ActionEntity.CREATE);

		// Validating whether given id is already present.
		if (retrieveSkillByGivenColumn(ColumnConstants.NAME, inputSkill.getName(), true) == null) {

			skillDao.addSkill(DtoUtility.convertSkillDtoToSkill(inputSkill));

		} else {

			LOG.error("Given skill {} is already present. Please try again with a valid input.", inputSkill);

			throw new DuplicateEntityException("Given skill is already present. Please try again with a valid input.");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addSkill(), method ENDED.");
		}
	}

	@Transactional
	public void updateSkill(SkillDto inputSkill, String id) throws DuplicateEntityException, InvalidInputException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateSkill(), method STARTED and the given input is : inputSkill - {}, id - {}.", inputSkill,
					id);
		}

		if (inputSkill != null) {

			if (StringUtils.isBlank(inputSkill.getId())) {

				inputSkill.setId(id);

			} else if (!inputSkill.getId().equals(id)) {

				throw new InvalidInputException("Skill identifier present in the URL is different from the request.");
			}
		}

		// Validating given skill.
		validateSkill(inputSkill, ActionEntity.UPDATE);

		// Validating whether given id is present and name given for update does not
		// exist.
		Skill existingSkill = skillDao.retrieveSkillByGivenColumn(ColumnConstants.ID, inputSkill.getId(), true);

		if (existingSkill != null && !inputSkill.getName().equalsIgnoreCase(existingSkill.getName())
				|| !inputSkill.getDescription().equalsIgnoreCase(existingSkill.getDescription())) {

			if (StringUtils.isNotBlank(inputSkill.getName())) {
				existingSkill.setName(inputSkill.getName());
			}

			if (StringUtils.isNotBlank(inputSkill.getDescription())) {
				existingSkill.setDescription(inputSkill.getDescription());
			}

			skillDao.updateSkill(existingSkill);

		} else {

			String errorMsg = String.format("Given skill %s is already present. Please try again with a valid input.",
					inputSkill);

			LOG.error(String.format("In updateSkill(), %s", errorMsg));
			throw new DuplicateEntityException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateSkill(), method ENDED.");
		}
	}

	@Transactional
	public void deleteSkill(String id) throws DuplicateEntityException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteSkill(), method STARTED and the given input is : id - {}.", id);
		}

		// Validating whether given id is present.
		if (retrieveSkillByGivenColumn(ColumnConstants.ID, id, true) != null) {

			skillDao.deleteSkill(id);

		} else {

			String errorMsg = String
					.format("Given skill identifier %s does not exist. Please try again with a valid input.", id);

			LOG.error(errorMsg);
			throw new DuplicateEntityException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteSkill(), method ENDED.");
		}
	}

	@Transactional
	public List<SkillDto> retrieveAllSkills() throws DataNotFoundException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveAllSkills(), method STARTED.");
		}

		List<SkillDto> outputSkillDtos = null;

		List<Skill> outputSkills = skillDao.retrieveAllSkills();

		if (!CollectionUtils.isEmpty(outputSkills)) {

			outputSkillDtos = new ArrayList<SkillDto>();

			for (Skill skill : outputSkills) {

				outputSkillDtos.add(DtoUtility.convertSkillToSkillDto(skill));
			}
		} else {

			String errorMsg = "No skill is present in the application.";

			LOG.error(String.format("In retrieveSkillById(), %s", errorMsg));
			throw new DataNotFoundException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {

			LOG.debug("In retrieveAllSkills(), method ENDED and the output skills size is {}.",
					!CollectionUtils.isEmpty(outputSkills) ? outputSkills.size() : 0);
		}

		return outputSkillDtos;
	}

	@Transactional
	public SkillDto retrieveSkillById(String id) throws DataNotFoundException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveSkillById(), method STARTED and the given input is : id - {}", id);
		}

		SkillDto outputSkill = DtoUtility
				.convertSkillToSkillDto(retrieveSkillByGivenColumn(ColumnConstants.ID, id, true));

		if (outputSkill == null) {

			String errorMsg = String.format("No skill is present for the given identifier %s.", id);

			LOG.error(String.format("In retrieveSkillById(), %s", errorMsg));
			throw new DataNotFoundException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveSkillById(), method ENDED and the result is : outputSkill - {}.", outputSkill);
		}

		return outputSkill;
	}

	@Transactional
	public SkillDto retrieveSkillByName(String name) throws DataNotFoundException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveSkillByName(), method STARTED and the given input is : name - {}", name);
		}

		SkillDto outputSkill = DtoUtility
				.convertSkillToSkillDto(retrieveSkillByGivenColumn(ColumnConstants.NAME, name, false));

		if (outputSkill == null) {

			String errorMsg = String.format("No skill is present for the given name %s.", name);

			LOG.error(String.format("In retrieveSkillByName(), %s", errorMsg));
			throw new DataNotFoundException(errorMsg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveSkillByName(), method ENDED and the result is : outputSkill - {}.", outputSkill);
		}

		return outputSkill;
	}

	private Skill retrieveSkillByGivenColumn(String column, String value, boolean isExactSearch) {

		return skillDao.retrieveSkillByGivenColumn(column, value, isExactSearch);
	}

	private void validateSkill(SkillDto inputSkill, ActionEntity action) throws DuplicateEntityException {

		String errorMsg = "";

		Set<ConstraintViolation<SkillDto>> constraintViolations = validator.validate(inputSkill);

		if (constraintViolations.size() > 0) {

			for (ConstraintViolation<SkillDto> violation : constraintViolations) {
				errorMsg = errorMsg.concat(violation.getMessage());
			}

		} else if (ActionEntity.UPDATE == action && StringUtils.isBlank(inputSkill.getId())) {

			errorMsg = "Skill identifier is missing. Please try again with a valid value.";

		} else if (ActionEntity.CREATE == action && StringUtils.isNotBlank(inputSkill.getId())) {

			errorMsg = "Skill identifier must not be present. Please try again with a valid value.";
		}

		if (StringUtils.isNotBlank(errorMsg)) {
			throw new DuplicateEntityException(errorMsg);
		}
	}

}
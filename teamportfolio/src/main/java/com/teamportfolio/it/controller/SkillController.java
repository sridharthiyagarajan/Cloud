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

import com.teamportfolio.it.dto.SkillDto;
import com.teamportfolio.it.exception.DataNotFoundException;
import com.teamportfolio.it.exception.DuplicateEntityException;
import com.teamportfolio.it.exception.InvalidInputException;
import com.teamportfolio.it.service.SkillService;
import com.teamportfolio.it.util.Constants;

@RestController
public class SkillController {

	private static final Logger LOG = LoggerFactory.getLogger(SkillController.class.getCanonicalName());

	private static final String SKILLS = "skills";

	@Autowired
	private SkillService skillService;

	@GetMapping(value = Constants.VERSION + SKILLS)
	public List<SkillDto> retrieveAllSkills() throws DataNotFoundException {

		return skillService.retrieveAllSkills();
	}

	@PostMapping(value = Constants.VERSION + SKILLS)
	public void addSkill(@Valid @RequestBody SkillDto skill) throws DuplicateEntityException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given skill for addition is {}.", skill);
		}

		skillService.addSkill(skill);
	}

	@PutMapping(value = Constants.VERSION + SKILLS + "/{id}")
	public void updateSkill(@Valid @RequestBody SkillDto skill, @PathVariable(required = true) String id)
			throws DuplicateEntityException, InvalidInputException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given skill for updation is {}.", skill);
		}

		skillService.updateSkill(skill, id);
	}

	@DeleteMapping(value = Constants.VERSION + SKILLS + "/{id}")
	public void deleteSkill(@PathVariable(required = true) String id) throws DuplicateEntityException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given skill identifier for deletion is {}.", id);
		}

		skillService.deleteSkill(id);
	}

	@GetMapping(Constants.VERSION + SKILLS + "/{id}")
	public SkillDto retrieveSkillById(@PathVariable(required = true) String id) throws DataNotFoundException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given identifier for reading the skill is : id - {}", id);
		}

		return skillService.retrieveSkillById(id);
	}

	@GetMapping(Constants.VERSION + SKILLS + "/searchByName")
	public SkillDto retrieveSkillByName(@RequestParam(required = true) String name) throws DataNotFoundException {

		if (LOG.isInfoEnabled()) {
			LOG.info("Given name for reading the skill is : name - {}", name);
		}

		return skillService.retrieveSkillByName(name);
	}

}
package com.teamportfolio.it.dao.skill;

import java.util.List;

import com.teamportfolio.it.dao.entity.Skill;

public interface SkillDao {

	void addSkill(Skill skill);

	void updateSkill(Skill skill);

	void deleteSkill(String id);

	Skill retrieveSkillByGivenColumn(String column, String value, boolean isExactSearch);

	List<Skill> retrieveAllSkills();
}
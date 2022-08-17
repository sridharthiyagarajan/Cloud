package com.teamportfolio.it.dao.project;

import java.util.List;

import com.teamportfolio.it.dao.entity.Project;

public interface ProjectDao {

	void addProject(Project project);

	void updateProject(Project project);

	void deleteProject(String id);

	Project retrieveProjectByGivenColumn(String column, String value, boolean isExactSearch);

	List<Project> retrieveAllProjects();
}
package com.teamportfolio.it.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.teamportfolio.it.dao.entity.Employee;
import com.teamportfolio.it.dao.entity.Project;
import com.teamportfolio.it.dao.entity.Role;
import com.teamportfolio.it.dao.entity.Skill;
import com.teamportfolio.it.dto.EmployeeDto;
import com.teamportfolio.it.dto.ProjectDto;
import com.teamportfolio.it.dto.RoleDto;
import com.teamportfolio.it.dto.SkillDto;

public class DtoUtility {

	public static Role convertRoleDtoToRole(RoleDto roleDto) {

		Role role = null;

		if (roleDto != null) {

			role = new Role();

			if (StringUtils.isNotBlank(roleDto.getId())) {

				role.setId(UUID.fromString(roleDto.getId()));
			}

			role.setTitle(roleDto.getTitle());
			role.setDescription(roleDto.getDescription());
			role.setCreatedDateTime(roleDto.getCreatedDateTime());
			role.setModifiedDateTime(roleDto.getModifiedDateTime());
		}

		return role;
	}

	public static RoleDto convertRoleToRoleDto(Role role) {

		RoleDto roleDto = null;

		if (role != null && role.getId() != null && StringUtils.isNotBlank(role.getId().toString())) {

			roleDto = new RoleDto();

			roleDto.setId(role.getId().toString());
			roleDto.setTitle(role.getTitle());
			roleDto.setDescription(role.getDescription());
			roleDto.setCreatedDateTime(role.getCreatedDateTime());
			roleDto.setModifiedDateTime(role.getModifiedDateTime());
		}

		return roleDto;
	}

	public static Employee convertEmployeeDtoToEmployee(EmployeeDto employeeDto, Role employeeRole) {

		Employee employee = null;

		if (employeeDto != null && employeeRole != null) {

			employee = new Employee();

			employee.setEmail(employeeDto.getEmail());
			employee.setEmployeeReferenceNumber(employeeDto.getEmployeeReferenceNumber());
			employee.setFirstName(employeeDto.getFirstName());
			employee.setLastName(employeeDto.getLastName());
			employee.setPhone(employeeDto.getPhone());
			employee.setRoleId(employeeRole.getId().toString());
		}

		return employee;
	}

	public static EmployeeDto convertEmployeeToEmployeeDto(Employee employee) {

		EmployeeDto employeeDto = null;

		if (employee != null) {

			employeeDto = new EmployeeDto();

			employeeDto.setId(employee.getId().toString());
			employeeDto.setCreatedDateTime(employee.getCreatedDateTime());
			employeeDto.setModifiedDateTime(employee.getModifiedDateTime());
			employeeDto.setEmail(employee.getEmail());
			employeeDto.setEmployeeReferenceNumber(employee.getEmployeeReferenceNumber());
			employeeDto.setFirstName(employee.getFirstName());
			employeeDto.setLastName(employee.getLastName());
			employeeDto.setPhone(employee.getPhone());
			employeeDto.setRoleId(employee.getRoleId());
		}

		return employeeDto;
	}

	public static Project convertProjectDtoToProject(ProjectDto projectDto) {

		Project project = null;

		if (projectDto != null) {

			project = new Project();

			if (StringUtils.isNotBlank(projectDto.getId())) {

				project.setId(UUID.fromString(projectDto.getId()));
			}

			project.setTitle(projectDto.getTitle());
			project.setDescription(projectDto.getDescription());
			project.setStartDate(projectDto.getStartDate());
			project.setEndDate(projectDto.getEndDate());
			project.setCreatedDateTime(projectDto.getCreatedDateTime());
			project.setModifiedDateTime(projectDto.getModifiedDateTime());
		}

		return project;
	}

	public static ProjectDto convertProjectToProjectDto(Project project) {

		ProjectDto projectDto = null;

		if (project != null && project.getId() != null && StringUtils.isNotBlank(project.getId().toString())) {

			projectDto = new ProjectDto();

			projectDto.setId(project.getId().toString());
			projectDto.setTitle(project.getTitle());
			projectDto.setDescription(project.getDescription());
			projectDto.setStartDate(project.getStartDate());
			projectDto.setEndDate(project.getEndDate());
			projectDto.setCreatedDateTime(project.getCreatedDateTime());
			projectDto.setModifiedDateTime(project.getModifiedDateTime());
		}

		return projectDto;
	}

	public static Skill convertSkillDtoToSkill(SkillDto skillDto) {

		Skill skill = null;

		if (skillDto != null) {

			skill = new Skill();

			if (StringUtils.isNotBlank(skillDto.getId())) {

				skill.setId(UUID.fromString(skillDto.getId()));
			}

			skill.setName(skillDto.getName());
			skill.setDescription(skillDto.getDescription());
			skill.setCreatedDateTime(skillDto.getCreatedDateTime());
			skill.setModifiedDateTime(skillDto.getModifiedDateTime());
		}

		return skill;
	}

	public static SkillDto convertSkillToSkillDto(Skill skill) {

		SkillDto skillDto = null;

		if (skill != null && skill.getId() != null && StringUtils.isNotBlank(skill.getId().toString())) {

			skillDto = new SkillDto();

			skillDto.setId(skill.getId().toString());
			skillDto.setName(skill.getName());
			skillDto.setDescription(skill.getDescription());
			skillDto.setCreatedDateTime(skill.getCreatedDateTime());
			skillDto.setModifiedDateTime(skill.getModifiedDateTime());
		}

		return skillDto;
	}
}
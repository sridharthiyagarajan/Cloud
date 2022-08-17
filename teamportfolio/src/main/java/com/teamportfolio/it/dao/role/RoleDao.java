package com.teamportfolio.it.dao.role;

import java.util.List;

import com.teamportfolio.it.dao.entity.Role;

public interface RoleDao {

	void addRole(Role role);

	void updateRole(Role role);

	void deleteRole(String id);

	Role retrieveRoleByGivenColumn(String column, String value, boolean isExactSearch);

	List<Role> retrieveAllRoles();
}
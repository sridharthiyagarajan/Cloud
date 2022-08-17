package com.teamportfolio.it.dao.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.teamportfolio.it.util.ColumnConstants;
import com.teamportfolio.it.util.TableConstants;

@Entity
@Table(name = TableConstants.EMPLOYEE)
public class Employee extends BaseEntity {

	@Id
	@Column(name = ColumnConstants.ID)
	@GeneratedValue
	private UUID id;

	@Column(name = ColumnConstants.EMPLOYEE_REFERENCE_NUMBER)
	private String employeeReferenceNumber;

	@Column(name = ColumnConstants.FIRST_NAME)
	private String firstName;

	@Column(name = ColumnConstants.LAST_NAME)
	private String lastName;

	@Column(name = ColumnConstants.EMAIL)
	private String email;

	@Column(name = ColumnConstants.PHONE)
	private String phone;

	@Column(name = ColumnConstants.ROLE_ID)
	private String roleId;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmployeeReferenceNumber() {
		return employeeReferenceNumber;
	}

	public void setEmployeeReferenceNumber(String employeeReferenceNumber) {
		this.employeeReferenceNumber = employeeReferenceNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {

		return getEmployeeReferenceNumber();
	}

}
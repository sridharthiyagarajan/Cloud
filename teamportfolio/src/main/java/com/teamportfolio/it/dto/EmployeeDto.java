package com.teamportfolio.it.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class EmployeeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String id;

	@JsonProperty
	@NotBlank(message = "{employee.employeeReferenceNumber.empty}")
	private String employeeReferenceNumber;

	@JsonProperty
	@NotBlank(message = "{employee.firstName.empty}")
	private String firstName;

	@JsonProperty
	@NotBlank(message = "{employee.lastName.empty}")
	private String lastName;

	@JsonProperty
	@Email
	@NotBlank(message = "{employee.email.empty}")
	private String email;

	@JsonProperty
	@NotBlank(message = "{employee.phone.empty}")
	private String phone;

	@JsonProperty
	@NotBlank(message = "{employee.roleId.empty}")
	private String roleId;

	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime createdDateTime;

	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime modifiedDateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public LocalDateTime getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	@Override
	public String toString() {

		return getEmployeeReferenceNumber();
	}
}
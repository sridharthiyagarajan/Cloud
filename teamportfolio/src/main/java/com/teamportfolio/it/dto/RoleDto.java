package com.teamportfolio.it.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class RoleDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String id;

	@JsonProperty
	@NotBlank(message = "{role.title.empty}")
	private String title;

	@JsonProperty
	@NotBlank(message = "{role.description.empty}")
	private String description;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

		return getTitle();
	}
}
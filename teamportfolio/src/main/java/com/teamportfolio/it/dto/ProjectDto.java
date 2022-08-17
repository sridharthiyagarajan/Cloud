package com.teamportfolio.it.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class ProjectDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String id;

	@JsonProperty
	@NotBlank(message = "{project.title.empty}")
	private String title;

	@JsonProperty
	@NotBlank(message = "{project.description.empty}")
	private String description;

	@JsonProperty
	@NotNull(message = "{project.startDate.invalid}")
	private LocalDate startDate;

	@JsonProperty
	@NotNull(message = "{project.endDate.invalid}")
	private LocalDate endDate;

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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
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
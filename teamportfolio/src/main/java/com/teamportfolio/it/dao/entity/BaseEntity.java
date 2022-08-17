package com.teamportfolio.it.dao.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.teamportfolio.it.util.ColumnConstants;
import com.teamportfolio.it.util.Constants;

@MappedSuperclass
public abstract class BaseEntity {

	@Column(name = ColumnConstants.CREATED_BY)
	private String createdBy;

	@CreationTimestamp
	@Column(name = ColumnConstants.CREATED_AT)
	private LocalDateTime createdDateTime;

	@Column(name = ColumnConstants.MODIFIED_BY)
	private String modifiedBy;

	@UpdateTimestamp
	@Column(name = ColumnConstants.MODIFIED_AT)
	private LocalDateTime modifiedDateTime;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public LocalDateTime getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	@PrePersist
	protected void beforeCreation() {

		LocalDateTime localDateTime = LocalDateTime.now(Constants.UTC_ZONE_ID);

		setCreatedDateTime(localDateTime);
		setModifiedDateTime(localDateTime);
	}

	@PreUpdate
	protected void beforeUpdation() {
		setModifiedDateTime(LocalDateTime.now(Constants.UTC_ZONE_ID));
	}

}
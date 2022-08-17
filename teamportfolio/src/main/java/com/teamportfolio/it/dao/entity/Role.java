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
@Table(name = TableConstants.ROLE)
public class Role extends BaseEntity {

	@Id
	@Column(name = ColumnConstants.ID)
	@GeneratedValue
	private UUID id;

	@Column(name = ColumnConstants.TITLE)
	private String title;

	@Column(name = ColumnConstants.DESCRIPTION)
	private String description;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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

	@Override
	public String toString() {

		return getTitle();
	}

}
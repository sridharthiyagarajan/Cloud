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
@Table(name = TableConstants.SKILL)
public class Skill extends BaseEntity {

	@Id
	@Column(name = ColumnConstants.ID)
	@GeneratedValue
	private UUID id;

	@Column(name = ColumnConstants.NAME)
	private String name;

	@Column(name = ColumnConstants.DESCRIPTION)
	private String description;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {

		return getName();
	}

}
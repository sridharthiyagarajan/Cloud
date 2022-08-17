package com.teamportfolio.it.dao.project;

import java.util.List;
import java.util.UUID;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.teamportfolio.it.dao.entity.Project;
import com.teamportfolio.it.util.ColumnConstants;
import com.teamportfolio.it.util.Constants;

@Repository
public class ProjectDaoImpl implements ProjectDao {

	private final static Logger LOG = LoggerFactory.getLogger(ProjectDaoImpl.class.getCanonicalName());

	private final SessionFactory sessionFactory;

	@Autowired
	public ProjectDaoImpl(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addProject(Project project) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addProject(), method STARTED and the given input is:  project - {}", project);
		}

		if (project != null) {

			Session session = sessionFactory.getCurrentSession();
			session.save(project);

			if (LOG.isDebugEnabled()) {
				LOG.debug("In addProject(), given project is added successfully.");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addProject(), method ENDED.");
		}
	}

	@Override
	public Project retrieveProjectByGivenColumn(String column, String value, boolean isExactSearch) {

		if (LOG.isDebugEnabled()) {
			LOG.debug(
					"In retrieveProjectByGivenColumn(), method STARTED and the given input is : column - {}, value - {}, isExactSearch - {}",
					column, value, isExactSearch);
		}

		Project outputProject = null;

		if (StringUtils.isNotBlank(column) && StringUtils.isNotBlank(value)) {

			Session session = null;

			try {

				session = sessionFactory.getCurrentSession();

				CriteriaBuilder cb = session.getCriteriaBuilder();
				CriteriaQuery<Project> criteriaQuery = cb.createQuery(Project.class);
				Root<Project> root = criteriaQuery.from(Project.class);

				if (ColumnConstants.ID.equals(column)) {

					criteriaQuery.select(root).where(cb.equal(root.get(column), UUID.fromString(value)));

				} else if (isExactSearch) {

					criteriaQuery.select(root).where(cb.equal(root.get(column), value));

				} else {

					criteriaQuery.select(root).where(cb.like(cb.lower(root.get(column)),
							Constants.PERCENTAGE_SYMBOL + value.toLowerCase() + Constants.PERCENTAGE_SYMBOL));
				}

				Query<Project> query = session.createQuery(criteriaQuery);

				outputProject = query.getSingleResult();

			} catch (NoResultException ex) {

				LOG.error(
						"In retrieveProjectByGivenColumn(), exception occurred while retrieving project based on given values i.e. column - {}, value - {}, and the error message is {}.",
						column, value, ex.getMessage(), ex);

			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveProjectByGivenColumn(), method ENDED and the result is : outputProject - {}.",
					outputProject);
		}

		return outputProject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> retrieveAllProjects() {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveAllProjects(), method STARTED.");
		}

		List<Project> outputProjects = null;

		Session session = sessionFactory.getCurrentSession();
		outputProjects = session.createQuery("from Project").list();

		if (LOG.isDebugEnabled()) {

			LOG.debug("In retrieveAllProjects(), method ENDED and the output projects size is {}.",
					!CollectionUtils.isEmpty(outputProjects) ? outputProjects.size() : 0);
		}

		return outputProjects;
	}

	@Override
	public void updateProject(Project project) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateProject(), method STARTED.");
		}

		if (project != null) {

			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(project);

			if (LOG.isDebugEnabled()) {
				LOG.debug("In updateProject(), given project is updated successfully.");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateProject(), method ENDED.");
		}
	}

	@Override
	public void deleteProject(String id) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteProject(), method STARTED.");
		}

		if (StringUtils.isNotBlank(id)) {

			Session session = sessionFactory.getCurrentSession();
			session.delete(retrieveProjectByGivenColumn(ColumnConstants.ID, id, true));

			if (LOG.isDebugEnabled()) {
				LOG.debug("In deleteProject(), given project is deleted successfully.");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteProject(), method ENDED.");
		}
	}

}

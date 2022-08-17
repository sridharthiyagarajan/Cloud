package com.teamportfolio.it.dao.role;

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

import com.teamportfolio.it.dao.entity.Role;
import com.teamportfolio.it.util.ColumnConstants;
import com.teamportfolio.it.util.Constants;

@Repository
public class RoleDaoImpl implements RoleDao {

	private final static Logger LOG = LoggerFactory.getLogger(RoleDaoImpl.class.getCanonicalName());

	private final SessionFactory sessionFactory;

	@Autowired
	public RoleDaoImpl(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addRole(Role role) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addRole(), method STARTED and the given input is:  role - {}", role);
		}

		if (role != null) {

			Session session = sessionFactory.getCurrentSession();
			session.save(role);

			if (LOG.isDebugEnabled()) {
				LOG.debug("In addRole(), given role is added successfully.");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addRole(), method ENDED.");
		}
	}

	@Override
	public Role retrieveRoleByGivenColumn(String column, String value, boolean isExactSearch) {

		if (LOG.isDebugEnabled()) {
			LOG.debug(
					"In retrieveRoleByGivenColumn(), method STARTED and the given input is : column - {}, value - {}, isExactSearch - {}",
					column, value, isExactSearch);
		}

		Role outputRole = null;

		if (StringUtils.isNotBlank(column) && StringUtils.isNotBlank(value)) {

			Session session = null;

			try {

				session = sessionFactory.getCurrentSession();

				CriteriaBuilder cb = session.getCriteriaBuilder();
				CriteriaQuery<Role> criteriaQuery = cb.createQuery(Role.class);
				Root<Role> root = criteriaQuery.from(Role.class);

				if (ColumnConstants.ID.equals(column)) {

					criteriaQuery.select(root).where(cb.equal(root.get(column), UUID.fromString(value)));

				} else if (isExactSearch) {

					criteriaQuery.select(root).where(cb.equal(root.get(column), value));

				} else {

					criteriaQuery.select(root).where(cb.like(cb.lower(root.get(column)),
							Constants.PERCENTAGE_SYMBOL + value.toLowerCase() + Constants.PERCENTAGE_SYMBOL));
				}

				Query<Role> query = session.createQuery(criteriaQuery);

				outputRole = query.getSingleResult();

			} catch (NoResultException ex) {

				LOG.error(
						"In retrieveRoleByGivenColumn(), exception occurred while retrieving role based on given values i.e. column - {}, value - {}, and the error message is {}.",
						column, value, ex.getMessage(), ex);

			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveRoleByGivenColumn(), method ENDED and the result is : outputRole - {}.", outputRole);
		}

		return outputRole;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> retrieveAllRoles() {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveAllRoles(), method STARTED.");
		}

		List<Role> outputRoles = null;

		Session session = sessionFactory.getCurrentSession();
		outputRoles = session.createQuery("from Role").list();

		if (LOG.isDebugEnabled()) {

			LOG.debug("In retrieveAllRoles(), method ENDED and the output roles size is {}.",
					!CollectionUtils.isEmpty(outputRoles) ? outputRoles.size() : 0);
		}

		return outputRoles;
	}

	@Override
	public void updateRole(Role role) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateRole(), method STARTED.");
		}

		if (role != null) {

			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(role);

			if (LOG.isDebugEnabled()) {
				LOG.debug("In updateRole(), given role is updated successfully.");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateRole(), method ENDED.");
		}
	}

	@Override
	public void deleteRole(String id) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteRole(), method STARTED.");
		}

		if (StringUtils.isNotBlank(id)) {

			Session session = sessionFactory.getCurrentSession();
			session.delete(retrieveRoleByGivenColumn(ColumnConstants.ID, id, true));

			if (LOG.isDebugEnabled()) {
				LOG.debug("In deleteRole(), given role is deleted successfully.");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteRole(), method ENDED.");
		}
	}

}

package com.teamportfolio.it.dao.skill;

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

import com.teamportfolio.it.dao.entity.Skill;
import com.teamportfolio.it.util.ColumnConstants;
import com.teamportfolio.it.util.Constants;

@Repository
public class SkillDaoImpl implements SkillDao {

	private final static Logger LOG = LoggerFactory.getLogger(SkillDaoImpl.class.getCanonicalName());

	private final SessionFactory sessionFactory;

	@Autowired
	public SkillDaoImpl(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addSkill(Skill skill) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addSkill(), method STARTED and the given input is:  skill - {}", skill);
		}

		if (skill != null) {

			Session session = sessionFactory.getCurrentSession();
			session.save(skill);

			if (LOG.isDebugEnabled()) {
				LOG.debug("In addSkill(), given skill is added successfully.");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addSkill(), method ENDED.");
		}
	}

	@Override
	public Skill retrieveSkillByGivenColumn(String column, String value, boolean isExactSearch) {

		if (LOG.isDebugEnabled()) {
			LOG.debug(
					"In retrieveSkillByGivenColumn(), method STARTED and the given input is : column - {}, value - {}, isExactSearch - {}",
					column, value, isExactSearch);
		}

		Skill outputSkill = null;

		if (StringUtils.isNotBlank(column) && StringUtils.isNotBlank(value)) {

			Session session = null;

			try {

				session = sessionFactory.getCurrentSession();

				CriteriaBuilder cb = session.getCriteriaBuilder();
				CriteriaQuery<Skill> criteriaQuery = cb.createQuery(Skill.class);
				Root<Skill> root = criteriaQuery.from(Skill.class);

				if (ColumnConstants.ID.equals(column)) {

					criteriaQuery.select(root).where(cb.equal(root.get(column), UUID.fromString(value)));

				} else if (isExactSearch) {

					criteriaQuery.select(root).where(cb.equal(root.get(column), value));

				} else {

					criteriaQuery.select(root).where(cb.like(cb.lower(root.get(column)),
							Constants.PERCENTAGE_SYMBOL + value.toLowerCase() + Constants.PERCENTAGE_SYMBOL));
				}

				Query<Skill> query = session.createQuery(criteriaQuery);

				outputSkill = query.getSingleResult();

			} catch (NoResultException ex) {

				LOG.error(
						"In retrieveSkillByGivenColumn(), exception occurred while retrieving skill based on given values i.e. column - {}, value - {}, and the error message is {}.",
						column, value, ex.getMessage(), ex);

			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveSkillByGivenColumn(), method ENDED and the result is : outputSkill - {}.", outputSkill);
		}

		return outputSkill;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Skill> retrieveAllSkills() {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveAllSkills(), method STARTED.");
		}

		List<Skill> outputSkills = null;

		Session session = sessionFactory.getCurrentSession();
		outputSkills = session.createQuery("from Skill").list();

		if (LOG.isDebugEnabled()) {

			LOG.debug("In retrieveAllSkills(), method ENDED and the output skills size is {}.",
					!CollectionUtils.isEmpty(outputSkills) ? outputSkills.size() : 0);
		}

		return outputSkills;
	}

	@Override
	public void updateSkill(Skill skill) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateSkill(), method STARTED.");
		}

		if (skill != null) {

			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(skill);

			if (LOG.isDebugEnabled()) {
				LOG.debug("In updateSkill(), given skill is updated successfully.");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateSkill(), method ENDED.");
		}
	}

	@Override
	public void deleteSkill(String id) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteSkill(), method STARTED.");
		}

		if (StringUtils.isNotBlank(id)) {

			Session session = sessionFactory.getCurrentSession();
			session.delete(retrieveSkillByGivenColumn(ColumnConstants.ID, id, true));

			if (LOG.isDebugEnabled()) {
				LOG.debug("In deleteSkill(), given skill is deleted successfully.");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteSkill(), method ENDED.");
		}
	}

}

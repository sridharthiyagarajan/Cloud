package com.teamportfolio.it.dao.employee;

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

import com.teamportfolio.it.dao.entity.Employee;
import com.teamportfolio.it.util.ColumnConstants;
import com.teamportfolio.it.util.Constants;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	private final static Logger LOG = LoggerFactory.getLogger(EmployeeDaoImpl.class.getCanonicalName());

	private final SessionFactory sessionFactory;

	@Autowired
	public EmployeeDaoImpl(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addEmployee(Employee employee) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addEmployee(), method STARTED and the given input is:  employee - {}", employee);
		}

		if (employee != null) {

			Session session = sessionFactory.getCurrentSession();
			session.save(employee);

			if (LOG.isDebugEnabled()) {
				LOG.debug("In addEmployee(), given employee is added successfully.");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In addEmployee(), method ENDED.");
		}
	}

	@Override
	public List<Employee> retrieveEmployeeByGivenColumn(String column, String value, boolean isExactSearch) {

		if (LOG.isDebugEnabled()) {
			LOG.debug(
					"In retrieveEmployeeByGivenColumn(), method STARTED and the given input is : column - {}, value - {}, isExactSearch - {}",
					column, value, isExactSearch);
		}

		List<Employee> outputEmployees = null;

		if (StringUtils.isNotBlank(column) && StringUtils.isNotBlank(value)) {

			Session session = null;

			try {

				session = sessionFactory.getCurrentSession();

				CriteriaBuilder cb = session.getCriteriaBuilder();
				CriteriaQuery<Employee> criteriaQuery = cb.createQuery(Employee.class);
				Root<Employee> root = criteriaQuery.from(Employee.class);

				if (ColumnConstants.ID.equals(column)) {

					criteriaQuery.select(root).where(cb.equal(root.get(column), UUID.fromString(value)));

				} else if (isExactSearch) {

					criteriaQuery.select(root).where(cb.equal(root.get(column), value));

				} else {

					criteriaQuery.select(root).where(cb.like(root.get(column),
							Constants.PERCENTAGE_SYMBOL + value + Constants.PERCENTAGE_SYMBOL));
				}

				Query<Employee> query = session.createQuery(criteriaQuery);

				outputEmployees = query.getResultList();

			} catch (NoResultException ex) {

				LOG.error(
						"In retrieveEmployeeByGivenColumn(), exception occurred while retrieving employee based on given values i.e. column - {}, value - {}, and the error message is {}.",
						column, value, ex.getMessage(), ex);

			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveEmployeeByGivenColumn(), method ENDED and the result is : outputEmployee - {}.",
					outputEmployees);
		}

		return outputEmployees;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> retrieveAllEmployees() {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In retrieveAllEmployees(), method STARTED.");
		}

		List<Employee> outputEmployees = null;

		Session session = sessionFactory.getCurrentSession();
		outputEmployees = session.createQuery("from Employee").list();

		if (LOG.isDebugEnabled()) {

			LOG.debug("In retrieveAllEmployees(), method ENDED and the output employees size is {}.",
					!CollectionUtils.isEmpty(outputEmployees) ? outputEmployees.size() : 0);
		}

		return outputEmployees;
	}

	@Override
	public void updateEmployee(Employee employee) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateEmployee(), method STARTED.");
		}

		if (employee != null) {

			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(employee);

			if (LOG.isDebugEnabled()) {
				LOG.debug("In updateEmployee(), given employee is updated successfully.");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In updateEmployee(), method ENDED.");
		}
	}

	@Override
	public void deleteEmployee(String id) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteEmployee(), method STARTED.");
		}

		if (StringUtils.isNotBlank(id)) {

			Session session = sessionFactory.getCurrentSession();
			session.delete(retrieveEmployeeByGivenColumn(ColumnConstants.ID, id, true));

			if (LOG.isDebugEnabled()) {
				LOG.debug("In deleteEmployee(), given employee is deleted successfully.");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("In deleteEmployee(), method ENDED.");
		}
	}

}

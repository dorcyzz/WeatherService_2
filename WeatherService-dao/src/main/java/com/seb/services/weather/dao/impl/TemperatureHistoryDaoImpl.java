package com.seb.services.weather.dao.impl;

import com.seb.services.weather.dao.TemperatureHistoryDao;
import com.seb.services.weather.domain.orm.TemperatureHistory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sebastien.vandamme@gmail.com on 12/07/2014.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
public class TemperatureHistoryDaoImpl implements TemperatureHistoryDao {
    @Autowired
    private SessionFactory sessionFactory;

    protected TemperatureHistoryDaoImpl() {
        super();
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<TemperatureHistory> findAll() {
        @SuppressWarnings("unchecked")
        List results = (List<TemperatureHistory>) getCurrentSession()
                .createCriteria(TemperatureHistory.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

        return results;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer save(TemperatureHistory temperatureHistory) {
        return (Integer) getCurrentSession().save(temperatureHistory);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(TemperatureHistory temperatureHistory) {
        getCurrentSession().update(temperatureHistory);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(TemperatureHistory temperatureHistory) {
        getCurrentSession().delete(temperatureHistory);
    }

    @Override
    public TemperatureHistory findByPrimaryKey(Integer id) {
        Query query = getCurrentSession().createQuery("from TemperatureHistory where id = :id ");
        query.setParameter("id", id);

        List<TemperatureHistory> temperatureHistories = (List<TemperatureHistory>) query.list();

        if (temperatureHistories != null && !temperatureHistories.isEmpty()) {
            return temperatureHistories.get(0);
        }

        return null;
    }
}

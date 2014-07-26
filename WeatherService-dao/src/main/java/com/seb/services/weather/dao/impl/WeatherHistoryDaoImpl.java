package com.seb.services.weather.dao.impl;

import com.seb.services.weather.dao.WeatherHistoryDao;
import com.seb.services.weather.domain.orm.WeatherHistory;
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
public class WeatherHistoryDaoImpl implements WeatherHistoryDao {
    @Autowired
    private SessionFactory sessionFactory;

    protected WeatherHistoryDaoImpl() {
        super();
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<WeatherHistory> findAll() {
        return (List<WeatherHistory>) getCurrentSession()
                .createCriteria(WeatherHistory.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer save(WeatherHistory weatherHistory) {
        return (Integer) getCurrentSession().save(weatherHistory);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(WeatherHistory weatherHistory) {
        getCurrentSession().update(weatherHistory);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(WeatherHistory weatherHistory) {
        getCurrentSession().delete(weatherHistory);
    }

    @Override
    public WeatherHistory findByPrimaryKey(Integer id) {
        Query query = getCurrentSession().createQuery("from WeatherHistory where id = :id ");
        query.setParameter("id", id);

        List<WeatherHistory> weatherHistories = (List<WeatherHistory>) query.list();

        if (weatherHistories != null && !weatherHistories.isEmpty()) {
            return weatherHistories.get(0);
        }

        return null;
    }
}

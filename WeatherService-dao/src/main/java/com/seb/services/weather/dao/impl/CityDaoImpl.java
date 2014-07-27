package com.seb.services.weather.dao.impl;

import com.seb.services.weather.dao.CityDao;
import com.seb.services.weather.domain.orm.City;
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
public class CityDaoImpl implements CityDao {
    @Autowired
    private SessionFactory sessionFactory;

    protected CityDaoImpl() {
        super();
    }

//    public CityDaoImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<City> findAll() {
        @SuppressWarnings("unchecked")
        List results = (List<City>) getCurrentSession()
                .createCriteria(City.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

        return results;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String save(City city) {
        return (String) getCurrentSession().save(city);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(City city) {
        getCurrentSession().update(city);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(City city) {
        getCurrentSession().delete(city);
    }

    @Override
    public City findByPrimaryKey(String name) {
        Query query = getCurrentSession().createQuery("from City where name = :name ");
        query.setParameter("name", name);

        List<City> cities = (List<City>) query.list();

        if (cities != null && !cities.isEmpty()) {
            return cities.get(0);
        }

        return null;
    }
}

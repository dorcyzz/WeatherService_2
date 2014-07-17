package com.seb.services.weather.domain.dao;

import com.seb.services.weather.domain.orm.City;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by sebastien.vandamme@gmail.com on 12/07/2014.
 */
@Repository
@Transactional
public class CityDaoImpl implements CityDao {
    @Autowired
    private SessionFactory sessionFactory;

    protected CityDaoImpl() {
        super();
    }

    public CityDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<City> list() {
        List<City> cities = (List<City>) getCurrentSession()
                .createCriteria(City.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

        return cities;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(City city) {
        getCurrentSession().save(city);
    }

    @Override
    public void update(City city) {
        getCurrentSession().update(city);
    }

    @Override
    public void delete(int id) {
        City cityToDelete = new City();
        cityToDelete.setId(id);
        getCurrentSession().delete(cityToDelete);
    }

    @Override
    public City getCity(String name) {
        String hql = "from City where name = " + name;
        Query query = getCurrentSession().createQuery(hql);

        List<City> cities = (List<City>) query.list();

        if (cities != null && !cities.isEmpty()) {
            return cities.get(0);
        }

        return null;
    }
}

package com.seb.services.weather.data.dao;

import com.seb.services.weather.data.orm.City;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by sebastien.vandamme@gmail.com on 12/07/2014.
 */
@Repository
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
    @Transactional
    public List<City> list() {
        List<City> cities = (List<City>) sessionFactory.getCurrentSession()
                .createCriteria(City.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

        return cities;
    }

    @Override
    @Transactional
    public void saveOrUpdate(City city) {
        sessionFactory.getCurrentSession().saveOrUpdate(city);
    }

    @Override
    @Transactional
    public void delete(int id) {
        City cityToDelete = new City();
        cityToDelete.setId(id);
        sessionFactory.getCurrentSession().delete(cityToDelete);
    }

    @Override
    @Transactional
    public City getCity(String name) {
        String hql = "from City where name = " + name;
        Query query = sessionFactory.getCurrentSession().createQuery(hql);

        List<City> cities = (List<City>) query.list();

        if (cities != null && !cities.isEmpty()) {
            return cities.get(0);
        }

        return null;
    }
}

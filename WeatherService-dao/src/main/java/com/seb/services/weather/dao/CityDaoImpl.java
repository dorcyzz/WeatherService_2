package com.seb.services.weather.dao;

import com.seb.services.weather.domain.orm.City;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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

    public CityDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        CityDao cityDao = context.getBean(CityDao.class);

        System.out.println(cityDao.list());

//        City city = new City();
//        city.setName("Brussels");
//        city.setPopulation(1138855);
//        city.setProvince(Province.NONE);
//        city.setRegion(Region.BRUSSELS);
//        city.set();
//        city.set();

//        cityDao.delete(2);
//        cityDao.save(city);
        City brussels = cityDao.getCity("Brussels");
        brussels.setPopulation(1138854);
        cityDao.update(brussels);

        System.out.println(cityDao.list());
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<City> list() {
        return (List<City>) getCurrentSession()
                .createCriteria(City.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(City city) {
        getCurrentSession().save(city);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(City city) {
        getCurrentSession().update(city);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(int id) {
        City cityToDelete = new City();
        cityToDelete.setId(id);
        getCurrentSession().delete(cityToDelete);
    }

    @Override
    public City getCity(String name) {
        Query query = getCurrentSession().createQuery("from City where name = :name ");
        query.setParameter("name", name);

        List<City> cities = (List<City>) query.list();

        if (cities != null && !cities.isEmpty()) {
            return cities.get(0);
        }

        return null;
    }
}

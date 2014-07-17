package com.seb.services.weather.domain;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by sebastien.vandamme@gmail.com on 15/07/2014.
 */
@Component
public class HibernateUtil {

    @Autowired
    Configuration configuration;

    public static void main(String args[]) {
//        Configuration configuration = new Configuration().configure();
//        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
//        SessionFactory factory = configuration.buildSessionFactory(builder.build());
//        Session session = factory.openSession();
//
////        Employee employee = new Employee();
////        employee.setEmpName("Senthil Kumar");
////
////        employee.setBranch("Pune");
////        session.beginTransaction();
////        session.save(employee);
//
//        session.getTransaction().commit();

        //new HibernateUtil().export();


        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        SchemaExport schema = new SchemaExport(configuration);
        schema.setOutputFile("C:/schema.sql");
        schema.create(true, false);
    }

    public void export() {
        new SchemaExport(configuration.configure()).create(true, true);
    }
}

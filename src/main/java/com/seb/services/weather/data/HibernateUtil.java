package com.seb.services.weather.data;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;


/**
 * Created by sebastien.vandamme@gmail.com on 15/07/2014.
 */
public class HibernateUtil {
    public static void main(String args[]) {
        Configuration configuration = new Configuration().configure();
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


        new SchemaExport(configuration).create(true, true);
    }
}

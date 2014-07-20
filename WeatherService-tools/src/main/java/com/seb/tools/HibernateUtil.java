package com.seb.tools;

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
        Configuration configuration = new Configuration().configure();
//        SessionFactory sessionFactory = configuration.buildSessionFactory();
//
        SchemaExport schema = new SchemaExport(configuration);
        schema.setOutputFile("C:/schema.sql");
        schema.create(true, false);
    }


}

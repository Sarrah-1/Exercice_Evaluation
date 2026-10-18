package ma.projet.util;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = construire();

    private HibernateUtil() {
    }

    private static SessionFactory construire() {
        try (InputStream in = HibernateUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties proprietes = new Properties();
            proprietes.load(in);
            Configuration configuration = new Configuration();
            configuration.setProperties(proprietes);
            configuration.addAnnotatedClass(Employe.class);
            configuration.addAnnotatedClass(Projet.class);
            configuration.addAnnotatedClass(Tache.class);
            configuration.addAnnotatedClass(EmployeTache.class);
            return configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        SESSION_FACTORY.close();
    }
}

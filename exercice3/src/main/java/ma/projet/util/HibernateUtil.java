package ma.projet.util;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.beans.Personne;
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
            configuration.addAnnotatedClass(Personne.class);
            configuration.addAnnotatedClass(Homme.class);
            configuration.addAnnotatedClass(Femme.class);
            configuration.addAnnotatedClass(Mariage.class);
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

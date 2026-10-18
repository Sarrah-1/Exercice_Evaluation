package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class FemmeService extends BaseService<Femme> implements IDao<Femme> {

    public FemmeService() {
        super(Femme.class);
    }

    public Femme trouverLaPlusAgee() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Femme f order by f.dateNaissance asc", Femme.class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }

    public int nombreEnfantsEntreDates(Femme femme, LocalDate debut, LocalDate fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Number resultat = (Number) session.getNamedNativeQuery("Femme.nombreEnfantsEntreDates")
                    .setParameter("femmeId", femme.getId())
                    .setParameter("debut", debut)
                    .setParameter("fin", fin)
                    .getSingleResult();
            return resultat.intValue();
        }
    }

    public List<Femme> femmesMarieesAuMoinsDeuxFois() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNamedQuery("Femme.mariees2Fois", Femme.class).list();
        }
    }
}

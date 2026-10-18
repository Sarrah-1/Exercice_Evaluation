package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TacheService extends BaseService<Tache> implements IDao<Tache> {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TacheService() {
        super(Tache.class);
    }

    public void afficherTachesPrixSuperieurA(double prix) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Tache> taches = session
                    .createNamedQuery("Tache.prixSuperieurA", Tache.class)
                    .setParameter("prix", prix)
                    .list();
            System.out.println("Tâches dont le prix est supérieur à " + String.format("%.0f", prix) + " DH");
            System.out.printf("%-5s%-18s%s%n", "Num", "Nom", "Prix");
            for (Tache t : taches) {
                System.out.printf("%-5d%-18s%s%n", t.getId(), t.getNom(), String.format("%.0f DH", t.getPrix()));
            }
        }
    }

    public void afficherTachesRealiseesEntreDates(LocalDate debut, LocalDate fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<EmployeTache> liste = session
                    .createQuery("select et from EmployeTache et join fetch et.tache t "
                            + "where et.dateDebutReelle >= :debut and et.dateFinReelle <= :fin "
                            + "order by et.dateDebutReelle", EmployeTache.class)
                    .setParameter("debut", debut)
                    .setParameter("fin", fin)
                    .list();
            System.out.println("Tâches réalisées entre " + FORMAT.format(debut) + " et " + FORMAT.format(fin));
            System.out.printf("%-5s%-18s%-22s%s%n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");
            for (EmployeTache et : liste) {
                System.out.printf("%-5d%-18s%-22s%s%n", et.getTache().getId(), et.getTache().getNom(),
                        FORMAT.format(et.getDateDebutReelle()), FORMAT.format(et.getDateFinReelle()));
            }
        }
    }
}

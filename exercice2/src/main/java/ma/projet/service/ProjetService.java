package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ProjetService extends BaseService<Projet> implements IDao<Projet> {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMAT_LONG = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);

    public ProjetService() {
        super(Projet.class);
    }

    public void afficherTachesPlanifiees(int idProjet) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Projet projet = session.get(Projet.class, idProjet);
            if (projet == null) {
                System.out.println("Projet introuvable");
                return;
            }
            List<Tache> taches = session
                    .createQuery("from Tache t where t.projet.id = :id order by t.id", Tache.class)
                    .setParameter("id", idProjet)
                    .list();
            afficherEntete(projet);
            System.out.println("Liste des tâches planifiées :");
            System.out.printf("%-5s%-18s%-22s%s%n", "Num", "Nom", "Date Début Planifiée", "Date Fin Planifiée");
            for (Tache t : taches) {
                System.out.printf("%-5d%-18s%-22s%s%n", t.getId(), t.getNom(),
                        FORMAT.format(t.getDateDebut()), FORMAT.format(t.getDateFin()));
            }
        }
    }

    public void afficherTachesRealisees(int idProjet) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Projet projet = session.get(Projet.class, idProjet);
            if (projet == null) {
                System.out.println("Projet introuvable");
                return;
            }
            List<EmployeTache> liste = session
                    .createQuery("select et from EmployeTache et join fetch et.tache t "
                            + "where t.projet.id = :id order by t.id, et.id", EmployeTache.class)
                    .setParameter("id", idProjet)
                    .list();
            afficherEntete(projet);
            System.out.println("Liste des tâches:");
            System.out.printf("%-5s%-18s%-22s%s%n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");
            for (EmployeTache et : liste) {
                System.out.printf("%-5d%-18s%-22s%s%n", et.getTache().getId(), et.getTache().getNom(),
                        FORMAT.format(et.getDateDebutReelle()), FORMAT.format(et.getDateFinReelle()));
            }
        }
    }

    private void afficherEntete(Projet projet) {
        System.out.println("Projet : " + projet.getId() + "      Nom : " + projet.getNom()
                + "     Date début : " + FORMAT_LONG.format(projet.getDateDebut()));
    }
}

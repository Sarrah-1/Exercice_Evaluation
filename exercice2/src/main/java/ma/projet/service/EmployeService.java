package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmployeService extends BaseService<Employe> implements IDao<Employe> {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EmployeService() {
        super(Employe.class);
    }

    public void afficherTachesRealisees(int idEmploye) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employe employe = session.get(Employe.class, idEmploye);
            if (employe == null) {
                System.out.println("Employé introuvable");
                return;
            }
            List<EmployeTache> liste = session
                    .createQuery("select et from EmployeTache et join fetch et.tache "
                            + "where et.employe.id = :id order by et.id", EmployeTache.class)
                    .setParameter("id", idEmploye)
                    .list();
            System.out.println("Employé : " + employe.getNom() + " " + employe.getPrenom());
            System.out.println("Liste des tâches réalisées :");
            System.out.printf("%-5s%-18s%-22s%s%n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");
            for (EmployeTache et : liste) {
                System.out.printf("%-5d%-18s%-22s%s%n", et.getTache().getId(), et.getTache().getNom(),
                        FORMAT.format(et.getDateDebutReelle()), FORMAT.format(et.getDateFinReelle()));
            }
        }
    }

    public void afficherProjetsGeres(int idEmploye) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employe employe = session.get(Employe.class, idEmploye);
            if (employe == null) {
                System.out.println("Employé introuvable");
                return;
            }
            List<Projet> projets = session
                    .createQuery("from Projet p where p.chefProjet.id = :id order by p.id", Projet.class)
                    .setParameter("id", idEmploye)
                    .list();
            System.out.println("Chef de projet : " + employe.getNom() + " " + employe.getPrenom());
            System.out.println("Liste des projets gérés :");
            System.out.printf("%-5s%-22s%-14s%s%n", "Num", "Nom", "Date Début", "Date Fin");
            for (Projet p : projets) {
                System.out.printf("%-5d%-22s%-14s%s%n", p.getId(), p.getNom(),
                        FORMAT.format(p.getDateDebut()), FORMAT.format(p.getDateFin()));
            }
        }
    }
}

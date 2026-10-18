package ma.projet.test;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;
import ma.projet.util.HibernateUtil;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService employeTacheService = new EmployeTacheService();

        Employe ouaday = new Employe("OUADAY", "sara", "0612345465");
        Employe echarkaoui = new Employe("ENCHARKAOUI", "hiba", "0600909876");
        Employe tayib = new Employe("TAYIB", "sayf", "0609898767");
        employeService.create(ouaday);
        employeService.create(echarkaoui);
        employeService.create(tayib);

        Projet stock = new Projet("Gestion de stock", LocalDate.of(2013, 1, 14), LocalDate.of(2013, 6, 30), ouaday);
        Projet commerce = new Projet("Gestion commerciale", LocalDate.of(2013, 2, 1), LocalDate.of(2013, 9, 30), ouaday);
        Projet rh = new Projet("Gestion RH", LocalDate.of(2013, 3, 1), LocalDate.of(2013, 10, 31), echarkaoui);
        projetService.create(stock);
        projetService.create(commerce);
        projetService.create(rh);

        Tache analyse = new Tache("Analyse", LocalDate.of(2013, 2, 10), LocalDate.of(2013, 2, 20), 1200, stock);
        Tache conception = new Tache("Conception", LocalDate.of(2013, 3, 10), LocalDate.of(2013, 3, 15), 800, stock);
        Tache developpement = new Tache("Développement", LocalDate.of(2013, 4, 10), LocalDate.of(2013, 4, 25), 3000, stock);
        Tache analyse2 = new Tache("Analyse besoins", LocalDate.of(2013, 3, 1), LocalDate.of(2013, 3, 12), 1500, commerce);
        Tache tests = new Tache("Tests", LocalDate.of(2013, 5, 1), LocalDate.of(2013, 5, 10), 900, commerce);
        tacheService.create(analyse);
        tacheService.create(conception);
        tacheService.create(developpement);
        tacheService.create(analyse2);
        tacheService.create(tests);

        employeTacheService.create(new EmployeTache(ouaday, analyse, LocalDate.of(2013, 2, 10), LocalDate.of(2013, 2, 20)));
        employeTacheService.create(new EmployeTache(echarkaoui, conception, LocalDate.of(2013, 3, 10), LocalDate.of(2013, 3, 15)));
        employeTacheService.create(new EmployeTache(tayib, developpement, LocalDate.of(2013, 4, 10), LocalDate.of(2013, 4, 25)));
        employeTacheService.create(new EmployeTache(ouaday, analyse2, LocalDate.of(2013, 3, 1), LocalDate.of(2013, 3, 12)));
        employeTacheService.create(new EmployeTache(ouaday, tests, LocalDate.of(2013, 5, 2), LocalDate.of(2013, 5, 9)));

        System.out.println("Liste des employés");
        for (Employe e : employeService.findAll()) {
            System.out.println(e.getId() + " " + e.getNom() + " " + e.getPrenom() + " " + e.getTelephone());
        }

        System.out.println();
        System.out.println("Tâches réalisées par un employé");
        employeService.afficherTachesRealisees(ouaday.getId());

        System.out.println();
        System.out.println("Projets gérés par un employé");
        employeService.afficherProjetsGeres(ouaday.getId());

        System.out.println();
        System.out.println("Tâches planifiées d'un projet");
        projetService.afficherTachesPlanifiees(stock.getId());

        System.out.println();
        System.out.println("Tâches réalisées d'un projet");
        projetService.afficherTachesRealisees(stock.getId());

        System.out.println();
        System.out.println("Tâches dont le prix est supérieur à 1000 DH");
        tacheService.afficherTachesPrixSuperieurA(1000);

        System.out.println();
        System.out.println("Tâches réalisées entre deux dates");
        tacheService.afficherTachesRealiseesEntreDates(LocalDate.of(2013, 3, 1), LocalDate.of(2013, 4, 30));

        HibernateUtil.shutdown();
    }
}

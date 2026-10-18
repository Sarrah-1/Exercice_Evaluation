package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import ma.projet.util.HibernateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        HommeService hommeService = new HommeService();
        FemmeService femmeService = new FemmeService();
        MariageService mariageService = new MariageService();

        Femme[] femmes = {
                new Femme("HIJAB", "HIND", "0623456543", "Marrakech", LocalDate.of(1965, 3, 12)),
                new Femme("MAATI", "IMANE", "0698764556", "RABAT", LocalDate.of(1975, 1, 30)),
                new Femme("MANSOUR", "RAYHANA", "0678654356", "Casablanca", LocalDate.of(1962, 5, 9))
        };
        for (Femme f : femmes) {
            femmeService.create(f);
        }

        Homme[] hommes = {
                new Homme("NOUINI", "AMINE", "0690876578", "Marrakech", LocalDate.of(1960, 1, 15)),
                new Homme("KOUBRI", "HATIM", "0690878900", "Rabat", LocalDate.of(1958, 8, 8)),
                new Homme("BOUSBAA", "ABDESSAMAD", "0645433234", "Casa", LocalDate.of(1966, 10, 10)),
        };
        for (Homme h : hommes) {
            hommeService.create(h);
        }

        mariageService.create(new Mariage(hommes[0], femmes[2], LocalDate.of(1989, 9, 3), LocalDate.of(1990, 9, 3), 0));
        mariageService.create(new Mariage(hommes[0], femmes[0], LocalDate.of(1990, 9, 3), null, 4));
        mariageService.create(new Mariage(hommes[0], femmes[1], LocalDate.of(1995, 9, 3), null, 2));

        LocalDate debut = LocalDate.of(1985, 1, 1);
        LocalDate fin = LocalDate.of(2005, 12, 31);

        System.out.println("Liste des femmes");
        for (Femme f : femmeService.findAll()) {
            System.out.println(f.getId() + " " + f.getNom() + " " + f.getPrenom()
                    + " " + FORMAT.format(f.getDateNaissance()));
        }

        System.out.println();
        System.out.println("Femme la plus âgée");
        Femme plusAgee = femmeService.trouverLaPlusAgee();
        System.out.println(plusAgee.getNom() + " " + plusAgee.getPrenom()
                + " née le " + FORMAT.format(plusAgee.getDateNaissance()));

        System.out.println();
        System.out.println("Épouses d'un homme entre deux dates");
        hommeService.afficherEpousesEntreDates(hommes[0], debut, fin);

        System.out.println();
        System.out.println("Nombre d'enfants d'une femme entre deux dates");
        System.out.println(femmes[0].getNom() + " " + femmes[0].getPrenom() + " : "
                + femmeService.nombreEnfantsEntreDates(femmes[0], debut, fin) + " enfant(s)");

        System.out.println();
        System.out.println("Femmes mariées deux fois ou plus");
        for (Femme f : femmeService.femmesMarieesAuMoinsDeuxFois()) {
            System.out.println(f.getNom() + " " + f.getPrenom());
        }

        System.out.println();
        System.out.println("Hommes mariés à quatre femmes entre deux dates");
        System.out.println(hommeService.nombreHommesMariesQuatreFemmesEntreDates(debut, fin) + " homme(s)");

        System.out.println();
        System.out.println("Mariages d'un homme");
        hommeService.afficherMariages(hommes[0]);

        HibernateUtil.shutdown();
    }
}

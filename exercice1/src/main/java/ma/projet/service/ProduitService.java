package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ProduitService extends BaseService<Produit> implements IDao<Produit> {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);

    public ProduitService() {
        super(Produit.class);
    }

    public void afficherProduitsParCategorie(Categorie categorie) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Produit> produits = session
                    .createQuery("from Produit p where p.categorie.id = :id order by p.id", Produit.class)
                    .setParameter("id", categorie.getId())
                    .list();
            System.out.println("Catégorie : " + categorie.getLibelle());
            System.out.printf("%-12s%s%n", "Référence", "Prix");
            for (Produit p : produits) {
                System.out.printf("%-12s%s%n", p.getReference(), formaterPrix(p.getPrix()));
            }
        }
    }

    public void afficherProduitsCommandesEntreDates(LocalDate debut, LocalDate fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<LigneCommandeProduit> lignes = session
                    .createQuery("select l from LigneCommandeProduit l join fetch l.produit join fetch l.commande c "
                            + "where c.date between :debut and :fin order by c.id, l.id", LigneCommandeProduit.class)
                    .setParameter("debut", debut)
                    .setParameter("fin", fin)
                    .list();
            afficherLignes(lignes);
        }
    }

    public void afficherProduitsParCommande(int idCommande) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<LigneCommandeProduit> lignes = session
                    .createQuery("select l from LigneCommandeProduit l join fetch l.produit join fetch l.commande c "
                            + "where c.id = :id order by l.id", LigneCommandeProduit.class)
                    .setParameter("id", idCommande)
                    .list();
            afficherLignes(lignes);
        }
    }

    public void afficherProduitsPrixSuperieurA(float prix) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Produit> produits = session
                    .createNamedQuery("Produit.prixSuperieurA", Produit.class)
                    .setParameter("prix", prix)
                    .list();
            System.out.println("Produits dont le prix est supérieur à " + formaterPrix(prix));
            System.out.printf("%-12s%s%n", "Référence", "Prix");
            for (Produit p : produits) {
                System.out.printf("%-12s%s%n", p.getReference(), formaterPrix(p.getPrix()));
            }
        }
    }

    private void afficherLignes(List<LigneCommandeProduit> lignes) {
        if (lignes.isEmpty()) {
            System.out.println("Aucun produit trouvé");
            return;
        }
        Commande courante = null;
        for (LigneCommandeProduit l : lignes) {
            Commande c = l.getCommande();
            if (courante == null || courante.getId() != c.getId()) {
                courante = c;
                System.out.println("Commande : " + c.getId() + "     Date : " + FORMAT.format(c.getDate()));
                System.out.println("Liste des produits :");
                System.out.printf("%-12s%-10s%s%n", "Référence", "Prix", "Quantité");
            }
            System.out.printf("%-12s%-10s%d%n", l.getProduit().getReference(),
                    formaterPrix(l.getProduit().getPrix()), l.getQuantite());
        }
    }

    private String formaterPrix(float prix) {
        return String.format("%.0f DH", prix);
    }
}

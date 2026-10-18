package ma.projet.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HommeService extends BaseService<Homme> implements IDao<Homme> {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public HommeService() {
        super(Homme.class);
    }

    public List<Femme> epousesEntreDates(Homme homme, LocalDate debut, LocalDate fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select m.femme from Mariage m where m.homme.id = :id "
                            + "and m.dateDebut between :debut and :fin order by m.dateDebut", Femme.class)
                    .setParameter("id", homme.getId())
                    .setParameter("debut", debut)
                    .setParameter("fin", fin)
                    .list();
        }
    }

    public void afficherEpousesEntreDates(Homme homme, LocalDate debut, LocalDate fin) {
        System.out.println("Épouses de " + homme.getNom() + " " + homme.getPrenom()
                + " entre le " + FORMAT.format(debut) + " et le " + FORMAT.format(fin) + " :");
        int numero = 1;
        for (Femme f : epousesEntreDates(homme, debut, fin)) {
            System.out.println(numero++ + ". " + f.getNom() + " " + f.getPrenom());
        }
    }

    public long nombreHommesMariesQuatreFemmesEntreDates(LocalDate debut, LocalDate fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
            Root<Mariage> mariage = cq.from(Mariage.class);
            cq.select(mariage.<Homme>get("homme").<Integer>get("id"))
                    .where(cb.between(mariage.<LocalDate>get("dateDebut"), debut, fin))
                    .groupBy(mariage.<Homme>get("homme").<Integer>get("id"))
                    .having(cb.equal(cb.count(mariage), 4L));
            return session.createQuery(cq).getResultList().size();
        }
    }

    public void afficherMariages(Homme homme) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Mariage> mariages = session
                    .createQuery("select m from Mariage m join fetch m.femme where m.homme.id = :id "
                            + "order by m.dateDebut", Mariage.class)
                    .setParameter("id", homme.getId())
                    .list();

            System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());

            System.out.println("Mariages En Cours :");
            int numero = 1;
            for (Mariage m : mariages) {
                if (m.getDateFin() == null) {
                    System.out.println(numero++ + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom()
                            + "   Date Début : " + FORMAT.format(m.getDateDebut())
                            + "    Nbr Enfants : " + m.getNbrEnfant());
                }
            }

            System.out.println();
            System.out.println("Mariages échoués :");
            numero = 1;
            for (Mariage m : mariages) {
                if (m.getDateFin() != null) {
                    System.out.println(numero++ + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom()
                            + "   Date Début : " + FORMAT.format(m.getDateDebut()));
                    System.out.println("   Date Fin : " + FORMAT.format(m.getDateFin())
                            + "    Nbr Enfants : " + m.getNbrEnfant());
                }
            }
        }
    }
}

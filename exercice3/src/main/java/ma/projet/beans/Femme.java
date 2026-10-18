package ma.projet.beans;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "femme")
@NamedNativeQuery(
        name = "Femme.nombreEnfantsEntreDates",
        query = "select coalesce(sum(m.nbr_enfant), 0) from mariage m "
                + "where m.femme_id = :femmeId and m.date_debut >= :debut and m.date_debut <= :fin")
@NamedQuery(
        name = "Femme.mariees2Fois",
        query = "select f from Femme f where (select count(m) from Mariage m where m.femme = f) >= 2")
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme")
    private List<Mariage> mariages = new ArrayList<>();

    public Femme() {
    }

    public Femme(String nom, String prenom, String telephone, String adresse, LocalDate dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}

package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.dao.IDao;

public class CommandeService extends BaseService<Commande> implements IDao<Commande> {

    public CommandeService() {
        super(Commande.class);
    }
}

package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;

public class LigneCommandeService extends BaseService<LigneCommandeProduit> implements IDao<LigneCommandeProduit> {

    public LigneCommandeService() {
        super(LigneCommandeProduit.class);
    }
}

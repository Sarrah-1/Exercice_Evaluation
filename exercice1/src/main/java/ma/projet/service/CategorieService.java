package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;

public class CategorieService extends BaseService<Categorie> implements IDao<Categorie> {

    public CategorieService() {
        super(Categorie.class);
    }
}

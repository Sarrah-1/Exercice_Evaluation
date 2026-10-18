package ma.projet.service;

import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;

public class MariageService extends BaseService<Mariage> implements IDao<Mariage> {

    public MariageService() {
        super(Mariage.class);
    }
}

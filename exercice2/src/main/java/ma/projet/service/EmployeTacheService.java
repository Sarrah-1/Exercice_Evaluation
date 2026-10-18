package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;

public class EmployeTacheService extends BaseService<EmployeTache> implements IDao<EmployeTache> {

    public EmployeTacheService() {
        super(EmployeTache.class);
    }
}

package services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by yuesongwang on 11/14/16.
 */
public class BaseService {
    EntityManagerFactory emFactory;
    EntityManager em;
    EntityTransaction tx;

    public BaseService(){
        this.emFactory  = Persistence.createEntityManagerFactory("persistenceUnit");
        this.em = emFactory.createEntityManager();
    }

    public void begin(){
        tx = em.getTransaction();
        tx.begin();
    }

    public void commit(){
        if(tx != null)
            tx.commit();
    }

}

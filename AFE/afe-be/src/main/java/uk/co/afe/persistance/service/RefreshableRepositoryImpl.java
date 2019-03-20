package uk.co.afe.persistance.service;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Sergey Teryoshin
 * 21.03.2018 0:12
 */
public class RefreshableRepositoryImpl<T> implements RefreshableRepository<T> {

    @PersistenceContext
    private EntityManager manager;

    public void refresh(T o) {
        Session session = manager.unwrap(Session.class);
        session.refresh(o);
    }

}

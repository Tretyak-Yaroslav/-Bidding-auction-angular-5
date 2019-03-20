package uk.co.afe.persistance.service;

/**
 * Refreshable repository
 *
 * @author Sergey Teryoshin
 * 21.03.2018 0:22
 */
public interface RefreshableRepository<T> {

    /**
     * Refresh cache
     */
    void refresh(T obj);
}

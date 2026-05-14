package model.DAO;

import java.sql.SQLException;
import java.util.Collection;

public interface DaoInterface<T, K> {

    // Recupera un record tramite chiave primaria
    T doRetrieveByKey(K key) throws SQLException;

    // Recupera tutti i record, con possibilità di ordinamento
    Collection<T> doRetrieveAll(String order) throws SQLException;

    // Inserisce un nuovo record
    void doSave(T item) throws SQLException;

    // Aggiorna un record esistente
    void doUpdate(T item) throws SQLException;

    // Elimina un record tramite chiave primaria
    boolean doDelete(K key) throws SQLException;
}

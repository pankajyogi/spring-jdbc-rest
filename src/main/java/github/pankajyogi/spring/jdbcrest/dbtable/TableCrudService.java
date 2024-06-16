package github.pankajyogi.spring.jdbcrest.dbtable;

public interface TableCrudService {

    String addEntityInTable(String tableName, DbEntity entity);


    DbEntity getEntityFromTable(String tableName, String entityId);


    void updateEntityInTable(String tableName, String entityId, DbEntity entity);


    void deleteEntityFromTable(String tableName, String entityId);
}

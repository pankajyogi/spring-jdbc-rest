package github.pankajyogi.spring.jdbcrest.dbtable;


import github.pankajyogi.spring.jdbcrest.annotation.RestRequestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


/**
 * {@code DbTableRestController} class represents a REST controller for performing CRUD operations on a database table.
 * It provides endpoints for adding, retrieving, updating, and deleting entities from a specified table.
 */
@Slf4j
@RestRequestController("/api")
public class DbTableRestController {


    private final TableCrudService tableCrudService;


    /**
     * Constructs a new instance of this class using the provided {@code TableCrudService}. Spring Boot invokes this
     * constructor automatically during bean initialization.
     *
     * @param tableCrudService the bean of {@code TableCrudService} supplied by Spring Boot
     */
    @Autowired
    public DbTableRestController(TableCrudService tableCrudService) {
        this.tableCrudService = tableCrudService;
    }


    /**
     * Adds a new entity to the specified database table.
     *
     * @param tableName the name of the table where the entity will be added
     * @param entity    the entity to be added to the table
     * @return a ResponseEntity representing {@code created} status and location of the created entity
     */
    @PostMapping("/{tableName}")
    public ResponseEntity<Void> addEntity(@PathVariable String tableName, @RequestBody DbEntity entity) {
        logger.info("Received request for creating new entity table:{}", tableName);
        String entityId = tableCrudService.addEntityInTable(tableName, entity);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entityId)
                .toUri();
        return ResponseEntity.created(location).build();
    }


    /**
     * Retrieves the specified entity from the given database table.
     *
     * @param tableName the name of the table containing the entity
     * @param entityId  the id of the entity to retrieve
     * @return a ResponseEntity representing {@code success} status with the retrieved entity
     */
    @GetMapping("/{tableName}/{entityId}")
    public ResponseEntity<DbEntity> getEntity(@PathVariable String tableName, @PathVariable String entityId) {
        logger.info("Received request for fetching an entity table:{}, entityId:{}", tableName, entityId);
        DbEntity dbEntity = tableCrudService.getEntityFromTable(tableName, entityId);
        return ResponseEntity.ok(dbEntity);
    }


    /**
     * Updates an entity in the specified table.
     *
     * @param tableName the name of the table to update the entity in
     * @param entityId  the id of the entity to update
     * @param entity    the updated entity data
     * @return A ResponseEntity with {@code no content} status
     */
    @PutMapping("/{tableName}/{entityId}")
    public ResponseEntity<Void> updateEntity(@PathVariable String tableName,
                                             @PathVariable String entityId,
                                             @RequestBody DbEntity entity) {
        logger.info("Received request for updating an entity table:{}, entityId:{}", tableName, entityId);
        tableCrudService.updateEntityInTable(tableName, entityId, entity);
        return ResponseEntity.noContent().build();
    }


    /**
     * Deletes an entity from a specified table.
     *
     * @param tableName the name of the table from which the entity will be deleted
     * @param entityId  the id of the entity to be deleted
     * @return A ResponseEntity with {@code no content} status
     */
    @DeleteMapping("/{tableName}/{entityId}")
    public ResponseEntity<Void> deleteEntity(@PathVariable String tableName, @PathVariable String entityId) {
        logger.info("Received request for deleting an entity table:{}, entityId:{}", tableName, entityId);
        tableCrudService.deleteEntityFromTable(tableName, entityId);
        return ResponseEntity.noContent().build();
    }


}

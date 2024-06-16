package github.pankajyogi.spring.jdbcrest.dbtable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class DbTableRestController {

    private final TableCrudService tableCrudService;


    @Autowired
    public DbTableRestController(TableCrudService tableCrudService) {
        this.tableCrudService = tableCrudService;
    }


    @PostMapping("/{tableName}")
    public ResponseEntity<Object> addEntity(@PathVariable("tableName") String tableName,
                                            @RequestBody DbEntity entity) {
        logger.info("Received request for creating new entity. table:{}", tableName);
        String entityId = tableCrudService.addEntityInTable(tableName, entity);
        URI location = URI.create(String.format("%s/%s", tableName, entityId));
        return ResponseEntity.created(location).build();
    }


    @GetMapping("/{tableName}/{entityId}")
    public ResponseEntity<Object> getEntity(@PathVariable("tableName") String tableName,
                                            @PathVariable("entityId") String entityId) {
        logger.info("Received request for fetching an entity. table:{}, entityId:{}", tableName, entityId);
        DbEntity dbEntity = tableCrudService.getEntityFromTable(tableName, entityId);
        return ResponseEntity.ok(dbEntity);
    }


    @PutMapping("/{tableName}/{entityId}")
    public ResponseEntity<Object> updateEntity(@PathVariable("tableName") String tableName,
                                               @PathVariable("entityId") String entityId,
                                               @RequestBody DbEntity entity) {
        logger.info("Received request for updating an entity. table:{}, entityId:{}", tableName, entityId);
        tableCrudService.updateEntityInTable(tableName, entityId, entity);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{tableName}/{entityId}")
    public ResponseEntity<Object> deleteEntity(@PathVariable("tableName") String tableName,
                                               @PathVariable("entityId") String entityId) {
        logger.info("Received request for deleting an entity. table:{}, entityId:{}", tableName, entityId);
        tableCrudService.deleteEntityFromTable(tableName, entityId);
        return ResponseEntity.noContent().build();
    }

}

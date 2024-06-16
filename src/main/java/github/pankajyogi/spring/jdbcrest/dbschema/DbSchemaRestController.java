package github.pankajyogi.spring.jdbcrest.dbschema;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dbSchema")
@Slf4j
public class DbSchemaRestController {

    private final DbSchemaService dbSchemaService;


    @Autowired
    public DbSchemaRestController(DbSchemaService dbSchemaService) {
        this.dbSchemaService = dbSchemaService;
    }


    @GetMapping
    public DbSchema getDbSchema() {
        return dbSchemaService.getDbSchema();
    }

}

package com.github.pankajyogi.spring.jdbcrest.dbtable;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = DbTableRestController.class)
public class DbTableRestControllerTest {


    @Autowired
    MockMvc mvc;

    @MockBean
    TableCrudService tableCrudService;


    @Test
    void shouldReturnCreatedWhenAddEntityCalledWithTableAndEntityPresent() throws Exception {
        String testTableName = "test_table";
        when(tableCrudService.addEntityInTable(eq(testTableName), any(DbEntity.class)))
                .thenReturn("1");

        mvc.perform(post("/api/" + testTableName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated());
    }


    @Test
    void shouldReturnBadRequestWhenAddEntityCalledWithTableAbsent() throws Exception {
        String testTableName = "non_existent_table";
        when(tableCrudService.addEntityInTable(eq(testTableName), any(DbEntity.class)))
                .thenThrow(RuntimeException.class);

        mvc.perform(post("/api/" + testTableName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldReturnOkWhenGetEntityWithTableAndEntityPresent() throws Exception {
        String testTableName = "test_table";
        String entityId = "1";
        when(tableCrudService.getEntityFromTable(eq(testTableName), eq(entityId)))
                .thenReturn(new DbEntity() {});

        mvc.perform(get("/api/" + testTableName + "/" + entityId))
                .andExpect(status().isOk());
    }


    @Test
    void shouldReturnNotFoundWhenGetEntityWithTableAbsent() throws Exception {
        String testTableName = "non_existent_table";
        String entityId = "1";
        when(tableCrudService.getEntityFromTable(eq(testTableName), eq(entityId)))
                .thenThrow(RuntimeException.class);

        mvc.perform(get("/api/" + testTableName + "/" + entityId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldReturnNoContentWhenUpdateEntityCalledWithTableAndEntityPresent() throws Exception {
        String testTableName = "test_table";
        String entityId = "1";
        doNothing()
                .when(tableCrudService).updateEntityInTable(eq(testTableName), eq(entityId), any(DbEntity.class));

        mvc.perform(put("/api/" + testTableName + "/" + entityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
    }


    @Test
    void shouldReturnNotFoundWhenUpdateEntityCalledWithTableAbsent() throws Exception {
        String testTableName = "non_existent_table";
        String entityId = "1";
        doThrow(RuntimeException.class)
                .when(tableCrudService).updateEntityInTable(eq(testTableName), eq(entityId), any(DbEntity.class));

        mvc.perform(put("/api/" + testTableName + "/" + entityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldReturnNoContentWhenDeleteEntityCalledWithTableAndEntityPresent() throws Exception {
        String testTableName = "test_table";
        String entityId = "1";
        doNothing()
                .when(tableCrudService).deleteEntityFromTable(eq(testTableName), eq(entityId));

        mvc.perform(delete("/api/" + testTableName + "/" + entityId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    void shouldReturnNotFoundWhenDeleteEntityCalledWithTableAbsent() throws Exception {
        String testTableName = "non_existent_table";
        String entityId = "1";
        doThrow(RuntimeException.class)
                .when(tableCrudService).deleteEntityFromTable(eq(testTableName), eq(entityId));

        mvc.perform(delete("/api/" + testTableName + "/" + entityId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}

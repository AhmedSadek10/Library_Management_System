package com.example.Library.Controllers;

import com.example.Library.Models.Book;
import com.example.Library.Models.Patron;
import com.example.Library.Services.PatronService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PatronControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PatronService patronService;

    @Test
    public void createPatron() throws Exception {
        String patronJson = "{\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"contactInformation\": \"+2012121212\"\n" +
                "}";

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void getAllPatrons() throws Exception {
        patronService.create(new Patron((long) 1, "John Doe", "someContact@contact"));

        mockMvc.perform(get("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].contactInformation").value("someContact@contact"));
    }

    @Test
    public void getPatronById() throws Exception {
        patronService.create(new Patron( 4L, "John Doe", "someContact@contact"));

        mockMvc.perform(get("/api/patrons/4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").value("John Doe"))
                .andExpect(jsonPath("contactInformation").value("someContact@contact"));
    }

    @Test
    public void updatePatron() throws Exception {
        patronService.create(new Patron((long) 1, "John Doe", "someContact@contact"));
        String updatedPatronJson = "{\n" +
                "  \"name\": \"Updated Name\",\n" +
                "  \"contactInformation\": \"+2012121212\"\n" +
                "}";

        mockMvc.perform(put("/api/patrons/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedPatronJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value("Updated Name"))
                .andExpect(jsonPath("contactInformation").value("+2012121212"));
    }

    @Test
    public void deletePatron() throws Exception {
        patronService.create(new Patron((long) 1, "John Doe", "someContact@contact"));

        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isOk());

    }
    @Test
    public void deletePatronNotFound ()throws Exception{
        ResultActions result = mockMvc.perform(delete("http://localhost/api/patrons/50"));
        result.andExpect(status().isNotFound());
    }
    @Test
    public void getByIdNotFound ()throws Exception{
        ResultActions result = mockMvc.perform(get("http://localhost/api/patrons/50"));
        result.andExpect(status().isNotFound());
    }
    @Test
    public void updateNotFound ()throws Exception{
        String patronJson = "{\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"contactInformation\": \"+2012121212\"\n" +
                "}";

       ResultActions result = mockMvc.perform(put("/api/patrons/50")
                .contentType(MediaType.APPLICATION_JSON)
                .content(patronJson));

        result.andExpect(status().isNotFound());
    }
}

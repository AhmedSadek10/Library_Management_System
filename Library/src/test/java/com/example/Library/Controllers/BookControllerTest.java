package com.example.Library.Controllers;

import com.example.Library.Models.Book;
import com.example.Library.Services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookService bookService;

    @Test
    public void createBook () throws Exception{
        String bookJson = "{\n" +
                "  \"title\": \"Samplee Book\",\n" +
                "  \"author\": \"John Doe\",\n" +
                "  \"publicationYear\": 2024,\n" +
                "  \"isbn\": \"978-1-23456-789-0\"\n" +
                "}";

        ResultActions result = mockMvc.perform(post("http://localhost:8080/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson));

        result.andExpect(status().isCreated());
    }
    @Test
    public void getAll () throws Exception{
        bookService.create(new Book((long) 1,"Sample Book", "John Doe", 2024, "978-1-23456-789-0"));

        ResultActions result = mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())

                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].title").value("Sample Book"))
                .andExpect(jsonPath("$[0].author").value("John Doe"))
                .andExpect(jsonPath("$[0].publicationYear").value(2024))
                .andExpect(jsonPath("$[0].isbn").value("978-1-23456-789-0"));
    }
    @Test
    public void getById () throws Exception{
        bookService.create(new Book((long) 1,"Sample Book", "John Doe", 2024, "978-1-23456-789-0"));

        ResultActions result = mockMvc.perform(get("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("title").value("Sample Book"))
                .andExpect(jsonPath("author").value("John Doe"))
                .andExpect(jsonPath("publicationYear").value(2024))
                .andExpect(jsonPath("isbn").value("978-1-23456-789-0"));
    }
    @Test
    public void update () throws Exception{
        bookService.create(new Book((long) 1,"Sample Book", "John Doe", 2024, "978-1-23456-789-0"));
        String updatedBookJson = "{" +
                "\"title\": \"Updated Book Title\"," +
                "\"author\": \"Updated Author\"," +
                "\"publicationYear\": 2023," +
                "\"isbn\": \"978-1-23456-789-1\"" +
                "}";


        ResultActions result = mockMvc.perform(put("/api/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedBookJson));


        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Book Title"))
                .andExpect(jsonPath("$.author").value("Updated Author"))
                .andExpect(jsonPath("$.publicationYear").value(2023))
                .andExpect(jsonPath("$.isbn").value("978-1-23456-789-1"));
    }
    @Test
    public void deleteBook ()throws Exception{
        bookService.create(new Book((long) 1,"Sample Book", "John Doe", 2024, "978-1-23456-789-0"));

        ResultActions result = mockMvc.perform(delete("http://localhost/api/books/1"));
        result.andExpect(status().isOk());
    }
    @Test
    public void deleteBookNotFound ()throws Exception{
        ResultActions result = mockMvc.perform(delete("http://localhost/api/books/50"));
        result.andExpect(status().isNotFound());
    }
    @Test
    public void getByIdNotFound ()throws Exception{
        ResultActions result = mockMvc.perform(get("http://localhost/api/books/50"));
        result.andExpect(status().isNotFound());
    }
    @Test
    public void updateByIdNotFound ()throws Exception{
        String updatedBookJson = "{" +
                "\"title\": \"Updated Book Title\"," +
                "\"author\": \"Updated Author\"," +
                "\"publicationYear\": 2023," +
                "\"isbn\": \"978-1-23456-789-1\"" +
                "}";
        ResultActions result = mockMvc.perform(put("/api/books/{id}", 50L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedBookJson));

        result.andExpect(status().isNotFound());
    }



}

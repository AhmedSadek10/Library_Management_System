package com.example.Library.Controllers;

import com.example.Library.Models.Book;
import com.example.Library.Models.BorrowingRecord;
import com.example.Library.Models.Patron;
import com.example.Library.Repository.BookRepository;
import com.example.Library.Repository.BorrowingRecordRepository;
import com.example.Library.Repository.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BorrowingRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;
    @BeforeEach
    public void setup() {
        Book book = bookRepository.save(new Book( 1L ,"Test Book", "Test Author", 2021, "1234567890"));
        Patron patron = patronRepository.save(new Patron(1L,"Test Patron", "123456789"));
    }
    @Test
    public void borrowSuccess() throws Exception{
        Long bookId = bookRepository.findAll().get(0).getId();
        Long patronId = patronRepository.findAll().get(0).getId();
        mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId))
                .andExpect(status().isOk());

    }
    @Test
    public void borrowFail() throws Exception{
        mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", 500, 500))
                .andExpect(status().isNotFound());

    }
    @Test
    public void returnSuccess() throws Exception{
        Book book = bookRepository.findAll().get(0);
        Patron patron = patronRepository.findAll().get(0);
        BorrowingRecord borrowingRecord = new BorrowingRecord(1L , book , patron , LocalDate.now() , LocalDate.MIN);
        borrowingRecordRepository.save(borrowingRecord);
        mockMvc.perform(put("/api/return/{bookId}/patron/{patronId}", book.getId(), patron.getId()))
                .andExpect(status().isOk());

    }
    @Test
    public void returnFail() throws Exception{
        mockMvc.perform(put("/api/return/{bookId}/patron/{patronId}", 500, 500))
                .andExpect(status().isNotFound());
    }
}

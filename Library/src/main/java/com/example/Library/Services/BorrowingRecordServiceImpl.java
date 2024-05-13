package com.example.Library.Services;

import com.example.Library.Exceptions.BookNotFound;
import com.example.Library.Exceptions.PatronNotFound;
import com.example.Library.Models.Book;
import com.example.Library.Models.BorrowingRecord;
import com.example.Library.Models.Patron;
import com.example.Library.Repository.BookRepository;
import com.example.Library.Repository.BorrowingRecordRepository;
import com.example.Library.Repository.PatronRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {
   @Autowired
    private BookRepository bookRepository;
   @Autowired
    private PatronRepository patronRepository;
   @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;


    @Override
    @Transactional
    public void borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFound("Book Id is wrong"));
        Patron patron = patronRepository.findById(patronId).orElseThrow(() -> new PatronNotFound("Patron Id is wrong"));
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecordRepository.save(borrowingRecord);
    }

    @Override
    @Transactional
    public void returnBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFound("Book Id is wrong"));
        Patron patron = patronRepository.findById(patronId).orElseThrow(() -> new PatronNotFound("Patron Id is wrong"));
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookAndPatron(book , patron);
        borrowingRecord.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(borrowingRecord);
    }
}

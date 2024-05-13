package com.example.Library.Repository;

import com.example.Library.Models.Book;
import com.example.Library.Models.BorrowingRecord;
import com.example.Library.Models.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository <BorrowingRecord , Long> {
    BorrowingRecord findByBookAndPatron(Book book, Patron patron);
}

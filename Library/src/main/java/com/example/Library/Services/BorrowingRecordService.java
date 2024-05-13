package com.example.Library.Services;

public interface BorrowingRecordService {
    void borrowBook(Long bookId , Long PatronId);
    void returnBook(Long bookId , Long patronId);
}


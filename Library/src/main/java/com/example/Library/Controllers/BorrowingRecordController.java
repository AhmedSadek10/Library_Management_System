package com.example.Library.Controllers;

import com.example.Library.Exceptions.BookNotFound;
import com.example.Library.Exceptions.PatronNotFound;
import com.example.Library.Services.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {
    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        try {
            borrowingRecordService.borrowBook(bookId, patronId);
            return new ResponseEntity<>("Book Borrowed" ,HttpStatus.OK);
        }
        catch (BookNotFound bookNotFound){
            return new ResponseEntity<>(bookNotFound.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (PatronNotFound patronNotFound){
            return new ResponseEntity<>(patronNotFound.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        try {
            borrowingRecordService.returnBook(bookId, patronId);
            return new ResponseEntity<>("Book returned successfully", HttpStatus.OK);
        }
        catch (BookNotFound bookNotFound){
            return new ResponseEntity<>(bookNotFound.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (PatronNotFound patronNotFound){
            return new ResponseEntity<>(patronNotFound.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}

package com.example.Library.Controllers;

import com.example.Library.Exceptions.BookNotFound;
import com.example.Library.Models.Book;
import com.example.Library.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController (BookService bookService){
        this.bookService = bookService;
    }
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = bookService.getAll();
        return new ResponseEntity<>(books , HttpStatus.OK);
    }
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        try {
            Book book = bookService.findById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        catch (BookNotFound bookNotFound){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/books")
    public ResponseEntity<Book> createBook (@RequestBody Book book){
        return new ResponseEntity<>(bookService.create(book) , HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> update(@RequestBody Book book , @PathVariable Long id){
        try {
            return new ResponseEntity<>(bookService.update(book, id), HttpStatus.OK);
        }
        catch (BookNotFound bookNotFound){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> delete (@PathVariable Long id){
        try {
            bookService.deleteById(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        catch (BookNotFound bookNotFound){
            return new ResponseEntity<>( bookNotFound.getMessage() , HttpStatus.NOT_FOUND);
        }
    }
}

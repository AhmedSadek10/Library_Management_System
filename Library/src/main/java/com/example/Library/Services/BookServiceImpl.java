package com.example.Library.Services;

import com.example.Library.Exceptions.BookNotFound;
import com.example.Library.Models.Book;
import com.example.Library.Repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAll() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    @Override
    public Book findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFound("Book Not Found"));
        return book;
    }

    @Override
    @Transactional
    public Book create(Book book) {
        bookRepository.save(book);
        return book;
    }

    @Override
    @Transactional
    public Book update(Book book , Long id) {
        Book updatedBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFound("Book Not Found"));
        updatedBook.setTitle(book.getTitle());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setIsbn(book.getIsbn());
        updatedBook.setPublicationYear(book.getPublicationYear());
        bookRepository.save(updatedBook);
        return updatedBook;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFound("Book Not Found"));
        bookRepository.delete(book);
    }

}

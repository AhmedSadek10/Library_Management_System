package com.example.Library.Services;

import com.example.Library.Models.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book findById(Long id);

    Book create(Book book);

    Book update(Book book , Long id);
    void deleteById(Long id);

}

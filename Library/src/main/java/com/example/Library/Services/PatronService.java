package com.example.Library.Services;

import com.example.Library.Models.Book;
import com.example.Library.Models.Patron;

import java.util.List;

public interface PatronService {
    List<Patron> getAll();

    Patron findById(Long id);

    Patron create(Patron patron);

    Patron update(Patron patron , Long id);
    void deleteById(Long id);
}

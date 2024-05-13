package com.example.Library.Services;

import com.example.Library.Exceptions.PatronNotFound;
import com.example.Library.Models.Book;
import com.example.Library.Models.Patron;
import com.example.Library.Repository.PatronRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatronServiceImpl implements PatronService {
    @Autowired
    private PatronRepository patronRepository;
    @Override
    public List<Patron> getAll() {
        List<Patron> patrons = patronRepository.findAll();
        return patrons;
    }

    @Override
    public Patron findById(Long id) {
        Patron patron = patronRepository.findById(id).orElseThrow(() -> new PatronNotFound("Patron Not Found"));
        return patron;
    }

    @Override
    @Transactional
    public Patron create(Patron patron) {
        patronRepository.save(patron);
        return patron;
    }

    @Override
    @Transactional
    public Patron update(Patron patron, Long id) {
        Patron updatedPatron = patronRepository.findById(id).orElseThrow(() -> new PatronNotFound("Patron Not Found"));
        updatedPatron.setName(patron.getName());
        updatedPatron.setContactInformation(patron.getContactInformation());
        patronRepository.save(updatedPatron);
        return updatedPatron;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Patron patron = patronRepository.findById(id).orElseThrow(() -> new PatronNotFound("Patron Not Found"));
        patronRepository.delete(patron);
    }
}

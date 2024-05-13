package com.example.Library.Controllers;

import com.example.Library.Exceptions.PatronNotFound;
import com.example.Library.Models.Patron;
import com.example.Library.Services.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PatronController {
    @Autowired
    private PatronService patronService;

    @GetMapping("/patrons")
    public ResponseEntity<List<Patron>> getAll(){
        List<Patron> patrons = patronService.getAll();
        return new ResponseEntity<>(patrons , HttpStatus.OK);
    }
    @GetMapping("/patrons/{id}")
    public ResponseEntity<Patron> getById ( @PathVariable long id){
        try{
            Patron patron = patronService.findById(id);
            return new ResponseEntity<>(patron , HttpStatus.OK);
        }
        catch (PatronNotFound patronNotFound) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/patrons")
    public ResponseEntity<Patron> create (@RequestBody Patron patron){

        return new ResponseEntity<>(patronService.create(patron) , HttpStatus.CREATED);
    }
    @PutMapping("/patrons/{id}")
    public ResponseEntity<Patron> update(@RequestBody Patron patron , @PathVariable Long id){
        try {
            return new ResponseEntity<>(patronService.update(patron, id), HttpStatus.OK);
        }
        catch (PatronNotFound patronNotFound){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/patrons/{id}")
    public ResponseEntity<String> delete (@PathVariable Long id){
        try {
            patronService.deleteById(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        catch (PatronNotFound patronNotFound){
            return new ResponseEntity<>( patronNotFound.getMessage() , HttpStatus.NOT_FOUND);
        }
    }
}

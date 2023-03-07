package com.kgisl.springBootDemo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.kgisl.springBootDemo.model.Person;
import com.kgisl.springBootDemo.repository.PersonRepository;
import com.kgisl.springBootDemo.service.PersonService;

@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonService personService;

    @GetMapping("/person")
    public @ResponseBody ResponseEntity<List<Person>> getAll() {
        return new ResponseEntity<>(personService.getPersonDetails(), HttpStatus.OK);
    }

    @GetMapping("/getAllDetails")
    public List<Person> getAllDetails() {
        List<Person> person = new ArrayList<Person>();
        personRepository.findAll().forEach(person1 -> person.add(person1));
        return person;

    }

    @GetMapping(value = "/personId/{id}")
    public ResponseEntity<Person> getTeamById(@PathVariable("id") int id) {
        Person user = personService.findByPersonId(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/person", headers = "Accept=application/json")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person actualBook = personService.createPerson(person);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(actualBook, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/personDetailsBy/{id}", headers = "Accept=application/json")
    public ResponseEntity<Person> updateUser(@PathVariable("id") int id, @RequestBody Person person) {
        Person persons=personService.updatePerson(id,person);
        return new ResponseEntity<>(persons,HttpStatus.OK);
    }

    @DeleteMapping(value = "/person/{id}", headers = "Accept=application/json")
    public ResponseEntity<Person> deleteUser(@PathVariable("id") int id) {
        Person user = personService.findByPersonId(id);
       if (user == null) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       personService.deletePersonById(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //Query method
    @GetMapping("/queryName/{name}")
    public ResponseEntity<?> findByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(personRepository.findByName(name), HttpStatus.OK);
    }

    @GetMapping(value = "/findByNameCount/{name}")
    public ResponseEntity<?> findByNameCount(@PathVariable("name") String name) {
        return new ResponseEntity<>(personRepository.findByNameCount(name), HttpStatus.OK);

    }

    @GetMapping(value = "/findByCount/")
    public ResponseEntity<?> findByCount() {
        return new ResponseEntity<>(personRepository.findByCount(), HttpStatus.OK);

    }

    //derived query method
    @GetMapping(value = "/personByEmail/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable("email") String email) {
        try {
            Person user = personRepository.findByEmail(email);

            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // only sorting
    @GetMapping("/person/{field}")
    public ResponseEntity<List<Person>> getSortedBy(@PathVariable String field) {
        List<Person> getSize = personRepository.findAll(Sort.by(Sort.Direction.DESC, field));
        return new ResponseEntity<>(getSize, HttpStatus.OK);
    }

    // only pagination
    @GetMapping("/person/pagination/{offSet}/{pageSize}")
    public ResponseEntity<Page<Person>> getDetailsWithPagination(@PathVariable int offSet, @PathVariable int pageSize) {
        Page<Person> person = personRepository.findAll(PageRequest.of(offSet, pageSize));
        return new ResponseEntity<>(person, HttpStatus.ACCEPTED);

    }

    // sorting and pagination
    @GetMapping("/person/PaginationAndSorting/{offSet}/{pageSize}/{field}")
    public ResponseEntity<Page<Person>> getDetailsWithPaginationAndSorting(@PathVariable int offSet,
            @PathVariable int pageSize, @PathVariable String field) {
        Page<Person> person = personRepository
                .findAll(PageRequest.of(offSet, pageSize).withSort(Sort.by(Sort.Direction.DESC, field)));
        return new ResponseEntity<>(person, HttpStatus.OK);

    }

}

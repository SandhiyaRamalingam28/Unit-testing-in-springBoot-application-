package com.kgisl.springBootDemo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgisl.springBootDemo.model.Person;
import com.kgisl.springBootDemo.repository.PersonRepository;

@Service
@Transactional

public class PersonServiceImpl implements PersonService{

    @Autowired
    PersonRepository personRepository;

    @Override
    public Person createPerson(Person person) {
        return personRepository.save(person);
        }

    @Override
    public List<Person> getPersonDetails() {
            return personRepository.findAll();
    }

    @Override
    public Person findByPersonId(int id) {
            return personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found"));
    }

    @Override
    public Person updatePerson(int id, Person person) {
        Person b = personRepository.findById(id).orElse(new Person());
        b.setId(person.getId());
        b.setName(person.getName());
        b.setEmail(person.getEmail());
        b.setPhoneNumber(person.getPhoneNumber());
        return personRepository.save(b);
    }

    @Override
    public void deletePersonById(int id) {
        personRepository.deleteById(id);
    }
    
}

package com.kgisl.springBootDemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kgisl.springBootDemo.model.Person;

@Service
public interface PersonService {
    public Person createPerson(Person person);
    public List<Person> getPersonDetails();
    public Person findByPersonId(int id);
    public Person updatePerson(int id,Person person);
    public void deletePersonById(int id); 

    
}

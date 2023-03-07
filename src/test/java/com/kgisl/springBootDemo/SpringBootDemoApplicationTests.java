package com.kgisl.springBootDemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kgisl.springBootDemo.controller.PersonController;
import com.kgisl.springBootDemo.model.Person;
import com.kgisl.springBootDemo.service.PersonService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SpringBootDemoApplicationTests {

    @Mock
    PersonService personService;

    @InjectMocks
    PersonController personController;

    // @Mock
	  // PersonController personController;

	public static List<Person> expected;
	Person person1 = new Person();
	Person person2 = new Person();

  //Test case without responseEntity
	// @Test
	// public void allDetailsTest(){
	// 	expected = Arrays.asList(person1,person2);
	// 	System.out.println(expected);
	// 	List<Person> actual = personController.getAllDetails();
	// 	assertNotNull(actual);
	// } 

  // test cases using responseEntity
  @Test
  public void allTest() {

    expected = Arrays.asList(person1, person2);
    System.out.println(expected);
    when(personService.getPersonDetails()).thenReturn(expected);
    ResponseEntity<List<Person>> actual = personController.getAll();
    assertNotNull(actual);
    assertEquals(expected, actual.getBody());
    assertEquals(HttpStatus.OK, actual.getStatusCode());
  }

  @Test
  public void getBookByIdTest() {
    int id = 1;
    when(personService.findByPersonId(id)).thenReturn(null);
    ResponseEntity<Person> actual = personController.getTeamById(id);
    assertNotNull(actual);
  }

  @Test
  public void createBookTest() {
    when(personService.createPerson(person1)).thenReturn(person1);
    personController.createPerson(person1);
  }

  @Test
  public void updateBookTest() {
    int id = 1;
    ResponseEntity<Person> actual = personController.updateUser(id, person1);
    assertNotNull(actual);
    System.out.println("Actual is  "+actual.getBody());
    System.out.println("expected-->" + expected);
  }

  @Test
  public void deleteBookTest() {
    int id = 1;
    when(personService.findByPersonId(id)).thenReturn(person1);
    personController.deleteUser(id);
    verify(personService).deletePersonById(id);
  }


}

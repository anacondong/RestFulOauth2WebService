/**
 * 
 */
package com.ws.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ws.entity.Person;

/**
 * @author Ittipol
 *
 */
@RestController
@RequestMapping("/persons")
public class PersonRestController {
	
	@RequestMapping("")
	public List<Person> personHandler(){
		return createSamplePerson();
	}
	
	@RequestMapping("/{id}")
	public Person personByIdHandler(@PathVariable Integer id){
		return createSamplePerson().get(id);
	}
	
	
	private List<Person> createSamplePerson(){
		List<Person> lists = new ArrayList<Person>();
		lists.add(new Person("Email1", "name1"));
		lists.add(new Person("Email2", "name2"));
		lists.add(new Person("Email3", "name3"));
		lists.add(new Person("Email4", "name4"));
		return lists;
	}
}

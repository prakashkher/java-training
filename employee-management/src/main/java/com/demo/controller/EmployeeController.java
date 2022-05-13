package com.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Employee;
import com.demo.service.EmployeeService;

@RestController
@RequestMapping(value="employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	//GET to get employees
	@RequestMapping(value="",method=RequestMethod.GET)
	public ResponseEntity<List<Employee>> getAllEmployees(){
		System.out.println("Get all employees");
		return new ResponseEntity<List<Employee>>(employeeService.getAllEmployees(), HttpStatus.OK);
	}
	
	//POST to create new employee
	@RequestMapping(value="", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity createEmployee(@RequestBody Employee employee){
		System.out.println("create new employee");
		employeeService.createEmployee(employee);
		return new ResponseEntity<>("Employee created successfully", HttpStatus.CREATED);
	}
	
	//PUT to update employee details
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateEmployee(@PathVariable(name="id") Integer id, @RequestBody Employee employee){
		System.out.println("update employee");
		//get employee details to be updated from db
		Optional<Employee> currentEmployee = employeeService.findById(id);
		if(currentEmployee.isPresent()){
			//found employee details
			Employee currentEmployeeDetails = currentEmployee.get();
			currentEmployeeDetails.setName(employee.getName());
			currentEmployeeDetails.setAge(employee.getAge());
			currentEmployeeDetails.setAddress(employee.getAddress());
			currentEmployeeDetails.setSalary(employee.getSalary());
			employeeService.updateEmployee(currentEmployeeDetails);
		} else {
			//employee does not exist for provided id
			employeeService.updateEmployee(employee);
		}
		return new ResponseEntity<>("Employee updated successfully", HttpStatus.OK);
	}
	
	//DELETE to delete employee from db
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity deleteEmployee(@PathVariable(name="id") Integer id){
		System.out.println("Inside delete employee API");
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
		
	}
}

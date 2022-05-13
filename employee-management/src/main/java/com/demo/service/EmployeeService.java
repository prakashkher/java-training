package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Employee;
import com.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees(){
		
		//call DAO layer to get all employees
		return employeeRepository.findAll();
		
	}

	public void createEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	public Optional<Employee> findById(Integer id) {
		return employeeRepository.findById(id);
	}

	public void updateEmployee(Employee currentEmployeeDetails) {
		employeeRepository.save(currentEmployeeDetails);
	}

	public void deleteEmployee(Integer id) {
		Optional<Employee> employee = findById(id);
		if (employee.isPresent()) {
			employeeRepository.delete(employee.get());	
		}
	}
}

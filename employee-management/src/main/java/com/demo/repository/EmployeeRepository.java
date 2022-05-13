package com.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

	public List<Employee> findAll();
	public Optional<Employee> findById(Integer id);
	public List<Employee> findByAge(Integer age);
}

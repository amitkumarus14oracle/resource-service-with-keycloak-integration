package com.restart.auth.authenticationservice.repository;

import com.restart.auth.authenticationservice.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findByEmail(String email);// It uses Derived Method Name Query
}

package com.restart.auth.authenticationservice.controller;

import com.restart.auth.authenticationservice.dto.EmployeeResponseDTO;
import com.restart.auth.authenticationservice.model.Employee;
import com.restart.auth.authenticationservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeRepository repository;

    @GetMapping("/employee")
    public ResponseEntity<EmployeeResponseDTO>/*Employee*/ getEmployee(/*@RequestParam String email*/) {
        /*Employee e = repository.findByEmail(email);
        if(e != null) {
            return e;
        }
        return null;*/
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO("Amit");
        return ResponseEntity.ok().body(responseDTO);
    }
}

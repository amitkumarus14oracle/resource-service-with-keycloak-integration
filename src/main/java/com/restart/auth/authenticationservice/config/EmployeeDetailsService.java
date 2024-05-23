package com.restart.auth.authenticationservice.config;

import com.restart.auth.authenticationservice.model.Employee;
import com.restart.auth.authenticationservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String usrname, password = null;
        List<GrantedAuthority> authorities = null;
        Employee emp = employeeRepository.findByEmail(username);
        if(emp == null) {
            throw new UsernameNotFoundException("User details not found for the user: "+username);
        } else {
            usrname = emp.getEmail();
            password = emp.getPassword();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(emp.getRole()));
        }
        return new User(usrname, password, authorities);
    }
}

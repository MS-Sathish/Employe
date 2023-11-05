package com.example.EmployeManagement.Service;

import com.example.EmployeManagement.Entity.Employe;
import com.example.EmployeManagement.Model.EmployeDetails;
import com.example.EmployeManagement.Model.LoginReuqest;
import com.example.EmployeManagement.Repository.EmployeRepo;
import com.example.EmployeManagement.Utility.EmployeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepo employeRepo;
    @Autowired
    private EmployeUtility employeUtility;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<Employe> getAll() {
        return employeRepo.findAll();
    }

    @Transactional
    public void updateEmploye(Long id, EmployeDetails employe) {
        Optional<Employe> existingEmployee = employeRepo.findById(id);
        if (existingEmployee.isPresent()) {
            Employe employe1 = existingEmployee.get();
            employe1.setName(employe.getName());
            employe1.setEmail(employe.getEmail());
            employe1.setPassword(passwordEncoder.encode(employe.getPassword()));
            employeRepo.save(employe1);
        } else {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
    }
    @Transactional
    public void deleteEmploye(Long id) {
        Optional<Employe> existingEmployee = employeRepo.findById(id);
        if (existingEmployee.isPresent()) {
            employeRepo.deleteById(id);
        } else {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
    }
    public Optional<Employe> getEmployeeById(Long id) {

        return employeRepo.findById(id);
    }
    @Transactional
    public void addEmploye(EmployeDetails employe) {
        Employe employe1 = new Employe();
        employe1.setName(employe.getName());
        employe1.setEmail(employe.getEmail());
        employe1.setPassword(passwordEncoder.encode(employe.getPassword()));
        employeRepo.save(employe1);
    }
    public String userLogin(LoginReuqest loginRequest) {

       Employe employe = employeRepo.findByEmail(loginRequest.getEmail());
        if (employe == null) {
            throw new RuntimeException("User not found with given email Id");
        }
        return employeUtility.generate(employe);
    }
}

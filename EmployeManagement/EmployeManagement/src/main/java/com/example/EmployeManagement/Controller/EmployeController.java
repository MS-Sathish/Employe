package com.example.EmployeManagement.Controller;

import com.example.EmployeManagement.Entity.Employe;
import com.example.EmployeManagement.Model.EmployeDetails;
import com.example.EmployeManagement.Model.LoginReuqest;
import com.example.EmployeManagement.Service.EmployeService;
import com.example.EmployeManagement.Utility.EmployeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeController.class);

    @Autowired
    private EmployeService employeService;
    @Autowired
    private EmployeUtility employeUtility;

    @GetMapping("/employe")
    public ResponseEntity<?> getAll() throws Exception {
        logger.info("Fetching all employees");
        try {
            List<Employe> employes = employeService.getAll();
            return new ResponseEntity<>(employes, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while fetching all employees", e);
            return new ResponseEntity<>("Failed to fetch employees", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEmploye(@RequestBody EmployeDetails employe) {
        try {
            logger.info("Adding employee: {}", employe);
            employeService.addEmploye(employe);
            return new ResponseEntity<>("Employee added successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while adding an employee", e);
            return new ResponseEntity<>("Failed to add employee Email already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginReuqest loginReuqest) {
        try {
            String token = employeService.userLogin(loginReuqest);
            logger.info("User logged in successfully");
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred during login", e);
            return new ResponseEntity<>("Failed to log in", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmploye(@RequestBody EmployeDetails employe, @PathVariable Long id) {
        try {
            logger.info("Updating employee with ID {}: {}", id, employe);
            employeService.updateEmploye(id, employe);
            return new ResponseEntity<>("Employee updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while updating an employee", e);
            return new ResponseEntity<>("Failed to update employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmploye(@PathVariable Long id) {
        try {
            logger.info("Deleting employee with ID: {}", id);
            employeService.deleteEmploye(id);
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while deleting an employee", e);
            return new ResponseEntity<>("Failed to delete employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employe/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        try {
            logger.info("Fetching employee by ID: {}", id);
            Optional<Employe> employe = employeService.getEmployeeById(id);
            if (employe.isPresent()) {
                return new ResponseEntity<>(employe.get(), HttpStatus.OK);
            } else {
                logger.warn("Employee with ID {} not found", id);
                return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("An error occurred while fetching an employee by ID", e);
            return new ResponseEntity<>("Failed to fetch employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

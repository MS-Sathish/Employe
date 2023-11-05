package com.example.EmployeManagement.Repository;

import com.example.EmployeManagement.Entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeRepo extends JpaRepository<Employe,Long> {
    Employe findByEmail(String email);
}

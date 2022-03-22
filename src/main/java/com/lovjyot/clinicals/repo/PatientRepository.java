package com.lovjyot.clinicals.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lovjyot.clinicals.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}

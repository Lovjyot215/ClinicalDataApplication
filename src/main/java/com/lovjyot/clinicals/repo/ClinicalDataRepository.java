package com.lovjyot.clinicals.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lovjyot.clinicals.model.ClinicalData;

public interface ClinicalDataRepository extends JpaRepository<ClinicalData, Integer> {

	List<ClinicalData> findByPatientIdAndComponentNameOrderByMeasuredDateTime(int patientId, String componentName);

}

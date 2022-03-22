package com.lovjyot.clinicals.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lovjyot.clinicals.dto.ClinicalDataRequest;
import com.lovjyot.clinicals.model.ClinicalData;
import com.lovjyot.clinicals.model.Patient;
import com.lovjyot.clinicals.repo.ClinicalDataRepository;
import com.lovjyot.clinicals.repo.PatientRepository;
import com.lovjyot.clinicals.util.BMICalculator;

@RestController
@RequestMapping("/api")
public class ClinicalDataController {

	private ClinicalDataRepository clinicalDataRepository;
	private PatientRepository patientRepository;

	ClinicalDataController(ClinicalDataRepository clinicalDataRepository, PatientRepository patientRepository) {
		this.clinicalDataRepository = clinicalDataRepository;
		this.patientRepository = patientRepository;
	}

	@RequestMapping(value = "/clinicalData", method = RequestMethod.POST)
	public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest clinicalDataRequest) {
		Patient patient = patientRepository.findById(clinicalDataRequest.getPatientId()).get();
		ClinicalData clinicalData = new ClinicalData();
		clinicalData.setComponentName(clinicalDataRequest.getComponentName());
		clinicalData.setComponentValue(clinicalDataRequest.getComponentValue());
		clinicalData.setPatient(patient);
		return clinicalDataRepository.save(clinicalData);

	}

	@RequestMapping(value = "/clinicalData/{patientId}/{componentName}", method = RequestMethod.GET)
	public List<ClinicalData> getClinicalData(@PathVariable("patientId") int patientId,
			@PathVariable("componentName") String componentName) {
		
		if(componentName.equalsIgnoreCase("bmi")) {
			componentName="hw";
		}

		List<ClinicalData> clinicalData = clinicalDataRepository
				.findByPatientIdAndComponentNameOrderByMeasuredDateTime(patientId, componentName);
		
		List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
		for (ClinicalData eachEntry : duplicateClinicalData) {
			BMICalculator.calculateBMI(clinicalData, eachEntry);
		}
		
		return clinicalData;

	}

}

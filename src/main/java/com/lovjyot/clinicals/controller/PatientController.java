package com.lovjyot.clinicals.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lovjyot.clinicals.model.ClinicalData;
import com.lovjyot.clinicals.model.Patient;
import com.lovjyot.clinicals.repo.PatientRepository;
import com.lovjyot.clinicals.util.BMICalculator;

@RestController
@RequestMapping("/api")
public class PatientController {

	private PatientRepository patientRepository;

	Map<String, String> filters = new HashMap<>();

	PatientController(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@RequestMapping(value = "/patients", method = RequestMethod.GET)
	public List<Patient> getPatients() {
		return patientRepository.findAll();

	}

	@RequestMapping(value = "/patients/{id}", method = RequestMethod.GET)
	public Patient getPatient(@PathVariable("id") int id) {
		return patientRepository.findById(id).get();
	}

	@RequestMapping(value = "/patients", method = RequestMethod.POST)
	public Patient savePatient(@RequestBody Patient patient) {
		return patientRepository.save(patient);
	}

	@RequestMapping(value = "/patients/analyze/{id}", method = RequestMethod.GET)
	public Patient analyze(@PathVariable("id") int id) {

		Patient patient = patientRepository.findById(id).get();
		List<ClinicalData> clinicalData = patient.getClinicalData();
		List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
		for (ClinicalData eachEntry : duplicateClinicalData) {

			if (filters.containsKey(eachEntry.getComponentName())) {
				clinicalData.remove(eachEntry);
				continue;
			} else {
				filters.put(eachEntry.getComponentName(), null);
			}
			BMICalculator.calculateBMI(clinicalData, eachEntry);
		}
		filters.clear();
		return patient;
	}


}

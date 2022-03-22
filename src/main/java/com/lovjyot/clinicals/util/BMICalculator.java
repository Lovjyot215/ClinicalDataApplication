package com.lovjyot.clinicals.util;

import java.util.List;

import com.lovjyot.clinicals.model.ClinicalData;

public class BMICalculator {
	
	public static void calculateBMI(List<ClinicalData> clinicalData, ClinicalData eachEntry) {
		if (eachEntry.getComponentName().equals("hw")) {
			String[] heightandweight = eachEntry.getComponentValue().split("/");
			if (heightandweight != null && heightandweight.length > 1) {
				float heightInMetres = Float.parseFloat(heightandweight[0]) * 0.4536F;
				float bmi = Float.parseFloat(heightandweight[1]) / heightInMetres * heightInMetres;
				ClinicalData bmiData = new ClinicalData();
				bmiData.setComponentName("bmi");
				bmiData.setComponentValue(Float.toString(bmi));
				clinicalData.add(bmiData);
			}
		}
	}

}

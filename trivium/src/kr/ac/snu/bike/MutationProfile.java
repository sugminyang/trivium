package kr.ac.snu.bike;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import kr.ac.snu.bike.model.ClinicalTrial;

public class MutationProfile {

	public Map<String,ClinicalTrial> readMutationProfile(String mutationProfilePath) {
		BufferedReader in = null;
		Map<String,ClinicalTrial> mapMutationProfile = new HashMap<>();
		
		try {
			in = new BufferedReader(new FileReader(mutationProfilePath));
			
			String line = "";
			boolean header = true;
			//Patient ID	Overall Survival (Months)	TMB Score	Cancer Type	Cancer Type Detailed	Oncotree Code	Sample Type	Drug Type	Metastatic Site	Fusion	Hugo_Symbol
			while((line=in.readLine()) != null)	{
				if(header) {
					header = false;
//					System.out.println(line);
					continue;
				}
				
				String[] item = line.split("\t");
//				System.out.println(line);
				String patientID = item[0];
				String fusion = item[9];
				String gene = item[10];
				
				if(mapMutationProfile.containsKey(patientID))	{	//exist
					ClinicalTrial patient = mapMutationProfile.get(patientID);
					patient.setHugo_Symbol(gene);
					patient.setFusion(fusion);
					mapMutationProfile.put(patientID, patient);
				}
				else	{	//doesn't exist
					ClinicalTrial patient = new ClinicalTrial(line);
					mapMutationProfile.put(patientID, patient);
				}
				
			}
			
			in.close();
			
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mapMutationProfile;
	}

}

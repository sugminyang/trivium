package kr.ac.snu.bike;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import kr.ac.snu.bike.model.ClinicalTrial;
import kr.ac.snu.bike.model.GenieEnriciment;

public class EnrichmentProfile {

	public Map<String, GenieEnriciment> readEnrichmentProfile(String enrichmentProfilePath) {
		Map<String, GenieEnriciment> mapEnrichProfile = new HashMap<>();
		
		BufferedReader in = null;
		Map<String,ClinicalTrial> mapMutationProfile = new HashMap<>();
		
		try {
			in = new BufferedReader(new FileReader(enrichmentProfilePath));
			
			String line = "";
			boolean header = true;
			//Patient	Type	Gene	Cytoband	Samples with alteration in altered group	Samples with alteration in unaltered group	Log Ratio	p-Value	q-Value	Tendency																																																																																																																																																																																																																																																						
			while((line=in.readLine()) != null)	{
				if(header) {
					header = false;
					System.out.println(line);
					continue;
				}
				
				String[] item = line.split("\t");
				String patientID = item[0];
				String gene = item[2];
				
				if(mapEnrichProfile.containsKey(patientID))	{	//exist
					GenieEnriciment patient = mapEnrichProfile.get(patientID);
					patient.setHugo_Symbol(gene);
					mapEnrichProfile.put(patientID, patient);
				}
				else	{	//doesn't exist
					GenieEnriciment patient = new GenieEnriciment(patientID,gene);
					mapEnrichProfile.put(patientID, patient);
				}
				
			}
			
			in.close();
			
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return mapEnrichProfile;
	}

}

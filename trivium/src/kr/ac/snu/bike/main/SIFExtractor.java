package kr.ac.snu.bike.main;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import kr.ac.snu.bike.EnrichmentProfile;
import kr.ac.snu.bike.MutationProfile;
import kr.ac.snu.bike.PathwayCommonsExtractor;
import kr.ac.snu.bike.model.ClinicalTrial;
import kr.ac.snu.bike.model.GenieEnriciment;

public class SIFExtractor {

	public static void main(String[] args) {
		
//		if(args.length != 2)	{
//			System.out.println("Invalid parameter ... e.g> java -jar PathwayCommonsExtractor.jar inputFile outputFile");
//			System.exit(1);	
//		}
		String mutationProfilePath = "./pathwaycommons/M_Breast_mutation_profile.txt";
		String enrichmentProfilePath = "./pathwaycommons/M_Breast_enrichment.txt";
		String output = "./pathwaycommons/output/output_M_breast_0423.txt";
		
		Map<String, LinkedHashSet<String>> mapGenebyPatient = new HashMap<String,LinkedHashSet<String>>();
		
		MutationProfile mutationProfile = new MutationProfile();
		Map<String,ClinicalTrial> mapMutProfile = mutationProfile.readMutationProfile(mutationProfilePath);

		EnrichmentProfile enrichmentProfile = new EnrichmentProfile();
		Map<String,GenieEnriciment> mapEnrichProfile = enrichmentProfile.readEnrichmentProfile(enrichmentProfilePath);
		
		mapGenebyPatient = mergeGeneByPatient(mapMutProfile,mapEnrichProfile);
		
		//mutation profile
		for(String pat : mapGenebyPatient.keySet())	{
			System.out.println(pat + "\t"+mapGenebyPatient.get(pat));
		}
		
		PathwayCommonsExtractor pcExtractor = new PathwayCommonsExtractor();
		pcExtractor.run(mapGenebyPatient,output,mapMutProfile);
		
	}

	private static Map<String, LinkedHashSet<String>> mergeGeneByPatient(Map<String, ClinicalTrial> mapMutProfile,
			Map<String, GenieEnriciment> mapEnrichProfile) {
		Map<String, LinkedHashSet<String>> mapGenebyPatient = new HashMap<String,LinkedHashSet<String>>();
		
		//mutation profile
		for(String pat : mapMutProfile.keySet())	{
//			System.out.println(mapMutProfile.get(pat).getPatientID() + "\t" + mapMutProfile.get(pat).getSetHugo_Symbol());
			
			LinkedHashSet<String> genes = mapMutProfile.get(pat).getSetHugo_Symbol();
			mapGenebyPatient.put(pat, genes);
		}
		
		//enrichment profile
		for(String pat : mapEnrichProfile.keySet())	{
//			System.out.println(mapEnrichProfile.get(pat).getPatientID() + "\t" + mapEnrichProfile.get(pat).getSetHugo_Symbol());
			
			if(mapGenebyPatient.containsKey(pat))	{	//exist
				LinkedHashSet<String> genes = mapGenebyPatient.get(pat);
				
				for(String gene : mapEnrichProfile.get(pat).getSetHugo_Symbol())	{
					if(!genes.contains(gene))	{	//new gene!
						genes.add(gene);
					}
				}
				mapGenebyPatient.put(pat, genes);
			}
			else	{	//doesn't exist
				LinkedHashSet<String> genes = mapEnrichProfile.get(pat).getSetHugo_Symbol();
				mapGenebyPatient.put(pat, genes);
			}
		}
//		System.out.println();
//		System.out.println();
		return mapGenebyPatient;
	}

}

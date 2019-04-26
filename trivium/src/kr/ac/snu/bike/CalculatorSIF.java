package kr.ac.snu.bike;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.ac.snu.bike.model.ClinicalTrial;

public class CalculatorSIF {

	public static void calculatorSIF(String inputFile, String outputFile, Map<String, ClinicalTrial> mapMutProfile) throws IOException {
		Map<String,List<String>> mapSIF = new HashMap<>();
		String line = "";
		boolean header = true;

		BufferedReader in = new BufferedReader( new FileReader(inputFile));

		while((line=in.readLine()) != null)	{
			String[] item = line.split("\t");

			if(header)	{
				header = false;
				continue;
			}

			if(item.length != 2)	continue;	//patient_id relation  

			//			System.out.println(line);
			String patient_id = item[0];
			String sif_relation = item[1];


			if(mapSIF.containsKey(sif_relation))	{		//exsist sif relation.
				List<String> patients = mapSIF.get(sif_relation);
				boolean flag = false;
				for(String pat : patients)	{
					if(pat.equalsIgnoreCase(patient_id))	{
						flag = true;
						break;
					}
				}
				if(!flag)	{
					patients.add(patient_id);
					mapSIF.put(sif_relation, patients);
				}
			}
			else	{	//new sif relation.
				List<String> patients = new ArrayList<>();

				patients.add(patient_id);

				mapSIF.put(sif_relation, patients);
			}
		}

		in.close();

		writeResultBySIF(mapSIF,outputFile,mapMutProfile);
		writeResultByCytoscape(mapSIF,outputFile);
		writeResultBySharm(mapSIF,outputFile);
		
		
	}

	private static void writeResultBySharm(Map<String, List<String>> mapSIF, String outputFile) throws IOException {
		outputFile = outputFile.replace(".txt","_sharm.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
		
		
		out.append("patient_id\tsifs\n");
		Map<String, List<String>> patients = pivotData(mapSIF);
		
		for(String pat : patients.keySet())	{
			System.out.print(pat + "\t");
			out.append(pat + "\t");
			for(String sif : patients.get(pat)) {
				System.out.print(sif + "\t");
				out.append(sif + "\t");
			}
			System.out.println();
			out.append("\n");
		}

		out.close();
	}

	private static Map<String, List<String>> pivotData(Map<String, List<String>> mapSIF) {
		Map<String, List<String>> patients = new HashMap<>();

		for(String sif : mapSIF.keySet())	{

			for(String patient : mapSIF.get(sif)) {
				
				if(patients.containsKey(patient))	{	//exist
					List<String> temp = patients.get(patient);
					temp.add(sif);
					
					patients.put(patient,temp);
				}
				else	{	//dose not exist
					List<String> temp = new ArrayList<>();
					temp.add(sif);
					
					patients.put(patient,temp);
				}
			}
		}
		return patients;
	}

	private static void writeResultByCytoscape(Map<String, List<String>> mapSIF, String outputFile) throws IOException {
		outputFile = outputFile.replace(".txt", "_cytoscape.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));

		out.append("GeneA\tedgeType\tGeneB\trelation_sif\tcount\n");

		for(String sif : mapSIF.keySet())	{
			String[] entity = sif.split(" ");
			for(String ent : entity)	{
				out.append( ent + "\t"); 
			}

			out.append(sif + "\t"+mapSIF.get(sif).size());
			out.append("\n");
		}

		out.close();
	}

	private static void writeResultBySIF(Map<String, List<String>> mapSIF, String outputFile, Map<String, ClinicalTrial> mapMutProfile) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
		outputFile = outputFile.replace(".txt","_SurvivalAnalysis.txt");
		
		BufferedWriter out2 = new BufferedWriter(new FileWriter(outputFile));

		out.append("patient_id\trelation_sif\tcount\tos\tTMB\n");

		out2.append("sif\tcount\tsd_os\tmean_os\tsd_TMB\tmean_TMB\tOSs\telseOSs\tTMBs\telseTMBs\n");
		for(String sif : mapSIF.keySet())	{
			List<Double> patient_os = new ArrayList<>();
			List<Double> patient_tmb = new ArrayList<>();
			
			List<Double> else_patient_os = new ArrayList<>();
			List<Double> else_patient_tmb = new ArrayList<>();
			List<String> temp = getUnusedPatient(mapSIF.get(sif),mapMutProfile);
			
//			System.out.println("unused: " + temp.toString());
//			System.out.println("used: " + mapSIF.get(sif).toString());
			
			for(String patient : mapSIF.get(sif)) {
				ClinicalTrial clinInfo = mapMutProfile.get(patient);
				patient_os.add(clinInfo.getOverall_Survival());
				patient_tmb.add(clinInfo.getTMB_Score());
				out.append(patient + "\t" + sif + "\t"+mapSIF.get(sif).size() + "\t" + clinInfo.getOverall_Survival() +"\t" + clinInfo.getTMB_Score());
				out.append("\n");
			}
			
			for(String unusedPat : temp) {
				ClinicalTrial clinInfo = mapMutProfile.get(unusedPat);
				else_patient_os.add(clinInfo.getOverall_Survival());
				else_patient_tmb.add(clinInfo.getTMB_Score());
			}
			
			out2.append(sif + "\t"+patient_os.size() + "\t"+ calcSD(patient_os) + "\t" + calcMean(patient_os)+ "\t"+ calcSD(patient_tmb) + "\t" + calcMean(patient_tmb) + "\t"+ patient_os.toString() + "\t" + else_patient_os.toString() + "\t"+ patient_tmb.toString() + "\t" + else_patient_tmb.toString() + "\n");
		}

		out.close();
		out2.close();
	}

	private static List<String> getUnusedPatient(List<String> list, Map<String, ClinicalTrial> mapMutProfile) {
		List<String> allPatient = new ArrayList<>(mapMutProfile.keySet());
		
		for(String item : list) {
			allPatient.remove(item);
		}
		
		return allPatient;
	}

	private static double calcSD(List<Double> patient_os) {
		double sum = 0.0, standardDeviation = 0.0;
		int length = patient_os.size();

		for(double num : patient_os) {
			sum += num;
		}

		double mean = sum/length;

		for(double num: patient_os) {
			standardDeviation += Math.pow(num - mean, 2);
		}

		return Math.sqrt(standardDeviation/length);
	}

	private static double calcMean(List<Double> patient_os) {
		double avg = 0.0;
		
		for(double os : patient_os)	{
			avg += os;
		}
		
		return avg / patient_os.size();
	}
	
	
}

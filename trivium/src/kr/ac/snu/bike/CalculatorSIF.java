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

		out.append("patient_id\trelation_sif\tcount\tos\tTMB\n");

		for(String sif : mapSIF.keySet())	{
			for(String patient : mapSIF.get(sif)) {
				ClinicalTrial clinInfo = mapMutProfile.get(patient);
				out.append(patient + "\t" + sif + "\t"+mapSIF.get(sif).size() + "\t" + clinInfo.getOverall_Survival() +"\t" + clinInfo.getTMB_Score());
				out.append("\n");
			}
		}

		out.close();
	}
}

package kr.ac.snu.bike;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.ac.snu.bike.model.ClinicalTrial;

public class PathwayCommonsExtractor {

	public void run(Map<String, LinkedHashSet<String>> mapGenebyPatient,String outputFile,Map<String,ClinicalTrial> mapMutProfile) {
		
		try {
			//source=PTEN&source=TERT&source=EGFR&source=MET
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
			for(String patient : mapGenebyPatient.keySet())	{
				String query = "";
				
				LinkedHashSet<String> genes = mapGenebyPatient.get(patient);
				ArrayList<String> wrappedGenes = new ArrayList<>();
				wrappedGenes.addAll(genes);
				for(int i =0; i<mapGenebyPatient.get(patient).size();i++)	{
					
					query += "sources=" + wrappedGenes.get(i) + "&";
					
					if((i+1) % 50 == 0)	{
						executeURLQuery(out,patient,query);
						query = "";
					}
				}
				executeURLQuery(out,patient,query);
			}
			
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch(Exception e) { 
			e.printStackTrace(); 
		}
		
		try {
			String newOutputFile = outputFile.replaceAll(".txt", "_sif.txt");
			CalculatorSIF.calculatorSIF(outputFile,newOutputFile,mapMutProfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void executeURLQuery(BufferedWriter out, String patient, String query) throws IOException {
		if(query.length() == 0) return;
		
		BufferedReader in = null;
		query = query.substring(0,query.length()-1);
//		String url = "http://www.pathwaycommons.org/pc2/graph?" + query +"pattern=CONTROLS_STATE_CHANGE_OF&pattern=CONTROLS_EXPRESSION_OF&kind=NEIGHBORHOOD&limit=1&direction=BOTHSTREAM&format=SIF&subpw=false";
		String url = "https://apps.pathwaycommons.org/api/interactions?" + query;
		System.out.println("["+patient + "]\nquery: " + url);
		
		// 호출할 url
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection)obj.openConnection(); 
		con.setRequestMethod("GET");

		con.connect();
		
		in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); 
		String line = null; 
		while((line = in.readLine()) != null) { // response를 차례대로 출력 
			jsonparser(out,patient,line);
		}
		
		con.disconnect();
	}

	private static void jsonparser(BufferedWriter out,String patient, String line) throws IOException {
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(line);
//		System.out.println(jsonObject);
		JsonObject networkObj = (JsonObject) jsonObject.get("network");
		JsonArray EdgeArray = (JsonArray) networkObj.get("edges");
//		System.out.println(EdgeArray);
		
		if(EdgeArray == null) return;
		
		for (int i = 0; i < EdgeArray.size(); i++) {
			JsonObject object = (JsonObject) EdgeArray.get(i);
			JsonObject data = (JsonObject) object.get("data");
			String type = data.get("type").getAsString();
			
			if(type.equalsIgnoreCase("controls-expression-of") || type.equalsIgnoreCase("controls-state-change-of"))	{
				String source = data.get("source").getAsString();
				String target = data.get("target").getAsString();
				out.write(patient +"\t"+source + " " + type + " " + target + "\n");
//				System.out.println(patient +"\t"+source + " " + type + " " + target);
			}
		}
//		System.out.println();
	}

}

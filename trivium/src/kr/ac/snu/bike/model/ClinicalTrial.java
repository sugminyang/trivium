package kr.ac.snu.bike.model;

import java.util.LinkedHashSet;

public class ClinicalTrial {
	String fullInformation;
	String patientID;
	double overall_Survival;
	double TMB_Score;
	String cancer_Type;
	String cancer_Type_Detailed;
	String oncotree_Code;
	String Sample_type;
	String drug_Type;
	String metastatic_Site;
	LinkedHashSet<String> setFusion = new LinkedHashSet<String>();
	LinkedHashSet<String> setHugo_Symbol = new LinkedHashSet<String>();
	
	public String getPatientID() {
		return patientID;
	}
	public void setPatientID(String patient_ID) {
		this.patientID = patient_ID;
	}
	public double getOverall_Survival() {
		return overall_Survival;
	}
	public void setOverall_Survival(double overall_Survival) {
		this.overall_Survival = overall_Survival;
	}
	public double getTMB_Score() {
		return TMB_Score;
	}
	public void setTMB_Score(double tMB_Score) {
		TMB_Score = tMB_Score;
	}
	public String getCancer_Type() {
		return cancer_Type;
	}
	public void setCancer_Type(String cancer_Type) {
		this.cancer_Type = cancer_Type;
	}
	public String getCancer_Type_Detailed() {
		return cancer_Type_Detailed;
	}
	public void setCancer_Type_Detailed(String cancer_Type_Detailed) {
		this.cancer_Type_Detailed = cancer_Type_Detailed;
	}
	public String getOncotree_Code() {
		return oncotree_Code;
	}
	public void setOncotree_Code(String oncotree_Code) {
		this.oncotree_Code = oncotree_Code;
	}
	public String getSample_type() {
		return Sample_type;
	}
	public void setSample_type(String sample_type) {
		Sample_type = sample_type;
	}
	public String getDrug_Type() {
		return drug_Type;
	}
	public void setDrug_Type(String drug_Type) {
		this.drug_Type = drug_Type;
	}
	public String getMetastatic_Site() {
		return metastatic_Site;
	}
	public void setMetastatic_Site(String metastatic_Site) {
		this.metastatic_Site = metastatic_Site;
	}
	public LinkedHashSet<String> getFusion() {
		return setFusion;
	}
	public void setFusion(String fusion) {
		if (!setFusion.contains(fusion)) {
			setFusion.add(fusion);	
		}
	}
	public LinkedHashSet<String> getSetHugo_Symbol() {
		return setHugo_Symbol;
	}
	public void setHugo_Symbol(String hugo_Symbol) {
		if (!setHugo_Symbol.contains(hugo_Symbol)) {
			setHugo_Symbol.add(hugo_Symbol);	
		}
	}
	@Override
	public String toString() {
		return "ClinicalTrial [patient_ID=" + patientID + ", overall_Survival=" + overall_Survival + ", TMB_Score="
				+ TMB_Score + ", cancer_Type=" + cancer_Type + ", cancer_Type_Detailed=" + cancer_Type_Detailed
				+ ", oncotree_Code=" + oncotree_Code + ", Sample_type=" + Sample_type + ", drug_Type=" + drug_Type
				+ ", metastatic_Site=" + metastatic_Site + ", setFusion=" + setFusion + ", setHugo_Symbol=" + setHugo_Symbol
				+ "]";
	}

	public ClinicalTrial() {
		super();
	}
	public ClinicalTrial(String fullInformation) {
		super();
		this.fullInformation = fullInformation;
		
		String[] item = fullInformation.split("\t");
		double os = Double.parseDouble(item[1]);
		double tmb = Double.parseDouble(item[2]); 
		
		setPatientID(item[0]);
		setOverall_Survival(os);
		setTMB_Score(tmb);
		setCancer_Type(item[3]);
		setCancer_Type_Detailed(item[4]);
		setOncotree_Code(item[5]);
		setSample_type(item[6]);
		setDrug_Type(item[7]);
		
		if(!item[6].equalsIgnoreCase("Metastasis"))	{
			setMetastatic_Site(item[8]);
		}
		
		setFusion(item[9]);
		setHugo_Symbol(item[10]);
	}
	
}

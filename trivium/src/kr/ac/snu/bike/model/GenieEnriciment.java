package kr.ac.snu.bike.model;

import java.util.LinkedHashSet;

public class GenieEnriciment {
	String patientID;
	LinkedHashSet<String> setHugo_Symbol = new LinkedHashSet<String>();
	public String getPatientID() {
		return patientID;
	}
	public void setPatientID(String patientID) {
		this.patientID = patientID;
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
		return "GenieEnriciment [patientID=" + patientID + ", setHugo_Symbol=" + setHugo_Symbol + "]";
	}
	public GenieEnriciment(String patientID, String hugo_Symbol) {
		super();
		this.patientID = patientID;
		setHugo_Symbol(hugo_Symbol);
	}
	
}

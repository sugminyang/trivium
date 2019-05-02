package kr.ac.snu.bike.test;

import static statgraphics.util.Argument.DATA_NAMES;

import java.util.Arrays;
import java.util.Hashtable;

import javastat.survival.KaplanMeierEstimate;
import statgraphics.GraphicalAnalysis;
import statgraphics.survival.SurvivalEstimatePlot;
import statgraphics.util.PlotFrame;
import statgraphics.util.PlotFrameFactory;

public class KaplanMeierEstimateExample {

	public static void main(String arg[])
	{
//		double[] time1 = {3.0, 2.0, 5.0, 5.0, 3.0, 9.0, 3.0, 1.0, 9.0, 3.0};
//		double[] time2 = {19.0, 15.0, 18.0, 4.0, 28.0, 19.0, 15.0, 14.0, 4.0, 22.0, 13.0, 10.0, 3.0, 14.0, 6.0, 1.0, 9.0, 3.0, 4.0, 17.0};
		
//		double[] censor1 = {0,0,0,0,0,1,0,0,0,0};
//		double[] censor2 = {0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1};
		
//		double[] censor1 = {1,1,1,1,1,0,1,1,1,1};
//		double[] censor2 = {1,1,0,1,0,0,0,0,1,1,0,1,0,1,1,1,1,1,1,0};
		double[] time1 = { 1, 1, 2, 3, 4, 4, 5, 5, 8, 8, 8, 8, 11, 11, 12, 12, 15, 17, 22, 23 };
		double[] time2 = { 6, 6, 7, 9, 10, 11, 13, 15, 16, 19, 20, 22, 23, 32, 6, 10, 17, 19, 24, 25, 25, 28, 28, 32, 33, 34, 35, 39 };
		
		double[] censor1 = {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0}; 
		double[] censor2 = {1.0,0.0,1.0,0.0,0.0,0.0,1.0,0.0,1.0,0.0,0.0,1.0,1.0,0.0,1.0,1.0,0.0,0.0,1.0,0.0,1.0,1.0,0.0,0.0,1.0,0.0,0.0,0.0};
		// Non-null constructor under proportional hazards assumption 
		KaplanMeierEstimate testclass1 = new KaplanMeierEstimate(0.05,time1, censor1); 
		double [] estimate1 = testclass1.estimate; 
		System.out.println("sif contain patient's os");
		for(double d : estimate1) {
			System.out.println(d);
		}
		
		System.out.println();
		
		KaplanMeierEstimate testclass2 = new KaplanMeierEstimate(0.05,time2, censor2);
		System.out.println("sif doesn't contain patient's os");
		double [] estimate2 = testclass2.estimate; 
		for(double d : estimate2) {
			System.out.println(d);
		}		
		
		System.out.println(testclass1.output.toString());
		System.out.println(testclass2.output.toString());
		
		Hashtable argument = new Hashtable();
		PlotFrame[] pf = new PlotFrame[1];
		String[] names = new String[] {"contain patients", "non patients"};
		argument.put(DATA_NAMES, names);
		GraphicalAnalysis graphicalAnalysis = new SurvivalEstimatePlot(argument,
                new double[][] {time1, time2},
                new double[][] {estimate1,
                	estimate2}).graphicalAnalysis;
        pf[0] = new PlotFrame("Kaplan-Meier Estimate Plot IV",
                           graphicalAnalysis.plot, 500, 270);
 
        new PlotFrameFactory().putPlotFrame(pf);
	}
}

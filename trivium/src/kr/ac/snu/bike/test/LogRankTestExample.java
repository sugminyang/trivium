package kr.ac.snu.bike.test;

import static javastat.util.Output.PVALUE;
import static javastat.util.Output.TEST_STATISTIC;

import java.util.Hashtable;

import javastat.StatisticalAnalysis;
import javastat.survival.KaplanMeierEstimate;
import javastat.survival.inference.LogRankTest;
import javastat.util.DataManager;

public class LogRankTestExample
{

	public static void main(String arg[])
	{
		//        double[] time1 = {18.0, 3.0, 4.0, 15.0, 14.0, 2.0, 5.0, 5.0, 3.0, 3.0, 1.0, 9.0, 9.0, 3.0};
		//        double[] time2 = {19.0, 15.0, 28.0, 19.0, 4.0, 22.0, 13.0, 10.0, 3.0, 14.0, 6.0, 9.0, 1.0, 3.0, 4.0, 17.0};

		//      double[] censor1 = {1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0};
		//      double[] censor2 = {0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0};

		//    	double[] time1 = {3.0, 2.0, 5.0, 5.0, 3.0, 9.0, 3.0, 1.0, 9.0, 3.0};
		//    	double[] time2 = {19.0, 15.0, 18.0, 4.0, 28.0, 19.0, 15.0, 14.0, 4.0, 22.0, 13.0, 10.0, 3.0, 14.0, 6.0, 1.0, 9.0, 3.0, 4.0, 17.0};
		//    	
		//        double[] sifUsed = {0,0,0,0,0,1,0,0,0,0};
		//        double[] sifUnused = {0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1};

		//        DataManager dm = new DataManager();
		//        LogRankTest testclass1 = new LogRankTest(time1, sifUsed, time2, sifUnused);
		//        double testStatistic = testclass1.testStatistic;
		//        double pValue = testclass1.pValue;
		//        System.out.println(pValue);
		//        System.out.println("testclass1-pvalue: " + pValue);
		//        
		//        LogRankTest testclass2 = new LogRankTest();
		//        testStatistic = testclass2.testStatistic(time1, sifUsed, time2, sifUnused);
		//        pValue = testclass2.pValue(time1, sifUsed, time2, sifUnused);
		//        System.out.println("testclass2-pvalue: " + pValue);
		//        
		//        Hashtable argument = new Hashtable();
		//        StatisticalAnalysis testclass3 = new LogRankTest(argument,time1, sifUsed, time2, sifUnused).statisticalAnalysis;
		//        testStatistic = (Double) testclass3.output.get(TEST_STATISTIC);
		//        pValue = (Double) testclass3.output.get(PVALUE);
		//        System.out.println("testclass3-pvalue: " + pValue);
		//        
		//        LogRankTest testclass4 = new LogRankTest(argument, null);
		//        testStatistic = testclass4.testStatistic(argument, time1, sifUsed,time2, sifUnused);
		//        pValue = testclass4.pValue(argument, time1, sifUsed, time2, sifUnused);
		//        System.out.println("testclass4-pvalue: " + pValue);

//		double[] time1 = {156, 1040, 59, 329, 268, 638, 1106, 431, 855, 803,115, 477, 448};
//		double[] time2 = {421, 769, 365, 770, 1227, 475, 1129, 464, 1206, 563,744, 353, 377};
//		double[] censor1 = {1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0};
//		double[] censor2 = {0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0};

//		double[] time1 = {3.0, 2.0, 5.0, 5.0, 3.0, 9.0, 3.0, 1.0, 9.0, 3.0};
//		double[] time2 = {19.0, 15.0, 18.0, 4.0, 28.0, 19.0, 15.0, 14.0, 4.0, 22.0, 13.0, 10.0, 3.0, 14.0, 6.0, 1.0, 9.0, 3.0, 4.0, 17.0};
////
////		double[] censor1 = {0,0,0,0,0,1,0,0,0,0};
////		double[] censor2 = {0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1};
//
//		double[] censor1 = {1,1,1,1,1,0,1,1,1,1};
//		double[] censor2 = {1,1,0,1,0,0,0,0,1,1,0,1,0,1,1,1,1,1,1,0};
//		
//		LogRankTest testclass1 = new LogRankTest(time1, censor1, time2, censor2);
//		double testStatistic = testclass1.testStatistic;
//		double pValue = testclass1.pValue;
//		System.out.println(testStatistic + "\tp-value: " + pValue);
		
		double[] sample1_points = { 1, 1, 2, 3, 4, 4, 5, 5, 8, -8, 8, 8, 11, 11, 12, 12, 15, 17, 22, 23 };
		double[] sample2_points = { 6, -6, 7, -9, -10, -11, 13, -15, 16, -19, -20, 22, 23, -32, 6, 10, -17, -19, 24, -25, 25, 28, -28, -32, 33, -34, -35, -39 };

//		double[] sample1_points = { 1, 1, 2, 3, 4, 4, 5, 5, 8, 8, 8, 8, 11, 11, 12, 12, 15, 17, 22, 23 };
//		double[] sample2_points = { 6, 6, 7, 9, 10, 11, 13, 15, 16, 19, 20, 22, 23, 32, 6, 10, 17, 19, 24, 25, 25, 28, 28, 32, 33, 34, 35, 39 };		

//		double[] censor1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//		double[] censor2 = {0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1};
		
		double[] censor1 = new double[sample1_points.length];
		double[] censor2 = new double[sample2_points.length];
		
		for(int i = 0 ; i <sample1_points.length; i++)	{
			censor1[i] = 1;
			if(sample1_points[i] <= 0)	{
//				censor1[i] = 0; 
				sample1_points[i] = Math.abs(sample1_points[i]);
			}
			else	{
				censor1[i] = 1;
			}
			System.out.print(censor1[i] + ",");
		}
		System.out.println();
		
		for(int i = 0 ; i <sample2_points.length; i++)	{
			censor2[i] = 1;
			if(sample2_points[i] <= 0)	{
//				censor2[i] = 0;
				sample2_points[i] = Math.abs(sample2_points[i]);
			}
			else	{
				censor2[i] = 1;
			}
			System.out.print(censor2[i] + ",");
		}
		System.out.println();
		
		
		DataManager dm = new DataManager();

		LogRankTest testclass1 = new LogRankTest(sample1_points,censor1,sample2_points,censor2);
		double testStatistic = testclass1.testStatistic;
		double pValue = testclass1.pValue;
		System.out.println(testStatistic + "\t" + pValue);
		
	}
	
//	1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 0.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 
//	1.0 0.0 1.0 0.0 0.0 0.0 1.0 0.0 1.0 0.0 0.0 1.0 1.0 0.0 1.0 1.0 0.0 0.0 1.0 0.0 1.0 1.0 0.0 0.0 1.0 0.0 0.0 0.0 
//	24.634931370301597	8.881784197001252E-15
}
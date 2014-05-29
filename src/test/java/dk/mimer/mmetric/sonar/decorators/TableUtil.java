package dk.mimer.mmetric.sonar.decorators;

import java.text.DecimalFormat;

import org.sonar.api.measures.Measure;

public class TableUtil {

	
	public static void printTable(String header, String projectName, String[] rowHeaders, String dataSet, AbstractDistributedWeightDecorator dec) {
		DecimalFormat f = new DecimalFormat("####0.0");
		Measure m = new Measure().setData(dataSet);
		double[] values = dec.getDistributionValues(m);
		double[] pct = dec.getValuesInPercentage(values);
		
		System.out.println("\n\n| "+header+"  | "+projectName+"    |"+projectName +" (%) |" );
		System.out.println("|------------|------------:|--------------:|");
		for (int i = 0; i < values.length; i++) {
			System.out.println("|"+rowHeaders[i]+"|        "+ (int)values[(values.length-1)-i]+" |        "+f.format(pct[(values.length-1)-i]) +" |");
		}
	}

	
	public static void printTablePct(String header, String projectName, String[] rowHeaders, String dataSet, AbstractDistributedWeightDecorator dec, boolean printHeader) {
		DecimalFormat f = new DecimalFormat("####0.0");
		Measure m = new Measure().setData(dataSet);
		double[] values = dec.getDistributionValues(m);
		double[] pct = dec.getValuesInPercentage(values);
		
		if (printHeader) {
			System.out.println("\n\n| "+projectName+"  | Ingen risiko | Moderat risiko | Høj risiko| Meget høj risiko |" );
			System.out.println("|------------|-------------:|---------------:|----------:|-----------------:|");
		}
		
		String row ="|"+projectName+"|"; 
		for (int i = 0; i < values.length; i++) {
			row += (int)values[i]+" ("+f.format(pct[i]) +"%) |";
		}
		System.out.println(row);
	}
}

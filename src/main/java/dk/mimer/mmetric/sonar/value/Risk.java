package dk.mimer.mmetric.sonar.value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Risk {

	LOW_RISK("Low risk", 0), MODERATE_RISK("Moderate risk", 1), HIGH_RISK("High risk", 2), VERY_HIGH_RISK("Very high risk", 3);
	
	private static Logger logger = LoggerFactory.getLogger(Risk.class);
	private String title;
	private int value;

	private Risk(String title, int value) {
		this.title = title;
		this.value = value;
		
	}
	
	public static Risk valueOf(int value) {
		switch (value) {
			case 0:
				return LOW_RISK;
			case 1:
				return MODERATE_RISK;
			case 2:
				return HIGH_RISK;
			case 3:
				return VERY_HIGH_RISK;
			default:
				throw new IllegalArgumentException("Illegal value for MeasureWeight: "+value);
		}
	}

	public String getTitle() {
		return title;
	}

	public int getValue() {
		return value;
	}
}

package dk.mimer.mmetric.sonar.value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MeasureWeight {

	MINUS_MINUS("--", 0), MINUS("-", 1), ZERO("0", 2), PLUS("+", 3), PLUS_PLUS("++", 4);
	
	private static Logger logger = LoggerFactory.getLogger(MeasureWeight.class);
	private String title;
	private int value;

	private MeasureWeight(String title, int value) {
		this.title = title;
		this.value = value;
		
	}
	
	public static MeasureWeight valueOf(int value) {
		switch (value) {
			case 0:
				return MINUS_MINUS;
			case 1:
				return MINUS;
			case 2:
				return ZERO;
			case 3:
				return PLUS;
			case 4:
				return PLUS_PLUS;
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

	public static MeasureWeight fromValue(String title) {
		logger.debug("Creating MeasureWeight based on: "+title);
		if (title == null) {
			throw new NullPointerException("MetricWeight title was null!");
		} else if (title.equals("--")) {
			return MINUS_MINUS;
		} else if (title.equals("-")) {
			return MINUS;
		} else if (title.equals("0")) {
			return ZERO;
		} else if (title.equals("+")) {
			return PLUS;
		} else if (title.equals("++")) {
			return PLUS_PLUS;
		} else {
			throw new IllegalArgumentException("Wrong title value for MetricWeight: "+title);
		}
	}
}

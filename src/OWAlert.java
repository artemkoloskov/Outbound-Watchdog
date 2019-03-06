public class OWAlert implements OWConstants {
	private OWAlertTemplate template;
	private int upperLimit;
	private int lowerLimit;
	private int singleLimit;
	private int value;
	private ComparisonTypes comparison;
	
	public OWAlert (OWAlertTemplate alertTemplate, String val) {
		template = alertTemplate;
		upperLimit = alertTemplate.getUpperLimit();
		lowerLimit = alertTemplate.getLowerLimit();
		singleLimit = alertTemplate.getSingleLimit();
		comparison = alertTemplate.getComparison();
		setValue(Integer.parseInt(val));
	}
	
	public OWAlert (OWAlertTemplate alertTemplate, int val) {
		template = alertTemplate;
		upperLimit = alertTemplate.getUpperLimit();
		lowerLimit = alertTemplate.getLowerLimit();
		singleLimit = alertTemplate.getSingleLimit();
		comparison = alertTemplate.getComparison();
		value = val;
	}
	
	public boolean isAlerted () {		
		if (singleLimit == -1) {
			switch (comparison) {
			case VALUE_OUTSIDE:
				return value < lowerLimit || value > upperLimit;
			case VALUE_OUTSIDE_OR_LOW:
				return value <= lowerLimit || value > upperLimit;
			case VALUE_OUTSIDE_OR_HIGH:
				return value < lowerLimit || value >= upperLimit;
			case VALUE_OUTSIDE_OR_EQUALS:
				return value <= lowerLimit || value >= upperLimit;
			case VALUE_INSIDE:
				return value > lowerLimit && value < upperLimit;
			case VALUE_INSIDE_OR_LOW:
				return value >= lowerLimit && value < upperLimit;
			case VALUE_INSIDE_OR_HIGH:
				return value > lowerLimit && value <= upperLimit;
			case VALUE_INSIDE_OR_EQUALS:
				return value >= lowerLimit && value <= upperLimit;
			default:
				return false;
			}
		} else if (lowerLimit == -1 && upperLimit == -1) {
			switch (comparison) {
			case VALUE_EQUALS_X:
				return value == singleLimit;
			case VALUE_GREATER_OR_EQUALS_X:
				return value >= singleLimit;
			case VALUE_GREATER_X:
				return value > singleLimit;
			case VALUE_LESS_OR_EQUALS_X:
				return value <= singleLimit;
			case VALUE_LESS_X:
				return value < singleLimit;
			default:
				return false;
			}
		} else return false;		
	}
	
	public int getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(int upperLimit) {
		this.upperLimit = upperLimit;
	}

	public int getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(int lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public int getSingleLimit() {
		return singleLimit;
	}

	public void setSingleLimit(int singleLimit) {
		this.singleLimit = singleLimit;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public OWAlertTemplate getTemplate() {
		return template;
	}

	public void setTemplate(OWAlertTemplate template) {
		this.template = template;
	}

	public ComparisonTypes getComparison() {
		return comparison;
	}

	public void setComparison(ComparisonTypes comparison) {
		this.comparison = comparison;
	}
	
}

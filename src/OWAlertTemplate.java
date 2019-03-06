public class OWAlertTemplate implements OWConstants{
	private int upperLimit;
	private int lowerLimit;
	private int singleLimit;
	private ComparisonTypes comparison;
	
	public OWAlertTemplate (String low, String up, String single, ComparisonTypes compare) {
		upperLimit = Integer.parseInt(up);
		lowerLimit = Integer.parseInt(low);
		comparison = compare;
		singleLimit = Integer.parseInt(single);
	}
	
	public OWAlertTemplate (int low, String up, String single, ComparisonTypes compare) {
		upperLimit = Integer.parseInt(up);
		lowerLimit = low;
		comparison = compare;
		singleLimit = Integer.parseInt(single);
	}
	
	public OWAlertTemplate (String low, int up, String single, ComparisonTypes compare) {
		upperLimit = up;
		lowerLimit = Integer.parseInt(low);
		comparison = compare;
		singleLimit = Integer.parseInt(single);
	}
	
	public OWAlertTemplate (String low, String up, int single, ComparisonTypes compare) {
		upperLimit = Integer.parseInt(up);
		lowerLimit = Integer.parseInt(low);
		comparison = compare;
		singleLimit = single;
	}
	
	public OWAlertTemplate (int low, int up, String single, ComparisonTypes compare) {
		upperLimit = up;
		lowerLimit = low;
		comparison = compare;
		singleLimit = Integer.parseInt(single);
	}
	
	public OWAlertTemplate (String low, int up, int single, ComparisonTypes compare) {
		upperLimit = up;
		lowerLimit = Integer.parseInt(low);
		comparison = compare;
		singleLimit = single;
	}
	
	public OWAlertTemplate (int low, int up, int single, ComparisonTypes compare) {
		upperLimit = up;
		lowerLimit = low;
		comparison = compare;
		singleLimit = single;
	}
	
	public OWAlertTemplate (String low, String up, ComparisonTypes compare) {
		upperLimit = Integer.parseInt(up);
		lowerLimit = Integer.parseInt(low);
		comparison = compare;
		singleLimit = -1;
	}
	
	public OWAlertTemplate (String low, int up, ComparisonTypes compare) {
		upperLimit = up;
		lowerLimit = Integer.parseInt(low);
		comparison = compare;
		singleLimit = -1;
	}
	
	public OWAlertTemplate (int low, String up, ComparisonTypes compare) {
		upperLimit = Integer.parseInt(up);
		lowerLimit = low;
		comparison = compare;
		singleLimit = -1;
	}
	
	public OWAlertTemplate (int low, int up, ComparisonTypes compare) {
		upperLimit = up;
		lowerLimit = low;
		comparison = compare;
		singleLimit = -1;
	}
	
	public OWAlertTemplate (String single, ComparisonTypes compare) {
		upperLimit = -1;
		lowerLimit = -1;
		comparison = compare;
		singleLimit = Integer.parseInt(single);
	}
	
	public OWAlertTemplate (int single, ComparisonTypes compare) {
		upperLimit = -1;
		lowerLimit = -1;
		comparison = compare;
		singleLimit = single;
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

	public ComparisonTypes getComparison() {
		return comparison;
	}

	public void setComparison(ComparisonTypes comparison) {
		this.comparison = comparison;
	}
	
	public String toString () {
		return "Lower_limit: " + lowerLimit + "\r\n" + "Upper_Limit: " + upperLimit + "\r\n" + "Single_limit: " + singleLimit + "\r\n" + "Comparison_type: " + comparison.toString();
	}
}

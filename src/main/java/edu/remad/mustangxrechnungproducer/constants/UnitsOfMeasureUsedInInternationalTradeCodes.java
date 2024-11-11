package edu.remad.mustangxrechnungproducer.constants;

public enum UnitsOfMeasureUsedInInternationalTradeCodes {
	HUR(0, "Hour");
	
	private final int ordinal;
	
	private final String unit;

	UnitsOfMeasureUsedInInternationalTradeCodes(int i, String unit) {
		ordinal = i;
		this.unit = unit;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public String getUnit() {
		return unit;
	}
}

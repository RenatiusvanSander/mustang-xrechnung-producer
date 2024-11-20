package edu.remad.mustangxrechnungproducer.constants;

public enum UnitsOfMeasureUsedInInternationalTradeCodes {
	HUR(0, "Hour");

	private final int ordinal;

	private final String unit;

	/**
	 * Constructor of {@link UnitsOfMeasureUsedInInternationalTradeCodes}
	 * 
	 * @param ordinalNo ordinal number
	 * @param unit unit to set
	 */
	UnitsOfMeasureUsedInInternationalTradeCodes(int ordinalNo, String unit) {
		ordinal = ordinalNo;
		this.unit = unit;
	}

	/**
	 * Gets ordinal
	 * 
	 * @return ordinal number
	 */
	public int getOrdinal() {
		return ordinal;
	}

	/**
	 * Gets unit.
	 * 
	 * @return string encoded unit
	 */
	public String getUnit() {
		return unit;
	}
}

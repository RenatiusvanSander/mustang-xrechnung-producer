package edu.remad.mustangxrechnungproducer.constants;

public final class XRechnungXmlProducerConstants {

	private XRechnungXmlProducerConstants() {
		// do not instantiate
	}

	/** DocumentName constant for Rechnung */
	public static final String DOCUMENT_NAME = "Rechnung";

	/** German profile is XRechnung */
	public static final String DEFAULT_PROFILE = "XRechnung";

	/** For Germany needed legal tax note on an invoice */
	public static final String LEGAL_TAX_NOTE = "Hinweis: Als Kleinunternehmer im Sinne von § 19 Abs. 1 UStG wird Umsatzsteuer nicht berechnet. (Das Rechnungsdatum entspricht dem Leistungsdatum)";
}

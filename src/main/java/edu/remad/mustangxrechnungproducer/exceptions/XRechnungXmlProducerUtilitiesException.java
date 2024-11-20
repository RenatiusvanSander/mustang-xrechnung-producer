package edu.remad.mustangxrechnungproducer.exceptions;

public class XRechnungXmlProducerUtilitiesException extends RuntimeException {

	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = -925120975169403397L;

	/**
	 * Constructor of {@link XRechnungXmlProducerUtilitiesException}
	 * 
	 * @param message error message
	 * @param e any type of {@link Throwable}
	 */
	public XRechnungXmlProducerUtilitiesException(String message, Exception e) {
		super(message, e);
	}

}

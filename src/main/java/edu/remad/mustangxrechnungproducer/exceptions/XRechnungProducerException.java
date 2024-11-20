package edu.remad.mustangxrechnungproducer.exceptions;

public class XRechnungProducerException extends RuntimeException {

	private static final long serialVersionUID = 5511683112731449900L;
	
	/**
	 * Constructor of {@link XRechnungProducerException}
	 * 
	 * @param message error message
	 */
	public XRechnungProducerException(String message) {
		super(message);
	}

}

package edu.remad.mustangxrechnungproducer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class XRechnungXmlProducerTest {

	@Test
	public void constructorSuccessfulCreatesInstance() {
		XRechnungXmlProducer producer = new XRechnungXmlProducer();
		
		assertNotNull(producer,"XRechnungXmlProducer has to be not null.");
	}
}

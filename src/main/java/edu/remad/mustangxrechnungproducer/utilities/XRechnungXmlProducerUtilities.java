package edu.remad.mustangxrechnungproducer.utilities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

import edu.remad.mustangxrechnungproducer.exceptions.XRechnungXmlProducerUtilitiesException;
import edu.remad.tutoring2.appconstants.XRechnungAppConstants;

public final class XRechnungXmlProducerUtilities {

	private XRechnungXmlProducerUtilities() {
		// do not instantiate
	}

	public static Date localeDateTimeToDate(LocalDateTime localeDateTime) {
		try {
			return Date.from(localeDateTime.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		} catch (Exception e) {
			throw new XRechnungXmlProducerUtilitiesException(
					"XRechnungXmlProducerUtilities error: LocaleDateTime was not converted to Date!", e);
		}
	}

	public static Date localeDateTimePlus30Days(LocalDateTime localeDateTime) {
		try {
			return Date.from(localeDateTime.toLocalDate().atStartOfDay().plusDays(30).atZone(ZoneId.systemDefault())
					.toInstant());
		} catch (Exception e) {
			throw new XRechnungXmlProducerUtilitiesException(
					"XRechnungXmlProducerUtilities error: LocaleDateTime was not increased by 30 days and converted to Date!",
					e);
		}
	}

	public static Properties ceateJUnitTestProperties() {
		Properties customProperties = new Properties();
		customProperties.setProperty(XRechnungAppConstants.XRECHNUNG_RECIPIENT_CUSTOM_VAT, "");
		customProperties.setProperty(XRechnungAppConstants.XRECHNUNG_SENDER_BANK_DETAILS_BIC, "DEMOBIC");
		customProperties.setProperty(XRechnungAppConstants.XRECHNUNG_SENDER_BANK_DETAILS_IBAN,
				"DE89370400440532013000");
		customProperties.setProperty(XRechnungAppConstants.XRECHNUNG_SENDER_COUNTRY, "Germany");
		customProperties.setProperty(XRechnungAppConstants.XRECHNUNG_SENDER_COUNTRY_CODE, "DE");
		customProperties.setProperty(XRechnungAppConstants.XRECHNUNG_SENDER_EMAIL, "john.doe@doesnotexist.com");
		customProperties.setProperty(XRechnungAppConstants.XRECHNUNG_SENDER_FULLNAME, "John Doe");
		customProperties.setProperty(XRechnungAppConstants.XRECHNUNG_SENDER_LEITWEG_ID, "63643643643646");
		customProperties.setProperty(XRechnungAppConstants.XRECHNUNG_SENDER_PHONE, "+49743646546464");
		customProperties.setProperty(XRechnungAppConstants.XRECHNUNG_SENDER_STREET_AND_HOUSE_NO, "Examplestreet 16A");
		customProperties.setProperty(XRechnungAppConstants.XRECHNUNG_SENDER_TAX_ID, "TAX-ID-6366363");
		customProperties.setProperty(XRechnungAppConstants.XRECHNUNG_SENDER_VAT_ID, "VAT_ID_723985");
		customProperties.setProperty(XRechnungAppConstants.XRECHNUNG_SENDER_ZIP, "6536363");
		customProperties.setProperty(XRechnungAppConstants.XRECHNUNG_SENDER_ZIP_LOCATION, "Hamburg");

		return customProperties;
	}
}

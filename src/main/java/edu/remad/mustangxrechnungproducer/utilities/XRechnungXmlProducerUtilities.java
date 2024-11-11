package edu.remad.mustangxrechnungproducer.utilities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import edu.remad.mustangxrechnungproducer.exceptions.XRechnungXmlProducerUtilitiesException;

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
			return Date.from(localeDateTime.plusDays(30).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault())
					.toInstant());
		} catch (Exception e) {
			throw new XRechnungXmlProducerUtilitiesException(
					"XRechnungXmlProducerUtilities error: LocaleDateTime was not increased by 30 days and converted to Date!",
					e);
		}
	}
}

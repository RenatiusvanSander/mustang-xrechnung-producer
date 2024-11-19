package edu.remad.mustangxrechnungproducer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.mustangproject.BankDetails;
import org.mustangproject.Contact;
import org.mustangproject.Invoice;
import org.mustangproject.Item;
import org.mustangproject.Product;
import org.mustangproject.TradeParty;
import org.mustangproject.ZUGFeRD.Profiles;
import org.mustangproject.ZUGFeRD.ZUGFeRD2PullProvider;

import edu.remad.mustangxrechnungproducer.constants.UnitsOfMeasureUsedInInternationalTradeCodes;
import edu.remad.mustangxrechnungproducer.constants.XRechnungXmlProducerConstants;
import edu.remad.mustangxrechnungproducer.exceptions.XRechnungProducerException;
import edu.remad.mustangxrechnungproducer.models.FourWeeksTutoringPaymentTerms;
import edu.remad.mustangxrechnungproducer.utilities.XRechnungXmlProducerUtilities;
import edu.remad.tutoring2.appconstants.XRechnungAppConstants;
import edu.remad.tutoring2.models.AddressEntity;
import edu.remad.tutoring2.models.InvoiceEntity;
import edu.remad.tutoring2.models.ServiceContractEntity;
import edu.remad.tutoring2.models.UserEntity;
import edu.remad.tutoring2.models.ZipCodeEntity;

/**
 * Produces from invoice a XRechnung.xml as byte array.
 */
public class XRechnungXmlProducer {

	private InvoiceEntity invoiceData;

	private Properties customProperties;

	/**
	 * Constructor
	 */
	public XRechnungXmlProducer() {
	}

	/**
	 * Constructor
	 * 
	 * @param invoice an invoice as {@link InvoiceEntity}
	 */
	public XRechnungXmlProducer(InvoiceEntity invoice) {
		invoiceData = invoice;
	}

	/**
	 * Constructor
	 * 
	 * @param invoice          an invoice as {@link InvoiceEntity}
	 * @param customProperties customized properties
	 */
	public XRechnungXmlProducer(InvoiceEntity invoice, Properties customProperties) {
		invoiceData = invoice;
		this.customProperties = customProperties;
	}

	/**
	 * @return Rechnung.xml as byte array
	 */
	public byte[] produceXmlByteArray() {
		if (invoiceData == null) {
			throw new XRechnungProducerException(
					"XRechnungXmlProducer error: field invoiceData is null and needed. Cannot produce XRechnung-XML.");
		}

		TradeParty recipient = createTradePartyRecipient();
		Product product = createProduct();
		Item invoiceRowItem = createItem(product);
		TradeParty sender = createSender();
		Date issuerDate = XRechnungXmlProducerUtilities.localeDateTimeToDate(invoiceData.getInvoiceCreationDate());
		Date dueDate = XRechnungXmlProducerUtilities.localeDateTimePlus30Days(invoiceData.getInvoiceCreationDate());
		String invoiceNumber = String.valueOf(invoiceData.getInvoiceNo());

		ZUGFeRD2PullProvider zf2p = new ZUGFeRD2PullProvider();
		zf2p.setProfile(Profiles.getByName(XRechnungXmlProducerConstants.DEFAULT_PROFILE));
		zf2p.generateXML(createInvoice(recipient, invoiceRowItem, sender, issuerDate, dueDate, invoiceNumber));

		return zf2p.getXML();
	}

	private Invoice createInvoice(TradeParty recipient, Item invoiceRowItem, TradeParty sender, Date issuerDate,
			Date dueDate, String invoiceNumber) {
		return new Invoice().setDocumentName(XRechnungXmlProducerConstants.DOCUMENT_NAME).setDueDate(dueDate)
				.setIssueDate(issuerDate).setDeliveryDate(issuerDate).setSender(sender).setRecipient(recipient)
				.setReferenceNumber(getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_LEITWEG_ID))
				.setNumber(invoiceNumber).addItem(invoiceRowItem)
				.addLegalNote(XRechnungXmlProducerConstants.LEGAL_TAX_NOTE)
				.setPaymentTerms(new FourWeeksTutoringPaymentTerms());
	}

	private TradeParty createTradePartyRecipient() {
		UserEntity user = invoiceData.getInvoiceUser();
		AddressEntity address = user.getAddresses().get(0);
		ZipCodeEntity zipCode = address.getAddressZipCode();
		String fullName = user.getFirstName() + " " + user.getLastName();
		String streetAndHouseNo = address.getAddressStreet() + " " + address.getAddressHouseNo();

		return new TradeParty(fullName, streetAndHouseNo, zipCode.getZipCode(), zipCode.getZipCodeLocation(),
				getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_COUNTRY_CODE)).setEmail(user.getEmail())
				.setCountry(getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_COUNTRY));
	}

	private Product createProduct() {
		ServiceContractEntity serviceContract = invoiceData.getInvoiceServiceContract();
		String vat = getCustomProperty(XRechnungAppConstants.XRECHNUNG_RECIPIENT_CUSTOM_VAT);
		BigDecimal vatValue = StringUtils.isBlank(vat) ? BigDecimal.ZERO : new BigDecimal(vat);

		return new Product(serviceContract.getServiceContractName(), serviceContract.getServiceContractDescription(),
				UnitsOfMeasureUsedInInternationalTradeCodes.HUR.name(), vatValue);
	}

	private String getCustomProperty(String propertyKey) {
		if (customProperties != null) {
			return customProperties.getProperty(propertyKey);
		}

		return null;
	}

	private Item createItem(Product product) {
		return new Item(product, invoiceData.getPrice().getPrice(), BigDecimal.ONE);
	}

	private TradeParty createSender() {
		return new TradeParty(getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_FULLNAME),
				getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_STREET_AND_HOUSE_NO),
				getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_ZIP),
				getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_ZIP_LOCATION),
				getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_COUNTRY_CODE))
				.addTaxID(getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_TAX_ID))
				.addVATID(getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_VAT_ID))
				.setContact(createSenderCustomer()).addBankDetails(createSenderBankDetails())
				.setEmail(getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_EMAIL))
				.setZIP(getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_ZIP))
				.setLocation(getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_ZIP_LOCATION));
	}

	private BankDetails createSenderBankDetails() {
		return new BankDetails(getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_BANK_DETAILS_IBAN),
				getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_BANK_DETAILS_BIC));
	}

	private Contact createSenderCustomer() {
		return new Contact(getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_FULLNAME),
				getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_PHONE),
				getCustomProperty(XRechnungAppConstants.XRECHNUNG_SENDER_EMAIL));
	}

	/**
	 * @return invoice as {@link InvoiceEntity}
	 */
	public InvoiceEntity getInvoiceData() {
		return invoiceData;
	}

	/**
	 * Sets invoice
	 * 
	 * @param invoiceData invoice as {@link InvoiceEntity} to set
	 */
	public void setInvoiceData(InvoiceEntity invoiceData) {
		this.invoiceData = invoiceData;
	}

	/**
	 * @return customized properties as {@link Properties}
	 */
	public Properties getCustomProperties() {
		return customProperties;
	}

	/**
	 * Sets customized properties
	 * 
	 * @param customProperties customized properties as {@link Properties}
	 */
	public void setCustomProperties(Properties customProperties) {
		this.customProperties = customProperties;
	}

}

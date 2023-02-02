package com.chbase.thing.oxm.jaxb.referral;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.ArrayList;

import com.chbase.thing.oxm.jaxb.base.CodableValue;
import com.chbase.thing.oxm.jaxb.base.Person;
import com.chbase.thing.oxm.jaxb.dates.DateTime;
import com.chbase.thing.oxm.jaxb.base.Task;

/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;summary xmlns="http://www.w3.org/2001/XMLSchema" xmlns:d="urn:com.microsoft.wc.dates" xmlns:referral="urn:com.microsoft.wc.thing.referral" xmlns:t="urn:com.microsoft.wc.thing.types"&gt;
 *                         A referral made by a physician to another.
 *                     &lt;/summary&gt;
 * </pre>
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;remarks xmlns="http://www.w3.org/2001/XMLSchema" xmlns:d="urn:com.microsoft.wc.dates" xmlns:referral="urn:com.microsoft.wc.thing.referral" xmlns:t="urn:com.microsoft.wc.thing.types"&gt;
 *                         This type contains information required to support a referral
 *                         request or a transfer of care request from one practitioner or
 *                         organization to another. It is intended for use when a patient
 *                         is required to be referred to another provider for a
 *                         consultation/second opinion and/or for short term or longer term
 *                         management of one or more health issues or problems.
 *                     &lt;/remarks&gt;
 * </pre>
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;singleton xmlns="http://www.w3.org/2001/XMLSchema" xmlns:d="urn:com.microsoft.wc.dates" xmlns:referral="urn:com.microsoft.wc.thing.referral" xmlns:t="urn:com.microsoft.wc.thing.types"/&gt;
 * </pre>
 * 
 * 
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="when" type="{urn:com.microsoft.wc.dates}date-time" minOccurs="0" maxOccurs="1"/>
 *         &lt;element name="type" type="{urn:com.microsoft.wc.thing.types}codable-value" minOccurs="0" maxOccurs="1"/>
 *         &lt;element name="reason" type="{urn:com.microsoft.wc.thing.types}codable-value" minOccurs="1" maxOccurs="1"/>
 *         &lt;element name="referred-by" type="{urn:com.microsoft.wc.thing.types}person" minOccurs="0" maxOccurs="1"/>
 *         &lt;element name="task" type="{urn:com.microsoft.wc.thing.types}task" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;element name="referred-to" type="{urn:com.microsoft.wc.thing.types}person" minOccurs="0" maxOccurs="1"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "when", "type", "reason", "referredBy", "task", "referredTo" })
@XmlRootElement(name = "referral")
public class Referral {
	public static String typeId = "B861CB93-9CD3-408A-9C65-E5F58E4E2C30";

	protected DateTime when;
	protected CodableValue type;
	@XmlElement(required = true)
	protected CodableValue reason;
	@XmlElement(name = "referred-by")
	protected Person referredBy;
	protected List<Task> task;
	@XmlElement(name = "referred-to")
	protected Person referredTo;

	/**
	 * Gets the value of the whaen property.
	 * 
	 * @return possible object is {@link DateTime }
	 * 
	 */
	public DateTime getWhen() {
		return when;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link DateTime }
	 * 
	 */
	public void setWhen(DateTime value) {
		this.when = value;
	}

	/**
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link CodableValue }
	 * 
	 */
	public CodableValue getType() {
		return type;
	}

	/**
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link CodableValue }
	 * 
	 */
	public void setType(CodableValue value) {
		this.type = value;
	}

	/**
	 * Gets the value of the reason property.
	 * 
	 * @return possible object is {@link CodableValue }
	 * 
	 */
	public CodableValue getReason() {
		return reason;
	}

	/**
	 * Sets the value of the reason property.
	 * 
	 * @param value
	 *            allowed object is {@link CodableValue }
	 * 
	 */
	public void setReason(CodableValue value) {
		this.reason = value;
	}	

	/**
	 * Gets the value of the referredBy property.
	 * 
	 * @return possible object is {@link Person }
	 * 
	 */
	public Person getReferredBy() {
		return referredBy;
	}

	/**
	 * Sets the value of the referredBy property.
	 * 
	 * @param value
	 *            allowed object is {@link Person }
	 * 
	 */
	public void setReferredBy(Person value) {
		this.referredBy = value;
	}	

	/**
	 * Gets the value of the tasks property.
	 * 
	 * @return possible object is {@link Task }
	 * 
	 */
	public List<Task> getTasks() {
		if (this.task == null) {
			this.task = new ArrayList<Task>();
		}
		return this.task;
	}

	/**
	 * Sets the value of the tasks property.
	 * 
	 * @param value
	 *            allowed object is {@link Task }
	 * 
	 */
	public void setTasks(List<Task> tasks) {
		this.task = tasks;
	}

	/**
	 * Gets the value of the referredTo property.
	 * 
	 * @return possible object is {@link Person }
	 * 
	 */
	public Person getReferredTo() {
		return referredTo;
	}

	/**
	 * Sets the value of the referredTo property.
	 * 
	 * @param value
	 *            allowed object is {@link Person }
	 * 
	 */
	public void setReferredTo(Person value) {
		this.referredTo = value;
	}	

}

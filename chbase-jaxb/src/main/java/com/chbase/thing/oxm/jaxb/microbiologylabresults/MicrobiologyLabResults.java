//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.07 at 01:41:59 AM PST 
//

package com.chbase.thing.oxm.jaxb.microbiologylabresults;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.chbase.thing.oxm.jaxb.base.CodableValue;
import com.chbase.thing.oxm.jaxb.base.LabTestType;
import com.chbase.thing.oxm.jaxb.dates.DateTime;

/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;summary xmlns="http://www.w3.org/2001/XMLSchema" xmlns:d="urn:com.microsoft.wc.dates" xmlns:mic="urn:com.microsoft.wc.thing.microbiology" xmlns:t="urn:com.microsoft.wc.thing.types"&gt;
 *                         Information related to a microbiology lab test.
 *                     &lt;/summary&gt;
 * </pre>
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;remarks xmlns="http://www.w3.org/2001/XMLSchema" xmlns:d="urn:com.microsoft.wc.dates" xmlns:mic="urn:com.microsoft.wc.thing.microbiology" xmlns:t="urn:com.microsoft.wc.thing.types"&gt;
 *                         This thing type describes the microbiology lab test results of a person.
 *                     &lt;/remarks&gt;
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
 *         &lt;element name="when" type="{urn:com.microsoft.wc.dates}date-time"/>
 *         &lt;element name="lab-tests" type="{urn:com.microsoft.wc.thing.types}lab-test-type" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sensitivity-agent" type="{urn:com.microsoft.wc.thing.types}codable-value" minOccurs="0"/>
 *         &lt;element name="sensitivity-value" type="{urn:com.microsoft.wc.thing.types}codable-value" minOccurs="0"/>
 *         &lt;element name="sensitivity-interpretation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specimen-type" type="{urn:com.microsoft.wc.thing.types}codable-value" minOccurs="0"/>
 *         &lt;element name="organism-name" type="{urn:com.microsoft.wc.thing.types}codable-value" minOccurs="0"/>
 *         &lt;element name="organism-comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "when", "labTests", "sensitivityAgent", "sensitivityValue",
		"sensitivityInterpretation", "specimenType", "organismName", "organismComment" })
@XmlRootElement(name = "microbiology-lab-results")
public class MicrobiologyLabResults {
	public static String typeId = "B8FCB138-F8E6-436A-A15D-E3A2D6916094";

	@XmlElement(required = true)
	protected DateTime when;
	@XmlElement(name = "lab-tests")
	protected List<LabTestType> labTests;
	@XmlElement(name = "sensitivity-agent")
	protected CodableValue sensitivityAgent;
	@XmlElement(name = "sensitivity-value")
	protected CodableValue sensitivityValue;
	@XmlElement(name = "sensitivity-interpretation")
	protected String sensitivityInterpretation;
	@XmlElement(name = "specimen-type")
	protected CodableValue specimenType;
	@XmlElement(name = "organism-name")
	protected CodableValue organismName;
	@XmlElement(name = "organism-comment")
	protected String organismComment;

	/**
	 * Gets the value of the when property.
	 * 
	 * @return possible object is {@link DateTime }
	 * 
	 */
	public DateTime getWhen() {
		return when;
	}

	/**
	 * Sets the value of the when property.
	 * 
	 * @param value
	 *            allowed object is {@link DateTime }
	 * 
	 */
	public void setWhen(DateTime value) {
		this.when = value;
	}

	/**
	 * Gets the value of the labTests property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the labTests property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getLabTests().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link LabTestType }
	 * 
	 * 
	 */
	public List<LabTestType> getLabTests() {
		if (labTests == null) {
			labTests = new ArrayList<LabTestType>();
		}
		return this.labTests;
	}

	/**
	 * Gets the value of the sensitivityAgent property.
	 * 
	 * @return possible object is {@link CodableValue }
	 * 
	 */
	public CodableValue getSensitivityAgent() {
		return sensitivityAgent;
	}

	/**
	 * Sets the value of the sensitivityAgent property.
	 * 
	 * @param value
	 *            allowed object is {@link CodableValue }
	 * 
	 */
	public void setSensitivityAgent(CodableValue value) {
		this.sensitivityAgent = value;
	}

	/**
	 * Gets the value of the sensitivityValue property.
	 * 
	 * @return possible object is {@link CodableValue }
	 * 
	 */
	public CodableValue getSensitivityValue() {
		return sensitivityValue;
	}

	/**
	 * Sets the value of the sensitivityValue property.
	 * 
	 * @param value
	 *            allowed object is {@link CodableValue }
	 * 
	 */
	public void setSensitivityValue(CodableValue value) {
		this.sensitivityValue = value;
	}

	/**
	 * Gets the value of the sensitivityInterpretation property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSensitivityInterpretation() {
		return sensitivityInterpretation;
	}

	/**
	 * Sets the value of the sensitivityInterpretation property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSensitivityInterpretation(String value) {
		this.sensitivityInterpretation = value;
	}

	/**
	 * Gets the value of the specimenType property.
	 * 
	 * @return possible object is {@link CodableValue }
	 * 
	 */
	public CodableValue getSpecimenType() {
		return specimenType;
	}

	/**
	 * Sets the value of the specimenType property.
	 * 
	 * @param value
	 *            allowed object is {@link CodableValue }
	 * 
	 */
	public void setSpecimenType(CodableValue value) {
		this.specimenType = value;
	}

	/**
	 * Gets the value of the organismName property.
	 * 
	 * @return possible object is {@link CodableValue }
	 * 
	 */
	public CodableValue getOrganismName() {
		return organismName;
	}

	/**
	 * Sets the value of the organismName property.
	 * 
	 * @param value
	 *            allowed object is {@link CodableValue }
	 * 
	 */
	public void setOrganismName(CodableValue value) {
		this.organismName = value;
	}

	/**
	 * Gets the value of the organismComment property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOrganismComment() {
		return organismComment;
	}

	/**
	 * Sets the value of the organismComment property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOrganismComment(String value) {
		this.organismComment = value;
	}

}
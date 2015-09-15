//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.11.03 at 02:57:00 PM PST 
//

package com.chbase.thing.oxm.jaxb.xmldsigcoreschema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3c.dom.Element;

/**
 * <p>
 * Java class for KeyInfoType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="KeyInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element ref="{}KeyName"/>
 *         &lt;element ref="{}KeyValue"/>
 *         &lt;element ref="{}RetrievalMethod"/>
 *         &lt;element ref="{}X509Data"/>
 *         &lt;element ref="{}PGPData"/>
 *         &lt;element ref="{}SPKIData"/>
 *         &lt;element ref="{}MgmtData"/>
 *         &lt;any/>
 *       &lt;/choice>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeyInfoType", propOrder = { "content" })
public class KeyInfoType {

	@XmlElementRefs({ @XmlElementRef(name = "RetrievalMethod", namespace = "", type = JAXBElement.class),
			@XmlElementRef(name = "MgmtData", namespace = "", type = JAXBElement.class),
			@XmlElementRef(name = "X509Data", namespace = "", type = JAXBElement.class),
			@XmlElementRef(name = "KeyName", namespace = "", type = JAXBElement.class),
			@XmlElementRef(name = "KeyValue", namespace = "", type = JAXBElement.class),
			@XmlElementRef(name = "SPKIData", namespace = "", type = JAXBElement.class),
			@XmlElementRef(name = "PGPData", namespace = "", type = JAXBElement.class) })
	@XmlMixed
	@XmlAnyElement(lax = true)
	protected List<Object> content;
	@XmlAttribute(name = "Id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	/**
	 * Gets the value of the content property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the content property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getContent().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Object }
	 * {@link Element } {@link JAXBElement }{@code <}{@link RetrievalMethodType
	 * }{@code >} {@link JAXBElement }{@code <}{@link String }{@code >}
	 * {@link JAXBElement }{@code <}{@link X509DataType }{@code >}
	 * {@link JAXBElement }{@code <}{@link String }{@code >} {@link JAXBElement
	 * }{@code <}{@link KeyValueType }{@code >} {@link JAXBElement }{@code <}
	 * {@link SPKIDataType }{@code >} {@link JAXBElement }{@code <}
	 * {@link PGPDataType }{@code >} {@link String }
	 * 
	 * 
	 */
	public List<Object> getContent() {
		if (content == null) {
			content = new ArrayList<Object>();
		}
		return this.content;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setId(String value) {
		this.id = value;
	}

}
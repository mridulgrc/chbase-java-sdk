//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.09.23 at 03:42:07 PM IST 
//


package com.chbase.thing.oxm.jaxb.cda;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SubcutaneousRoute.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SubcutaneousRoute">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="SQIMPLNT"/>
 *     &lt;enumeration value="SQINFUS"/>
 *     &lt;enumeration value="IPUMPINJ"/>
 *     &lt;enumeration value="SQ"/>
 *     &lt;enumeration value="SQSURGINS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SubcutaneousRoute")
@XmlEnum
public enum SubcutaneousRoute {

    SQIMPLNT,
    SQINFUS,
    IPUMPINJ,
    SQ,
    SQSURGINS;

    public String value() {
        return name();
    }

    public static SubcutaneousRoute fromValue(String v) {
        return valueOf(v);
    }

}
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.09.23 at 03:42:07 PM IST 
//


package com.chbase.thing.oxm.jaxb.cda;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TableCellScope.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TableCellScope">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="col"/>
 *     &lt;enumeration value="colgroup"/>
 *     &lt;enumeration value="row"/>
 *     &lt;enumeration value="rowgroup"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TableCellScope")
@XmlEnum
public enum TableCellScope {

    @XmlEnumValue("col")
    COL("col"),
    @XmlEnumValue("colgroup")
    COLGROUP("colgroup"),
    @XmlEnumValue("row")
    ROW("row"),
    @XmlEnumValue("rowgroup")
    ROWGROUP("rowgroup");
    private final String value;

    TableCellScope(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TableCellScope fromValue(String v) {
        for (TableCellScope c: TableCellScope.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
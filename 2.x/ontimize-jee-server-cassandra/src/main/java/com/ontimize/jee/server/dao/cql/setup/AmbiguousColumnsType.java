//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.07.16 at 04:38:17 PM CEST
//

package com.ontimize.jee.server.dao.cql.setup;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for AmbiguousColumnsType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AmbiguousColumnsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AmbiguousColumn" type="{http://www.ontimize.com/schema/jdbc}AmbiguousColumnType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmbiguousColumnsType", propOrder = { "ambiguousColumn" })
public class AmbiguousColumnsType {

	@XmlElement(name = "AmbiguousColumn", required = true)
	protected List<AmbiguousColumnType> ambiguousColumn;

	/**
	 * Gets the value of the ambiguousColumn property.
	 *
	 * <p> This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB
	 * object. This is why there is not a <CODE>set</CODE> method for the ambiguousColumn property.
	 *
	 * <p> For example, to add a new item, do as follows:
	 *
	 * <pre>
	 * getAmbiguousColumn().add(newItem);
	 * </pre>
	 *
	 *
	 * <p> Objects of the following type(s) are allowed in the list {@link AmbiguousColumnType }
	 *
	 *
	 */
	public List<AmbiguousColumnType> getAmbiguousColumn() {
		if (this.ambiguousColumn == null) {
			this.ambiguousColumn = new ArrayList<>();
		}
		return this.ambiguousColumn;
	}

}

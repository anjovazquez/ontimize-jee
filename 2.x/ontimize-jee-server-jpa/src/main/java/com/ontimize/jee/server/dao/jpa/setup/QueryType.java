//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.07.21 at 11:50:12 AM CEST
//

package com.ontimize.jee.server.dao.jpa.setup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for QueryType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="QueryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AmbiguousColumns" type="{http://jpaentity.server.jee.imatia.com/setup}AmbiguousColumnsType" minOccurs="0"/>
 *         &lt;element name="FunctionColumns" type="{http://jpaentity.server.jee.imatia.com/setup}FunctionColumnsType" minOccurs="0"/>
 *         &lt;element name="Sentence" type="{http://jpaentity.server.jee.imatia.com/setup}SentenceType"/>
 *         &lt;element name="Return" type="{http://jpaentity.server.jee.imatia.com/setup}ReturnType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="syntax">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="SQL"/>
 *             &lt;enumeration value="JPQL"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryType", propOrder = { "ambiguousColumns", "functionColumns", "sentence", "_return" })
public class QueryType {

	@XmlElement(name = "AmbiguousColumns")
	protected AmbiguousColumnsType	ambiguousColumns;
	@XmlElement(name = "FunctionColumns")
	protected FunctionColumnsType	functionColumns;
	@XmlElement(name = "Sentence", required = true)
	protected SentenceType			sentence;
	@XmlElement(name = "Return")
	protected ReturnType			_return;
	@XmlAttribute(name = "id")
	protected String				id;
	@XmlAttribute(name = "syntax")
	protected String				syntax;

	/**
	 * Gets the value of the ambiguousColumns property.
	 *
	 * @return possible object is {@link AmbiguousColumnsType }
	 *
	 */
	public AmbiguousColumnsType getAmbiguousColumns() {
		return this.ambiguousColumns;
	}

	/**
	 * Sets the value of the ambiguousColumns property.
	 *
	 * @param value
	 *            allowed object is {@link AmbiguousColumnsType }
	 *
	 */
	public void setAmbiguousColumns(AmbiguousColumnsType value) {
		this.ambiguousColumns = value;
	}

	/**
	 * Gets the value of the functionColumns property.
	 *
	 * @return possible object is {@link FunctionColumnsType }
	 *
	 */
	public FunctionColumnsType getFunctionColumns() {
		return this.functionColumns;
	}

	/**
	 * Sets the value of the functionColumns property.
	 *
	 * @param value
	 *            allowed object is {@link FunctionColumnsType }
	 *
	 */
	public void setFunctionColumns(FunctionColumnsType value) {
		this.functionColumns = value;
	}

	/**
	 * Gets the value of the sentence property.
	 *
	 * @return possible object is {@link SentenceType }
	 *
	 */
	public SentenceType getSentence() {
		return this.sentence;
	}

	/**
	 * Sets the value of the sentence property.
	 *
	 * @param value
	 *            allowed object is {@link SentenceType }
	 *
	 */
	public void setSentence(SentenceType value) {
		this.sentence = value;
	}

	/**
	 * Gets the value of the return property.
	 *
	 * @return possible object is {@link ReturnType }
	 *
	 */
	public ReturnType getReturn() {
		return this._return;
	}

	/**
	 * Sets the value of the return property.
	 *
	 * @param value
	 *            allowed object is {@link ReturnType }
	 *
	 */
	public void setReturn(ReturnType value) {
		this._return = value;
	}

	/**
	 * Gets the value of the id property.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getId() {
		return this.id;
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

	/**
	 * Gets the value of the syntax property.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getSyntax() {
		return this.syntax;
	}

	/**
	 * Sets the value of the syntax property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	public void setSyntax(String value) {
		this.syntax = value;
	}

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.12 at 02:19:17 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for entryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="entryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="headword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="part-of-speech" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="senses" type="{}sensesType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entryType", propOrder = {
    "headword",
    "partOfSpeech",
    "senses"
})
public class EntryType {

    @XmlElement(required = true)
    protected String headword;
    @XmlElement(name = "part-of-speech", required = true)
    protected String partOfSpeech;
    @XmlElement(required = true)
    protected SensesType senses;

    /**
     * Gets the value of the headword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeadword() {
        return headword;
    }

    /**
     * Sets the value of the headword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeadword(String value) {
        this.headword = value;
    }

    /**
     * Gets the value of the partOfSpeech property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    /**
     * Sets the value of the partOfSpeech property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartOfSpeech(String value) {
        this.partOfSpeech = value;
    }

    /**
     * Gets the value of the senses property.
     * 
     * @return
     *     possible object is
     *     {@link SensesType }
     *     
     */
    public SensesType getSenses() {
        return senses;
    }

    /**
     * Sets the value of the senses property.
     * 
     * @param value
     *     allowed object is
     *     {@link SensesType }
     *     
     */
    public void setSenses(SensesType value) {
        this.senses = value;
    }

}

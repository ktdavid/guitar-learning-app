package hu.unideb.inf.prt.guitarlearningapplication.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Public class for the ChordType enumeration.
 * 
 * @author Dávid Kistamás
 * @version 1.0
 */
@XmlEnum(String.class)
public enum ChordType {
	
	@XmlEnumValue("Major") 
	MAJOR,
	
	@XmlEnumValue("Minor") 
	MINOR,
	
	@XmlEnumValue("Augmented") 
	AUGMENTED,
	
	@XmlEnumValue("Diminished") 
	DIMINISHED
}

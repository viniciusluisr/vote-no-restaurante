package br.com.vote.no.restaurante.aspect;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Vinicius on 21/12/15.
 */
@XmlType(name = "Type")
@XmlEnum
public enum MessageType {

	@XmlEnumValue("Unexpected_Error")
	Unexpected_Error("Some type of error occurred."),
	@XmlEnumValue("User_Not_Authorized")
	User_Not_Authorized("Authentication failed or access to some resource is denied."),
	@XmlEnumValue("Parameter_Error")
	Parameter_Error("A require param was missing, or malformed."),
	@XmlEnumValue("Unsuported_MediaType_Error")
	Unsuported_MediaType_Error("The request entity is in a format not supported."),
	@XmlEnumValue("Range_Limit_Error")
	Range_Limit_Error("The limit of query range is too high."),
	@XmlEnumValue("Endpoint_Error")
	Endpoint_Error("Some error occurred when try to access the resource."),
	@XmlEnumValue("Business_Logic_Error")
	Business_Logic_Error("Business logic error."),
	@XmlEnumValue("Internal_Architecture_Error")
	Internal_Architecture_Error("Ooops! some big problem found.");

	private final String description;

	private MessageType(final String des) {
		description = des;
	}

	public String getDescription() {
		return description;
	}
}
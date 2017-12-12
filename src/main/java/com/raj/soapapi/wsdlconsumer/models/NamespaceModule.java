package com.raj.soapapi.wsdlconsumer.models;

public class NamespaceModule {

	private String nameSpace = "";
	private String nameSpaceElement = "";

	private String recordDelimeter = "";

	public String getRecordDelimeter() {
		return recordDelimeter;
	}

	public void setRecordDelimeter(String recordDelimeter) {
		this.recordDelimeter = recordDelimeter;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getNameSpaceElement() {
		return nameSpaceElement;
	}

	public void setNameSpaceElement(String nameSpaceElement) {
		this.nameSpaceElement = nameSpaceElement;
	}

}

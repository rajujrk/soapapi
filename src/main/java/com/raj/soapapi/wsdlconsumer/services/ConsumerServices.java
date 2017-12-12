package com.raj.soapapi.wsdlconsumer.services;

import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.AxisFault;

public interface ConsumerServices {

	public List<HashMap<String, String>> consumeService(String serviceEndPointUrl) throws AxisFault, XMLStreamException;

	public void setGlobalNs();
	
	public void setBodyContent(String serviceName, HashMap<String, Object> hmElement);
	
	public void setHeaderContent(String serviceName, HashMap<String, Object> hmElement);
}

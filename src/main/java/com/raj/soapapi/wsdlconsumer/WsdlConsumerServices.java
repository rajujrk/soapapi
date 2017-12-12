package com.raj.soapapi.wsdlconsumer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.OperationClient;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl.Authenticator;

import com.raj.soapapi.wsdlconsumer.models.NamespaceModule;
import com.raj.soapapi.wsdlconsumer.services.ConsumerServices;

public class WsdlConsumerServices implements ConsumerServices {

	private HttpTransportPropertiesImpl.Authenticator authenticator = null;
	private NamespaceModule namespaceModule = null;
	private OMNamespace globalNs = null;

	private Integer timeOutMilliSeconds = 60000;

	/**
	 * @return the timeOutMilliSeconds
	 */
	public Integer getTimeOutMilliSeconds() {
		return timeOutMilliSeconds;
	}

	/**
	 * @param timeOutMilliSeconds
	 *            the timeOutMilliSeconds to set
	 */
	public void setTimeOutMilliSeconds(Integer timeOutMilliSeconds) {
		this.timeOutMilliSeconds = timeOutMilliSeconds;
	}

	private OMElement bodyContent = null;

	private OMElement headerContent = null;

	private SOAPFactory fac = null;
	// Create the request envelope
	private SOAPEnvelope envelope = null;

	public WsdlConsumerServices() {
		// TODO Auto-generated constructor stub
		this.fac = OMAbstractFactory.getSOAP11Factory();
		this.envelope = fac.getDefaultEnvelope();
	}

	public WsdlConsumerServices(Authenticator authenticator, NamespaceModule namespaceModule) throws IOException {
		// TODO Auto-generated constructor stub
		this.authenticator = authenticator;
		this.namespaceModule = namespaceModule;
		this.fac = OMAbstractFactory.getSOAP11Factory();
		this.envelope = fac.getDefaultEnvelope();
	}

	private List<HashMap<String, String>> generateList(SOAPEnvelope response) throws XMLStreamException {

		List<HashMap<String, String>> alList = new ArrayList<HashMap<String, String>>();

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();

		XMLStreamReader streamReader = inputFactory.createXMLStreamReader(new StringReader(response.toString()));

		if (response.toString().toLowerCase().indexOf(namespaceModule.getRecordDelimeter()) == -1) {
			namespaceModule.setRecordDelimeter("results");
		}
		/*
		 * System.out.println(streamReader.nextTag()); // Advance to //
		 * "soap:Envelope" element System.out.println(streamReader.nextTag());
		 * // Advance to "soap:Body" // element
		 * System.out.println(streamReader.nextTag()); // Advance to //
		 * "ns2:rfqHeaderImportResponse" // element
		 * System.out.println(streamReader.nextTag()); // Advance to "return" //
		 * element System.out.println(streamReader.nextTag()); // Advance to
		 * "return" // element
		 */ HashMap<String, String> hmElements = new HashMap<String, String>();

		Boolean blFlg = new Boolean(true);
		while (streamReader.hasNext()) {
			if (streamReader.isStartElement()) {
				String strLocalName = streamReader.getLocalName();

				if (!strLocalName.equalsIgnoreCase(namespaceModule.getRecordDelimeter()) && blFlg) {
					streamReader.nextTag();
					continue;
				} else {
					blFlg = false;
				}
				if (strLocalName.trim().toLowerCase().equals(namespaceModule.getRecordDelimeter())) {
					hmElements = new HashMap<String, String>();

				} else {
					hmElements.put(strLocalName, streamReader.getElementText());
					// System.out.println(strLocalName + " : " +
					// streamReader.getElementText());
				}
			}
			if (streamReader.isEndElement()) {
				String strLocalName = streamReader.getLocalName();
				if (strLocalName.trim().toLowerCase().equals(namespaceModule.getRecordDelimeter())) {
					alList.add(hmElements);
				}
			}
			streamReader.next();
		}
		// alList.add(hmElements);
		// System.out.println(alList.size());

		return alList;

	}

	public List<HashMap<String, String>> consumeService(String serviceEndPointUrl)
			throws AxisFault, XMLStreamException {
		// TODO Auto-generated method stub
		ServiceClient client = new ServiceClient();

		OperationClient operationClient = client.createClient(ServiceClient.ANON_OUT_IN_OP);

		// creating message context
		MessageContext outMsgCtx = new MessageContext();
		// assigning message context’s option object into instance variable
		Options opts = outMsgCtx.getOptions();

		// setting properties into option
		opts.setTo(new EndpointReference(serviceEndPointUrl));
		opts.setAction(""); // your soap action is null
		opts.setProperty(HTTPConstants.SO_TIMEOUT, new Integer(timeOutMilliSeconds));
		opts.setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(timeOutMilliSeconds));

		if (authenticator != null) {
			opts.setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, authenticator);
		}
		// method.addChild(elements);

		if (null != this.headerContent) {
			envelope.getHeader().addChild(this.headerContent);
		}
		if (null != this.bodyContent) {
			envelope.getBody().addChild(this.bodyContent);
		}

		envelope.build();

		System.out.println(envelope);

		outMsgCtx.setEnvelope(envelope);

		operationClient.addMessageContext(outMsgCtx);
		operationClient.execute(true);
		MessageContext inMsgtCtx = operationClient.getMessageContext("In");
		// get the response
		SOAPEnvelope response = inMsgtCtx.getEnvelope();

		return generateList(response);
	}

	private OMElement createBodyOMElement(String serviceName, HashMap<String, Object> hmElement) {
		// TODO Auto-generated method stub
		OMElement method = this.fac.createOMElement(serviceName, this.globalNs);
		for (Map.Entry<String, Object> element : hmElement.entrySet()) {
			String key = element.getKey();
			Object value = element.getValue();

			OMElement elementData = this.fac.createOMElement(key, null);

			if (value instanceof String) {

				elementData.setText((String) value);
				method.addChild(elementData);
			}

			if (value instanceof HashMap) {
				OMElement subelementData = this.fac.createOMElement(key, null);
				HashMap<String, Object> hmSubElement = (HashMap<String, Object>) value;
				for (Map.Entry<String, Object> subelement : hmSubElement.entrySet()) {
					String keys = subelement.getKey();
					Object values = subelement.getValue();
					OMElement subelementData1 = this.fac.createOMElement(keys, null);
					subelementData1.setText((String) values);
					subelementData.addChild(subelementData1);
				}
				method.addChild(subelementData);
			}

		}

		return method;
	}

	private OMElement createHeaderOMElement(String serviceName, HashMap<String, Object> hmElement) {
		// TODO Auto-generated method stub
		OMElement method = this.fac.createOMElement(serviceName, this.globalNs);
		for (Map.Entry<String, Object> element : hmElement.entrySet()) {
			String key = element.getKey();
			Object value = element.getValue();

			OMElement elementData = this.fac.createOMElement(key, this.globalNs);

			if (value instanceof String) {
				elementData.setText((String) value);
				method.addChild(elementData);
			}

			if (value instanceof HashMap) {
				OMElement subelementData = this.fac.createOMElement(key, null);
				HashMap<String, Object> hmSubElement = (HashMap<String, Object>) value;
				for (Map.Entry<String, Object> subelement : hmSubElement.entrySet()) {
					String keys = subelement.getKey();
					Object values = subelement.getValue();
					OMElement subelementData1 = this.fac.createOMElement(keys, null);
					subelementData1.setText((String) values);
					subelementData.addChild(subelementData1);
				}
				method.addChild(subelementData);
			}

		}

		return method;
	}

	public HttpTransportPropertiesImpl.Authenticator getAuthenticator() {
		return authenticator;
	}

	public void setAuthenticator(HttpTransportPropertiesImpl.Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	public NamespaceModule getNamespaceModule() {
		return namespaceModule;
	}

	public void setNamespaceModule(NamespaceModule namespaceModule) {
		this.namespaceModule = namespaceModule;
	}

	public OMNamespace getGlobalNs() {
		return globalNs;
	}

	public void setGlobalNs() {
		this.globalNs = fac.createOMNamespace(namespaceModule.getNameSpace(), namespaceModule.getNameSpaceElement());
	}

	public OMElement getBodyContent() {
		return bodyContent;
	}

	public void setBodyContent(String serviceName, HashMap<String, Object> hmElement) {
		this.bodyContent = createBodyOMElement(serviceName, hmElement);
	}

	public OMElement getHeaderContent() {
		return headerContent;
	}

	public void setHeaderContent(String serviceName, HashMap<String, Object> hmElement) {
		this.headerContent = createHeaderOMElement(serviceName, hmElement);
	}

}

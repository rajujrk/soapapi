

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl.Authenticator;

import com.raj.soapapi.wsdlconsumer.WsdlConsumerServices;
import com.raj.soapapi.wsdlconsumer.exception.ConsumeException;
import com.raj.soapapi.wsdlconsumer.models.NamespaceModule;

public class ServiceManagerImpl {

	Authenticator authenticate = null;

	NamespaceModule namespaceModule = null;

	public ServiceManagerImpl() {
		// TODO Auto-generated constructor stub
		this.authenticate = loadAuthenticator();
		this.namespaceModule = getNamespace();
	}

	public ServiceManagerImpl(Authenticator authenticate, NamespaceModule namespaceModule) {
		// TODO Auto-generated constructor stub
		this.authenticate = authenticate;
		this.namespaceModule = namespaceModule;
	}

	public List<HashMap<String, String>> getWSDLResponse(String strEndPoint, HashMap<String, Object> hmElements,
			String serviceName) throws ConsumeException, AxisFault {
		// TODO Auto-generated method stub
		WsdlConsumerServices consumerServices = null;
		List<HashMap<String, String>> alMap = new ArrayList<>();
		try {
			consumerServices = new WsdlConsumerServices(null, namespaceModule);
			consumerServices.setGlobalNs();
			consumerServices.setTimeOutMilliSeconds(300000);
			consumerServices.setBodyContent(serviceName, hmElements);
			alMap = consumerServices.consumeService(strEndPoint);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ConsumeException(e.getMessage());
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			throw new ConsumeException(e.getMessage());
		}

		return alMap;
	}

	private NamespaceModule getNamespace() {

		NamespaceModule namespaceModule = new NamespaceModule();

		namespaceModule.setNameSpace("http://service.wss.phoenix.com/");
		namespaceModule.setNameSpaceElement("ser"); // Example <soapenv:Body>
													// <ws:serviceName>

		namespaceModule.setRecordDelimeter("return"); // Example <return>
														// <message>Test</message><status>false</status>
														// </return>

		return namespaceModule;
	}

	private Authenticator loadAuthenticator() {
		// TODO Auto-generated method stub
		// Return null if your service doesn't have authentication
		HttpTransportPropertiesImpl.Authenticator auth = new HttpTransportPropertiesImpl.Authenticator();
		String strUsername = "USERNAME";
		String strPassword = "PASSWORD";
		auth.setUsername(strUsername);
		auth.setPassword(strPassword);

		return auth;
	}
}
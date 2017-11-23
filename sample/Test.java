

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl.Authenticator;


public class Test {

	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		ServiceManagerImpl serviceManagerImpl = new ServiceManagerImpl();
		List<HashMap<String, String>> alMap = new ArrayList<>();
		
		HashMap<String, Object> hmBody = new HashMap<String, Object>();

		String accNumber = "42343432";
		String strTransid = "577676BS86868V";
		String paidAmount = "234.00";
		String payMode = "Wallet";
		String payDate = "22-11-2017";
		
		String strEndPoint = "END_POINT_URL";
		String serviceName = "SERVICE_NAME";

		
		hmBody.put("accNo", accNumber);
		hmBody.put("txnId", strTransid);
		hmBody.put("paidAmt", paidAmount);
		hmBody.put("paySource", payMode);
		hmBody.put("payDate", payDate);
		
		try {
			alMap = serviceManager.getWSDLResponse(strEndPoint, hmBody, serviceName);
		}catch(Exception ex){
			
		}
		System.out.println("Result = " + alMap);
	
	}
	

}

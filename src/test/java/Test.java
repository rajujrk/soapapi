
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		ServiceManagerImpl serviceManagerImpl = new ServiceManagerImpl();
		List<HashMap<String, String>> alMap = new ArrayList<>();

		HashMap<String, Object> hmBody = formConsumerBody();

		String strEndPoint = "https://puvvnlwss.mpower.in/wss/services/mobileAppUserLogin";
		String serviceName = "getSCNOAccountNoDetails";

		try {
			alMap = serviceManagerImpl.getWSDLResponse(strEndPoint, hmBody, serviceName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Result = " + alMap);

	}

	private static HashMap<String, Object> formConsumerBody() {

		HashMap<String, Object> hmBody = new HashMap<>();

		hmBody.put("SCNO", "");
		hmBody.put("ACCOUNTNO", "781705363470");
		hmBody.put("DISCOMCODE", "");
		hmBody.put("REQUESTFOR", "SCNOACCDETAILS");

		return hmBody;

	}

}
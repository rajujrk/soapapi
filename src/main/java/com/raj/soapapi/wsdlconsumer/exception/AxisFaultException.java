package com.raj.soapapi.wsdlconsumer.exception;

import org.apache.axis2.AxisFault;

public class AxisFaultException extends AxisFault {

	/**
	 * 
	 */
	private static final long serialVersionUID = 541311859275243595L;
	 

	public AxisFaultException() {
		// TODO Auto-generated constructor stub
		this("Unable to process");
	}

	public AxisFaultException(String strMessage) {
		// TODO Auto-generated constructor stub
		super(strMessage);
	}
	
	

}

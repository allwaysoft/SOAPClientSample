import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class SOAPWSClientSAAJ {
	public String invokeWebService(String destination, String strSOAPRequest) throws SOAPException, IOException {
		SOAPConnection connection;
		String response;
		connection = null;
		response = "";
		try {
			SOAPMessage request = createSOAPMessage(strSOAPRequest);
			SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
			connection = soapConnFactory.createConnection();
			SOAPMessage reply = connection.call(request, destination);
			response = getSOAPResponse(reply);
		} catch (javax.xml.soap.SOAPException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (javax.xml.soap.SOAPException e) {
					e.printStackTrace();
				}
		}
		return response;
	}

	private SOAPMessage createSOAPMessage(String strRequestXML) throws SOAPException, IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(strRequestXML.getBytes());
		MessageFactory messageFactory = MessageFactory.newInstance();
		MimeHeaders mimeHeaders = new MimeHeaders();
		mimeHeaders.addHeader("SOAPAction", "");
		mimeHeaders.addHeader("Content-Type", "text/xml; charset=UTF-8");
		SOAPMessage requestMsg = messageFactory.createMessage(mimeHeaders, is);
		return requestMsg;
	}

	private java.lang.String getSOAPResponse(SOAPMessage reply) throws SOAPException, IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		String strSOAPResponse = "";
		reply.writeTo(os);
		strSOAPResponse = new String(os.toByteArray());
		return strSOAPResponse;
	}
}

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
public class SAAJClientDemo {
	private static SOAPMessage createSoapRequest() throws Exception{
		 MessageFactory messageFactory = MessageFactory.newInstance();
		 SOAPMessage soapMessage = messageFactory.createMessage();
		 SOAPPart soapPart = soapMessage.getSOAPPart();
    	         SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
    	         soapEnvelope.addNamespaceDeclaration("end", "http://service.web.com");
		 SOAPBody soapBody = soapEnvelope.getBody();
		 SOAPElement soapElement = soapBody.addChildElement("subtractValue", "end");
		 SOAPElement element1 = soapElement.addChildElement("value");
		 element1.addTextNode("100");
		 soapMessage.saveChanges();
		 System.out.println("----------SOAP Request------------");
		 soapMessage.writeTo(System.out);
		 return soapMessage;
	 }
	 private static void createSoapResponse(SOAPMessage soapResponse) throws Exception  {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		System.out.println("\n----------SOAP Response-----------");
		StreamResult result = new StreamResult(System.out);
		transformer.transform(sourceContent, result);
	 }
	 

			

	 public static void main(String args[]){
	        try{
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			String url = "http://localhost:8080/SOAPServer/services/HelloWorld?wsdl";
			SOAPMessage soapRequest = createSoapRequest();
			//hit soapRequest to the server to get response
			SOAPMessage soapResponse = soapConnection.call(soapRequest, url);
			createSoapResponse(soapResponse);
			soapConnection.close();
			
		}catch (Exception e) {
		     e.printStackTrace();
		}
	 }
}
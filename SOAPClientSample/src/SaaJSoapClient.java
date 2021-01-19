import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * SOAP Client Implementation using SAAJ Api.
 */
public class SaaJSoapClient {
	/**
	 * Method used to create the SOAP Request
	 */
	private static SOAPMessage createSOAPRequest() throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		/*
		 * Construct SOAP Request Message: <soap:Envelope
		 * xmlns:soap="http://www.w3.org/2003/05/soap-envelope"
		 * xmlns:sam="http://samples.axis2.techdive.in"> <soap:Header/> <soap:Body>
		 * <sam:getStudentName> <!--Optional:--> <sam:rollNumber>3</sam:rollNumber>
		 * </sam:getStudentName> </soap:Body> </soap:Envelope>
		 */

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("sam", "http://service.web.com");

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("addValue", "sam");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("value", "sam");
		soapBodyElem1.addTextNode("80");

		soapMessage.saveChanges();

		// Check the input
		System.out.println("Request SOAP Message = ");
		soapMessage.writeTo(System.out);
		System.out.println();
		return soapMessage;
	}

	/**
	 * Method used to print the SOAP Response
	 */
	private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		System.out.println("\nResponse SOAP Message = ");
		StreamResult result = new StreamResult(System.out);
		transformer.transform(sourceContent, result);
	}

	public static String getvaluefromxml(String inxml) {
		System.out.println(inxml);
		String tempxml = inxml;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		String outval = null;
		try {
			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(tempxml));
			Document doc = builder.parse(is);

			Element root = doc.getDocumentElement();
			NodeList children = root.getChildNodes();
			System.out.println(children.toString());
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if (child instanceof Element) {
					var childElement = (Element) child;
					System.out.println(childElement.getTagName());
					System.out.println(childElement.getTextContent());
				}
				System.out.println(child.getNodeName());
				System.out.println(child.getNodeValue());

			}

			NodeList list = doc.getElementsByTagName("addValueReturn");
			outval = list.item(0).getTextContent();
			// System.out.println(outval);

		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
		return outval;

	}

	/**
	 * Starting point for the SAAJ - SOAP Client Testing
	 */
	public static void main(String args[]) throws FileNotFoundException {
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			String url = "http://localhost:8080/SOAPServer/services/HelloWorld?wsdl";
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			String strSOAPResponse = "";
			soapResponse.writeTo(os);
			strSOAPResponse = new String(os.toByteArray());
			System.out.println(strSOAPResponse);
			System.out.println(getvaluefromxml(strSOAPResponse));
			try {
				XMLInputFactory xif = XMLInputFactory.newFactory();
				final StringReader reader = new StringReader(strSOAPResponse);
				XMLStreamReader xsr = xif.createXMLStreamReader(reader);
				xsr.nextTag(); // Advance to Envelope tag

				xsr.nextTag(); // Advance to Body tag
				xsr.nextTag();
				// xsr.nextTag();

				JAXBContext jc = JAXBContext.newInstance(addValueReturn.class);
				Unmarshaller unmarshaller = jc.createUnmarshaller();
				JAXBElement<addValueReturn> je = unmarshaller.unmarshal(xsr, addValueReturn.class);

				// System.out.println(je.getName());
				// System.out.println(je.getValue());
				System.out.println("SOAP Response addValueReturn: " + je.getValue().getaddValueReturn());
			} catch (XMLStreamException e) {
				e.printStackTrace();
			} catch (JAXBException e) {
				e.printStackTrace();
			}

			// Process the SOAP Response
			printSOAPResponse(soapResponse);

			soapConnection.close();
		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
		}
	}
}
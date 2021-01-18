import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ParseSOAPXML {
	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><addValueResponse xmlns=\"http://service.web.com.crunchify\"><addValueReturn>50.0</addValueReturn></addValueResponse></soapenv:Body></soapenv:Envelope>\r\n";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		try {
			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(xml));
			Document doc = builder.parse(is);
			NodeList list = doc.getElementsByTagName("addValueReturn");
			System.out.println(list.item(0).getTextContent());
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
	}
}
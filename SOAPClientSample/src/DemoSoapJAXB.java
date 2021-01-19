import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class DemoSoapJAXB {
	public static void main(String[] args) {
        try {
            XMLInputFactory xif = XMLInputFactory.newFactory();
            XMLStreamReader xsr = xif.createXMLStreamReader(new FileReader("soapnew.xml"));
            xsr.nextTag(); // Advance to Envelope tag

            xsr.nextTag(); // Advance to Body tag
            xsr.nextTag();
            //xsr.nextTag();


            JAXBContext jc = JAXBContext.newInstance(addValueReturn.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement<addValueReturn> je = unmarshaller.unmarshal(xsr, addValueReturn.class);

            System.out.println(je.getName());
            System.out.println(je.getValue());
            System.out.println(je.getValue().getaddValueReturn());
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}

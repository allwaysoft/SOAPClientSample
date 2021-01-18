import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XML2Object {
	public static void main(String[] args) {
	
	File xmlFile = new File("c:\\employee.xml");
	 
	JAXBContext jaxbContext;
	try
	{
	    jaxbContext = JAXBContext.newInstance(Employee.class);              
	 
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	 
	    Employee employee = (Employee) jaxbUnmarshaller.unmarshal(xmlFile);
	     
	    System.out.println(employee);
	}
	catch (JAXBException e) 
	{
	    e.printStackTrace();
	}
	}

}

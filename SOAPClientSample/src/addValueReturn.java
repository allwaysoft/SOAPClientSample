import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "addValueResponse", namespace = "http://service.web.com")
@XmlAccessorType(XmlAccessType.FIELD)
public class addValueReturn {
	@XmlElement(name = "addValueReturn", namespace = "http://service.web.com")
	private String addValueReturn;

	public String getaddValueReturn() {
		return addValueReturn;
	}

	public void setaddValueReturn(String addValueReturn) {
		this.addValueReturn = addValueReturn;
	}

}

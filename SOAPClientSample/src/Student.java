

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement
public class Student{
 
   String name;
   int age;
   int id;

   public String getName(){
      return name;
   }

   @XmlElement
   public void setName(String name){
      this.name = name;
   }

   public int getAge(){
      return age;
   }

   @XmlElement
   public void setAge(int age){
      this.age = age;
   }

   public int getId(){
      return id;
   }

   @XmlAttribute
   public void setId(int id){
      this.id = id;
   }
  
   public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Student [");
      stringBuilder.append("ID = ");
      stringBuilder.append(id);
      stringBuilder.append(", Name = ");
      stringBuilder.append(name);
      stringBuilder.append(", Age = ");
      stringBuilder.append(age);
      stringBuilder.append("]");
      return stringBuilder.toString();
   }
}
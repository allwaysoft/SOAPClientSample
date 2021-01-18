

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class JAXBElementDemo {
   public static void main(String[] args) {
      //Create a student object
      Student student = new Student();

      //fill details of the student
      student.setName("Robert");
      student.setId(1);
      student.setAge(12);
      
      //create JAXBElement of type Student
      //Pass it a null object
      JAXBElement<Student> jaxbElement =  new JAXBElement( 
         new QName(Student.class.getSimpleName()), Student.class, null);

      Student retrivedStudent;

      //check if content model not null
      if(!jaxbElement.isNil()){
         //get the content values as Student object
         retrivedStudent = jaxbElement.getValue();
         //print the result
         System.out.println("Student #1: "+retrivedStudent.toString());  
      } else {
         jaxbElement.setValue(student);   		  
      }

      //get the content values as Student object
      retrivedStudent = jaxbElement.getValue();
	  
      //print the result
      System.out.println("Student #2:"+retrivedStudent.toString());  
   }
}
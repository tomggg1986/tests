package xml.parser;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.util.AbstractList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.RandomAccess;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Xmlparse {
	static final class NodeListWrapper extends AbstractList<Node>
	  implements RandomAccess {
	    private final NodeList list;
	    NodeListWrapper(NodeList l) {
	      list=l;
	    }
	    public Node get(int index) {
	      return list.item(index);
	    }
	    public int size() {
	      return list.getLength();
	    }
	  }

   public void parseXML(String[] args) {

      try {
    	  Date date = new Date();
    	  LocalDate  ldate = LocalDate.now();
    	 
    	 System.out.println("Start " + Instant.now());
         File inputFile = new File("C:\\Users\\Tomek\\OneDrive\\Public\\WorkSpace\\Eclipse\\tests\\src\\main\\java\\xml\\parser\\data.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("student");
//         NodeListWrapper nodeWrapper = new NodeListWrapper(nList);
//         nodeWrapper.stream().map(el -> el.getNodeName()).forEach(System.out::println);
//         Stream<Node> nodeStream = IntStream.range(0, nList.getLength())
//                 .mapToObj(nList::item);
         System.out.println("First Name: " + this.readValue(nList.item(0)));
//         List<String> list = nodeWrapper.stream().map(el -> el.getNodeName()).collect(Collectors.toList());
//         list.forEach(System.out::print);
         System.out.println("----------------------------");
        // Xmlparse.read(nList);
         System.out.println("----------------------------");
//         for (int temp = 0; temp < nList.getLength(); temp++) {
//            Node nNode = nList.item(temp);
//            System.out.println("\nCurrent Element :" + nNode.getNodeName());
//            
//            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//               Element eElement = (Element) nNode;
//               System.out.println("Student roll no : " 
//                  + eElement.getAttribute("rollno"));
//               System.out.println("First Name : " 
//                  + eElement
//                  .getElementsByTagName("firstname")
//                  .item(0)
//                  .getTextContent());
//               System.out.println("Last Name : " 
//                  + eElement
//                  .getElementsByTagName("lastname")
//                  .item(0)
//                  .getTextContent());
//               System.out.println("Nick Name : " 
//                  + eElement
//                  .getElementsByTagName("nickname")
//                  .item(0)
//                  .getTextContent());
//               System.out.println("Marks : " 
//                  + eElement
//                  .getElementsByTagName("marks")
//                  .item(0)
//                  .getTextContent());
//            }
//         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      System.out.println("End " + Instant.now());
   }
   private static void  read(NodeList list) {
	   if(list.getLength() >0) {
		   Node node = list.item(0);
		   while(true) {
			   if(node.getNodeType() == Node.ELEMENT_NODE) {
				   System.out.println("Node: "+node.getNodeName() +" "+ node.getTextContent());
			   }		   
			   node = node.getNextSibling();
			   if(node == null)
				   break;
		   }
	   }
   }
   private String readValue(Node node) {
	   NodeList nList = node.getChildNodes();
	   Stream<Node> nodeStream = IntStream.range(0, nList.getLength()).mapToObj(nList::item);
	   Optional<String> fname =  nodeStream
			   .peek(p -> System.out.println(p.getNodeName()))
			   .filter(f -> f.getNodeName().equals("firstname"))
			   .flatMap(n ->{
				   NodeList nL = n.getChildNodes();
				   Stream<Node> stream = IntStream.range(0, nL.getLength()).mapToObj(nL::item);
				   return stream;
			   })			  
			   .map(n -> n.getNodeValue()).findFirst();
	  // Optional<Node> lname =  nodeStream.filter(f -> f.getNodeName().equals("Last Name")).findFirst();
	   if(fname.isPresent()) {
		   return fname.get();
	   }else
		   return null;
   }
}

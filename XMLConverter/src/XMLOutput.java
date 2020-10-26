import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XMLOutput {
	public static   boolean isFirstElemnt=true;
    static Element element = null;
    public static Element mainRoot =null;
	public static void main(String[] args) {
		 try {
	            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = docFactory.newDocumentBuilder();
	            Document document = builder.newDocument();
	            String root,a1="a1";
	            Map<String, String> data = new HashMap<String, String>();
	            data.put("A.B.C.C1.C11", "C11");
	            data.put("A.B.C.C1.C12", "C12");
	            data.put("A.B.C.C2.C21", "C21");
	            data.put("A.B.C.C2.C22", "C22");
	            data.put("A.B.C.C3.C31", "C31");
	            data.put("A.B.C.C.D", "D");

	            data = new TreeMap<String, String>(data);
	            Set set1 = data.entrySet();
	            Iterator i1 = set1.iterator();
	            while(i1.hasNext()) {
	                Map.Entry me1 = (Map.Entry)i1.next();
	                System.out.println(me1.getKey() + ": ");
	                //System.out.println(me1.getValue());

	                //create Buddy XML
	                String key= (String) me1.getKey();
	                String[] buddyXML= key.split("\\.");

	                NodeList rootElementList = document.getElementsByTagName(key);
	                if(!(rootElementList.getLength()>0)){
	                    mainRoot = document.createElement(key);
	                    document.appendChild(mainRoot);
	                    System.out.println("root created\n");
	                }

	                for(int i=0; i<buddyXML.length; i++){
	                    System.out.println("value: "+buddyXML[i]);
	                    NodeList elementList = document.getElementsByTagName(buddyXML[i]);
	                    System.out.println("here "+elementList.getLength());
	                    System.out.println("creating Node "+buddyXML[i]);
	                    if(!(elementList.getLength()>0)){
	                        System.out.println("creating Node "+buddyXML[i] + " & main root: "+mainRoot.getNodeName());
	                        Element element =createNode(buddyXML[i], document, mainRoot);
	                        mainRoot = element;
	                    }
	                }
	                System.out.println("");
	            }

	            TransformerFactory tFactory = TransformerFactory.newInstance();
	            Transformer transformer = tFactory.newTransformer();
	            // Add indentation to outputs
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	            DOMSource source = new DOMSource(document);
	            StreamResult result = new StreamResult(new File("C://share/BuddyCode.xml"));
	            transformer.transform(source, result);

	        } catch (Exception e) {
	            System.out.println("Exception: "+e);
	        }
		 		 
	}
	
	 public static void createElement(Element elemnetName, Document document, Object data) {
	        Text text =document.createTextNode(data.toString());
	        elemnetName.appendChild(text);
	    }

	    private static Element createNode(String nodeName, Document document,Element rootElement) {
	        Element node = document.createElement(nodeName);
	        rootElement.appendChild(node);
	        return node;
	    }

}

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLWriter1 {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, XMLStreamException, FactoryConfigurationError {
		
		
		OutputStream outputStream = new FileOutputStream(new File("doc.xml"));

		XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(
		                new OutputStreamWriter(outputStream, "utf-8"));

		out.writeStartDocument();
		out.writeStartElement("doc");

		out.writeStartElement("title");
		out.writeCharacters("Document Title");
		out.writeEndElement();

		out.writeEndElement();
		out.writeEndDocument();

		out.close();
		saveToXML("test.xml");
	}
	
	public static void saveToXML(String xml) {
	    Document dom;
	    Element e = null;
	    Element f = null;
	    Map<String, Map<String, String>> eimMetricsMap = new HashMap<String, Map<String, String>>();
	    Map<String, String> jsServerMap = new HashMap<String,String>();
	    jsServerMap.put("os", "Windows Server 2008 6.1");
	    jsServerMap.put("cpu", "AMD64");
	    jsServerMap.put("host_name", "SAMBITLM");
	    jsServerMap.put("cores", "4");
	    jsServerMap.put("host_addr", "234.456.7");
	    eimMetricsMap.put("IPSSYSTEM", jsServerMap);
	    Map<String, String> dsServerMap = new HashMap<String,String>();
	    dsServerMap.put("os", "Windows Server 2008 6.1");
	    dsServerMap.put("cpu", "INTEL");
	    dsServerMap.put("host_name", "SAMBADI");
	    dsServerMap.put("cores", "4");
	    dsServerMap.put("host_addr", "234.456.7");
	    eimMetricsMap.put("DISYSTEM", dsServerMap);
	    // instance of a DocumentBuilderFactory
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        // use factory to get an instance of document builder
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        // create instance of DOM
	        dom = db.newDocument();
	        
	        // create the root element
	        Element rootEle = dom.createElement("roles");
	        Element rootEleNew = dom.createElement("rolesNew");
	        
	        // create data elements and place them under root
	        e = dom.createElement("role1");
	      //  e.appendChild(dom.createTextNode("role1"));
	        rootEle.appendChild(e);
	        for(int i=0;i<3;i++){
	        f = dom.createElement("role1");
	        f.appendChild(dom.createTextNode("role1"));
	        e.appendChild(f);
	        }
	        e = dom.createElement("role2");
	        e.appendChild(dom.createTextNode("role2"));
	        rootEle.appendChild(e);

	        e = dom.createElement("role3");
	        e.appendChild(dom.createTextNode("role3"));
	        rootEle.appendChild(e);

	        e = dom.createElement("role4");
	        e.appendChild(dom.createTextNode("role4"));
	        rootEle.appendChild(e);

	        dom.appendChild(rootEle);

	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.INDENT, "yes");
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	            tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
	            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	            // send DOM to file
	            tr.transform(new DOMSource(dom), 
	                                 new StreamResult(new FileOutputStream(xml)));

	        } catch (TransformerException te) {
	            System.out.println(te.getMessage());
	        } catch (IOException ioe) {
	            System.out.println(ioe.getMessage());
	        }
	    } catch (ParserConfigurationException pce) {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
	    }
	}
}

package fr.xs.jtk.format.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLWriter {
	private static DocumentBuilderFactory builderFactory     = DocumentBuilderFactory.newInstance();
	private static TransformerFactory     transformerFactory = TransformerFactory.newInstance();
	private static DocumentBuilder        builder     = null;
	private static Transformer            transformer = null;

	protected Document document;

	static {
		try {
			builder     = builderFactory.newDocumentBuilder();
			transformer = transformerFactory.newTransformer();
		} catch (ParserConfigurationException | TransformerConfigurationException e) {
			e.printStackTrace();
		}
	}

	public Element createElement(String _name) {
		return document.createElement(_name);
	}

	public Element setRootNode(String _name) {
		document = builder.newDocument();
		Element root = document.createElement(_name);
		document.appendChild(root);
		return root;
	}

	public Element addElement(String _name, String _parent) {
		final Element newElement = document.createElement(_name);

		if(_parent.equalsIgnoreCase("root")) {
			document.getDocumentElement().appendChild(newElement);
		} else {
			((Element) document.getDocumentElement().getElementsByTagName(_parent).item(0)).appendChild(newElement);
		}

		return newElement;
	}
	public boolean addElement(Element _elt, String _parent) {
		if(_parent.equalsIgnoreCase("root")) {
			document.getDocumentElement().appendChild(_elt);
		} else {
			((Element) document.getDocumentElement().getElementsByTagName(_parent).item(0)).appendChild(_elt);
		}

		return true;
	}
	public boolean addElement(Element _elt, Element _parent) {
		_parent.appendChild(_elt);
		return true;
	}

/*
	public Document getDocument() {
		return document;
	}
	
	public boolean checkRootNode(String _name) {
		if(document == null)
			return false;

		return document.getDocumentElement().getNodeName().compareToIgnoreCase(_name) == 0;
	}
	
	public Element getElement(String _name, int _index) {
		return (Element) document.getDocumentElement().getElementsByTagName( _name ).item(_index);
	}
	

	public void load(String _path) throws FileNotFoundException {
		try {
			document = builder.parse(new File(_path));
/*
	        System.out.println("HEADER");
	        System.out.println("path : "       + document.getDocumentURI());
	        System.out.println("version : "    + document.getXmlVersion());
	        System.out.println("encodage : "   + document.getXmlEncoding());
	        System.out.println("entrée : "     + document.getInputEncoding());
	        System.out.println("standalone : " + document.getXmlStandalone());
	        System.out.println("erreur : "     + document.getStrictErrorChecking());

	        final Element root = document.getDocumentElement();
	        System.out.println(root.getNodeName());
* /
		} catch (SAXException | IOException e) {
			throw new java.io.FileNotFoundException();
		}
	}
*/
	public void save(String _path) {
		if(document == null)
			return ;

		try {
			final DOMSource    source = new DOMSource(document);
			final StreamResult sortie = new StreamResult(new File(_path));

			transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

			transformer.transform(source, sortie);

		} catch(TransformerException e) {
			e.printStackTrace();
		}
	}

}

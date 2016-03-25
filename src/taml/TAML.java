/*
 * 
 * 
 * 
 */
package taml;

import Elements.animate.SpeakAnimable;
import Elements.background.Background;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.util.Pair;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * TAML.java
 *
 */
public class TAML {

	private static TAML taml = null;

	private final DocumentBuilderFactory dbFactory;
	private final DocumentBuilder dBuilder;
	private Document doc;

	public static final void init() {
		if (taml != null) {
			throw new Error("TAMLloader déjà existant !");
		}
		try {
			taml = new TAML();
		} catch (ParserConfigurationException ex) {
			Logger.getLogger(TAML.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private TAML() throws ParserConfigurationException {
		dbFactory = DocumentBuilderFactory.newInstance();
		dBuilder = dbFactory.newDocumentBuilder();
	}

	private HashMap<String, Pair<Elements.Element, ArrayList<Integer>>> getElements(File file) throws SAXException, IOException {
		doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		HashMap<String, Pair<Elements.Element, ArrayList<Integer>>> ret = new HashMap();

		NodeList nList;
		Node node;
		Element e;

		nList = doc.getElementsByTagName("element");
		for (int i = 0; i < nList.getLength(); i++) {
			node = nList.item(i);
			System.out.println("\nCurrent Element :" + node.getNodeName());
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				e = (Element) node;
				Elements.Element elem = null;
				switch (e.getAttribute("class").toLowerCase()) {
					case "speakanimable":
						elem = new SpeakAnimable(e.getAttribute("scml"),
								Float.parseFloat(e.getAttribute("cx")), Float.parseFloat(e.getAttribute("cy")),
								Double.parseDouble(e.getAttribute("cw")), Double.parseDouble(e.getAttribute("ch")));
						((SpeakAnimable) elem).setPosition(Double.parseDouble(e.getAttribute("x")), Double.parseDouble(e.getAttribute("y")),
								Integer.parseInt(e.getAttribute("withbottom")) == 1);
						break;
					case "background":
						elem = new Background(e.getAttribute("img"));
						break;
					default:
						throw new Error();
				}
				ret.put(e.getAttribute("name"), new Pair(elem, new ArrayList()));
			}
		}

		nList = doc.getElementsByTagName("action_data");
		NodeList aList;
		Element a;
		Pair<Elements.Element, ArrayList<Integer>> pair;
		Elements.Element elem;
		for (int i = 0; i < nList.getLength(); i++) {
			node = nList.item(i);
			System.out.println("\nCurrent Element :" + node.getNodeName());
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				e = (Element) node;
				pair = ret.get(e.getAttribute("forelement"));
				elem = pair.getKey();
				if (elem == null) {
					throw new Error();
				}
				aList = e.getElementsByTagName("action");
				for (int j = 0, k = 0; j < aList.getLength(); j++) {
					if (aList.item(j).getNodeType() == Node.ELEMENT_NODE) {
						a = (Element) aList.item(j);
						switch (a.getAttribute("class").toLowerCase()) {
							case "pause":
								elem.pause(Long.parseLong(a.getAttribute("d")));
								break;
							case "flip":
								elem.flip();
								break;
							case "move":
								elem.move(Double.parseDouble(a.getAttribute("x")), Double.parseDouble(a.getAttribute("y")),
										getInterpolator(a.getAttribute("ipt")), Long.parseLong(a.getAttribute("d")));
								break;
							case "speakpause":
								elem.speakPause(Long.parseLong(a.getAttribute("d")));
								break;
							case "speak":
								elem.speak(a.getTextContent().trim(),
										getInterpolator(a.getAttribute("ipt")), Long.parseLong(a.getAttribute("d")));
								break;
							case "custom":
								pair.getValue().add(k);
								break;
							default:
								System.err.println("Class action non reconnue : " + a.getAttribute("class"));
						}
						k++;
					}
				}
			}
		}
		return ret;
	}

	private static Interpolator getInterpolator(String ipt) {
		switch (ipt) {
			case "linear":
				return Interpolator.LINEAR;
			case "ease_both":
				return Interpolator.EASE_BOTH;
			case "ease_out":
				return Interpolator.EASE_OUT;
			case "ease_in":
				return Interpolator.EASE_IN;
			case "discrete":
				return Interpolator.DISCRETE;
		}
		throw new Error();
	}

	public static final HashMap<String, Pair<Elements.Element, ArrayList<Integer>>> getElementsFromTAML(String path) throws IOException, SAXException {
		String beginPath = new File(".").getCanonicalPath() + "/";
		return taml.getElements(new File(beginPath + path));
	}

}

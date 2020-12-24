/**
 * 
 */
package com.goweb.webapp.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.mxp1.MXParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goweb.webapp.core.model.exception.BaseException;
import com.goweb.webapp.core.model.exception.ServiceRuntimeException;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.copy.HierarchicalStreamCopier;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppReader;

/**
 * @author GVL00940
 *
 */
public class CommonUtil {
	public static final String INPUT = "input";
	public static final String INVALID_INPUT = "input-invalid";
	public static final String INPUT2 = "input2";
	public static final String INVALID_INPUT2 = "input2-invalid";
	public static final String RESULTS = "results";
	public static final String VALID = "valid";


	protected static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	public static Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
	private static Map<String, Date> tokenExpiryMap = new HashMap<>();
	
	@Autowired
	private transient static Environment env;
	
	public static void main(String[] args) {

		
	
	}
	
	
	public static void removeTokenMap(){
		tokenExpiryMap = new HashMap<>();
	}

	public static String base64UrlDecode(String input) {
		String result = null;
		byte[] decodedBytes = Base64.getDecoder().decode(input);
		result = new String(decodedBytes);
		return result;
	}

	public static Map<String, Object> putParamInMap(Map<String, Object> inputParams, String key, Object value) {
		if (inputParams.containsKey(key)) {
			inputParams.remove(key);
		}
		inputParams.put(key, value);
		return inputParams;
	}

	public static List<String> importParamIntoList(String param) {
		List<String> paramList = null;
		if (StringUtils.isNotEmpty(param)) {
			paramList = new ArrayList<>();
			if (param.contains(",")) {
				String[] arr = param.split(",");
				if (arr.length > 0) {
					for (String string : arr) {
						if (StringUtils.isNotEmpty(string)) {
							paramList.add(string);
						}
					}
				}
			} else {
				paramList.add(param);
			}
		}
		return paramList;
	}

	public static String toJson(final Object object) throws ServiceRuntimeException {
		try {
			return gson.toJson(object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_004"), e.getCause());
		}
	}

	public static Object toPojo(final String jsonString, final Class<?> clazz) throws ServiceRuntimeException {
		try {
			return gson.fromJson(jsonString, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_004"), e.getCause());
		}
	}
	
	public static <T> List<T> toListPojo(final String jsonArray, final Class<T> clazz) throws ServiceRuntimeException {
		try {
			return gson.fromJson(jsonArray, TypeToken.getParameterized(List.class, clazz).getType());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_004"), e.getCause());
		}
	}

	/**
	 * Should use #clonePojo()(Meaningful and easier to understand) instead of this one
	 * @param json
	 * @param clazz
	 * @return Object
	 * @throws ServiceRuntimeException
	 */
	public static Object toPojo(final Object json, final Class<?> clazz) throws ServiceRuntimeException {
		try {
			return gson.fromJson(toJson(json), clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_004"), e.getCause());
		}
	}
	
	/**
	 * This util use json to do a deep clone of an object
	 * With other object different than entity(prevent hibernate automatic persist that instante?) 
	 * or just want an shallow clone should implement interface Cloneable instead of using this one
	 * @param objectToClone
	 * @return the cloned object
	 * @throws ServiceRuntimeException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clonePojo(final T objectToClone) throws ServiceRuntimeException {
		try {
			return (T) gson.fromJson(toJson(objectToClone), objectToClone.getClass());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_004"), e.getCause());
		}
	}

	public static boolean saveFile(InputStream inputStream, String fileName, String filePath)
			throws ServiceRuntimeException {
		boolean isFile = false;
		OutputStream outputStream = null;
		try {
			File files = new File(filePath);
			if (!files.exists()) {
				if (!files.mkdirs())
					;
			}
			String fileNameToCreate = (filePath + fileName);
			File fileSave = new File(fileNameToCreate);
			outputStream = new FileOutputStream(fileSave);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			
			isFile = true;
			outputStream.close();
			logger.info("Save file : " + isFile + " " + fileNameToCreate);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_002"), e.getCause());
		}
		return isFile;
	}

	public static String formatNumberfromView(String input) {
		return input.replaceAll("\\,", "").replaceAll("\\.", "");
	}

	/**
	 * Format a double to the given pattern and the symbols
	 * 
	 * @param number
	 * @param str
	 * @return the number formatted
	 */
	public static String formatDecimal(double number, String str) {
		Locale local = new Locale("vi", "VN");
		DecimalFormat df = new DecimalFormat(str, new DecimalFormatSymbols(local));
		return df.format(number);
	}

	public static String writeDecimal(double number, int frac) {
		if (number == 0.00)
			return "-";
		switch (frac) {
		case 0:
			return formatDecimal(number, "#,###");
		case 1:
			return formatDecimal(number, "#,##0.0");
		case 2:
			return formatDecimal(number, "#,###.#");
		case 3:
			return formatDecimal(number, "#,##0.000");
		case 5:
			return formatDecimal(number, "#,##0.00000");
		case 7:
			return formatDecimal(number, "#,###.0");
		case 8:
			return formatDecimal(number, "#,000");
		case 9:
			return formatDecimal(number, "#,##0.00");
		default:
			return formatDecimal(number, "#,###");
		}
	}
	public static String writeDecimalFor0(double number, int frac) {
		if (number == 0.00)
			return "0";
		switch (frac) {
		case 0:
			return formatDecimal(number, "#,###");
		case 1:
			return formatDecimal(number, "#,##0.0");
		case 2:
			return formatDecimal(number, "#,###.#");
		case 3:
			return formatDecimal(number, "#,##0.000");
		case 5:
			return formatDecimal(number, "#,##0.00000");
		case 7:
			return formatDecimal(number, "#,###.0");
		case 8:
			return formatDecimal(number, "#,000");
		case 9:
			return formatDecimal(number, "#,##0.00");
		default:
			return formatDecimal(number, "#,###");
		}
	}
	public static BigDecimal round(BigDecimal val, int scale) {
		BigDecimal ret = new BigDecimal(0);
		ret = val.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return ret;
	}

	public static BigDecimal roundUP(BigDecimal val, int scale) {
		BigDecimal ret = new BigDecimal(0);
		ret = val.setScale(scale, BigDecimal.ROUND_UP);
		return ret;
	}

	/**
	 * calculate years old of given date
	 * 
	 * @param pastDate
	 * @return age
	 */
	public static int calculateAge(Date pastDate) {
		// before Calendar
		Calendar calendarBefore = Calendar.getInstance();
		calendarBefore.setTime(pastDate);
		int beforeYear = calendarBefore.get(Calendar.YEAR);
		int beforeMonth = calendarBefore.get(Calendar.MONTH) - 1;
		int beforeDate = calendarBefore.get(Calendar.DATE);
		// current Calendar
		Calendar calendar = Calendar.getInstance();
		int curYear = calendar.get(Calendar.YEAR);
		int curMonth = calendar.get(Calendar.MONTH) - 1;
		int curDate = calendar.get(Calendar.DATE);

		// calculate years old
		int age = curYear - beforeYear;
		if (beforeMonth > curMonth) {
			age = age - 1;
		} // next birthday not yet reached
		else if (beforeMonth == curMonth && curDate < beforeDate) {
			age = age - 1;
		}
		return age;

	}

	/**
	 * 
	 * @param pdate
	 * @param pformat
	 * @return
	 */
	public static String formatDate(Date pdate, String pformat) {
		SimpleDateFormat sdf = new SimpleDateFormat(pformat);
		return sdf.format(pdate);
	}
	
	public static <T> String convertXMLObjectToString(T objectToConvert, Class<T> clazz) {
		String result = null;
		try {
			if (objectToConvert != null) {
				StringWriter sw = new StringWriter();
				JAXBContext context = JAXBContext.newInstance(clazz);
				JAXBIntrospector introspector = context.createJAXBIntrospector();
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
				if (null == introspector.getElementName(clazz.getSimpleName())) {
					JAXBElement jx = new JAXBElement<T>(new QName(clazz.getName()), clazz, objectToConvert);
					marshaller.marshal(jx, sw);
				} else {
					marshaller.marshal(objectToConvert, sw);
				}
				result = sw.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static <T> String convertObjectToXMLString(T objectToConvert, Class<T> clazz) {
		String result = null;
		try {
			if (objectToConvert != null) {
				StringWriter sw = new StringWriter();
				JAXBContext context = JAXBContext.newInstance(clazz);
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
				marshaller.marshal(objectToConvert, sw);
				result = sw.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String convertXMLtoJSON(String docByXML) {
		String result = null;
		String docByXMLReplace = docByXML.replaceAll(">\\s+<", "><");
		HierarchicalStreamReader sourceReader = new XppReader(new StringReader(docByXMLReplace), new MXParser());

		StringWriter buffer = new StringWriter();
		JettisonMappedXmlDriver jettisonDriver = new JettisonMappedXmlDriver();
		HierarchicalStreamWriter destinationWriter = jettisonDriver.createWriter(buffer);

		HierarchicalStreamCopier copier = new HierarchicalStreamCopier();
		copier.copy(sourceReader, destinationWriter);

		result = buffer.toString();
		return result;
	}

	public static String converterJSONtoXML(String docByString) {
		String docByXML = null;
		HierarchicalStreamReader sourceReader = new JettisonMappedXmlDriver()
				.createReader(new StringReader(docByString));

		StringWriter buffer = new StringWriter();
		PrettyPrintWriter writer = new PrettyPrintWriter(buffer);

		HierarchicalStreamCopier copier = new HierarchicalStreamCopier();
		copier.copy(sourceReader, writer);
		docByXML = buffer.toString();
		return docByXML;
	}

	public static Document converterXMLtoDOM(String docByXML) throws BaseException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new ServiceRuntimeException(e);
		}
		try {
			return builder.parse(new ByteArrayInputStream(docByXML.getBytes(StandardCharsets.UTF_8)));
		} catch (SAXException | IOException e) {
			throw new ServiceRuntimeException(e);
		}
	}
	

	public static String setRootElementToJson(String document, String rootElement)
			throws ServiceRuntimeException {
		JsonObject json = new JsonObject();
		json.add(rootElement, (JsonElement) toPojo(document, JsonElement.class));
		return json.toString();
	}

	public static String converterDOMToXML(Document document) throws ServiceRuntimeException {
		try {
			StringWriter writer = new StringWriter();
			TransformerFactory tFactory = TransformerFactory.newInstance();
			StreamResult docXML = new StreamResult(writer);
			DOMSource source = new DOMSource(document);
			try {
				Transformer transformer = tFactory.newTransformer();
				transformer.transform(source, docXML);
			} catch (TransformerException e) {
			}
			String xml = writer.toString();
			writer.close();
			return xml;
		}catch(Exception e){
			throw new ServiceRuntimeException(env.getProperty("MSG_004"), e.getCause());
		}
	}

	public static void setNodeValue(Node node, String value) {
		value = value == null ? "" : value;
		if (isOptionNode(node)) {
			setOptionNodeValue(node, value.trim());
		} else {
			node.setTextContent(value.trim());
		}
	}

	public static boolean isOptionNode(Node node) {
		if (node.hasChildNodes()) {
			Node child = node.getLastChild();
			boolean hasOptionsNode = false;
			boolean hasValueNode = false;

			while (!(hasOptionsNode && hasValueNode)) {
				if (child == null) {
					return false;
				}
				if ("Value".equals(child.getNodeName())) {
					hasValueNode = true;
				} else if ("Options".equals(child.getNodeName())) {
					hasOptionsNode = true;
				}
				child = child.getPreviousSibling();
			}
			return true;
		}
		return false;
	}

	public static void setOptionNodeValue(Node node, String value) {
		NodeList childOptionNode = node.getChildNodes();
		if (childOptionNode == null) {
			return;
		}
		for (int i = 0; i < childOptionNode.getLength(); i++) {
			Node childNode = childOptionNode.item(i);
			if ("Value".equals(childNode.getNodeName())) {
				childNode.setTextContent(value);
			}
		}
	}

	public static String getOptionNodeValue(Node node) {
		NodeList childOptionNode = node.getChildNodes();
		if (childOptionNode == null) {
			return null;
		}

		for (int i = 0; i < childOptionNode.getLength(); i++) {
			Node childNode = childOptionNode.item(i);
			if ("Value".equals(childNode.getNodeName())) {
				return childNode.getTextContent().trim();
			}
		}
		return null;
	}

	public static String getNodeValue(Node node) {
		if (isOptionNode(node)) {
			return getOptionNodeValue(node).trim();
		} else {
			return node.getTextContent().trim();
		}
	}

	public static Node getNodeByXpath(Document document, String xpath) {

		Node node = null;
		try {
			XPath xPathIntance = XPathFactory.newInstance().newXPath();
			node = (Node) xPathIntance.evaluate(xpath, document.getDocumentElement(), XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			logger.error(e.getMessage(), e);
		}
		return node;
	}

	public static Node getNodeByXpath(Node document, String xpath) {

		Node node = null;
		try {
			XPath xPathIntance = XPathFactory.newInstance().newXPath();
			node = (Node) xPathIntance.evaluate(xpath, document, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			logger.info(e.getMessage(), e);
		}
		return node;
	}
	

	public static String escapeString(String text) {
		if (StringUtils.isBlank(text)) {
			return "";
		}
		text = text.replaceAll("'", "");
		text = text.replaceAll("-", "");
		text = text.replaceAll("\"", "");
		return text;
	}



	public static String readFileToString(String path) throws ServiceRuntimeException {
		File file = new File(path);
		try {
			if (!file.exists()) {
				throw new ServiceRuntimeException(env.getProperty("MSG_002"));
			}
			return new Scanner(file).useDelimiter("\\Z").next();
		} catch (Exception e) {
			throw new ServiceRuntimeException(env.getProperty("MSG_002"), e.getCause());
		}

	}

	public static String readFile(String fileName) throws ServiceRuntimeException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			throw new ServiceRuntimeException(env.getProperty("MSG_002"), e.getCause());
		}
	}

	public static boolean parseStringToBoolean(String flag) throws ServiceRuntimeException {
		if ("true".equalsIgnoreCase(flag) || "Y".equalsIgnoreCase(flag) || "yes".equalsIgnoreCase(flag)) {
			return true;
		}
		return false;
	}

	public static String rename(String stName) {
		if (stName != null) {
			stName = stName.trim();
			for (int i = 0; i < stName.length(); ++i) {
				char c = stName.charAt(i);
				int j = (int) c;
				switch (j) {
				case 224:
				case 225:
				case 7843:
				case 227:
				case 7841:
				case 259:
				case 7857:
				case 7855:
				case 7859:
				case 7861:
				case 7863:
				case 226:
				case 7847:
				case 7845:
				case 7849:
				case 7851:
				case 7853:
				case 97: {
					stName = stName.replace(stName.charAt(i), 'a');
				}
					break;
				case 65:
				case 192:
				case 193:
				case 7842:
				case 195:
				case 7840:
				case 258:
				case 7856:
				case 7854:
				case 7858:
				case 7860:
				case 7862:
				case 194:
				case 7846:
				case 7844:
				case 7848:
				case 7850:
				case 7852: {
					stName = stName.replace(stName.charAt(i), 'A');
				}
					break;
				case 98: {
					stName = stName.replace(stName.charAt(i), 'b');
				}
					break;
				case 66: {
					stName = stName.replace(stName.charAt(i), 'B');
				}
					break;
				case 99: {
					stName = stName.replace(stName.charAt(i), 'c');
				}
					break;
				case 67: {
					stName = stName.replace(stName.charAt(i), 'C');
				}
					break;
				case 273:
				case 100: {
					stName = stName.replace(stName.charAt(i), 'd');
				}
					break;
				case 272:
				case 68: {
					stName = stName.replace(stName.charAt(i), 'D');
				}
					break;
				case 101:
				case 232:
				case 233:
				case 7867:
				case 7869:
				case 7865:
				case 234:
				case 7873:
				case 7871:
				case 7875:
				case 7877:
				case 7879: {
					stName = stName.replace(stName.charAt(i), 'e');
				}
					break;
				case 69:
				case 200:
				case 201:
				case 7866:
				case 7868:
				case 7864:
				case 202:
				case 7872:
				case 7870:
				case 7874:
				case 7876:
				case 7878: {
					stName = stName.replace(stName.charAt(i), 'E');
				}
					break;
				case 102: {
					stName = stName.replace(stName.charAt(i), 'f');
				}
					break;
				case 70: {
					stName = stName.replace(stName.charAt(i), 'F');
				}
					break;
				case 103: {
					stName = stName.replace(stName.charAt(i), 'g');
				}
					break;
				case 71: {
					stName = stName.replace(stName.charAt(i), 'G');
				}
					break;
				case 104: {
					stName = stName.replace(stName.charAt(i), 'h');
				}
					break;
				case 72: {
					stName = stName.replace(stName.charAt(i), 'H');
				}
					break;
				case 105:
				case 236:
				case 237:
				case 7881:
				case 297:
				case 7883: {
					stName = stName.replace(stName.charAt(i), 'i');
				}
					break;
				case 73:
				case 204:
				case 205:
				case 7880:
				case 296:
				case 7882: {
					stName = stName.replace(stName.charAt(i), 'I');
				}
					break;
				case 106: {
					stName = stName.replace(stName.charAt(i), 'j');
				}
					break;
				case 74: {
					stName = stName.replace(stName.charAt(i), 'J');
				}
					break;
				case 107: {
					stName = stName.replace(stName.charAt(i), 'k');
				}
					break;
				case 75: {
					stName = stName.replace(stName.charAt(i), 'K');
				}
					break;
				case 108: {
					stName = stName.replace(stName.charAt(i), 'l');
				}
					break;
				case 76: {
					stName = stName.replace(stName.charAt(i), 'L');
				}
					break;
				case 109: {
					stName = stName.replace(stName.charAt(i), 'm');
				}
					break;
				case 77: {
					stName = stName.replace(stName.charAt(i), 'M');
				}
					break;
				case 110: {
					stName = stName.replace(stName.charAt(i), 'n');
				}
					break;
				case 78: {
					stName = stName.replace(stName.charAt(i), 'N');
				}
					break;
				case 111:
				case 242:
				case 243:
				case 7887:
				case 245:
				case 7885:
				case 244:
				case 7891:
				case 7889:
				case 7893:
				case 7895:
				case 7897:
				case 417:
				case 7901:
				case 7899:
				case 7903:
				case 7905:
				case 7907: {
					stName = stName.replace(stName.charAt(i), 'o');
				}
					break;
				case 79:
				case 210:
				case 211:
				case 7886:
				case 213:
				case 7884:
				case 212:
				case 7890:
				case 7888:
				case 7892:
				case 7894:
				case 7896:
				case 416:
				case 7900:
				case 7898:
				case 7902:
				case 7904:
				case 7906: {
					stName = stName.replace(stName.charAt(i), 'O');
				}
					break;
				case 117:
				case 249:
				case 250:
				case 7911:
				case 361:
				case 7909:
				case 432:
				case 7915:
				case 7913:
				case 7917:
				case 7919:
				case 7921: {
					stName = stName.replace(stName.charAt(i), 'u');
				}
					break;
				case 85:
				case 217:
				case 218:
				case 7910:
				case 360:
				case 7908:
				case 431:
				case 7914:
				case 7912:
				case 7916:
				case 7918:
				case 7920: {
					stName = stName.replace(stName.charAt(i), 'U');
				}
					break;
				case 118: {
					stName = stName.replace(stName.charAt(i), 'v');
				}
					break;
				case 86: {
					stName = stName.replace(stName.charAt(i), 'V');
				}
					break;
				case 119: {
					stName = stName.replace(stName.charAt(i), 'w');
				}
					break;
				case 87: {
					stName = stName.replace(stName.charAt(i), 'W');
				}
					break;
				case 120: {
					stName = stName.replace(stName.charAt(i), 'x');
				}
					break;
				case 88: {
					stName = stName.replace(stName.charAt(i), 'X');
				}
					break;
				case 121:
				case 7923:
				case 253:
				case 7927:
				case 7929:
				case 7925: {
					stName = stName.replace(stName.charAt(i), 'y');
				}
					break;
				case 89:
				case 7922:
				case 221:
				case 7926:
				case 7928:
				case 7924: {
					stName = stName.replace(stName.charAt(i), 'Y');
				}
					break;
				case 122: {
					stName = stName.replace(stName.charAt(i), 'z');
				}
					break;
				case 90: {
					stName = stName.replace(stName.charAt(i), 'Z');
				}
					break;
				default:
					break;
				}
			}

			stName = stName.replace('?', '_');
			stName = stName.replace('.', '_');
			stName = stName.replace('`', '_');
			stName = stName.replace(' ', '_');
			stName = stName.replace(',', '_');
			stName = stName.replace('/', '_');
			stName = stName.replace(':', '_');
			stName = stName.replace('^', '_');
			stName = stName.replace(';', '_');
			stName = stName.replace('\\', '_');
			stName = stName.replace('\'', '_');
		} else {
			stName = "";
		}
		return stName;
	}

	public static Method getMethodByNameJavaReflection(Object object, String methodName){
		if(object != null && StringUtils.isNotBlank(methodName)){
			Class cls = object.getClass();
			Method[] methods = cls.getMethods();
			for(Method m : methods){
				if(m.getName().equalsIgnoreCase(methodName)){
					return m;
				}
			}
		}
		return null;
	}
	
	public static String convertInputStreamToBase64(InputStream is) throws IOException{
		byte[] bytes = IOUtils.toByteArray(is);
		String encoded = Base64.getEncoder().encodeToString(bytes);
		return encoded;
	}
	
	public static File convertBase64ToFile(String str, String filePath, String fileName) throws IOException{
		// Note preferred way of declaring an array variable
		File file = new File(filePath + fileName);
		byte[] data = Base64.getDecoder().decode(str);
		FileUtils.writeByteArrayToFile(file, data);
		return file;
	}
	
	public static void copyFile(File input, File output) throws IOException{
		FileUtils.copyFile(input, output);
		logger.info("Copy file " + input.getName() + " from " + input.getPath());
		logger.info("To file " + output.getName() + " from " + output.getPath());
	}
	
	public static String toUpperCase(String input) {
		return StringUtils.isNotBlank(input) ? input.toUpperCase() : "";
	}
	
	public static String toLowerCase(String input) {
		return StringUtils.isNotBlank(input) ? input.toLowerCase() : "";
	}
	
	public static String getKeyBarcode(String userName){
		String now = DateUtils.simpleDateFormat.format(new Date());
		/**String keyXML = now+last 3 characters of userName;**/
		StringBuilder sb = new StringBuilder();
		String keyBar = sb.append(now).append(userName.substring(userName.length()-3,userName.length())).toString();
		return keyBar;
	}
	
	public static String getResourceJsonFileInFolder(String dirFolder){
		String[] types = {"json"};
		Collection<File> files = FileUtils.listFiles(new File(dirFolder), types, true);
		File resource = null;
		long temp = 0;
		for(File file : files){
			logger.info(file.lastModified() + "");
			logger.info(file.getAbsolutePath());
			if(temp == 0){
				temp = file.lastModified();
				resource = file;
			} else {
				if(file.lastModified() > temp){
					resource = file;
					temp = file.lastModified();
				}
			}
		}
		try (FileInputStream inputStream = new FileInputStream(resource)) {
			return IOUtils.toString(inputStream);
		}catch(Exception e){
			return null;
		}
	}
	
	public static <T> T trimAllStringProperty(T object) throws ServiceRuntimeException {
		if(object == null) return null;
		for (Field field : object.getClass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				Object value = field.get(object);
				if (value instanceof String) {
					field.set(object, ((String) value).trim());
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceRuntimeException(env.getProperty("MSG_004"), e.getCause());
			}
		}
		return object;
	}
	
	public static String getLogMessenge(Exception e) {
		String error = null;
		try {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			error =  sw.toString();
		} catch (Exception e2) {
			e2.printStackTrace();
			return e.getMessage();
		}
		return error;
	}
	
	/**
	 * This function help set a value into a field of an object
	 * Support multiple level on an Object (separate by an ".")
	 * Important note:
	 * 	- if a field is a List if will add value inside that List if the type is match
	 *  - it also check the super class, mean if the class extend another class it is automatically cared
	 *  - swallow exception so best practice is check the boolean response
	 * @param object
	 * @param fieldName
	 * @param fieldValue
	 * @return boolean
	 */
	public static <T,V> Boolean setPropertyIntoObject(T object, V fieldValue, String fieldName) {
		return setPropertyIntoObjectHandler(object, fieldValue, fieldName.split("\\."));
	}
	
	
	private static <T,V> Boolean setPropertyIntoObjectHandler(T object, V fieldValue, String... fieldName) {
		if(object == null || fieldValue == null || ArrayUtils.isEmpty(fieldName)) {
			return false;
		}
		if(fieldName.length == 1) {
			return setPropertyIntoObjectHandler(object, fieldValue, fieldName[0]);
		} else {
			try {
				Field field = getField(object, fieldName[0]);
				if(field == null) return false;
				Object subObject = Optional.ofNullable(field.get(object)).orElse(Class.forName(field.getType().getName()).newInstance());
				boolean isSucess = setPropertyIntoObjectHandler(subObject, fieldValue, Arrays.copyOfRange(fieldName, 1, fieldName.length));
				if (isSucess) {
					field.set(object, subObject);
				}
				return isSucess;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T,V> Boolean setPropertyIntoObjectHandler(T object, V fieldValue, String fieldName) {
		if(object == null || fieldValue == null || StringUtils.isBlank(fieldName)) {
			return false;
		}
        try {
            Field field = getField(object, fieldName);
            if(field == null) return false;
            
            if(field.getType().equals(fieldValue.getClass())) {
				field.set(object, fieldValue);
				return true;
			}
            boolean islistWithSameType = field.getType().equals(List.class)	&& ((Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]).equals(fieldValue.getClass());
			if (islistWithSameType) {
				List<V> currentValue = Optional.ofNullable((List<V>) field.get(object)).orElse(new ArrayList<>());
				currentValue.add(fieldValue);
				field.set(object, currentValue);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static <T> Field getField(T object, String fieldName) {
		Class clazz = object.getClass();
		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		String message = String.format("Property: \"%s\" not available on %s", fieldName, object.getClass().getName());
		logger.error(message);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@SafeVarargs
	public static <T> List<T> joinList(final List<? extends T>...lists){
		if(lists.length == 1) {
			return (List<T>) Optional.ofNullable(lists[0]).orElse(new ArrayList<>());
		}
		final ArrayList<T> result = new ArrayList<>();
		result.addAll(Optional.ofNullable(lists[0]).orElse(new ArrayList<>()));
		result.addAll(joinList(Arrays.copyOfRange(lists, 1, lists.length)));
		return result;
	}
	
	/**
	 * This function return only "." and alphanumeric character inside String input
	 * @param input
	 * @return
	 */
	public static String removeSpecialCharacter(final String input) {
		return input.replaceAll("[^a-zA-Z0-9.]+","");
	}
	
	public static String stripAccents(final CharSequence input) {
		return Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	/**
	 * input null -> false
	 * input empty -> false
	 * input not match regex -> false
	 * Other -> true
	 * @param regex
	 * @param input
	 * @return
	 */
	public static boolean isMatchWithRegex(final String regex, final String input) {
		if(StringUtils.isBlank(input)) return false;
		return input.matches(regex);
	}
	public static boolean isNotMatchWithRegex(final String regex, final String input) {
		return !isMatchWithRegex(regex, input);
	}
	
}

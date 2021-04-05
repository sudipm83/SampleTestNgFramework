package util.reader.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class ConfigXmlReader {
	
	protected static final Logger logger = LogManager.getLogger(ConfigXmlReader.class.getName());

	private Document configXML;
	
	XPathFactory xpath = XPathFactory.instance();
	
	public ConfigXmlReader(String xmlPathStr)
	{
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = builder.build(new File(xmlPathStr));
			this.configXML = document;
		}catch (Exception var4)
		{
			logger.error(var4.getMessage());
		}
	}
	
	public String getAttributeValue(String xpathExpression, String keyName)
	{
		String [] xpathTreeElements = xpathExpression.split("\\.");
		String xpathFinalExpressionStr = "//" + xpathTreeElements[0];
		
		for (int iterator = 1; iterator < xpathTreeElements.length-1; ++iterator)
		{
			xpathFinalExpressionStr = xpathFinalExpressionStr + "/" + xpathTreeElements[iterator];
		}
		
		xpathFinalExpressionStr = xpathFinalExpressionStr + "[@key='" + keyName + "']/@" 
		+ xpathTreeElements[xpathTreeElements.length-1];
		
		XPathExpression<Attribute> attrExpr = this.xpath.compile(xpathFinalExpressionStr, Filters.attribute());
		Attribute attribute = (Attribute) attrExpr.evaluateFirst(this.configXML);
		String attributeValue = "";
		
		try {
			attributeValue = attribute.getValue();
		}catch (Exception var14)
		{
			List<String> nodes = this.getNodeAttrFromConfig(keyName);
			if(nodes.get(0) != null)
			{
				String childNodeName = " <" + (String) nodes.get(0);
				String secondParentNodeName = nodes.get(1) == null ? "" : " under <" + (String) nodes.get(1) + ">";
				String parentNodeName = " under <" + (String) nodes.get(2) + ">";
				logger.error("Missing a key in the config : " + childNodeName + " key=\"" + keyName + "\">"
						+ secondParentNodeName + parentNodeName);
			}
		}
		
		return attributeValue;
	}
	
	private List<String> getNodeAttrFromConfig(String keyName)
	{
		List<String> generalSettings = Arrays.asList("defaultEnvironmentName", "defaultTargetMedium", "defaultLanguage",
				"secondsToTry");
		List<String> targetMediums = Arrays.asList("webBrowser", "webMobile", "appMobile");
		List<String> mobileSettings = Arrays.asList("hostUrl", "androidDeviceID", "appPackageName", "iOSDeviceID",
				"appBundleID", "iOSBrowser", "androidBrowser", "proxyHost", "proxyPort", "securityToken");
		List<String> nodes = new ArrayList();
		String parentNodeName = null;
		String secondParentNodeName = null;
		String childNodeName = null;
		if(generalSettings.contains(keyName))
		{
			parentNodeName = "generalSettings";
			childNodeName = "frameworkSettings";
			
		}
		else if(mobileSettings.contains(keyName))
		{
			parentNodeName = "MobileSettings";
			secondParentNodeName = "Perfecto";
			childNodeName = "mSettings";
		}
		else if(targetMediums.contains(keyName))
		{
			parentNodeName = "targetMediums";
			childNodeName = "targetMedium";
		}
		nodes.add(childNodeName);
		nodes.add(secondParentNodeName);
		nodes.add(parentNodeName);
		return nodes;
	}
	
	public String getAttribetureValueByXpath(String xpathExpressionStr)
	{
		XPathExpression<Attribute> attrExpr = this.xpath.compile(xpathExpressionStr, Filters.attribute());
		Attribute attribute = (Attribute) attrExpr.evaluateFirst(this.configXML);
		return attribute.getValue();
	}
	
	public String getXMLTagValue(String xpathExpressionStr)
	{
		XPathExpression<Element> elementExpr = this.xpath.compile(xpathExpressionStr, Filters.element());
		Element element = (Element) elementExpr.evaluateFirst(this.configXML);
		return element.getValue();
	}
	
	public int getNumberOfNodeswithAttribute(String xpathExpressionStr)
	{
		XPathExpression<Attribute> attrExpr = this.xpath.compile(xpathExpressionStr, Filters.attribute());
		List<Attribute> attributeList = attrExpr.evaluate(this.configXML);
		return attributeList.size();
		
	}
	
	public int getNUmberOFNodeswithTagName(String xpathExpressionStr)
	{
		XPathExpression<Element> elementExpr = this.xpath.compile(xpathExpressionStr, Filters.element());
		List<Element> elementList = elementExpr.evaluate(this.configXML);
		return elementList.size();
	}
	
	
	
	
	
}

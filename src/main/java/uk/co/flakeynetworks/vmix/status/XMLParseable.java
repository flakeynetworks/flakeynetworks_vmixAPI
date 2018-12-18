package uk.co.flakeynetworks.vmix.status;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.List;

abstract class XMLParseable {

    XMLParseable parseXML(String xml) throws IOException {

        if(xml == null) return null;

        // Go through the xml and see if we want to parse any of the elements.
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            InputSource is = new InputSource(new StringReader(xml));
            Document doc = dBuilder.parse(is);
            doc.normalize();

            NodeList nodes = doc.getChildNodes();

            if(nodes.getLength() == 0) return null;

            return parseXML(nodes.item(0));
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }  // end of catch

        return null;
    } // end of parseXML


    XMLParseable parseXML(Node xml) {

        // Get all the nodes
        NodeList nodes = xml.getChildNodes();

        // Go through each node
        for(int i = 0; i < nodes.getLength(); i++)
            parseNode(nodes.item(i));

        return this;
    } // end of parseXML


    private void setMemberValue(Field field, Class type, String value) {

        // Check the type for this value
        Object valueToSet = null;
        try {
            if (type.equals(String.class))
                valueToSet = value;
            else if (type.equals(Integer.class))
                valueToSet = Integer.valueOf(value);
            else if (type.equals(Double.class))
                valueToSet = Double.valueOf(value);
            else if (type.equals(Long.class))
                valueToSet = Long.valueOf(value);
            else if (type.equals(Boolean.class))
                valueToSet = Boolean.valueOf(value);
        } catch (Exception e) { } // end of catch


        // Make the field accessible.
        boolean wasAccessible = field.isAccessible();
        if(!wasAccessible)
            field.setAccessible(true);

        try {
            // Update the field
            field.set(this, valueToSet);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        } finally {

            // If the field wasn't originally accessible then change it back,
            if(!wasAccessible)
                field.setAccessible(false);
        } // end of finally
    } // end of setMemberValue


    private void parseNode(Node node) {

        Field[] fields = getClass().getDeclaredFields();

        // Get the attributes for this node and parse them
        NamedNodeMap attributes = node.getAttributes();
        if(attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++)
                parseAttribute(attributes.item(i));
        } // end of if


        // Look to see if the model has this node. If so then parse it.
        for(Field field : fields) {

            // If we couldn't find a member with the exact name then check the annotations.
            VMixStatusNode annotation = field.getAnnotation(VMixStatusNode.class);
            if(annotation != null && annotation.name().equals(node.getNodeName())) {

                setMemberValue(field, annotation.type(), node.getTextContent());
                break;
            } // end of if

            // Check for it the param is a list with child elements
            VMixStatusListNode listAnnnotations = field.getAnnotation(VMixStatusListNode.class);
            if(listAnnnotations != null && listAnnnotations.name().equals(node.getNodeName()))
                parseListNode(field, node, listAnnnotations.type());
        } // end of for
    } // end of parseNode


    private void parseAttribute(Node node) {

        Field[] fields = getClass().getDeclaredFields();

        // Look to see if the model has this node. If so then parse it.
        for(Field field : fields) {

            // If we couldn't find a member with the exact name then check the annotations.
            VMixStatusAttribute annotation = field.getAnnotation(VMixStatusAttribute.class);
            if(annotation != null && annotation.name().equals(node.getNodeName())) {

                setMemberValue(field, annotation.type(), node.getTextContent());
                break;
            } // end of if
        } // end of for
    } // end of parseNode


    private void parseListNode(Field field, Node node, Class listItemType) {

        // Check that the field is a list
        if(!field.getType().equals(List.class)) return;

        // Make the field accessible
        boolean wasAccessible = field.isAccessible();
        if(!wasAccessible)
            field.setAccessible(true);

        // Get a reference to the list
        List list;
        try {
            list = (List) field.get(this);
        } catch (IllegalAccessException e) { return; } // end of catch

        // Get all the child nodes for this list
        NodeList listNodes = node.getChildNodes();

        for(int j = 0; j < listNodes.getLength(); j++) {

            Node listNode = listNodes.item(j);

            // Create a new instance
            try {

                // Parse the list item
                XMLParseable typeInstance = (XMLParseable) listItemType.newInstance();
                typeInstance.parseNode(listNode);

                // Add the list item
                list.add(typeInstance);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            } // end of catch
        } // end of for

        // Set the accessibility back
        if(!wasAccessible)
            field.setAccessible(false);
    } // end of parseListNode
} // end of XMLParseable

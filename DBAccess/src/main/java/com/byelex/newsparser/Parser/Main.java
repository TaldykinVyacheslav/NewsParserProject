package com.byelex.newsparser.Parser;

import com.byelex.newsparser.DBManager.Get;
import com.byelex.newsparser.Models.Event;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Alexzander
 * Date: 13.02.14
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main (String ... args) throws IOException, XMLStreamException {
        /*
        URL url = new URL("http://widget.opinionmining.nl/bt-api-uni/dependency?buzzreport_uid=df42a5dd-6d69-4870-80d2-005062acb391&buzzevent_name=Criticism");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //Get the DOM Builder
        DocumentBuilder builder = factory.newDocumentBuilder();
        //Load and Parse the XML document
        //document contains the complete XML as a Tree.
        Document document = builder.parse(new BufferedInputStream(url.openStream()));
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.transform(new DOMSource(document), new StreamResult("C:\\data.xml"));
        */
        Pars pars = new Pars();
        //todo add row report_id to publication table
        List<Event> eventList = Get.listEvents();
        for(Event event : eventList) {
            if(!event.getName().equals("Intro")) {
                String buzzevent_name;
                buzzevent_name = event.getName();
                buzzevent_name = buzzevent_name.replace("&", "%26");
                buzzevent_name = buzzevent_name.replace(" ", "%20");
                pars.getUrlsList().addUrlToListOfUrls("http://widget.opinionmining.nl/bt-api-uni/dependency?buzzreport_uid=df42a5dd-6d69-4870-80d2-005062acb391&buzzevent_name=" + buzzevent_name, event.getName());

            }
        }
        /*pars.getUrlsList().addUrlToListOfUrls("http://widget.opinionmining.nl/bt-api-uni/dependency?buzzreport_uid=df42a5dd-6d69-4870-80d2-005062acb391&buzzevent_name=Apology", "Apology");
        pars.getUrlsList().addUrlToListOfUrls("http://widget.opinionmining.nl/bt-api-uni/dependency?buzzreport_uid=df42a5dd-6d69-4870-80d2-005062acb391&buzzevent_name=Robbery", "Robbery");
        pars.getUrlsList().addUrlToListOfUrls("http://widget.opinionmining.nl/bt-api-uni/dependency?buzzreport_uid=df42a5dd-6d69-4870-80d2-005062acb391&buzzevent_name=Criticism", "Criticism");
        pars.getUrlsList().addUrlToListOfUrls("http://widget.opinionmining.nl/bt-api-uni/dependency?buzzreport_uid=df42a5dd-6d69-4870-80d2-005062acb391&buzzevent_name=Conflict", "Conflict");
        */pars.getXmlFromServiceUsingUrls();
        //Testing references
        //System.out.println(Get.GetPublication().get(0).getPublicationtags().iterator().next().getOpencalaistags().iterator().next().getCategory());
        /*Map<String, String> reportMap = Get.createReport();
        for(Map.Entry<String, String> reportEvent : reportMap.entrySet()) {
            System.out.println(reportEvent.getKey() + " : " + reportEvent.getValue());
        }*/

        System.out.println(Arrays.toString(Get.GetTopPersons(5, "Criticism").toArray()));
        System.out.println("Yap!");
    }

}

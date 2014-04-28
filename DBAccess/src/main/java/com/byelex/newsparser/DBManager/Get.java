package com.byelex.newsparser.DBManager;

// предоставление ссылок на Buzztalk
//todo звук
// то что читается, выделяется
// применить ссылки
// кнопка синхронизации с Buzztalk
// настройка сервисов

import com.byelex.newsparser.Models.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.byelex.newsparser.Parser.Pars;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.joda.time.DateTime;
import org.joda.time.Weeks;

import javax.xml.stream.XMLStreamException;

/**
 * Created with IntelliJ IDEA.
 * User: Игорь
 * Date: 2/19/14
 * Time: 5:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class Get {
    public static Transaction t;
    public static Configuration cfg;
    public static SessionFactory factory;
    public static Session session;
    public static Query query;

    static {
        cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        factory = cfg.buildSessionFactory();
        session = factory.openSession();
    }

    public static List<Publication> getPublication()
    {
        t = session.beginTransaction();
        query = session.createQuery("from Publication ");
        java.util.List<Publication> list = query.list();
        t.commit();
        session.flush();
        session.clear();
        return list;
    }

    public static Map<String, String> associateItemsWithUrls(String reportID) {
        Map<String, String> association;
        association = new HashMap<String, String>();

        association.put("BuzzTalk", "http://www.buzztalkmonitor.com/");
        association.putAll(associateTagsWithUrls(reportID));
        association.putAll(associateTitlesWithUrls(reportID));
        return association;
    }

    public static Map<String, String> associateTitlesWithUrls(String reportID) {
        Map<String, String> association;
        association = new HashMap<String, String>();
        List<Publication> publications;
        List<Map<String,Object>> aliasToValueMapList;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select title, url from publication where report_id = :reportID group by title order by count(title) desc");
        query.setString("reportID", reportID);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        aliasToValueMapList = query.list();
        for(Map<String,Object> associationEntry : aliasToValueMapList) {
            association.put("{[" + associationEntry.get("title").toString() + "]}", associationEntry.get("url").toString());
        }
        t.commit();
        session.flush();
        session.clear();

        return association;
    }

    public static Map<String, String> associateTagsWithUrls(String reportID) {
        Map<String, String> association;
        List<Map<String,Object>> aliasToValueMapList;
        Query query;
        association = new HashMap<String, String>();
        t = session.beginTransaction();
        t.setTimeout(5);
        query = session.createSQLQuery("select o.name as name, p.url as url from opencalaistag o"
                                + " INNER JOIN publicationtags pt ON o.id = pt.opencalaistag_id"
                                + " INNER JOIN publication p ON p.pk = pt.publication_pk"
                                + " where p.report_id = :reportID"
                                + " group by o.name order by count(o.name) desc");
        query.setString("reportID", reportID);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        aliasToValueMapList = query.list();
        for(Map<String,Object> associationEntry : aliasToValueMapList) {
            association.put("{[" + associationEntry.get("name").toString() + "]}", associationEntry.get("url").toString());
        }
        query.setParameter("reportID", reportID);
        t.commit();
        session.flush();
        session.clear();

        return association;
    }

    public static void refreshData(Report report) throws IOException, XMLStreamException {
        Pars pars = new Pars();
        List<Event> eventList = Get.listEvents();
        for(Event event : eventList) {
            if(!event.getName().equals("Intro")) {
                String buzzevent_name;
                buzzevent_name = event.getName();
                buzzevent_name = buzzevent_name.replace("&", "%26");
                buzzevent_name = buzzevent_name.replace(" ", "%20");
                pars.getUrlsList().addUrlToListOfUrls(report.getUri() + "?buzzreport_uid=" + report.getId() + "&buzzevent_name=" + buzzevent_name, event.getName());
                //pars.getUrlsList().addUrlToListOfUrls("http://widget.opinionmining.nl/bt-api-uni/dependency?buzzreport_uid=df42a5dd-6d69-4870-80d2-005062acb391&buzzevent_name=" + buzzevent_name, event.getName());
            }
        }
        pars.getXmlFromServiceUsingUrls(report.getId());
    }

    public static List<Report> listReports() {
        t = session.beginTransaction();
        query = session.createQuery("from Report ");
        java.util.List<Report> list = query.list();
        t.commit();
        session.flush();
        session.clear();
        return list;
    }

    public static Report getReport(String id) {
        Report report;
        t = session.beginTransaction();
        report = (Report)session.get(Report.class, id);
        t.commit();
        session.flush();
        session.clear();
        return report;
    }

    public static List<Token> listTokens() {
        t = session.beginTransaction();
        query = session.createQuery("from Token ");
        java.util.List<Token> list = query.list();
        t.commit();
        session.flush();
        session.clear();
        return list;
    }

    public static List<Profile> listProfiles() {
        t = session.beginTransaction();
        query = session.createQuery("from Profile ");
        java.util.List<Profile> list = query.list();
        t.commit();
        session.flush();
        session.clear();
        return list;
    }

    public static List<Event> listEvents() {
        List<Event> list;
        t = session.beginTransaction();
        query = session.createQuery("from Event ");
        list = query.list();
        t.commit();
        session.flush();
        session.clear();
        return list;
    }

    public static Profile getProfile(Long id) {
        Profile profile;
        t = session.beginTransaction();
        profile = (Profile)session.get(Profile.class, id);
        t.commit();
        session.flush();
        session.clear();
        return profile;
    }

    public static void updateProfile(Profile profile, List<Event> events, Long profileId) {
        Query updateQuery;

        t = session.beginTransaction();
        Iterator<Event> eventIterator = events.iterator();
        Iterator<Template> templateIterator = profile.getTemplates().iterator();
        for ( ; eventIterator.hasNext(); ) {
            Event event;
            Template template;
            event = eventIterator.next();
            template = templateIterator.next();

            updateQuery = session.createSQLQuery("UPDATE templates t " +
                    "INNER JOIN events e on t.event_id = e.event_id " +
                    "INNER JOIN profiles p on p.profile_id = t.profile_id " +
                    "SET t.event_text = :event_text " +
                    "WHERE p.profile_id = :profileId and e.name = :event")
                    .setParameter("profileId", profileId)
                    .setParameter("event", event.getName())
                    .setParameter("event_text", template.getEventText());
            updateQuery.executeUpdate();
        }

        updateQuery = session.createSQLQuery("UPDATE profiles SET name = :profile WHERE profile_id = :profileId")
                    .setParameter("profile", profile.getName())
                    .setParameter("profileId", profileId);
        updateQuery.executeUpdate();

        updateQuery = session.createSQLQuery("UPDATE profiles SET report_id = :reportId WHERE profile_id = :profileId")
                .setParameter("reportId", profile.getReportId())
                .setParameter("profileId", profileId);
        updateQuery.executeUpdate();

        t.commit();
        session.flush();
        session.clear();
    }

    public static void addProfile(Profile profile, List<Event> events) {
        Query updateQuery;
        t = session.beginTransaction();
        session.save(profile);
        t.commit();
        session.flush();


        //session = factory.openSession();
        t = session.beginTransaction();
        Iterator<Event> eventIterator = events.iterator();
        Iterator<Template> templateIterator = profile.getTemplates().iterator();
        for ( ; eventIterator.hasNext(); ) {
            Event event;
            Template template;
            event = eventIterator.next();
            template = templateIterator.next();
            updateQuery = session.createQuery("UPDATE Template set event_id = :eventId where id = :templateId");
            updateQuery.setLong("eventId", event.getId());
            updateQuery.setLong("templateId", template.getId());
            updateQuery.executeUpdate();
        }

        t.commit();
        session.flush();
        session.clear();
    }

    public static void deleteProfile(Long profileID) {
        Profile profile;
        t = session.beginTransaction();
        profile = (Profile)session.get(Profile.class, profileID);
        if(profile != null) {
            session.delete(profile);
        }
        t.commit();
        session.flush();
        session.clear();
    }


    public static String getFullReport(Profile profile, String reportID) {
        StringBuffer result;
        List<String> events;
        String eventTemplate;
        result = new StringBuffer();

        eventTemplate = getEventTemplate(("Intro"), profile.getName());
        if(eventTemplate.length() > 2) {
            result.append(generateReportFromTemplate(eventTemplate, "Intro", 0, reportID) + "\n");
        }

        int position = 1;
        events = Get.listOrderedEvents(reportID);
        for(String eventName : events) {
            if(!eventName.equals("Intro")/* && position <= 5*/) {
                eventTemplate = getEventTemplate(eventName, profile.getName());
                if(eventTemplate.length() > 2) {
                    result.append("\n\n");
                    switch (position) {
                        case 1:
                            result.append(generateReportFromTemplate(("As said the main event is " + events.get(0)) + ".\n", eventName, position, reportID).replace("\n\n", "\n"));
                            break;
                        case 2:
                            result.append(generateReportFromTemplate("For now let's move on to the next major event in your %REPORT_NAME% search.\n"
                                            + "Remember this was " + events.get(1) + ".\n", eventName, position, reportID).replace("\n\n", "\n"));
                            break;
                        case 3:
                            result.append(generateReportFromTemplate(
                                    "The third event type drawing attention is " + events.get(2) + ".\n", eventName, position, reportID).replace("\n\n", "\n"));
                            break;
                        case 4:
                            result.append(generateReportFromTemplate("There is a lot going on in the field you searched."
                                            + "The fourth major event type we will explore further is " + events.get(3) + ".\n", eventName, position, reportID).replace("\n\n", "\n"));
                            break;
                        case 5:
                            result.append(generateReportFromTemplate("The last event type we will look at is " + events.get(4) + ".\n", eventName, position, reportID).replace("\n\n", "\n"));
                            break;
                        default:
                            result.append(generateReportFromTemplate("The next additional event type we will look at is " + events.get(position - 1) + ".\n", eventName, position, reportID).replace("\n\n", "\n"));
                    }
                    String tempResult = generateReportFromTemplate(eventTemplate, eventName, position, reportID).replace("\n\n", "\n");
                    if(tempResult.startsWith("\n")) {
                        tempResult = tempResult.substring(1, tempResult.length());
                    }
                    if(!tempResult.endsWith("\n")) {
                        tempResult += "\n";
                    }
                    result.append(tempResult);
                }
                position++;
            }
        }

        result.append("\n\n");
        result.append("Did you hear some interesting things? I encourage you to open BuzzTalk, drill down and find the details you need.");
        session.flush();
        session.clear();

        return result.toString();
    }

    private static String getEventTemplate(String eventName, String profileName) {
        String result;
        Query query;
        t = session.beginTransaction();
        query = session.createSQLQuery("SELECT t.event_text"
                + " FROM events e, templates t, profiles p"
                + " WHERE t.profile_id = p.profile_id and t.event_id = e.event_id"
                + " and p.name = :profile and e.name = '" + eventName + "'")
                .setParameter("profile", profileName);
        result = (String)query.list().get(0);
        t.commit();
        session.flush();
        session.clear();

        return result;
    }

    private static String generateReportFromTemplate (String template, String eventName, int position, String reportID) {
        String matchString;
        String resultReport;
        String replaceString;
        Pattern pattern;
        resultReport = new String(template);

        pattern = Pattern.compile("%(.*?)%");
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            matchString = matcher.group();

            if(matchString.startsWith("%IF ")) {
                String condition = null;
                String operationAfterIf = null;
                String leftConditionToken, rightConditionToken;
                Integer leftConditionValue, rightConditionValue;
                String conditionOperator;

                // %IF %%CONDITION%%{>,=,<}%%5%% THEN %%ACTION%% END%
                // GET CONDITION AND OPERATOR STRINGS
                matcher.find();
                String leftOperator = matcher.group();
                matchString += leftOperator;
                leftOperator = leftOperator.trim();
                leftConditionValue = Integer.valueOf(processSimpleToken(leftOperator, eventName, position, reportID));
                matcher.find();
                conditionOperator = matcher.group();
                matchString += conditionOperator;
                conditionOperator = conditionOperator.substring(1, conditionOperator.length() - 1);
                matcher.find();
                rightConditionToken = matcher.group();
                matchString += rightConditionToken;
                rightConditionValue = Integer.valueOf(rightConditionToken.substring(1, rightConditionToken.length() - 1).trim());
                matcher.find();
                matchString += matcher.group();
                matcher.find();
                operationAfterIf = matcher.group();
                matchString += operationAfterIf;
                matcher.find();
                matchString += matcher.group();


                conditionOperator = conditionOperator.trim();
                if(conditionOperator.equals(">") && (leftConditionValue > rightConditionValue)) {
                    replaceString = processSimpleToken(operationAfterIf, eventName, position, reportID);
                } else if(conditionOperator.equals("<") && (leftConditionValue < rightConditionValue)) {
                    replaceString = processSimpleToken(operationAfterIf, eventName, position, reportID);
                } else if(conditionOperator.equals("=") && (leftConditionValue == rightConditionValue)) {
                    replaceString = processSimpleToken(operationAfterIf, eventName, position, reportID);
                } else {
                    replaceString = "";
                }
            } else {
                replaceString = processSimpleToken(matchString, eventName, position, reportID);
            }
            resultReport = resultReport.replace(matchString, replaceString);
        }

        return resultReport;
    }

    private static String processSimpleToken(String matchString, String eventName, Integer position, String reportID) {
        String replaceString;
        Integer numberParam;
        List resultList;

        if(matchString.equals("%REPORT_NAME%")) {
            replaceString = "{[" + GetNameOfReport(reportID) + "]}";
        } else if(matchString.startsWith("%TOP_EVENT(")) {
            numberParam = Integer.valueOf(
                    matchString.substring(matchString.indexOf('(') + 1
                            , matchString.length() - 2));
            replaceString = GetTopEvent(numberParam, reportID);
        } else if(matchString.startsWith("%TOP_COMPANIES(")) {
            numberParam = Integer.valueOf(
                    matchString.substring(matchString.indexOf('(') + 1
                            , matchString.length() - 2));
            resultList = GetTopCompanies(numberParam, eventName, reportID);
            replaceString = new String();
            for(Object result : resultList) {
                replaceString += "{[" + (String)result + "]}, ";
            }
            if(replaceString.length() >= 3) {
                replaceString = replaceString.substring(0, replaceString.length() - 2);
            }
        } else if(matchString.startsWith("%TOP_PERSON(")) {
            numberParam = Integer.valueOf(
                    matchString.substring(matchString.indexOf('(') + 1
                            , matchString.length() - 2));
            resultList = GetTopPersons(numberParam, eventName, reportID);
            replaceString = new String();
            for(Object result : resultList) {
                replaceString += "{[" + (String)result + "]}, ";
            }
            if(replaceString.length() >= 3) {
                replaceString = replaceString.substring(0, replaceString.length() - 2);
            }
        } else if(matchString.startsWith("%TOP_ORGANIZATIONS(")) {
            numberParam = Integer.valueOf(
                    matchString.substring(matchString.indexOf('(') + 1
                            , matchString.length() - 2));
            resultList = GetTopOrganizations(numberParam, eventName, reportID);
            replaceString = new String();
            for(Object result : resultList) {
                replaceString += "{[" + (String)result + "]}, ";
            }
            if(replaceString.length() >= 3) {
                replaceString = replaceString.substring(0, replaceString.length() - 2);
            }
        } else if(matchString.startsWith("%TOP_POSITIONS(")) {
            numberParam = Integer.valueOf(
                    matchString.substring(matchString.indexOf('(') + 1
                            , matchString.length() - 2));
            resultList = GetTopPositions(numberParam, eventName, reportID);
            replaceString = new String();
            for(Object result : resultList) {
                replaceString += "{[" + (String)result + "]}, ";
            }
            if(replaceString.length() >= 3) {
                replaceString = replaceString.substring(0, replaceString.length() - 2);
            }
        } else if(matchString.startsWith("%TOP_TECHNOLOGIES(")) {
            numberParam = Integer.valueOf(
                    matchString.substring(matchString.indexOf('(') + 1
                            , matchString.length() - 2));
            resultList = GetTopTechnologies(numberParam, eventName, reportID);
            replaceString = new String();
            for(Object result : resultList) {
                replaceString += "{[" + (String)result + "]}, ";
            }
            if(replaceString.length() >= 3) {
                replaceString = replaceString.substring(0, replaceString.length() - 2);
            }
        } else if(matchString.startsWith("%TOP_INDUSTRY_TERMS(")) {
            numberParam = Integer.valueOf(
                    matchString.substring(matchString.indexOf('(') + 1
                            , matchString.length() - 2));
            resultList = GetTopIndustryTerms(numberParam, eventName, reportID);
            replaceString = new String();
            for(Object result : resultList) {
                replaceString += "{[" + (String)result + "]}, ";
            }
            if(replaceString.length() >= 3) {
                replaceString = replaceString.substring(0, replaceString.length() - 2);
            }
        } else if(matchString.startsWith("%TOP_MEDICAL_CONDITIONS(")) {
            numberParam = Integer.valueOf(
                    matchString.substring(matchString.indexOf('(') + 1
                            , matchString.length() - 2));
            resultList = GetTopMedicalConditions(numberParam, eventName, reportID);
            replaceString = new String();
            for(Object result : resultList) {
                replaceString += "{[" + (String)result + "]}, ";
            }
            if(replaceString.length() >= 3) {
                replaceString = replaceString.substring(0, replaceString.length() - 2);
            }
        } else if(matchString.startsWith("%TOP_EVENT_TAGS(")) {
            numberParam = Integer.valueOf(
                    matchString.substring(matchString.indexOf('(') + 1
                            , matchString.length() - 2));
            resultList = GetTopEventTags(numberParam, eventName, reportID);
            replaceString = new String();
            for(Object result : resultList) {
                replaceString += "{[" + (String)result + "]}, ";
            }
            if(replaceString.length() >= 3) {
                replaceString = replaceString.substring(0, replaceString.length() - 2);
            }
        } else if(matchString.startsWith("%TOP_CITIES(")) {
            numberParam = Integer.valueOf(
                    matchString.substring(matchString.indexOf('(') + 1
                            , matchString.length() - 2));
            resultList = GetTopCities(numberParam, eventName, reportID);
            replaceString = new String();
            for(Object result : resultList) {
                replaceString += "{[" + (String)result + "]}, ";
            }
            if(replaceString.length() >= 3) {
                replaceString = replaceString.substring(0, replaceString.length() - 2);
            }
        } else if(matchString.startsWith("%TOP_HEADLINES(")) {
            String result2;
            numberParam = Integer.valueOf(
                    matchString.substring(matchString.indexOf('(') + 1
                            , matchString.length() - 2));
            resultList = GetTopHeadlines(numberParam, eventName, reportID);
            replaceString = new String();
            for(Object result : resultList) {
                result2 = result.toString().replace("\"", "");
                result2 = result2.replace("\'", "");
                result2 = result2.replace("\n", "");
                replaceString += "{[" + (String)result2 + "]}, ";
            }
            if(replaceString.length() >= 3) {
                replaceString = replaceString.substring(0, replaceString.length() - 2);
            }
        } else if(matchString.startsWith("%PRINT(\"")) {
            replaceString = matchString.substring(matchString.indexOf("(\"") + 2
                    , matchString.length() - 3);
        } else if(matchString.equals("%EVENTS_NUMBER%")) {
            replaceString = GetEventsNumber();
        } else if(matchString.equals("%COMPANIES_NUMBER%")) {
            replaceString = GetCompaniesNumber(eventName, reportID);
        } else if(matchString.equals("%EVENT_POSITION%")) {
            replaceString = position.toString();
        } else if(matchString.equals("%REPORT_WEEKS%")) {
            replaceString = GetReportWeeks(reportID).toString();
        } else {
            replaceString = "WRONG_TOKEN";
        }

        if(replaceString.length() == 0) {
            replaceString = "none";
        }
        return replaceString;
    }

    public static List<String> listOrderedEvents(String reportID)
    {
        List<String> results;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select event from publication where report_id = :reportID group by event order by count(event) desc");
        query.setParameter("reportID", reportID);
        results = query.list();
        t.commit();
        session.flush();
        session.clear();

        return results;
    }

    // DATE FORMAT: "yyyy-MM-dd'T'HH:mm:ssXXX"
    public static Integer GetReportWeeks(String reportID) {
        Date publishDate = null;
        Date nowDate = null;
        SimpleDateFormat simpleDateFormat;
        List<String> dates;
        t = session.beginTransaction();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select publishDate from publication where report_id = :reportID");
        query.setString("reportID", reportID);
        dates = query.list();
        publishDate = new Date();

        for(String dateString : dates) {
            try {
                if(publishDate.after(simpleDateFormat.parse(dateString))) {
                    publishDate = simpleDateFormat.parse(dateString);
                }
            } catch (ParseException e) {
                t.commit();
                session.flush();
                return -1;
            }
        }

        nowDate = new Date();
        t.commit();
        session.flush();
        session.clear();

        return Weeks.weeksBetween(new DateTime(publishDate), new DateTime(nowDate)).getWeeks();
    }

    public static List<String> GetTopHeadlines(int number, String eventName, String reportID) {
        List<String> results;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select title from publication"
                + " where report_id = :reportID and event = '" + eventName
                + "' group by title order by count(title) desc limit " + number);
        query.setString("reportID", reportID);
        results = query.list();
        t.commit();
        session.flush();
        session.clear();

        return results;
    }

    public static String GetTopEvent(int position, String reportID)
    {
        String result;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select event from publication where report_id = :reportID group by event order by count(event) desc");
        query.setString("reportID", reportID);
        result = (String)query.list().get(position - 1);
        t.commit();
        session.flush();
        return result;
    }

    public static String GetEventsNumber()
    {
        String result;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select count(name) from events");
        result = query.list().get(0).toString();
        t.commit();
        session.flush();
        session.clear();

        return result;
    }

    public static String GetCompaniesNumber(String eventName, String reportID)
    {
        String result;
        List resultList;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select count(o.name) from opencalaistag o"
                + " INNER JOIN publicationtags pt ON o.id = pt.opencalaistag_id"
                + " INNER JOIN publication p ON p.pk = pt.publication_pk"
                + " where p.report_id = :reportID and o.category like 'Company' and p.event = '" + eventName
                + "' group by o.name order by count(o.name) desc");
        query.setString("reportID", reportID);
        resultList = query.list();
        if(resultList.size() == 0) {
            result = "0";
        } else {
            result = resultList.get(0).toString();
        }
        t.commit();
        session.flush();
        session.clear();

        return result;
    }

    public static String GetNameOfReport(String reportID)
    {
        return Get.getReport(reportID).getName();
    }

    public static List<String> GetTopTechnologies(int number, String reportID, String eventName)
    {
        List results;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select o.name from opencalaistag o"
                + " INNER JOIN publicationtags pt ON o.id = pt.opencalaistag_id"
                + " INNER JOIN publication p ON p.pk = pt.publication_pk"
                + " where p.report_id = :reportID and o.category like 'Technology' and p.event = '" + eventName
                + "' group by o.name order by count(o.name) limit "+ number);
        query.setString("reportID", reportID);
        results = query.list();
        t.commit();
        session.flush();
        session.clear();

        return results;
    }

    public static List<String> GetTopEventTags(int number, String eventName, String reportID)
    {
        List results;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select o.name from opencalaistag o"
            + " INNER JOIN publicationtags pt ON o.id = pt.opencalaistag_id"
            + " INNER JOIN publication p ON p.pk = pt.publication_pk"
            + " where p.report_id = :reportID and o.category like 'Event' and p.event = '" + eventName
            + "' group by o.name order by count(o.name) desc limit "+ number);
        query.setString("reportID", reportID);
        results = query.list();
        t.commit();
        session.flush();
        session.clear();

        return results;
    }

    public static List<String> GetTopCities(int number, String eventName, String reportID)
    {
        List results;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select o.name from opencalaistag o"
                + " INNER JOIN publicationtags pt ON o.id = pt.opencalaistag_id"
                + " INNER JOIN publication p ON p.pk = pt.publication_pk"
                + " where p.report_id = :reportID and o.category like 'City' and p.event = '" + eventName
                + "' group by o.name order by count(o.name) desc limit "+ number);
        query.setString("reportID", reportID);
        results = query.list();
        t.commit();
        session.flush();
        session.clear();

        return results;
    }

    public static List<String> GetTopPersons(int number, String eventName, String reportID)
    {
        List results;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select o.name from opencalaistag o"
                + " INNER JOIN publicationtags pt ON o.id = pt.opencalaistag_id"
                + " INNER JOIN publication p ON p.pk = pt.publication_pk"
                + " where p.report_id = :reportID and o.category like 'Person' and p.event = '" + eventName
                + "' group by o.name order by count(o.name) desc limit "+ number);
        query.setString("reportID", reportID);
        results = query.list();
        t.commit();
        session.flush();
        session.clear();

        return results;
    }

    public static List<String> GetTopMedicalConditions(int number, String eventName, String reportID)
    {
        List results;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select o.name from opencalaistag o"
                + " INNER JOIN publicationtags pt ON o.id = pt.opencalaistag_id"
                + " INNER JOIN publication p ON p.pk = pt.publication_pk"
                + " where p.report_id = :reportID and o.category like 'MedicalCondition' and p.event = '" + eventName
                + "' group by o.name order by count(o.name) desc limit "+ number);
        query.setString("reportID", reportID);
        results = query.list();
        t.commit();
        session.flush();
        session.clear();

        return results;
    }

    public static List<String> GetTopPositions(int number, String eventName, String reportID)
    {
        List results;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select o.name from opencalaistag o"
                + " INNER JOIN publicationtags pt ON o.id = pt.opencalaistag_id"
                + " INNER JOIN publication p ON p.pk = pt.publication_pk"
                + " where p.report_id = :reportID and o.category like 'Position' and p.event = '" + eventName
                + "' group by o.name order by count(o.name) desc limit " + number);
        query.setString("reportID", reportID);
        results = query.list();
        t.commit();
        session.flush();
        session.clear();

        return results;
    }

    public static List<String> GetTopOrganizations(int number, String eventName, String reportID)
    {
        List results;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select o.name from opencalaistag o"
                + " INNER JOIN publicationtags pt ON o.id = pt.opencalaistag_id"
                + " INNER JOIN publication p ON p.pk = pt.publication_pk"
                + " where p.report_id = :reportID and o.category like 'Organization' and p.event = '" + eventName
                + "' group by o.name order by count(o.name) desc limit " + number);
        query.setString("reportID", reportID);
        results = query.list();
        t.commit();
        session.flush();
        session.clear();

        return results;
    }

    public static List<String> GetTopCompanies(int number, String eventName, String reportID)
    {
        List results;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select o.name from opencalaistag o"
                + " INNER JOIN publicationtags pt ON o.id = pt.opencalaistag_id"
                + " INNER JOIN publication p ON p.pk = pt.publication_pk"
                + " where p.report_id = :reportID and o.category like 'Company' and p.event = '" + eventName
                + "' group by o.name order by count(o.name) desc limit "+ number);
        query.setString("reportID", reportID);
        results = query.list();
        t.commit();
        session.flush();
        session.clear();

        return results;
    }

    public static List GetTopIndustryTerms(int number, String eventName, String reportID)
    {
        List results;
        t = session.beginTransaction();
        t.setTimeout(5);
        Query query =  session.createSQLQuery("select o.name from opencalaistag o"
                + " INNER JOIN publicationtags pt ON o.id = pt.opencalaistag_id"
                + " INNER JOIN publication p ON p.pk = pt.publication_pk"
                + " where p.report_id = :reportID and o.category like 'IndustryTerm' and p.event = '" + eventName
                + "' group by o.name order by count(o.name) desc limit "+ number);
        query.setString("reportID", reportID);
        results = query.list();
        t.commit();
        session.flush();
        session.clear();

        return results;
    }
}

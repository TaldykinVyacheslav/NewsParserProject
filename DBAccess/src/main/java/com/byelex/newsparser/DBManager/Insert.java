package com.byelex.newsparser.DBManager;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import com.byelex.newsparser.Models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Игорь
 * Date: 2/17/14
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Insert {
    public static boolean isNewRefresh = false;

    public static void InsertDocs(ArrayList<Publication> pubs, String eventName) {
        Session sessions = null;
        Transaction transaction = null;
        try{
            SessionFactory sessionFactory1 = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            sessions = sessionFactory1.openSession();
            for(Publication pub : pubs)
            {
                List<Publication> publicationFound;
                Query query = sessions.createQuery("from Publication where pk = :pk");
                query.setParameter("pk", pub.getPk());
                publicationFound = query.list();
                if(!publicationFound.isEmpty()) {
                    transaction = sessions.beginTransaction();
                    sessions.delete(publicationFound.get(0));
                    transaction.commit();
                }
                pub.setEvent(eventName);
                transaction = sessions.beginTransaction();
                transaction.setTimeout(5);
                sessions.saveOrUpdate(pub);
                transaction.commit();
            }
            System.out.println("success");
        }
        catch(Exception ex){
            ;
        }
        finally{
            sessions.close();
        }
    }
    public static void InsertTerms(ArrayList<Opencalaistag> tags) {
        Session sessions = null;
        Transaction transaction;
        try {
            SessionFactory sessionFactory1 = new Configuration().configure().buildSessionFactory();
            sessions = sessionFactory1.openSession();
            for(Opencalaistag tag :tags)
            {
                transaction = sessions.beginTransaction();
                transaction.setTimeout(5);
                if(tag.getId() != null)
                    sessions.saveOrUpdate(tag);
                transaction.commit();
            }
            System.out.println("success");
        }
        catch(Exception ex){
            ;
        }
        finally{
            sessions.close();
        }
    }

    public static void InsertPubTags (List<Publicationtags> pubtags) {
        Session sessions = null;
        Transaction transaction;
        try {
            SessionFactory sessionFactory1 = new Configuration().configure().buildSessionFactory();
            sessions = sessionFactory1.openSession();
            transaction = sessions.beginTransaction();
            for(Publicationtags pt : pubtags) {
                try {
                    //opencalaistag_id = null [Id]
                    if(isNewRefresh) {
                        sessions.createQuery("delete from Publicationtags").executeUpdate();
                    }
                    transaction.setTimeout(5);
                    sessions.saveOrUpdate(pt);
                } catch (Exception ex) {
                    continue;
                }
            }
            transaction.commit();

            System.out.println("PUBLICATIONTAGS SIZE IS " + pubtags.size());
            System.out.println("success");
        }
        catch(Exception ex) {
            ;
        }
        finally {
            sessions.close();
        }
    }

    public static void clearNullPublicationTags() {
        Session sessions = null;
        Transaction transaction;
        try {
            SessionFactory sessionFactory1 = new Configuration().configure().buildSessionFactory();
            sessions = sessionFactory1.openSession();
            transaction = sessions.beginTransaction();
            sessions.createSQLQuery("delete from publicationtags where opencalaistag_id is null").executeUpdate();
            transaction.commit();
        }
        catch(Exception ex) {
            ;
        }
        finally {
            sessions.close();
        }
    }

    public static void clearAllTables() {
        SessionFactory sessionFactory1 = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session sessions = sessionFactory1.openSession();

        sessions.createQuery("delete from Publicationtags").executeUpdate();
        sessions.createQuery("delete from Publication").executeUpdate();
        sessions.createQuery("delete from Opencalaistag").executeUpdate();
        sessions.close();
    }
}


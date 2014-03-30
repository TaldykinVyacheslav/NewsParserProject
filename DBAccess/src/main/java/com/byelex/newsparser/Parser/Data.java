package com.byelex.newsparser.Parser;
import com.byelex.newsparser.Models.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Alexzander
 * Date: 13.02.14
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */
public class Data{
    private ArrayList<Opencalaistag> OpenCalaisTags;
    private ArrayList<Publication> Publications;
    private ArrayList<Publicationtags> Publicationtag;

    public Data() {
        OpenCalaisTags = new ArrayList<Opencalaistag>();
        Publications = new ArrayList<Publication>();
        Publicationtag = new ArrayList<Publicationtags>();
    }

    public ArrayList<Opencalaistag> getOpenCalaisTags() {
        return OpenCalaisTags;
    }
    public void setOpenCalaisTags(ArrayList<Opencalaistag> openCalaisTags) {
        OpenCalaisTags = openCalaisTags;
    }
    public void addToOpenCalaisTags(Opencalaistag openCalaisTag){
        this.OpenCalaisTags.add(openCalaisTag);
    }
    public void clearOpenCalaisTags(){
        this.OpenCalaisTags.clear();
    }

    public ArrayList<Publication> getPublications() {
        return Publications;
    }
    public void setPublications(ArrayList<Publication> publications) {
        Publications = publications;
    }
    public void addToPublications(Publication publication){
        this.Publications.add(publication);
    }
    public void clearPublications(){
        this.Publications.clear();
    }


    public ArrayList<Publicationtags> getPublicationtag() {
        return Publicationtag;
    }

    public void setPublicationtag(ArrayList<Publicationtags> publicationtag) {
        Publicationtag = publicationtag;
    }

    public void addToPublicationtag(Publicationtags publication){
        this.Publicationtag.add(publication);
    }
    public void clearPublicationtag(){
        this.Publicationtag.clear();
    }
}

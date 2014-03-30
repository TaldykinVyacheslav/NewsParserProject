package com.byelex.newsparser.Parser;


import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import com.byelex.newsparser.Models.*;
import com.byelex.newsparser.DBManager.*;

import static javax.xml.stream.XMLStreamConstants.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alexzander
 * Date: 12.02.14
 * Time: 20:17
 * To change this template use File | Settings | File Templates.
 */

public class Pars {
    private ListOfUrls urlsList;
    private ArrayList <String> filesToDelete;
    private ArrayList <String> filesToUse;

    public Pars() {
        urlsList = new ListOfUrls();
        filesToDelete = new ArrayList<String>();
        filesToUse = new ArrayList<String>();
    }

    public ListOfUrls getUrlsList() {
        return urlsList;
    }

    public ArrayList<String> getFilesToDelete() {
        return filesToDelete;
    }
    public void addFileToDelete(String fileToDelete){
        this.filesToDelete.add(fileToDelete);
    }
    public void clearFileToDelete(){
        this.filesToDelete.clear();
    }

    public ArrayList<String> getFilesToUse() {
        return filesToUse;
    }
    public void addFileToUse(String fileToUse){
        this.filesToUse.add(fileToUse);
    }
    public void clearFileToUse(){
        this.filesToUse.clear();
    }

    public void getXmlFromServiceUsingUrls() throws IOException, XMLStreamException {
        //Insert.clearAllTables();
        for(Map.Entry<String, String> urlEntry: urlsList.getListOfUrls().entrySet()){
            String eventName = urlEntry.getKey();
            String url = urlEntry.getValue();

            System.out.println("******************************\n" + eventName + "\n*********************************");

            // Из списка берется ссылка и загружает архив на диск.
            URL serviceUrl = new URL(url);
            File outFile = new File("outfile.zip");
            FileOutputStream outStream = new FileOutputStream(outFile);
            InputStream inStream = serviceUrl.openStream();
            int c;
            while ((c = inStream.read()) != -1)
            { outStream.write(c);}
            inStream.close();
            outStream.close();
            //Загрузка архива закончена.

            //Добавим название архива в список файлов на удаление.
            addFileToDelete("outfile.zip");
            //Добавление в список на удаление закончено.

            //Извлекаем файлы из архива "outfile.zip" на диск.
            ZipInputStream zipinputstream = new ZipInputStream(new FileInputStream("outfile.zip"));
            ZipEntry entry = zipinputstream.getNextEntry();
            byte[] buf = new byte[1024];
            int len;
            while(entry != null){
                String targetFile = entry.getName();
                addFileToUse(targetFile);
                OutputStream out = new FileOutputStream(targetFile);
                while ((len = zipinputstream.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                entry = zipinputstream.getNextEntry();
            }
            //Извлечение файлов .xml из архива "outfile.zip" закончено.

            //Чтение xml файлов и сохранение в базу данных
            for(String nameFileToUse: getFilesToUse()){
                XMLInputFactory factory = XMLInputFactory.newInstance();
                XMLStreamReader reader = factory.createXMLStreamReader(new BufferedInputStream(new FileInputStream(nameFileToUse)));
                String s, tagContent = null;
                Data data = null;
                Opencalaistag openCalaisTag = null;
                Publication publication = null;
                Publicationtags publicationtags = null;
                ArrayList openCalaisTagIds = null;
                int event;
                while(reader.hasNext()){
                    event = reader.next();
                    switch(event){
                        case START_ELEMENT:
                            s = reader.getLocalName();
                            if (s.equals("Data")){
                                //System.out.println("Data"); /////////////////////////Проверка на правильность работы////////////////////////////////////////////////
                                data = new Data();
                            } else if (s.equals("openCalaisTag")) {
                                openCalaisTag = new Opencalaistag();
                            } else if (s.equals("publication")){
                               // System.out.println("Publication"); /////////////////////////Проверка на правильность работы////////////////////////////////////////////////
                                publication = new Publication();
                            } else if (s.equals("openCalaisTagIds")){
                                openCalaisTagIds = new ArrayList();
                            } else if (s.equals("tagId")){
                                publicationtags = new Publicationtags();
                            }
                            break;
                        case CHARACTERS: {
                            tagContent = reader.getText().trim();
                            break;
                        }
                        case END_ELEMENT:  {
                            s = reader.getLocalName();
                            if (s.equals("id")) {
                                openCalaisTag.setId(Long.valueOf(tagContent));
                            } else if (s.equals("name")) {
                                openCalaisTag.setName(String.valueOf(tagContent));
                            } else if (s.equals("category")) {
                                openCalaisTag.setCategory(String.valueOf(tagContent));
                            } else if (s.equals("openCalaisTag")) {
                                data.addToOpenCalaisTags(openCalaisTag);
                            } else if (s.equals("pk")) {
                                publication.setPk(Long.valueOf(tagContent));
                            } else if (s.equals("publishDate")) {
                                publication.setPublishDate(String.valueOf(tagContent));
                            } else if (s.equals("contentType")) {
                                publication.setContentType(String.valueOf(tagContent));
                            } else if (s.equals("title")) {
                                publication.setTitle(String.valueOf(tagContent));
                            } else if (s.equals("url")) {
                                publication.setUrl(String.valueOf(tagContent));
                            } else if (s.equals("reach")) {
                                publication.setReach(Long.valueOf(tagContent));
                            } else if (s.equals("tagId")) {
                                publicationtags.setPk(publication.getPk());
                                publicationtags.setId(Long.valueOf(tagContent));
                                data.addToPublicationtag(publicationtags);
                                // new

                                //fin new
                            } else if (s.equals("publication")) {
                                data.addToPublications(publication);
                            }
                        }
                        default:
                            break;
                    }
                }
                // using data

                Insert.InsertTerms(data.getOpenCalaisTags());
                Insert.InsertDocs(data.getPublications(), eventName);
                Insert.InsertPubTags(data.getPublicationtag());
                addFileToDelete(nameFileToUse);   //добавляем в список на удаление
            }
            clearFileToUse();
            // Чтение xml файлов и сохранение в базу данных закончено

            //Удалим файлы которые находятся в списке на удаление
            for(String nameFileToDelete: getFilesToDelete()){
                File f = new File(nameFileToDelete);
                f.delete();
            }
            clearFileToDelete();
            //Удаление из списка на удаление завершено
        }
    }
}


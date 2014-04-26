package Agent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.apache.commons.io.FileUtils;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: Игорь
 * Date: 4/19/14
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebAgent {
    public static void main(String[] args) throws Exception {
        String text = "Many of his plays were published" +
                " in editions of varying quality and accuracy" +
                " during his lifetime. In 1623, John Heminges and Henry Condell," +
                " two friends and fellow actors of Shakespeare, published the First Folio," +
                " a collected edition of his dramatic works that included all but two of the" +
                " plays now recognised as Shakespeare's. It was prefaced with a poem by Ben Jonson," +
                " in which Shakespeare is hailed, presciently," +
                " as \"not of an age, but for all time.";
        Settings settings = new Settings();
        getTextSpeechMp3(text, settings);
    }

    public static String getTextSpeechMp3(String text, Settings settings) throws Exception {
        WebAgent http = new WebAgent();
        Document doc =  Jsoup.parse(http.sendPost(text,settings));  //setting можно и тут передавать, как те удобнее
        for(Element link:doc.select("a") )
        {
            String linkHref = link.attr("href");
            if (linkHref.contains(".mp3"))
            {
                return "http://www.fromtexttospeech.com"+linkHref;
            }
        }
        return null;
    }

    // HTTP POST request
    private String sendPost(String Text, Settings settings) throws Exception {
        String url = "http://www.fromtexttospeech.com";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        String urlParameters = CreateQuery(Text, settings);//"input_text=Departing+just+after+sunset%2C+the+Australian+Defense+Vessel+Ocean+Shield+sets+out+to+the+search+zone+in+the+southern+Indian+Ocean%2C+one+of+the+most+remote+places+on+Earth%2C+some+1%2C100+miles+off+the+coast+of+Western+Australia.%0D%0AIt+will+take+three+days+just+to+get+to+this+corner+of+the+word%2C+where+waves+are+taller+than+many+buildings%2C+and+the+seas+conceal+mountain+ranges.%0D%0ACNN+asked+to+ride+with+the+crew+of+the+Ocean+Shield+to+document+their+journey.+But+the+mission+is+too+long+and+too+important+to+allow+for+the+added+burden+of+carrying+journalists+onboard.%0D%0AAnxious+wait%0D%0ASo+we+sit+in+a+chartered+fishing+boat+named+Thunder+and+wait+anxiously+for+the+Ocean+Shield+to+begin+its+journey.+Our+expectation+of+a+Monday+morning+departure+is+quickly+dashed.+So+we+wait+on+the+water%2C+watching+the+boat+and+filing+hourly+live+updates.+The+6%3A30pm+departure+time+is+pushed+back+an+hour.+At+7%3A30pm%2C+the+ship+still+sits+at+the+dock.%0D%0AThe+Ocean+Shield+finally+departs+just+before+8pm%2C+cloaked+in+darkness%2C+only+its+deck+lights+guiding+the+way+from+Garden+Island%2C+out+of+Cockburn+Sound%2C+and+into+the+Indian+Ocean.%0D%0AWe+watch+as+the+Ocean+Shield+makes+its+way+up+the+narrow+channel+headed+for+the+open+sea%2C+traveling+so+fast+our+60-foot+fishing+boat+can%27t+keep+up.%0D%0A%22We+started+out%2C+he+was+running+about+8+to+10+knots+and+started+picking+up+speed%2C%22+says+our+captain%2C+Ray+Ruby.+%22I+think+by+the+time+he+was+doing+about+15+knots+we+were+still+in+the+channel.%22&language=US+English&voice=9&speed=0&action=process_text";
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        System.out.println("\nSending 'POST' request to URL : " + url);
        int responseCode = con.getResponseCode();
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();

    }
    public String CreateQuery(String text,Settings settings)
    {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("input_text",text);
        map.put("language",settings.language);
        map.put("voice",settings.voice);
        map.put("speed",settings.speed);
        map.put("action","process_text") ;

        System.out.println("EXITING CreateQuery METHOD");
        return UrlQueryCreator.urlEncodeUTF8(map);
    }

}

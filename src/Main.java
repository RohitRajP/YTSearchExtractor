import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // JSON object which holds the data to send to front end
        JsonObject finalJson =  new JsonObject();
        JsonObject tempJson = new JsonObject();

        // map variable to hold the data to insert into JSON object
        Map<String,String> tempMap = new HashMap< String,String>();

        // clearing map variable on each iteration
        tempMap.clear();

        try {
            // getting the html content of the page
            Document doc = Jsoup.connect("https://www.youtube.com/results?search_query=nigahug").get();
            Element content = doc.getElementById("content");
//            Element container = content.getElementsByClass("branded-page-v2-container").first();
            Element results = content.getElementById("results");
            Element ol = results.getElementsByTag("ol").first();

//            // getting count of results returned
            int count = ol.getElementsByClass("yt-lockup").size();

            // getting 20 records
            for(int i=0;i<count;i++){

                //System.out.println(""+i);
                // getting each result
                Element lock_up = ol.getElementsByClass("yt-lockup").get(i);

                // getting thumbnail
                Element thumbAnchor = lock_up.getElementsByTag("a").first();
                Element thumbImg = thumbAnchor.getElementsByTag("img").first();
                String thumbURL = thumbImg.attr("data-thumb");
                // some videos don't have the url stored in data-thumb
                // and instead have data stored in "src" attribute
                // if the url is not present in data-thumb
                if(thumbURL.length()==0)
                    // get the thumbnail image from the src
                    thumbURL = thumbImg.attr("src");
                //System.out.println(thumbURL);
                tempJson.addProperty("thumbURL",thumbURL);

                // getting content
                Element lock_up_content = lock_up.getElementsByClass("yt-lockup-content").first();

                // getting title
                Element aTitle = lock_up_content.getElementsByTag("a").first();
                String vidTitle = aTitle.attr("title");
                //System.out.println(vidTitle);
                tempJson.addProperty("vidTitle",vidTitle);

                // getting vidID
                String vidId = aTitle.attr("href");
                vidId = vidId.substring(9);
                //System.out.println(vidId);
                tempJson.addProperty("vidId",vidId);

                // getting channel name
                Element lockup_by_line = lock_up_content.getElementsByClass("yt-lockup-byline").first();
                try {
                    Element channelAnchor = lockup_by_line.getElementsByTag("a").first();
                    String vidChannel = channelAnchor.text();
                    //System.out.println(vidChannel);
                    tempJson.addProperty("vidChannel",vidChannel);

                    // getting view count
                    Element lockup_meta = lock_up_content.getElementsByClass("yt-lockup-meta").first();
                    Element viewCListItem = lockup_meta.getElementsByTag("li").get(1);
                    String vidViewCount = viewCListItem.text();
                    //System.out.println(vidViewCount+"\n");
                    tempJson.addProperty("vidViewCount",vidViewCount);

                    // adding data into jsonObject
                    finalJson.add(""+i,tempJson);

                } catch (NullPointerException e){
                    continue;
                }
                tempJson = new JsonObject();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(finalJson.toString());

    }

}

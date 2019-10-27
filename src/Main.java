import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            // getting the html content of the page
            Document doc = Jsoup.connect("https://www.youtube.com/results?search_query=we+cant+stop").get();

            Element content = doc.getElementById("content");
//            Element container = content.getElementsByClass("branded-page-v2-container").first();
            Element results = content.getElementById("results");
            Element ol = results.getElementsByTag("ol").first();
            Element lock_up = ol.getElementsByClass("yt-lockup").get(0);
//            int count = ol.getElementsByClass("yt-lockup").size();
//            System.out.println(count);

            // getting thumbnail
            Element thumbAnchor = lock_up.getElementsByTag("A").first();
            Element thumbImg = thumbAnchor.getElementsByTag("img").first();
            String thumbURL = thumbImg.attr("src");
            System.out.println(thumbURL);

            // getting content
            Element lock_up_content = lock_up.getElementsByClass("yt-lockup-content").first();

            // getting title
            Element aTitle = lock_up_content.getElementsByTag("a").first();
            String vidTitle = aTitle.attr("title");
            System.out.println(vidTitle);

            // getting vidID
            String vidId = aTitle.attr("href");
            vidId = vidId.substring(9);
            System.out.println(vidId);

            // getting channel name
            Element lockup_by_line = lock_up_content.getElementsByClass("yt-lockup-byline").first();
            Element channelAnchor = lockup_by_line.getElementsByTag("a").first();
            String vidChannel = channelAnchor.text();
            System.out.println(vidChannel);

            // getting view count
            Element lockup_meta = lock_up_content.getElementsByClass("yt-lockup-meta").first();
            Element viewCListItem = lockup_meta.getElementsByTag("li").get(1);
            String vidViewCount = viewCListItem.text();
            System.out.println(vidViewCount);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

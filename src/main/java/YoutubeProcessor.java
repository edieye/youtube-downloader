import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by edieye on 2019-12-11.
 */
public class YoutubeProcessor {

    /**
     *  String literal for youtube search query URL
     */
    private static final String YOUTUBE_QUERY_URL = "https://www.googleapis.com/youtube/v3/search";

    /**
     *  Integer to represent the maximum search results for youtube query
     */
    private static final Integer MAX_RESULTS = 1;

    /**
     * String literal for UTF-8 encoding
     */
    private static final String ENCODING = "UTF-8";

    /**
     *
     * @param query
     * @return videoId
     */
    public String getVideoString(String query) throws IOException {

        FileAccessUtilities fau = new FileAccessUtilities();
        String[] apiKey = fau.readFileIntoString(System.getProperty("user.home") + "/Desktop/youtube.txt");
        String searchURL = YOUTUBE_QUERY_URL + "?chart=mostPopular&part=snippet&maxResults=" + MAX_RESULTS + "&q=" + query +
                "&type=video&key=" + apiKey[0];
        String videoId = null;
        HttpURLConnection conn = null;
        try {
            URL newsUrl = new URL(searchURL);
            conn = (HttpURLConnection) newsUrl.openConnection();
            InputStream response = conn.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(response, ENCODING));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
            JSONObject items = jsonObject.getJSONArray("items").getJSONObject(0);
            videoId = items.getJSONObject("id").getString("videoId");
        }

        catch (IOException e) {
            System.out.println("There was an error getting the videoID" + e.getCause()
                    + " : " + e.getMessage());
        }

        finally {
            if (conn != null)
                conn.disconnect();
        }

        return videoId;
    }
}

import com.github.axet.vget.VGet;
import java.io.*;
import java.net.*;
import org.json.*;

/**
 * Created by edieye on 2019-12-09.
 */
public class DownloadMP3Processor {

    /**
     *  String literal that identifies the file name of youtube API key.
     */

    private static final String WRITE_TO_PATH = "/Users/edieye/Desktop/Music";

    /**
     *  String literal for youtube search query URL
     */
    private static final String YOUTUBE_QUERY_URL = "https://www.googleapis.com/youtube/v3/search";

    /**
     *  String literal for youtube download video URL
     */
    private static final String YOUTUBE_DOWNLOAD_URL = "https://www.youtube.com/watch?v=";

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
     * @return
     */
    private String getVideoString(String query) throws IOException {

        FileAccessUtilities fau = new FileAccessUtilities();
        String[] apiKey = fau.readFileIntoString(System.getProperty("user.home") + "/Desktop/youtube.txt");
        String searchURL = YOUTUBE_QUERY_URL + "?chart=mostPopular&part=snippet&maxResults=" + MAX_RESULTS + "&q=" + query +
                "&type=video&key=" + apiKey[0];
        String videoId = null;
        try {
            URL newsUrl = new URL(searchURL);
            HttpURLConnection conn = (HttpURLConnection) newsUrl.openConnection();
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

        } catch (IOException e) {
            System.out.println("There was an error getting the videoID" + e.getCause()
                    + " : " + e.getMessage());
        }
        return videoId;
    }

    private void downloadVideo(String videoId) {
        String downloadURL =  YOUTUBE_DOWNLOAD_URL + videoId;
        System.out.println(downloadURL);
        try {
            VGet v = new VGet(new URL(downloadURL), new File(WRITE_TO_PATH ));
            v.download();
            System.out.println("downloaded");
        }
        catch(MalformedURLException e) {
            System.out.println("There was an error getting the videoID" + e.getCause()
                    + " : " + e.getMessage());
        }
    }

    public void processFile(String fileName) throws IOException {
        FileAccessUtilities fau = new FileAccessUtilities();
        String[] songList = fau.readFileIntoString(fileName);
        for (String s: songList) {
            System.out.println(s);
        }

        for (String song: songList) {
            String videoId = getVideoString(song);
            downloadVideo(videoId);
        }
    }

    //https://stackoverflow.com/questions/36232867/search-videos-by-keyword-using-youtube-data-api-v3
}

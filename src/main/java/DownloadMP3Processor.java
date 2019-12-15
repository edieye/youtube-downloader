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
     *  String literal for youtube download video URL
     */
    private static final String YOUTUBE_DOWNLOAD_URL = "https://www.youtube.com/watch?v=";

    /**
     *
     * @param videoId
     * @return
     */
    private void downloadVideo(String videoId) {
        String downloadURL =  YOUTUBE_DOWNLOAD_URL + videoId;
        System.out.println(downloadURL);
        try {
            VGet v = new VGet(new URL(downloadURL), new File(WRITE_TO_PATH ));
            v.download();
        }
        catch(MalformedURLException e) {
            System.out.println("There was an error getting the videoID" + e.getCause()
                    + " : " + e.getMessage());
        }
    }

    /**
     *
     * @param fileName
     * @return
     */
    public void processFile(String fileName) throws IOException {
        FileAccessUtilities fau = new FileAccessUtilities();
        YoutubeProcessor youtubeProcessor = new YoutubeProcessor();
        String[] songList = fau.readFileIntoString(fileName);
        for (String song: songList) {
            String videoId = youtubeProcessor.getVideoString  (song);
            downloadVideo(videoId);
        }
    }
    //https://stackoverflow.com/questions/36232867/search-videos-by-keyword-using-youtube-data-api-v3
}

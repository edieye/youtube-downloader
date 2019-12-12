import java.io.IOException;

/**
 * Created by edieye on 2019-12-09.
 */
public class Main {

    public static void main(String[] args) throws IOException{
        DownloadMP3Processor obj = new DownloadMP3Processor();
        obj.processFile("/Users/edieye/Desktop/test.txt");
    }
}

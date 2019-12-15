import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by edieye on 2019-12-09.
 */
public class Main {

    public static void main(String[] args) throws IOException{
        DownloadMP3Processor obj = new DownloadMP3Processor();
        //obj.processFile("/Users/edieye/Desktop/test.txt");
        String download_path ="/Users/edieye/Desktop/Music/";
        String url ="https://www.youtube.com/watch?v=BuAURiD4Wmw";
        String[] command =
                {
                        "cmd",
                };
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("cd \""+download_path+"\"");
            stdin.println(download_path+"\\youtube-dl "+url);
            stdin.close();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


/**
 * Created by edieye on 2019-12-14.
 */
import java.io.InputStream;
import java.io.OutputStream;
public class SyncPipe implements Runnable {

    /**
     * Variable to write to buffer
     */
    private final OutputStream outputStream;

    /**
     * Variable to read to buffer
     */
    private final InputStream inputStream;

    public SyncPipe(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void run() {
        try {
            final byte[] buffer = new byte[1024];
            for (int length = 0; (length = inputStream.read(buffer)) != -1; ) {
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
/**
 * Created by edieye on 2019-12-09.
 */

import java.io.*;
import java.util.Scanner;

public class FileAccessUtilities {

    /**
     *
     * @param path
     * @return
     * @throws IOException
     */
    public String[] readFileIntoString(String path) throws IOException {

        String[] ret = null;
        if (!fileExists(path)) {
            throw new IOException("File doesn't exist");
        }

        File file = new File(path);
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ret = line.split(",");
            }
            return ret;
        }

        catch(FileNotFoundException e){
            e.printStackTrace();
        }

        finally {
            scanner.close();
        }
        return ret;
    }

    /**
     * Determines whether the file exists
     * @param path
     * @return true if the path exists, false otherwise
     */

    public boolean fileExists(String path) {
        if (!(path.contains("/Users"))) {
            path = path.replace("Users", "/Users");
        }
        File check = new File(path);
        return (check.exists());
    }
}

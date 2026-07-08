
import com.mixplus.Config;
import com.mixplus.FileType;

import java.io.IOException;

public class TestMain {

    public static void main(String[] args) {
        String path = "C:\\Users\\mixpl\\Desktop\\config-api\\src\\test\\resources\\config.json";
        Config config = new Config(path, FileType.JSON);
        config.createFile();
        try {
            config.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 50; i++) {
            config.set("I" + i, i);
        }

        try {
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
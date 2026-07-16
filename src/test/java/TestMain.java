
import com.mixplus.Config;
import com.mixplus.FileType;

import java.io.IOException;
import java.util.Map;

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
        Map<String, Object> server = config.getMap("server");
        System.out.println(server);

        System.out.println("name: " + server.get("name"));
        System.out.println("port: " + server.get("port"));
    }

}
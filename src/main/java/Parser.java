import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Parser {

    final static ClassLoader classLoader = Parser.class.getClassLoader();
    final static File file = new File(classLoader.getResource("inputData.json").getFile());
    static JsonReader reader;

    static {
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static Gson gson = new Gson();
    static InputData inputData = gson.fromJson(reader, InputData.class);

}

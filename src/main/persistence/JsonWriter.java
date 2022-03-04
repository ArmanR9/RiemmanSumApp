package persistence;

import model.RiemmanSum;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


/*
 Represents a file I/O writer that writes a JSON representation of a RiemmanSum to file

 NOTE:
 The JSONWriter is largely modelled off of
 https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonWriter {
    private String dest;
    private static final int TAB = 4;
    private PrintWriter writer;

    // EFFECTS: constructs new JsonWriter that has destination file set to dest
    public JsonWriter(String dest) {
        this.dest = dest;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(dest));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Riemman Sum to file
    public void write(RiemmanSum riSum) {
        JSONObject json = riSum.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    private void saveToFile(String jsonString) {
        writer.print(jsonString);
    }

}

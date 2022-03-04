package persistence;

import org.json.JSONObject;

/*

Interface for implementing objects to JSON translation functionality

NOTE:
The Writable interface model is borrowed from
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public interface Writable {
    // EFFECTS: returns this as JSON Object
    JSONObject toJson();
}

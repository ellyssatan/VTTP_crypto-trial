package vttp.crypto_trial.models;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Profile {
    private String name;
    private List<Coin> contents = new LinkedList<>();

    public Profile(String name) { }

    public String getName() {    return name;   }
    public void setName(String name) {      this.name = name;   }

    public List<Coin> getContents() {   return contents;    }
    public void setContents(List<Coin> contents) {      this.contents = contents;   }

    public List<Coin> add(Coin c) {
        // Check if duplicate content exists
        if(contents.contains(c)) {
            return contents;
        } else {
            contents.add(c);
            return contents;
        }
    }

    // jsonString ->jsonObject -> Coin object
    public static Profile create(String user) {
        System.out.printf(">>USER: %s", user);
        Reader reader = new StringReader(user);
        JsonReader jr = Json.createReader(reader);
        JsonObject jo = jr.readObject();
        Profile p = new Profile(jo.getString("name"));
        List<Coin> contents = jo.getJsonArray("contents").stream()
                            .map(v -> (JsonObject) v )
                            .map(Coin :: create)
                            .toList();
        p.setContents(contents);
        return p;
    }
    

    
}

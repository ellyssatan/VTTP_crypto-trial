package vttp.crypto_trial.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.JsonObject;

public class News {
    private String rank;
    private String imageUrl;
    private String title;
    private String url;
    private String source;
    private String body;
    private List<String> tags;
    private List<String> categories;

    public String getRank() {   return rank;    }
    public void setRank(String rank) {  this.rank = rank;   }

    public String getImageUrl() {   return imageUrl;    }
    public void setImageUrl(String imageUrl) {  this.imageUrl = imageUrl;   }

    public String getTitle() {      return title;   }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public List<String> getCategories() {
        return categories;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public static News create(JsonObject jo, int rank) {
        News n = new News();
        n.setRank(Integer.toString(rank));
        n.setImageUrl(jo.getString("imageurl"));
        n.setTitle(jo.getString("title"));
        n.setUrl(jo.getString("url"));
        n.setSource(jo.getString("source"));
        n.setBody(jo.getString("body"));
        n.setTags(n.split(jo.getString("tags")));
        n.setCategories(n.split(jo.getString("categories")));
        return n;

    }

    public ArrayList<String> split(String jsonStr) {
        ArrayList<String> list = new ArrayList<>();
        String[] separated = jsonStr.split("\\|");
        for (String l :separated) {
            list.add(l);
        }
        System.out.printf(">>> LIST: %s\n\n", list);
        return list;
    }
}

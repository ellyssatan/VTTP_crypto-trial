package vttp.crypto_trial.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Coin {
    private String rank;
    private String name;
    private String fullName;
    private String asset;
    private String price;
    private String mktCap;
    private String volumeDayTo;
    private String highDay;
    private String lowDay;

    public String getRank() {  return rank;    }
    public void setRank(String rank) {     this.rank = rank;   }

    public String getName() {   return name;    }
    public void setName(String name) {      this.name = name;   }

    public String getFullName() {   return fullName;    }
    public void setFullName(String fullName) {      this.fullName = fullName;   }

    public String getAsset() {   return this.asset = this.fullName + " (" + this.name + ")";    }
    public void setAsset(String asset) {      this.asset = this.fullName + " (" + this.name + ")";   }

    public String getPrice() {      return price;   }
    public void setPrice(String price) {    this.price = price;     }

    public String getMktCap() {     return mktCap;  }
    public void setMktCap(String mktCap) {      this.mktCap = mktCap;   }

    public String getVolumeDayTo() {    return volumeDayTo;     }
    public void setVolumeDayTo(String volumeDayTo) {    this.volumeDayTo = volumeDayTo;     }

    public String getHighDay() {    return highDay;     }
    public void setHighDay(String highDay) {    this.highDay = highDay;     }

    public String getLowDay() {     return lowDay;  }
    public void setLowDay(String lowDay) {
        this.lowDay = lowDay;
    }

    // Convert from json to Model object
    public static Coin create(JsonObject coinInfoJo, JsonObject displayJo) {
        Coin c = new Coin();
        // c.setRank(jo.getString(""));
        c.setName(coinInfoJo.getString("Name"));
        c.setFullName(coinInfoJo.getString("FullName"));
        // c.setAsset(coinInfoJo.getString("FullName") + "(" + coinInfoJo.getString("Name") + ")");
        c.setPrice(displayJo.getString("PRICE"));
        c.setMktCap(displayJo.getString("MKTCAP"));
        c.setVolumeDayTo(displayJo.getString("VOLUMEDAYTO"));
        c.setHighDay(displayJo.getString("HIGHDAY"));
        c.setLowDay(displayJo.getString("LOWDAY"));
        // System.out.printf(">>>> COIN: %s\n", c.toString());
        // System.out.printf(">>>>>>> setAsset:   %s", coinInfoJo.getString("FullName") + "(" + coinInfoJo.getString("Name") + ")");
        return c;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("FullName", asset)
            .add("PRICE", price)
            .add("MKTCAP", mktCap)
            .add("VOLUMEDAYTO", volumeDayTo)
            .add("HIGHDAY", highDay)
            .add("LOWDAY", lowDay)
            .build();
    }

    
}

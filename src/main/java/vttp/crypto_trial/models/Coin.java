package vttp.crypto_trial.models;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Coin {
    private String rank;
    private String name;
    private String fullName;
    private String asset;
    private String price;
    private String mktCap;
    private String volume24;
    private String openDay;
    private String highDay;
    private String lowDay;
    private String changePCTDay;
    private String changePCTHour;
    private String cirSupply;

    public String getRank() {  return rank;    }
    public void setRank(String rank) {     this.rank = rank;   }

    public String getName() {   return name;    }
    public void setName(String name) {      this.name = name;   }

    public String getFullName() {   return fullName;    }
    public void setFullName(String fullName) {      this.fullName = fullName;   }

    public String getAsset() {   return asset = this.fullName + " (" + this.name + ")";    }
    public void setAsset(String asset) {      this.asset = this.fullName + " (" + this.name + ")";   }

    public String getPrice() {      return price;   }
    public void setPrice(String price) {    this.price = price;     }

    public String getMktCap() {     return mktCap;  }
    public void setMktCap(String mktCap) {      this.mktCap = mktCap;   }

    public String getVolume24() {    return volume24;     }
    public void setVolume24(String volume24) {    this.volume24 = volume24;     }

    public String getOpenDay() {    return openDay;     }
    public void setOpenDay(String openDay) {    this.openDay = openDay;     }

    public String getHighDay() {    return highDay;     }
    public void setHighDay(String highDay) {    this.highDay = highDay;     }

    public String getLowDay() {     return lowDay;  }
    public void setLowDay(String lowDay) {      this.lowDay = lowDay;   }

    public String getChangePCTDay() {   return changePCTDay;    }
    public void setChangePCTDay(String changePCTDay) {  this.changePCTDay = changePCTDay;   }
    
    public String getChangePCTHour() {  return changePCTHour;   }
    public void setChangePCTHour(String changePCTHour) {    this.changePCTHour = changePCTHour; }
    
    public String getCirSupply() {      return cirSupply;   }
    public void setCirSupply(String cirSupply) {    this.cirSupply = cirSupply;     }

    // Convert from JsonObject to Model object (API 1)
    public static Coin create(JsonObject coinInfoJo, JsonObject displayJo, int rank) {
        Coin c = new Coin();
        c.setRank(Integer.toString(rank));
        c.setName(coinInfoJo.getString("Name" + ""));
        c.setFullName(coinInfoJo.getString("FullName"));
        c.setAsset(coinInfoJo.getString("FullName") + "(" + coinInfoJo.getString("Name") + ")");
        // c.setAsset(c.getFullName() + "(" + c.getName() + ")");
        c.setPrice(displayJo.getString("PRICE"));
        c.setMktCap(displayJo.getString("MKTCAP"));
        c.setVolume24(displayJo.getString("VOLUME24HOUR"));
        c.setOpenDay(displayJo.getString("OPENDAY"));
        c.setHighDay(displayJo.getString("HIGHDAY"));
        c.setLowDay(displayJo.getString("LOWDAY"));
        c.setChangePCTDay(displayJo.getString("CHANGEPCTDAY"));
        c.setChangePCTHour(displayJo.getString("CHANGEPCTHOUR"));
        c.setCirSupply(displayJo.getString("CIRCULATINGSUPPLY"));
        System.out.printf(">>>> COIN: %s\n", c.toString());
        // System.out.printf(">>>>>>> setAsset:   %s", coinInfoJo.getString("FullName") + "(" + coinInfoJo.getString("Name") + ")");
        return c;
    }

    // API 2
    public static Coin createTsym(JsonObject coins, String tsyms) {
        Coin c = new Coin();
        c.setPrice(coins.get(tsyms).toString());

        System.out.printf(">>>> COIN PRICE: %s\n", c.getPrice());

        return c;
    }

    // Convert from JsonObject to Model object (API 3)
    public static Coin create(JsonObject coinDisplay, String fsym) {
        Coin c = new Coin();
        c.setName(fsym);
        c.setPrice(coinDisplay.getString("PRICE"));
        c.setMktCap(coinDisplay.getString("MKTCAP"));
        c.setVolume24(coinDisplay.getString("VOLUME24HOUR"));
        c.setOpenDay(coinDisplay.getString("OPENDAY"));
        c.setHighDay(coinDisplay.getString("HIGHDAY"));
        c.setLowDay(coinDisplay.getString("LOWDAY"));
        c.setChangePCTDay(coinDisplay.getString("CHANGEPCTDAY"));
        c.setChangePCTHour(coinDisplay.getString("CHANGEPCTHOUR"));
        c.setCirSupply(coinDisplay.getString("CIRCULATINGSUPPLY"));
        // System.out.printf(">>>> COIN: %s\n", c.toString());
        // System.out.printf(">>>>>>> setAsset:   %s", coinInfoJo.getString("FullName") + "(" + coinInfoJo.getString("Name") + ")");
        return c;
    }

    // Create Coin from JsonObject
    public static Coin create(JsonObject jo) {
        Coin c = new Coin();
        c.setRank(jo.getString("rank"));
        c.setName(jo.getString("name"));
        c.setFullName(jo.getString("fullName"));
        c.setAsset(jo.getString("asset"));
        c.setPrice(jo.getString("price"));
        c.setMktCap(jo.getString("mktCap"));
        c.setVolume24(jo.getString("volume24"));
        c.setOpenDay(jo.getString("openDay"));
        c.setHighDay(jo.getString("highDay"));
        c.setLowDay(jo.getString("lowDay"));
        c.setChangePCTDay(jo.getString("changePCTDay"));
        c.setChangePCTHour(jo.getString("changePCTHour"));
        c.setCirSupply(jo.getString("cirSupply"));
        return c;
    }

    // Create Coin from JsonString
    public static Coin create(String jsonStr) {

        // {"item":"banana", "quan":"5"}

        System.out.printf(">>>>JSON STRING: %s", jsonStr);
        StringReader reader = new StringReader(jsonStr);
        JsonReader r = Json.createReader(reader);

        return create(r.readObject());
    }

    // Convert to JsonObject for saving
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("FullName", asset)
            .add("PRICE", price)
            .add("MKTCAP", mktCap)
            .add("VOLUME24HOUR", volume24)
            .add("OPENDAY", openDay)
            .add("HIGHDAY", highDay)
            .add("LOWDAY", lowDay)
            .add("CHANGEPCTDAY", changePCTDay)
            .add("CHANGEPCTHOUR", changePCTHour)
            .add("CIRCULATINGSUPPLY", cirSupply)
            .build();
    }

    
}

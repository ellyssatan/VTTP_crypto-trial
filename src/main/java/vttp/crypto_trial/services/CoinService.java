package vttp.crypto_trial.services;

import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.crypto_trial.models.Coin;
import vttp.crypto_trial.repositories.CoinRepository;

@Service
public class CoinService {
    private static final String topURL = "https://min-api.cryptocompare.com/data/top/mktcapfull";
    private static final String tsymURL = "https://min-api.cryptocompare.com/data/price";
    private static final String URL3 = "https://min-api.cryptocompare.com/data/pricemultifull";

    @Value("${CRYPTO_KEY}")
    private String key;

    @Autowired
    private CoinRepository coinRepo;

    public List<Coin> getCoins(String limit, String tsym) {

        // String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String payload;

        // Create url with query string (add parameters)
        String uri = UriComponentsBuilder.fromUriString(topURL)
        .queryParam("limit", limit)
        .queryParam("tsym", tsym)
        .queryParam("api_key", key)
        .toUriString();

        // Create the GET request, GET url
        RequestEntity<Void> req = RequestEntity.get(uri).build();

        // Make the call to OpenWeatherApp
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp;

        try {
            // Throws exception if status code is not between 200 - 399
            resp = template.exchange(req, String.class);
        } catch (Exception e) {
            System.err.printf("Error: %s\n", e);
            return Collections.emptyList();
        }

        // Check status code
        if (resp.getStatusCodeValue() != 200) {
            System.err.println("Error status code is not 200\n");
            return Collections.emptyList();
        }

        // Get payload 
        payload = resp.getBody();
        // System.out.println(">>> Payload: \n" + payload);
        
        // Convert payload into JsonObject
        // Convert string to a Reader
        Reader strReader = new StringReader(payload);

        // Create a JsonReader from reader
        JsonReader jsonReader = Json.createReader(strReader);

        // Read and save the payload as Json Object
        JsonObject coins = jsonReader.readObject();
                                         // should tally with the object name from api
        JsonArray coinList = coins.getJsonArray("Data");

        List<Coin> list = new LinkedList<>();
        for (int i = 0; i < coinList.size(); i++) {
            // loop through the top _ coins
            JsonObject jo = coinList.getJsonObject(i);

            JsonObject coinInfoJo = jo.getJsonObject("CoinInfo");
            
            JsonObject coinDetails = jo.getJsonObject("DISPLAY");
            JsonObject displayJo = coinDetails.getJsonObject("USD");
            list.add(Coin.create(coinInfoJo, displayJo, i+1));
        }
        return list;
    }

    public Coin getTsym(String fsym, String tsyms) {

        String payload;

        // Create url with query string (add parameters)
        String uri = UriComponentsBuilder.fromUriString(tsymURL)
        .queryParam("fsym", fsym)
        .queryParam("tsyms", tsyms)
        .queryParam("api_key", key)
        .toUriString();

        // Create the GET request, GET url
        RequestEntity<Void> req = RequestEntity.get(uri).build();

        // Make the call to OpenWeatherApp
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp;

        try {
            // Throws exception if status code is not between 200 - 399
            resp = template.exchange(req, String.class);
        } catch (Exception e) {
            System.err.printf("Error: %s\n", e);
            return null;
        }

        // // Check status code
        // if (resp.getStatusCodeValue() != 200) {
        //     System.err.println("Error status code is not 200\n");
        //     return Collections.emptyList();
        // }

        // Get payload 
        payload = resp.getBody();
        System.out.println(">>> Payload: \n" + payload);
        
        // Convert payload into JsonObject
        // Convert string to a Reader
        Reader strReader = new StringReader(payload);

        // Create a JsonReader from reader
        JsonReader jsonReader = Json.createReader(strReader);

        // Read and save the payload as Json Object
        JsonObject coins = jsonReader.readObject();
                                         // should tally with the object name from api
        // JsonObject price = coins.getJsonObject("USD");

        Coin c = Coin.createTsym(coins, tsyms);

        System.out.printf(">>> result: %s\n\n", c);
        return c;
    }

    public Coin getWatchlist(String fsyms, String tsyms) {

        String payload;
        // Create url with query string (add parameters)
        String uri = UriComponentsBuilder.fromUriString(URL3)
        .queryParam("fsyms", fsyms)
        .queryParam("tsyms", tsyms)
        .queryParam("api_key", key)
        .toUriString();

        // Create the GET request, GET url
        RequestEntity<Void> req = RequestEntity.get(uri).build();

        // Make the call to OpenWeatherApp
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp;

        try {
            // Throws exception if status code is not between 200 - 399
            resp = template.exchange(req, String.class);
        } catch (Exception e) {
            System.err.printf("Error: %s\n", e);
            return null;
        }

        // Check status code
        if (resp.getStatusCodeValue() != 200) {
            System.err.println("Error status code is not 200\n");
            return null;
        }

        // Get payload 
        payload = resp.getBody();
        System.out.println(">>> Payload: \n" + payload);
        
        // Convert payload into JsonObject
        // Convert string to a Reader
        Reader strReader = new StringReader(payload);

        // Create a JsonReader from reader
        JsonReader jsonReader = Json.createReader(strReader);

        // Read and save the payload as Json Object
        JsonObject coins = jsonReader.readObject();
                                         // should tally with the object name from api
        JsonObject coinRaw = coins.getJsonObject("DISPLAY");
        JsonObject coinName = coinRaw.getJsonObject(fsyms);
        JsonObject coinDisplay = coinName.getJsonObject(tsyms);
        System.out.printf(">>> COINDEETS: %s\n\n", coinDisplay);


        Coin c = Coin.create(coinDisplay, fsyms);
        return c;
    }
}

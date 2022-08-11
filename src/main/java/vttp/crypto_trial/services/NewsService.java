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
import vttp.crypto_trial.models.News;
import vttp.crypto_trial.repositories.CoinRepository;

@Service
public class NewsService {
    private static final String url = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";
    
    @Value("${CRYPTO_KEY}")
    private String key;

    public List<News> getNews() {

        String payload;

        // Create url with query string (add parameters)
        String uri = UriComponentsBuilder.fromUriString(url)
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

        List<News> list = new LinkedList<>();
        for (int i = 0; i < coinList.size(); i++) {
            // loop through the top _ coins
            JsonObject jo = coinList.getJsonObject(i);

            list.add(News.create(jo, i+1));
        }
        return list;
    }

    
}

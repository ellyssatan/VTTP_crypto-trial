package vttp.crypto_trial.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.crypto_trial.models.Coin;
import vttp.crypto_trial.models.News;
import vttp.crypto_trial.models.Profile;
import vttp.crypto_trial.repositories.CoinRepository;
import vttp.crypto_trial.services.CoinService;
import vttp.crypto_trial.services.NewsService;

@Controller
@RequestMapping(path = "/account")
public class CryptoController {

    @Autowired
    private CoinService cSvc;

    @Autowired
    private NewsService nSvc;

    @Autowired
    private CoinRepository cRepo;

    @PostMapping
    public String getUser(@RequestBody MultiValueMap<String, String> form, Model model) {

        String user = form.getFirst("user");

        Optional<Profile> p = cRepo.getProfile(user);
        List<String> contents;
        Profile profile;

        if (p.isEmpty()) {
            System.out.println("<<<<NEW USER>>>>");
            profile = new Profile(user);
            System.out.printf(">>> New PROFILE: %s\n", profile);
            contents = profile.getCart();
        } else {
            profile = p.get();
            System.out.printf(">>> Loaded PROFILE: %s\n", profile);
            // List of items
            contents = profile.getCart();
        }
        

        System.out.printf(">>> Contents: %s\n", contents);

        model.addAttribute("user", user.toUpperCase());
        // model.addAttribute("contents",  contents);
        return "test";

        
    }

    @PostMapping(path = "/crypto")
    public String getList(@RequestBody MultiValueMap<String, String> form, Model model) {

        String user = form.getFirst("user");
        String limit = form.getFirst("number");
        String tsym = form.getFirst("sym");

        List<Coin> list = cSvc.getCoins(limit, tsym);
        System.out.printf(">> LIST: %s\n", list);

        model.addAttribute("user", user);
        model.addAttribute("number", limit);
        model.addAttribute("list", list);
        return "test";
    }

    @GetMapping(path = "/tsym")
    public String getTsym(@RequestParam String fsym, @RequestParam String tsyms, Model model) {

        Coin price = cSvc.getTsym(fsym, tsyms);
        String coinPrice = price.getPrice();
        System.out.printf(">>>>> Price from controller: %s\n\n", price);

        model.addAttribute("price", coinPrice);
        return "test";
    }

    @PostMapping(path = "/watchlist")
    public String getWatchlist(@RequestBody MultiValueMap<String, String> form, Model model) {
        
        String user = form.getFirst("user");
        String fsyms = form.getFirst("fsyms");
        Coin watchlist = cSvc.getWatchlist(fsyms.toUpperCase(), "USD");
        System.out.printf(">>> Watchlist: %s", watchlist);

        model.addAttribute("user", user);
        model.addAttribute("watchlist", watchlist);
        return "test";
    }

    @PostMapping(path = "/news")
    public String getNews(Model model) {
        
        // String user = form.getFirst("user");
        List<News> newsList = nSvc.getNews();
        System.out.printf(">>> newsList: %s", newsList);

        // model.addAttribute("user", user);
        model.addAttribute("newsList", newsList);
        return "test";
    }

}

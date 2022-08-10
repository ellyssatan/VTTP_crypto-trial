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
import vttp.crypto_trial.models.Profile;
import vttp.crypto_trial.repositories.CoinRepository;
import vttp.crypto_trial.services.CoinService;

@Controller
@RequestMapping
public class CryptoController {

    @Autowired
    private CoinService cSvc;

    @Autowired
    private CoinRepository cRepo;

    @PostMapping(path = "/account")
    public String getUser(@RequestBody MultiValueMap<String, String> form, Model model) {

        String user = form.getFirst("user");

        Optional<Profile> p = cRepo.getProfile(user);
        List<Coin> contents;
        Profile profile;

        if (p.isEmpty()) {

            profile = new Profile(user);
            System.out.printf(">>> New PROFILE: %s\n", profile);
            contents = profile.getContents();
        } else {
            profile = p.get();
            System.out.printf(">>> Loaded PROFILE: %s\n", profile);
            // List of items
            contents = profile.getContents();
        }
        
        System.out.printf(">>> Contents: %s\n", contents);

        model.addAttribute("user", user.toUpperCase());
        model.addAttribute("contents",  contents);
        return "account";

        
    }

    @PostMapping(path = "/crypto")
    public String getList(@RequestBody MultiValueMap<String, String> form, Model model) {

        String limit = form.getFirst("number");
        String tsym = form.getFirst("sym");

        List<Coin> list = cSvc.getCoins(limit, tsym);

        model.addAttribute("number", limit);
        model.addAttribute("list", list);
        return "account";
    }

    @GetMapping(path = "/tsym")
    public String getTsym(@RequestParam String fsym, @RequestParam String tsyms, Model model) {

        Coin price = cSvc.getTsym(fsym, tsyms);
        String coinPrice = price.getPrice();
        System.out.printf(">>>>> Price from controller: %s\n\n", price);


        // String[] favCoins = request.get

        // List<Coin> list = cSvc.getCoins(limit, tsym);

        model.addAttribute("price", coinPrice);
        return "account";
    }
}

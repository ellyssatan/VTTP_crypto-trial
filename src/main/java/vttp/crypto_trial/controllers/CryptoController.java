package vttp.crypto_trial.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.crypto_trial.models.Coin;
import vttp.crypto_trial.services.CoinService;

@Controller
@RequestMapping
public class CryptoController {

    @Autowired
    private CoinService cSvc;

    @PostMapping(path = "/crypto")
    public String getList(@RequestBody MultiValueMap<String, String> form, Model model) {

        String limit = form.getFirst("number");
        String tsym = form.getFirst("sym");

        List<Coin> list = cSvc.getCoins(limit, tsym);

        model.addAttribute("number", limit);
        model.addAttribute("list", list);
        // model.addAttribute("limit", limit);
        // model.addAttribute("tsym", tsym);
        return "index";
    }
}

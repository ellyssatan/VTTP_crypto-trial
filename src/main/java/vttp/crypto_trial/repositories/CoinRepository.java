package vttp.crypto_trial.repositories;

import java.lang.StackWalker.Option;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import vttp.crypto_trial.models.Coin;
import vttp.crypto_trial.models.Profile;

@Repository
public class CoinRepository {

    @Autowired
    @Qualifier("redis")
    private RedisTemplate<String, String> redisTemplate;

    public void saveUser(String user) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.leftPush("users", user);
    }

    // Get profile
    public Optional<Profile> getProfile(String user) {

        System.out.printf("<<USER>> IS %s\n\n", user);

		// 1. check if empty
        if (!redisTemplate.hasKey(user)) {
            System.out.printf(">>>> %s NOT FOUND\n\n", user);
            return Optional.empty();
        }

        System.out.println("HERRERERERE");
        // Retrieve items in cart if cart exists
        List<Coin> contents = new LinkedList<>();
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        // returns how many items in the cart
        long size = listOps.size(user);
        
        for (long i = 0; i < size; i++) {
            String coin = listOps.index(user, i);
            System.out.println("COINNNN" + coin);
            contents.add(Coin.create(coin));

        }
    
        Profile p = new Profile(user);
        p.setContents(contents);
        return Optional.of(p);
	}

	// clear all existing and add
	public void save(Profile p) {
		String name = p.getName();
		List<Coin> contents = p.getContents();
		ListOperations<String, String> listOps = redisTemplate.opsForList();
		long l = listOps.size(name);
		if (l > 0) {
			listOps.trim(name, 0, l);
		}
		listOps.leftPushAll(name, 
				contents.stream()
					.map(v -> v.toJson().toString()).toList()
		);
	}

}

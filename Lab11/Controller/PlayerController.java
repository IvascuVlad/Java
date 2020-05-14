package com.example.demo.Controller;

import com.example.demo.Game;
import com.example.demo.Gomoku.Board;
import com.example.demo.Player;
import com.example.demo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    private PlayerRepository repository;

    @GetMapping
    public ResponseEntity<List<Player>> getPlayers(){
        List<Player> players = repository.findAll();
        if(players.size() > 0){
            return new ResponseEntity<List<Player>>(players,new HttpHeaders(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<List<Player>>(new ArrayList<>(),new HttpHeaders(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Player> create(@RequestBody Player player){
        player.setId(UUID.randomUUID());
        player = repository.save(player);
        return new ResponseEntity<Player>(player, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePlayer(@PathVariable UUID id, @RequestParam String name) {
        List<Player> players = repository.findAll();
        for (Player player : players) {
            if(player.getId().equals(id)) {
                player.setName(name);
                repository.save(player);
                return new ResponseEntity<>("Player name changed in " + player.getName(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND); //or GONE
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable UUID id) {
        List<Player> players = repository.findAll();
        for (Player player : players) {
            if(player.getId().equals(id)) {
                repository.delete(player);
                return new ResponseEntity<>("Player " + player.getName() + " removed", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Player not found", HttpStatus.GONE);
    }

    public Player createPlayerController(String name){
        final String uri = "http://localhost:8081/players";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("name", name);

        ResponseEntity<Player> response = restTemplate.postForEntity(uri, map, Player.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return new Player();
        }
    }
}

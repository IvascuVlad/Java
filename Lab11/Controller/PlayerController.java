package com.example.demo.Controller;

import com.example.demo.GameRepository;
import com.example.demo.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    private GameRepository repository;

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
            if(player.id.equals(id)) {
                player.setName(name);
                return new ResponseEntity<>("Player name changed in " + player.getName(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND); //or GONE
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable UUID id) {
        List<Player> players = repository.findAll();
        for (Player player : players) {
            if(player.id.equals(id)) {
                players.remove(player);
                return new ResponseEntity<>("Player " + player.getName() + " removed", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Player not found", HttpStatus.GONE);
    }
}

package com.example.demo.Controller;

import com.example.demo.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    private GameRepository repository;

    @GetMapping
    public ResponseEntity<List<Game>> getGames() throws GameNotFound {
        List<Game> games = repository.findAll();
        if(null == games){
            throw new GameNotFound("No games to show",new Throwable("Get error"));
        }
        if(games.size() > 0){
            return new ResponseEntity<>(games,new HttpHeaders(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ArrayList<>(),new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Game> create(){
        Game game = new Game();
        game.setId(UUID.randomUUID());
        game = repository.save(game);
        return new ResponseEntity<>(game, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGame(@PathVariable UUID id, @RequestParam UUID playerId) {
        List<Game> games = repository.findAll();
        for (Game game : games) {
            if(game.id.equals(id)) {
                if(game.getPlayer1() == null)
                    game.setPlayer1(playerId);
                else if (game.getPlayer2() == null){
                    game.setPlayer2(playerId);
                }else if (game.getWinner() == null){
                    game.setWinner(playerId);
                    repository.save(game);
                    return new ResponseEntity<>("You are the winner of this game!", HttpStatus.OK);
                }else
                {
                    return new ResponseEntity<>("The game has maximum number of players", HttpStatus.OK);
                }
                repository.save(game);
                return new ResponseEntity<>("You are now in this game!", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND); //or GONE
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable UUID id) {
        List<Game> games = repository.findAll();
        for (Game game : games) {
            if(game.id.equals(id)) {
                repository.delete(game);
                return new ResponseEntity<>("Game with id = " + game.getId() + " removed", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Game not found", HttpStatus.GONE);
    }

    public List<Game> getGamesController() throws GameNotFound {
        final String uri = "http://localhost:8081/games";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Game>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Game>>(){});
        if (response == null)
            throw new GameNotFound("There are no games!",null);
        return response.getBody();
    }

    public Game createGameController(){
        final String uri = "http://localhost:8081/games";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Game> response = restTemplate.exchange(uri, HttpMethod.POST, null, new ParameterizedTypeReference<Game>(){});
        if(response.getStatusCode() == HttpStatus.CREATED)
            return response.getBody();
        else
            return null;
    }

    public boolean joinGameController(UUID gameId, UUID playerId){
        final String uri = "http://localhost:8081/games/" + gameId + "?playerId=" + playerId;
        RestTemplate restTemplate = new RestTemplate();
        Game newGame = new Game();
        restTemplate.put(uri, newGame);
        return true;
    }

    /*rezolvarea exceptiilor*/
    @RestControllerAdvice
    public class MyExceptionAdvice {
        @ExceptionHandler(GameNotFound.class)
        public ResponseEntity<MyErrorResponse> handleGenericNotFoundException(GameNotFound e) {
            MyErrorResponse error = new MyErrorResponse(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}

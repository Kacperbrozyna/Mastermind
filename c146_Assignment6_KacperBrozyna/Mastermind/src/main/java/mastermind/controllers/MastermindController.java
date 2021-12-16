package mastermind.controllers;

import java.util.List;

import mastermind.models.Round;
import mastermind.service.GameFunctionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mastermind.models.Game;


//controller for the application
@RestController
@RequestMapping("/mastermind")
public class MastermindController {

    //spring DI
    @Autowired
    private GameFunctionality service;

    //mapping /mastermind/game to get all games
    @GetMapping("/game")
    public List<Game> all() {

        //getting all the games
        List<Game>Games = service.getAllGames();

        //for each game in the array, cover up the answer if the game is not finished
        for(Game game : Games)
        {
            if(!game.isGameFinished())
            {
                game.setGeneratedAnswer("Currently Confidential");
            }
        }

        return Games;
    }

    //mapping /begin to create a game
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(Game game) {
        Game tempGame = service.createGame(game);
        tempGame.setGeneratedAnswer("Currently Confidential");
        return tempGame;
    }

    //mapping /game/id to find a specific game
    @GetMapping("/game/{id}")
    public ResponseEntity<Game> findById(@PathVariable int id) {

        //getting a game
        Game result = service.getGame(id);

        //checking if the returned value is null
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

        //if the game is not finished cover up the answer
        if(!result.isGameFinished())
        {
            result.setGeneratedAnswer("Currently Confidential");
        }

        //return the specific game
        return ResponseEntity.ok(result);
    }

    //mapping /guess/id
    @PostMapping("/guess/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public String addRound(@PathVariable int id, @RequestBody Round round)
    {
        //creating variable
        String output = "";

        //creating a new round
        Round newRound = service.createRound(id, round);

        //checking if the guess was created
        if(newRound == null)
        {
            return "Unable to process the guess";
        }

        //checking if the round has all correct guesses
        if(newRound.getExact() == 4)
        {
            output = "You Won! \n";
        }

        //returning the output
         return output + "\n Number of Exact Matches: " + newRound.getExact()
                 + "\n Number of Partial Matches: " + newRound.getPartial();
    }

    //mapping /guess/id to get all guesses from a specific game
    @GetMapping("/guess/{id}")
    public String getGuesses(@PathVariable int id)
    {
        //getting all the rounds
        List<Round> rounds = service.getAllRoundsForGame(id);

        //checking if the array is empty
        if(rounds.isEmpty())
        {
            return "No rounds were able to be retrieved";
        }

        //outputting all the rounds
        return rounds.toString();
    }
}

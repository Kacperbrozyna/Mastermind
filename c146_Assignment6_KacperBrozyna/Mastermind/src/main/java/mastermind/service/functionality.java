package mastermind.service;

import mastermind.models.Game;
import mastermind.models.Round;

import java.util.List;

//service layer interface
public interface functionality {

    //CRUD Operations

    //Function to create a game object
    Game createGame(Game game);

    //function to update the game object
    boolean updateGame(Game game);

    //function to create a round with gameID
    Round createRound(int iD, Round round);

    //function to get game
    Game getGame(int id);

    //function to get all game objects
    List<Game> getAllGames();

    //function to get rounds for a specific game id
    List<Round> getAllRoundsForGame(int id);

    //function to be overridden to generate the answer for a game
    String generateAnswer();

    //function to be overridden to check if the user guessed the answer
    int[] checkGuesses(String answer, String guess);
}

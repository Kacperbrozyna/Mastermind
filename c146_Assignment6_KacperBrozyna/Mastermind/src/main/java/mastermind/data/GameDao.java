package mastermind.data;

import mastermind.models.Game;

import java.util.List;

//interface for game CRUD operations
public interface GameDao {

    //function to add game
    Game addGame(Game game);

    //function to get all games
    List<Game> getAllGames();

    //get game by id
    Game getGameById(int id);

    // true if item exists and is updated
    boolean updateGame(Game game);

    //function to delete game by id
    void deleteGameById(int id);

}
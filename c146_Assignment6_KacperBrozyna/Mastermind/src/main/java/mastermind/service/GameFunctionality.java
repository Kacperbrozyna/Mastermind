package mastermind.service;

import mastermind.data.GameDaoDB;
import mastermind.data.RoundDaoDB;
import mastermind.models.Game;
import mastermind.models.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class GameFunctionality implements functionality {

    private GameDaoDB gameDao;
    private RoundDaoDB roundDao;

    //constructor
    @Autowired
    public GameFunctionality(GameDaoDB gameDao, RoundDaoDB roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }

    //overridden create function
    @Override
    public Game createGame(Game game) {
        game.setGeneratedAnswer(generateAnswer());
        return gameDao.addGame(game);
    }

    //overridden update function
    @Override
    public boolean updateGame(Game game) {
        gameDao.updateGame(game);
        return true;
    }

    //overridden create round function
    @Override
    public Round createRound(int Id, Round round) {
        //getting game from DB with specific id
        Game game = gameDao.getGameById(Id);

        //if the game is not null
        if (game != null) {

            //setting gameId in round to id of the game
            round.setGameID(Id);

            //calling function to check both user and generated arrays and returning value
            int[] temp = checkGuesses(gameDao.getGameById(Id).getGeneratedAnswer(), round.getUserGuess());

            //setting exact and partial values to the round object
            round.setExact(temp[0]);
            round.setPartial(temp[1]);

            //if all the numbers are exact
            if (temp[0] == 4) {
                //set the game state to finished
                game.setGameFinished(true);
            }

            //calling update of the game
            updateGame(game);

            //calling dao function to create round
            return roundDao.addRound(round);
        }

        //returns null in case
        return null;
    }

    //overridden function to get game by id
    @Override
    public Game getGame(int id) {
        return gameDao.getGameById(id);
    }

    //overridden function to get all games
    @Override
    public List<Game> getAllGames() {
        return gameDao.getAllGames();
    }

    //overridden function to get all rounds for a specific game
    @Override
    public List<Round> getAllRoundsForGame(int id) {
        return roundDao.getRoundsForGame(id);
    }

    //overridden function to generate an answer for the game object
    @Override
    public String generateAnswer() {
        //creating variables
        boolean bFlag = true;
        int increment = 0;
        int[] generatedGuess = {-1, -1, -1, -1};
        Random numbGenerator = new Random();

        //while loop until all the array numbers are unique
        while (increment != 4) {

            //generate a temp new random numb
            int generatedNumber = numbGenerator.nextInt(9) + 1;

            //for each number in generated guess array
            for (int guess : generatedGuess) {

                //if the generated number is the same as the number in the array
                if (generatedNumber == guess) {
                    bFlag = false;
                    break;
                }
            }

            //only increment and add the generated number to the array if the flag is true
            if (bFlag) {
                generatedGuess[increment] = generatedNumber;
                increment++;
            }

            //setting flag
            bFlag = true;
        }

        //return a string of the array of ints
        return generatedGuess[0] + ","
                + generatedGuess[1] + ","
                + generatedGuess[2] + ","
                + generatedGuess[3];
    }

    //overridden function to check generated answer and user guess
    @Override
    public int[] checkGuesses(String answer, String guess) {
        //creating array
        int[] matches = {0, 0};

        //creating array by splitting string
        String[] answerArray = answer.split(",");
        String[] guessArray = guess.split(",");

        //if the arrays are equal
        if (Arrays.equals(answerArray, guessArray)) {

            //set array variable to 4
            matches[0] = 4;
        } else {

            //looping through guess array
            for (int i = 0; i < guessArray.length; i++) {
                //checking if the inputs are between 1 and 9
                if (Integer.parseInt(guessArray[i]) < 1 || Integer.parseInt(guessArray[i]) > 9) {
                    continue;
                }
                //looping through answer array
                for (int j = 0; j < answerArray.length; j++) {
                    //if the numbers are in exact positions, increment exact
                    if (answerArray[i].equals(guessArray[i]) && i == j) {
                        matches[0]++;

                    }
                    //if the numbers are not in exact position but in array, increment partial
                    else if (guessArray[i].equals(answerArray[j])) {
                        matches[1]++;
                    }
                }
            }
        }

        //return the array
        return matches;
    }
}

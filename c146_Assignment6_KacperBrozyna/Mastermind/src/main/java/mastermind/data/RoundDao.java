package mastermind.data;

import mastermind.models.Round;
import java.util.List;


//Interface for Round CRUD operations
public interface RoundDao {

    //function to add round
    Round addRound(Round round);

    void deleteRoundById(int id);

    List<Round> getAllRounds();

    //get all rounds for specific game id
    List<Round> getRoundsForGame(int id);
}

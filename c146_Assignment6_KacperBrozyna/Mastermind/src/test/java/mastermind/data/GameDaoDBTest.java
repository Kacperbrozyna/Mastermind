package mastermind.data;

import mastermind.models.Game;
import mastermind.models.Round;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class GameDaoDBTest {

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    public GameDaoDBTest()
    {}

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        List<Game> games = gameDao.getAllGames();
        for(Game game : games)
        {
            gameDao.deleteGameById(game.getId());
        }

        List<Round> rounds = roundDao.getAllRounds();
        for (Round round : rounds)
        {
            roundDao.deleteRoundById(round.getRoundID());
        }
    }

    @AfterEach
    public void tearDown() {
    }


    @Test
    public void testAddAndGetGame()
    {
        Game game = new Game();
        game.setGeneratedAnswer("1,2,3,4");
        game.setTime(game.getTime());
        game.setId(1);
        game = gameDao.addGame(game);

        Game fromDao = gameDao.getGameById(game.getId());

        assertEquals(game.getId(), fromDao.getId());
    }

    @Test
    public void testGetAllGames()
    {
        Game game1 = new Game();
        game1.setGeneratedAnswer("1,2,3,4");
        game1.setTime(game1.getTime());
        game1.setId(2);
        game1 = gameDao.addGame(game1);

        Game game2 = new Game();
        game2.setGeneratedAnswer("1,2,3,4");
        game2.setTime(game1.getTime());
        game2.setId(3);
        game2 = gameDao.addGame(game2);

        List<Game> games = gameDao.getAllGames();

        assertEquals(2, games.size());
    }

    @Test
    public void testUpdateGame()
    {
        Game game1 = new Game();
        game1.setGeneratedAnswer("1,2,3,4");
        game1.setTime(game1.getTime());
        game1.setId(4);
        game1 = gameDao.addGame(game1);

        Game fromDao = gameDao.getGameById(game1.getId());

        assertEquals(game1.getId(), fromDao.getId());

        game1.setGeneratedAnswer("2,3,4,5");

        gameDao.updateGame(game1);

        fromDao = gameDao.getGameById(game1.getId());
        assertEquals(game1.getGeneratedAnswer(),fromDao.getGeneratedAnswer());
    }

}
package mastermind.data;

import java.sql.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import mastermind.models.Game;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GameDaoDB implements GameDao {

    //using JDBC template
    private final JdbcTemplate jdbcTemplate;

    //constructor with spring DI
    @Autowired
    public GameDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //function to add game
    @Override
    @Transactional
    public Game addGame(Game game) {

        //creating sql query
        final String sql = "INSERT INTO game(timeStarted, generatedAnswer) VALUES(?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        //calling the sql query with updated variables
        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setTimestamp(1, game.getTime());
            statement.setString(2, game.getGeneratedAnswer());
            return statement;

        }, keyHolder);

        game.setId(keyHolder.getKey().intValue());

        return game;
    }

    //function to get all games
    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT * FROM game;";
        return jdbcTemplate.query(sql, new gameMapper());
    }

    //function to get a specific game by id
    @Override
    public Game getGameById(int id) {

        final String sql = "SELECT * "
                + "FROM game WHERE Id = ?;";

        return jdbcTemplate.queryForObject(sql, new gameMapper(), id);
    }

    //function to update the game
    @Override
    @Transactional
    public boolean updateGame(Game game) {

        final String sql = "UPDATE game SET "
                + "timeStarted = ?, "
                + "generatedAnswer = ?, "
                + "gameFinished = ? "
                + "WHERE Id = ?;";

        return jdbcTemplate.update(sql,
                game.getTime(),
                game.getGeneratedAnswer(),
                game.isGameFinished(),
                game.getId()) > 0;
    }


    //deleting function
    @Override
    @Transactional
    public void deleteGameById(int id)
    {
        final String sql = "DELETE from game WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }


    //implementing row mapper as inner class
    private static final class gameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setId(rs.getInt("Id"));
            game.setTime(rs.getTimestamp("timeStarted"));
            game.setGeneratedAnswer(rs.getString("generatedAnswer"));
            game.setGameFinished(rs.getBoolean("gameFinished"));;
            return game;
        }
    }
}
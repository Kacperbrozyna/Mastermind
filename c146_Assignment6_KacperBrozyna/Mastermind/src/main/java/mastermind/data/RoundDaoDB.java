package mastermind.data;

import mastermind.models.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Repository
public class RoundDaoDB implements RoundDao {

    //using JDBC template
    private final JdbcTemplate jdbcTemplate;

    //constructor with DI
    @Autowired
    public RoundDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //overridden function
    @Override
    @Transactional
    public Round addRound(Round round) {

        //creating sql query
        final String sql = "INSERT INTO gameRound(timeOfGuess, userGuess, gameId, noOfExact, noOfPartial) VALUES(?,?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        //calling the query with dynamic variables
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setTimestamp(1, round.getTime());
            statement.setString(2, round.getUserGuess());
            statement.setInt(3, round.getGameID());
            statement.setInt(4,round.getExact());
            statement.setInt(5,round.getPartial());
            return statement;

        }, keyHolder);

        //returning the round that was created
        return round;
    }

    //getting all the rounds for a specific game id
    @Override
    public List<Round> getRoundsForGame(int id)
    {
        //creating sql query
        final String sql = "select * from gameRound\n" +
                "where gameId = ?;";

        //calling the query with update variables
        return jdbcTemplate.query(sql, new roundMapper(), id);
    }


    //function to delete round by Id
    @Override
    @Transactional
    public void deleteRoundById(int id)
    {
        final String sql = "DELETE from gameRound WHERE roundId = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Round> getAllRounds()
    {
        final String sql = "SELECT * from gameRound";
        return jdbcTemplate.query(sql, new roundMapper());
    }


    //implementing row mapper as inner class
    private static final class roundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundID(rs.getInt("roundId"));
            round.setTime(rs.getTimestamp("timeOfGuess"));
            round.setUserGuess(rs.getString("userGuess"));
            round.setExact(rs.getInt("noOfExact"));
            round.setPartial(rs.getInt("noOfPartial"));
            round.setGameID(rs.getInt("gameId"));
            return round;
        }
    }
}

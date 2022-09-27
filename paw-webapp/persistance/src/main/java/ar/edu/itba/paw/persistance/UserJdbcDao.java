package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserJdbcDao implements UserDao{

    private static final RowMapper<User> USER_ROW_MAPPER = (resultSet, rowNum) ->
            new User(resultSet.getLong("userid"),
                    resultSet.getString("email"),
                    resultSet.getString("name"),
                    resultSet.getString("password"),
                    resultSet.getLong("reputation"),
                    resultSet.getBytes("image"),
                    resultSet.getString("role"));

    private static final RowMapper<Content> CONTENT_ROW_MAPPER = (resultSet, rowNum) ->
            new Content(resultSet.getLong("contentid"),
                    resultSet.getString("contentName"),
                    resultSet.getBytes("contentImage"),
                    resultSet.getString("description"),
                    resultSet.getString("released"),
                    resultSet.getString("genre"),
                    resultSet.getString("creator"),
                    resultSet.getString("duration"),
                    resultSet.getString("type"),
                    resultSet.getInt("rating")/(resultSet.getInt("reviewsAmount") ==0 ? 1 : resultSet.getInt("reviewsAmount"))
                    ,resultSet.getInt("reviewsAmount"));

    private static final RowMapper<Long> LONG_ROW_MAPPER = (resultSet, rowNum) ->
            resultSet.getLong("contentid");


    private final JdbcTemplate template;

    @Autowired
    public UserJdbcDao(final DataSource ds){
        this.template = new JdbcTemplate(ds);
    }

    @Override
    public Optional<User> create(final String userEmail, String userName, String password, Long rating) {
        try {
            template.update("INSERT INTO userdata(name, email, password, reputation,role) VALUES(?,?,?,?,'user')", userName, userEmail, password, rating);
        }catch(Exception e) {
        }
        //Chequeo si el {usuario,email} existe
        return  template.query("SELECT * FROM userdata WHERE email = ? AND name = ?", new Object[]{userEmail,userName}, USER_ROW_MAPPER).stream().findFirst();

    }

    @Override
    public Optional<User> findByEmail(final String email) {
        return template.query("SELECT * FROM userdata WHERE email = ?", new Object[]{ email }, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> findById(final long userId) {
        return template.query("SELECT * FROM userdata WHERE userid = ?", new Object[]{ userId }, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return template.query("SELECT * FROM userdata WHERE name = ?", new Object[]{ userName }, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public void setPassword(String password, User user){
        template.update("UPDATE userdata SET password = ? WHERE userid = ?", password, user.getId());
    }

    @Override
    public void setProfilePicture(byte[] profilePicture, User user) {
        template.update("UPDATE userdata SET image = ? WHERE  userid = ?", profilePicture, user.getId());
    }

    @Override
    public void addToWatchList(User user, Long contentId) {
        template.update("INSERT INTO userwatchlist(userid, contentid) VALUES(?,?)", user.getId(), contentId);
    }

    @Override
    public void deleteFromWatchList(User user, Long contentId) {
        template.update("DELETE FROM userwatchlist WHERE userid = ? and contentid = ?", user.getId(), contentId);
    }

    @Override
    public List<Content> getWatchList(User user) {
        return template.query("SELECT (content.id) AS contentId, (content.name) AS contentName, (content.image) AS contentImage, description, released, genre, creator, duration, type, rating, reviewsamount FROM (content JOIN userwatchlist ON content.id = userwatchlist.contentId) JOIN userdata ON userdata.userid = userwatchlist.userid WHERE userwatchlist.userid = ? ORDER BY userwatchlist.id desc", new Object[]{ user.getId() }, CONTENT_ROW_MAPPER);
    }

    @Override
    public Optional<Long> searchContentInWatchList(User user, Long contentId) {
        Optional<Long> contentIdDB = template.query("SELECT * FROM userwatchlist WHERE userid = ? AND contentid = ?", new Object[]{user.getId(), contentId} , LONG_ROW_MAPPER).stream().findFirst();
        return contentIdDB.isPresent() ? Optional.of(contentIdDB.get()) : Optional.of((long) -1);
    }

    @Override
    public List<Long> getUserWatchListContent(User user) {
        return template.query("SELECT contentid FROM userwatchlist WHERE userid = ?", new Object[]{user.getId()} , LONG_ROW_MAPPER);
    }



    @Override
    public void promoteUser(Long userId){
        template.update("UPDATE userdata SET role = 'admin' WHERE userid = ?",new Object[]{userId});
    }

}

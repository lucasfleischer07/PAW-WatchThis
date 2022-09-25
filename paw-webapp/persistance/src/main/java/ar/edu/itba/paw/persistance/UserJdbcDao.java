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
                    resultSet.getBytes("image"));


    private final JdbcTemplate template;

    @Autowired
    public UserJdbcDao(final DataSource ds){
        this.template = new JdbcTemplate(ds);
    }

    @Override
    public Optional<Long> create(final String userEmail, String userName, String password, Long rating) {
        try {
            template.update("INSERT INTO userdata(name, email, password, reputation) VALUES(?,?,?,?)", userName, userEmail, password, rating);
        }catch(Exception e) {
        }
        //Chequeo si el {usuario,email} existe
        Optional<User> userFound = template.query("SELECT * FROM userdata WHERE email = ? AND name = ?", new Object[]{userEmail,userName}, USER_ROW_MAPPER).stream().findFirst();
        return userFound.isPresent() ? Optional.of(userFound.get().getId()) : Optional.of((long) -1);
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
    public List<Content> getWatchList(User user) {
//      TODO: VER COMO HCAER EL JOIN CON LA DE USERWATCHLIST ASI ME TRAIGO EL CONTENIDO Y TODO ESO
//        return template.query("SELECT * FROM content WHERE name = ?", new Object[]{ user.getId() }, USER_ROW_MAPPER);
        return null;
    }


}

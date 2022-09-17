package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.User;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserJdbcDao implements UserDao{

    private static final RowMapper<User> USER_ROW_MAPPER = (resultSet, rowNum) ->
            new User(resultSet.getLong("userid"),
                    resultSet.getString("email"),
                    resultSet.getString("name"),
                    resultSet.getString("password"),
                    resultSet.getLong("reputation"));

    private final JdbcTemplate template;
//    private final SimpleJdbcInsert insert;

    @Autowired
    public UserJdbcDao(final DataSource ds){
        this.template = new JdbcTemplate(ds);
//        this.insert = new SimpleJdbcInsert(ds)
//                .withTableName("users")
//                .usingGeneratedKeyColumns("id");

        //Hacer esto NO esta bueno, ya va a mostrar una mejor forma
//        template.execute("CREATE TABLE IF NOT EXISTS users ("
//                + "id SERIAL PRIMARY KEY,"
//                + "email VARCHAR(255) UNIQUE NOT NULL,"
//                + "password VARCHAR(255) NOT NULL"
//                + ")");
    }

    @Override
    public Optional<Long> create(final String userEmail, String userName, String password, Long rating) {
        try {
            template.update("INSERT INTO userdata(name, email, password, reputation) VALUES(?,?,?,?)", userName, userEmail, password, rating);
        }catch(Exception e) {
            //Por ahora vacio, en el futuro un cartel
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
    public void setPassword(String password, User user){
        template.update("UPDATE userdata SET password = ? WHERE userid = ?",new Object[]{password, user.getId()});
    }

}

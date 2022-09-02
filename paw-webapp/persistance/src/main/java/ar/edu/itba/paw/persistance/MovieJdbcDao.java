package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MovieJdbcDao implements MovieDao {

    private static final RowMapper<Movie> MOVIE_ROW_MAPPER = (resultSet, rowNum) ->
            new Movie(resultSet.getLong("movieid"),
                    resultSet.getString("name"),
                    resultSet.getString("image"),
                    resultSet.getString("description"),
                    resultSet.getString("released"),
                    resultSet.getString("genre"),
                    resultSet.getString("creator"),
                    resultSet.getString("duration"));

    private final JdbcTemplate template;
//    private final SimpleJdbcInsert insert;

    @Autowired
    public MovieJdbcDao(final DataSource ds){
        this.template = new JdbcTemplate(ds);
//        this.insert = new SimpleJdbcInsert(ds)
//                .withTableName("users")
//                .usingGeneratedKeyColumns("id");
//
//        //Hacer esto NO esta bueno, ya va a mostrar una mejor forma
//        template.execute("CREATE TABLE IF NOT EXISTS users ("
//                + "id SERIAL PRIMARY KEY,"
//                + "email VARCHAR(255) UNIQUE NOT NULL,"
//                + "password VARCHAR(255) NOT NULL"
//                + ")");
    }

    @Override
    public List<Movie> getAllMovies() {
        return template.query("SELECT * FROM movies", MOVIE_ROW_MAPPER);
    }

    @Override
    public Optional<Movie> findByName(String name) {
        return template.query("SELECT * FROM movies WHERE name = ?",
                new Object[]{ name }, MOVIE_ROW_MAPPER
        ).stream().findFirst();
    }

    @Override
    public Optional<Movie> findByGenre(String genre) {
        // TODO: Ver como hacer para que, dentro de los genereos, que me agarre 1 de todos los que tiene
        return template.query("SELECT * FROM movies WHERE genre = ?",
                new Object[]{ genre }, MOVIE_ROW_MAPPER
        ).stream().findFirst();
    }

    @Override
    public Optional<Movie> findByDuration(String duration) {
        // TODO: Aca, cuando definamos para hacer la consulta, tiene que ser en el sigueinte formato: 2 horas 22 minutos, es decir, numero horas numero minuto
        return template.query("SELECT * FROM movies WHERE duration = ?",
                new Object[]{ duration }, MOVIE_ROW_MAPPER
        ).stream().findFirst();
    }

    @Override
    public Optional<Movie> findById(long id) {
        // TODO: Aca, cuando definamos para hacer la consulta, tiene que ser en el sigueinte formato: 2 horas 22 minutos, es decir, numero horas numero minuto
        return template.query("SELECT * FROM movies WHERE movieId = ?",
                new Object[]{ id }, MOVIE_ROW_MAPPER
        ).stream().findFirst();
    }
}

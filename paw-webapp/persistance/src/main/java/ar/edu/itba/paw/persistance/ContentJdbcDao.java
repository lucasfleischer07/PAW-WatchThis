package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ContentJdbcDao implements ContentDao {

    private static final RowMapper<Content> CONTENT_ROW_MAPPER = (resultSet, rowNum) ->
            new Content(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("image"),
                    resultSet.getString("description"),
                    resultSet.getString("released"),
                    resultSet.getString("genre"),
                    resultSet.getString("creator"),
                    resultSet.getString("duration"),
                    resultSet.getString("type"));


    private final JdbcTemplate template;

    @Autowired
    public ContentJdbcDao(final DataSource ds){
        this.template = new JdbcTemplate(ds);
    }

    @Override
    public List<Content> getAllContent(String type) {
        if(Objects.equals(type, "movie") || Objects.equals(type, "serie")) {
            return template.query("SELECT * FROM content WHERE type = ?", new Object[]{type}, CONTENT_ROW_MAPPER);
        } else {
            return template.query("SELECT * FROM content", CONTENT_ROW_MAPPER);
        }
    }

    @Override
    public Optional<Content> findByName(String name) {
        return template.query("SELECT * FROM content WHERE name = ?",
                new Object[]{ name }, CONTENT_ROW_MAPPER
        ).stream().findFirst();
    }

    @Override
    public List<Content> findByGenre(String type, String genre) {
        // TODO: Ver como hacer para que, dentro de los genereos, que me agarre 1 de todos los que tiene
        return template.query("SELECT * FROM content WHERE genre LIKE '%'||?||'%' AND type = ?", new Object[]{ genre, type }, CONTENT_ROW_MAPPER);
    }

    @Override
    public List<Content> findByDuration(String type, int durationFrom, int durationTo) {
        // TODO: Aca, cuando definamos para hacer la consulta, tiene que ser en el sigueinte formato: 2 horas 22 minutos, es decir, numero horas numero minuto
        return template.query("SELECT * FROM content WHERE durationnum > ? AND durationnum <= ? AND type = ?", new Object[]{ durationFrom, durationTo, type }, CONTENT_ROW_MAPPER);
    }

    @Override
    public List<Content> findByDurationAndGenre(String type, String genre,int durationFrom, int durationTo){
        return template.query("SELECT * FROM content WHERE durationnum > ? AND durationnum <= ? AND genre LIKE '%'||?||'%' AND type = ?",new Object[]{ durationFrom, durationTo, genre, type},CONTENT_ROW_MAPPER);
    }

    @Override
    public Optional<Content> findById(long id) {
        // TODO: Aca, cuando d"SELECT * FROM content WHERE durationnum > ? and durationnum <= ?efinamos para hacer la consulta, tiene que ser en el sigueinte formato: 2 horas 22 minutos, es decir, numero horas numero minuto
        return template.query("SELECT * FROM content WHERE id = ?",
                new Object[]{ id }, CONTENT_ROW_MAPPER
        ).stream().findFirst();
    }

    @Override
    public List<Content> getSearchedContent(String query) {
        List<Content> content =  template.query("SELECT * FROM content WHERE LOWER(name) LIKE ? ",
                new Object[]{"%" + query.toLowerCase() + "%"},CONTENT_ROW_MAPPER);
        return content;
    }


    @Override
    public List<Content> ordenByAsc(String parameter) {
        return template.query("SELECT * FROM content order by ? asc", new Object[]{ parameter }, CONTENT_ROW_MAPPER);
    }

    @Override
    public List<Content> ordenByDesc(String parameter) {
        return template.query("SELECT * FROM content order by ? desc", new Object[]{ parameter }, CONTENT_ROW_MAPPER);

    }



    @Override
    public List<Content> findByType(String type) {
        return template.query("SELECT * FROM content where type = ?",new Object[]{ type }, CONTENT_ROW_MAPPER);
    }

}

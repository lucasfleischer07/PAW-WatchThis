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
    public List<Content> getAllContent(String type, String sort) {
        if (Objects.equals(type, "movie") || Objects.equals(type, "serie")) {
            if (Objects.equals(sort, "ANY")) {
                return template.query("SELECT * FROM content WHERE type = ?", new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query("SELECT * FROM content WHERE type = ? ORDER BY released desc", new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query("SELECT * FROM content WHERE type = ? ORDER BY released asc", new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query("SELECT * FROM content WHERE type = ? ORDER BY rating desc", new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query("SELECT * FROM content WHERE type = ? ORDER BY name asc", new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query("SELECT * FROM content WHERE type = ? ORDER BY name desc", new Object[]{type}, CONTENT_ROW_MAPPER);
            }
        } else {
            if (Objects.equals(sort, "ANY")) {
                return template.query("SELECT * FROM content", CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query("SELECT * FROM content ORDER BY released desc", CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query("SELECT * FROM content ORDER BY released asc", CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query("SELECT * FROM content ORDER BY rating desc", CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query("SELECT * FROM content ORDER BY name asc", CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query("SELECT * FROM content ORDER BY name desc", CONTENT_ROW_MAPPER);
            }
        }
        return null;
    }

    @Override
    public Optional<Content> findByName(String name) {
        return template.query("SELECT * FROM content WHERE name = ?",
                new Object[]{ name }, CONTENT_ROW_MAPPER
        ).stream().findFirst();
    }

    @Override
    public List<Content> findByGenre(String type, String genre, String sort) {
        // TODO: Ver como hacer para que, dentro de los genereos, que me agarre 1 de todos los que tiene
        if(Objects.equals(sort, "ANY")) {
            return template.query("SELECT * FROM content WHERE genre LIKE '%'||?||'%' AND type = ?", new Object[]{ genre, type }, CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "Last-released")){
            return template.query("SELECT * FROM content WHERE genre LIKE '%'||?||'%' AND type = ? ORDER BY released desc", new Object[]{ genre, type }, CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "Older-released")){
            return template.query("SELECT * FROM content WHERE genre LIKE '%'||?||'%' AND type = ? ORDER BY released asc", new Object[]{ genre, type }, CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "Best-ratings")){
            return template.query("SELECT * FROM content WHERE genre LIKE '%'||?||'%' AND type = ? ORDER BY rating desc", new Object[]{ genre, type }, CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "A-Z")){
            return template.query("SELECT * FROM content WHERE genre LIKE '%'||?||'%' AND type = ? ORDER BY name asc", new Object[]{ genre, type }, CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "Z-A")){
            return template.query("SELECT * FROM content WHERE genre LIKE '%'||?||'%' AND type = ? ORDER BY name desc", new Object[]{ genre, type }, CONTENT_ROW_MAPPER);
        }
        return null;
    }

    @Override
    public List<Content> findByDuration(String type, int durationFrom, int durationTo, String sort) {
        if(Objects.equals(sort, "ANY")) {
            return template.query("SELECT * FROM content WHERE durationnum > ? AND durationnum <= ? AND type = ?", new Object[]{ durationFrom, durationTo, type }, CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "Last-released")){
            return template.query("SELECT * FROM content WHERE durationnum > ? AND durationnum <= ? AND type = ? ORDER BY released desc", new Object[]{ durationFrom, durationTo, type }, CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "Older-released")){
            return template.query("SELECT * FROM content WHERE durationnum > ? AND durationnum <= ? AND type = ? ORDER BY released asc", new Object[]{ durationFrom, durationTo, type }, CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "Best-ratings")){
            return template.query("SELECT * FROM content WHERE durationnum > ? AND durationnum <= ? AND type = ? ORDER BY rating desc", new Object[]{ durationFrom, durationTo, type }, CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "A-Z")){
            return template.query("SELECT * FROM content WHERE durationnum > ? AND durationnum <= ? AND type = ? ORDER BY name asc", new Object[]{ durationFrom, durationTo, type }, CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "Z-A")){
            return template.query("SELECT * FROM content WHERE durationnum > ? AND durationnum <= ? AND type = ? ORDER BY name desc", new Object[]{ durationFrom, durationTo, type }, CONTENT_ROW_MAPPER);
        }
        return null;
    }

    @Override
    public List<Content> findByDurationAndGenre(String type, String genre,int durationFrom, int durationTo, String sort){
        if(Objects.equals(sort, "ANY")) {
            return template.query("SELECT * FROM content WHERE durationnum > ? AND durationnum <= ? AND genre LIKE '%'||?||'%' AND type = ?",new Object[]{ durationFrom, durationTo, genre, type},CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "Last-released")){
            return template.query("SELECT * FROM content WHERE durationnum > ? AND durationnum <= ? AND genre LIKE '%'||?||'%' AND type = ? ORDER BY released desc",new Object[]{ durationFrom, durationTo, genre, type},CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "Older-released")){
            return template.query("SELECT * FROM content WHERE durationnum > ? AND durationnum <= ? AND genre LIKE '%'||?||'%' AND type = ? ORDER BY released asc",new Object[]{ durationFrom, durationTo, genre, type},CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "Best-ratings")){
            return template.query("SELECT * FROM content WHERE durationnum > ? AND durationnum <= ? AND genre LIKE '%'||?||'%' AND type = ? ORDER BY rating desc",new Object[]{ durationFrom, durationTo, genre, type},CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "A-Z")){
            return template.query("SELECT * FROM content WHERE durationnum > ? AND durationnum <= ? AND genre LIKE '%'||?||'%' AND type = ? ORDER BY name asc",new Object[]{ durationFrom, durationTo, genre, type},CONTENT_ROW_MAPPER);
        } else if(Objects.equals(sort, "Z-A")){
            return template.query("SELECT * FROM content WHERE durationnum > ? AND durationnum <= ? AND genre LIKE '%'||?||'%' AND type = ? ORDER BY name desc",new Object[]{ durationFrom, durationTo, genre, type},CONTENT_ROW_MAPPER);
        }
        return null;
    }

    @Override
    public Optional<Content> findById(long id) {
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
    public List<Content> findByType(String type) {
        return template.query("SELECT * FROM content where type = ?",new Object[]{ type }, CONTENT_ROW_MAPPER);
    }

}

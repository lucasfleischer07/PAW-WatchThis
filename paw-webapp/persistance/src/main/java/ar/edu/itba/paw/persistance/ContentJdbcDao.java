package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.mail.Multipart;
import javax.sql.DataSource;
import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ContentJdbcDao implements ContentDao {

    private static final RowMapper<Content> CONTENT_ROW_MAPPER = (resultSet, rowNum) ->
            new Content(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getBytes("image"),
                    resultSet.getString("description"),
                    resultSet.getString("released"),
                    resultSet.getString("genre"),
                    resultSet.getString("creator"),
                    resultSet.getString("duration"),
                    resultSet.getString("type"),
                    resultSet.getInt("rating"),
                    resultSet.getInt("reviewsAmount")
            );

    private static final RowMapper<String> ENGLISH_QUOTE_ROW_MAPPER = (resultSet, rowNum) -> resultSet.getString("english");
    private static final RowMapper<String> SPANISH_QUOTE_ROW_MAPPER = (resultSet, rowNum) -> resultSet.getString("spanish");

    private final JdbcTemplate template;

    @Autowired
    public ContentJdbcDao(final DataSource ds){
        this.template = new JdbcTemplate(ds);
    }

    private static final String TYPE_BASE_QUERY = "SELECT content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) AS rating,count(reviewid) AS reviewsAmount FROM content LEFT JOIN (select * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid  WHERE content.type = ? GROUP BY content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";
    private static final String BASE_QUERY = "SELECT content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) AS rating,count(reviewid) AS reviewsAmount FROM content LEFT JOIN (SELECT * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid GROUP BY content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";


    @Override
    public List<Content> getAllContent(String type, String sort) {
        if (Objects.equals(type, "movie") || Objects.equals(type, "serie")) {
            if (Objects.equals(sort, "ANY")) {
                return template.query(TYPE_BASE_QUERY, new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(TYPE_BASE_QUERY +"ORDER BY released DESC", new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(TYPE_BASE_QUERY + "ORDER BY released ASC", new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(TYPE_BASE_QUERY +"ORDER BY rating DESC NULLS LAST", new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(TYPE_BASE_QUERY +"BY content.name ASC", new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(TYPE_BASE_QUERY +"ORDER BY content.name DESC", new Object[]{type}, CONTENT_ROW_MAPPER);
            }
        } else {
            if (Objects.equals(sort, "ANY")) {
                return template.query(BASE_QUERY, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(BASE_QUERY +"ORDER BY released DESC", CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(BASE_QUERY +"ORDER BY released ASC", CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(BASE_QUERY + "ORDER BY rating DESC NULLS LAST", CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(BASE_QUERY + "ORDER BY content.name ASC", CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(BASE_QUERY + "ORDER BY content.name DESC", CONTENT_ROW_MAPPER);
            }
        }
        return null;
    }
    private static final String NAME_BASE_QUERY = "SELECT content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) AS rating,count(reviewid) AS reviewsAmount FROM content LEFT JOIN (select * from review as r2 where r2.rating<>0) as review on content.id = review.contentid where content.name = ? group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";

    @Override
    public Optional<Content> findByName(String name) {
        return template.query(NAME_BASE_QUERY , new Object[]{ name }, CONTENT_ROW_MAPPER).stream().findFirst();
    }
    private static final String GENRE_BASE_QUERY = "SELECT content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) AS rating,count(reviewid) AS reviewsAmount FROM content LEFT JOIN (SELECT * from review as r2 where r2.rating<>0) as review on content.id = review.contentid WHERE genre LIKE '%'||?||'%' AND content.type = ? group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";
    private static final String GENRE_BASE_QUERY_ANY = "SELECT content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) AS rating,count(reviewid) AS reviewsAmount FROM content LEFT JOIN (select * from review as r2 where r2.rating<>0) as review on content.id = review.contentid WHERE genre LIKE '%'||?||'%' group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";

    @Override
    public List<Content> findByGenre(String type, String genre, String sort) {
        if(!Objects.equals(type, "all")) {
            if (Objects.equals(sort, "ANY")) {
                return template.query(GENRE_BASE_QUERY, new Object[]{genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(GENRE_BASE_QUERY+ "ORDER BY released DESC", new Object[]{genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(GENRE_BASE_QUERY + "ORDER BY released ASC", new Object[]{genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(GENRE_BASE_QUERY + "ORDER BY rating DESC NULLS LAST", new Object[]{genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(GENRE_BASE_QUERY + "ORDER BY content.name ASC", new Object[]{genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(GENRE_BASE_QUERY + "ORDER BY content.name DESC", new Object[]{genre, type}, CONTENT_ROW_MAPPER);
            }
        } else {
            if (Objects.equals(sort, "ANY")) {
                return template.query(GENRE_BASE_QUERY_ANY, new Object[]{genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(GENRE_BASE_QUERY_ANY + "ORDER BY released DESC", new Object[]{genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(GENRE_BASE_QUERY_ANY + "ORDER BY released ASC", new Object[]{genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(GENRE_BASE_QUERY_ANY + "ORDER BY rating DESC NULLS LAST", new Object[]{genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(GENRE_BASE_QUERY_ANY + "ORDER BY content.name ASC", new Object[]{genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(GENRE_BASE_QUERY_ANY + "ORDER BY content.name DESC", new Object[]{genre}, CONTENT_ROW_MAPPER);
            }
        }
        return null;
    }
    private static final String DURATION_BASE_QUERY = "SELECT content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) AS rating,count(reviewid) AS reviewsAmount FROM content LEFT JOIN (SELECT * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid WHERE durationnum > ? AND durationnum <= ? AND content.type = ? GROUP BY content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";
    private static final String DURATION_BASE_QUERY_ANY = "SELECT content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) AS rating,count(reviewid) AS reviewsAmount FROM content LEFT JOIN (SELECT * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid WHERE durationnum > ? AND durationnum <= ? GROUP BY content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";

    @Override
    public List<Content> findByDuration(String type, int durationFrom, int durationTo, String sort) {
        if(!Objects.equals(type, "all")) {
            if (Objects.equals(sort, "ANY")) {
                return template.query(DURATION_BASE_QUERY, new Object[]{durationFrom, durationTo, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(DURATION_BASE_QUERY + "ORDER BY released DESC", new Object[]{durationFrom, durationTo, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(DURATION_BASE_QUERY + "ORDER BY released ASC", new Object[]{durationFrom, durationTo, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(DURATION_BASE_QUERY + "ORDER BY rating DESC NULLS LAST", new Object[]{durationFrom, durationTo, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(DURATION_BASE_QUERY + "ORDER BY content.name ASC", new Object[]{durationFrom, durationTo, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(DURATION_BASE_QUERY + "ORDER BY content.name DESC", new Object[]{durationFrom, durationTo, type}, CONTENT_ROW_MAPPER);
            }
        } else {
            if (Objects.equals(sort, "ANY")) {
                return template.query(DURATION_BASE_QUERY_ANY, new Object[]{durationFrom, durationTo}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(DURATION_BASE_QUERY_ANY + "ORDER BY released DESC", new Object[]{durationFrom, durationTo}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(DURATION_BASE_QUERY_ANY + "ORDER BY released ASC", new Object[]{durationFrom, durationTo}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(DURATION_BASE_QUERY_ANY + "ORDER BY rating DESC NULLS LAST", new Object[]{durationFrom, durationTo}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(DURATION_BASE_QUERY_ANY + "ORDER BY content.name ASC", new Object[]{durationFrom, durationTo}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(DURATION_BASE_QUERY_ANY + "ORDER BY content.name DESC", new Object[]{durationFrom, durationTo}, CONTENT_ROW_MAPPER);
            }
        }
        return null;
    }

    private static final String DURATION_AND_GENRE_BASE_QUERY = "SELECT content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) AS rating,count(reviewid) AS reviewsAmount FROM content LEFT JOIN (SELECT * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid WHERE durationnum > ? AND durationnum <= ? AND genre LIKE '%'||?||'%' AND content.type = ? GROUP BY content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";
    private static final String DURATION_AND_GENRE_BASE_QUERY_ANY = "SELECT content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) AS rating,count(reviewid) AS reviewsAmount FROM content LEFT JOIN (SELECT * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid WHERE durationnum > ? AND durationnum <= ? AND genre LIKE '%'||?||'%' GROUP BY content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";


    @Override
    public List<Content> findByDurationAndGenre(String type, String genre,int durationFrom, int durationTo, String sort){
        if(!Objects.equals(type, "all")) {
            if (Objects.equals(sort, "ANY")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY, new Object[]{durationFrom, durationTo, genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY + "ORDER BY released DESC", new Object[]{durationFrom, durationTo, genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY + "ORDER BY released ASC", new Object[]{durationFrom, durationTo, genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY + "ORDER BY rating DESC NULLS LAST", new Object[]{durationFrom, durationTo, genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY + "ORDER BY content.name ASC", new Object[]{durationFrom, durationTo, genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY + "ORDER BY content.name DESC", new Object[]{durationFrom, durationTo, genre, type}, CONTENT_ROW_MAPPER);
            }
        } else {
            if (Objects.equals(sort, "ANY")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY_ANY, new Object[]{durationFrom, durationTo, genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY_ANY + "ORDER BY released DESC", new Object[]{durationFrom, durationTo, genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY_ANY + "ORDER BY released ASC", new Object[]{durationFrom, durationTo, genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY_ANY + "ORDER BY rating DESD NULLS LAST", new Object[]{durationFrom, durationTo, genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY_ANY + "ORDER BY content.name ASC", new Object[]{durationFrom, durationTo, genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY_ANY + "ORDER BY content.name DESC", new Object[]{durationFrom, durationTo, genre}, CONTENT_ROW_MAPPER);
            }
        }
        return null;
    }

    private static final String ID_BASE_QUERY = "SELECT content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) AS rating,count(reviewid) AS reviewsAmount FROM content LEFT JOIN (SELECT * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid  WHERE id = ? GROUP BY content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";


    @Override
    public Optional<Content> findById(long id) {
        return template.query(ID_BASE_QUERY, new Object[]{ id }, CONTENT_ROW_MAPPER).stream().findFirst();
    }

    private static final String SEARCHED_BASE_QUERY = "SELECT content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) AS rating,count(reviewid) AS reviewsAmount FROM content LEFT JOIN (SELECT * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid  WHERE (LOWER(content.name) LIKE ? OR LOWER(creator) LIKE ?) GROUP BY content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";


    @Override
    public List<Content> getSearchedContent(String query) {
        List<Content> content =  template.query(SEARCHED_BASE_QUERY,
                new Object[]{"%" + query.toLowerCase() + "%", "%" + query.toLowerCase() + "%"},CONTENT_ROW_MAPPER);
        return content;
    }



    @Override
    public List<Content> getSearchedContentRandom(String query) {
        List<Content> content =  template.query( SEARCHED_BASE_QUERY + "ORDER BY RANDOM()", new Object[]{"%" + query.toLowerCase() + "%"},CONTENT_ROW_MAPPER);
        return content;
    }

    @Override
    public List<Content> findByType(String type) {
        return template.query(TYPE_BASE_QUERY,new Object[]{ type }, CONTENT_ROW_MAPPER);
    }


    private static final String BEST_RATED = "SELECT content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) AS rating,count(reviewid) AS reviewsAmount FROM content LEFT JOIN (SELECT * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid GROUP BY content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum HAVING sum(review.rating)/count(*) > 3 ORDER BY sum(review.rating)/count(*) DESC LIMIT 20 ";

    @Override
    public List<Content> getBestRated() {
        return template.query(BEST_RATED, CONTENT_ROW_MAPPER);
    }

    @Override
    public List<Content> getUserRecommended(User user) {
        return template.query("SELECT content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) AS rating,count(reviewid) AS reviewsAmount FROM content LEFT JOIN (SELECT * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid WHERE content.id IN (SELECT DISTINCT t2.contentid FROM userwatchlist t1 INNER JOIN userwatchlist t2 ON t1.userid = ? AND t2.userid IN (SELECT userdata.userid FROM userwatchlist t1  INNER JOIN userwatchlist t2 ON (t2.contentid = t1.contentid) INNER JOIN userdata ON userdata.userid = t2.userid INNER JOIN content ON content.id = t1.contentid WHERE t1.userid = ? AND t2.userid <> ? GROUP BY userdata.userid) AND t2.contentid NOT IN (SELECT contentid FROM userwatchlist WHERE userid = ?)) GROUP BY content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum LIMIT 20", new Object[]{user.getId(), user.getId(), user.getId(), user.getId()}, CONTENT_ROW_MAPPER);
    }

    @Override
    public List<Content> getMostUserSaved() {
        return template.query("SELECT (content.id) AS id, MAX(content.name) AS name, (content.image) AS image, MAX(content.description) AS description, MAX(released) AS released, MAX(genre) AS genre, MAX(creator) AS creator, MAX(duration) AS duration, MAX(content.type) AS type, SUM(review.rating)/count(*) AS rating, COUNT(reviewid) AS reviewsAmount FROM (content JOIN userwatchlist ON content.id = userwatchlist.contentId) LEFT JOIN (SELECT * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid GROUP BY content.id, content.image ORDER BY COUNT(*) DESC LIMIT 20", CONTENT_ROW_MAPPER);
    }

    @Override
    public List<Content> getLastAdded() {
        return template.query(BASE_QUERY + "ORDER BY id DESC LIMIT 20", CONTENT_ROW_MAPPER);
    }

    @Override
    public void contentCreate(String name, String description, String releaseDate, String genre, String creator, Integer duration,String durationString, String type, byte[] contentImage){
        template.update(
                "INSERT INTO content(name,image,description,released,genre,creator,duration,durationNum,type) VALUES(?,?,?,?,?,?,?,?,?)",name,contentImage,description,releaseDate,genre,creator,durationString,duration,type
        );
    }

    @Override
    public Optional<String> getContentQuote(String language) {
        if(Objects.equals(language, "Spanish")) {
            return template.query("SELECT spanish FROM quotes ORDER BY RANDOM() LIMIT 1",SPANISH_QUOTE_ROW_MAPPER).stream().findFirst();
        } else {
            return template.query("SELECT english FROM quotes ORDER BY RANDOM() LIMIT 1",ENGLISH_QUOTE_ROW_MAPPER).stream().findFirst();
        }
    }

    @Override
    public void updateContent(Long id,String name, String description, String releaseDate, String genre, String creator, Integer duration,String durationString, String type){
        template.update("UPDATE content SET name = ?,description = ?,released = ?, genre = ?, creator = ?, duration = ?, durationNum = ?, type = ?  WHERE id = ?", new Object[] {name,description,releaseDate,genre,creator,durationString,duration,type,id});
    }

    @Override
    public void updateWithImageContent(Long id,String name, String description, String releaseDate, String genre, String creator, Integer duration,String durationString, String type,byte[] contentImage){
        template.update("UPDATE content SET name = ?,description = ?,released = ?, genre = ?, creator = ?, duration = ?, durationNum = ?, type = ?, image = ? WHERE id = ?", new Object[] {name,description,releaseDate,genre,creator,durationString,duration,type,contentImage,id});
    }

    @Override
    public void deleteContent(Long id){
        template.update("DELETE FROM content WHERE id = ?", new Object[] {id});
    }

}

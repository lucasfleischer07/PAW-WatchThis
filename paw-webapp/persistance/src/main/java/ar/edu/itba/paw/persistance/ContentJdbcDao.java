package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Content;
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
                    resultSet.getInt("reviewsAmount"));

    private static final RowMapper<String> ENGLISH_QUOTE_ROW_MAPPER = (resultSet, rowNum) -> resultSet.getString("english");
    private static final RowMapper<String> SPANISH_QUOTE_ROW_MAPPER = (resultSet, rowNum) -> resultSet.getString("spanish");

    private final JdbcTemplate template;

    @Autowired
    public ContentJdbcDao(final DataSource ds){
        this.template = new JdbcTemplate(ds);
    }

    private static final String TYPE_BASE_QUERY = "select content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) as rating,count(reviewid) as reviewsAmount\n from content left join (select * from review as r2 where r2.rating<>0) as review on content.id = review.contentid\n  where content.type = ? \n group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";
    private static final String BASE_QUERY = "select content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) as rating,count(reviewid) as reviewsAmount\n from content left join (select * from review as r2 where r2.rating<>0) as review on content.id = review.contentid\n group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";


    @Override
    public List<Content> getAllContent(String type, String sort) {
        if (Objects.equals(type, "movie") || Objects.equals(type, "serie")) {
            if (Objects.equals(sort, "ANY")) {
                return template.query(TYPE_BASE_QUERY, new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(TYPE_BASE_QUERY +"ORDER BY released desc", new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(TYPE_BASE_QUERY + "ORDER BY released asc", new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(TYPE_BASE_QUERY +"ORDER BY rating desc", new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(TYPE_BASE_QUERY +"BY content.name asc", new Object[]{type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(TYPE_BASE_QUERY +"ORDER BY content.name desc", new Object[]{type}, CONTENT_ROW_MAPPER);
            }
        } else {
            if (Objects.equals(sort, "ANY")) {
                return template.query(BASE_QUERY, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(BASE_QUERY +"ORDER BY released desc", CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(BASE_QUERY +"ORDER BY released asc", CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(BASE_QUERY + "ORDER BY rating desc", CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(BASE_QUERY + "ORDER BY content.name asc", CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(BASE_QUERY + "ORDER BY content.name desc", CONTENT_ROW_MAPPER);
            }
        }
        return null;
    }
    private static final String NAME_BASE_QUERY = "select content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) as rating,count(reviewid) as reviewsAmount\n from content left join (select * from review as r2 where r2.rating<>0) as review on content.id = review.contentid\n where content.name = ?\n group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";


    @Override
    public Optional<Content> findByName(String name) {
        return template.query(NAME_BASE_QUERY , new Object[]{ name }, CONTENT_ROW_MAPPER).stream().findFirst();
    }
    private static final String GENRE_BASE_QUERY = "select content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) as rating,count(reviewid) as reviewsAmount\n from content left join (select * from review as r2 where r2.rating<>0) as review on content.id = review.contentid\n WHERE genre LIKE '%'||?||'%' AND content.type = ?\n group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";
    private static final String GENRE_BASE_QUERY_ANY = "select content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) as rating,count(reviewid) as reviewsAmount\n from content left join (select * from review as r2 where r2.rating<>0) as review on content.id = review.contentid\n WHERE genre LIKE '%'||?||'%'\n group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";

    @Override
    public List<Content> findByGenre(String type, String genre, String sort) {
        if(!Objects.equals(type, "all")) {
            if (Objects.equals(sort, "ANY")) {
                return template.query(GENRE_BASE_QUERY, new Object[]{genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(GENRE_BASE_QUERY+ "ORDER BY released desc", new Object[]{genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(GENRE_BASE_QUERY + "ORDER BY released asc", new Object[]{genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(GENRE_BASE_QUERY + "ORDER BY rating desc", new Object[]{genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(GENRE_BASE_QUERY + "ORDER BY content.name asc", new Object[]{genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(GENRE_BASE_QUERY + "ORDER BY content.name desc", new Object[]{genre, type}, CONTENT_ROW_MAPPER);
            }
        } else {
            if (Objects.equals(sort, "ANY")) {
                return template.query(GENRE_BASE_QUERY_ANY, new Object[]{genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(GENRE_BASE_QUERY_ANY + "ORDER BY released desc", new Object[]{genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(GENRE_BASE_QUERY_ANY + "ORDER BY released asc", new Object[]{genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(GENRE_BASE_QUERY_ANY + "ORDER BY rating desc", new Object[]{genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(GENRE_BASE_QUERY_ANY + "ORDER BY content.name asc", new Object[]{genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(GENRE_BASE_QUERY_ANY + "ORDER BY content.name desc", new Object[]{genre}, CONTENT_ROW_MAPPER);
            }
        }
        return null;
    }
    private static final String DURATION_BASE_QUERY = "select content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) as rating,count(reviewid) as reviewsAmount\n from content left join (select * from review as r2 where r2.rating<>0) as review on content.id = review.contentid\n WHERE durationnum > ? AND durationnum <= ? AND content.type = ?\n group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";
    private static final String DURATION_BASE_QUERY_ANY = "select content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) as rating,count(reviewid) as reviewsAmount\n from content left join (select * from review as r2 where r2.rating<>0) as review on content.id = review.contentid\n WHERE durationnum > ? AND durationnum <= ?\n group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";

    @Override
    public List<Content> findByDuration(String type, int durationFrom, int durationTo, String sort) {
        if(!Objects.equals(type, "all")) {
            if (Objects.equals(sort, "ANY")) {
                return template.query(DURATION_BASE_QUERY, new Object[]{durationFrom, durationTo, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(DURATION_BASE_QUERY + "ORDER BY released desc", new Object[]{durationFrom, durationTo, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(DURATION_BASE_QUERY + "ORDER BY released asc", new Object[]{durationFrom, durationTo, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(DURATION_BASE_QUERY + "ORDER BY rating desc", new Object[]{durationFrom, durationTo, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(DURATION_BASE_QUERY + "ORDER BY content.name asc", new Object[]{durationFrom, durationTo, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(DURATION_BASE_QUERY + "ORDER BY content.name desc", new Object[]{durationFrom, durationTo, type}, CONTENT_ROW_MAPPER);
            }
        } else {
            if (Objects.equals(sort, "ANY")) {
                return template.query(DURATION_BASE_QUERY_ANY, new Object[]{durationFrom, durationTo}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(DURATION_BASE_QUERY_ANY + "ORDER BY released desc", new Object[]{durationFrom, durationTo}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(DURATION_BASE_QUERY_ANY + "ORDER BY released asc", new Object[]{durationFrom, durationTo}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(DURATION_BASE_QUERY_ANY + "ORDER BY rating desc", new Object[]{durationFrom, durationTo}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(DURATION_BASE_QUERY_ANY + "ORDER BY content.name asc", new Object[]{durationFrom, durationTo}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(DURATION_BASE_QUERY_ANY + "ORDER BY content.name desc", new Object[]{durationFrom, durationTo}, CONTENT_ROW_MAPPER);
            }
        }
        return null;
    }

    private static final String DURATION_AND_GENRE_BASE_QUERY = "select content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) as rating,count(reviewid) as reviewsAmount\n from content left join (select * from review as r2 where r2.rating<>0) as review on content.id = review.contentid\n WHERE durationnum > ? AND durationnum <= ? AND genre LIKE '%'||?||'%' AND content.type = ?\n group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";
    private static final String DURATION_AND_GENRE_BASE_QUERY_ANY = "select content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) as rating,count(reviewid) as reviewsAmount\n from content left join (select * from review as r2 where r2.rating<>0) as review on content.id = review.contentid\n WHERE durationnum > ? AND durationnum <= ? AND genre LIKE '%'||?||'%'\n group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";


    @Override
    public List<Content> findByDurationAndGenre(String type, String genre,int durationFrom, int durationTo, String sort){
        if(!Objects.equals(type, "all")) {
            if (Objects.equals(sort, "ANY")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY, new Object[]{durationFrom, durationTo, genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY + "ORDER BY released desc", new Object[]{durationFrom, durationTo, genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY + "ORDER BY released asc", new Object[]{durationFrom, durationTo, genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY + "ORDER BY rating desc", new Object[]{durationFrom, durationTo, genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY + "ORDER BY content.name asc", new Object[]{durationFrom, durationTo, genre, type}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY + "ORDER BY content.name desc", new Object[]{durationFrom, durationTo, genre, type}, CONTENT_ROW_MAPPER);
            }
        } else {
            if (Objects.equals(sort, "ANY")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY_ANY, new Object[]{durationFrom, durationTo, genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Last-released")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY_ANY + "ORDER BY released desc", new Object[]{durationFrom, durationTo, genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Older-released")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY_ANY + "ORDER BY released asc", new Object[]{durationFrom, durationTo, genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Best-ratings")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY_ANY + "ORDER BY rating desc", new Object[]{durationFrom, durationTo, genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "A-Z")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY_ANY + "ORDER BY content.name asc", new Object[]{durationFrom, durationTo, genre}, CONTENT_ROW_MAPPER);
            } else if (Objects.equals(sort, "Z-A")) {
                return template.query(DURATION_AND_GENRE_BASE_QUERY_ANY + "ORDER BY content.name desc", new Object[]{durationFrom, durationTo, genre}, CONTENT_ROW_MAPPER);
            }
        }
        return null;
    }

    private static final String ID_BASE_QUERY = "select content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) as rating,count(reviewid) as reviewsAmount\n from content left join (select * from review as r2 where r2.rating<>0) as review on content.id = review.contentid\n  WHERE id = ?\n group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";


    @Override
    public Optional<Content> findById(long id) {
        return template.query(ID_BASE_QUERY,
                new Object[]{ id }, CONTENT_ROW_MAPPER
        ).stream().findFirst();
    }

    private static final String SEARCHED_BASE_QUERY = "select content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) as rating,count(reviewid) as reviewsAmount\n from content left join (select * from review as r2 where r2.rating<>0) as review on content.id = review.contentid\n  WHERE (LOWER(content.name) LIKE ? OR LOWER(creator) LIKE ?)\n group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum ";


    @Override
    public List<Content> getSearchedContent(String query) {
        List<Content> content =  template.query(SEARCHED_BASE_QUERY,
                new Object[]{"%" + query.toLowerCase() + "%", "%" + query.toLowerCase() + "%"},CONTENT_ROW_MAPPER);
        return content;
    }



    @Override
    public List<Content> getSearchedContentRandom(String query) {
        List<Content> content =  template.query( SEARCHED_BASE_QUERY + "ORDER BY RANDOM()",
                new Object[]{"%" + query.toLowerCase() + "%"},CONTENT_ROW_MAPPER);
        return content;
    }

    @Override
    public List<Content> findByType(String type) {
        return template.query(TYPE_BASE_QUERY,new Object[]{ type }, CONTENT_ROW_MAPPER);
    }


    private static final String BEST_RATED = "select content.id,content.name,image,content.description,released,genre,creator,duration,content.type, sum(review.rating)/count(*) as rating,count(reviewid) as reviewsAmount\n from content left join (select * from review as r2 where r2.rating<>0) as review on content.id = review.contentid \n group by content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum having sum(review.rating)/count(*) > 3\n  order by sum(review.rating)/count(*) desc ";

    @Override
    public List<Content> getBestRated() {
        return template.query(BEST_RATED, CONTENT_ROW_MAPPER);
    }

    @Override
    public List<Content> getLessDuration(String type) {
        return template.query(TYPE_BASE_QUERY + "ORDER BY durationnum asc LIMIT 20", new Object[] {type}, CONTENT_ROW_MAPPER);
    }

    @Override
    public List<Content> getLastAdded() {
        return template.query(BASE_QUERY + "ORDER BY id desc LIMIT 20", CONTENT_ROW_MAPPER);
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
        template.update(
                "UPDATE content SET name = ?,description = ?,released = ?, genre = ?, creator = ?, duration = ?, durationNum = ?, type = ?  WHERE id = ?", new Object[] {name,description,releaseDate,genre,creator,durationString,duration,type,id}
        );
    }

    @Override
    public void updateWithImageContent(Long id,String name, String description, String releaseDate, String genre, String creator, Integer duration,String durationString, String type,byte[] contentImage){
        template.update(
                "UPDATE content SET name = ?,description = ?,released = ?, genre = ?, creator = ?, duration = ?, durationNum = ?, type = ?, image = ? WHERE id = ?", new Object[] {name,description,releaseDate,genre,creator,durationString,duration,type,contentImage,id}
        );
    }

    @Override
    public void deleteContent(Long id){
        template.update(
                "DELETE FROM content WHERE id = ?", new Object[] {id});
    }

}

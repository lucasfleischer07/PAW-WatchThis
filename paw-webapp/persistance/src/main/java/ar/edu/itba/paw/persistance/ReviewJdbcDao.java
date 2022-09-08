package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.paw.models.Review;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@Repository
public class ReviewJdbcDao implements ReviewDao{

    private static final RowMapper<Review> REVIEW_ROW_MAPPER = (resultSet, rowNum) ->
            new Review(resultSet.getLong("reviewid"),
                    resultSet.getString("type"),
                    resultSet.getLong("movieid"),
                    resultSet.getLong("serieid"),
                    resultSet.getLong("userid"),
                    resultSet.getString("name"),
                    resultSet.getString("description"));

    private final JdbcTemplate template;

    @Autowired
    public ReviewJdbcDao(final DataSource ds) {
        this.template = new JdbcTemplate(ds);
    }

    @Override
    public void addReview(Review review){
        template.update(
                "INSERT INTO review(type, movieid, serieid, userid, name, description) VALUES(?,?,?,?,?,?)",
                review.getType(), review.getMovieId(), review.getSerieId(), review.getUserId(), review.getName(), review.getDescription());
    }

    @Override
    public List<Review> getAllReviews(String type, Long id) {
        if(type.equals("movie")) {
            return template.query("SELECT * FROM review where movieid = ? order by review.reviewid desc", new Object[]{ id }, REVIEW_ROW_MAPPER);
//            return template.query("SELECT * FROM review where type = ? and movieid = ?", new Object[]{ type, id }, REVIEW_ROW_MAPPER);

        } else if(type.equals("serie")) {
            return template.query("SELECT * FROM review where serieid = ? order by review.reviewid desc", new Object[]{ id }, REVIEW_ROW_MAPPER);
//            return template.query("SELECT * FROM review where type = ? and serieid = ?", new Object[]{ type, id }, REVIEW_ROW_MAPPER);

        }
        return null;

    }

}

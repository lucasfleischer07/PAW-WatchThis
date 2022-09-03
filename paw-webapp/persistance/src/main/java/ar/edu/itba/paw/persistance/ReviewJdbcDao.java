package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.paw.models.Review;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReviewJdbcDao implements ReviewDao{

    private static final RowMapper<Review> REVIEW_ROW_MAPPER = (resultSet, rowNum) ->
            new Review(resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("username"),
                    resultSet.getString("useremail"),
                    resultSet.getInt("typeid"),
                    resultSet.getString("type"),
                    resultSet.getLong("reviewid"));

    private final JdbcTemplate template;

    @Autowired
    public ReviewJdbcDao(final DataSource ds) {
        this.template = new JdbcTemplate(ds);
    }

    @Override
    public void addReview(Review review){
        template.update(
                "INSERT INTO review(type, typeid, username, useremail, name, description) VALUES(?,?,?,?,?,?)",
                review.getType(), review.getId(), review.getUserName(), review.getEmail(), review.getName(), review.getDescription()
        );
    }

    @Override
    public List<Review> getAllReviews(String type, Long id) {
        return template.query("SELECT * FROM review where type = ? and typeid = ?", new Object[]{ type, id }, REVIEW_ROW_MAPPER);
    }

}

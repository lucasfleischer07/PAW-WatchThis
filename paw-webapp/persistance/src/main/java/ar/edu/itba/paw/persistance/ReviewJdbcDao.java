package ar.edu.itba.paw.persistance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.paw.models.Review;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ReviewJdbcDao implements ReviewDao{

    private static final RowMapper<Review> REVIEW_ROW_MAPPER = (resultSet, rowNum) ->
            new Review(resultSet.getLong("reviewid"),
                    resultSet.getString("type"),
                    resultSet.getLong("contentid"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getInt("rating"),
                    resultSet.getString("username"),
                    resultSet.getString("contentname"));

    private final JdbcTemplate template;

    @Autowired
    public ReviewJdbcDao(final DataSource ds) {
        this.template = new JdbcTemplate(ds);
    }

    @Override
    public void addReview(Review review){
        template.update(
                "INSERT INTO review(type, contentid, userid, name, description, rating)" +
                        "(SELECT ?,?,userid,?,?,? FROM userdata" +
                        "WHERE userdata.name=?)",
                review.getType(), review.getContentId(), review.getName(), review.getDescription(), review.getRating(),review.getUserName());
    }

    @Override
    public List<Review> getAllReviews(Long id) {
        return template.query("SELECT reviewid,review.type,contentid,review.name AS name,review.description,review.rating,userdata.name AS username,content.name AS contentname FROM review JOIN userdata ON review.userid = userdata.userid JOIN content ON contentid= content.id WHERE contentid = ? ORDER BY reviewid DESC", new Object[]{ id }, REVIEW_ROW_MAPPER);
    }

    @Override
    public List<Review> getAllUserReviews(String username) {
        return template.query("SELECT reviewid,review.type,contentid,review.name AS name,review.description,review.rating,userdata.name AS username,content.name AS contentname FROM review JOIN userdata ON review.userid = userdata.userid JOIN content ON contentid= content.id WHERE userdata.name = ? ORDER BY reviewid DESC", new Object[]{ username }, REVIEW_ROW_MAPPER);
    }

    @Override
    public Optional<Review> findById(Long reviewId) {
        return template.query("SELECT reviewid,review.type,contentid,review.name as name,review.description,review.rating,userdata.name AS username,content.name AS contentname FROM review JOIN userdata ON review.userid = userdata.userid JOIN content ON contentid= content.id WHERE reviewid = ?", new Object[]{ reviewId }, REVIEW_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public void deleteReview(Long id) {
        template.update("DELETE FROM review WHERE reviewid = ?", new Object[] {id});
    }

    @Override
    public void updateReview(String name, String description, Integer rating, Long id) {
        template.update("UPDATE review SET name = ?, description = ?, rating = ? WHERE reviewid = ?", new Object[]{name, description, rating, id});
    }



}

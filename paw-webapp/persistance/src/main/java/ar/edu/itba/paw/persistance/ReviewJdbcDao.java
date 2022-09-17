package ar.edu.itba.paw.persistance;

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
            new Review(resultSet.getLong("reviewid"),
                    resultSet.getString("type"),
                    resultSet.getLong("contentid"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getLong("rating"),
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

                "INSERT INTO review(type, contentid, userid, name, description, rating)\n" +
                        "(SELECT ?,?,userid,?,?,? from userdata\n" +
                        "WHERE userdata.name=?)",
                review.getType(), review.getContentId(), review.getName(), review.getDescription(), review.getRating(),review.getUserName());
    }

    @Override
    public List<Review> getAllReviews(Long id) {
        return template.query("SELECT reviewid,review.type,contentid,review.name as name,review.description,review.rating,userdata.name as username,content.name as contentname FROM review JOIN userdata ON review.userid = userdata.userid JOIN content ON contentid= content.id where contentid = ? order by reviewid desc", new Object[]{ id }, REVIEW_ROW_MAPPER);

    }

    @Override
    public List<Review> getAllUserReviews(String username) {
        return template.query("SELECT reviewid,review.type,contentid,review.name as name,review.description,review.rating,userdata.name as username,content.name as contentname FROM review JOIN userdata ON review.userid = userdata.userid JOIN content ON contentid= content.id where userdata.name = ? order by reviewid desc", new Object[]{ username }, REVIEW_ROW_MAPPER);

    }

    @Override
    public void deleteReview(Long id) {
        template.update("DELETE FROM review WHERE reviewid = ?", new Object[] {id});
    }

    @Override
    public void editReview(String newDesc, Long id, String typeOfEdit) {
        template.update("UPDATE review SET ? = ? WHERE reviewid = ?", new Object[] {typeOfEdit, newDesc, id});
    }

}

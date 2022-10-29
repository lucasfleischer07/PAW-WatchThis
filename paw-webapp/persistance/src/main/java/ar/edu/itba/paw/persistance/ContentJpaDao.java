package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import org.postgresql.core.NativeQuery;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.*;

@Primary
@Repository
@Transactional
public class ContentJpaDao implements ContentDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Content> getAllContent(String type, String sort) {
        TypedQuery<Content> query=em.createQuery("FROM Content",Content.class);
        if (Objects.equals(type, "movie") || Objects.equals(type, "serie")) {
            if (Objects.equals(sort, "ANY")) {
                query= em.createQuery("FROM Content WHERE type = :type",Content.class);
            } else if (Objects.equals(sort, "Last-released")) {
                query= em.createQuery("FROM Content WHERE type = :type ORDER BY released DESC",Content.class);
            } else if (Objects.equals(sort, "Older-released")) {
                query= em.createQuery("FROM Content WHERE type = :type ORDER BY released ASC",Content.class);
            } else if (Objects.equals(sort, "Best-ratings")) {
                query= em.createQuery("FROM Content WHERE type = :type ORDER BY rating DESC NULLS LAST",Content.class);
            } else if (Objects.equals(sort, "A-Z")) {
                query= em.createQuery("FROM Content WHERE type = :type ORDER BY name ASC",Content.class);
            } else if (Objects.equals(sort, "Z-A")) {
                query= em.createQuery("FROM Content WHERE type = :type ORDER BY name DESC",Content.class);
            }
            query.setParameter("type",type);
        } else {
            if (Objects.equals(sort, "ANY")) {
                query= em.createQuery("FROM Content",Content.class);
            } else if (Objects.equals(sort, "Last-released")) {
                query= em.createQuery("FROM Content ORDER BY released DESC",Content.class);
            } else if (Objects.equals(sort, "Older-released")) {
                query= em.createQuery("FROM Content ORDER BY released ASC",Content.class);
            } else if (Objects.equals(sort, "Best-ratings")) {
                query= em.createQuery("FROM Content ORDER BY rating DESC NULLS LAST",Content.class);
            } else if (Objects.equals(sort, "A-Z")) {
                query= em.createQuery("FROM Content ORDER BY name ASC",Content.class);
            } else if (Objects.equals(sort, "Z-A")) {
                query= em.createQuery("FROM Content ORDER BY name DESC",Content.class);
            }
        }

        return query.getResultList();
    }

    @Override
    public Optional<Content> findByName(String name) {
        final TypedQuery<Content> query=em.createQuery( "FROM Content WHERE name = :name",Content.class);
        query.setParameter("name",name);
        return query.getResultList().stream().findFirst();
    }

    private List<Long> genreBaseQuery(String genre){
        Query inQuery = null;
        String baseQuery = "SELECT id FROM content WHERE genre LIKE " + genre ;
        inQuery= em.createNativeQuery(baseQuery);
        List<Integer> resulList = inQuery.getResultList();
        List<Long> longList = new ArrayList<>();
        for (Integer big:resulList) {
            longList.add(big.longValue());
        }
        return longList;
    }

    @Override
    public List<Content> findByGenre(String type, String genre, String sort) {
        List<Long> longList = genreBaseQuery(genre);
        TypedQuery<Content> query;
        if (Objects.equals(type, "movie") || Objects.equals(type, "serie")) {
            if (Objects.equals(sort, "Last-released")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND type = :type  ORDER BY released DESC",Content.class);
            } else if (Objects.equals(sort, "Older-released")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND type = :type  ORDER BY released ASC",Content.class);
            } else if (Objects.equals(sort, "Best-ratings")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND type = :type  ORDER BY rating DESC NULLS LAST",Content.class);
            } else if (Objects.equals(sort, "A-Z")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND type = :type  ORDER BY name ASC",Content.class);
            } else if (Objects.equals(sort, "Z-A")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND type = :type  ORDER BY name DESC",Content.class);
            } else {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND type = :type ",Content.class);
            }
            query.setParameter("type",type);
        } else {
            if (Objects.equals(sort, "Last-released")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList )  ORDER BY released DESC",Content.class);
            } else if (Objects.equals(sort, "Older-released")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList )  ORDER BY released ASC",Content.class);
            } else if (Objects.equals(sort, "Best-ratings")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList )  ORDER BY rating DESC NULLS LAST",Content.class);
            } else if (Objects.equals(sort, "A-Z")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList )  ORDER BY name ASC",Content.class);
            } else if (Objects.equals(sort, "Z-A")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList )  ORDER BY name DESC",Content.class);
            } else {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList )  ",Content.class);
            }
        }
        query.setParameter("resultList",longList);
        return query.getResultList();
    }

    @Override
    public List<Content> findByDuration(String type, int durationFrom, int durationTo, String sort) {
        TypedQuery<Content> query= em.createQuery("From Content",Content.class);
        if(!Objects.equals(type, "all")) {
            if (Objects.equals(sort, "ANY")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo AND type = :type",Content.class);
            } else if (Objects.equals(sort, "Last-released")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo AND type = :type ORDER BY released DESC",Content.class);
            } else if (Objects.equals(sort, "Older-released")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo AND type = :type ORDER BY released ASC",Content.class);
            } else if (Objects.equals(sort, "Best-ratings")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo AND type = :type ORDER BY rating DESC NULLS LAST",Content.class);
            } else if (Objects.equals(sort, "A-Z")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo AND type = :type ORDER BY name ASC",Content.class);
            } else if (Objects.equals(sort, "Z-A")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo AND type = :type ORDER BY name DESC",Content.class);
            }
            query.setParameter("type",type);
        } else {
            if (Objects.equals(sort, "ANY")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo",Content.class);
            } else if (Objects.equals(sort, "Last-released")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY released DESC",Content.class);
            } else if (Objects.equals(sort, "Older-released")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY released ASC",Content.class);
            } else if (Objects.equals(sort, "Best-ratings")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY rating DESC NULLS LAST",Content.class);
            } else if (Objects.equals(sort, "A-Z")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY name ASC",Content.class);
            } else if (Objects.equals(sort, "Z-A")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY name DESC",Content.class);
            }
        }
        query.setParameter("durationFrom",durationFrom);
        query.setParameter("durationTo",durationTo);
        return query.getResultList();
    }

    @Override
    public List<Content> findByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, String sort) {
        List<Long> longList = genreBaseQuery(genre);
        TypedQuery<Content> query;
        if (Objects.equals(type, "movie") || Objects.equals(type, "serie")) {
            if (Objects.equals(sort, "Last-released")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND type = :type and durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY released DESC",Content.class);
            } else if (Objects.equals(sort, "Older-released")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND type = :type and durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY released ASC",Content.class);
            } else if (Objects.equals(sort, "Best-ratings")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND type = :type and durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY rating DESC NULLS LAST",Content.class);
            } else if (Objects.equals(sort, "A-Z")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND type = :type and durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY name ASC",Content.class);
            } else if (Objects.equals(sort, "Z-A")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND type = :type and durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY name DESC",Content.class);
            } else {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND type = :type and durationnum > :durationFrom  AND durationnum <= :durationTo",Content.class);
            }
            query.setParameter("type",type);
        } else {
            if (Objects.equals(sort, "Last-released")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY released DESC",Content.class);
            } else if (Objects.equals(sort, "Older-released")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY released ASC",Content.class);
            } else if (Objects.equals(sort, "Best-ratings")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY rating DESC NULLS LAST",Content.class);
            } else if (Objects.equals(sort, "A-Z")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY name ASC",Content.class);
            } else if (Objects.equals(sort, "Z-A")) {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY name DESC",Content.class);
            } else {
                query= em.createQuery("FROM Content WHERE id IN ( :resultList ) AND durationnum > :durationFrom  AND durationnum <= :durationTo ",Content.class);
            }
        }
        query.setParameter("durationFrom",durationFrom);
        query.setParameter("durationTo",durationTo);
        query.setParameter("resultList",longList);
        return query.getResultList();
    }

    @Override
    public Optional<Content> findById(long id) {
        return Optional.ofNullable(em.find(Content.class,id));

    }

    @Override
    public List<Content> getSearchedContent(String type,String queryUser) {
        TypedQuery<Content> query;
        if(Objects.equals(type, "movie") || Objects.equals(type, "serie")){
            query= em.createQuery("From Content WHERE type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query)",Content.class);
            query.setParameter("type",type);
        } else{
            query= em.createQuery("From Content WHERE (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query)",Content.class);

        }
        query.setParameter("query","%" + queryUser.toLowerCase() + "%");
        return query.getResultList();
    }

    //First makes the genre query in a NativeQuery
    @Override
    public List<Content> getSearchedContentByGenre(String type, String genre, String sort,String queryUser) {
        List<Long> longList = genreBaseQuery(genre);
        TypedQuery<Content> query;

        if (Objects.equals(type, "movie") || Objects.equals(type, "serie")) {
            if (Objects.equals(sort, "Last-released")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND type = :type  and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY released DESC ",Content.class);
            } else if (Objects.equals(sort, "Older-released")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND type = :type  and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY released ASC ",Content.class);
            } else if (Objects.equals(sort, "Best-ratings")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query)  ORDER BY rating DESC NULLS LAST ",Content.class);
            } else if (Objects.equals(sort, "A-Z")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY name ASC",Content.class);
            } else if (Objects.equals(sort, "Z-A")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY name DESC",Content.class);
            } else{
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ",Content.class);
            }
            query.setParameter("type",type);
        } else {
            if (Objects.equals(sort, "Last-released")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) OR LOWER(released) LIKE :query ORDER BY released DESC ",Content.class);
            } else if (Objects.equals(sort, "Older-released")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY released ASC ",Content.class);
            } else if (Objects.equals(sort, "Best-ratings")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY rating DESC NULLS LAST ",Content.class);
            } else if (Objects.equals(sort, "A-Z")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY name ASC",Content.class);
            } else if (Objects.equals(sort, "Z-A")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY name DESC",Content.class);
            } else{
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ",Content.class);
            }
        }
        query.setParameter("query","%" + queryUser.toLowerCase() + "%");
        query.setParameter("resultList",longList);
        return query.getResultList();
    }

    @Override
    public List<Content> getSearchedContentByDuration(String type, int durationFrom, int durationTo, String sort,String queryUser) {
        TypedQuery<Content> query= em.createQuery("From Content",Content.class);
        if(!Objects.equals(type, "all")) {
            if (Objects.equals(sort, "ANY")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query)",Content.class);
            } else if (Objects.equals(sort, "Last-released")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY released DESC",Content.class);
            } else if (Objects.equals(sort, "Older-released")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY released ASC",Content.class);
            } else if (Objects.equals(sort, "Best-ratings")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY rating DESC NULLS LAST",Content.class);
            } else if (Objects.equals(sort, "A-Z")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY name ASC",Content.class);
            } else if (Objects.equals(sort, "Z-A")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY name DESC",Content.class);
            }
            query.setParameter("type",type);
        } else {
            if (Objects.equals(sort, "ANY")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo",Content.class);
            } else if (Objects.equals(sort, "Last-released")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY released DESC",Content.class);
            } else if (Objects.equals(sort, "Older-released")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY released ASC",Content.class);
            } else if (Objects.equals(sort, "Best-ratings")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY rating DESC NULLS LAST",Content.class);
            } else if (Objects.equals(sort, "A-Z")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY name ASC",Content.class);
            } else if (Objects.equals(sort, "Z-A")) {
                query = em.createQuery("FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY name DESC",Content.class);
            }
        }
        query.setParameter("query","%" + queryUser.toLowerCase() + "%");
        query.setParameter("durationFrom",durationFrom);
        query.setParameter("durationTo",durationTo);
        return query.getResultList();
    }


    @Override
    public List<Content> getSearchedContentByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, String sort,String queryUser) {

        List<Long> longList = genreBaseQuery(genre);
        TypedQuery<Content> query;
        if (Objects.equals(type, "movie") || Objects.equals(type, "serie")) {
            if (Objects.equals(sort, "Last-released")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND type = :type  and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) AND durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY released DESC ",Content.class);
            } else if (Objects.equals(sort, "Older-released")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND type = :type  and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) AND durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY released ASC ",Content.class);
            } else if (Objects.equals(sort, "Best-ratings")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) AND durationnum > :durationFrom  AND durationnum <= :durationTo  ORDER BY rating DESC NULLS LAST ",Content.class);
            } else if (Objects.equals(sort, "A-Z")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) AND durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY name ASC",Content.class);
            } else if (Objects.equals(sort, "Z-A")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) AND durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY name DESC",Content.class);
            } else{
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) AND durationnum > :durationFrom  AND durationnum <= :durationTo ",Content.class);
            }
            query.setParameter("type",type);
        } else {
            if (Objects.equals(sort, "Last-released")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) AND durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY released DESC ",Content.class);
            } else if (Objects.equals(sort, "Older-released")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) AND durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY released ASC ",Content.class);
            } else if (Objects.equals(sort, "Best-ratings")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) AND durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY rating DESC NULLS LAST ",Content.class);
            } else if (Objects.equals(sort, "A-Z")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) AND durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY name ASC",Content.class);
            } else if (Objects.equals(sort, "Z-A")) {
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) AND durationnum > :durationFrom  AND durationnum <= :durationTo ORDER BY name DESC",Content.class);
            } else{
                query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) AND (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) AND durationnum > :durationFrom  AND durationnum <= :durationTo ",Content.class);
            }
        }
        query.setParameter("durationFrom",durationFrom);
        query.setParameter("durationTo",durationTo);
        query.setParameter("query","%" + queryUser.toLowerCase() + "%");
        query.setParameter("resultList",longList);
        return query.getResultList();
    }



    @Override
    public List<Content> getSearchedContentRandom(String queryUser) {
        TypedQuery<Content> query= em.createQuery("From Content WHERE (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY RANDOM()",Content.class);
        query.setParameter("query","%" + queryUser.toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public List<Content> findByType(String type) {
        TypedQuery<Content> query= em.createQuery("FROM Content WHERE type = :type",Content.class);
        query.setParameter("type",type);
        return query.getResultList();
    }

    @Override
    public List<Content> getBestRated() {
        Query inQuery=em.createNativeQuery("SELECT content.id FROM content LEFT JOIN (SELECT * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid GROUP BY content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum HAVING sum(review.rating)/count(*) > 3 ORDER BY sum(review.rating)/count(*) DESC,count(*) DESC LIMIT 20 ");
        List<Integer> resulList=inQuery.getResultList();
        List<Long> longList=new ArrayList<>();
        for (Integer big:resulList) {
            longList.add(big.longValue());
        }
        TypedQuery<Content> query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) ",Content.class);
        query.setParameter("resultList",longList);
        return query.getResultList();
    }

    @Override
    public List<Content> getUserRecommended(User user) {
        Query inQuery=em.createNativeQuery("SELECT content.id FROM content LEFT JOIN (SELECT * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid WHERE content.id IN (SELECT DISTINCT t2.contentid FROM userwatchlist t1 INNER JOIN userwatchlist t2 ON t1.userid = :userid AND t2.userid IN (SELECT userdata.userid FROM userwatchlist t1  INNER JOIN userwatchlist t2 ON (t2.contentid = t1.contentid) INNER JOIN userdata ON userdata.userid = t2.userid INNER JOIN content ON content.id = t1.contentid WHERE t1.userid = :userid AND t2.userid <> :userid GROUP BY userdata.userid) AND t2.contentid NOT IN (SELECT contentid FROM userwatchlist WHERE userid = :userid)) GROUP BY content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum LIMIT 20");
        inQuery.setParameter("userid",user.getId());
        List<Integer> resulList=inQuery.getResultList();
        List<Long> longList=new ArrayList<>();
        for (Integer big:resulList) {
            longList.add(big.longValue());
        }
        TypedQuery<Content> query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) ",Content.class);
        query.setParameter("resultList",longList);
        return query.getResultList();
    }

    @Override
    public List<Content> getMostUserSaved() {
        Query inQuery=em.createNativeQuery("SELECT content.id FROM (content JOIN userwatchlist ON content.id = userwatchlist.contentId) GROUP BY content.id, content.image ORDER BY COUNT(*) DESC LIMIT 20");
        List<Integer> resulList=inQuery.getResultList();
        List<Long> longList=new ArrayList<>();
        for (Integer big:resulList) {
            longList.add(big.longValue());
        }
        TypedQuery<Content> query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) ",Content.class);
        query.setParameter("resultList",longList);
        return query.getResultList();
    }

    @Override
    public List<Content> getLastAdded() {
        TypedQuery<Content> query= em.createQuery(" FROM Content ORDER BY id DESC ",Content.class);
        query.setMaxResults(20);
        return query.getResultList();

    }

    @Override
    public void contentCreate(String name, String description, String releaseDate, String genre, String creator, Integer duration, String durationString, String type, byte[] contentImage) {
        Content toAdd=new Content(name,contentImage,description,releaseDate,genre,creator,durationString,duration,type);
        em.persist(toAdd);
    }

    @Override
    public Optional<String> getContentQuote(String language) {
        Query query;
        if(Objects.equals(language, "Spanish")) {
        query=em.createNativeQuery( "SELECT spanish FROM quotes ORDER BY RANDOM() LIMIT 1");}
        else{query=em.createNativeQuery( "SELECT english FROM quotes ORDER BY RANDOM() LIMIT 1");}
        String res=(String) query.getResultList().stream().findFirst().get();
        return Optional.of(res);
    }

    @Override
    public void updateContent(Long id, String name, String description, String releaseDate, String genre, String creator, Integer duration, String durationString, String type) {
        Content content=findById(id).get();
        content.setName(name);
        content.setDescription(description);
        content.setReleased(releaseDate);
        content.setCreator(creator);
        content.setGenre(genre);
        content.setDuration(durationString);
        content.setDurationNum(duration);
        em.merge(content);
    }

    @Override
    public void updateWithImageContent(Long id, String name, String description, String releaseDate, String genre, String creator, Integer duration, String durationString, String type, byte[] contentImage) {
        Content content=findById(id).get();
        content.setName(name);
        content.setDescription(description);
        content.setReleased(releaseDate);
        content.setCreator(creator);
        content.setGenre(genre);
        content.setDuration(durationString);
        content.setDurationNum(duration);
        content.setImage(contentImage);
        em.merge(content);
    }

    @Override
    public void deleteContent(Long id) {
        Content toDelete =findById(id).get();
        em.remove(toDelete);
    }
}
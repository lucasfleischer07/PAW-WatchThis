package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.models.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Primary
@Repository
@Transactional
public class ContentJpaDao implements ContentDao{
    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    private TypedQuery<Content> getFinalQuery(List resultList, String sortString, int page, int pageSize){
        if(resultList.size() == 0)
            return null;
        final List<Long> idList = (List<Long>) resultList.stream()
                .map(n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        final TypedQuery<Content> query = em.createQuery("FROM Content WHERE id in :idList " + sortString,Content.class);
        query.setParameter("idList",idList);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query;
    }

    @Override
    public PageWrapper<Content> getAllContent(String type, Sorting sort, int page, int pageSize) {
        Query nativeQuery;

        String sortString = sort == null ? "" : sort.getQueryString();
        if (Objects.equals(type, "movie") || Objects.equals(type, "serie")) {
            nativeQuery = em.createNativeQuery("SELECT id FROM Content WHERE type = :type");
            nativeQuery.setParameter("type",type);
        } else {
            nativeQuery = em.createNativeQuery("SELECT id FROM Content " + sortString);
        }
        long totalContent = PageWrapper.calculatePageAmount(nativeQuery.getResultList().size(),pageSize);
        int totalSize = nativeQuery.getResultList().size();

        final TypedQuery<Content> query = getFinalQuery(nativeQuery.getResultList(),sortString, page, pageSize);
        if(query != null){
            return new PageWrapper<Content>(page,totalContent,pageSize, query.getResultList(),totalSize);
        }
        return new PageWrapper<Content>(page,totalContent,pageSize, Collections.emptyList(),0);
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
    public PageWrapper<Content> findByGenre(String type, String genre, Sorting sort, int page, int pageSize) {
        String sortString = sort == null ? "" : sort.getQueryString();
        List<Long> longList = genreBaseQuery(genre);
        Query nativeQuery;
        if (longList.size() == 0){
            return new PageWrapper<>(0,0,0,Collections.emptyList(),0);
        }
        if (Objects.equals(type, "movie") || Objects.equals(type, "serie")) {
            nativeQuery = em.createNativeQuery("SELECT id FROM Content WHERE id IN (:resultList) AND type = :type");
            nativeQuery.setParameter("type",type);
        } else {
            nativeQuery = em.createNativeQuery("SELECT id FROM Content WHERE id IN (:resultList) ");
        }

        nativeQuery.setParameter("resultList",longList);
        long totalContent = PageWrapper.calculatePageAmount(nativeQuery.getResultList().size(),pageSize);
        int totalSize = nativeQuery.getResultList().size();

        final TypedQuery<Content> query = getFinalQuery(nativeQuery.getResultList(),sortString, page, pageSize);
        if(query != null){
            return new PageWrapper<Content>(page,totalContent,pageSize, query.getResultList(),totalSize);
        }
        return new PageWrapper<Content>(page,totalContent,pageSize, Collections.emptyList(),0);
    }

    @Override
    public PageWrapper<Content> findByDuration(String type, int durationFrom, int durationTo, Sorting sort, int page, int pageSize) {
        String sortString = sort == null ? "" : sort.getQueryString();
        Query nativeQuery;
        if(!Objects.equals(type, "all")) {
            nativeQuery = em.createNativeQuery("select id FROM Content WHERE durationnum > :durationFrom AND durationnum <= :durationTo AND type = :type ");
            nativeQuery.setParameter("type",type);
        } else {
            nativeQuery = em.createNativeQuery("select id FROM Content WHERE durationnum > :durationFrom AND durationnum <= :durationTo ");
        }
        nativeQuery.setParameter("durationFrom",durationFrom);
        nativeQuery.setParameter("durationTo",durationTo);
        long totalContent = PageWrapper.calculatePageAmount(nativeQuery.getResultList().size(),pageSize);
        int totalSize = nativeQuery.getResultList().size();

        final TypedQuery<Content> query = getFinalQuery(nativeQuery.getResultList(), sortString, page, pageSize);
        if(query != null){
            return new PageWrapper<Content>(page,totalContent,pageSize, query.getResultList(),totalSize);
        }
        return new PageWrapper<Content>(page,totalContent,pageSize, Collections.emptyList(),0);
    }

    @Override
    public PageWrapper<Content> findByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, Sorting sort, int page, int pageSize) {
        String sortString = sort == null ? "" : sort.getQueryString();
        List<Long> longList = genreBaseQuery(genre);
        Query nativeQuery;
        if (longList.size() == 0){
            return new PageWrapper<>(0,0,0,Collections.emptyList(),0);
        }
        if (Objects.equals(type, "movie") || Objects.equals(type, "serie")) {
            nativeQuery= em.createNativeQuery("SELECT id FROM Content WHERE id IN ( :resultList ) AND type = :type and durationnum > :durationFrom  AND durationnum <= :durationTo");
            nativeQuery.setParameter("type",type);
        } else {
            nativeQuery= em.createNativeQuery("FROM Content WHERE id IN ( :resultList ) AND durationnum > :durationFrom  AND durationnum <= :durationTo ");
        }
        nativeQuery.setParameter("durationFrom",durationFrom);
        nativeQuery.setParameter("durationTo",durationTo);
        nativeQuery.setParameter("resultList",longList);
        long totalContent = PageWrapper.calculatePageAmount(nativeQuery.getResultList().size(),pageSize);
        int totalSize = nativeQuery.getResultList().size();

        final TypedQuery<Content> query = getFinalQuery(nativeQuery.getResultList(),sortString, page, pageSize);
        if(query != null){
            return new PageWrapper<Content>(page,totalContent,pageSize, query.getResultList(),totalSize);
        }
        return new PageWrapper<Content>(page,totalContent,pageSize, Collections.emptyList(),0);
    }

    @Override
    public Optional<Content> findById(long id) {
        return Optional.ofNullable(em.find(Content.class,id));
    }

    @Override
    public PageWrapper<Content> getSearchedContent(String type, String queryUser, Sorting sort, int page, int pageSize) {
        Query nativeQuery;
        String sortString = sort == null ? "" : sort.getQueryString();
        if(Objects.equals(type, "movie") || Objects.equals(type, "serie")){
            nativeQuery= em.createNativeQuery("SELECT id From Content WHERE type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query)");
            nativeQuery.setParameter("type",type);
        } else{
            nativeQuery= em.createNativeQuery("SELECT id From Content WHERE (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query)");
        }


        nativeQuery.setParameter("query","%" + queryUser.toLowerCase() + "%");
        long totalContent = PageWrapper.calculatePageAmount(nativeQuery.getResultList().size(),pageSize);
        int totalSize = nativeQuery.getResultList().size();

        final TypedQuery<Content> query = getFinalQuery(nativeQuery.getResultList(), sortString, page, pageSize);
        if(query != null){
            return new PageWrapper<Content>(page,totalContent,pageSize, query.getResultList(),totalSize);
        }
        return new PageWrapper<Content>(page,totalContent,pageSize, Collections.emptyList(),0);
    }

    //First makes the genre query in a NativeQuery
    @Override
    public PageWrapper<Content> getSearchedContentByGenre(String type, String genre, Sorting sort, String queryUser, int page, int pageSize) {
        List<Long> longList = genreBaseQuery(genre);
        Query nativeQuery;
        if (longList.size() == 0){
            return new PageWrapper<>(0,0,0,Collections.emptyList(),0);
        }
        String sortString = sort == null ? "" : sort.getQueryString();
        if (Objects.equals(type, "movie") || Objects.equals(type, "serie")) {
            nativeQuery= em.createNativeQuery("SELECT id FROM Content WHERE id IN ( :resultList ) AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query)");
            nativeQuery.setParameter("type",type);
        } else {
            nativeQuery= em.createNativeQuery("SELECT id FROM Content WHERE id IN ( :resultList ) AND (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query)");
        }


        nativeQuery.setParameter("query","%" + queryUser.toLowerCase() + "%");
        nativeQuery.setParameter("resultList",longList);
        long totalContent = PageWrapper.calculatePageAmount(nativeQuery.getResultList().size(),pageSize);
        int totalSize = nativeQuery.getResultList().size();

        final TypedQuery<Content> query = getFinalQuery(nativeQuery.getResultList(),sortString, page, pageSize);
        if(query != null){
            return new PageWrapper<Content>(page,totalContent,pageSize, query.getResultList(),totalSize);
        }
        return new PageWrapper<Content>(page,totalContent,pageSize, Collections.emptyList(),0);
    }

    @Override
    public PageWrapper<Content> getSearchedContentByDuration(String type, int durationFrom, int durationTo, Sorting sort, String queryUser, int page, int pageSize) {
        String sortString = sort == null ? "" : sort.getQueryString();
        Query nativeQuery;
        if(!Objects.equals(type, "all")) {
            nativeQuery = em.createNativeQuery("SELECT id FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query)");
            nativeQuery.setParameter("type",type);
        } else {
            nativeQuery = em.createNativeQuery("SELECT id FROM Content WHERE durationnum > :durationFrom  AND durationnum <= :durationTo and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query)");
        }

        nativeQuery.setParameter("query","%" + queryUser.toLowerCase() + "%");
        nativeQuery.setParameter("durationFrom",durationFrom);
        nativeQuery.setParameter("durationTo",durationTo);
        long totalContent = PageWrapper.calculatePageAmount(nativeQuery.getResultList().size(),pageSize);
        int totalSize = nativeQuery.getResultList().size();

        nativeQuery.setFirstResult((page - 1) * pageSize);
        nativeQuery.setMaxResults(pageSize);

        final TypedQuery<Content> query = getFinalQuery(nativeQuery.getResultList(), sortString, page, pageSize);
        if(query != null){
            return new PageWrapper<Content>(page,totalContent,pageSize, query.getResultList(),totalSize);
        }
        return new PageWrapper<Content>(page,totalContent,pageSize, Collections.emptyList(),0);
    }


    @Override
    public PageWrapper<Content> getSearchedContentByDurationAndGenre(String type, String genre, int durationFrom, int durationTo, Sorting sort, String queryUser, int page, int pageSize) {
        String sortString = sort == null ? "" : sort.getQueryString();
        List<Long> longList = genreBaseQuery(genre);
        Query nativeQuery;
        if (longList.size() == 0){
            return new PageWrapper<>(0,0,0,Collections.emptyList(),0);
        }
        if (Objects.equals(type, "movie") || Objects.equals(type, "serie")) {
            nativeQuery= em.createNativeQuery("SELECT id FROM Content WHERE id IN ( :resultList ) AND type = :type and (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) AND durationnum > :durationFrom  AND durationnum <= :durationTo");
            nativeQuery.setParameter("type",type);
        } else {
            nativeQuery= em.createNativeQuery("SELECT id FROM Content WHERE id IN ( :resultList ) AND (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) AND durationnum > :durationFrom  AND durationnum <= :durationTo");
        }

        nativeQuery.setParameter("durationFrom",durationFrom);
        nativeQuery.setParameter("durationTo",durationTo);
        nativeQuery.setParameter("query","%" + queryUser.toLowerCase() + "%");
        nativeQuery.setParameter("resultList",longList);
        long totalContent = PageWrapper.calculatePageAmount(nativeQuery.getResultList().size(),pageSize);
        int totalSize = nativeQuery.getResultList().size();

        final TypedQuery<Content> query = getFinalQuery(nativeQuery.getResultList(), sortString, page, pageSize);
        if(query != null){
            return new PageWrapper<Content>(page,totalContent,pageSize, query.getResultList(),totalSize);
        }
        return new PageWrapper<Content>(page,totalContent,pageSize, Collections.emptyList(),0);
    }



    @Override
    public PageWrapper<Content> getSearchedContentRandom(String queryUser, int page, int pageSize) {
        Query nativeQuery= em.createNativeQuery("SELECT id From Content WHERE (LOWER(name) LIKE :query OR LOWER(creator) LIKE :query OR LOWER(released) LIKE :query) ORDER BY RANDOM()");
        nativeQuery.setParameter("query","%" + queryUser.toLowerCase() + "%");

        long totalContent = PageWrapper.calculatePageAmount(nativeQuery.getResultList().size(),pageSize);
        int totalSize = nativeQuery.getResultList().size();

        final TypedQuery<Content> query = getFinalQuery(nativeQuery.getResultList()," ORDER BY RANDOM()", page, pageSize);
        if(query != null){
            return new PageWrapper<Content>(page,totalContent,pageSize, query.getResultList(),totalSize);
        }
        return new PageWrapper<Content>(page,totalContent,pageSize, Collections.emptyList(),0);
    }

    @Override
    public PageWrapper<Content> findByType(String type, int page, int pageSize) {
        Query nativeQuery= em.createNativeQuery("SELECT id FROM Content WHERE type = :type");

        nativeQuery.setParameter("type",type);
        long totalContent = PageWrapper.calculatePageAmount(nativeQuery.getResultList().size(),pageSize);
        int totalSize = nativeQuery.getResultList().size();

        final TypedQuery<Content> query = getFinalQuery(nativeQuery.getResultList(),"", page, pageSize);
        if(query != null){
            return new PageWrapper<Content>(page,totalContent,pageSize, query.getResultList(),totalSize);
        }
        return new PageWrapper<Content>(page,totalContent,pageSize, Collections.emptyList(),0);
    }

    @Override
    public PageWrapper<Content> getBestRated(int page, int pageSize) {
        Query inQuery=em.createNativeQuery("SELECT content.id FROM content LEFT JOIN (SELECT * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid GROUP BY content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum HAVING sum(review.rating)/count(*) >= 3 ORDER BY sum(review.rating)/count(*) DESC,count(*) DESC LIMIT 20 ");
        List<Integer> resulList=inQuery.getResultList();
        List<Long> longList=new ArrayList<>();
        for (Integer big:resulList) {
            longList.add(big.longValue());
        }
        TypedQuery<Content> query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) ORDER BY rating DESC",Content.class);
        query.setParameter("resultList",longList);
        long totalContent = PageWrapper.calculatePageAmount(query.getResultList().size(),pageSize);
        int totalSize = query.getResultList().size();
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        return new PageWrapper<Content>(page,totalContent,pageSize,query.getResultList(),totalSize) ;
    }

    @Override
    public PageWrapper<Content> getUserRecommended(User user, int page, int pageSize) {
        Query inQuery=em.createNativeQuery("SELECT content.id FROM content LEFT JOIN (SELECT * FROM review AS r2 WHERE r2.rating<>0) AS review ON content.id = review.contentid WHERE content.id IN (SELECT DISTINCT t2.contentid FROM userwatchlist t1 INNER JOIN userwatchlist t2 ON t1.userid = :userid AND t2.userid IN (SELECT userdata.userid FROM userwatchlist t1  INNER JOIN userwatchlist t2 ON (t2.contentid = t1.contentid) INNER JOIN userdata ON userdata.userid = t2.userid INNER JOIN content ON content.id = t1.contentid WHERE t1.userid = :userid AND t2.userid <> :userid GROUP BY userdata.userid) AND t2.contentid NOT IN (SELECT contentid FROM userwatchlist WHERE userid = :userid)) GROUP BY content.id,content.name,content.description,content.released,content.genre,content.creator,content.duration,content.type,content.durationNum LIMIT 20");
        inQuery.setParameter("userid",user.getId());
        List<Integer> resulList=inQuery.getResultList();
        List<Long> longList=new ArrayList<>();
        for (Integer big:resulList) {
            longList.add(big.longValue());
        }
        if(longList.size()>0){
            TypedQuery<Content> query= em.createQuery(" FROM Content WHERE id IN ( :resultList ) ",Content.class);
            query.setParameter("resultList",longList);
            long totalContent = PageWrapper.calculatePageAmount(query.getResultList().size(),pageSize);
            int totalSize = query.getResultList().size();

            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);

            return new PageWrapper<Content>(page,totalContent,pageSize,query.getResultList(),totalSize);
        }
        return new PageWrapper<Content>(0,0,0,Collections.emptyList(),0) ;

    }

    @Override
    public PageWrapper<Content> getMostUserSaved(int page, int pageSize) {
        Query inQuery=em.createNativeQuery("SELECT content.id FROM (content JOIN userwatchlist ON content.id = userwatchlist.contentId) GROUP BY content.id, content.image ORDER BY COUNT(*) DESC LIMIT 20");
        List<Integer> resulList=inQuery.getResultList();
        List<Long> longList=new ArrayList<>();
        for (Integer big:resulList) {
            longList.add(big.longValue());
        }
        if(longList.size()>0) {
            TypedQuery<Content> query = em.createQuery(" FROM Content WHERE id IN ( :resultList ) ", Content.class);
            query.setParameter("resultList", longList);
            long totalContent = PageWrapper.calculatePageAmount(query.getResultList().size(),pageSize);
            int totalSize = query.getResultList().size();

            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);

            return new PageWrapper<Content>(page,totalContent,pageSize,query.getResultList(),totalSize) ;
        }
        return new PageWrapper<Content>(0,0,0,Collections.emptyList(),0);
    }

    @Override
    public PageWrapper<Content> getLastAdded(int page, int pageSize) {
        Query nativeQuery= em.createNativeQuery("SELECT id FROM Content ORDER BY id DESC ");

        long totalContent = PageWrapper.calculatePageAmount(nativeQuery.getResultList().size(),pageSize);
        int totalSize = nativeQuery.getResultList().size();

        final TypedQuery<Content> query = getFinalQuery(nativeQuery.getResultList()," ORDER BY id DESC", page, pageSize);
        if(query != null){
            return new PageWrapper<Content>(page,totalContent,pageSize, query.getResultList(),totalSize);
        }
        return new PageWrapper<Content>(page,totalContent,pageSize, Collections.emptyList(),0);
    }

    @Override
    public Content contentCreate(String name, String description, String releaseDate, String genre, String creator, Integer duration, String durationString, String type, byte[] contentImage) {
        Content toAdd=new Content(name,contentImage,description,releaseDate,genre,creator,durationString,duration,type);
        em.persist(toAdd);
        return toAdd;
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
        em.remove(findById(id).get());
    }

    public List<User> getContentReviewers(Long id) {
        Query inQuery=em.createNativeQuery("SELECT userid from review where contentid = :id");
        inQuery.setParameter("id",id);
        List<Integer> resulList = inQuery.getResultList();
        List<Long> longList=new ArrayList<>();
        for (Integer big:resulList) {
            longList.add(big.longValue());
        }
        if(longList.size()>0) {
            TypedQuery<User> query = em.createQuery(" FROM User WHERE id IN ( :resultList ) ", User.class);
            query.setParameter("resultList", longList);
            return query.getResultList();
        }
        return new ArrayList<User>();
    }
}

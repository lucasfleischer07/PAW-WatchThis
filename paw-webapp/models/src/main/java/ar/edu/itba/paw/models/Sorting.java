package ar.edu.itba.paw.models;

public enum Sorting {
    OlderReleased(" ORDER BY released DESC"),
    NewestReleased(" ORDER BY released ASC"),
    MostRated(" ORDER BY rating DESC NULLS LAST"),
    NameAsc(" ORDER BY name ASC"),
    NameDesc(" ORDER BY name DESC");


    private String queryString;

    private Sorting(String queryString){
        this.queryString=queryString;
    }

    public String getQueryString(){
        return queryString;
    }


}

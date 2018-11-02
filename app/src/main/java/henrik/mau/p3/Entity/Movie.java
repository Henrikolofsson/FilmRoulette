package henrik.mau.p3.Entity;

import java.io.Serializable;

public class Movie implements Serializable {
    private String title;
    private String date;
    private String overview;
    private String rating;
    private String id;
    private String youtube;
    private String poster;
    private String backdrop;


    public Movie() {
    }

    public Movie(String title, String date, String overview, String rating, String id, String poster) {
        this.title = title;
        this.date = date;
        this.overview = overview;
        this.rating = rating;
        this.id = id;
        this.youtube = youtube;
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

}

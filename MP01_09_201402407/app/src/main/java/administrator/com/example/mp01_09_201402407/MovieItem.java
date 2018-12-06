package administrator.com.example.mp01_09_201402407;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MovieItem {
    private String movieName;
    private int movieYear;
    private String directorName;
    private double movieScore;
    private String movieCountry;

    public MovieItem(String movieName, int movieYear, String directorName, double movieScore, String movieCountry) {
        this.movieName = movieName;
        this.movieYear = movieYear;
        this.directorName = directorName;
        this.movieScore = movieScore;
        this.movieCountry = movieCountry;
    }
    public String getMovieCountry() {
        return movieCountry;
    }

    public void setMovieCountry(String movieCountry) {
        this.movieCountry = movieCountry;
    }

    public double getMovieScore() {
        return movieScore;
    }

    public void setMovieScore(double movieScore) {
        this.movieScore = movieScore;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public int getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(int movieYear) {
        this.movieYear = movieYear;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}

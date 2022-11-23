package easv.mrs.GUI.Model;

import easv.mrs.BE.Movie;
import easv.mrs.BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieModel {

    private ObservableList<Movie> moviesToBeViewed;

    private MovieManager movieManager;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }



    public ObservableList<Movie> getObservableMovies() {
        return moviesToBeViewed;
    }

    public void searchMovie(String query) throws Exception {
        List<Movie> searchResults = movieManager.searchMovies(query);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResults);
    }

    public void createNewMovie(String tittle, int year) throws Exception {
        //Makes Movie
        Movie m = movieManager.createNewMovie(tittle, year);
        //Adds movie to observable list
        moviesToBeViewed.add(m);
    }

    public void updateMovie(Movie updatedMovie) throws Exception{
        //Call BLL
        //update movie in DB
        movieManager.updateMovie(updatedMovie);

        //UpdateListView
        //Clears the list, and returns a new list from DB
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }

    public void deleteMovie(Movie movieForDeletion) throws Exception {
        //Delete Movie
        movieManager.deleteMovie(movieForDeletion);

        //UpdateListView
        //Clears the list, and returns a new list from DB
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }
}

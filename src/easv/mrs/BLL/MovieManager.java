package easv.mrs.BLL;

import easv.mrs.BE.Movie;
import easv.mrs.BLL.util.MovieSearcher;
import easv.mrs.DAL.IMovieDataAccess;
import easv.mrs.DAL.MovieDAO;
import easv.mrs.DAL.MovieDAO_Mock;
import easv.mrs.DAL.db.MovieDAO_DB;

import java.util.List;

public class MovieManager {

    private MovieSearcher movieSearcher = new MovieSearcher();

    private IMovieDataAccess movieDAO;


    public MovieManager() {
        movieDAO = new MovieDAO_DB();
    }

    public List<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    public List<Movie> searchMovies(String query) throws Exception {
        List<Movie> allMovies = getAllMovies();
        List<Movie> searchResult = movieSearcher.search(allMovies, query);
        return searchResult;
    }

    public Movie createNewMovie(String tittle, int year) throws Exception {
        return movieDAO.createMovie(tittle, year);
    }


    public void updateMovie(Movie updatedMovie) throws Exception{
        movieDAO.updateMovie(updatedMovie);
    }

    public void deleteMovie(Movie movieForDeletion) throws Exception {
        movieDAO.deleteMovie(movieForDeletion);
    }
}

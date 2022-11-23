package easv.mrs.DAL.db;

import easv.mrs.BE.Movie;
import easv.mrs.DAL.IMovieDataAccess;
import easv.mrs.DAL.MovieDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess {

    private MyDatabaseConnector databaseConnector;

    public MovieDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    public List<Movie> getAllMovies() throws Exception {

        List<Movie> allMovies = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection();
            Statement stmt = conn.createStatement())//Stmt comunicates with the database and send commands)
        {
            // SELECT all collums FROM movie table
            String sql = "SELECT * FROM Movie;";

            ResultSet rs = stmt.executeQuery(sql);

            //loop through rows from the fatabase resualt set
            while(rs.next()) {

                //Map db row to movie object

                //gets the values based on the DB column name
                int id = rs.getInt("Id");
                String tittle = rs.getString("Title");
                int year = rs.getInt("Year");

                //Makes movie object and adds it to allMovie ArrayList
                Movie movie = new Movie(id, year, tittle);
                allMovies.add(movie);
            }

        } catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not get all movies from the DataBase", ex);
        }

        return allMovies;
    }

    public Movie createMovie(String title, int year) throws Exception {

        //insert variables into sql DB
        String sql = "INSERT INTO Movie (Title,Year)VALUES (?,?)";

        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  //prepared statement is like a normal statement but protects against malisius sql injections

            //Sets the variables for DB
            stmt.setString(1,title);
            stmt.setInt(2,year);

            //Run the specfied sql statenebt and update DB
            stmt.executeUpdate();

            //get the generated ID from DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            if(rs.next()) {
                id = rs.getInt(1);
            }

            //Makes new movie object and returns
            Movie movie = new Movie(id,year,title);
            return movie;
        } catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not create a new movie", ex);
        }
    }

    public void updateMovie(Movie movie) throws Exception {

        try(Connection conn = databaseConnector.getConnection()){

            //sql code for DB
            String sql = "UPDATE Movie set Title = ?, Year = ? WHERE Id = ?";

            //Connnect prepared stametnt to sql
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Bind parameters to stmt
            stmt.setString(1, movie.getTitle());
            stmt.setInt(2, movie.getYear());
            stmt.setInt(3,movie.getId());

            //Run the specfied sql statenebt and update DB
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not update Movie" + ex);
        }

    }

    public void deleteMovie(Movie movie) throws Exception {
        try(Connection conn = databaseConnector.getConnection()){

            //sql code for DB
            String sql = "DELETE Movie WHERE Id = ?";

            //Connnect prepared stametnt to sql
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Bind parameters to stmt
            stmt.setInt(1,movie.getId());

            //Run the specfied sql statenebt and update DB
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not delete Movie" + ex);
        }
    }

    public List<Movie> searchMovies(String query) throws Exception {

        //TODO Do this
        throw new UnsupportedOperationException();
    }

}

package Transfer;

import easv.mrs.BE.Movie;
import easv.mrs.DAL.MovieDAO;
import easv.mrs.DAL.db.MyDatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TransferFilesToDB {
    private static MyDatabaseConnector databaseConnector;

    public TransferFilesToDB() {
        databaseConnector = new MyDatabaseConnector();
    }

    private static void transferFile(List<Movie> listMovieForTransfer) throws Exception {

        for (Movie dao:listMovieForTransfer) {

            if (dao.getId() > 190) {
                //insert variables into sql DB
                String sql = "INSERT INTO Movie (Title,Year)VALUES (?,?)";

                try (Connection conn = databaseConnector.getConnection()) {
                    PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  //prepared statement is like a normal statement but protects against malisius sql injections

                    //Sets the variables for DB
                    stmt.setString(1, dao.getTitle());
                    stmt.setInt(2, dao.getYear());

                    //Run the specfired sql statenebt and update DB
                    stmt.executeUpdate();

                    System.out.println(dao.getTitle());

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new Exception("Could not Transfer all movies", ex);
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException {

        MovieDAO mdao = new MovieDAO();

        MyDatabaseConnector databaseConnector = new MyDatabaseConnector();

        try (Connection connection = databaseConnector.getConnection()) {

            transferFile(mdao.getAllMovies());

        } //Connection gets closed here
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

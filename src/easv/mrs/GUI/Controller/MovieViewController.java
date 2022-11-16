package easv.mrs.GUI.Controller;

import easv.mrs.BE.Movie;
import easv.mrs.GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {


    @FXML private TextField txtMovieSearch;
    @FXML private ListView<Movie> lstMovies;
    @FXML private TextField txtTittle;
    @FXML private TextField txtYear;

    private MovieModel movieModel;

    public MovieViewController()  {

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        lstMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }


    public void handleAddNewMovie(ActionEvent actionEventEvent) {
        try {
            String tittle = txtTittle.getText();
            int year = Integer.parseInt(txtYear.getText());

            System.out.println("Creating:  " + tittle + " ; " +  year);

            movieModel.createNewMovie(tittle, year);

            System.out.println("Movie " + tittle + " Created");

        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
            System.out.println("Could not create movie");
        }
    }
}

package easv.mrs.GUI.Controller;

import easv.mrs.BE.Movie;
import easv.mrs.GUI.Model.MovieModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {


    @FXML private Button btnDelete;
    @FXML private Button btnUpdate;
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
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        lstMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

        lstMovies.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Movie>() {
            @Override
            public void changed(ObservableValue<? extends Movie> observable, Movie oldValue, Movie newValue) {
                if (newValue != null){
                    txtTittle.setText(newValue.getTitle());
                    txtYear.setText(String.valueOf(newValue.getYear()));
                    btnUpdate.setDisable(false);
                    btnDelete.setDisable(false);
                } else if(newValue == null){
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                }
            }
        });

    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("!!ERROR!!");
        alert.setHeaderText("Something went wrong, \n ERROR:      " + t.getMessage());
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

    public void handleUpdateMovie(ActionEvent actionEvent) {
        System.out.println("UPDATE btn WAS CLICKED");

        try {
            Movie updatedMovie = lstMovies.getSelectionModel().getSelectedItem();
            updatedMovie.setTitle(txtTittle.getText()); //sets new title on the sellected object from the indput box.
            updatedMovie.setYear(Integer.parseInt(txtYear.getText())); //sets new year on the sellected object from the indput box.

            movieModel.updateMovie(updatedMovie);
        } catch (Exception e) {
            displayError(e);
            throw new RuntimeException(e);
        }
    }

    public void handleDeleteMovie(ActionEvent actionEvent) {
        System.out.println("DELETE btn WAS CLICKED");

        try {
            Movie movieForDeletion = lstMovies.getSelectionModel().getSelectedItem();

            movieModel.deleteMovie(movieForDeletion);
        } catch (Exception e) {
            displayError(e);
            throw new RuntimeException(e);
        }

    }
}

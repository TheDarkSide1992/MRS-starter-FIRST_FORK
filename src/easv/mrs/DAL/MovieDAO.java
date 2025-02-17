package easv.mrs.DAL;

import easv.mrs.BE.Movie;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;

public class MovieDAO implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    public Path pathToFile = Path.of(MOVIES_FILE);

    public List<Movie> getAllMovies() throws IOException {
        //Read all lines from file.
        List<String> lines = Files.readAllLines(pathToFile);
        List<Movie> movies = new ArrayList<>();

        //Pass each line to a movie object
        for (String line:lines) {

            //Separate eatch line for every regex (","), and makes a array, with a limmit of 2 splits
            String[] sepLine = line.split(",",3);

            //map eatch separtet line to movie object
            int id = Integer.parseInt(sepLine[0]);
            int year = Integer.parseInt(sepLine[1]);
            String name = sepLine[2];

            //Creates an object with data
            Movie movie = new Movie(id,year,name);

            //Adds object to list of object
            movies.add(movie);
        }

        //Sorts the list of all movies based on ID, the movie with the lowest valued ID wil be first int the list.
        movies.sort(Comparator.comparing(Movie::getId));

        //return List of Movie objects
        return movies;
    }

    @Override
    public Movie createMovie(String title, int year) throws Exception {
        //Makes new ID
        int id = getNextID();
        //Makes movie for db
        String newLIne = id + "," + year + "," + title;

        //Append new Line ussing java NIO
        Files.write(pathToFile, ("\r\n" + newLIne).getBytes(), APPEND);

        return new Movie(id, year, title);
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {

    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {

    }

    private int getNextID() throws IOException {
        List<Movie> movies = getAllMovies();

        Movie lastMovie = movies.get(movies.size()-1);
        return lastMovie.getId() + 1;
    }






    /*
    public List<Movie> getAllMovies() {
        List<Movie> allMovieList = new ArrayList<>();

        File moviesFile = new File(MOVIES_FILE);


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(moviesFile))) {
            boolean hasLines = true;
            while (hasLines) {
                String line = bufferedReader.readLine();
                hasLines = (line != null);
                if (hasLines && !line.isBlank())
                {
                    String[] separatedLine = line.split(",");

                    int id = Integer.parseInt(separatedLine[0]);
                    int year = Integer.parseInt(separatedLine[1]);
                    String title = separatedLine[2];
                    if(separatedLine.length > 3)
                    {
                        for(int i = 3; i < separatedLine.length; i++)
                        {
                            title += "," + separatedLine[i];
                        }
                    }
                    Movie movie = new Movie(id, title, year);
                    allMovieList.add(movie);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allMovieList;
    }
    */


}
package services.search;

import models.Movie;
import models.Show;
import models.ShowSeat;
import models.Theatre;
import repositories.movie.MovieRepository;
import repositories.show.ShowRepository;
import repositories.showseat.ShowSeatRepository;
import repositories.theatre.TheatreRepository;

import java.util.List;

public class SearchService {
    private final TheatreRepository theatreRepository;
    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;

    public SearchService(TheatreRepository theatreRepository, MovieRepository movieRepository,
                         ShowRepository showRepository, ShowSeatRepository showSeatRepository) {
        this.theatreRepository = theatreRepository;
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
    }

    public List<Theatre> getTheatresByCity(String city) {
        return theatreRepository.findByCity(city);
    }

    public List<Movie> getMoviesByCity(String city) {
        return movieRepository.findMoviesPlayingInCity(city);
    }

    public List<Show> getShowsForMovieInCity(String movieId, String city) {
        return showRepository.findByMovieAndCity(movieId, city);
    }

    public List<ShowSeat> getSeatAvailabilityForShow(String showId) {
        return showSeatRepository.findByShowId(showId);
    }
}
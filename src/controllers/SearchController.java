package controllers;

import dto.MovieResponse;
import dto.SeatAvailabilityResponse;
import dto.ShowResponse;
import models.Movie;
import models.ShowSeat;
import models.Theatre;
import services.search.SearchService;

import java.util.List;
import java.util.stream.Collectors;

public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    public List<Theatre> getTheatresByCity(String city) {
        return searchService.getTheatresByCity(city);
    }

    public List<MovieResponse> getMoviesByCity(String city) {
        List<Movie> movies = searchService.getMoviesByCity(city);
        return movies.stream()
                .map(m -> new MovieResponse(m.getId(), m.getTitle(), m.getLanguages(), m.getGenres()))
                .collect(Collectors.toList());
    }

    public List<ShowResponse> getShows(String movieId, String city) {
        return searchService.getShowsForMovieInCity(movieId, city).stream()
                .map(s -> new ShowResponse(
                        s.getId(),
                        "Fetched_Theatre_Name",
                        "Fetched_Screen_Name",
                        s.getStartTime()))
                .collect(Collectors.toList());
    }

    public List<SeatAvailabilityResponse> getSeatAvailability(String showId) {
        List<ShowSeat> seats = searchService.getSeatAvailabilityForShow(showId);
        return seats.stream()
                .map(s -> new SeatAvailabilityResponse(
                        s.getId(),
                        s.getSeat().getSeatLabel(),
                        200.0,
                        s.getStatus()))
                .collect(Collectors.toList());
    }
}
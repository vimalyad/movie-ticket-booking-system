package seeder;

import enums.SeatType;
import models.*;
import repositories.movie.InMemoryMovieRepository;
import repositories.show.InMemoryShowRepository;
import repositories.showseat.InMemoryShowSeatRepository;
import repositories.theatre.InMemoryTheatreRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DemoDataSeeder {

    public static class SeederContext {
        public String city;
        public String movieId;
        public String showId;
        public List<String> regularSeatIds = new ArrayList<>();
        public List<String> reclinerSeatIds = new ArrayList<>();
    }

    public static SeederContext seed(
            InMemoryTheatreRepository theatreRepo,
            InMemoryMovieRepository movieRepo,
            InMemoryShowRepository showRepo,
            InMemoryShowSeatRepository showSeatRepo) {

        SeederContext context = new SeederContext();
        context.city = "Bengaluru";

        Movie movie1 = new Movie("Dune: Part Two", List.of("English"), List.of("Sci-Fi", "Action"), 166, LocalDate.of(2024, 3, 1));
        movieRepo.save(movie1);
        movieRepo.mapMovieToCity(movie1.getId(), context.city);
        context.movieId = movie1.getId();

        Theatre theatre = new Theatre("PVR Director's Cut", context.city);
        Screen screen1 = new Screen(theatre.getId(), "Screen 1 (IMAX)");
        theatre.addScreen(screen1);
        theatreRepo.save(theatre);

        LocalDateTime showTime = LocalDateTime.now().plusDays(1).withHour(19).withMinute(0);
        Show show1 = new Show(movie1.getId(), screen1.getId(), showTime);
        showRepo.save(show1);
        context.showId = show1.getId();

        List<ShowSeat> showSeats = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Seat regularSeat = new Seat("A", i, SeatType.REGULAR);
            ShowSeat ss = new ShowSeat(regularSeat, show1.getId());
            showSeats.add(ss);
            context.regularSeatIds.add(ss.getId());
        }

        for (int i = 1; i <= 2; i++) {
            Seat reclinerSeat = new Seat("B", i, SeatType.RECLINER);
            ShowSeat ss = new ShowSeat(reclinerSeat, show1.getId());
            showSeats.add(ss);
            context.reclinerSeatIds.add(ss.getId());
        }

        showSeatRepo.saveAll(showSeats);

        System.out.println("✅ [SYSTEM] Initial Database Seeded with Movies, Theatres, and Seats.");
        return context;
    }
}
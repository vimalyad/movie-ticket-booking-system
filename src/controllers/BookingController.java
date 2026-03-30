package controllers;

import dto.BookingRequest;
import dto.BookingResponse;
import dto.PaymentRequest;
import services.booking.BookingFacade;

public class BookingController {
    private final BookingFacade bookingFacade;

    public BookingController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    public BookingResponse createBooking(BookingRequest request) {
        return bookingFacade.initiateBooking(request);
    }

    public String confirmBooking(String bookingId, PaymentRequest paymentRequest) {
        return bookingFacade.confirmBooking(bookingId, paymentRequest);
    }

    public String cancelBooking(String bookingId, String customerId) {
        return bookingFacade.cancelBooking(bookingId, customerId);
    }
}
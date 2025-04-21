package tech.buildrun.cucumber.service;

import io.restassured.response.Response;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import tech.buildrun.cucumber.config.RestConfig;
import tech.buildrun.cucumber.dto.BookingRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class BookService {

    private RestConfig rest;

    public BookService(RestConfig rest) {
        this.rest = rest;
    }

    public Response bookRoom(Long roomId, LocalTime startTime, LocalTime endTime) {

        var today = LocalDate.now();
        var startDateTime = LocalDateTime.of(today, startTime);
        var endDateTime = LocalDateTime.of(today, endTime);


        var request = new BookingRequest(roomId, startDateTime, endDateTime);

        var response = rest.givenBackend()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/bookings");

        return response;
    }
}

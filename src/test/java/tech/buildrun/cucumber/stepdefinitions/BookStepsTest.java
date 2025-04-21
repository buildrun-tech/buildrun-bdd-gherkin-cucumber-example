package tech.buildrun.cucumber.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import tech.buildrun.cucumber.config.RestConfig;
import tech.buildrun.cucumber.config.ScenarioContext;
import tech.buildrun.cucumber.service.BookService;

import java.time.LocalTime;

public class BookStepsTest {

    private final RestConfig rest;
    private final ScenarioContext scenarioContext;
    private final BookService bookService;

    public BookStepsTest(RestConfig rest,
                         ScenarioContext scenarioContext,
                         BookService bookService) {
        this.rest = rest;
        this.scenarioContext = scenarioContext;
        this.bookService = bookService;
    }

    @And("the room has no bookings for today")
    public void theRoomHasNoBookingsForToday() {
        var roomId = scenarioContext.get("roomId", Long.class);

        rest.givenBackend()
                .queryParam("room_id", roomId)
                .delete("/test-utils/bookings")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @And("one user book the room for one hour from now")
    @When("I book the room for one hour from now")
    public void bookTheRoomFromStartTimeToEndTime() {
        var roomId = scenarioContext.get("roomId", Long.class);

        var response = bookService.bookRoom(roomId, LocalTime.now().plusHours(1), LocalTime.now().plusHours(2));

        scenarioContext.put("response", response);
    }

    @And("I get the booking number")
    public void getBookingNumber() {
        var response = scenarioContext.get("response", Response.class);

        scenarioContext.put("bookingId", response.getBody().jsonPath().getLong("id"));
    }

    @And("I book the room for right now")
    public void iBookedTheRoomForTodayAnHourAgo() {
        var roomId = scenarioContext.get("roomId", Long.class);

        var response = bookService.bookRoom(
                roomId,
                LocalTime.now().plusSeconds(3),
                LocalTime.now().plusHours(1)
        );

        scenarioContext.put("response", response);
        scenarioContext.put("bookingId", response.getBody().jsonPath().getLong("id"));
    }

    @Then("the room should be successfully booked")
    public void theRoomShouldBeSuccessfullyBooked() {
        var response = scenarioContext.get("response", Response.class);

        response.then()
                .statusCode(200);
    }

    @Then("the booking should conflict")
    public void theBookingShouldConflict() {
        var response = scenarioContext.get("response", Response.class);

        response.then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

}

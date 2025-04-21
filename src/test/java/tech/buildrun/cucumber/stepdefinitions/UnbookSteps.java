package tech.buildrun.cucumber.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import tech.buildrun.cucumber.config.RestConfig;
import tech.buildrun.cucumber.config.ScenarioContext;

public class UnbookSteps {

    private final RestConfig rest;
    private final ScenarioContext scenarioContext;

    public UnbookSteps(RestConfig rest, ScenarioContext scenarioContext) {
        this.rest = rest;
        this.scenarioContext = scenarioContext;
    }


    @When("I try to unbook the room")
    public void iTryToUnbookTheRoom() {
        var bookingId = scenarioContext.get("bookingId", Long.class);

        var response = rest.givenBackend()
                .pathParam("bookingId", bookingId)
                .delete("/bookings/{bookingId}");

        scenarioContext.put("response", response);
    }

    @Then("the room should be successfully unbooked")
    public void theRoomShouldBeSuccessfullyUnbooked() {
        var response = scenarioContext.get("response", Response.class);

        response.then()
                .statusCode(200);
    }

    @Then("the room cannot be unbooked")
    public void theRoomCannotBeUnbooked() {
        var response = scenarioContext.get("response", Response.class);

        response.then()
                .statusCode(422);
    }

    @And("the booking time has already passed")
    public void passedTheTimeFromTheBooking() throws InterruptedException {
        Thread.sleep(5000);
    }
}

package tech.buildrun.cucumber.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import tech.buildrun.cucumber.config.RestConfig;
import tech.buildrun.cucumber.config.ScenarioContext;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class RoomSteps {

    private ScenarioContext scenarioContext;
    private RestConfig restConfig;

    public RoomSteps(ScenarioContext scenarioContext,
                     RestConfig restConfig) {
        this.scenarioContext = scenarioContext;
        this.restConfig = restConfig;
    }

    @When("i list all the rooms")
    public void iListAllTheRooms() {
        var response = restConfig.givenBackend()
                .contentType(APPLICATION_JSON_VALUE)
                .get("/rooms");

        scenarioContext.put("response", response);
    }

    @Then("we detect that the room {string} exists")
    public void weDetectThatTheRoomNameExists(String name) {
        var response = scenarioContext.get("response", Response.class);

        var body = response.then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<Map<String, String>>>() {});

        assertTrue(body.stream()
                .anyMatch(i -> i.get("name").equalsIgnoreCase(name)));

    }

    @Given("the room {string} exists")
    public void theRoomExists(String roomName) {

        var response = restConfig.givenBackend()
                .get("/rooms");

        var body = response.then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<Map<String, String>>>() {});

        var room = body.stream()
                .filter(i -> i.get("name").equalsIgnoreCase(roomName))
                .findFirst();

        assertTrue(room.isPresent(), "Room not found");
        scenarioContext.put("roomId", Long.parseLong(room.get().get("id")));
    }
}

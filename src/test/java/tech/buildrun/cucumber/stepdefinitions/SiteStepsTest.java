package tech.buildrun.cucumber.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import tech.buildrun.cucumber.config.ScenarioContext;

public class SiteStepsTest {

    private final ScenarioContext scenarioContext;

    public SiteStepsTest(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Given("I want to access {string}")
    public void iWantToAccess(String url) {
        scenarioContext.put("url", url);
    }

    @When("I access this website")
    public void iAccessThisWebsite() {
        String url = scenarioContext.get("url", String.class);

        Response response = RestAssured.given()
                .baseUri(url)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter())
                .get();

        scenarioContext.put("response", response);
    }

    @Then("the website is correctly loaded")
    public void theWebsiteIsCorrectlyLoaded() {
        Response response = scenarioContext.get("response", Response.class);

        response.then()
                .statusCode(200);
    }
}

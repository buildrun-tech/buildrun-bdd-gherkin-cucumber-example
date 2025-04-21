package tech.buildrun.cucumber.config;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.springframework.stereotype.Component;

@Component
public class RestConfig {

    private static PropertiesConfig propertiesConfig;

    public RestConfig(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }

    public RequestSpecification givenBackend() {
        return RestAssured.given()
                .baseUri(propertiesConfig.getBackendUrl())
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}

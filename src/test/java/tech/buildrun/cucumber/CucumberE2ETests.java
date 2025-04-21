package tech.buildrun.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestContextManager;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "tech.buildrun.cucumber")
public class CucumberE2ETests {


}

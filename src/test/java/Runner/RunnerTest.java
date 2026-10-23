package Runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/orangehrm-pim.feature",
        // features = "src/test/resources/features/login.feature",
        glue = {"StepDefinition"},
        plugin = {"pretty", "html:target/cucumber-report.html", "Runner.ExitCodePlugin"},
        monochrome = true,
        tags = "@TC-005"
)

public class RunnerTest {
}

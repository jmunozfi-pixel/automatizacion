package Runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/leave.feature",
        glue = {"StepDefinition"},
        plugin = {"pretty", "html:target/cucumber-leave-report.html", "Runner.ExitCodePlugin"},
        monochrome = true,
        tags = "@TLEAVE-001"
)

public class RunnerTest {
}

package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features"   ,
        glue = "step_def",
        dryRun = false,
        snippets = CucumberOptions.SnippetType.UNDERSCORE



)
public class HomeworkRunners {

}

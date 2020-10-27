package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/Features"},
        glue = "",
        plugin = {
                "html:target/htmlreports",
                "json:target/jsonreports/report.json",
                "rerun:target/rerun.txt"
        },
        tags = {"@Scenario1,@Scenario2"},
        monochrome = true,
        dryRun = false,
        strict = true
)
public class TestRunner {
}
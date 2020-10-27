import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.junit.AfterClass;

public class CucumberReports {
    @AfterClass
    public static void main(String[] args) {
        try {

            String RootDir = System.getProperty("user.dir");
            File reportOutPutDirectory = new File("target/CucumberReports");
            List<String> jsonFiles = new ArrayList<>();
            jsonFiles.add("target/jsonreports/report.json");

            String buildProject = "TestProject";
            Configuration configuration = new Configuration(reportOutPutDirectory,buildProject);
            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles,configuration);
            reportBuilder.generateReports();
        } catch (Exception e) {

        }
    }
}

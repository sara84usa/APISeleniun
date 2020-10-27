package steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.json.Ad;
import com.test.json.Datum;
import com.test.json.PostSchema;
import com.test.json.Test;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


import static io.restassured.RestAssured.given;


public class MyStepdefs {
    private static String getUrl = "";
    private static String postURL = "";
    private static String result = "";
    private static String jsonReq = "";
    public static WebDriver driver;

    @Given("^get the \"([^\"]*)\" for testing$")
    public void getTheForTesting(String url) throws Throwable {
        getUrl = url;
    }

    @Then("^hit the url and validate the response$")
    public void hitTheUrlAndValidateTheResponse() throws IOException {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType(ContentType.JSON);
        RequestSpecification reqSpec = builder.build();
        Response response = given().spec(reqSpec).when().get(getUrl);
        System.out.println("Response -->>" + response.getStatusCode());
        result = response.getBody().asString();
        System.out.println("Result->" + result);

        JsonPath jsp = response.jsonPath();
        int count = Integer.parseInt(jsp.get("per_page").toString());

        List<String> firstName = jsp.get("data.first_name");

        for(String name: firstName) {
            System.out.println("Name-->>" + name);
        }

    }

    @Then("^write the response in the output folder$")
    public void writeTheResponseInTheOutputFolder() throws Exception {
        FileWriter frw = new FileWriter("E:\\OCT26\\Framework\\JsonOut\\JsonResponse.json");
        frw.write(result);
        frw.flush();
    }

    @Then("^validate the \"([^\"]*)\" and \"([^\"]*)\"$")
    public void validateTheAnd(String firstName, String lastName) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Test> reference1 = new TypeReference<Test>() {
        };
        InputStream in1 = new FileInputStream("E:\\OCT26\\Framework\\JsonOut\\JsonResponse.json");
        Test test = mapper.readValue(in1, reference1);
        int size = Integer.parseInt(test.getPerPage().toString());

        for (int i = 0; i < size; i++) {
            System.out.println(test.getData().get(i).getFirstName());

            if (firstName.equalsIgnoreCase(test.getData().get(i).getFirstName())) {
                Assert.assertEquals(firstName, test.getData().get(i).getFirstName());
                Assert.assertEquals(lastName, test.getData().get(i).getLastName());
            }
        }
    }

    @Given("^get the post \"([^\"]*)\" for testing$")
    public void getThePostForTesting(String postUrl) throws Throwable {
        postURL = postUrl;
    }

    @Then("^post the url and validate the response$")
    public void postTheUrlAndValidateTheResponse() throws IOException {
        jsonReq = new String(Files.readAllBytes(Paths.get("E:\\OCT26\\Framework\\src\\test\\resources\\Input\\input.json")));
        System.out.println("JsonReq ->" + jsonReq);
        System.out.println("PostUrl-->>" + postURL);
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBody(jsonReq);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
         Response response = given().contentType(ContentType.JSON).body(jsonReq).post(postURL);
         Assert.assertEquals(200,response.getStatusCode());
         String results = response.getBody().asString();

        JsonPath jsp = response.jsonPath();
        jsp.get("status");

        System.out.println("JsResponse--->>" + jsp.get("status"));
        System.out.println("JsResponse--->>" + jsp.get("data.name"));
        System.out.println("JsResponse--->>" + jsp.get("data.salary"));
        System.out.println("JsResponse--->>" + jsp.get("data.age"));
        FileWriter frw = new FileWriter("E:\\OCT26\\Framework\\JsonOut\\PostResponse.json");
        frw.write(results);
        frw.flush();


        ObjectMapper mapper = new ObjectMapper();
        TypeReference<PostSchema> reference1 = new TypeReference<PostSchema>() {
        };
        InputStream in1 = new FileInputStream("E:\\OCT26\\Framework\\JsonOut\\PostResponse.json");

        PostSchema postSchema = mapper.readValue(in1,reference1);
        System.out.println(postSchema.getMessage());
    }

    @Given("^Launch the chrome browser and navigate to \"([^\"]*)\"$")
    public void launch_the_chrome_browser_and_navigate_to(String url)  {
        System.setProperty("webdriver.chrome.driver","E:\\OCT26\\Framework\\src\\test\\resources\\driver\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.navigate().to(url);
    }

    @Then("^search for the samsung phone \"([^\"]*)\"$")
    public void searchForTheSamsungPhone(String phone) throws Throwable {
        driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
        driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys(phone);
        driver.findElement(By.xpath("//div[@class='nav-search-submit nav-sprite']")).click();
        driver.findElement(By.xpath("(//span[@class='a-size-medium a-color-base a-text-normal'])[1]")).click();
        driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
        driver.findElement(By.xpath("//input[@id='add-to-cart-button']")).click();

//        driver.findElement(By.xpath("//button[@id='siNoCoverage-announce']")).click();


    }
}

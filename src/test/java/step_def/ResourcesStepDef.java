package step_def;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.Json;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import utils.ConfigReader;

import java.util.List;
import java.util.Map;

public class ResourcesStepDef {
    Response response;

    @Given("user has List Resources endpoint")
    public void user_has_list_resources_endpoint() {
        RestAssured.baseURI = ConfigReader.readProperty("baseURI");
        RestAssured.basePath = ConfigReader.readProperty("basePathResource");
    }

    @When("user sends GET request to List Resources")
    public void user_sends_get_request_to_list_resources() {
        response = RestAssured.given().accept(ContentType.JSON).
                queryParam("per_page", "12").
                when().get();
    }

    @Then("user validate status code {int}")
    public void user_validate_status_code(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @Then("user validate Sum of ids equals to {int}")
    public void user_validate_sum_of_ids_equals_to(int sumOfId) {
        List<Map<String, Object>> deserializeResponse = response.jsonPath().getList("data");
        int actualIdSum = 0;
        for (Map<String, Object> resourceInfo : deserializeResponse) {
            actualIdSum += (int) resourceInfo.get("id");
        }
        Assert.assertEquals(sumOfId, actualIdSum);


    }

    @Then("user validate Avg of years equals to {double}")
    public void user_validate_avg_of_years_equals_to(double avgOfYears) {
        List<Map<String, Object>> deserializeResponse = response.jsonPath().getList("data");
        double actualAvgOfYears = 0;
        for (Map<String, Object> resourceInfo : deserializeResponse) {
            actualAvgOfYears += (int) resourceInfo.get("year");
        }
        double delta = 0.01;
        actualAvgOfYears /= deserializeResponse.size();
        Assert.assertEquals(avgOfYears, actualAvgOfYears, delta);
    }


    /**Single Resource**/
    @When("user sends GET request with {int} to obtain resource info")
    public void user_sends_get_request_with_to_obtain_resource_info(int id) {
        response = RestAssured.given().accept(ContentType.JSON).
                when().get(String.valueOf(id));
    }
    @Then("validate response body {int}, {int}, {string}, {string}")
    public void validate_response_body(int id, int year, String color, String support) {
        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(id,jsonPath.getInt("data.id"));
        Assert.assertEquals(year,jsonPath.getInt("data.year"));
        Assert.assertEquals(color,jsonPath.getString("data.color"));
        Assert.assertEquals(support,jsonPath.getString("support.text"));

    }

    /**Resource not found **/
    @When("user sends GET request with no existing id {int}")
    public void user_sends_get_request_with_no_existing_id(int id) {
        response = RestAssured.given().accept(ContentType.JSON).
                when().get(String.valueOf(id));
    }
    @Then("user validate empty response body")
    public void user_validate_empty_response_body() {
        Map<String,Object> body = response.as(new TypeRef<Map<String, Object>>() {
        });
        Assert.assertTrue(body.isEmpty());
    }




}

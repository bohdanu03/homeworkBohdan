package step_def;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.Json;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import pojo.SingleUserDataPojo;
import utils.ConfigReader;

import java.util.*;

public class UserSetDef {
    Response response;

    @Given("client has  Users endpoint")
    public void client_has_list_users_endpoint() {
        RestAssured.baseURI = ConfigReader.readProperty("baseURI");
        RestAssured.basePath = ConfigReader.readProperty("basePath");
    }

    @When("client sends GET request to List Users")
    public void client_sends_get_request_to_list_users() {
        response = RestAssured.given().accept(ContentType.JSON).
                queryParam("per_page", "12").
                when().get();
    }

    @Then("client validate status code {int}")
    public void client_validate_status_code(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }


    @Then("client validate users count,All users names, ids, avatar URLs:")
    public void client_validate_all_users_names_ids_avatar_ur_ls(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> expectedUserInfo = dataTable.asMaps();
        List<Map<String, Object>> deserializeResponse = response.jsonPath().getList("data");
        LinkedHashMap<String, String> userInfo = new LinkedHashMap<>();
        for (int i = 0; i < deserializeResponse.size(); i++) {
            //casting
            Map<String, String> actualUserInfo = new HashMap<>();
            deserializeResponse.get(i).forEach((key, value) -> actualUserInfo.put(key, String.valueOf(value)));
            //validate single user info
            Assert.assertEquals(expectedUserInfo.get(i), actualUserInfo);
            //adding user first,LastName,email to new userInfo map
            userInfo.put(actualUserInfo.get("first_name") + " " + actualUserInfo.get("last_name"), actualUserInfo.get("email"));
        }
        //validate number of users
        Assert.assertEquals(response.jsonPath().getInt("total"), userInfo.size());
    }


    /**
     * single User
     **/
    @When("client sends GET request with {int} to obtain user info")
    public void client_sends_get_request_with_to_obtain_user_info(int id) {
        response = RestAssured.given().accept(ContentType.JSON).
                when().get(String.valueOf(id));
    }

    @When("client validate supportURL {string}")
    public void client_validate_support_url(String supportURL) {
        Assert.assertEquals(supportURL, response.path("support.url"));
    }

    @When("client validate user {int},{string},{string},{string}:")
    public void client_validate_user(int id, String first_name, String last_name, String avatar) {
        SingleUserDataPojo deserializeResponse = response.as(SingleUserDataPojo.class);
        Assert.assertEquals(id, deserializeResponse.getData().getId());
        Assert.assertEquals(first_name, deserializeResponse.getData().getFirst_name());
        Assert.assertEquals(last_name, deserializeResponse.getData().getLast_name());
        Assert.assertEquals(avatar, deserializeResponse.getData().getAvatar());
    }


    /**
     * Single User Not Found API
     */
    @When("client sends GET request with no existing id {int}")
    public void client_sends_get_request_with_no_existing_id(int id) {
        response = RestAssured.given().accept(ContentType.JSON).
                when().get(String.valueOf(id));
    }

    @Then("client validate empty response body")
    public void client_validate_empty_response_body() {
       Map<String,Object> body = response.as(new TypeRef<Map<String, Object>>() {
       });
       Assert.assertTrue(body.isEmpty());
    }


}

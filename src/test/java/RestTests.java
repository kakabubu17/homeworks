import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class RestTests {

    private String baseUrl = "https://petstore.swagger.io/v2";
    private String userEndPoint = "/user";

    RequestSpecification reqSpec = new RequestSpecBuilder()
            .setBaseUri(baseUrl)
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .build();

    @Epic(value = "RestTest")
    @Story(value = "Отправляем запросы")
    @Description(value = "")
    @Test
    public void testGet() {
                        RestAssured.when()
                        .get("http://httpbin.org/get?a=1")
                        .then().assertThat().statusCode(200)
                        .body("args.a", equalTo("1"))
                                .log();
    }

    @Test
    public void createValidUser() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", "85466");
        requestBody.put("username", "MilaJackson");
        requestBody.put("firstName", "Mila");
        requestBody.put("lastName", "Jackson");
        requestBody.put("email", "Jackson@fmail.com");
        requestBody.put("password", "Jackson11");
        requestBody.put("phone", "895654877889");
        requestBody.put("userStatus", "1");
        Response resp = given()
                .spec(reqSpec)
                .body(requestBody)
                .post(userEndPoint)
                .then().extract().response();
        Assertions.assertEquals(200, resp.getStatusCode());
        System.out.print(resp);

    }

    @Test
    public void getValidUser() {
        Response resp = given()
                .spec(reqSpec)
                .get(userEndPoint+"MilaJackson")
                .then().extract().response();
        System.out.print(resp);
        Assertions.assertEquals(200, resp.getStatusCode());


    }


}

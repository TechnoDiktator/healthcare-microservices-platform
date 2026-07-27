import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest {



    //so we are simulating hitting the dockerized gateway as a frontend user
    //so these tests do not run inside a docker container
    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void shouldReturnOkWithValidationToken(){
        //three phases of an inegration test
        //1. Arrange
        //2. Act
        //3. Assert

        String loginPayload = """
                {
                    "email":"testuser@test.com",
                    "password": "password123"
                }
                
                """;
        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token" , notNullValue())
                .extract()
                .response();

        System.out.println("generated Token " + response.jsonPath().getString("token"));


    }


    @Test
    public void shouldReturnUnauthorizedWithInValidLogin(){
        //three phases of an inegration test
        //1. Arrange
        //2. Act
        //3. Assert

        String loginPayload = """
            {
                "email":"invalidemailr@test.com",
                "password": "invalidpassword"
            }
            
            """;
        given()
        .contentType("application/json")
        .body(loginPayload)
        .when()
        .post("/auth/login")
        .then()
        .statusCode(401);

        System.out.println("Invalid Login Test successful");


    }








}

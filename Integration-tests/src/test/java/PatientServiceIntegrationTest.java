import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PatientServiceIntegrationTest {


    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:4004";
    }


    @Test
    public void shouldReturnAllPatientsWhenValidTokenIsSent(){
        //1. do a successfule login

        // this is a seeded user that we seed into the database at application start . so it is a valid one
        String loginPayload = """
                {
                    "email":"testuser@test.com",
                    "password": "password123"
                }
                
                """;

        String token = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200).extract()
                .jsonPath().get("token");

        Response response = given().header("Authorization", "Bearer " + token)
                .when()
                .get("/api/patients")
                .then()                 // then keyword / function is used for assertion
                .statusCode(200)
                .body("patients" , notNullValue()).extract().response();

        System.out.println(response.asPrettyString());




    }




}

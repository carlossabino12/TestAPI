import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import static io.restassured.RestAssured.*;


public class PostLoginTests extends TestBase{

    private static User validUser;
    private static User invalidUser;


    @BeforeClass
    public static void generateTestData(){
        validUser = new User("Cicero", "cicero@gmail.com", "789", "true");
        validUser.registerUserRequest();
        invalidUser = new User("Polly", "polly@gmail.com", "987", "true");
    }

    @Test
    public void shouldReturnSuccessMessageAuthTokenAndStatus200(){

        Response loginResponse = validUser.authenticateUser();
        loginResponse.then().assertThat().
                    statusCode(200).
                    body("message", equalTo("Login realizado com sucesso"));

    }

    //    @Test
    //    public void shouldReturnSuccessMessageAuthTokenAndStatus200(){
    //
    //        String credencials = validUser.getUserCredentials();
    //
    //        given().
    //                spec(SPEC).
    //                header("Content-Type", "application/json").
    //                and().
    //                body(credencials).
    //                when().
    //                post("login").
    //
    //                then().
    //                assertThat().
    //                statusCode(200).
    //                body("message", equalTo("Login realizado com sucesso")).
    //                body("authorization", startsWith("Bearer")).log().body();
    //        //body("authorization", notNullValue());
    //
    //    }


    @Test
    public void shouldReturnFailMessageAndStatus401(){

        String credencials = validUser.getUserCredentials();

        given().
                spec(SPEC).
                header("Content-Type", "application/json").
        and().
                body(credencials).
        when().
                post("login").

        then().
                assertThat().
                statusCode(401).
                body("message", equalTo("Email e/ou senha inválidos")).
                body("authorization", nullValue());

    }
}

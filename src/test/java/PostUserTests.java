import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class PostUserTests extends TestBase {

    private static User validUser;
    private static User invalidUser;

    @BeforeClass
    public static void generateTestData(){
        validUser = new User("CarlosTest", "carlostest@gmail.com", "1234", "true");
        validUser.registerUserRequest();
        invalidUser = new User("Jose", "jose@gmail.com", "546", "true");

    }

    @Test
    public void shouldReturnSucessMessageAuthTokenAndStatus200() {
        String json =
                "{\"nome\": \"CarlosTest\","+
                "  \"email\": \"carlostest@gmail.com\"," +
                "  \"password\": \"1234\"," +
                "  \"administrador\": \"true\"}";

        given()
                .spec(SPEC)
                .header("Content-Type","application/json").
        and()
                .body(json).
       when()
                .post("usuarios").
       then()
                .assertThat()
                .statusCode(201)
                .body("message",equalTo("Cadastro realizado com sucesso"));

    }
}

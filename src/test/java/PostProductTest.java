import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import jdk.nashorn.internal.parser.JSONParser;
import models.Products;
import models.User;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostProductTest extends TestBase {

    private static Products products;
    private static User validUser;
    private String token;


    @BeforeClass
    public void dadosLogin(){
        validUser = new User("CarlosTest","carlostest@gmail.com","1234","true");
        validUser.registerUserRequest();


    }
    @BeforeClass
    public void loginAndGetToken(){
        Response loginResponse = validUser.authenticateUser();
        token = loginResponse.getBody().jsonPath().get("authorization").toString();
    }

    public void newProducts(){
        products =  new Products("Xiaomi Redmi note 8","800","Smartphone", "11");

    }

    @Test
    public void shouldReturnSucessMessageAuthTokenAndStatus201() {
        String json =
                "{\"nome\": \"Xiaomi Redmi note 7\","+
                "  \"preco\": \"900\"," +
                "  \"descricao\": \"Smartphone\"," +
                "  \"quantidade\": \"10\"}";

        given()
                .spec(SPEC)
                .header("Content-Type","application/json")
                .header(            "Authorization", token).
        and()
                .body(json).
        when()
                .post("produtos").
        then()
                .assertThat()
                .statusCode(201)
                .body("message",equalTo("Cadastro realizado com sucesso"));
    }

}

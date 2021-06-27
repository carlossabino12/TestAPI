import io.restassured.response.Response;
import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

public class DeleteUserTests extends TestBase{

    private static User validUser;
    //private static User invalidUser;


    @BeforeClass
    public static void generateTestData(){
        validUser = new User("Dudu", "dudu@gmail.com", "159", "true");
        validUser.registerUserRequest();

    }
    @Test
    public void shouldRemoveUserAndReturnSuccessMessageAndStatus200(){

       Response deleteUserResponse = validUser.deleteUserRequest(SPEC);
       deleteUserResponse.

       then().
                assertThat().
                statusCode(200).
                body("message", equalTo("Registro excluído com sucesso"));

       }
    }

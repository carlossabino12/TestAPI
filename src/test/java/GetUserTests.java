import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class GetUserTests extends TestBase{

    private static User validUser1;
    private static User validUser2;
    private static User validUser3;
    private static User invalidUser1;

    @BeforeClass
    public static void generateTestData(){

        validUser1 = new User("Maria", "maria@gmail.com", "123456", "true");
        validUser1.registerUserRequest();
        validUser2 = new User("Carlos", "carlos@gmail.com", "123456", "true");
        validUser2.registerUserRequest();
        validUser3 = new User("Maria", "maria2@gmail.com", "12145", "true");
        validUser3.registerUserRequest();
        invalidUser1 = new User("Anne", "anne@gmail.com", "blabla", "0");

    }

    @AfterClass
    public void removeTestData(){
        validUser1.deleteUserRequest(SPEC);
        validUser2.deleteUserRequest(SPEC);
        validUser3.deleteUserRequest(SPEC);

    }

    @DataProvider(name = "usersData")
    public Object[][] createTestData(){
        return new Object[][]{
                {"?nome=" + validUser1.name, 2},
                {"?password=" + validUser2.password, 2},
                {"?email=" + validUser3.email, 1},
                {"?nome=" + invalidUser1.name + "&email=" + invalidUser1.email, 0}
        };
    }

    //    @DataProvider(name = "usersData")
    //    public Object[][] createTestData(){
    //        return new Object[][]{
    //                {"?nome=Fulano da Silva", 1},
    //                {"?password=teste", 1}
    //        };
    //    }

    @Test(dataProvider= "usersData")
    public void shouldReturnALlUsersAndStatus200(String query, int totalUsers){

        given().
               spec(SPEC).
        when().

                get("usuarios" + query).

        then().
                assertThat().
                statusCode(200).
                body("quantidade", equalTo(totalUsers));


    }

}

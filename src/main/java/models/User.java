package models;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


public class User {

    public String name;
    public String email;
    public String password;
    public String isAdmin;
    public String authToken;
    public String userID;

    public User(String name, String email, String password, String isAdmin){
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;

    }

    public Response authenticateUser(){

        Response loginResponse =
                given().
                        header("accept", "application/json").
                        header("Content-Type", "application/json").
                        and().
                        body(getUserCredentials()).
                        when().post("http://localhost:3000/login");

        JsonPath jsonPathEvaluator = loginResponse.jsonPath();
        setUserAuthToken(jsonPathEvaluator.get("authorization"));

        return loginResponse;


    }
    public void setUserID(String userID){
        this.userID = userID;

    }

    public void setUserAuthToken(String authToken){
        this.authToken = authToken;

    }

    public String getUserCredentials(){

        JSONObject userJsonRepreentation = new JSONObject();
        userJsonRepreentation.put("email", this.email);
        userJsonRepreentation.put("password", this.password);
        return userJsonRepreentation.toJSONString();
    }

    public Response registerUserRequest(){

        JSONObject userJsonRepresentation = new JSONObject();
        userJsonRepresentation.put("nome", this.name);
        userJsonRepresentation.put("email", this.email);
        userJsonRepresentation.put("password", this.password);
        userJsonRepresentation.put("administrador", this.isAdmin);

        Response registerUserRequest =
                given().
                    header("accept", "application/json").
                    header("Content-Type", "application/json").
                and().
                    body(userJsonRepresentation.toJSONString()).
                when().
                    post("http://localhost:3000/usuarios");

        JsonPath jsonPathEvaluator = registerUserRequest.jsonPath();
        setUserID(jsonPathEvaluator.get("_id"));

        return registerUserRequest;
    }

    public Response deleteUserRequest(RequestSpecification spec){

        Response deleteUserResponse =
        given().
                spec(spec).
                header("Content-Type", "application/json").

        when().
                delete("usuarios/" + this.userID);

        return deleteUserResponse;
    }
}




package RestAPITesting;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
/*
public class API_Utils {

    public static Response response;
    public static JsonPath jPath;

    public static void main(String... args){
        String authToken = OAuth2Client.getAccessTokenForCredentials();
    }

    public String getValidAuthToken(){
        return OAuth2Client.getAccessTokenForCredentials();
    }

    public Response getResponseForAPIRequest(String URL, String payload, String authToken){

        response  = given()
                .auth().oauth2(authToken)
                .header("Content-Type", application/json)
                .body(payload)
                .when()
                .post(URL);

        return response;
    }

    public String getExpStringResponseData(Response res, String jsonPath){
        jPath = res.jsonPath();
        return jPath.get(jsonPath).toString();
    }

}
*/
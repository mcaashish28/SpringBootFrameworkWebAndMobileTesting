package RestAPITesting;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

public class RestAPIFunctions {

    private static Response resp;
    public static final String OrderID = "orderid";
    public static String TransactionPayload;
    public static String UAT_URL = "https://uat.apiurl.com/order";  // enter api url here
    public static String generateAuthToken;
  //  private static API_Utils apiUtils = new API_Utils();


    public static void main(String[] args){
        RestAPIFunctions api = new RestAPIFunctions();
        // assume these value picked from Excel and pass here
        api.createTransactionWithAPI("Env_Val", "AccNum", "ProductCode", "Amount");
    }   // end of main



    // API Method call
    public String createTransactionWithAPI(String Env_Val, String AccNum, String ProductCode, String Amount){
        String TransactionID = "";

        try{
            setAPIPayLoad(Env_Val, AccNum, ProductCode, Amount);
          //  generateAuthToken();
            APIRequestAndResponse(Env_Val);
           // ValidateSuccessResponseCodeOfAPI();
           // TransactionID = apiUtils.getExpStringResponseData(resp, OrderID);

        }catch(Exception e){
            System.out.println("API Exception while processing : " + e.getMessage());
        }


        return TransactionID;
    }   // end of function - createTransactionWithAPI


    // Create Payload and store in public variable
    public static void setAPIPayLoad(String Env_Val, String AccNum, String ProductCode, String Amount){

        // generate Random number
        String RamdomNumber = RandomStringUtils.randomAlphanumeric(10).toLowerCase();
        // create payload in Json format
        TransactionPayload = "{\n" +
                " \"Env_Val\" : \""+Env_Val+"\",\n" +
                " \"AccNum\" : \""+AccNum+"\",\n" +
                " \"ProductCode\" : \""+ProductCode+"\",\n" +
                " \"currency\" : \"SGD\",\n" +
                " \"Amount\" : \""+Amount+"\",\n" +
                "}";

        System.out.println("Payload is : " + TransactionPayload);

    }   // end of function -  setAPIPayLoad


    public static void APIRequestAndResponse(String Env){
        try{
            if(Env.equals("UAT")){
             //   resp = apiUtils.getResponseForAPIRequest(UAT_URL, TransactionPayload, generateAuthToken);
            }

        }catch(Exception e){
            System.out.println("Exception in API Request and Response is : " + e.getMessage());
        }
    }

}// end of class

package RestAPITesting.oauh;

import org.apache.commons.codec.binary.Base64;


public class Base64Encoded {

    public static String encodeCredentials(String username, String password){
        String cred = username + ":" + password;
        String encodedValue = null;
        byte[] encodedBytes = Base64.encodeBase64(cred.getBytes());
        encodedValue = new String(encodedBytes);

        System.out.println("encodedBytes : " + encodedValue);

        byte[] decodedBytes = Base64.decodeBase64(encodedBytes);

        return encodedValue;

    }

}

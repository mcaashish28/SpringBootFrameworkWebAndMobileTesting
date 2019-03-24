
package RestAPITesting.oauh;

import java.util.Properties;

public class OAuth2Client {

    public static String getAccessTokenForCredentials(){


        Properties config = OAuthUtils.getClientConfigProps(OAuthConstants.CONFIG_FILE_PATH);
        String accessToken = null;

        // Generate the OAuthDetails from the config properties file
        OAuth2Details oauthDetails = OAuthUtils.createOAuthDetails(config);
        // validate input
        if(!OAuthUtils.isValidInput(oauthDetails)){
            System.exit(0);
        }

        // Determine Operation
        if (oauthDetails.isAccessTokenRequest()) {
            // Generate new Access token
            accessToken = OAuthUtils.getAccessToken(oauthDetails);
            if(!OAuthUtils.isValid(accessToken)){
                System.out.println("Error: could not generate Access Token");
            }

        }else{
            // Access protected resource from server using OAuth2.0
            // Response from the resource server should be in Json or URLencoded or xml
            OAuthUtils.getProtectedResource(oauthDetails);
        }

        return accessToken;
    }
}


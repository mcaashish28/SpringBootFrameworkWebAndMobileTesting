
package RestAPITesting.oauh;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.json.JSONParser;
//import org.json.simple.parser.JSONParser;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class OAuthUtils {

    public static OAuth2Details createOAuthDetails(Properties config){
        OAuth2Details oauthDetails = new OAuth2Details();
        oauthDetails.setAccessToken((String) config
            .get(OAuthConstants.ACCESS_TOKEN));
        oauthDetails.setRefreshToken((String) config
            .get(OAuthConstants.REFRESH_TOKEN));
        oauthDetails.setGrantType((String) config
            .get(OAuthConstants.GRANT_TYPE));
        oauthDetails.setClientId((String) config
            .get(OAuthConstants.CLIENT_ID));
        oauthDetails.setClientSecret((String) config
            .get(OAuthConstants.CLIENT_SECRET));
        oauthDetails.setScope((String) config
            .get(OAuthConstants.SCOPE));
        oauthDetails.setAuthenticationServerUrl((String) config
            .get(OAuthConstants.AUTHENTICATION_SERVER_URL));
        oauthDetails.setUsername((String) config
            .get(OAuthConstants.USERNAME));
        oauthDetails.setPassword((String) config
            .get(OAuthConstants.PASSWORD));
        oauthDetails.setResourceServerUrl((String) config
            .get(OAuthConstants.RESOURCE_SERVER_URL));

        if(!isValid(oauthDetails.getResourceServerUrl())){
            oauthDetails.setAccessTokenRequest(true);
        }

    return oauthDetails;
    }   // end of function - createOAuthDetails

    public static Properties getClientConfigProps(String path){
        InputStream is = null;
        try{
            is = new FileInputStream("./src/test/java/RestAPITesting/oauth/Oauth2Client.config");

        }catch(IOException e){
            e.printStackTrace();
        }

        Properties config = new Properties();
        try{
            config.load(is);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }

        return config;
    }   // end of function getClientConfigProps

    public static void getProtectedResource(OAuth2Details oAuthDetails){
        String resourceURL = oAuthDetails.getResourceServerUrl();
        // doTrustToCertificates();//

        HttpGet get = new HttpGet(resourceURL);
        get.addHeader(OAuthConstants.AUTHORIZATION, getAuthorizationHeaderForAccessToken(oAuthDetails.getAccessToken()));
        HttpClient client = new DefaultHttpClient();
        client = WebClientDevWrapper.wrapClient(client);
        HttpResponse response = null;
        int code = -1;
        try{
            response = client.execute(get);
            code = response.getStatusLine().getStatusCode();
            String accessToken = getAccessToken(oAuthDetails);
            if(isValid(accessToken)){
                oAuthDetails.setAccessToken(accessToken);
                get.removeHeaders(OAuthConstants.AUTHORIZATION);
                get.addHeader(OAuthConstants.AUTHORIZATION, getAuthorizationHeaderForAccessToken(oAuthDetails.getAccessToken()));
                get.releaseConnection();
                response = client.execute(get);
                if(code>=400){
                    throw new RuntimeException("Could not access Protected Resource. Server returned http code : " + code);
                }else{
                    throw new RuntimeException("Could not regenerate access token. ");
                }
            }
            handleResponse(response);

        }catch(ClientProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            get.releaseConnection();
        }

    }       // end of function - getProtectedResource

    /*

    public static void doTrustToCertificates() throws Exception {

        TrustManager[] trustAllCerts = new TrustManager[]{
          new X509TrustManager() {

              public X509Certificate[] getAcceptedIssuers(){
                  return null;
              }

              @Override
              public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException{
                  // Todo
              }


              @Override
              public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

              }

          }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory();
        HostnameVerifier hv = (urlHostName, session) {
            if (!urlHostname.equalsIgnoreCase(session.getPeerHost)){

            }
            return false;
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);

    }       // end of function - doTrustToCertificates

*/

    public static String getAccessToken(OAuth2Details oauthDetails){

        HttpPost post = new HttpPost((oauthDetails.getAuthenticationServerUrl()));
        String clientId = oauthDetails.getClientId();
        String clientSecret = oauthDetails.getClientSecret();
        String scope = oauthDetails.getScope();

        List<BasicNameValuePair> parametersBody = new ArrayList<BasicNameValuePair>();
        parametersBody.add(new BasicNameValuePair(OAuthConstants.GRANT_TYPE, oauthDetails.getGrantType()));
        parametersBody.add(new BasicNameValuePair(OAuthConstants.CLIENT_ID, oauthDetails.getClientId()));
        parametersBody.add(new BasicNameValuePair(OAuthConstants.CLIENT_SECRET, oauthDetails.getClientSecret()));

        if(isValid(scope)){
            parametersBody.add(new BasicNameValuePair(OAuthConstants.SCOPE, scope));

        }

        HttpClient client = new DefaultHttpClient();
        client = WebClientDevWrapper.wrapClient(client);
        HttpResponse response = null;
        String accessToken = null;

        try{
            post.setEntity(new UrlEncodedFormEntity(parametersBody, HTTP.UTF_8));
            response = client.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if(code == OAuthConstants.HTTP_UNAUTHORIZED){
                post.addHeader(OAuthConstants.AUTHORIZATION, getBasicAuthorizationHeader(oauthDetails.getClientId(), oauthDetails.getClientSecret()));
                post.releaseConnection();
                response = client.execute(post);
                code = response.getStatusLine().getStatusCode();
                if(code==401 || code == 403){
                    throw new RuntimeException("Could not retrieve access token for client: " + oauthDetails.getClientId());
                }
            }
            Map<String, String> map = handleResponse(response);
            accessToken = map.get(OAuthConstants.ACCESS_TOKEN);


        }catch(ClientProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }


        return accessToken;
    }       // end of function = getAccessToken


    public static Map handleResponse(HttpResponse response) throws IOException {

        String contentType = OAuthConstants.JSON_CONTENT;
        if(response.getEntity().getContentType() != null){
            contentType = response.getEntity().getContentType().getValue();
        }
        if(contentType.contains(OAuthConstants.JSON_CONTENT)){
            return handleJsonResponse(response);
        }else if(contentType.contains(OAuthConstants.URL_ENCODED_CONTENT)){
            return handleURLEncentodedResponse(response);
        }else if(contentType.contains(OAuthConstants.XML_CONTENT)){
            return handleXMLResponse(response);
        }else{
            throw new RuntimeException("Cannot handle " + contentType + " content type. Supported content types includes JSON, XML and URLEncoded");
        }

    }   //end of function = handleResponse


    public static Map handleJsonResponse(HttpResponse response) throws IOException {

        Map<String, String> oauthLoginResponse = null;
        String contentType = response.getEntity().getContentType().getValue();
        try{
            //oauthLoginResponse = (Map<String,String>) new JSONParser().parse(EntityUtils.toString(response.getEntity()));


        } catch(ParseException e){
            e.printStackTrace();
            throw  new RuntimeException();
        }catch(RuntimeException e) {
            throw e;
        }

        return oauthLoginResponse;

    }       // end of function = handleJsonResponse


    public static Map handleURLEncentodedResponse(HttpResponse response){

        Map<String, Charset> map = Charset.availableCharsets();
        Map<String, String> oauthResponse = new HashMap<String, String>();
        Set<Map.Entry<String, Charset>> set = map.entrySet();
        Charset charset = null;

        HttpEntity entity = response.getEntity();

        for(Map.Entry<String, Charset> entry : set){
            if(entry.getKey().equalsIgnoreCase(HTTP.UTF_8)){
                charset = entry.getValue();
            }
        }

        try{
            List<NameValuePair> list = URLEncodedUtils.parse(EntityUtils.toString(entity), Charset.forName(HTTP.UTF_8));
            for(NameValuePair pair : list){
                System.out.println(String.format("  %s = %s ", pair.getName(), pair.getValue()));
                oauthResponse.put(pair.getName(), pair.getValue());
            }
        }catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException("Counld not parse URLEncoded Response ");
        }

        return oauthResponse;
    }       // end of function =handleURLEncentodedResponse


    public  static Map handleXMLResponse(HttpResponse response){

        Map<String, String> oauthResponse = new HashMap<String, String>();
        try{
            String xmlString = EntityUtils.toString(response.getEntity());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            InputSource inStream = new InputSource();
            inStream.setCharacterStream(new StringReader(xmlString));
            Document doc = db.parse(inStream);
            parseXMLDoc(null, doc, oauthResponse);


        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Exception occurred while parsing XML response ");
        }

        return oauthResponse;
    }       // end of function = handleXMLResponse

    public static void parseXMLDoc(Element element, Document doc, Map<String, String> oauthResponse){

        NodeList child = null;
        if(element == null){
            child = doc.getChildNodes();
        }else {
            child = element.getChildNodes();
        }

        for(int j = 0; j < child.getLength(); j++){
            if(child.item(j).getNodeType() == Node.ELEMENT_NODE){
                Element childElement = (Element) child.item(j);
                if(childElement.hasChildNodes()){
                    System.out.println(childElement.getTagName() + " : " + childElement.getTextContent());
                    oauthResponse.put(childElement.getTagName(), childElement.getTextContent());
                    parseXMLDoc(childElement, null, oauthResponse);
                }
            }
        }

    }       // end of function =parseXMLDoc


    public static String getAuthorizationHeaderForAccessToken(String accessToken){
        return OAuthConstants.BEARER + " " + accessToken;
    }

    public static String getBasicAuthorizationHeader(String username, String password){
        return OAuthConstants.BASIC + " " + encodeCredentials(username, password);
    }

    public static String encodeCredentials(String username, String password){

        String cred = username + ":" + password;
        String encodedValue = null;
        byte[] encodedBytes = Base64.encodeBase64(cred.getBytes());
        encodedValue = new String(encodedBytes);

        byte[] decodedBytes = Base64.decodeBase64(encodedBytes);

        return encodedValue;
    }   // end of function = encodeCredentials


    public static boolean isValidInput(OAuth2Details input){

        if(input == null){
            return false;
        }

        String grantType = input.getGrantType();

        if (!isValid(grantType)) {
            return false;

        }

        if(!isValid(input.getAuthenticationServerUrl())){
            return false;

        }

        if(grantType.equals(OAuthConstants.GRANT_TYPE_PASSWORD)){
            if(!isValid(input.getUsername()) || !isValid(input.getPassword())){
                return false;
            }
        }

        if(grantType.equals(OAuthConstants.GRANT_TYPE_CLIENT_CREDENTIALS)){
            if(!isValid(input.getClientId()) || !isValid(input.getClientSecret())){
                return false;
            }
        }

        return true;
    }       // end of function =isValidInput


 public static boolean isValid(String str){
        return (str != null && str.trim().length() > 0);
 }

}

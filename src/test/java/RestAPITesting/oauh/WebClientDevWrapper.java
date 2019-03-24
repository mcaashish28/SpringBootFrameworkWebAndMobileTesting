package RestAPITesting.oauh;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.*;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


public class WebClientDevWrapper {

    @SuppressWarnings("deprecation")
    public static DefaultHttpClient wrapClient(HttpClient base){
        try{
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            X509HostnameVerifier verifier = new X509HostnameVerifier() {
                @Override
                public void verify(String arg0, SSLSocket arg1) throws IOException {
                }

                @Override
                public void verify(String arg0, X509Certificate arg1) throws SSLException {
                }

                @Override
                public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {
                }

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return false;
                }
            };

            ctx.init(null,new  TrustManager[]{tm},null);
            SSLSocketFactory ssf = new SSLSocketFactory() {
                @Override
                public String[] getDefaultCipherSuites() {
                    return new String[0];
                }

                @Override
                public String[] getSupportedCipherSuites() {
                    return new String[0];
                }

                @Override
                public Socket createSocket(Socket socket, String s, int i, boolean b) throws IOException {
                    return null;
                }

                @Override
                public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
                    return null;
                }

                @Override
                public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException, UnknownHostException {
                    return null;
                }

                @Override
                public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
                    return null;
                }

                @Override
                public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
                    return null;
                }
            };

            //ssf.setHostnameVerifier(verifier);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr= ccm.getSchemeRegistry();
            sr.register(new Scheme("https", (SocketFactory) ssf, 443));
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            return defaultHttpClient;

        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }   // end of function =wrapClient



}

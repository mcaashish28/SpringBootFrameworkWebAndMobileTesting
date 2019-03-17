package Utilities;



import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

//import static sg.govtech.TestCases.BaseClass.logger;

public class ReadConfig {
    Properties prop;

    public ReadConfig(){
        File src = new File("./Configurations/config.properties");
        try{
            FileInputStream fis = new FileInputStream(src);
            prop = new Properties();
            prop.load(fis);
        } catch (Exception e) {
            System.out.println("Exception in loading config.properties file is : " + e.getMessage());
           // logger.info("Exception in loading config.properties file is : " + e.getMessage());
        }
    }

    public String getManulifeURLURL(){
        return prop.getProperty("ManulifeURL");
    }

    public String getGoogleURL(){
        return prop.getProperty("GoogleURL");
    }

    public String getChromeDriverpath(){
        return prop.getProperty("chromepath");
    }

    public String getIEDriverpath(){
        return prop.getProperty("iepath");
    }

    public String getFirefoxDriverpath(){
        return prop.getProperty("firefoxpath");
    }


}

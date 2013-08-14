package fw.basic.data.properties;

import fw.basic.data.BaseDataProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 30.12.12
 * Time: 15:33
 * To change this template use File | Settings | File Templates.
 */
public class EnvironmentProperties {

    private static Properties prop;
    private EnvironmentProperties(){}

    private static void init(String configName) {
        prop = new Properties();
        try {
            prop.load(new FileInputStream(configName));
        } catch (FileNotFoundException ex) {
            }   catch (IOException ex) {
        }
    }

    public static String getProperty(String propertyName) {
        if (prop == null) {
            init(BaseDataProvider.envPropertiesFile);
        }
        return prop.getProperty(propertyName);
    }
}

package darkoyd;

import java.util.*;
import java.io.*;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ATorFirefox {

  // -----------------------------------------------------------------
  // Atributos
  // -----------------------------------------------------------------

  // Lectura de archivo de configuración
  private static InputStream is;
  private static Properties props;

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {

    try {

      // Config file load
      is = new FileInputStream("ArchivoOficialTwitch-main/TwitchExtensionBot/config.properties");
      props = new Properties();
      props.load(is);
      System.out.println("Config file loaded correctly!");
    } catch (Exception e) {
      System.err.println("Config file missing! " + e);
    }

    System.setProperty("webdriver.gecko.driver", props.getProperty("driverpath"));
    FirefoxOptions options = new FirefoxOptions();
    options.setBinary(props.getProperty("torpath"));
    FirefoxProfile profile = new FirefoxProfile(new File(props.getProperty("profilepath")));

    // Profile configuration
    profile.setPreference("network.proxy.type", 1);
    profile.setPreference("network.proxy.socks", "127.0.0.1");
    profile.setPreference("network.proxy.socks_port", 9150);
    profile.setPreference("network.proxy.socks_remote_dns", "False");
    options.setProfile(profile);

    try {
      // Parse JSON File
      JSONObject json = readAccountJSON();
      JSONArray sheet = (JSONArray) json.get("Hoja1");
      System.out.println("Account JSON loaded correctly");
      // Create thread arraylist
      ArrayList<StreamWatcher> threadArray = new ArrayList<StreamWatcher>();
      sheet.forEach(acc -> threadArray.add(new StreamWatcher((JSONObject) acc, options)));
      // Start theads
      threadArray.forEach(t -> t.start());
    } catch (Exception e) {
      System.err.println("Couldn't load account JSON " + e);
    }
  }

  // JSON reader method
  private static JSONObject readAccountJSON() throws IOException, ParseException {
    JSONParser parser = new JSONParser();
    FileReader reader = new FileReader("ArchivoOficialTwitch-main/TwitchExtensionBot/accounts.json");
    Object obj = parser.parse(reader);
    JSONObject accountJSON = (JSONObject) obj;
    return accountJSON;
  }
}

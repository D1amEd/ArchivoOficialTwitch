
package darkoyd;

import java.time.Duration;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

public class StreamWatcher extends Thread {

  // Attributes
  private String twitchUser;
  private String twitchPassword;
  private String id;
  private String email;
  private String emailPassword;
  private String twitch2FA;
  private WebDriver driver;

  /**
   * StreamWatcher constructor
   *
   * @param account JSONObject containing the account details
   * @param driver  Selenium driver instance
   */
  public StreamWatcher(JSONObject account, FirefoxOptions options) {
    WebDriver driver = new FirefoxDriver(options);
    this.id = (String) account.get("#");
    this.twitchUser = (String) account.get("User");
    this.twitchPassword = (String) account.get("Contra");
    // this.email = (String) account.get("Correo");
    // this.emailPassword = (String) account.get("Contra correo");
    this.driver = driver;
  }

  /**
   * Thread run method
   */
  public void run() {
    System.out.println("Running account thread with ID: " + this.id);
    openTwitchAndLogIn(this.twitchUser, this.twitchPassword);
    // this.twitch2FA = openHotmail(this.email, this.emailPassword);
    // joinBrawlhalla(twitch2FA);
  }

  /**
   * Method that opens twitch and logs in.
   *
   * @param twitchUser     String containing the Twitch.tv username
   * @param twitchPassword String containing the Twitch.tv password
   */
  private void openTwitchAndLogIn(String twitchUser, String twitchPassword) {
    driver.get("https://www.twitch.tv/");
    driver.findElement(By.xpath("//button[normalize-space()=\"Log In\"]")).click();

    WebElement userElement = new WebDriverWait(driver, Duration.ofSeconds(2000))
        .until(ExpectedConditions.elementToBeClickable(By.id("login-username")));
    userElement.sendKeys(twitchUser);

    WebElement passElement = new WebDriverWait(driver, Duration.ofSeconds(2000))
        .until(ExpectedConditions.elementToBeClickable(By.id("password-input")));
    passElement.sendKeys(twitchPassword);
    driver.findElement(By.xpath("//html/body/div[3]/div/div/div/div/div/div[1]/div/div/div[3]/form/div/div[3]/button"))
        .click();
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
    }
    WebElement remindmelater = new WebDriverWait(driver, Duration.ofSeconds(10000))
        .until(ExpectedConditions
            .elementToBeClickable(By.xpath("/html/body/div[3]/div/div/div/div/div/div/div/div[3]/div[2]/button")));
    remindmelater.click();
    // driver.findElement(By.xpath("/html/body/div[3]/div/div/div/div/div/div/div/div[3]/div[2]/button")).click();
    try {
      Thread.sleep(4000);
      driver.get("https://www.twitch.tv/brawlhalla");
    } catch (InterruptedException e) {
    }
    WebElement options = new WebDriverWait(driver, Duration.ofSeconds(2000))
        .until(ExpectedConditions
            .elementToBeClickable(By.xpath(
                "/html/body/div[1]/div/div[2]/div[1]/main/div[2]/div[3]/div/div/div[2]/div/div[2]/div/div/div/div/div[7]/div/div[2]/div[2]/div[2]/div[2]/div/button")));
    options.click();
    WebElement quality = new WebDriverWait(driver, Duration.ofSeconds(2000))
        .until(ExpectedConditions
            .elementToBeClickable(By.xpath(
                "/html/body/div[1]/div/div[2]/div[1]/main/div[2]/div[3]/div/div/div[2]/div/div[2]/div/div/div/div/div[7]/div/div[2]/div[2]/div[1]/div[1]/div/div/div/div/div/div/div[3]/button")));
    quality.click();
    WebElement cienp = new WebDriverWait(driver, Duration.ofSeconds(20))
        .until(ExpectedConditions
            .elementToBeClickable(By.xpath(
                "/html/body/div[1]/div/div[2]/div[1]/main/div[2]/div[3]/div/div/div[2]/div/div[2]/div/div/div/div/div[7]/div/div[2]/div[2]/div[1]/div[1]/div/div/div/div/div/div/div[9]/div/div/div/label")));
    cienp.click();
  }

  /**
   * Method that opens Hotmail, checks for 2FA code from Twitch
   *
   * @param email         String containing the email address
   * @param emailPassword String containing the password for email
   * @return String containing the 2FA code from Twitch
   */
  private String openHotmail(String email, String emailPassword) {
    driver.switchTo().newWindow(WindowType.TAB);
    driver.navigate().to("https://outlook.live.com/owa/");
    driver.findElement(By.xpath("/html/body/header/div/aside/div/nav/ul/li[2]/a")).click();
    WebElement signInBox = new WebDriverWait(driver, Duration.ofSeconds(20))
        .until(ExpectedConditions.elementToBeClickable(By.id("i0116")));
    signInBox.sendKeys(email + Keys.ENTER);
    // driver.findElement(By.id("i0116")).sendKeys(email + Keys.ENTER);

    WebElement pswordBox = new WebDriverWait(driver, Duration.ofSeconds(20))
        .until(ExpectedConditions.elementToBeClickable(By.id("i0118")));
    pswordBox.sendKeys(emailPassword + Keys.ENTER);

    WebElement recordarContraseña = new WebDriverWait(driver, Duration.ofSeconds(20))
        .until(ExpectedConditions.elementToBeClickable(By.xpath(
            "//html/body/div/form/div/div/div[2]/div[1]/div/div/div/div/div/div[3]/div/div[2]/div/div[3]/div[2]/div/div/div[2]/input")));
    recordarContraseña.click();

    WebElement TwitchCode = new WebDriverWait(driver, Duration.ofSeconds(20))
        .until(driver -> driver.findElement(By.className(
            "JLM0tHh_sEShVEtZRMmT")));
    TwitchCode = driver.findElement((By.className(
        "JLM0tHh_sEShVEtZRMmT")));
    // "/html/body/div[3]/div/div[2]/div[2]/div[2]/div/div/div[3]/div[2]/div/div[1]/div[2]/div/div/div/div/div/div[1]/div/div/div/div[2]/div[2]/div/span"));

    String infoPrecode = TwitchCode.getText();
    infoPrecode = infoPrecode.substring(0, 6);
    String twitchCode = "";
    System.out.println("------------");
    System.out.println("Twitch 2FA: " + twitchCode);
    System.out.println("------------");
    if (isNumeric(infoPrecode)) {
      twitchCode = infoPrecode;
    } else {
      WebElement otros = new WebDriverWait(driver, Duration.ofSeconds(20))
          .until(ExpectedConditions.elementToBeClickable(By.id(
              "Pivot18-Tab1")));
      otros.click();
      WebElement codeOtros = new WebDriverWait(driver, Duration.ofSeconds(20))
          .until(driver -> driver.findElement(By.className(
              "JLM0tHh_sEShVEtZRMmT")));
      String OtrosCode = codeOtros.getText();
      infoPrecode = OtrosCode.substring(0, 6);
      twitchCode = infoPrecode;
    }
    // Esto cierra el driver y te cambia otra vez a la de twitch
    driver.close();
    ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
    driver.switchTo().window(tabs2.get(0));
    return twitchCode;
  }

  /**
   * Method that opens the Brawlhalla stream, TODO sets the stream properties to
   * the appropriate values
   *
   * @param codeTwitch String containing the 2FA code from Twitch
   */
  // public void joinBrawlhalla(String codeTwitch) {
  // WebElement verificationCode = new WebDriverWait(driver,
  // Duration.ofSeconds(20))
  // .until(ExpectedConditions.elementToBeClickable(
  // By.xpath("/html/body/div[3]/div/div/div/div/div/div[1]/div/div/div[3]/div[2]/div/div[1]/div/input")));
  // verificationCode.sendKeys(codeTwitch);
  // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
  // try {
  // Thread.sleep(7000);
  // } catch (InterruptedException e) {
  // }

  // try {
  // Thread.sleep(5000);
  // } catch (InterruptedException e) {
  // }

  // WebElement btnbrawl = new WebDriverWait(driver, Duration.ofSeconds(20))
  // .until(ExpectedConditions.elementToBeClickable(
  // By.xpath(
  // "/html/body/div[1]/div/div[2]/div[1]/main/div[2]/div[3]/div/div/div[2]/div/div[2]/div/div/div/div/div[4]/div/div[1]/div[2]/div/div/div/div/div[2]")));
  // btnbrawl.click();
  // WebElement TwitchCode = new WebDriverWait(driver, Duration.ofSeconds(20))
  // .until(driver -> driver.findElement(By.xpath(
  // "/html/body/div[1]/div/div[2]/div[1]/main/div[2]/div[3]/div/div/div[2]/div/div[2]/div/div/div/div/div[5]/div/div[1]/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div[4]/div[2]/button")));
  // TwitchCode.click();
  // WebElement button = new WebDriverWait(driver, Duration.ofSeconds(50))
  // .until(driver -> driver.findElement(By.xpath(
  // "/html/body/div/div/div/button")));
  // button.click();
  // WebElement button2ndtry = new WebDriverWait(driver, Duration.ofSeconds(20))
  // .until(driver -> driver.findElement(By.className(
  // "online-button")));
  // button2ndtry.submit();

  // }

  /**
   * Helper method to verify that the given code matches a valid code.
   *
   * @param strNum String containing the Twitch.tv 2FA code.
   * @return True if the given code matches. False otherwise.
   */
  private static boolean isNumeric(String strNum) {
    if (strNum == null) {
      return false;
    }
    try {
      Double.parseDouble(strNum);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }
}

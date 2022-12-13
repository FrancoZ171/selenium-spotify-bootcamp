package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;


import java.util.concurrent.TimeUnit;

public class CasosDePrueba {
    //Atributos
    private WebDriver driver;
    private WebDriverWait wait;

    private JavascriptExecutor js;

    private String rutaDriver= "D:\\Program Files\\IntelliJ Projects\\SeleniumPrimerosPasos1\\src\\test\\resources\\drivers\\chromedriver.exe";
    private String propertyDriver = "webdriver.chrome.driver";

    @AfterMethod
    public void posCondicion(){
        driver.close();
    }

    @BeforeMethod
    public void preCondiciones(){

        System.setProperty(propertyDriver,rutaDriver);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=es");
        driver = new ChromeDriver(options);

        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        driver.manage().timeouts().setScriptTimeout(40, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver,10);




        js = (JavascriptExecutor) driver;

        driver.navigate().to("https://open.spotify.com/");

        driver.manage().window().maximize();
    }



    @Test
    public void TC01_Error_Contrasena_Corta(){

        By localizadorBtnRegistrase = By.xpath("//button[contains(text(),'Registrarte')]");

        WebElement btnRegistrarse = driver.findElement(localizadorBtnRegistrase);

        btnRegistrarse.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password"))).sendKeys("123");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("confirm"))).sendKeys(".");

        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'Tu contraseña es demasiado corta.')]")).getText(),"Tu contraseña es demasiado corta.");
    }
    @Test
    public void TC02_Anio_Invalido() {

        By localizadorBtnRegistrase = By.xpath("//button[contains(text(),'Registrarte')]");

        WebElement btnRegistrarse = driver.findElement(localizadorBtnRegistrase);

        btnRegistrarse.click();


        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("year"))).sendKeys("199");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("day"))).sendKeys("28");


        WebElement optionMale = driver.findElement(By.xpath("//label[@for='gender_option_male']"));

        js.executeScript("arguments[0].scrollIntoView();", optionMale);


        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'Indica un año válido.')]")).getText(),"Indica un año válido.");
    }
    //

    @Test
    public void TC03_Dia_Invalido()  {

        By localizadorBtnRegistrase = By.xpath("//button[contains(text(),'Registrarte')]");

        WebElement btnRegistrarse = driver.findElement(localizadorBtnRegistrase);

        btnRegistrarse.click();


        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("day"))).sendKeys("33");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("year"))).sendKeys("1995");


        WebElement optionMale = driver.findElement(By.xpath("//label[@for='gender_option_male']"));

        js.executeScript("arguments[0].scrollIntoView();", optionMale);

        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'Indica un día válido del mes.')]")).getText(),"Indica un día válido del mes.");


    }
    @Test
    public void TC04_Nombre_Perfil_En_Blanco() {

        By localizadorBtnRegistrase = By.xpath("//button[contains(text(),'Registrarte')]");

        WebElement btnRegistrarse = driver.findElement(localizadorBtnRegistrase);

        btnRegistrarse.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("displayname"))).sendKeys("");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("confirm"))).sendKeys(".");
        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'Indica un nombre para tu perfil.')]")).getText(),"Indica un nombre para tu perfil.");
    }
    @Test
    public void TC05_Correo_Invalido() {

        By localizadorBtnRegistrase = By.xpath("//button[contains(text(),'Registrarte')]");

        WebElement btnRegistrarse = driver.findElement(localizadorBtnRegistrase);

        btnRegistrarse.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email"))).sendKeys("esteCorreoTiene2Puntos@tsoftglobal..com");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("confirm"))).sendKeys(".");

        Assert.assertEquals(driver.findElement(By.xpath("//span[contains(text(),'Este correo electrónico no es válido')]")).getText(),"Este correo electrónico no es válido. Asegúrate de que tenga un formato como este: ejemplo@email.com");
    }

    @Test
    public void TC06_Correo_No_Coincide() {

        By localizadorBtnRegistrase = By.xpath("//button[contains(text(),'Registrarte')]");

        WebElement btnRegistrarse = driver.findElement(localizadorBtnRegistrase);

        btnRegistrarse.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email"))).sendKeys("PruebaEjej@tsoftglobal.com");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("confirm"))).sendKeys("esteCorreoTaMalj@tsoftglobal.com");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password"))).sendKeys("");

        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'Las direcciones de correo electrónico no coinciden.')]")).getText(),"Las direcciones de correo electrónico no coinciden.");
    }

}
package pomtests;



import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;
import pages.ProcessPage;
import pages.ProductsPage;
import pages.WebOrdersPage;

public class WebOrdersTest {

	WebDriver driver;
	LoginPage loginPage;
	WebOrdersPage webOrdersPage;
	ProductsPage productsPage;
	ProcessPage processPage;
	String userName = "tester" ;
	String password = "test" ;
	

	@BeforeClass //runs once for all tests
	public void setUp() {
		System.out.println("Setting up WebDriver in BeforeClass...");
	WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();

	}
	
	@Test(priority=0)
	public void loginPageTest () {
driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
loginPage = new LoginPage(driver);		
loginPage.userName.sendKeys(userName);
loginPage.password.sendKeys(password);	
loginPage.loginButton.click();	
	
	}
	
	@Test(priority=1)	
	public void WebOrdersPage() {
	webOrdersPage = new WebOrdersPage(driver);
	assertTrue(webOrdersPage.viewAllOrders.isDisplayed());
	assertTrue(webOrdersPage.viewAllProducts.isDisplayed());
    assertTrue(webOrdersPage.order.isDisplayed());
	
	}
	
	@Test(priority=2)	
	
		public void verifyProducts() {
			
			webOrdersPage = new WebOrdersPage(driver);
			productsPage = new ProductsPage(driver);
	 		
	    	webOrdersPage.viewAllProducts.click();
		 	
			List<String> expectedProducts = Arrays.asList("MyMoney", "FamilyAlbum", "ScreenSaver");
	
			List<String> actualProducts = new ArrayList<>();
			// iterate over each products name
			for (WebElement eachProduct : productsPage.productNamesColumn) {
				// getting products text and adding to ArrayList
				actualProducts.add(eachProduct.getText());

			}
			assertEquals(actualProducts, expectedProducts);
			// iterating on productsRows
			for (WebElement eachRow : productsPage.productsRows) {
				System.out.println(eachRow.getText());

	        	String[] productsData = eachRow.getText().split(" ");

				switch (productsData[0]) {
				case "MyMoney":
					assertEquals(productsData[1], "$100");
					assertEquals(productsData[2], "8%");
					break;
				case "FamilyAlbum":
					assertEquals(productsData[1], "$80");
				assertEquals(productsData[2], "15%");
					break;
				case "ScreenSaver":
					assertEquals(productsData[1], "$20");
					assertEquals(productsData[2], "10%");
					break;

				}
			}
	
		}
	@Test(priority = 3)
	public void placeOrder() {
		String name = "Zeynep";
		processPage = new ProcessPage(driver);
		processPage.order.click();
		processPage.quantity.clear();
		processPage.quantity.sendKeys("1");
		processPage.customerName.sendKeys(name);
		processPage.street.sendKeys("123 Main Street");
		processPage.city.sendKeys("Omaha");
		processPage.zipCode.sendKeys("12345");
		processPage.radioButton.click();
		processPage.cardNumber.sendKeys("4003218965433765");
		processPage.expDate.sendKeys("12/18");
		processPage.processButton.click();
		assertEquals(processPage.orderPlaced.getText(), "New order has been successfully added.");

	}
	
	@AfterClass
	public void tearDown() throws InterruptedException {
		Thread.sleep(2000);
		driver.quit();
	}
	
	
}

	
	
	
	
	
	
	
	
	
	

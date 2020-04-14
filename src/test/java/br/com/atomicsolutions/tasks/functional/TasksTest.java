package br.com.atomicsolutions.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class TasksTest {

	
	public WebDriver acessarAplicacao() throws MalformedURLException
	{
		System.setProperty("webdriver.chrome.driver",  "C:\\Proton\\ProtonClient\\Drivers\\chromedriver.exe");
	//	WebDriver driver = new ChromeDriver();
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"),cap);
		driver.navigate().to("http://192.168.100.23:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		return driver;
	}
	
	@Test
	public void deveSalvarComSucesso() throws MalformedURLException
	{
		WebDriver driver = acessarAplicacao();

		try 
		{
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", message);
		}
		finally
		{
			
			driver.quit();

		}
		
	}
	
	@Test
	public void naoDeveSalvarSemDesc() throws MalformedURLException
	{
		WebDriver driver = acessarAplicacao();

		try 
		{
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", message);
		}
		finally
		{
			
			driver.quit();

		}
		
	}
	@Test
	public void naoDeveSalvarSemData() throws MalformedURLException
	{
		WebDriver driver = acessarAplicacao();

		try 
		{
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", message);
		}
		finally
		{
			
			driver.quit();

		}
		
	}
	@Test
	public void naoDeveSalvarComDataPassada() throws MalformedURLException
	{
		WebDriver driver = acessarAplicacao();

		try 
		{
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", message);
		}
		finally
		{
			
			driver.quit();

		}
		
		
	}
	
}

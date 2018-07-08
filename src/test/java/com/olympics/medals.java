package com.olympics;

import java.util.Map.Entry;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class medals {

	static WebDriver driver;
	String url = "https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table.";

	@BeforeClass 
	public void setUp() {

		WebDriverManager.chromedriver().setup();

		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
     	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();

		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
		WebElement point = driver
				.findElement(By.xpath("//table [@class = 'wikitable sortable plainrowheaders jquery-tablesorter']"));
		Actions action = new Actions(driver);
		action.moveToElement(point).perform();

	}

	@Test(priority = 1)
	public void sortTestCase1() {
		List<WebElement> firstColumn = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[1]"));
		ArrayList<Integer> actualList = new ArrayList<>();
		
		for (int i = 0; i < firstColumn.size() - 1; i++) {
			actualList.add(Integer.parseInt(firstColumn.get(i).getText()));
		}
		SortedSet<Integer> expectedList = new TreeSet<>(actualList);
		Assert.assertEquals(actualList, expectedList);
		
		driver.findElement(By.xpath("//th[@class='headerSort'][contains(text(),'NOC')]")).click();
		//countries
		List<WebElement> countries = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th/a"));
		ArrayList<String> actualCountryList = new ArrayList<>();

		for (int i = 0; i < countries.size() - 1; i++) {
			actualCountryList.add(countries.get(i).getText());
		}
		System.out.println(actualCountryList + " Arraylist");
		SortedSet<String> expectedCountryList = new TreeSet<>(actualCountryList);
		System.out.println(expectedCountryList + "Treeset");
		Assert.assertEquals(actualCountryList, expectedCountryList);
		
		List<WebElement> firstColumn1 = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[1]"));
		ArrayList<Integer> actualList1 = new ArrayList<>();
		for (int i = 0; i < firstColumn.size() - 1; i++) {
			actualList1.add(Integer.parseInt((firstColumn1.get(i).getText())));
		}
		Assert.assertFalse(expectedList.equals(actualList1));
	}

	@Test(priority = 2)
	public void TheMostTestCase2() throws InterruptedException {
		Thread.sleep(2000);
		// Click Rank sort
	    driver.findElement(By.xpath("//th [@scope='col'] [@class = 'headerSort headerSortUp']")).click();
	   
	    WebElement gold = driver.findElement(By.xpath("//th [@scope='col'] [@style = 'background-color:gold; width:6em;']"));
		WebElement silver = driver.findElement(By.xpath("//th [@scope='col'] [@style = 'background-color:silver; width:6em;']"));
		WebElement bronze = driver.findElement(By.xpath("//th [@scope='col'] [@style = 'background-color:#cc9966; width:6em;']"));
		WebElement total = driver.findElement(By.xpath("//th [@scope='col'] [@style = 'width:6em;']"));
		Assert.assertEquals(mostCountyMedal(gold,"gold"), " United States (USA) 46");
		Assert.assertEquals(mostCountyMedal(silver,"silver"), " United States (USA) 37");
		Assert.assertEquals(mostCountyMedal(bronze,"bronze"), " United States (USA) 38");
		Assert.assertEquals(mostCountyMedal(total,"total"), " United States (USA) 121");
	}

	public String mostCountyMedal(WebElement medal,String medalName) {
		if (medal.getAttribute("title").equals("Sort descending")) {
			medal.click();
		}
		String MedalCountry = driver.findElement(By.xpath("//table[@class= 'wikitable sortable plainrowheaders jquery-tablesorter'] //tbody/tr[1]//th[@scope = 'row']")).getText();
		String countXpath ="//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']//tbody/tr[1]/";
		String MedalCount ="";
				switch (medalName) {
        case "gold": MedalCount =driver.findElement(By.xpath(countXpath+"td[2]")).getText();
                 break;
        case "silver":  MedalCount =driver.findElement(By.xpath(countXpath+"td[3]")).getText();
        break;
        case "bronze": MedalCount =driver.findElement(By.xpath(countXpath+"td[4]")).getText();
        break;
        case "total":  MedalCount =driver.findElement(By.xpath(countXpath+"td[5]")).getText();
        break;
		}
				System.out.println(MedalCountry +" "+ MedalCount);
		return MedalCountry +" "+ MedalCount;
	}

//	Test Case 3: COUNTRY BY MEDAL
//	1. Go to website https://en.wikipedia.org/wiki/2016_Summer_Olympics.
//	2. Write a method that returns a list of countries whose silver medal count is equal to 18.
//	3. Write TestNG test for your method.
	
	 @Test(priority=3)
	public void countryByMedalTestCase3() {
		List<String> expectedList18 = Arrays.asList("China", "France");
		Assert.assertEquals(silver18(), expectedList18);
	}
	 public List<String> silver18() {
		
		List<WebElement> silverElement = driver.findElements(By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']//tbody//td[3]"));
		//countries
		List<WebElement> countries = driver.findElements(
						By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th/a"));

		Set<String> silverSet = new HashSet<>();
		for (int i = 0; i < silverElement.size(); i++) {

			if(silverElement.get(i).getText().equals("18")) {
			silverSet.add(countries.get(i).getText());
			}
		}
		System.out.println(silverSet);
		List<String> list = new ArrayList<>(silverSet);
		return list;
	}
//	 Test Case 4: GET INDEX
//	 1. Go to website https://en.wikipedia.org/wiki/2016_Summer_Olympics.
//	 2. Write a method that takes country name and returns the row and column number. You decide the datatype of the return.
//	 3. Write TestNG test for your method (use Japan as test input).
	 
	 public String rowColumn(String country) {
	//countries
		 List<WebElement> countries = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th/a"));
		 Map<String,Integer> rowCol = new HashMap<>();
		 for (int i = 0; i < countries.size(); i++) {
			 rowCol.put((countries.get(i).getText()), i + 1);
			 System.out.println("index: " + i + " column: " +(i+1) + " row: 2 " + countries.get(i).getText());
			}
		 return ""+rowCol.get(country) + "," + 2;
		 }
	 
	 @Test(priority=4)
	public void getIndexCase4() {
		Assert.assertEquals(rowColumn("Russia"), "3,2");
	}
//	 Test Case 5: GET SUM
//	 1. Go to website https://en.wikipedia.org/wiki/2016_Summer_Olympics.
//	 2. Write a method that returns a list of two countries whose sum of bronze medals is 18.
//	 3. Write TestNG test for your method.

	 @Test(priority=5)
	public void testCase5() {
		getSum2();
		List<String> expectedtotal18 = Arrays.asList("Italy + Australia");
	    Assert.assertEquals(getSum2(), expectedtotal18);
	}
	
	public static List<String> getSum2() {
		List<WebElement> bronze = driver.findElements(By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']//tbody//td[4]"));
		List<WebElement> countries = driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th/a"));
//		Map<String,Integer> bronzeTotal18 = new HashMap<>();
//		 for (int i = 0; i < countries.size(); i++) {
//			 bronzeTotal18.put((countries.get(i).getText()), Integer.parseInt(bronze.get(i).getText()));
//			 System.out.println("country: " + countries.get(i).getText()+ " bronze medal count: " +bronze.get(i).getText());
//			}
		 List<String> ls = new ArrayList<>();
		 
		 for (int i = 0; i < countries.size(); i++) {
			 for (int j = i+1; j < countries.size(); j++) {
				 int first = Integer.parseInt(bronze.get(i).getText());
				 int next = Integer.parseInt(bronze.get(j).getText());
				 if ((first+next)==18) {
					 ls.add(countries.get(i).getText() +" + " +countries.get(j).getText());
				   }
			     }
			   }
		 System.out.println(ls);
		return ls;
	}	
}
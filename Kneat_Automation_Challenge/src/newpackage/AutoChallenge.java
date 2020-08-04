/*
 * AutoChallenge
 * Kneat Automation Code Challenge
 * Michael O'Connor
 * August 2020
 */
package newpackage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AutoChallenge {
	
	WebDriver driver;
	
	// Launching the browser
	public void LaunchBrowser() {
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		driver = new ChromeDriver();
		// Making browser window fullscreen
		driver.manage().window().maximize(); 
		//Go to the website booking.com
		driver.get("http://booking.com");
		System.out.println("Test Begin");
	}
	
	// Search Method. location is location,months is in how many months away the trip is,
	// day is first day of stay, leaving_day is the final day of trip,
	// adult is number of adults, child is number of children and room is number of rooms
	public void Search(String location,int months, int day, int leaving_day, int adult, int child, int room) {
		// Make thread sleep to give page a chance to load
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		// wait for the cookie pop up to appear before trying to click it
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cookie_warning\"]/div/div/div[2]/button")));
		driver.findElement(By.xpath("//*[@id=\"cookie_warning\"]/div/div/div[2]/button")).click();
		// Enter location
		driver.findElement(By.id("ss")).sendKeys(location);
		// Go to correct month by clicking the > button
		driver.findElement(By.xpath("//*[@id=\"frm\"]/div[1]/div[2]/div[1]/div[2]/div/div/div/div/span")).click();
		for (int i = 0; i<months; i++) {
			driver.findElement(By.xpath("//*[@id=\"frm\"]/div[1]/div[2]/div[2]/div/div/div[2]")).click();
		}
		// List all the table elements for the chosen month. click the starting day when found and then click the leaving day
		List<WebElement> dates = driver.findElements(By.xpath("//*[@id=\"frm\"]/div[1]/div[2]/div[2]/div/div/div[3]/div[1]/table//td"));
		for(int i = 0;i<dates.size();i++) {
			String date = dates.get(i).getText();
			if (date.equals(Integer.toString(day))) {
				dates.get(i).click();
			}
			if (date.equals(Integer.toString(leaving_day))) {
				dates.get(i).click();
				break;
			}
		}
		// click the amount of guests element
		driver.findElement(By.xpath("//*[@id=\"xp__guests__toggle\"]/span[2]")).click();
		// Compare current value of adults to the target and then adjust using the increment and decrement buttons
		WebElement adults = driver.findElement(By.xpath("//*[@id=\"xp__guests__inputs-container\"]/div/div/div[1]/div/div[2]/span[1]"));
		int adult_number = Integer.parseInt(adults.getText());
		int diff = adult - adult_number;
		if (diff > 0) {
			for(int i = 0;i<diff;i++) {
				driver.findElement(By.xpath("//*[@id=\"xp__guests__inputs-container\"]/div/div/div[1]/div/div[2]/button[2]")).click();
			}
		}
		if (diff < 0) {
			for(int i = 0;i<Math.abs(diff);i++) {
				driver.findElement(By.xpath("//*[@id=\"xp__guests__inputs-container\"]/div/div/div[1]/div/div[2]/button[1]")).click();
			}
		}
		// Compare current value of children to the target and then increment if needed. Defaults to zero so no need to decrement
		WebElement children = driver.findElement(By.xpath("//*[@id=\"xp__guests__inputs-container\"]/div/div/div[2]/div/div[2]/span[1]"));
		int child_number = Integer.parseInt(children.getText());
		int child_diff = child - child_number;
		if (child_diff > 0) {
			for(int i = 0;i<child_diff;i++) {
				driver.findElement(By.xpath("//*[@id=\"xp__guests__inputs-container\"]/div/div/div[2]/div/div[2]/button[2]")).click();
			}
		}
		// Compare current value of room to the target and then increment if needed. Defaults to one so no need to decrement
		WebElement rooms = driver.findElement(By.xpath("//*[@id=\"xp__guests__inputs-container\"]/div/div/div[3]/div/div[2]/span[1]"));
		int room_number = Integer.parseInt(rooms.getText());
		int room_diff = room - room_number;
		if (room_diff > 0) {
			for(int i = 0;i<room_diff;i++) {
				driver.findElement(By.xpath("//*[@id=\"xp__guests__inputs-container\"]/div/div/div[3]/div/div[2]/button[2]")).click();
			}
		}
		// Click the search button
		driver.findElement(By.xpath("//*[@id=\"frm\"]/div[1]/div[4]/div[2]/button")).click();
		// Give the page a chance to load
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Method for filtering a star rating
	public void StarFilter(String star) {
		// Get all available star ratings
		List<WebElement> stars = driver.findElements(By.xpath("//*[@id=\"filter_class\"]/div[2]/a"));
		// if an available rating matches the target click it
		for(int i = 0;i<stars.size();i++) {
			String star_rating = stars.get(i).getText().substring(0, 1);
			if (star_rating.equals(star)) {
				stars.get(i).click();
				break;
			}
		}
		// let the page load
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Star Rating: "+star);
	}
	
	// Method for adding facility filters
	public void FacilFilter(String Facil) {
		// CLick the show more options on the facility options
		driver.findElement(By.xpath("//*[@id=\"filter_facilities\"]/div[2]/button[1]")).click();
		// if this brings up the search dialog, then enter the chosen facility and give page a chance to load
		if ( driver.findElements(By.xpath("//*[@id=\"sr-facility-search-modal\"]/div/div/div/div[1]/div/div/input")).size() != 0 ) {
			driver.findElement(By.xpath("//*[@id=\"sr-facility-search-modal\"]/div/div/div/div[1]/div/div/input")).sendKeys(Facil);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// make a list of all available facilities
			List<WebElement> facilities = driver.findElements(By.xpath("//*[@id=\"sr-facility-search-modal\"]/div/div/div/div[2]/div"));
			// if a facility matches the target then select it
			for(int i = 0;i<facilities.size();i++) {
				String facility = facilities.get(i).getText();
		
				if (facility.contains(Facil)) {
					facilities.get(i).click();
					break;
				}
			}
			// Search for the facility
			driver.findElement(By.xpath("//*[@id=\"sr-facility-search-modal\"]/div/div/div/button")).click();
			// Let the page load
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// if clicking show more didn't bring up the search box
		else {
			List<WebElement> facilities = driver.findElements(By.xpath("//*[@id=\"filter_facilities\"]/div[2]/a"));
			// if a facility matches the target then select it
			for(int i = 0;i<facilities.size();i++) {
				String facility = facilities.get(i).getText();
				if (facility.contains(Facil)) {
					facilities.get(i).click();
					break;
				}
			}
			
		}
		System.out.println("Facility Filter: "+Facil);
	}
	
	// Method for checking if a certain hotel is listed
	public void IsListed(String hotel_name) {
		System.out.println("Hotel: "+hotel_name);
		// flag variable
		int flag = 0;
		// List of hotels showing
		List<WebElement> hotels = driver.findElements(By.xpath("//*[@id=\"hotellist_inner\"]/div/div/div/div/div/h3/a/span[1]"));
		// If a listed hotel matches the target then set the flag and print Listed
		for(int i = 0;i<hotels.size();i++) {
			String hotel = hotels.get(i).getText();
			if (hotel.contains(hotel_name)) {
				System.out.println("Listed");
				flag = 1;
				break;
			}
		}
		// If flag is not set print Not Listed
		if (flag == 0) {
			System.out.println("Not Listed");
		}
		System.out.println();
	}
	
	//Method for closing the browser
	public void CLoseBrowser() {
		driver.quit();
	}

	// Main Method
	public static void main(String[] args) {
		AutoChallenge test1 = new AutoChallenge();
		test1.LaunchBrowser();
		test1.Search("Limerick",3,6,9,4,7,4);
		test1.StarFilter("4");
		test1.FacilFilter("Spa");
		test1.IsListed("Absolute Hotel");
		test1.CLoseBrowser();
		System.out.println("Expected Listed");
		
		AutoChallenge test2 = new AutoChallenge();
		test2.LaunchBrowser();
		test2.Search("Limerick",4,10,17,2,0,1);
		test2.StarFilter("5");
		test2.IsListed("George Hotel");
		test2.CLoseBrowser();
		System.out.println("Expected Not Listed");
		
		AutoChallenge test3 = new AutoChallenge();
		test3.LaunchBrowser();
		test3.Search("Limerick",5,1,12,1,1,2);
		test3.FacilFilter("Sauna");
		test3.IsListed("George Hotel");
		test3.CLoseBrowser();
		System.out.println("Expected Not Listed");
		
		AutoChallenge test4 = new AutoChallenge();
		test4.LaunchBrowser();
		test4.Search("Limerick",2,20,21,5,9,5);
		test4.FacilFilter("Sauna");
		test4.IsListed("Strand Hotel");
		test4.CLoseBrowser();
		System.out.println("Expected Listed");
		
		AutoChallenge test5 = new AutoChallenge();
		test5.LaunchBrowser();
		test5.Search("Limerick",3,4,5,2,0,1);
		test5.StarFilter("5");
		test5.IsListed("Savoy Hotel");
		test5.CLoseBrowser();
		System.out.println("Expected Listed");
		
		
	}

}

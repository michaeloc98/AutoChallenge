Kneat_Automation_Challenge

Using Selenium WebDriver to test new filters added to booking.com

In the src folder there is newpackage which contains AutoChallenge.java

Functions in AutoChallenge:
	LaunchBrowser() - opens the chrome browser, fullscreen to booking.com
	
	Search(String location,int months, int day, int leaving_day, int adult, int child, int room) - months is in how many months
	away the trip is. Adult,child and room are the quantity of each for the booking.Function enters all the correct parameters 
	and then searches for available listings. 
	
	StarFilter(String star) - add a filter for a certain star rating if it is available
	
	FacilFIlter(String Facil) - add a filter for a certain facility if it is available
	
	IsListed(String hotel_name) - check if a certain hotel is currently listed
	
	CloseBrowser() - close the browser window
	

Author: Michael O'Connor

Status - Working the majority of the time but on rare occasions it times out when waiting for the accept cookies pop up, even 
         with the pop up is visible on the browser. Unsure why it happens and happens only rarely.
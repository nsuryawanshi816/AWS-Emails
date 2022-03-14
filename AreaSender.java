package com.amazonaws.samples;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest; 

public class AreaSender {
	
	public static void main(String[] args) {
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/dm_data/area_outreach.csv"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(",");
		        records.add(Arrays.asList(values));
		    }
		    
		    records.remove(0);
		    
		    for(int i = 0; i < records.size(); i++) {
		    	for(int j = 0; j < records.get(i).size(); j++) {
		    		System.out.print(records.get(i).get(j) + " ");
		    	}
		    	System.out.println();
		    }
		} catch (FileNotFoundException e) {
			System.out.println("Hello");
			
		} catch (IOException e) {
			System.out.println("World");
		}
		
		for(int i = 0; i < records.size(); i++) {
			String from = records.get(i).get(5);
			String to = records.get(i).get(1);
			String receiver_name = records.get(i).get(0);
			String sender_name = records.get(i).get(4);
			String link = records.get(i).get(3);
			String area = records.get(i).get(2);
			String unsub_link = "https://docs.google.com/forms/d/e/1FAIpQLScUr5nlayqfozM5XD4A1-gegvpV0vovCdEjYlWeBKZX4Zec3w/viewform\n";
			String subject = "Northwestern University Dance Marathon " + area + " Outreach";
			
			String htmlBody = "<p>Hi " + receiver_name + ",</p>\n"
					+ "\n"
					+ "<p>I hope all is well! My name is " + sender_name + " and I’m dancing this year in Northwestern’s 48th annual Dance Marathon, which kicks off exactly 30 days from today! After two years of online programming, we are so excited to come together in the tent again to celebrate our incredible beneficiaries. </p>\n"
					+ "\n"
					+ "<p>I’m reaching out this year because I am serving as the " + area + " representative. As I grew up in " + area + ", I am super excited by the number of dancers participating from there! Would you be willing to support fundraisers from our area this year by donating <a href=\"" + link + "\">" + "here" + "</a>? </p>\n"
					+ "\n"
					+ "<p>100% of your donation will go toward our beneficiaries, Chicago Youth Programs (CYP) and the Evanston Community Foundation (ECF). Since its founding in 1984, CYP has worked to improve the life opportunities and health of youth on Chicago’s south and west sides through a variety of support networks. CYP follows students from early childhood through age 25 to ensure that they can develop the skills necessary to avoid the compounded effects of adverse childhood experiences and succeed personally, academically, and professionally. Through programs like “Read to Me” and SAT preparation workshops, CYP provides support and resources that are often difficult for youth to find due to systemic inequalities. </p>\n"
					+ "\n"
					+ "<p>In addition to CYP, NUDM is partnering with the ECF for the 25th year as our secondary beneficiary. We are so excited to be working with these two amazing organizations to give back to our local community.</p>\n"
					+ "\n"
					+ "<p>By donating <a href= \"" + link + "\">" + "here" + "</a>, you can support dancers from our area and support students served by CYP. Thank you in advance for your time and consideration, and if you have any questions, feel free to email me for more information. </p>\n"
					+ "<p> I really appreciate your support; my fundraising page can be found at "
					+ "<a href= \"" + link + "\">" + link + "</a></p>"
					+ "Best, <br>\n"
					+ sender_name
					+ "<br><br> -- <br><br>"
					+ "<footer>\n"
					+ "This email is being sent by a Registered Student Organization of Northwestern University. The Office of Alumni Relations and Development is aware of the email but did not provide any data to the group sending this email. If you would like to be removed from their list please respond to the group directly or use the provided unsubscribe link. <br>"
					+ "<a href= \"" + unsub_link + "\">" + " If you are no longer interested in receiving communication from NUDM click here." + "</a><br>  \n"
					+ "Northwestern University Dance Marathon <br>\n"
					+ "<a href= \"" + "https://www.nudm.org" + "\">" + "nudm.org" + "</a><br>"
					+ "If you have concerns pertaining to this email please call or email the Alumni Relations and Development Office of Annual Giving at 1-800-222-5603 or mailto:giving@northwestern.edu"
					+ "</footer>";
			
			String textBody = "";
			
			try {
			      AmazonSimpleEmailService client = 
			          AmazonSimpleEmailServiceClientBuilder.standard()
			            .withRegion(Regions.US_EAST_1).build();
			      SendEmailRequest request = new SendEmailRequest()
			          .withDestination(
			              new Destination().withToAddresses(to))
			          .withMessage(new Message()
			              .withBody(new Body()
			                  .withHtml(new Content()
			                      .withCharset("UTF-8").withData(htmlBody))
			                  .withText(new Content()
			                      .withCharset("UTF-8").withData(textBody)))
			              .withSubject(new Content()
			                  .withCharset("UTF-8").withData(subject)))
			          .withSource(from);
			      	System.out.println("Email sent!");
			      client.sendEmail(request);
			    } catch (Exception ex) {
			      System.out.println("from address: " + from);
				  System.out.println("to address: " + to);
				  System.out.println("receiver_name: " + receiver_name);
				  System.out.println("sender_name: " + sender_name);
				  System.out.println("link: " + link);
				  System.out.println("area: " + area);
			      System.out.println("The email was not sent. Error message: " 
			          + ex.getMessage());
			      System.out.println();
			    }
		}
		
	}
}

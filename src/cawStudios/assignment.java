package cawStudios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;

public class assignment {

	public static void main(String[] args) {
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
		driver.findElement(By.xpath("//summary[contains(text(),'Table Data')]")).click();
		String input = "[{\"name\" : \"Bob\", \"age\" : 20, \"gender\": \"male\"}, {\"name\": \"George\", \"age\" : 42, \"gender\": \"male\"}, {\"name\":\r\n"
				+ "\"Sara\", \"age\" : 42, \"gender\": \"female\"}, {\"name\": \"Conor\", \"age\" : 40, \"gender\": \"male\"}, {\"name\":\r\n"
				+ "\"Jennifer\", \"age\" : 42, \"gender\": \"female\"}]";
		WebElement textbox = driver.findElement(By.id("jsondata"));
		textbox.sendKeys(Keys.CONTROL + "a");
		textbox.sendKeys(input);
		driver.findElement(By.id("refreshtable")).click();
		Gson gson = new Gson();// parse the JSON data
		JsonArray jsonArray = gson.fromJson(input, JsonArray.class);
		// Retrieve the data from the data table
        WebElement table = driver.findElement(By.id("dynamictable"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        List<Map<String, String>> tableData = new ArrayList<>();

        for (int i = 1; i < rows.size(); i++) {
            List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));
            Map<String, String> row = new HashMap<>();
            row.put("name", columns.get(0).getText());
            row.put("age", columns.get(1).getText());
            row.put("gender", columns.get(2).getText());
            tableData.add(row);
        }

        // Compare the data
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonRow = jsonArray.get(i).getAsJsonObject();
            Map<String, String> tableRow = tableData.get(i);
            Assert.assertEquals(jsonRow.get("name").getAsString(), tableRow.get("name"));
            Assert.assertEquals(jsonRow.get("age").getAsString(), tableRow.get("age"));
            Assert.assertEquals(jsonRow.get("gender").getAsString(), tableRow.get("gender"));
        }
    }

	}


	

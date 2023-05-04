package Project_3;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CarGurus
{

    @Test
    public void carGurus() throws InterruptedException {


        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.cargurus.com/");

        driver.findElement(By.xpath("//label[@for = \"heroSearch-tab-1\"]")).click();
        Assert.assertEquals(new Select(driver.findElement(By.id("carPickerUsed_makerSelect"))).getFirstSelectedOption().getText(), "All Makes");
        new Select(driver.findElement(By.id("carPickerUsed_makerSelect"))).selectByVisibleText("Lamborghini");

        List<Integer> original = new ArrayList<>();
        Assert.assertEquals(new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).getFirstSelectedOption().getText(), "All Models");
        List<String> expectedModels = List.of("All Models", "Aventador", "Huracan", "Urus", "400GT", "Centenario", "Countach", "Diablo", "Espada", "Gallardo", "Murcielago");
        List<WebElement> models = new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).getOptions();
        List<String> actualModels = new ArrayList<>();

        for (WebElement each : models) {

            System.out.println(actualModels.add(each.getText()));

        }

        Assert.assertEquals(actualModels, expectedModels);


        new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).selectByVisibleText("Gallardo");

        driver.findElement(By.name("zip")).sendKeys("22031", Keys.ENTER);


        List<WebElement> elements = driver.findElements(By.xpath("//a[@data-cg-ft='car-blade-link'][not(contains(@href,'FEATURED'))]"));
        for (WebElement element : elements) {
            Assert.assertTrue(element.getText().contains("Lamborghini Gallardo"));
        }

        Assert.assertEquals(elements.size(), 15);

        new Select(driver.findElement(By.id("sort-listing"))).selectByVisibleText("Lowest price first");

        Thread.sleep(2000);
        List<WebElement> prices = driver.findElements(By.xpath("//div[@class='vQG_Ff']//span[@class='JzvPHo']"));
        prices.remove(0);
        for (WebElement price : prices) {
            String text = price.getText();
            original.add(Integer.parseInt(text.substring(0, text.indexOf(" ")).replaceAll("[$,]", "")));
        }


        List<Integer> sorted = new ArrayList<>(original);
        Collections.sort(sorted);

        System.out.println("orig:" + original);
        System.out.println("sorted" + sorted);

        Assert.assertEquals(original, sorted);


        new Select(driver.findElement(By.id("sort-listing"))).selectByVisibleText("Highest mileage first");
        Thread.sleep(2000);
        List<WebElement> mileages = driver.findElements(By.xpath("//p[@class='JKzfU4 umcYBP']"));
        mileages.remove(0);

        List<Integer> originalMiles = new ArrayList<>();
        for (WebElement miles : mileages) {
            String text = miles.getText();
            originalMiles.add(Integer.parseInt(text.substring(0, text.indexOf(" ")).replace(",", "")));
        }

        List<Integer> sortedMiles = new ArrayList<>(originalMiles);
        Collections.sort(sortedMiles, Collections.reverseOrder());

        System.out.println("origMiles:" + originalMiles);
        System.out.println("sortedMiles:" + sortedMiles);

        Assert.assertEquals(originalMiles, sortedMiles);

        driver.findElement(By.xpath("//p[contains(.,'Coupe AWD')]")).click();
        Thread.sleep(2000);
        List<WebElement> elements2 = driver.findElements(By.xpath("//a[@data-cg-ft='car-blade-link']"));
        for (WebElement element : elements2) {
            Assert.assertTrue(element.getText().contains("Coupe AWD"));
        }

        elements2.get(elements2.size() - 1).click();
        Thread.sleep(2000);
        driver.navigate().back();
        Thread.sleep(2000);
        Assert.assertEquals(driver.findElement(By.xpath("//p[contains(.,'Viewed')]")).getText(), "Viewed");
        driver.quit();

    }
}

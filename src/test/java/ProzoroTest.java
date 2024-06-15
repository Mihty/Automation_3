
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;


public class ProzoroTest extends BaseTest {

    @Test
    public void ProzzoroTest() {
        driver.get("https://prozorro.gov.ua/");

        String searchValue = "Тумба";
        WebElement searchInput = driver.findElement(By.className("search-text__input"));
        searchInput.sendKeys(searchValue);
        searchInput.submit();

        List<WebElement> itemHeaders = new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("item-title__title")));

        List<WebElement> itemStatus = new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("search-result-card__label")));

       List <WebElement> itemPlace = new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("search-result-card__description")));

        List <WebElement> itemPrice = new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("p.text-color--green.app-price__amount")));


        if (!itemHeaders.isEmpty() && !itemStatus.isEmpty()) {

            String expectedTitle = itemHeaders.get(0).getText().trim();
            String expectedStatus = itemStatus.get(0).getText().trim();

            String Place = itemPlace.get(0).getText().trim();
            String[] titleParts = Place.split("•");
            String expectedPlace = titleParts[0].trim();

            String Price= itemPrice.get(0).getAttribute("textContent").trim();
            String expectedPrice = Price.replace(" UAH", "").trim();
            System.out.println("значение 1: " + expectedPrice);
            itemHeaders.get(0).click();


            String actualTitle = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'tender--head--title')]")))
                    .getText().trim();

            String actualStatus = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='marked']")))
                    .getText().trim();

            String actualPlace = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td[contains(@class, 'col-sm-6')])[1]")))
                    .getText().trim();

            String Price2 = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//strong)[1]")))
                    .getText().trim();

            String actualPrice = Price2.replace("UAH", "")
                            .trim();

            System.out.println("значение 2: " + actualPrice);
            Assert.assertEquals(actualTitle, expectedTitle, "Elements do not match");
            Assert.assertEquals(actualStatus, expectedStatus, "Elements do not match");
            Assert.assertEquals(actualPlace, expectedPlace, "Elements do not match");
            Assert.assertEquals(actualPrice, expectedPrice, "Elements do not match");
        } else {
            Assert.fail("No search results found");
        }
    }

}
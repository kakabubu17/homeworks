import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PersonalPage extends AbstractPage {

    public PersonalPage(WebDriver driver) {
        super(driver);
    }

    String nameTb = "//input[@name='fname']";
    String lastNameTb = "//input[@name='lname']";
    String dateTb = "//input[@name='date_of_birth']";
    String countryTb = "//p[contains(text(),'Страна')]/../following-sibling::div/div";
    String countryName = "//button[@title='%1']";
    String cityTb = "//p[contains(text(),'Город')]/../following-sibling::div/div";
    String cityName = "//button[@title='%1']";
    String langTb = "//p[contains(text(),'Уровень')]/../following-sibling::div/div";
    String langLvl = "//div[contains(@class, 'scroll  js-custom-select-options')]/button[contains(@title, '%1')]";
    String contactSb = "//div[contains(@class, 'md-4')]/label/..";
    String contactType = "//button[@title='%1']";
    String contactInput = "//div[contains(@class, 'custom-select')]/../input[contains(@id, 'id_contact')]";
    String addBtn = "//button[.='Добавить']";
    String btnSubmit = "//button[@title='Сохранить и продолжить']";
    String contactTypes = "//div[contains(@data-selected-option-class, 'option_selected')]//input[contains(@name, 'contact')]";
    String contactValues = "//div[contains(@class, 'container')]/input[contains(@id, 'id_contact') and @type='text']";
    String deleteContactBtn = "//div[@data-num='$1']//button[.='Удалить']";

    public PersonalPage enterName(String name) {
        sendText(name, nameTb);
        return this;
    }

    public PersonalPage enterLastName(String lastName)
    {
        sendText(lastName, lastNameTb);
        return this;
    }
    public PersonalPage enterDate(String date)
    {
        sendText(date + "\n", dateTb);
        return this;
    }

    public PersonalPage enterCountry(String country)
    {
        clickElement(countryTb);
        clickElement(countryName.replace("%1", country));
        return this;
    }

    public PersonalPage enterCity(String city)
    {
        clickElement(cityTb);
        clickElement(cityName.replace("%1", city));
        return this;
    }

    public PersonalPage enterLanguageLevel(String level)
    {
        clickElement(langTb);
        clickElement(langLvl.replace("%1", level));
        return this;
    }

    public PersonalPage addContact(String option, String text)
    {
        List <WebElement> elemsInput = driver.findElements(By.xpath(contactInput));
        String textInput = elemsInput.get(elemsInput.size()-1).getAttribute("value"); //текст последнего контакта
        if (!textInput.isEmpty())
        {
            clickElement(addBtn);
            elemsInput = driver.findElements(By.xpath(contactInput));
        }

        List<WebElement> elems = driver.findElements(By.xpath(contactSb));
        Integer numberOfContacts = elems.size();
        elems.get(numberOfContacts - 1).click();
        List <WebElement> elemsType = driver.findElements(By.xpath(contactType.replace("%1", option)));
        elemsType.get(numberOfContacts - 1).click();
        elemsInput.get(numberOfContacts-1).sendKeys(text);
        return this;

    }

    public PersonalPage submit()
    {
        clickElement(btnSubmit);
        return this;
    }

    public PersonalPage deleteContacts(int num)
    {
        WebElement elem =  driver.findElements(By.xpath(deleteContactBtn.replace("$1", String.valueOf(num)))).get(1);
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(elem));
        elem.click();
        return this;
    }
    public String getCountryText() { return driver.findElement(By.xpath(countryTb)).getText(); }

    public String getCityText() { return driver.findElement(By.xpath(cityTb)).getText(); }

    public String getNameText()  {  return driver.findElement(By.xpath(nameTb)).getAttribute("value");  }

    public String getLastNameText()  {  return driver.findElement(By.xpath(lastNameTb)).getAttribute("value");  }

    public String getDayText()  {  return driver.findElement(By.xpath(dateTb)).getAttribute("value");  }

    public String getLangLvlText()  {  return driver.findElement(By.xpath(langTb)).getText();  }

    public String getContactTypeText(int num)  {  return driver.findElements(By.xpath(contactTypes)).get(num).getAttribute("value");  }

    public String getContactValueText(int num)  {  return driver.findElements(By.xpath(contactValues)).get(num).getAttribute("value");  }

}

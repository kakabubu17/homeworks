import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PersonalPage extends AbstractPage {

    public PersonalPage(WebDriver driver) {
        super(driver);
    }

    public static String nameField = "//input[@name='fname']";
    public static String lastNameTb = "//input[@name='lname']";
    public static String dateTb = "//input[@name='date_of_birth']";
    public static String countryTb = "//p[contains(text(),'Страна')]/../following-sibling::div/div";
    public String countryName = "//button[@title='%1']";
    public static String cityTb = "//p[contains(text(),'Город')]/../following-sibling::div/div";
    String cityName = "//button[@title='%1']";
    public static String langTb = "//p[contains(text(),'Уровень')]/../following-sibling::div/div";
    String langLvl = "//div[contains(@class, 'scroll  js-custom-select-options')]/button[contains(@title, '%1')]";
    String communicationSb = "//div[contains(@class, 'md-4')]/label/..";
    String communicationType = "//button[@title='%1']";
    String communicationInput = "//div[contains(@class, 'custom-select')]/../input[contains(@id, 'id_contact')]";
    String addBtn = "//button[.='Добавить']";
    String btnSubmit = "//button[@title='Сохранить и продолжить']";

    public PersonalPage enterName(String name) {
        sendText(name, nameField);
        return this;
    }

    public void enterLastName(String lastName) { sendText(lastName, lastNameTb); }
    public void enterDate(String date) { sendText(date + "\n", dateTb); }

    public void enterCountry(String country)
    {
        clickElement(countryTb);
        clickElement(countryName.replace("%1", country));
    }

    public void enterCity(String city)
    {
        clickElement(cityTb);
        clickElement(cityName.replace("%1", city));
    }

    public void enterLanguageLevel(String level)
    {
        clickElement(langTb);
        clickElement(langLvl.replace("%1", level));
    }

    public void addContact(String option, String text)
    {
        List <WebElement> elemsInput = driver.findElements(By.xpath(communicationInput));
        String textInput = elemsInput.get(elemsInput.size()-1).getAttribute("value"); //текст последнего контакта
        if (!textInput.isEmpty())
        {
            clickElement(addBtn);
            elemsInput = driver.findElements(By.xpath(communicationInput));
        }
        List<WebElement> elems = driver.findElements(By.xpath(communicationSb));
        Integer numberOfContacts = elems.size();
        elems.get(numberOfContacts - 1).click();
        List <WebElement> elemsType = driver.findElements(By.xpath(communicationType.replace("%1", option)));
        elemsType.get(numberOfContacts - 1).click();
        elemsInput.get(numberOfContacts-1).sendKeys(text);

    }

    public void submit() { clickElement(btnSubmit); }



}

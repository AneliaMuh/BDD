package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.w3c.dom.Text;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list__item div");
    private final SelenideElement reloadButton = $("[data-test-id=action-reload]");

    public DashboardPage(){
        heading.shouldBe(Condition.visible);
    }

    public int getCardBalance(String maskedCarNumber) {
        var text = cards.findBy(Condition.text(maskedCarNumber)).getText();
        return extractBalance(text);
    }

    public int getCardBalance(int index){
        var text = cards.get(index).getText();
        return extractBalance(text);
    }

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
        cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestId())).$("button").click();
        return new TransferPage();
    }

    public void reloadDashboardPage(){
        reloadButton.click();
        heading.shouldBe(Condition.visible);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }


}

package pages;

import assets.Advert;
import assets.Condition;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Page {

    static String page;
    static String url;

    private Integer pageIterator = 1;
    private static final Integer ENDPAGE = 1;//TODO:

    List<Advert> advertList = new ArrayList<>();

    public abstract void getAdverts();

    public abstract void getAdvertDetails(Advert advert);

    public void getAdvertsDetails() {
        for (Advert advert : advertList)
            getAdvertDetails(advert);
    }

    Document getMainPage() {
        if (pageIterator > ENDPAGE) {
            pageIterator = 1;
        }
        return getPage(url + pageIterator++);
    }

    Document getPage(String url) {
        Document document = null;
        do {
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                // If there is a problem with the internet connection show info and wait
                System.out.println("----------ERROR----------> There is problem with the internet connection. Reconnecting in 15 seconds...");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        while (document == null);
        return document;
    }

//    private boolean valueExists(Advert advert, Object value) {
//        if (value == null && !advert.getGotDetails()) {
//            getAdvertDetails(advert);
//            return valueExists(advert, value);
//        } else return value != null;
//    }

    public List<Advert> checkCondition(Condition condition) {

        List<Advert> advertsCondition = new ArrayList<>();

        for (Advert advert : advertList) {

            boolean isFulFilled = true;

            if (condition.getPriceFilter() != null) {
                if (advert.getPrice() == null && !advert.getGotDetails()) {
                    getAdvertDetails(advert);
                }
                if (advert.getPrice() == null && advert.getGotDetails())
                    System.out.println("There is no value");
                else
                    isFulFilled = condition.getPriceFilter() >= advert.getPrice();
            }

            if (isFulFilled && condition.getAreaFilter() != null) {
                if (advert.getArea() == null && !advert.getGotDetails()) {
                    getAdvertDetails(advert);
                }
                if (advert.getArea() == null && advert.getGotDetails())
                    System.out.println("There is no value");
                else
                    isFulFilled = condition.getAreaFilter() <= advert.getArea();
            }

            if (isFulFilled && condition.getLocationNoFilter() != null) {
                if (advert.getLocation() == null && !advert.getGotDetails()) {
                    getAdvertDetails(advert);
                }
                if (advert.getLocation() == null && advert.getGotDetails())
                    System.out.println("There is no value");
                else
                    isFulFilled = Condition.stringInStrings(advert.getLocation(), condition.getLocationNoFilter());
            }
            if (isFulFilled && condition.getLocationYesFilter() != null) {
                if (advert.getLocation() == null && !advert.getGotDetails()) {
                    getAdvertDetails(advert);
                }
                if (advert.getLocation() == null && advert.getGotDetails())
                    System.out.println("There is no value");
                else
                    isFulFilled = !Condition.stringInStrings(advert.getLocation(), condition.getLocationYesFilter());
            }
            if (isFulFilled)
                advertsCondition.add(advert);
        }
        return advertsCondition;
    }
}

package pages;

import assets.Advert;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Gratka extends Page {

    public Gratka() {
        page = "Gratka";
        url = "https://gratka.pl/nieruchomosci/warszawa?page=";
    }

    @Override
    public void getAdverts() {
        Document document = getMainPage();

        Elements offers = document.select("article.teaserEstate");

        for (Element offer : offers) {
            Advert newAdvert = new Advert();
            newAdvert.setPage(page);
            newAdvert.setName(offer.select("a.teaserEstate__anchor").text());
            newAdvert.setUrl(offer.select("a.teaserEstate__anchor").attr("href"));
            newAdvert.setPrize(offer.select("p.teaserEstate__price").first().ownText());
            newAdvert.setPricePerMeter(offer.select("span.teaserEstate__additionalPrice").text());
            newAdvert.setArea(offer.select("ul.teaserEstate__offerParams").select("li").first().text());
            newAdvert.setLocation(offer.select("span.teaserEstate__localization").text());
            newAdvert.setFrontPhotoUrl(offer.select("img.teaserEstate__img").attr("data-src"));
            advertList.add(newAdvert);
        }
    }

    @Override
    public void getAdvertDetails(Advert advert) {
        Document document = getPage(advert.getUrl());
        advert.setContent(document.select("div.ql-container").text());

        Elements photos = document.select("meta[property=og:image]");
        for (Element photo : photos) {
            advert.addPhoto(photo.attr("content"));
        }
        advert.setMoreInfo(document.select("ul.parameters__rolled").select("li").text());
        advert.setGotDetails(true);
    }
}

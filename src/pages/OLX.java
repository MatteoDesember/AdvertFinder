package pages;

import assets.Advert;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OLX extends Page {

    public OLX() {
        page = "OLX";
        url = "https://www.olx.pl/nieruchomosci/mieszkania/sprzedaz/warszawa/?page=";
    }

    @Override
    public void getAdverts() {

        Document document = getMainPage();

        Elements offers = document.select("table#offers_table").select("tr.wrap:not([rel=external])");

        for (Element offer : offers) {
            Advert newAdvert = new Advert();
            newAdvert.setPage(page);
            newAdvert.setName(offer.select("h3.lheight22.margintop5").text());
            newAdvert.setUrl(offer.select("h3.lheight22.margintop5").select("a").attr("href"));
            newAdvert.setPrize(offer.select("p.price").text());
            newAdvert.setLocation(offer.select("i[data-icon=location-filled]").parents().first().text());
            newAdvert.setFrontPhotoUrl(offer.select("img.fleft").attr("src"));
            advertList.add(newAdvert);
        }
    }

    @Override
    public void getAdvertDetails(Advert advert) {
        Document document = getPage(advert.getUrl());
        advert.setContent(document.select("div#textContent").text());
        Elements details = document.select("li.offer-details__item");
        for (Element detail : details) {
            if (detail.select("span.offer-details__name").text().equals("Cena za m²"))
                advert.setPricePerMeter(detail.select("strong.offer-details__value").text());
            else if (detail.select("span.offer-details__name").text().equals("Powierzchnia"))
                advert.setArea(detail.select("strong.offer-details__value").text());
        }

        Elements images = document.select("ul#bigGallery").select("a");
        for (Element image : images)
            advert.addPhoto(image.attr("href"));

        advert.setMoreInfo(document.select("ul.offer-bottombar__items").text());
        advert.setGotDetails(true);
    }
}

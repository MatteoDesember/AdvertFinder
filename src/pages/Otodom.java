package pages;

import assets.Advert;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Otodom extends Page {

    public Otodom() {
        page = "OTODOM";
        url = "https://www.otodom.pl/sprzedaz/warszawa/?nrAdsPerPage=72&search%5Border%5D=created_at_first%3Adesc&page=";
    }

    @Override
    public void getAdverts() {
        Document document = getMainPage();
        Elements offers = document.select("article[data-featured-name=\"listing_no_promo\"]");
        for (Element offer : offers) {
            Advert newAdvert = new Advert();
            newAdvert.setPage(page);
            newAdvert.setName(offer.select("span.offer-item-title").text());
            newAdvert.setUrl(offer.select("article").attr("data-url"));
            newAdvert.setPrize(offer.select("li.offer-item-price").text());
            newAdvert.setArea(offer.select("strong.visible-xs-block").text());
            newAdvert.setLocation(offer.select("p.text-nowrap").first().ownText());
            newAdvert.setPricePerMeter(offer.select("li.offer-item-price-per-m").text());
            newAdvert.setFrontPhotoUrl(offer.select("span.img-cover").attr("data-src"));
            advertList.add(newAdvert);
        }
    }

    @Override
    public void getAdvertDetails(Advert advert) {
        Document document = getPage(advert.getUrl());
        advert.setContent(document.select("div.css-1bi3ib9").text());
//        Elements details = doc.select("div.css-dwmx8v-Fe").select("li");
//        for (Element detail : details) {}//TODO
        Elements images = document.select("div.css-d8fgxy").select("img");
        for (Element image : images)
            advert.addPhoto(image.attr("src"));

        advert.setMoreInfo(document.select("div.css-1bpegon").text());
        advert.setGotDetails(true);
    }
}
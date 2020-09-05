package pages;

import assets.Advert;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Gumtree extends Page {

    public Gumtree() {
        page = "Gumtree";
        url = "https://www.gumtree.pl/s-mieszkania-i-domy-sprzedam-i-kupie/warszawa/v1c9073l3200008p";
    }

    @Override
    public void getAdverts() {

        Document document = getMainPage();

        Elements offers = document.select("div.view").select("div.tileV1");

        for (Element offer : offers) {
            Advert newAdvert = new Advert();
            newAdvert.setPage(page);
            newAdvert.setName(offer.select("div.title").text());
            newAdvert.setUrl(offer.select("a.tile-title-text").first().absUrl("href"));
            newAdvert.setPrize(offer.select("span.ad-price").text());
            newAdvert.setLocation(offer.select("div.category-location").text());
            newAdvert.setFrontPhotoUrl(offer.select("img.lazyload").attr("data-src"));
            advertList.add(newAdvert);
        }
    }

    @Override
    public void getAdvertDetails(Advert advert) {
        Document document = getPage(advert.getUrl());
        advert.setContent(document.select("div.description").text());

        Elements details = document.select("ul.selMenu").select("li");
        for (Element detail : details) {
            if (detail.select("span.name").text().equals("Wielkość (m2)")) {
                advert.setArea(detail.select("span.value").text());
                if (advert.getPrice() != null)
                    advert.setPricePerMeter(advert.getPrice() / advert.getArea());
            }
        }

        Elements images = document.select("img[data-index]");
        for (Element image : images)
            advert.addPhoto(image.attr("src"));

        advert.setMoreInfo(document.select("ul.selMenu").text());
        advert.setGotDetails(true);
    }
}

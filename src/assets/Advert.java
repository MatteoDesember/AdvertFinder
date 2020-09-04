package assets;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;


public class Advert {

    private Long timeStamp;
    private Long timeStampChange;
    private String uniqueForMany;
    private Integer db_advert_id;
    private String advert_id;
    private Boolean isActive;
    private Integer counter;
    private Boolean similar;

    private String name;
    private String url;
    private String page;
    private String frontPhotoUrl;
    private String location;
    private Double price;
    private Double pricePerMeter;
    private Double area;
    private String content;
    private String contentToCompare;
    private String moreInfo;
    private Boolean gotDetails = false;
    private List<String> photos = new ArrayList<>();


    public Advert() {
        this.uniqueForMany = UUID.randomUUID().toString();
        this.timeStamp = new GregorianCalendar().getTimeInMillis();
        this.timeStampChange = this.timeStamp;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\r\n" +
                "Prize: " + this.price + "\r\n" +
                "Location: " + this.location + "\r\n" +
                this.url + "\r\n";
    }

    public String getUniqueForMany() {
        return uniqueForMany;
    }

    public void setUniqueForMany(String uniqueForMany) {
        this.uniqueForMany = uniqueForMany;
    }

    public String getPhotos() {
        return photos.toString();
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public void addPhoto(String photo) {
        this.photos.add(photo);
    }

    public Boolean getGotDetails() {
        return gotDetails;
    }

    public void setGotDetails(Boolean gotDetails) {
        this.gotDetails = gotDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.replaceAll("\n", " ");
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getTimeStampChange() {
        return timeStampChange;
    }

    public void setTimeStampChange(Long timeStampChange) {
        this.timeStampChange = timeStampChange;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPrize(String price) {
        if (!price.isEmpty())
            this.price = Double.parseDouble(price.replaceAll("[^\\d,]", "").replaceAll(",", "."));
    }

    public Double getPricePerMeter() {
        return pricePerMeter;
    }

    public void setPricePerMeter(Double pricePerMeter) {
        this.pricePerMeter = pricePerMeter;
    }

    public void setPricePerMeter(String pricePerMeter) {
        if (!pricePerMeter.isEmpty())
            this.pricePerMeter = Double.parseDouble(pricePerMeter.replaceAll("[^\\d,]", "").replaceAll(",", "."));
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public void setArea(String area) {
        if (!area.isEmpty())
            this.area = Double.parseDouble(area.replaceAll("[^\\d,]", "").replaceAll(",", "."));

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFrontPhotoUrl() {
        return frontPhotoUrl;
    }

    public void setFrontPhotoUrl(String frontPhotoUrl) {
        this.frontPhotoUrl = frontPhotoUrl;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentToCompare() {
        return contentToCompare;
    }

    private void setContentToCompare(String contentToCompare) {
        this.contentToCompare = contentToCompare;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Boolean getSimilar() {
        return similar;
    }

    public void setSimilar(Boolean similar) {
        this.similar = similar;
    }

}

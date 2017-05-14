package com.example.user.oicsch;

import java.util.List;

/**
 * Created by user on 2/25/2017.
 */

public class ResponseModel {

    /**
     * status : ok
     * source : techcrunch
     * sortBy : latest
     * articles : [{"author":"Khaled \"Tito\" Hamze","title":"Crunch Report | Tesla and Rollercoasters","description":"Waymo is suing Otto and Uber for allegedly stealing its trade secrets, Layer gets more funding and acquires Cola, a major Cloudflare bug leaked sensitive data..","url":"https://techcrunch.com/2017/02/24/crunch-report-tesla-and-rollercoasters/","urlToImage":"https://img.vidible.tv/prod/2017-02/24/58b0bbdfc59f802f55641a59/58b0bcb7ad5cd352eab434af_o_U_v1.png?w=764&h=400","publishedAt":"2017-02-25T03:00:02Z"},{"author":"Kate Conger","title":"How to secure your data after the Cloudflare leak","description":"Cloudflare revealed yesterday that a bug in its code caused sensitive data to leak from some of the major websites that use its performance enhancement and..","url":"https://techcrunch.com/2017/02/24/how-to-secure-your-data-after-the-cloudflare-leak/","urlToImage":"https://tctechcrunch2011.files.wordpress.com/2016/06/securityhall.jpg?w=764&h=400&crop=1","publishedAt":"2017-02-24T23:47:59Z"},{"author":"Devin Coldewey","title":"Super Smash Borg Melee: AI takes on top players of the classic Nintendo fighting game","description":"You can add the cult classic Super Smash Bros Melee to the list of games soon to be dominated by AIs. Research at MIT's Computer Science and Artificial..","url":"https://techcrunch.com/2017/02/24/super-smash-borg-melee-ai-takes-on-top-players-of-the-classic-nintendo-fighting-game/","urlToImage":"https://tctechcrunch2011.files.wordpress.com/2017/02/smashbros.jpg?w=764&h=400&crop=1","publishedAt":"2017-02-24T23:26:17Z"},{"author":"Lucas Matney","title":"The NBA gets high-tech with VR, drones and futuristic dreams","description":"https://www.youtube.com/watch?v=Dtdr1vlf52k This past weekend, something kind of incredible happened; basketball aficionados from across the globe were able..","url":"https://techcrunch.com/2017/02/24/the-nba-gets-high-tech-with-virtual-reality-drones-and-big-dreams/","urlToImage":"https://tctechcrunch2011.files.wordpress.com/2016/10/gettyimages-613796716.jpg?w=764&h=400&crop=1","publishedAt":"2017-02-24T23:14:26Z"},{"author":"Matt Burns","title":"Announcing the Startup Battlefield Scholarship Fund for Disrupt NY 2017","description":"After the success of the Startup Battlefield Scholarship Fund at Disrupt SF and Disrupt London, we are pleased to announce that we will once again be..","url":"https://techcrunch.com/2017/02/24/announcing-the-startup-battlefield-scholarship-fund-for-disrupt-ny-2017/","urlToImage":"https://tctechcrunch2011.files.wordpress.com/2015/06/9726191699_3eb5ca3b23_k-compressor.jpg?w=764&h=400&crop=1","publishedAt":"2017-02-24T23:00:13Z"},{"author":"Sarah Buhr","title":"Uber says it\u2019s \u2018absolutely not\u2019 behind a smear campaign against ex-employee Susan Fowler Rigetti","description":"Uber is under attack once again today as the Twittersphere quickly concluded it was behind a smear campaign against a former engineer for the company, Susan..","url":"https://techcrunch.com/2017/02/24/uber-says-its-absolutely-not-behind-a-smear-campaign-against-ex-employee-susan-fowler-rigetti/","urlToImage":"https://tctechcrunch2011.files.wordpress.com/2017/01/gettyimages-541025592.jpg?w=764&h=400&crop=1","publishedAt":"2017-02-24T22:42:59Z"},{"author":"Lora Kolodny","title":"DARTdrones pitches Shark Tank to build a flight school for drone pilots","description":"Entrepreneurs who pitch on ABC's Shark Tank typically make packaged goods and apparel. Occasionally, the high tech breaks through. XCraft, the company..","url":"https://techcrunch.com/2017/02/24/dartdrones-pitches-shark-tank-to-build-a-flight-school-for-drone-pilots/","urlToImage":"https://tctechcrunch2011.files.wordpress.com/2017/02/sharktank_dartdrones_abby_speicher.jpg?w=764&h=400&crop=1","publishedAt":"2017-02-24T22:34:54Z"},{"author":"Brooks Rainwater, Nicole DuPuis","title":"Smart cities must be people-centered, equitable cities","description":"Technology has always been a critical force deeply intertwined with the evolution of cities. The development of smart cities builds upon this strong..","url":"https://techcrunch.com/2017/02/24/smart-cities-must-be-people-centered-equitable-cities/","urlToImage":"https://tctechcrunch2011.files.wordpress.com/2017/02/gettyimages-611997318.jpg?w=764&h=400&crop=1","publishedAt":"2017-02-24T22:00:39Z"},{"author":"Matt Burns","title":"Extra early-bird ticket deadline extended until March 3","description":"Want to attend Disrupt NY 2017 but missed the early bird ticket deadline? You're in luck! We're giving you a few extra days to get your hands on..","url":"https://techcrunch.com/2017/02/24/extra-early-bird-ticket-deadline-extended-until-march-3/","urlToImage":"https://tctechcrunch2011.files.wordpress.com/2017/01/tc-disrupt-ny-17-flatiron-image.png?w=730&h=400&crop=1","publishedAt":"2017-02-24T22:00:02Z"},{"author":"Contributor","title":"Doug creates custom Windows folder icons","description":"Tired of living life by the defaults - default ringtone, default desktop background, default Twitter avatar - gadget reviewer Doug Aamoth decides to live a..","url":"https://techcrunch.com/video/doug-creates-custom-windows-folder-icons/58b0b29fba82aa2a6fb37a24/","urlToImage":"https://img.vidible.tv/prod/2017-02/24/58b0b29fba82aa2a6fb37a24/58b0b59d50954970ce57c0ea_o_U_v1.png?w=764&h=400","publishedAt":"2017-02-24T21:39:20Z"}]
     */

    private String status;
    private String source;
    private String sortBy;
    private List<ArticlesBean> articles;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean {
        /**
         * author : Khaled "Tito" Hamze
         * title : Crunch Report | Tesla and Rollercoasters
         * description : Waymo is suing Otto and Uber for allegedly stealing its trade secrets, Layer gets more funding and acquires Cola, a major Cloudflare bug leaked sensitive data..
         * url : https://techcrunch.com/2017/02/24/crunch-report-tesla-and-rollercoasters/
         * urlToImage : https://img.vidible.tv/prod/2017-02/24/58b0bbdfc59f802f55641a59/58b0bcb7ad5cd352eab434af_o_U_v1.png?w=764&h=400
         * publishedAt : 2017-02-25T03:00:02Z
         */

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;



    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
}

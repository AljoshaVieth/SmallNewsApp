package eu.aljoshavieth.simplevirus;

import java.io.Serializable;

/**
 * Created by Aljosha Vieth on 04.06.2016.
 * https://aljoshavieth.eu
 */

public class Article implements Serializable{

    //set the schema of the ArticleObject :

    private String imageUrl;
    private String articleTitle;
    private String url;
    private String description;
    private String category;

    public Article(String imageUrl, String title, String url, String description, String category) {
        this.imageUrl = imageUrl;
        this.articleTitle = title;
        this.url = url;
        this.description = description;
        this.category = category;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String title) {
        this.articleTitle = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

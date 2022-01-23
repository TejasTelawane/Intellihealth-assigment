package com.interview.truemedinterview.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ArticlesModel implements Serializable {

    private Result result;


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result implements Serializable{
        private ArrayList<Category> category;
        private ArrayList<Article> article;

        public ArrayList<Category> getCategory() {
            return category;
        }

        public void setCategory(ArrayList<Category> category) {
            this.category = category;
        }

        public ArrayList<Article> getArticle() {
            return article;
        }

        public void setArticle(ArrayList<Article> article) {
            this.article = article;
        }
    }

// Article.java

    public class Article implements Serializable{
        private String description;
        private String categoryName;
        private long type;
        private String image;
        private long articleTime;
        private long ranking;
        private String author;
        private long categoryID;
        private String name;
        private long id;
        private String url;
        private String createdOn;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public long getType() {
            return type;
        }

        public void setType(long type) {
            this.type = type;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public long getArticleTime() {
            return articleTime;
        }

        public void setArticleTime(long articleTime) {
            this.articleTime = articleTime;
        }

        public long getRanking() {
            return ranking;
        }

        public void setRanking(long ranking) {
            this.ranking = ranking;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public long getCategoryID() {
            return categoryID;
        }

        public void setCategoryID(long categoryID) {
            this.categoryID = categoryID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }
    }

// Category.java

    public class Category implements Serializable{
        private String name;
        private long id;

    }

}

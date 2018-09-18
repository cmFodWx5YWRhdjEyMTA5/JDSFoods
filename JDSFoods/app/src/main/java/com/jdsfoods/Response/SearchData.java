package com.jdsfoods.Response;

public class SearchData {

    String categoryId;

    public SearchData(String categoryId, String productId, String searchName, String result,String childCategoryCounter) {
        this.categoryId = categoryId;
        this.productId = productId;
        this.searchName = searchName;
        this.result = result;
        this.childCategoryCounter = childCategoryCounter;
    }

    String productId;
    String searchName;
    String result;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductId() {
        return productId;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public void setProductId(String productId) {

        this.productId = productId;
    }



    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public String getChildCategoryCounter() {
        return childCategoryCounter;
    }

    public void setChildCategoryCounter(String childCategoryCounter) {
        this.childCategoryCounter = childCategoryCounter;
    }


    String childCategoryCounter;

}

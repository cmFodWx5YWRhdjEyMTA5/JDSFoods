package com.jdsfoods.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 31-07-2018.
 */

public class SearchResponse {

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product.Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Product.Category> categoryList) {
        this.categoryList = categoryList;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @SerializedName("flag")
    @Expose

    public Integer flag;

    @SerializedName("product")
    @Expose
    public Product product;

    @SerializedName("category")
    @Expose
    List<Product.Category> categoryList;

    @SerializedName("image_path")
    @Expose
    public String imagePath;

    public class Product {

        @SerializedName("list")
        @Expose
        List<ProductList> productListList;

        public List<ProductList> getProductListList() {
            return productListList;
        }

        public void setProductListList(List<ProductList> productListList) {
            this.productListList = productListList;
        }

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories(List<Category> categories) {
            this.categories = categories;
        }

        @SerializedName("category")
        @Expose
        List<Category> categories;

        public class ProductList {

            @SerializedName("product_id")
            @Expose
            public String productId;

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            @SerializedName("name")
            @Expose
            public String name;

            @SerializedName("result")
            @Expose
            public String result;

        }

        public class Category {

            @SerializedName("category_id")
            @Expose
            public String categoryId;

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getChildCategoryCounter() {
                return childCategoryCounter;
            }

            public void setChildCategoryCounter(String childCategoryCounter) {
                this.childCategoryCounter = childCategoryCounter;
            }

            @SerializedName("category_name")
            @Expose

            public String categoryName;

            @SerializedName("child_category_counter")
            @Expose
            public String childCategoryCounter;

        }
    }
}

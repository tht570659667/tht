package com.how2java.tmall.pojo;

import java.util.List;

/*
* Category新增两个瞬时字段products和productsByRow。
 List<Product> products;
List<List<Product>> productsByRow;
products比较好理解，代表一个分类下有多个产品。
productsByRow这个属性的类型是List<List<Product>> productsByRow。
即一个分类又对应多个 List<Product>，提供这个属性，是为了在首页竖状导航的分类名称右边显示推荐产品列表。
* */
public class Category {
    private Integer id;

    private String name;

    /*如下是非数据库字段*/
    private List<Product> products;

    private List<List<Product>> productsByRow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }
}
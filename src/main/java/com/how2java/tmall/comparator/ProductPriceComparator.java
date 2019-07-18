package com.how2java.tmall.comparator;

import java.util.Comparator;

import com.how2java.tmall.pojo.Product;

public class ProductPriceComparator implements Comparator<Product>{

    @Override
    public int compare(Product p1, Product p2) {
        // 返回值为int类型，大于0表示正序，小于0表示逆序，这里用的是降序
        return (int) (p2.getPromotePrice()-p1.getPromotePrice());
    }

}
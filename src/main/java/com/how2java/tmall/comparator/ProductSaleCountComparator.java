package com.how2java.tmall.comparator;

import java.util.Comparator;

import com.how2java.tmall.pojo.Product;



/*
* int方法返回值的具体意义如下：
负整数 	当前对象的值 < 比较对象的值 ， 位置排在前
零 	当前对象的值 = 比较对象的值 ， 位置不变
正整数 	当前对象的值 > 比较对象的值 ， 位置排在后
在这个比较器中，比的是两个产品之间的销售量。
* */

public class ProductSaleCountComparator implements Comparator<Product>{

    @Override
    public int compare(Product p1, Product p2) {
        return p2.getSaleCount()-p1.getSaleCount();
    }

}
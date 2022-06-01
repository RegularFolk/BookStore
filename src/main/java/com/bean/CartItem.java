package com.bean;

import java.math.BigDecimal;

public class CartItem {
    private Integer bookId;
    private String bookName;
    private String imgPath;
    private Double price;  //商品单价
    private Integer count;
    private Double amount; //购物项金额

    @Override
    public String toString() {
        return "CartItem{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", amount=" + amount +
                '}';
    }

    public CartItem() {
    }

    public CartItem(Integer bookId, String bookName, String imgPath, Double price, Integer count, Double amount) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.imgPath = imgPath;
        this.price = price;
        this.count = count;
        this.amount = amount;
    }


    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        BigDecimal bigDecimalPrice = new BigDecimal(this.price + "");
        BigDecimal bigDecimalCount = new BigDecimal(this.count + "");
        this.amount = bigDecimalCount.multiply(bigDecimalPrice).doubleValue();
        return this.amount;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 数量加一
     */
    public void countIncrease() {
        this.count++;
    }

    /**
     * 数量减一
     */
    public void countDecrease() {
        this.count--;
    }
}

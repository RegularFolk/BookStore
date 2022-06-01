package com.bean;

public class OrderItem {
    private Integer itemId;
    private String bookName;
    private Double price;
    private String imgPath;
    private Integer itemCount;
    private Double itemAmount;
    private Integer orderId;

    public OrderItem() {
    }

    public OrderItem(Integer itemId, String bookName, Double price, String imgPath, Integer itemCount, Double itemAmount, Integer orderId) {
        this.itemId = itemId;
        this.bookName = bookName;
        this.price = price;
        this.imgPath = imgPath;
        this.itemCount = itemCount;
        this.itemAmount = itemAmount;
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemId=" + itemId +
                ", bookName='" + bookName + '\'' +
                ", price=" + price +
                ", imgPath='" + imgPath + '\'' +
                ", itemCount=" + itemCount +
                ", itemAmount=" + itemAmount +
                ", orderId=" + orderId +
                '}';
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Double getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Double itemAmount) {
        this.itemAmount = itemAmount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}

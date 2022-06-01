package com.bean;

public class Order {
    private Integer orderId;
    private String orderSequence;
    private String createTime;
    private Integer totalCount;
    private Double totalAmount;
    private Integer orderStatus;
    private Integer userId;

    public Order() {
    }

    public Order(Integer orderId, String orderSequence, String createTime, Integer totalCount, Double totalAmount, Integer orderStatus, Integer userId) {
        this.orderId = orderId;
        this.orderSequence = orderSequence;
        this.createTime = createTime;
        this.totalCount = totalCount;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderSequence='" + orderSequence + '\'' +
                ", createTime='" + createTime + '\'' +
                ", totalCount=" + totalCount +
                ", totalAmount=" + totalAmount +
                ", orderStatus=" + orderStatus +
                ", userId=" + userId +
                '}';
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderSequence() {
        return orderSequence;
    }

    public void setOrderSequence(String orderSequence) {
        this.orderSequence = orderSequence;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

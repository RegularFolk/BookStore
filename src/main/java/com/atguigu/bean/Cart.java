package com.atguigu.bean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Cart {
    private Map<Integer, CartItem> cartItemMap = new HashMap<>();

    @Override
    public String toString() {
        return "Cart{" +
                "cartItemMap=" + cartItemMap +
                '}';
    }

    public void addBookToCart(Book book) {
        //判断购物车中是否已经存在
        if (cartItemMap.containsKey(book.getBookId())) {
            itemCountIncrease(book.getBookId());
        } else {
            CartItem cartItem = new CartItem(book.getBookId(), book.getBookName(), book.getImgPath(), book.getPrice(), 1, book.getPrice());
            cartItemMap.put(cartItem.getBookId(), cartItem);
        }
    }

    /**
     * 显示购物车信息
     */
    public Map<Integer, CartItem> getCartItemMap() {
        return this.cartItemMap;
    }

    /**
     * 购物车中指定项目加一
     */
    public void itemCountIncrease(Integer bookId) {
        cartItemMap.get(bookId).countIncrease();
    }

    /**
     * 购物车中指定项目减一
     */
    public void itemCountDecrease(Integer bookId) {
        CartItem cartItem = cartItemMap.get(bookId);
        //判断数量是否为1
        if (cartItem.getCount() == 1) {
            //为零则将当前购物项从购物车中移除
            removeCartItem(bookId);
            return;
        }
        cartItem.countDecrease();
    }

    /**
     * 删除某个购物项
     */
    public void removeCartItem(Integer bookId) {
        cartItemMap.remove(bookId);
    }

    /**
     * 设置bookId的数量，输入数据的判断一般在前端完成
     */
    public void updateItemCount(Integer bookId, Integer newCount) {
        cartItemMap.get(bookId).setCount(newCount);
    }

    /**
     * 计算商品的总数
     */
    public Integer getTotalCount() {
        AtomicReference<Integer> totalCount = new AtomicReference<>(0);
        //forEach方法是异步的(多线程)，所以需要考虑线程安全的问题,也可以使用一般的方法
        cartItemMap.forEach((k, cartItem) -> {
            totalCount.updateAndGet(v -> v + cartItem.getCount());
        });
        return totalCount.get();
    }

    /**
     * 获取总金额
     * 使用BigDecimal解决显示精度问题
     */
    public Double getTotalAmount() {
        AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(new BigDecimal("0.00"));
        cartItemMap.forEach((bookId, cartItem) -> {
            totalAmount.updateAndGet(v -> v.add(BigDecimal.valueOf(cartItem.getAmount())));
        });
        return totalAmount.get().doubleValue();
    }
}

package com.lulu.demo

class ShoppingImpl : Shopping {
    override fun doShopping(money: Long): Array<Any> {
        System.out.println("逛淘宝 ,逛商场,买买买!!")
        System.out.println(String.format("花了%s块钱", money))
        return arrayOf("鞋子", "衣服", "零食")
    }
}
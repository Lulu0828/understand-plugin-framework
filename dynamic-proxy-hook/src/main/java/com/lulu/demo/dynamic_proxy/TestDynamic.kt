package com.lulu.demo.dynamic_proxy

import com.lulu.demo.Shopping
import com.lulu.demo.ShoppingImpl
import java.lang.reflect.Proxy
import java.util.*

class TestDynamic {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            var women: Shopping

            women = ShoppingImpl()
            System.out.println(Arrays.toString(women.doShopping(100)))

            women = Proxy.newProxyInstance(Shopping::class.java.classLoader, women::class.java.interfaces,
                ShoppingHandler(women)) as Shopping
            System.out.println(Arrays.toString(women.doShopping(100)))
        }
    }
}
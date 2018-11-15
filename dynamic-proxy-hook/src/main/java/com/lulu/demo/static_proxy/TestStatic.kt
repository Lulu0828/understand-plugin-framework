package com.lulu.demo.static_proxy

import com.lulu.demo.Shopping
import com.lulu.demo.ShoppingImpl
import java.util.*

class TestStatic {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            var women: Shopping

            women = ShoppingImpl()
            System.out.println(Arrays.toString(women.doShopping(100)))

            women = ProxyShopping(women)
            System.out.println(Arrays.toString(women.doShopping(100)))
        }

    }
}
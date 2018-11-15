package com.lulu.demo.dynamic_proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class ShoppingHandler(var base: Any): InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        if ("doShopping" == method?.name) {
            val money = args?.get(0) as Long
            val readCost = (money * 0.5).toLong()
            System.out.println(String.format("花了%s块钱", readCost))

            val things = method.invoke(base, readCost) as Array<Any>
            if (things.size > 1) {
                things[0] = "被掉包的东西!!"
            }

            return things
        }
        return null
    }
}
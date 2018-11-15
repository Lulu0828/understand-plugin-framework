package com.lulu.demo.static_proxy

import com.lulu.demo.Shopping

class ProxyShopping(var base: Shopping) : Shopping {

    override fun doShopping(money: Long): Array<Any> {
        // 先黑点钱(修改输入参数)
        val readCost = (money * 0.5).toLong()

        println(String.format("花了%s块钱", readCost))

        // 帮忙买东西
        val things = base.doShopping(readCost)

        // 偷梁换柱(修改返回值)
        if (things.size > 1) {
            things[0] = "被掉包的东西!!"
        }

        return things
    }
}
package com.test.util.accessibility.lib


class Start {
    companion object {
        var net: Network = Network()

        fun startAutoOperator() {
            net.connect()
        }
    }


}
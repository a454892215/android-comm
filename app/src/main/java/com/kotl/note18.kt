package com.kotl

class note18 {
}

fun main() {

    for (time in 0..3000 step 500) {
        Thread.sleep(1000)
        Log.d("======time: $time")
    }

}
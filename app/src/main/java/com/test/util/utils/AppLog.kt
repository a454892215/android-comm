package com.test.util.utils

import android.text.TextUtils
import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.Exception
import java.lang.NumberFormatException
import java.lang.StringBuilder

/**
 * Description: 日志工具类
 */
object AppLog {
    private const val TAG = "LLpp "
    private var debugEnable = true

    fun d(vararg msgList: String) {
        val msg = getStr(*msgList)
        if (debugEnable) {
            if (!TextUtils.isEmpty(msg)) {
                val length = msg.length
                val count = 1024 * 3
                val times = length / count + 1
                for (i in 0 until times) {
                    val end = ((i + 1) * count).coerceAtMost(msg.length)
                    Log.d(TAG, getTrace() + "  " + unicodetoutf8(msg.substring(i * count, end))!!)
                }
            }
        }
    }

    fun i(vararg msgList: String) {
        val msg = getStr(*msgList)
        Log.i(TAG, getTrace() + "  " + unicodetoutf8(msg)!!)
    }

    fun v(vararg msgList: String) {
        val msg = getStr(*msgList)
        Log.v(TAG, getTrace() + "  " + unicodetoutf8(msg)!!)
    }

    fun w(vararg msgList: String) {
        val msg = getStr(*msgList)
        Log.w(TAG, getTrace() + "  " + unicodetoutf8(msg)!!)
    }

    fun e(vararg msgList: String) {
        val text = getStr(*msgList)
        Log.e(TAG, getTrace() + "  " + unicodetoutf8(text)!!)
    }

     private fun getStr(vararg msgList: String):String{
         var text = ""
         for (item in msgList) {
             text += "$item "
         }
         return text
     }


    fun e(e: Throwable) {
        Log.e(TAG, getTrace() + "  " + getThrowableInfo(e))
    }

    private fun unicodetoutf8(src: String): String? {
        if (TextUtils.isEmpty(src)) {
            return null
        }
        val out = StringBuilder()
        var i = 0
        while (i < src.length) {
            val c = src[i]
            if (i + 6 < src.length && c == '\\' && src[i + 1] == 'u') {
                val hex = src.substring(i + 2, i + 6)
                try {
                    out.append(hex.toInt(16).toChar())
                } catch (nfe: NumberFormatException) {
                    nfe.fillInStackTrace()
                }
                i += 6
            } else {
                out.append(src[i])
                ++i
            }
        }
        return out.toString()
    }

    private fun getTrace(): String {
        val ste = Throwable().stackTrace[2]
        return ste.toString()
        //  Log.d("LLpp ", ste.toString())
        //  return "(" + ste.fileName.toString() + ":" + ste.lineNumber + ")"
    }

    private fun getThrowableInfo(e: Throwable): String {
        var text = ":null:"
        try {
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            e.printStackTrace(pw)
            text = sw.toString()
            sw.close()
        } catch (e1: Exception) {
            e("===============e:$e")
            e1.printStackTrace()
        }
        return text
    }
}
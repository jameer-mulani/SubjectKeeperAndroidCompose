package com.jameermulani.subjectkeepercompose

import org.junit.Test
import java.math.BigInteger

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        findFiboSeries(10)
//        swapNumbers(10, 20)
//        fibo1(2000)
//        reverseString("hello")

        checkPalindrome("cat")
        checkPalindrome("map")
        checkPalindrome("mam")
        checkPalindrome("catac")

    }

    private fun checkPalindrome(string : String){
//        val result = string == string.reversed()
//        println("$string is palindrome : $result")

        var start = 0
        var end = string.length-1
        var result = true
        while (start != end){
            if (string[start] == string[end]){
                start++
                end--
            }else{
                result = false
                break
            }
        }
        println("$string is palindrome : $result")
    }


    private fun reverseString(string : String){
        val stringBuilder = StringBuilder()
        for (i in string.length-1 downTo 0){
            stringBuilder.append(string[i])
        }
        println(stringBuilder)
    }

    private fun fibo1(limit : Int){
        val list = mutableListOf<BigInteger>()

        var a  = 0.toBigInteger()
        var b = 1.toBigInteger()

        list.apply {
            add(a)
            add(b)
        }

//        println(a)
//        println(b)
        var sum = 0.toBigInteger()
        for (i in 2..limit){
            sum = a + b
            list.add(sum)
//            println(sum)
            a = b
            b = sum
        }

        list.map {
            it.toString()
        }.forEachIndexed { index, s ->
//            if ((s.contains("999"))){
//                println("[$index] = $s")
//            }
            println("[$index] = $s")

        }


//        list.map {
//            it.toString()
//        }.filter {
//            it.startsWith("7") /*&& it.endsWith("7")*/
//        }.forEachIndexed { index, s ->
//            println("[$index] = $s")
//        }
    }

    private fun swapNumbers(a : Int , b : Int){
        println("original : ${a}, $b")
        var aa = a
        var bb = b
        var temp = aa
        aa = bb
        bb = temp
        println("after : $aa $bb")
    }

    private fun findFiboSeries(limit : Int){
        var a = 0
        var b = 1
        println(a)
        println(b)
        //0, 1, 1, 2, 3, 5, 8, 13
        var sum = 0
        for (i in 2..limit){
            sum = a + b
            println(sum)
            a = b
            b = sum
        }
    }
}
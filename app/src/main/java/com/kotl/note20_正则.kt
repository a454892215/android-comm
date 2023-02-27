package com.kotl

class note20 {

}

/**
    ^[0-9]+abc$  : 匹配数字开头，abc结尾的字符串
    *   匹配前面的子表达式零次或多次。例如，zo*能匹配“z”以及“zoo”, *等价于{0, }
    +	匹配前面的子表达式一次或多次。例如，“zo+”能匹配“zo”以及“zoo”，但不能匹配“z”。+等价于{1,}。
    ?	匹配前面的子表达式零次或一次。例如，“do(es)?”可以匹配“does”或“does”中的“do”。?等价于{0,1}。
    {n}	    n是一个非负整数。匹配确定的n次。例如，“o{2}”不能匹配“Bob”中的“o”，但是能匹配“food”中的两个o。
    {n,}    n是一个非负整数。至少匹配n次
    {n,m}	m和n均为非负整数，其中n<=m。最少匹配n次且最多匹配m次
    ?	    当该字符紧跟在任何一个其他限制符（*,+,?，{n}，{n,}，{n,m}）后面时，匹配模式是非贪婪的:例如，对于字符串“oooo”，“o+?”将匹配单个“o”，而“o+”将匹配所有“o”
    .	    匹配除“\n”之外的任何单个字符。要匹配包括“\n”在内的任何字符，请使用像“(.|\n)”的模式
    (pattern)	匹配pattern并获取这一匹配
     x|y	    匹配x或y。例如，“z|food”能匹配“z”或“food”。“(z|f)ood”则匹配“zood”或“food”
    [xyz]	字符集合。匹配所包含的任意一个字符。例如，“[abc]”可以匹配“plain”中的“a”
    [^xyz]	负值字符集合。匹配未包含的任意字符。例如，“[^abc]”可以匹配“plain”中的“p”
    [a-z]	字符范围。匹配指定范围内的任意字符。例如，“[a-z]”可以匹配“a”到“z”范围内的任意小写字母字符。
    [^a-z]	负值字符范围。匹配任何不在指定范围内的任意字符。例如，“[^a-z]”可以匹配任何不在“a”到“z”范围内的任意字符。
    \b	  匹配一个单词边界，也就是指单词和空格间的位置。例如，“er\b”可以匹配“never”中的“er”，但不能匹配“verb”中的“er”。
    \B	  匹配非单词边界。“er\B”能匹配“verb”中的“er”，但不能匹配“never”中的“er”。
    \cx	  匹配由x指明的控制字符。例如，\cM匹配一个Control-M或回车符。x的值必须为A-Z或a-z之一。否则，将c视为一个原义的“c”字符。====???
    \d	  匹配一个数字字符。等价于[0-9]。
    \D	  匹配一个非数字字符。等价于[^0-9]。
    \f	  匹配一个换页符。等价于\x0c和\cL。  ???
    \n	  匹配一个换行符。等价于\x0a和\cJ。
    \r	  匹配一个回车符。等价于\x0d和\cM。
    \s	  匹配任何空白字符，包括空格、制表符、换页符等等。等价于[ \f\n\r\t\v]
    \S	  匹配任何非空白字符。等价于[^ \f\n\r\t\v]。
    \t	  匹配一个制表符。等价于\x09和\cI。   ???
    \v	  匹配一个垂直制表符。等价于\x0b和\cK。   ???
    \w	  匹配包括下划线的任何单词字符。等价于“[A-Za-z0-9_]”
    \W	  匹配任何非单词字符。等价于“[^A-Za-z0-9_]”。
    \xn   匹配n，其中n为十六进制转义值。十六进制转义值必须为确定的两个数字长。例如，“\x41”匹配“A”。“\x041”则等价于“\x04&1”。正则表达式中可以使用ASCII编码。.
    \num  匹配num，其中num是一个正整数, 对所获取的匹配的引用。例如，“(.)\1”匹配两个连续的相同字符。 ???
    \n	  标识一个八进制转义值或一个向后引用。如果\n之前至少n个获取的子表达式，则n为向后引用。否则，如果n为八进制数字（0-7），则n为一个八进制转义值。
    \nm	  标识一个八进制转义值或一个向后引用。如果\nm之前至少有nm个获得子表达式，则nm为向后引用    ???
    \nml  如果n为八进制数字（0-3），且m和l均为八进制数字（0-7），则匹配八进制转义值nml。   ???
    \un	  匹配n，其中n是一个用四个十六进制数字表示的Unicode字符。例如，\u00A9匹配版权符号（©）。    ???
 */
fun main() {

    for (time in 0..3000 step 500) {
        Thread.sleep(1000)
        Log.d("======time: $time")
    }

}
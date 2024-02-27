#正则表达式 #互联网

> 本来是放在python学习笔记里的, 后来觉得和python关系不大所以单独拿出来了


# 正则表达式相关知识 

[正则表达式30分钟入门教程 (deerchao.cn)](https://deerchao.cn/tutorials/regex/regex.htm)

[微软的正则表达式教程](https://learn.microsoft.com/zh-cn/dotnet/standard/base-types/regular-expression-language-quick-reference?redirectedfrom=MSDN)

[一个可以解释正则表达式含义的网站](https://regex101.com/)



# 元字符汇总:

| 字符                                                         | 描述                                                                                                                                                                                                                                                     |
| ---------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| \                                                          | 将下一个字符标记为一个特殊字符、或一个原义字符、或一个 向后引用、或一个八进制转义符。例如，'n' 匹配字符 "n"。'\n' 匹配一个换行符。序列 '\\' 匹配 "\" 而 "\(" 则匹配 "("。                                                                                                                                                 |
| ^                                                          | 匹配输入字符串的开始位置。如果设置了 RegExp 对象的 Multiline 属性，^ 也匹配 '\n' 或 '\r' 之后的位置。                                                                                                                                                                                    |
| $                                                          | 匹配输入字符串的结束位置。如果设置了RegExp 对象的 Multiline 属性，$ 也匹配 '\n' 或 '\r' 之前的位置。                                                                                                                                                                                     |
| *                                                          | 匹配前面的子表达式零次或多次                                                                                                                                                                                                                                         |
| +                                                          | 匹配前面的子表达式一次或多次                                                                                                                                                                                                                                         |
| ?                                                          | 匹配前面的子表达式零次或一次                                                                                                                                                                                                                                         |
| {n}                                                        | n 为非负整数。匹配确定的 n 次                                                                                                                                                                                                                                      |
| {n,}                                                       | n 为非负整数。至少匹配n 次                                                                                                                                                                                                                                        |
| {n,m}                                                      | m 和 n 均为非负整数，其中n <= m。最少匹配 n 次且最多匹配 m 次                                                                                                                                                                                                                |
| ?                                                          | 当该字符紧跟在任何一个其他限制符 (*, +, ?, {n}, {n,}, {n,m}) 后面时，匹配模式是非贪婪的。非贪婪模式尽可能少的匹配所搜索的字符串，而默认的贪婪模式则尽可能多的匹配所搜索的字符串。例如，对于字符串 "oooo"，'o+?' 将匹配单个 "o"，而 'o+' 将匹配所有 'o'。                                                                                               |
| .                                                          | 匹配除换行符（\n、\r）之外的任何单个字符。要匹配包括 '\n' 在内的任何字符，请使用像"**(.\|\n)**"的模式。                                                                                                                                                                                        |
| (pattern)                                                  | 匹配 pattern 并获取这一匹配。所获取的匹配可以从产生的 Matches 集合得到，在VBScript 中使用 SubMatches 集合，在JScript 中则使用 $0…$9 属性。要匹配圆括号字符，请使用 '\(' 或 '\)'。                                                                                                                              |
| (?:pattern)                                                | 匹配 pattern 但不获取匹配结果，也就是说这是一个非获取匹配，不进行存储供以后使用。这在使用 "或" 字符 (\|) 来组合一个模式的各个部分是很有用。例如， 'industr(?:y\|ies) 就是一个比 'industry\|industries' 更简略的表达式。                                                                                                            |
| (?=pattern)                                                | 正向肯定预查(零宽断言)（look ahead positive assert），在任何匹配pattern的字符串开始处匹配查找字符串。这是一个非获取匹配，也就是说，该匹配不需要获取供以后使用。例如，"Windows(?=95\|98\|NT\|2000)"能匹配"Windows2000"中的"Windows"，但不能匹配"Windows3.1"中的"Windows"。预查不消耗字符，也就是说，在一个匹配发生后，在最后一次匹配之后立即开始下一次匹配的搜索，而不是从包含预查的字符之后开始。 |
| (?!pattern)                                                | 正向否定预查(负向零宽断言)((negative assert)，在任何不匹配pattern的字符串开始处匹配查找字符串。这是一个非获取匹配，也就是说，该匹配不需要获取供以后使用。例如"Windows(?!95\|98\|NT\|2000)"能匹配"Windows3.1"中的"Windows"，但不能匹配"Windows2000"中的"Windows"。预查不消耗字符，也就是说，在一个匹配发生后，在最后一次匹配之后立即开始下一次匹配的搜索，而不是从包含预查的字符之后开始。         |
| (?<=pattern)                                               | 反向(look behind)肯定预查，与正向肯定预查类似，只是方向相反。例如，"`(?<=95\|98\|NT\|2000)Windows`"能匹配"`2000Windows`"中的"`Windows`"，但不能匹配"`3.1Windows`"中的"`Windows`"。                                                                                                              |
| (?<!pattern)                                               | 反向否定预查，与正向否定预查类似，只是方向相反。例如"`(?<!95\|98\|NT\|2000)Windows`"能匹配"`3.1Windows`"中的"`Windows`"，但不能匹配"`2000Windows`"中的"`Windows`"。                                                                                                                            |
| x\|y                                                       | 匹配 x 或 y。例如，'z\|food' 能匹配 "z" 或 "food"。'(z\|f)ood' 则匹配 "zood" 或 "food"。                                                                                                                                                                                |
| [xyz]                                                      | 字符集合。匹配所包含的任意一个字符。例如， '[abc]' 可以匹配 "plain" 中的 'a'。                                                                                                                                                                                                     |
| [^xyz]                                                     | 负值字符集合。匹配未包含的任意字符。例如， '[^abc]' 可以匹配 "plain" 中的'p'、'l'、'i'、'n'。                                                                                                                                                                                         |
| [a-z]                                                      | 字符范围。匹配指定范围内的任意字符                                                                                                                                                                                                                                      |
| [^a-z]                                                     | 负值字符范围。匹配任何不在指定范围内的任意字符。例如，'[^a-z]' 可以匹配任何不在 'a' 到 'z' 范围内的任意字符。                                                                                                                                                                                       |
| [\b](https://www.runoob.com/regexp/regexp-metachar-b.html) | 匹配一个单词边界，也就是指单词和空格间的位置。例如， 'er\b' 可以匹配"never" 中的 'er'，但不能匹配 "verb" 中的 'er'。                                                                                                                                                                            |
| [\B](https://www.runoob.com/regexp/regexp-metachar-b.html) | 匹配非单词边界。'er\B' 能匹配 "verb" 中的 'er'，但不能匹配 "never" 中的 'er'。                                                                                                                                                                                               |
| \cx                                                        | 匹配由 x 指明的控制字符。例如， \cM 匹配一个 Control-M 或回车符。x 的值必须为 A-Z 或 a-z 之一。否则，将 c 视为一个原义的 'c' 字符。                                                                                                                                                                  |
| \d                                                         | 匹配一个数字字符。等价于 [0-9]。                                                                                                                                                                                                                                    |
| \D                                                         | 匹配一个非数字字符。等价于 [^0-9]。                                                                                                                                                                                                                                  |
| \f                                                         | 匹配一个换页符。等价于 \x0c 和 \cL。                                                                                                                                                                                                                                |
| \n                                                         | 匹配一个换行符。等价于 \x0a 和 \cJ。                                                                                                                                                                                                                                |
| \r                                                         | 匹配一个回车符。等价于 \x0d 和 \cM。                                                                                                                                                                                                                                |
| \s                                                         | 匹配任何空白字符，包括空格、制表符、换页符等等。等价于 [ \f\n\r\t\v]。                                                                                                                                                                                                             |
| \S                                                         | 匹配任何非空白字符。等价于 [^ \f\n\r\t\v]。                                                                                                                                                                                                                          |
| \t                                                         | 匹配一个制表符。等价于 \x09 和 \cI。                                                                                                                                                                                                                                |
| \v                                                         | 匹配一个垂直制表符。等价于 \x0b 和 \cK。                                                                                                                                                                                                                              |
| \w                                                         | 匹配字母、数字、下划线。等价于'[A-Za-z0-9_]'。                                                                                                                                                                                                                         |
| \W                                                         | 匹配非字母、数字、下划线。等价于 '[^A-Za-z0-9_]'。                                                                                                                                                                                                                      |
| \xn                                                        | 匹配 n，其中 n 为十六进制转义值。十六进制转义值必须为确定的两个数字长。例如，'\x41' 匹配 "A"。'\x041' 则等价于 '\x04' & "1"。正则表达式中可以使用 ASCII 编码。                                                                                                                                                  |
| \num                                                       | 匹配 num，其中 num 是一个正整数。对所获取的匹配的引用。例如，'(.)\1' 匹配两个连续的相同字符。                                                                                                                                                                                                |
| \n                                                         | 标识一个八进制转义值或一个向后引用。如果 \n 之前至少 n 个获取的子表达式，则 n 为向后引用。否则，如果 n 为八进制数字 (0-7)，则 n 为一个八进制转义值。                                                                                                                                                                  |
| \nm                                                        | 标识一个八进制转义值或一个向后引用。如果 \nm 之前至少有 nm 个获得子表达式，则 nm 为向后引用。如果 \nm 之前至少有 n 个获取，则 n 为一个后跟文字 m 的向后引用。如果前面的条件都不满足，若 n 和 m 均为八进制数字 (0-7)，则 \nm 将匹配八进制转义值 nm。                                                                                                      |
| \nml                                                       | 如果 n 为八进制数字 (0-3)，且 m 和 l 均为八进制数字 (0-7)，则匹配八进制转义值 nml。                                                                                                                                                                                                 |
| \un                                                        | 匹配 n，其中 n 是一个用四个十六进制数字表示的 Unicode 字符。例如， \u00A9 匹配版权符号 (?)。                                                                                                                                                                                            |

# 正则表达式语法规则

## 1. 分支条件:

用 `|` 将两边分开, 表示 "或"

example: `0\d{2}-\d{8}|0\d{3}-\d{7}` 


## 2. 分组

用 `()` 指定子表达式(分组)

example: `(\d{1,3}\.){3}\d{1,3}`

每个组都有组号, 因此可以用组号来进行后向引用

语法: `\1` (匹配组号为1的组)

- 分组0对应整个表达式
- 匹配时先给未命名组分配名称, 再给命名组分配名称, 因此命名组组号均大于非命名组组号

指定组名: `(?<Word>\w+)` or `(?'Word'\w+)`

此时反向引用: `\k<Word>`


## 3. 注释

- W1: `(?#comment)` 
- W2: 启用 "忽略空白", 此时从 `#` 开始到这行结尾都将被忽略


## 4. 贪婪匹配与懒惰匹配

**贪婪匹配:** 在variable字符数时, 通常匹配尽可能多的字符

**懒惰匹配:** 匹配尽可能少的字符. 通过在符号后加 `?` 来启用
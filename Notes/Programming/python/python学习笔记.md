#程序设计 #python #基础教程 
# Python 学习笔记

> Created by lxx11451 on 2024/1/18
>
> python基础学习

GPT :你是一名python程序设计教师，假设我，一名python初学者，是你的学生，现我将向你询问一系列问题，请为我细致，详细的解答, 必要时附上具体实例进行讲解

## 目录

[TOC]



##  准备入门



### 1. Python之诗

> The Zen of Python, by Tim Peters
>
> Beautiful is better than ugly.
> Explicit is better than implicit.
> Simple is better than complex.
> Complex is better than complicated.
> Flat is better than nested.
> Sparse is better than dense.
> Readability counts.
> Special cases aren't special enough to break the rules.
> Although practicality beats purity.
> Errors should never pass silently.
> Unless explicitly silenced.
> In the face of ambiguity, refuse the temptation to guess.
> There should be one-- and preferably only one --obvious way to do it.
> Although that way may not be obvious at first unless you're Dutch.
> Now is better than never.
> Although never is often better than *right* now.
> If the implementation is hard to explain, it's a bad idea.
> If the implementation is easy to explain, it may be a good idea.
> Namespaces are one honking great idea -- let's do more of those!



## 一. 变量和元素



### 1. 变量

#### 变量类型

python自动给变量赋予类型

- 整型：Python中可以处理任意大小的整数（只有int一种类型），而且支持二进制（如`0b100`，换算成十进制是4）、八进制（如`0o100`，换算成十进制是64）、十进制（`100`）和十六进制（`0x100`，换算成十进制是256）的表示法。
- 浮点型：浮点数除了数学写法（如`123.456`）之外还支持科学计数法（如`1.23456e2`）。
- 字符串型：单引号或双引号括起来，比如`'hello'`和`"hello"`,字符串还有原始字符串表示法、字节字符串表示法、Unicode字符串表示法，而且可以书写成多行的形式（用三个单引号或三个双引号开头，三个单引号或三个双引号结尾）。
- 布尔型：布尔值只有`True`、`False`两种值，要么是`True`，要么是`False`，在Python中，可以直接用`True`、`False`表示布尔值（请注意大小写），也可以通过布尔运算计算出来（例如`3 < 5`会产生布尔值`True`，而`2 == 1`会产生布尔值`False`）。
- 复数型：形如`3+5j`，跟数学上的复数表示一样，唯一不同的是虚部的`i`换成了`j`。



#### 变量命名

和C要求一样



#### 类型转换

- 可以使用Python中内置的函数对变量类型进行转换。
- `int()`：将一个数值或字符串转换成整数，可以指定进制。
- `float()`：将一个字符串转换成浮点数。
- `str()`：将指定的对象转换成字符串形式，可以指定编码。
- `chr()`：将整数转换成该编码对应的字符串（一个字符）。
- `ord()`：将字符串（一个字符）转换成对应的编码（整数）。



example :

```python
a = int(input('a = ')) 
# 此处input("str") 返回值类型为str, 需用类型转换函数做类型转换
b = int(input('b = '))
print('%d + %d = %d' % (a, b, a + b))
print('%d - %d = %d' % (a, b, a - b))
print('%d * %d = %d' % (a, b, a * b))
print('%d / %d = %f' % (a, b, a / b))
print('%d // %d = %d' % (a, b, a // b))
print('%d %% %d = %d' % (a, b, a % b))
print('%d ** %d = %d' % (a, b, a ** b))
```

或者可以这么写:

```py
a = int(input("a = "))
b = int(input("b = "))

print(a,"+",b,"=",a + b)
```



#### 运算符

优先级从高到低

| 运算符                                                       | 描述                           |
| ------------------------------------------------------------ | ------------------------------ |
| `[]` `[:]`                                                   | 下标，切片                     |
| `**`                                                         | 指数                           |
| `~` `+` `-`                                                  | 按位取反, 正负号               |
| `*` `/` `%` `//`                                             | 乘，除，模，整除               |
| `+` `-`                                                      | 加，减                         |
| `>>` `<<`                                                    | 右移，左移                     |
| `&`                                                          | 按位与                         |
| `^` `\|`                                                     | 按位异或，按位或               |
| `<=` `<` `>` `>=`                                            | 小于等于，小于，大于，大于等于 |
| `==` `!=`                                                    | 等于，不等于                   |
| `is`  `is not`                                               | 身份运算符                     |
| `in` `not in`                                                | 成员运算符                     |
| `not` `or` `and`                                             | 逻辑运算符                     |
| `=` `+=` `-=` `*=` `/=` `%=` `//=` `**=` `&=` `|=` `^=` `>>=` `<<=` | （复合）赋值运算符             |



## 二. if

- 分支嵌套
- elif 

```python
score = float(input('请输入成绩: '))
if score >= 90:
    grade = 'A'
elif score >= 80:
    grade = 'B'
elif score >= 70:
    grade = 'C'
elif score >= 60:
    grade = 'D'
else:
    grade = 'E'
print('对应的等级是:', grade)
```



## 三. for and while



### 1. for in 循环

知道循环次数时用 (和C差不多)

```py
sum = 0

for x in range(101):
	sum += x

print(sum)
```

- `range(101)`：可以用来产生0到100范围的整数，需要注意的是取不到101。
- `range(1, 101)`：可以用来产生1到100范围的整数，相当于前面是闭区间后面是开区间。
- `range(1, 101, 2)`：可以用来产生1到100的奇数，其中2是步长，即每次数值递增的值。
- `range(100, 0, -2)`：可以用来产生100到1的偶数，其中-2是步长，即每次数字递减的值。

### 2. while 循环

- 经典的猜数游戏

```python
#猜数字

import random

number = random.randint(1, 100)

time = 0
while True:
    time += 1
    guess = int(input("Guess! "))
    if guess == number:
        print("Right!")
        break
    elif guess < number:
        print("Too low")
    elif guess > number:
        print("Too high")
print("You guessed the number in %d tries"%(time))

if time > 6:
    print("You know, you are as stupid as me.")
```

- 检验素数

```python
# 检验素数

import math

x = int(input("Enter a number: "))
isprime = True
if x % 2 == 0:
    isprime = False
else:
    for i in range(3, int(math.sqrt(x))+1, 2):
        if x % i == 0:
            isprime = False
            break
if isprime == True and x != 1:
    print(x, "is a prime number")
else:
    print(x, "is not a prime number")
```

- 寻找水仙花数: 

```python
# 寻找水仙花数

for i in range(100, 1000):
    low = i % 10
    mid = i // 10 % 10
    high = i // 100

    if low ** 3 + mid ** 3 + high ** 3 == i:
        print(i)
```

- 整数反转

```python
# 整数反转

num = int(input('Enter a number: '))

reserved = 0
while num != 0:
    reserved *= 10
    reserved += num % 10
    num = num // 10
print(reserved)
```



## 四. 函数



### 1. 格式

形式 : `def function_name(var):`

```python
# 计算C(n,m)

# 求阶乘
def fractional(num):
    result = 1
    for i in range(1, num + 1):
        result *= i
    return result

m = int(input("M = "))
n = int(input("N = "))

print("C(m ,n) =", fractional(m) // fractional(n) // fractional(m - n))
```



- 在Python中，函数的参数可以有默认值，也支持使用可变参数

```python
#若不输入参数, 则参数为默认值; 若输入, 则参数为输入值
import random

def roll_dice(n = 1):
    ret = 0
    for _ in range(n):
        ret += random.randint(1, 6)
    return ret


print(roll_dice(), roll_dice(2), roll_dice(3))
```



- 可变数量参数: 

```python
def add(*nums):
    ret = 0
    for num in nums:
        ret += num
    return ret

print(add(0, 1, 2, 3, 4))
```

> for i in ... :对所有在...范围内的i

- 从文件中引入函数

```python
from file_1 import function
from file_2 import function
#or
import function as file_1
```

- 不同文件中函数可以重名
- 调用不同文件中的同名函数:

```python
#OK
import module1 as m1
import module2 as m2

m1.foo()
m2.foo()

#只会执行后import的
from file_1 import function
from file_2 import function

function()
```



- 如果我们导入的模块除了定义函数之外还有可以执行代码，那么Python解释器在导入这个模块时就会执行这些代码，事实上我们可能并不希望如此，因此如果我们在模块中编写了执行代码，最好是将这些执行代码放入如下所示的条件中，这样的话除非直接运行该模块，if条件下的这些代码是不会执行的，因为只有直接执行的模块的名字才是"__main__"。

```python
def foo():
    pass


def bar():
    pass


# __name__是Python中一个隐含的变量它代表了模块的名字
# 只有被Python解释器直接执行的模块的名字才是__main__
if __name__ == '__main__':
    print('call foo()')
    foo()
    print('call bar()')
    bar()
```

```python
import module3

# 导入module3时 不会执行模块中if条件成立时的代码 因为模块的名字是module3而不是__main__
```



- 函数定义可以互相嵌套

```python
def genshin():
    def launch():  #作用域: genshin()函数内部
        print("Genshin")
    launch()
    return "Launch!"


print(genshin())
"""
Output :
Genshin
Launch!
"""
```





练习:

```python
#判断是否为回文数

def is_circle(a):
    original = a
    opposite = 0
    while a > 0: 
        opposite = opposite * 10 + a % 10
        a = a // 10
    return opposite == original
```

```python
#判断是否为素数

def is_prime(x):
    if x % 2 == 0 or x == 1:
        return False
    else:
        for i in range(3, int(math.sqrt(x))+1, 2):
            if x % i == 0:
                return False
    return True
```



### 2. 变量作用域

- 代码主体中: 全局变量
- 函数: 局部变量
- 局部变量名称可以和全局相同
- 作用域: 所在的范围内



- 通过在函数中声明 `global` 改变全局变量:

```python
def foo():
    global a
    a = a ** a


a = 2
foo()
print("a =", a)
# 输出: a = 4
```

- 通过 `nonlocal` 关键字来改变嵌套作用域中变量值

```python
def outer_function():
    x = 10
    
    def inner_function():
        nonlocal x
        x = 20
        print("Inner function: x =", x)
    
    inner_function()
    print("Outer function: x =", x)

outer_function()
"""""
Output:
Inner function: x = 20
Outer function: x = 20
"""""
```



- 和C语言一样, 应尽量减少对全局变量的使用
- example(较为标准的格式) :

```python
def main():
    #Todo :add code here
    pass

if __name__ == '__main__':
    main()
```



## 五. 字符串



- 以三个双引号或单引号开头的字符串可以折行
- 在`\`后面还可以跟一个八进制或者十六进制数来表示字符
- 在`\`后面跟Unicode字符编码来表示字符
- 如果不希望字符串中的`\`表示转义，可以在字符串的最前面加上`r`

- 奇怪的用法:

```python
str = "abcdef123456"

str[2:5] # = "cde"
str[2:]  # = "cdef123456"
str[2:5:2] # = "ce"
str[-1:-4] # = "654"
str[::-1] # = "654321fedcba"

# 第二个数不算
```

- 一些函数:

```python
str1 = 'hello, world!'
# 通过内置函数len计算字符串的长度
print(len(str1)) # 13
# 获得字符串首字母大写的拷贝
print(str1.capitalize()) # Hello, world!
# 获得字符串每个单词首字母大写的拷贝
print(str1.title()) # Hello, World!
# 获得字符串变大写后的拷贝
print(str1.upper()) # HELLO, WORLD!
# 从字符串中查找子串所在位置
print(str1.find('or')) # 8
print(str1.find('shit')) # -1
# 与find类似但找不到子串时会引发异常
# print(str1.index('or'))
# print(str1.index('shit'))
# 检查字符串是否以指定的字符串开头
print(str1.startswith('He')) # False
print(str1.startswith('hel')) # True
# 检查字符串是否以指定的字符串结尾
print(str1.endswith('!')) # True
# 将字符串以指定的宽度居中并在两侧填充指定的字符
print(str1.center(50, '*'))
# 将字符串以指定的宽度靠右放置左侧填充指定的字符
print(str1.rjust(50, ' '))
str2 = 'abc123456'
# 检查字符串是否由数字构成
print(str2.isdigit())  # False
# 检查字符串是否以字母构成
print(str2.isalpha())  # False
# 检查字符串是否以数字和字母构成
print(str2.isalnum())  # True
str3 = '  jackfrued@126.com '
print(str3)
# 获得字符串修剪左右两侧空格之后的拷贝
print(str3.strip())
```



- print字符串:

```python
# W1:
a, b = 5, 10
print('{0} * {1} = {2}'.format(a, b, a * b))

# W2:
a, b = 5, 10
print(f'{a} * {b} = {a * b}')
```



## 六. 常用数据结构

python中定义的数据结构包括列表、元组、集合和字典

### 1. 列表

- 定义:

```python
list_1 = [1, 3, 5, 7, 9]
print(list_1)

# Output: [1, 3, 5, 7, 9]
```

- 操作:

```python
# 乘号表示列表元素的重复
list2 = ['hello'] * 3
print(list2) # ['hello', 'hello', 'hello']
# 计算列表长度(元素个数)
print(len(list1)) # 5
# 下标(索引)运算
print(list1[0]) # 1
print(list1[4]) # 100
# print(list1[5])  # IndexError: list index out of range
print(list1[-1]) # 100
print(list1[-3]) # 5
list1[2] = 300
print(list1) # [1, 3, 300, 7, 100]
# 通过循环用下标遍历列表元素
for index in range(len(list1)):
    print(list1[index])
# 通过for循环遍历列表元素
for elem in list1:
    print(elem)
# 通过enumerate函数处理列表之后再遍历可以同时获得元素索引和值
for index, elem in enumerate(list1):
    print(index, elem)
```

- 添加 && 移除元素

```python
list1 = [1, 3, 5, 7, 100]
# 添加元素
list1.append(200)
list1.insert(1, 400)
# 合并两个列表
# list1.extend([1000, 2000])
list1 += [1000, 2000]
print(list1) # [1, 400, 3, 5, 7, 100, 200, 1000, 2000]
print(len(list1)) # 9
# 先通过成员运算判断元素是否在列表中，如果存在就删除该元素
if 3 in list1:
	list1.remove(3)
if 1234 in list1:
    list1.remove(1234)
print(list1) # [1, 400, 5, 7, 100, 200, 1000, 2000]
# 从指定的位置删除元素
list1.pop(0)
list1.pop(len(list1) - 1)
print(list1) # [400, 5, 7, 100, 200, 1000]
# 清空列表元素
list1.clear()
print(list1) # []
```

- 也可以切片

- 排序 :

```python
list1 = ['orange', 'apple', 'zoo', 'internationalization', 'blueberry']
list2 = sorted(list1)
# sorted函数返回列表排序后的拷贝不会修改传入的列表
# 函数的设计就应该像sorted函数一样尽可能不产生副作用
list3 = sorted(list1, reverse=True)
# 通过key关键字参数指定根据字符串长度进行排序而不是默认的字母表顺序
list4 = sorted(list1, key=len)
print(list1)
print(list2)
print(list3)
print(list4)
# 给列表对象发出排序消息直接在列表对象上进行排序
list1.sort(reverse=True)
print(list1)
```



#### 生成式和生成器

生成器方法创建list :

```python
import sys

f = [x for x in range(1, 10)]
print(f)
f = [x + y for x in 'ABCDE' for y in '1234567']
print(f)
# 用列表的生成表达式语法创建列表容器
# 用这种语法创建列表之后元素已经准备就绪所以需要耗费较多的内存空间
f = [x ** 2 for x in range(1, 1000)]
print(sys.getsizeof(f))  # 查看对象占用内存的字节数, 返回值单位为byte
print(f)
# 请注意下面的代码创建的不是一个列表而是一个生成器对象
# 通过生成器可以获取到数据但它不占用额外的空间存储数据
# 每次需要数据的时候就通过内部的运算得到数据(需要花费额外的时间)
f = (x ** 2 for x in range(1, 1000))
print(sys.getsizeof(f))  # 相比生成式生成器不占用存储数据的空间
print(f)
for val in f:
    print(val)
```

- How to 定义生成器 : `yield`

- example :

```python
def fib(n):
    a, b = 0, 1
    for _ in range(n):
        a, b = b, a + b
        yield a  # return改成yield


def main():
    for val in fib(20):
        print(val)


if __name__ == '__main__':
    main()
```



### 2.元组

容器数据类型

**对象不可修改, 只能重新赋值**

- 所占空间小, 且便于维护, 创建速度快

- 定义 && 使用

```python
# 定义元组
t = ('骆昊', 38, True, '四川成都')
print(t)
# 获取元组中的元素
print(t[0])
print(t[3])
# 遍历元组中的值
for member in t:
    print(member)
# 重新给元组赋值
# t[0] = '王大锤'  # TypeError
# 变量t重新引用了新的元组原来的元组将被垃圾回收
t = ('王大锤', 20, True, '云南昆明')
print(t)
# 将元组转换成列表
person = list(t)
print(person)
# 列表是可以修改它的元素的
person[0] = '李小龙'
person[1] = 25
print(person)
# 将列表转换成元组
fruits_list = ['apple', 'banana', 'orange']
fruits_tuple = tuple(fruits_list)
print(fruits_tuple)
```



### 3. 集合

- 和数学上的集合是一致的，不允许有重复元素，而且可以进行交集、并集、差集等运算。

- 会自动合并相同元素

example :

```python
# 创建集合的字面量语法
set1 = {1, 2, 3, 3, 3, 2}
print(set1)
print('Length =', len(set1))
# 创建集合的构造器语法(面向对象部分会进行详细讲解)
set2 = set(range(1, 10))
set3 = set((1, 2, 3, 3, 2, 1))
print(set2, set3)
# 创建集合的推导式语法(推导式也可以用于推导集合)
set4 = {num for num in range(1, 100) if num % 3 == 0 or num % 5 == 0}
print(set4)
```

- 集合是无序的

example :

```python
import math

def is_prime(num):
    for x in range(1, num + 1):
        isprime = True
        if (x % 2 == 0 and x != 2) or x == 1:
            isprime = False
        else:
            for i in range(3, int(math.sqrt(x))+1, 2):
                if x % i == 0:
                    isprime = False
                    break
        if isprime == True:
            yield x


set_prime = {num for num in is_prime(1000)}

print(set_prime)
#输出无序
```

- 添加 / 删除元素

```python
set1.add(4)
set1.add(5)
set2.update([11, 12])
set2.discard(5)
if 4 in set2:
    set2.remove(4)
print(set1, set2)
print(set3.pop())
print(set3)
```

- ∩ ∪ -  对称差 包含 被包含 运算

```python
# 集合的交集、并集、差集、对称差运算
print(set1 & set2)
# print(set1.intersection(set2))
print(set1 | set2)
# print(set1.union(set2))
print(set1 - set2)
# print(set1.difference(set2))
print(set1 ^ set2)   # ∪ - ∩
# print(set1.symmetric_difference(set2))
# 判断子集和超集
print(set2 <= set1)
# print(set2.issubset(set1))
print(set3 <= set1)
# print(set3.issubset(set1))
print(set1 >= set2)
# print(set1.issuperset(set2))
print(set1 >= set3)
# print(set1.issuperset(set3))
```



### 4. 字典

- 每个元素都是由一个键和一个值组成的“键值对”，键和值通过冒号分开。

```python
# 创建字典的字面量语法
scores = {'骆昊': 95, '白元芳': 78, '狄仁杰': 82}
print(scores)
# 创建字典的构造器语法
items1 = dict(one=1, two=2, three=3, four=4)
# 通过zip函数将两个序列压成字典
items2 = dict(zip(['a', 'b', 'c'], '123'))
# 创建字典的推导式语法
items3 = {num: num ** 2 for num in range(1, 10)}
print("items1 =", items1)
print("items2 =", items2)
print("items3 =", items3)
# 通过键可以获取字典中对应的值
print(scores['骆昊'])
print(scores['狄仁杰'])
# 对字典中所有键值对进行遍历
for key in scores:
    print(f'{key}: {scores[key]}')
# 更新字典中的元素
scores['白元芳'] = 65
scores['诸葛王朗'] = 71
scores.update(冷面=67, 方启鹤=85)
print(scores)
if '武则天' in scores:
    print(scores['武则天'])
print(scores.get('武则天'))
# get方法也是通过键获取对应的值但是可以设置默认值
print(scores.get('武则天', 60))
# 删除字典中的元素
# 从后往前
print(scores.popitem())
print(scores.popitem())
print(scores.pop('骆昊', 100))
# 清空字典
scores.clear()
print(scores)
```

- Output :

```python
{'骆昊': 95, '白元芳': 78, '狄仁杰': 82}
items1 = {'one': 1, 'two': 2, 'three': 3, 'four': 4}
items2 = {'a': '1', 'b': '2', 'c': '3'}
items3 = {1: 1, 2: 4, 3: 9, 4: 16, 5: 25, 6: 36, 7: 49, 8: 64, 9: 81}
95
82
骆昊: 95
白元芳: 78
狄仁杰: 82
{'骆昊': 95, '白元芳': 65, '狄仁杰': 82, '诸葛王朗': 71, '冷面': 67, '方启鹤': 85}
None
60
('方启鹤', 85)
('冷面', 67)
95
{'白元芳': 65, '狄仁杰': 82, '诸葛王朗': 71}
{}
```



### 练习 :

#### 1. 生成验证码

```python
import random

def get_code(length = 6):
    targets = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM"
    ret = ''

    for _ in range(length):
        ret += random.choice(targets)

    return ret

print(get_code())
```

#### 2. 获得文件后缀名

```python
def get_suffix(filename, has_dot = False):
    pos = filename.rfind('.')
    if pos == -1:
        return ''
    index = pos if has_dot else pos + 1
    return filename[index:]

def main():
    filename = str(input('Enter a filename: '))
    suffix = get_suffix(filename)
    print(suffix)


if __name__ == '__main__':
    main()
```

#### 3. 获得列表中最大的两个数

```python
def max_two_numbers(num_list):
    copy = list(num_list)
    max_num = max(num_list)
    copy.remove(max_num)
    return max_num, max(copy)
```

#### 4. TODO





## 七. 面向对象编程

 三大支柱: 封装、继承和多态

### 1. 类和对象

简单的说，类是对象的蓝图和模板，而对象是类的实例。这个解释虽然有点像用概念在解释概念，但是从这句话我们至少可以看出，类是抽象的概念，而对象是具体的东西。在面向对象编程的世界中，一切皆为对象，对象都有属性和行为，每个对象都是独一无二的，而且对象一定属于某个类（型）。当我们把一大堆拥有共同特征的对象的静态特征（属性）和动态特征（行为）都抽取出来后，就可以定义出一个叫做“类”的东西。

~~(如说)~~

- 某种程度上是一个结构体, 内含相关参数及函数指针(C语言式理解) (widget)
- 封装的方法

#### 创建类

```python
class Student(object):

    def __init__(self, name, age):
        self.name = name
        self.age = age

    def study(self, course_name):
        print(self.name, "is studying", course_name)

    def play_video_games(self):
        if self.age >= 18:
            print(self.name, "is playing Genshin Impact")
        else:
            print(self.name, "can play Genshin Impact only on Friday")


def main():
    stu = Student("蔡徐坤", 114)
    stu.study("原神科技与工程")
    stu.play_video_games()

if __name__ == '__main__':
    main()
```

#### 访问权限

- private : 给函数名字前加 __
- example :

```python
class Test:

    def __init__(self, foo):
        self.__foo = foo

    def __bar(self):
        print(self.__foo)
        print('__bar')


def main():
    test = Test('hello')
    # AttributeError: 'Test' object has no attribute '__bar'
    test.__bar()
    # AttributeError: 'Test' object has no attribute '__foo'
    print(test.__foo)


if __name__ == "__main__":
    main()
```

- 并没有严格保证, 仍可以通过某些手段访问 :

```python
def main():
    test = Test('hello')
    test._Test__bar()
    print(test._Test__foo)


if __name__ == "__main__":
    main()
```

- 在实际开发中不建议将属性设置为私有的，因为这会导致子类无法访问。
- 命名惯例: 属性名以单下划线开头来表示属性是受保护的
- 本类之外的代码在访问这样的属性时应该要保持慎重
- 这种做法并不是语法上的规则，单下划线开头的属性和方法外界仍然是可以访问的，所以更多的时候它是一种暗示或隐喻



### 2. 访问权限和动态

#### Property和Setter装饰器

- @property : 访问属性
- @setter : 修改属性

```python
class Person(object):

    def __init__(self, name, age):
        self._name = name
        self._age = age

    # 访问器 - getter方法
    @property
    def name(self):
        return self._name

    # 访问器 - getter方法
    @property
    def age(self):
        return self._age

    # 修改器 - setter方法
    @age.setter
    def age(self, age):
        self._age = age

    def play(self):
        if self._age <= 16:
            print('%s正在玩飞行棋.' % self._name)
        else:
            print('%s正在玩斗地主.' % self._name)


def main():
    person = Person('王大锤', 12)
    person.play()
    person.age = 22
    person.play()
    # person.name = '白元芳'  # AttributeError: can't set attribute


if __name__ == '__main__':
    main()
```

#### slots

限定对象只能绑定相关属性 :

```python
class Person(object):

    # 限定Person对象只能绑定_name, _age和_gender属性
    __slots__ = ('_name', '_age', '_gender')

    def __init__(self, name, age):
        self._name = name
        self._age = age

    @property
    def name(self):
        return self._name

    @property
    def age(self):
        return self._age

    @age.setter
    def age(self, age):
        self._age = age

    def play(self):
        if self._age <= 16:
            print('%s正在玩飞行棋.' % self._name)
        else:
            print('%s正在玩斗地主.' % self._name)


def main():
    person = Person('王大锤', 22)
    person.play()
    person._gender = '男'
    # AttributeError: 'Person' object has no attribute '_is_gay'
    # person._is_gay = True
```

#### 静态方法和类方法

- 静态方法(@staticmethod) : 通过给类传入参数来调用, 不属于对象

example :

```python
from math import sqrt


class Triangle(object):

    def __init__(self, a, b, c):
        self._a = a
        self._b = b
        self._c = c

    @staticmethod
    def is_valid(a, b, c):
        return a + b > c and b + c > a and a + c > b

    def perimeter(self):
        return self._a + self._b + self._c

    def area(self):
        half = self.perimeter() / 2
        return sqrt(half * (half - self._a) *
                    (half - self._b) * (half - self._c))


def main():
    a, b, c = 3, 4, 5
    # 静态方法和类方法都是通过给类发消息来调用的
    if Triangle.is_valid(a, b, c):
        t = Triangle(a, b, c)
        print(t.perimeter())
        # 也可以通过给类发消息来调用对象方法但是要传入接收消息的对象作为参数
        # print(Triangle.perimeter(t))
        print(t.area())
        # print(Triangle.area(t))
    else:
        print('无法构成三角形.')


if __name__ == '__main__':
    main()
```

- 属于类 : Triangle.function
- 属于对象 : t.function

- 类方法(@classmethod) : 第一个参数约定为 cls , 可用于创建对象

```python
from time import time, localtime, sleep


class Clock(object):
    """数字时钟"""

    def __init__(self, hour=0, minute=0, second=0):
        self._hour = hour
        self._minute = minute
        self._second = second

    @classmethod
    def now(cls):
        ctime = localtime(time())
        return cls(ctime.tm_hour, ctime.tm_min, ctime.tm_sec)

    def run(self):
        """走字"""
        self._second += 1
        if self._second == 60:
            self._second = 0
            self._minute += 1
            if self._minute == 60:
                self._minute = 0
                self._hour += 1
                if self._hour == 24:
                    self._hour = 0

    def show(self):
        """显示时间"""
        return '%02d:%02d:%02d' % \
               (self._hour, self._minute, self._second)


def main():
    # 通过类方法创建对象并获取系统时间
    clock = Clock.now()
    while True:
        print(clock.show())
        sleep(1)
        clock.run()


if __name__ == '__main__':
    main()
```

#### 类之间的关系

is-a、has-a和use-a

- is-a关系也叫继承或泛化，比如学生和人的关系、手机和电子产品的关系都属于继承关系。

- has-a关系通常称之为关联，比如部门和员工的关系，汽车和引擎的关系都属于关联关系；关联关系如果是整体和部分的关联，那么我们称之为聚合关系；如果整体进一步负责了部分的生命周期（整体和部分是不可分割的，同时同在也同时消亡），那么这种就是最强的关联关系，我们称之为合成关系。
- use-a关系通常称之为依赖，比如司机有一个驾驶的行为（方法），其中（的参数）使用到了汽车，那么司机和汽车的关系就是依赖关系。

#### 继承和多态

**继承 :** 让一个类从另一个类那里将属性和方法直接继承下来，从而减少重复代码的编写

- 提供继承信息的 : 父类
- 得到的 : 子类
- 子类除了继承, 还可以有自己的属性
- `super().__init__` 用于父类初始化

example :

```python
class Person(object):
    """人"""

    def __init__(self, name, age):
        self._name = name
        self._age = age

    @property
    def name(self):
        return self._name

    @property
    def age(self):
        return self._age

    @age.setter
    def age(self, age):
        self._age = age

    def play(self):
        print('%s正在愉快的玩耍.' % self._name)

    def watch_av(self):
        if self._age >= 18:
            print('%s正在观看爱情动作片.' % self._name)
        else:
            print('%s只能观看《熊出没》.' % self._name)


class Student(Person):
    """学生"""

    def __init__(self, name, age, grade):
        super().__init__(name, age)
        self._grade = grade

    @property
    def grade(self):
        return self._grade

    @grade.setter
    def grade(self, grade):
        self._grade = grade

    def study(self, course):
        print('%s的%s正在学习%s.' % (self._grade, self._name, course))


class Teacher(Person):
    """老师"""

    def __init__(self, name, age, title):
        super().__init__(name, age)
        self._title = title

    @property
    def title(self):
        return self._title

    @title.setter
    def title(self, title):
        self._title = title

    def teach(self, course):
        print('%s%s正在讲%s.' % (self._name, self._title, course))


def main():
    stu = Student('王大锤', 15, '初三')
    stu.study('数学')
    stu.watch_av()
    t = Teacher('骆昊', 38, '砖家')
    t.teach('Python程序设计')
    t.watch_av()


if __name__ == '__main__':
    main()
```

- 抽象类 : 不能创建对象的类
- 重写 : 对父类原有方法给出新的实现版本

```python
from abc import ABCMeta, abstractmethod


class Pet(object, metaclass=ABCMeta): # 抽象类
    """宠物"""

    def __init__(self, nickname):
        self._nickname = nickname

    @abstractmethod
    def make_voice(self):
        """发出声音"""
        pass


class Dog(Pet):
    """狗"""

    def make_voice(self):
        print('%s: 汪汪汪...' % self._nickname)


class Cat(Pet):
    """猫"""

    def make_voice(self):
        print('%s: 喵...喵...' % self._nickname)


def main():
    pets = [Dog('旺财'), Cat('凯蒂'), Dog('大黄')]
    for pet in pets:
        pet.make_voice()


if __name__ == '__main__':
    main()
```

- 通过`abc`模块的`ABCMeta`元类和`abstractmethod`包装器来达到抽象类的效果，如果一个类中存在抽象方法那么这个类就不能够实例化（创建对象）

- 使用 `ABCMeta` 元类，我们确保子类 `MyClass` 必须实现抽象方法，否则会引发 `TypeError`。

#### 练习：

**21点游戏**

```python
# 21点游戏
import random


class Card(object):

    def __init__(self, mark, value, name):
        self._mark = mark
        self._value = value
        self._name = name

    @staticmethod
    def get_card_value(mark):
        if mark == 11 or mark == 12 or mark == 13:
            return 1
        else:
            return mark

    @staticmethod
    def get_card_name(mark):
        if mark == 11:
            return 'J'
        elif mark == 12:
            return 'Q'
        elif mark == 13:
            return 'K'
        elif mark == 1:
            return 'A'
        else:
            return str(mark)

    @classmethod
    def rand_card(cls):
        mark = random.randint(1, 13)
        return cls(mark, cls.get_card_value(mark), cls.get_card_name(mark))

    @property
    def value(self):
        return self._value

    def print_card(self):
        print("你抽到了%s, 分数加%d"%(self._name, self._value))


class Player(object):

    def __init__(self, marks, stop = False):
        self._marks = marks
        self._stop = stop

    @property
    def marks(self):
        return self._marks

    @property
    def stop(self):
        return self._stop

    @marks.setter
    def marks(self, value):
        self._marks = value

    @stop.setter
    def stop(self, yes_or_no):
        self._stop = yes_or_no

    def lose(self):
        if self._marks > 21:
            return True
        return False

    def print_marks(self):
        print("当前分数: %d"%self._marks)


def main():
    player1 = Player(0)
    player2 = Player(0)
    print("21点游戏. 输入 q 抽牌, 输入 x 放弃抽牌")

    while True:
        if player1.stop and player2.stop:
            break

        if not player1.stop:
            player1_motion = str(input("Player 1 :"))
            if player1_motion == "q":
                player1_card = Card.rand_card()
                player1_card.print_card()
                player1.marks += player1_card.value
                player1.print_marks()
                if player1.lose():
                    print("玩家2赢了")
                    return
            else:
                player1.stop = True

        if not player2.stop:
            player2_motion = str(input("Player 2 :"))
            if player2_motion == "q":
                player2_card = Card.rand_card()
                player2_card.print_card()
                player2.marks += player2_card.value
                player2.print_marks()
                if player2.lose():
                    print("玩家1赢了")
                    return
            else:
                player2.stop = True

    if player1.marks > player2.marks:
        print("玩家1赢了")
    else:
        print("玩家2赢了")


if __name__ == "__main__":
    main()
```



## 八. 文件和异常处理

### 1. 文件

打开方式 :

| 操作模式 | 具体含义                         |
| -------- | -------------------------------- |
| `'r'`    | 读取 （默认）                    |
| `'w'`    | 写入（会先截断之前的内容）       |
| `'x'`    | 写入，如果文件已经存在会产生异常 |
| `'a'`    | 追加，将内容写入到已有文件的末尾 |
| `'b'`    | 二进制模式                       |
| `'t'`    | 文本模式（默认）                 |
| `'+'`    | 更新（既可以读又可以写）         |

example :

```python
def main():
    f = open('致橡树.txt', 'r', encoding='utf-8')
    print(f.read())
    f.close()


if __name__ == '__main__':
    main()
```

- `encoding` : 解码方式
- `f.read()` : 读取文件全部信息

- 异常处理方法 :

```python
def main():
    f = None
    try:
        f = open('原神.txt', 'r', encoding='utf-8')
        print(f.read())
    except FileNotFoundError:
        print('无法打开指定的文件!')
    except LookupError:
        print('指定了未知的编码!')
    except UnicodeDecodeError:
        print('读取文件时解码错误!')
    finally:
        if f:
            f.close()


if __name__ == '__main__':
    main()
```

- finally : 无论何种情况都会执行的代码, 被称为 "总是执行代码块"
- 不想写finally关闭f : 用`with` `as` :

```python
def main():
    try:
        with open('原神.txt', 'r', encoding='utf-8') as f:
            print(f.read())
    except FileNotFoundError:
        print('无法打开指定的文件!')
    except LookupError:
        print('指定了未知的编码!')
    except UnicodeDecodeError:
        print('读取文件时解码错误!')


if __name__ == '__main__':
    main()
```



## 九. 正则表达式

**正则表达式相关知识** [正则表达式30分钟入门教程 (deerchao.cn)](https://deerchao.cn/tutorials/regex/regex.htm)

更加详细的正则表达式教程: [[../Miscell/正则表达式|正则表达式]]

- Python 提供 re 模块支持正则表达式 :

| 函数                                         | 说明                                                         |
| -------------------------------------------- | ------------------------------------------------------------ |
| compile(pattern, flags=0)                    | 编译正则表达式返回正则表达式对象                             |
| match(pattern, string, flags=0)              | 用正则表达式匹配字符串 成功返回匹配对象 否则返回None         |
| search(pattern, string, flags=0)             | 搜索字符串中第一次出现正则表达式的模式 成功返回匹配对象 否则返回None |
| split(pattern, string, maxsplit=0, flags=0)  | 用正则表达式指定的模式分隔符拆分字符串 返回列表              |
| sub(pattern, repl, string, count=0, flags=0) | 用指定的字符串替换原字符串中与正则表达式匹配的模式 可以用count指定替换的次数 |
| fullmatch(pattern, string, flags=0)          | match函数的完全匹配（从字符串开头到结尾）版本                |
| findall(pattern, string, flags=0)            | 查找字符串所有与正则表达式匹配的模式 返回字符串的列表        |
| finditer(pattern, string, flags=0)           | 查找字符串所有与正则表达式匹配的模式 返回一个迭代器          |
| purge()                                      | 清除隐式编译的正则表达式的缓存                               |
| re.I / re.IGNORECASE                         | 忽略大小写匹配标记                                           |
| re.M / re.MULTILINE                          | 多行匹配标记                                                 |

- example :

```python
# 匹配用户名和QQ号

import re


def check_username(username):
    if re.match("\w{6,20}", username) is not None:
        return True
    else:
        return False


def check_qq_number(qq_number):
    if re.match("[1-9]\d{4,11}", qq_number) is not None:
        return True
    else:
        return False


def main():
    while True:
        username = input("Enter your username: ")
        if check_username(username):
            break
        print("Invalid username")

    while True:
        qq_number = input("Enter your qq number: ")
        if check_qq_number(qq_number):
            break
        print("Invalid qq number")

    print("Your username is: ", username)
    print("Your qq number is: ", qq_number)


if __name__ == "__main__":
    main()
```

- 输出`原神.txt` 中的中国大陆手机号 :

```python
import re


def check_phone_number_cn(text):
    pattern = re.compile(r'(?<=\D)(?:13\d|14[57]|15[0-3]|15[5-9]|17[6-8]|18\d)\d{8}(?=\D)')
    phone_number_list = re.findall(pattern, text)
    return phone_number_list


def main():
    try:
        with open('原神.txt', 'r', encoding='utf-8') as f:
            for text in check_phone_number_cn(f.read()):
                print(text)
    except FileNotFoundError:
        print('无法打开指定的文件!')
    except LookupError:
        print('指定了未知的编码!')
    except UnicodeDecodeError:
        print('读取文件时解码错误!')


if __name__ == '__main__':
    main()
```



## 十. 进程和线程

### 多进程 :

Windows : 引入multiprocessing模块

example :

````python
from multiprocessing import Process
from os import getpid
from random import randint
from time import time, sleep


def download_task(filename):
    print('启动下载进程，进程号[%d].' % getpid())
    print('开始下载%s...' % filename)
    time_to_download = randint(5, 10)
    sleep(time_to_download)
    print('%s下载完成! 耗费了%d秒' % (filename, time_to_download))


def main():
    start = time()
    p1 = Process(target=download_task, args=('Python从入门到住院.pdf', ))
    p1.start()
    p2 = Process(target=download_task, args=('Peking Hot.avi', ))
    p2.start()
    p1.join()
    p2.join()
    end = time()
    print('总共耗费了%.2f秒.' % (end - start))


if __name__ == '__main__':
    main()
````

- 在上面的代码中，我们通过`Process`类创建了进程对象，通过`target`参数我们传入一个函数来表示进程启动后要执行的代码，后面的`args`是一个元组，它代表了传递给函数的参数。`Process`对象的`start`方法用来启动进程，而`join`方法表示等待进程执行结束。运行上面的代码可以明显发现两个下载任务“同时”启动了，而且程序的执行时间将大大缩短，不再是两个任务的时间总和。下面是程序的一次执行结果。
- 当我们在程序中创建进程的时候，子进程复制了父进程及其所有的数据结构，每个子进程有自己独立的内存空间
- 即每个进程中的全局变量等不会同步改变
- 使用Quene可在进程间传递数据 :

```python
from multiprocessing import Process, Queue


def producer(queue):
    for i in range(5):
        data = f"Data item {i}"
        queue.put(data)
        print(f"Produced: {data}")


def consumer(queue):
    while True:
        data = queue.get()
        if data is None:
            break
        print(f"Consumed: {data}")


if __name__ == "__main__":
    # 创建一个队列
    my_queue = Queue()

    # 创建生产者和消费者进程，将队列传递给它们
    producer_process = Process(target=producer, args=(my_queue,))
    consumer_process = Process(target=consumer, args=(my_queue,))

    # 启动进程
    producer_process.start()
    consumer_process.start()

    # 等待生产者完成，并向队列发送结束信号
    producer_process.join()
    my_queue.put(None)  # 结束信号，告诉消费者不再有数据了

    # 等待消费者完成
    consumer_process.join()

```

### 多线程

- threading模块

```python
from random import randint
from threading import Thread
from time import time, sleep


def download(filename):
    print('开始下载%s...' % filename)
    time_to_download = randint(5, 10)
    sleep(time_to_download)
    print('%s下载完成! 耗费了%d秒' % (filename, time_to_download))


def main():
    start = time()
    t1 = Thread(target=download, args=('Python从入门到住院.pdf',))
    t1.start()
    t2 = Thread(target=download, args=('Peking Hot.avi',))
    t2.start()
    t1.join()
    t2.join()
    end = time()
    print('总共耗费了%.3f秒' % (end - start))


if __name__ == '__main__':
    main()
```

> 似乎和多进程的语法是一样的

- 可以使用threading模块的`Thread` 类创建线程
- 即创建其子进程
- 子进程可以调用父进程的函数

```python
from random import randint
from threading import Thread
from time import time, sleep


class DownloadTask(Thread):

    def __init__(self, filename):
        super().__init__()	# 继承
        self._filename = filename

    def run(self):
        print('开始下载%s...' % self._filename)
        time_to_download = randint(5, 10)
        sleep(time_to_download)
        print('%s下载完成! 耗费了%d秒' % (self._filename, time_to_download))


def main():
    start = time()
    t1 = DownloadTask('Python从入门到住院.pdf')
    t1.start()	# 子进程调用父进程的函数
    t2 = DownloadTask('Peking Hot.avi')
    t2.start()
    t1.join()
    t2.join()
    end = time()
    print('总共耗费了%.2f秒.' % (end - start))


if __name__ == '__main__':
    main()
```


#软件工程与计算 #程序设计 #Scheme
# 一. 构造过程和抽象

Lisp😓

Scheme方言

[配置Scheme环境](https://www.cnblogs.com/unixart/p/9182847.html)

## 1.1 程序设计的基本元素

### 1.1.1 表达式

前缀表示

```scheme
(+ 137 349 114514)
; output: 115000
(* 5 99)
; output: 495
```

Lisp采用**前缀表示**, 即将运算符放在所有运算对象左边

这种表示方法适合多个对象, 并且可以直接扩充

```scheme
(+ (* 3 5) (- 10 6))
; output: 19
```

- 美观性: 将属于相同运算符的各个对象放在一列:

```scheme
(+ (- 10

        (* 114

            (/ 2 514)

            2334

            (+ 114

                514))))
```


### 1.1.2 命名&& 环境

Scheme中通过`define`来给变量命名:

```scheme
(define size 3)

(* 5 size)
```


### 1.1.3 组合式的求值

What to do:
1. 求值该组合式的各个子表达式
2. 将运算符作用于其他值

过程本身是递归的

用数形象的表示求值的过程, 求职的过程 (值向上穿行) 为树形积累的一个特例

- 数的值为他们所表示的数值
- 内部运算符的值即为对应的机器指令
- 名称的值为对应的值


### 1.1.4 复合过程

编程语言通性:

- 数 && 算数运算
- 组合式的嵌套
- 定义变量

**过程定义**: 即定义函数

Example:

```scheme
(define (square x) (* x x))
```

Common type:

```scheme
(define (<name> <paramenters>) <body>)
```


### 1.1.5 过程应用的代换模型

分为应用序代换和正则序代换

- 应用序代换: 先计算每个部分再带进去
- 正则序代换: 先把表达式带进去再递归求值


### 1.1.6 条件表达式和谓词

#### 条件表达式

Lisp中, 用`cond`表示条件: 

```scheme
(define (abs x)
    (cond   ((> x 0) x)
            ((= x 0) x)
            ((< x 0) (- x))))
            
(write (abs -100))
```

原理: 从上至下检查表达式的值是否为真, 若为真则返回

可以使用`else`来简化:

```scheme
(define (abs x)
    (cond   ((< x 0) (- x))
            (else x)))
            
(write (abs -100))
```

若只有两种情况, 则可以使用`if`

```scheme
(define (abs x)
  (if (< x 0) (- x)
			  x))
```


#### 谓词

`and`, `or`, `not`

```scheme
; 从左到右分别求值, 如果有值为F的, 则直接返回F; 如果直到结束还没有返回, 则返回T
(and <e1> ... <en>)
; 从左到右分别求值, 如果有值为T的, 则直接返回T; 如果直到结束还没有返回, 则返回F
(or <e1> ... <en>)
; 返回 $\lnot e$
(not <e>)
```


### 1.1.7 用牛顿法求平方根

> 就是牛顿迭代法

```scheme
(define (sqrt_iter guess x)
  (if (good_enough guess x)
	  guess
	  (sqrt_iter (improve guess x)
				  x)))

(define (improve guess x)
  (average guess (/ x guess)))

(define (average x y)
  (/ (+ x y) 2))

(define (good_enough guess x)
  (< (abs (- (square guess ) x)) 0.001))

(define (sqrt x)
  sqrt_iter(x x))

(define (abs x)
  (if (< x 0) (- x)
			  x))

(define (square x) (* x x))
```
#程序设计 #Java #软件工程与计算 

# 1. 类型

java有类型

**为什么需要类型:**

- 便于早期检查错误
- 生成更加高效的代码(比如存储的大小)
- 给了程序更多语义信息, 方便进一步优化 (如省去异常处理部分)
- 模块化, 有助于代码阅读 && 维护

缺点: 程序受限

# \* 在lambda calculus 中使用类型

可防止无限循环

$M, N::=$ $x|\lambda x: τ .M|MN$

**类型判断:**

$Γ ⊢ M : τ$

- 上下文 Γ 是对于 中每个自由变元的类型设定，比如如果出现了变元 x , Γ 必须包含一个 $x: \sigma$ 这样的设定 
- Γ可以是 $\cdot$ 表示为一个空的上下文

类型推导规则:

![[../../00 Resource/res/Pasted image 20240506101636.png]]

可以帮助删掉一些不合法的项

## 静态类型 && 动态类型

- 静态类型: 编译阶段被获知
	- 一般情况下需要程序员标注类型, 但也有语言(如 OCaml) 使用类型推断来获取类型, 无需标注
- 动态: 运行阶段才根据变量的值来获取类型

## 类型检查

类型检查一般用来检测类型相关的错误（⚠不是和动态、静态语言对应） 

- 静态检查在程序运行之前发现相关错误： 
	- 语法错误（python也会做相关的检查，如缩进错误等）
	- 操作数类型错误
	- 参数类型错误
	- 返回类型错误等 
- 动态检查在程序执行时发现类型错误（静态分析无法找到所有可能的类型错误（根据莱斯定理））： 
	- 非法的具体的操作数值（比如除数为0）
	- 非法的类型转换（比如 Integer.valueOf(“hello”)）
	- 越界错误
	- null对象的方法调用等。

## Java中的类型

**原始数据类型**

和 C 基本一致

| Type    | 数据位 | 范围                              | 默认值       |
| ------- | --- | ------------------------------- | --------- |
| char    | 16  | '\\u0000' - '\\uffff' (unicode) | '\\u0000' |
| boolean | 1   | true, false                     | false     |

**常用对象类型**

| Type   | 所属类              | 描述                                                  | 默认值  |
| ------ | ---------------- | --------------------------------------------------- | ---- |
| String | java.lang.String | 字符串 (const)                                         | null |
| Number | java.lang.Number | 数字类 (BigInterger, Byte, Double, Float, Long, Short) | null |

## 常量 && 变量

- 常量: 不可变量, 用 `final` 修饰

```java
final int MAX = 10;
final double PI = 3.1415d;
// 可以连续声明
final int NUM1 = 114, NUM2 = 514;
```

> 常量最好大写

- 变量: 不用 `final`

local variable 没有赋值前无法使用

Example:

```java
int num;

if(hailstone(50) == 1){
	num = 1;
}else{

}

System.out.println(num); // Error
```

类变量不会出现问题

## 操作

![[../../00 Resource/res/Pasted image 20240506104750.png]]

## 类型转换

隐式: 自动

字符串 -> 数:

`<typename>.parse<typename>(String)`


# 2. 控制

## switch

- 使用 `yield` 让 `switch` 包含一个复合语句
- 可以将 `switch` 的值赋给变量, 此时不用写 `break`, 可以写 `->` or `yield` : 
 
```java
int num = switch (day){
	case MONDAY, FRIDAY, SUNDAY -> 6;
	case THURSDAY -> {
		System.out.println("疯狂星期四v哦50");
		yield 50;
	}
	default -> {
		throw new IllegalStateException("Invalid day" + day);
	}
};
```

## 循环

和 C 基本一致

```java
int[] seq = {1, 2, 3, 4, 5};
for(int j: seq){
	System.out.println(j);
}
```


# 3. 容器

## 3.1 数组

```java
type[] var = {v1, v2, ...}; // declaration
type[] var = new type[size]; // declaration and allocation
```

```java
int[] num = new int[] {1, 2, 3, 4, 5, 6}; // 堆
int[] num = {1, 2, 3, 4, 5, 6}; // 栈
int[] num = new int[10]; // 堆
```

**Java语言内存分配:** 

- 栈内存：定义的基本类型的变量和对象的引用变量，超出作用域将自动释放。 
- 堆内存：存放由new运算符创建的对象和数组。由Java虚拟机的自动垃圾回收器来管理。

**多维数组**

```java
int[][] a = new int[3][4];
type[][]... name = new type[size1][size2]...;
```


## 3.2 字符串

```java
String a = "abc";
String b = new String("abc");
String c = new String("abc");
System.out.println(b.equals(c)); // true
System.out.println(b == c); // false (判断对象引用是否相同)
System.out.println(b.substring(1, 2)); // "b"
System.out.println(a.substring(1)); // "bc"
char c = a.charAt(0); // 'a'
int index = a.indexOf("bc"); // 1
String d = " abc ";
System.oout.println(d.trim()); // "abc"
```

尽量用 `equals` 方法去判断两个字符串

```java
String s1 = "abc";
String s2 = "abc";
System.out.println(s1 == s2); // true
```

## 3.3 列表

常用的列表: ArrayList, LinkedList

```java
import java.util.List; 
import java.util.ArrayList;
import java.util.LinkedList;

public class ListDemo { 
	public static void main(String[] args) { 
		List L = new ArrayList(); 
		L.add("a"); 
		L.add("b"); 
		L.add("c"); 
		System.out.println(L); 
	} 
}

public class ListDemo2 { 
	public static void main(String[] args) { 
		List L = new LinkeList(); 
		L.add("a"); 
		L.add("b"); 
		L.add(1); 
		System.out.println(L); 
	} 
}
```

java 是如何实现的: 类型擦除

## 3.4 Map

常用方法:

![[../../00 Resource/res/Pasted image 20240513090457.png]]


## 3.5 容器的迭代

使用 `Iterator` 或者 for

```java
List<String> cities = new ArrayList<String>;

// way 1
for(var x: cities){
	...;
}

Iterator<String> x = cities.iterator;

while(x.hasNext()){
	System.out.println(x.next());
}
```

迭代时最好不要做修改
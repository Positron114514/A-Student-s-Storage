#Java #程序设计 #基础教程 

> [!Notes] 
> 阅读Java核心技术过程中记的笔记
> 基础知识请见[[Java学习笔记|Java学习笔记]]


# 1. 数据类型

## 1.1 String 

Java中String类似于一个指针, 可以按 `UTF-16` 存储字符

`length` 方法返回 `UTF-16` 表示给定字符串所需要的代码单元数量

```java
public class Main{  
  
    public static void main(String [] args){  
        String s = "😓";  
        System.out.println(s.length());  
    }  
}

// Output: 2
```

想得到实际的长度, 可以使用方法 `codePointCount(int, int)`

```java
public class Main{  
  
    public static void main(String [] args){  
        String s = "😓";  
        System.out.println(s.codePointCount(0, s.length()));  
    }  
}

// Output: 1
```

方法 `charAt(int n)` 返回字符串第n个位置的字符

想要得到第 n 个码点, 应使用下列语句:

```java
public class Main{  
  
    public static void main(String [] args){  
        String s = "原神😓";  
        // 寻找码点在哪个位置
        int index = s.offsetByCodePoints(0, 2);  // 索引和C一致
        int cp = s.codePointAt(index);  
    }  
}
```

 方法`repeate()`可以重复字符串：

```Java
String shit = "shit".repeate(3);
```

方法 `equals` 可以检测字符串是否相等：

```java
if(s.equals("genshin")){
	System.out.println("Genshin");
}

// Output: Nothing
```

检查字符串既不是 `null` 也不是空串：

```java
if(str != null && str.length() != 0){
	// Your code here
}
```

遍历 Java 字符串:

```java
int i = 0;
while(i < s.length()){
	int cp = s.codePointAt(i);
	
	// Your code here
	
	if(Character.isSupplementaryCodePoint(cp)){
		i += 2;
	}else{
		i++;
	}
}
```

A better way:

```java
int[] codePoints = s.codePoints().toArray();
// 遍历数组
```

码点 $\to$ 字符串

```java
String s = new String(codePoints, 0, codePoints.length());
```


如果想要由多个小的片段拼接成一个字符串，可以使用`StringBuilder` 类

```java
StringBuilder builder = new StringBuilder();

builder.append(ch);
builder.append(str);

String s = builder.toString();
```

可以用 `format` 方法创建一格式化的字符串：

```java
String msg = String.format("You've played Genshin for %d times.", time);
```


# 2. I / O

## 2.1 控制台 I / O

Java中读入内容需要创建 `Scanner` 对象：

```java
Scanner in = new Scanner(System.in);
```

`Scanner` 中的方法：

```java
Scanner(InputStream in);

String nextLine();

String next();

int nexInt();

double nextDouble();

boolean hasNext();

boolean hasNextInt();

boolean hasNextDouble();
```

密码输入: 使用 Console 类:

```java
Console cons = System.comsole();
char[] pwd = cons.readPassword("Password: );
```

使用 `char[]` 存储密码保证安全 (?) , 对密码处理完成后, 需立刻用一个填充数覆盖字符串

Java中也可以使用 `System.out.printf()` 来像[[../../C/CPL进阶-翁恺|C语言]]一样使用 `printf`

## 2.2 文件 I / O

Java中读入文件需要创建 `Scanner` 对象

```java
Scanner in = new Scanner(Path.of(path), Standard(Charset.UTF_8));
```

Java中写入文件需要构造 `PrintWriter` 对象：

```java
PrintWriter out = new Printwriter("my file.txt", StandardCharsets.UTF_8);
```

文件名不存在则创造该文件

在main方法中用 `throws` 子句标记来告诉编译器可能会产生 `I / O` 问题:

```java
public static void main(String[] args) throw IOException{
	// your code here
}
```


# 3. 循环和判断

Differences between Java and C:

- Java 中 switch 语句的 case 值可以为 String 
 
- break 中加入标签: 

```java
genshin:
while(x > 100){
	...
	while(...){
		...
		break genshin; // get out of "genshin:"
	}
}
```

这种方法能用于任何代码块, 不局限于循环



# 4. HP (大数)

> 应该是用高精度算法实现的

```java
BigInterger a = new Biginterger("128749813789471983749813749871389471398347")
```

 常量: `BigInterger.ZERO` `BigInterger.ONE` `BigInterger.TEN` `BigInterger.Two`

加减乘除: 

```java
BigInterger c = a.add(b);
BigInterger d = a.multiply(b);
```


方法: 

```java
BigDemical add(BigDemical other);

BigDecimal add(BigDecimal other)  
  
BigDecimal multiply(BigDecimal other)  
  
BigDecimal subtract(BigDecimal other)  
  
BigDecimal divide(BigDecimal other)  
  
int compareTo(BigDecimal other)  
  
static BigDecimal valueOf(long x)  
  
static BigDecimal valueOf(long x, int scale)
```


# 5. 数组

## 5.1 数组拷贝

用 "=" 拷贝数组: 拷贝后引用同一个数组 (相当于拷贝指针)

用 `Arrays.copyOf` 拷贝数组: 直接复制全部值到另一个数组

一种常用来增加数组长度的方法:

```java
longerArr = Array.copyOf(arr, 2 * arr.length)
```

额外的元素设为这种类型的默认值 (0 / false / etc)

> [!Notes]
> `Java`中用
> 
> 	int[] a = new int[100];
> 
> 相当于`Cpp`中的
> 
> 	int* a = new int[100];
> 
> 而与
> 
> 	int a[100]
> 
> 不同
> 
> Java中的 `[]` 运算符会检查越界问题, 且不支持指针运算 (如 \*(a + 1))


## 5.2 命令行参数

使用如下命令调用 `Java` 程序:

```java
java Message -g holy shit
```

则有 `args[0] = "-g"` , `args[1] = holy` , `args[2] = shit`


# 5.3 java.util.Arrays

常用方法:

```java
public static <T> List<T> asList(T... a);

public static int binarySearch(int[] a, int key);

public static <T> T[] copyOf(T[] original, int newLength);

public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType);

public static int[] copyOfRange(int[] original, int from, int to);

public static boolean equals(int[] a, int[] a2);

public static void fill(int[] a, int val);

public static void sort(int[] a);

public static <T> Spliterator<T> spliterator(T[] array);

public static <T> Stream<T> stream(T[] array);

```

> TMD让GPT帮忙写结果写出来将近100条, 可见 Java 之大


## 5.4 多维数组

```java
int[][] genshin = {
	{1, 1, 4, 5},
	{4, 5, 1, 4},
	{1, 9, 1, 9},
	{9, 8, 1, 0}
}
```

剩下和 C 一样

由于Java中多维数组实际上是装着数组的数组, 因此可以构造不规则数组: 

```java
int[][] a = {
	{1},
	{1, 4},
	{5, 1, 4}
}
```


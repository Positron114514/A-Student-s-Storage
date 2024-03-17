#Java #程序设计 #OOP #基础教程 

> [!Notes] 
> Java中的OOP, 感觉应该和Cpp差不多

> book: [[Java核心技术·卷 I（原书第11版） (凯·S.霍斯特曼 (Cay S. Horstmann)) (Z-Library).pdf|Java核心技术]]
#  1.类和对象

## 1.1 Introduction

对象的三个特征:

- 行为: 方法
- 状态: 用对象中的数据的值白哦是
- 标识: 对象名

如何组织程序: 名词对应类, 动词对应方法

### 1.1.1 类之间的关系

- 依赖 ("uses-a")
- 聚合 ("has-a")
- 继承 ("is-a")

**依赖**: 一个类需要另一个类的数据等才能工作, 组织程序时应尽可能减少相互依赖, 即尽可能减少类之间的**耦合**

**聚合**: 一个对象内部包含另一个对象. 如 `Rect` 和 `Point`

**继承**: 一个更抽象的类和一个更一般的类的关系

可以使用 `UML`(统一建模语言) 来绘制类图, 用来描述类之间的关系. 下图为 UML 中常见的箭头样式:

![[../../00 Resource/res/Pasted image 20240313221632.png]]

## 1.2 使用预定义类

Java中并非所有类都表现OOP特性 (如 `Math` 类)

```java
// 此时class起的作用更像是C++中的namespace
a = Math.abs(a);
```

### 1.2.1 对象与对象变量

Example: `Date` 类

用 `new` 构造一个新对象

可以将用 `new` 构造的新对象作为参数传递给函数, 也可以直接使用刚构造好的对象的方法:

```java
String s = new Date().toString();
```

使用对象的类方法时必须先用 `new` 构造对象

Java 中的对象都是通过引用传递的, 包括 `new` , 相当于 C++ 中的指针

可以将对象设置为 `null`, 指示该对象没有任何引用对象

```java
deadline = null;
```

Java中需要使用 `clone` 方法获得对象的完整副本

### 1.2.2 Localdate类

用日历表示法表示日期

和 Date 类不同, Date 类用于表示时间点

不需要使用构造器来构造 `Localdate` 类的对象, 应使用静态工厂方法, 其会代表你使用构造器: 

```java
// 构造一个新对象, 表示构造该对象时的日期
LocalDate.now();
// 提供年月日来构造特定日期的对象
LocalDate.of(1999, 12, 31);

LocalDate zhiShuJie = LocalDate.of(2024, 3, 12);
LocalDate dateNow = LocalDate.now();

int year = zhiShuJie.getYear();
int month = zhiShuJie.getMonthValue();
int day = zhiShuJie.getDayOfMonth();

LocalDate twoThousandDaysLater =  zhiShuJie.plusDays(2000);  
year = twoThousandDaysLater.getYear();  
month = twoThousandDaysLater.getMonthValue();  
day = twoThousandDaysLater.getDayOfMonth();  
```

Example: 打印出如下的当月日历:

![[../../00 Resource/res/Pasted image 20240313230712.png]]

```java
import java.math.BigDecimal;  
import java.time.DayOfWeek;  
import java.time.LocalDate;  
import java.util.*;  
  
public class Main{  
  
  
    public static void main(String [] args){  
        LocalDate date = LocalDate.now();  
  
        int month = date.getMonthValue();  
        int today = date.getDayOfMonth();  
  
        date = date.minusDays(today - 1);  
        DayOfWeek dayOfWeek = date.getDayOfWeek();  
  
        int week = dayOfWeek.getValue();  
  
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");  
        for(int i = 1; i < week; i++){  
            System.out.print("    ");  
        }  
  
        while(true){  
            int day_now = date.getDayOfMonth();  
            System.out.printf("%3d", day_now);  
  
            if(day_now == today){  
                System.out.print("*");  
            }else {  
                System.out.print(" ");  
            }  
  
            date = date.plusDays(1);  
  
            if(date.getMonthValue() != month){  
                break;  
            }  
            dayOfWeek = date.getDayOfWeek();  
            week = dayOfWeek.getValue();  
            if(week == 1){  
                System.out.println("");  
            }  
        }  
    }  
}
```

OutPut: 

```console
Mon Tue Wed Thu Fri Sat Sun
                  1   2   3 
  4   5   6   7   8   9  10 
 11  12  13* 14  15  16  17 
 18  19  20  21  22  23  24 
 25  26  27  28  29  30  31 
 ```

### 1.2.3 更改器和访问器方法

和[[../python/python学习笔记#Property和Setter装饰器|python]]类似, 但没有修饰词

## 1.3 自定义类

Example: Employee 类

![[../../00 Resource/res/Employee.java]]

### 1.3.1 构造器

所有Java对象都是在堆中构造的, 每个类可以有一个以上的构造器

注意在构造器中定义的变量是局部的

### 1.3.2 用 var

类似于C++中的 [[../C++/C++学习笔记#^autoInCpp|auto]]

```java
var genshin = new Employee("Genshin", "Cleaner", 2000.0, 2011, 12, 31);
```

只能用于方法中的局部变量

### 1.3.3 对 null 的处理

当接受一个对象作为参数时, 需要考虑该问题

Way1: 手动检测

```java
if(name == null){
	this.name = "unknown";
}else{
	this.name = name;
}
```

Way2: 使用Objects中的类方法:

```java
this.name = Objects.requireNonNullElse(name, "default");  
this.job = Objects.requireNonNullElse(job, "default");  
this.bankCodeNumber = "default";  
this.salary = salary;  
this.hireDate = LocalDate.of(year, month, day);

// 严格禁止等于null:
Objects.requireNonNull(name, "The name cannot be null.");
this.name = name;
```

### 1.3.4 封装的优点

- 防止数据被随意篡改
- 在更改数据时能检查更改是否合法
- 返回对象中数据时可以返回其克隆, 防止被篡改

```java
return (Node) root.clone(); // OK
```

如果需要返回一个可变数据字段的副本, 就应该使用 `clone` 

So可以改一下我的 Huffman Tree 中返回对象的方式:

```java
// Old
return new Node(root);
// New
return (Node) root.clone();
```

### 1.3.5 基于类的访问权限

Java中, 一个类方法可以访问所有该类的对象的私有数据



### 一个sortedSet类

其实是我在刷 SICP 的时候写的 ()

![[../../00 Resource/res/sortedSet.java]]

我自己觉得写的好的几个点: 

- 方法merge采用了merge sort的排序方法, 加速过程
- 定义了 private 构造器, 能够直接使用一个排序好的 ArrayList 构造一个集合, 速度更快
- 向getElementsPlace传入left参数, 使得add(sortedSet) 效率更高
- add使用魔改二分查找添加, 效率更高
- 重载了多个add方法和构造器方法, 更为灵活

### Huffman Tree

From SICP Chap2

![[../../软件工程与计算/SICP/2. 构造数据抽象#2.3.4 实例 Huffman编码树|Huffman Tree]]


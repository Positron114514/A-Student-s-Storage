#程序设计 #Java #软件工程与计算 

> PPT: [[../钮老师课件/12 编程进阶-Java面向对象.pdf|12 编程进阶-Java面向对象]]


# 1. 类

## 1.1 类声明

```java
[modifiers] class ClassName [extends Super] [implements InterfaceNames]{
	...;
}
```

- `modifiers`: `abstract`, `final`, `public`
- ClassName: 名字
- Super 只能有一个
- InterfaceNames: 接口名字的合集, 用 `,` 隔开, 当前定义的类会实现这些接口

modifiers:
- public: 可被包外的类访问
- final: 不可有子类
- abstract: 抽象类, 不可以实例化对象
- 空: 缺省类 (package-private)

## 1.2 包结构

- 包为Java的类提供了一种类似文件系统的组织形式，可以有效避免 命名冲突。将相关的类组织到相关的包中是很好的模块化设计。 
- 每一个Java的类都隶属于一个包 
- 每个Java的源文件的第一句就是包声明 
- 如果源文件没有包声明语句，那么其隶属于一个无名的默认包中

Example:

```java
package huffmantree;

public class HuffmanTree{
	...;
}
```

```java
package huffmantree.node;

public class NodeContent{
	...;
}
```

(类似于文件夹系统)

可使用 import 语句引入包中的内容

```java
import begin.Itera;
import other.*

public class ImportSample{
	Iter i;
	Sample s;
}
```

等价于:

```java
public class ImportSample{
	begin.Itera i;
	other.Sample s;
}
```

## 1.3 成员变量

```java
class MyClass{
	private int a = 10;
	public static int b = 20;
}
```

不同关键字的效果:

![[../../00 Resource/res/Pasted image 20240513094612.png]]

## 1.4 访问控制

对于敏感数据应设为私有变量, 从而对外界进行隔离

通过公共方法 (get 和 set) 来进行访问和修改这些变量

Example:

```java
public class Person{
	private String name;

	public String getName(){
		return name;
	}

	public void setName(String newName){
		this.name = newName;
	}
}
```

## 1.5 方法

```java
[access] [final] returnType ([paramlist]) [throws exceptionsList]
```

`javadoc` 用来提供类 / 方法的一些说明

如参数 返回值

```java
public class Hailstone{
	/**
	 * introduction
	 * @param n ...
	 * @return ...
	 */
	 type myFunc(int n){
		 ...
	 }
}
```

可以改变引用的对象的值, 但对引用的重新赋值不会对原引用造成影响

## 1.6 参数传递

**构造方法**: 与类同名的方法, 没有返回值

```java
class Name{
	private int a;
	private String b;
	
	Name(int a, String b){
		this.a = a;
		this.b = b;
	}
}
```

通过 new 来隐式调用构造子

## 1.7 static

用来表达隶属于“类”本身，而不隶属于类的对 象的一个关键字

可以修饰：

- 变量（该变量即为类变量或静态变量）
- 方法（该方法即为类方法或静态方法） 
- 语句块（用来初始化类变量）

**静态变量**: 所有对象的共有属性

静态变量在JVM对类加载之后就得到了相应的内存， 其只有一份，不会随着对象的创建增多。

**静态方法**: 可以直接用类名来调用，而无需实例化 一个对象来调用

可以访问和更改静态变量，但不能直接访问非静态 的成员（变量和方法）

在静态方法中，this（表示当前对象的引用）和super（表示当 前对象的相应的父类对象引用）无法使用

```java
class LlmHandler{
	public static Py_Object getPyFunc(Py_Objetc pyFile, String funcName){
		...;
	}
}

// use: 
LlmHandler.getPyFunc(file, "getCompletion");
```

不会把对象作为参数传入 static 方法 (?)

# 2. 继承

`extends`

```java
class SubClass extends SuperClassName{
	...;
}
```

java 只支持单继承

**重写**: 重新定义方法

重新定义变量会隐藏父类的变量

- 重写对应运行时，Java会在运行时判断哪个方法会被调用 （It is for non-static methods.） 
- 隐藏对应编译时，Java在编译阶段就已经确定好了调用的 对象（即静态和实例变量、静态方法）

- 任何类的构造方法, 第一行语句必须是调用父类的构造方法
- 若没有明确调用, 则编译器会自动加一句 `super();`
- 构造方法无法继承, 隶属于特定的类. 若类无构造方法, 则会有一个默认构造方法, 而不是继承父类的构造方法

**访问修饰符与继承的关系**:

- 私有的（private）类成员不能被子类继承 
- 构造方法不被继承 
- 公共的（public）和保护性的（protected）类成员能被 子类继承，且子类和父类可以属于不同的包 
- 无修饰的父类成员，仅在同一个包中才能被子类继承

- 重写可继承的函数时, 其访问权限不能比父类中被重写的方法的访问权限更低
	- 可以 `protected` `->` `public`, 但不能 `protected` `->` `private`
- 重写可继承的函数时，返回值类型如果是原始类型，必须一致，如果是对象类型，那么可以不一致（但必须是原函数返回值的子类）
- 对隐藏而言没有限制 (本质上时和原来两种不同的成员, 在编译阶段就已经分开了)

## 函数重载

```java
int sum(int a, int b);

double sum(double a, double b);
```

## instanceof

- 子类的对象也是父类的对象
- 可以直接用子类对象赋给以恶搞父类对象: `SuperClass variable = new SubClass();`
- 称为向上转型 (upcasting)
- 想要安全的进行向下转型, 一般需要使用 `instanceof` 运算符来判断:

```java
if(animal instanceof Dog){
	Dog dog = (Dog) animal; // 强制类型转换
}
```

# 3. 抽象类

- 如果一个类中没有包含足够的信息来描绘一个具体的对象 （方法没实现），这样的类就是抽象类。 
- 并不能直接由抽象类创建对象，只能通过抽象类派生出新的子类，再由其子类来创建对象。

```Java
abstract class ClassName{
	...;
	abstract typeName methodName(parameters);
}
```

**继承抽象类**:

- 抽象类的子类可以是抽象类
- 若不是抽象类, 则必须实现父类中的所有抽象方法 (即抽象方法必须被子类的方法所覆盖)
- 抽象类限制了子类的行为, 类似于模板

Example:

```java
abstract public class Shape{
	protected String name;
	public Shape(String xm){
		name = xm;
	}
	abstract public doubel getArea(); // 面积
	abstract public doubel getLength(); // 周长
}

public class Circle extends Shape{
	private final static doubel PI = 3.1415926;
	private double radius;

	public Circle(String xm, doubel r){
		super(xm);
		this.radius = r;
	}

	public doubel getArea(){
		return PI * radius * radius;
	}

	public doubel getLength(){
		return 2 * PI * radius;
	}
}
```


# 4. 接口

java 单继承 -> 引入接口实现类似 "多继承"

继承也会有很多问题，如果滥用继承，会导致继承 层次过深。而复杂的继承关系会导致维护性降低

提供了多个模板的可能

可以看成抽象方法的集合。一个类可以实现多个接口

```java
[public] interface interfaceName [extends List_of_super_implements]{
	[static/final] type var = const;
	retType function(type a, tyoe b);

	static retType methodName(par){
		body;
	}

	[public] default type methodname(para){
		body;
	}
}
```

- 接口不能用于实例化对象。 
- 接口没有构造方法。 
- 接口只有三种类型的方法（抽象方法（缺省）、静态方法、default 方法）。 
- 接口的变量都是 static final 修饰的（静态常量），缺省的也是静态常量。 
- 接口的成员可访问性**都是public**（缺省也是public）。

```java
class Trial implements List_of_interface{
	...
}
```

- 一个类必须实现接口的所有抽象方法（除非该类是抽 象类） 
- 类实现的所有抽象方法都必须声明public 
- 接口可以作为一种引用类型使用，可以声明接口类型 的变量或数组，并用它来访问实现该接口的类的对象。

- 静态方法必须接口名访问

## 接口继承

接口可继承接口

```java
public interface Face1{
	...
}

public interface Face2{
	...
}

public interface Face3 extends Face1, Face2{
	...
}
```

## Mixin

命名冲突问题:

- 对于常量，若名称不冲突，子接口可以继承多个父接口中的常量，但如果多个父接口中有同名的常量，则必须通过接⼝名.常量名区分。 
- 对于多个父接口中存在同名的方法包含默认方法时，也会发生命名冲突，这时不能通过接⼝名.默认⽅法名来解决, 必须通过在当前类中定义一个同名的方法才行

```java
public interface Face1 {
    static final double PI = 3.14;

    abstract double area();

    default void setColor(String c) {}
}

  

public interface Face2 {
    static final double PI = 3.1415; 

    default void setColor(String c) {
        System.out.println("颜⾊是 ： " + c);
    } 

    abstract void volume();
}

/** error */ 
public interface Face3 extends Face1, Face2 {
} 
// Face3 inherits unrelated defaults for setColor(String) from types Face1 and Face2 
/** correct */ 
public interface Face3 extends Face1, Face2 { 
	default void setColor(String c){ 
		System.out.println(Face2.PI); 
		System.out.println("颜⾊是 ： "+ c); 
	} 
} 
/** correct */ 
public interface Face3 extends Face1, Face2 { 
	default void setColor(String c){ 
		Face2.super.setColor(c); 
	} 
}
```

如果发生了继承类和实现接口的命名冲突, 那么“类”优先，继承的父方法中的同名方法
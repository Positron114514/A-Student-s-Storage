#软件工程与计算 #Java #程序设计 

# 1. 泛型

通过引入“泛型”（generics）的概念，Java可以将类型也作为一种参数，从而实现参数多态

- 泛型类的定义：` [modifiers] class className <T, E, …> {…} `
- 泛型接口的定义： `[modifiers] interface interfaceName <T, E, …> {} `
- 泛型方法的定义： `[modifiers] <T, E, … > returnType functionName (parameterList) {…}`

泛型的类型可以做一些限制:

`<T extends anyClass>` : T 必须是 anyClass 的子类, 或实现了anyClass接口的类, anyClass可以为类/接口

**类型擦除**:

- 其实Java的泛型都是伪泛型，其为了能够后向兼容Java旧时代 （Java 4.0及以前，还没有泛型时）代码，并不是运行时进行泛型的 支持，而是通过编译器在编译阶段对类型进行擦除
- 如果类型是受限的，则会替换为其限定类型

```java
class Foo<T>{
	T x;
	T method(T y){
		...
	}
}
// after compile
class Foo{
	Object x;
	Object method(Object y){
		...
	}
}
```

注意:

- 泛型的实际参数类型不能是原始类型（因为都会被擦除为Object或其限定类） 
- instanceof 判断不了泛型，比如： `C<String> a = … ; a instanceof C<Integer> ; //Error `
- 不能用泛型创建对象，即 T a = new T(); （因为本质上是new Object()！而不是想要的类对象）
- 不能声明静态的泛类型的变量

**类型通配符** 

- 之前一个泛型对象名只能引用同一种泛型对象，如 `GeneralType<String> a` 只能指向`GeneralType<String>` 的对象。 
- 如果要使用同一个泛型对象名去引用不同的泛型对象，就需要 使用通配符 “?” 创建泛型类对象。
	- 但要求不同泛型对象的类型实参必须是某个类或者其子类，或实现某个接口
	- `<? extends T> x = null;`

```java
GeneralType<? entends Number> x = null;
x = new GeneralType<Long>();
x = new GeneralType<Interger>();
Number a = x.getObj(); // Correct
x.setObj(Interger.valueOf(1)); // Error ?
```

对于JVM而言，其无法确定x的对象的实际参数类型到底指向Number哪个子类， 如果实际是Integer类型，此时写入Long修改会出错 （Integer和Long不是父子关系！） 但是读取是不影响的（都以Number类型的方式读取）(?)

- 除了可以利用extends限定实际类型参数是某个类 型的子类外 （设置上限），还可以用super 限定 其是某个类的父类 （设置下限） 
- 语法： 泛型类型 `<? super anyclass> x = null;`

```java
GeneralType <? super Integer> x = null; 
x = new GeneralType <Object> (); 
x = new GeneralType <Number> (); 
Number a = x.getObj(); //Error 
x.setObj(Integer.valueOf(1)); //Correct
```

编译器并不知道到底是Integer的哪个父类 （注：这里可以强制转化为Object类使得编译通过）

# 2. 其他特性

## 2.1 对象的一些特殊函数

- String toString()， 默认返回 class名称 + @ + hashCode 的十六进制字符串，重写可以定制一 个对象的具体打印结果（System.out.println()） 
- boolean equals()，判断两个对象是否相等，默认两个对象是否是同一个对象，但一般将其重 写为连个对象的内容是否相等（自己定义“内容相等”）。比如字符串类就重写了这个函数，只 要字符串包含的内容相等， equals()就返回 True 
- int hashCode()，返回对象的散列值（hash code），当重写了equals()之后，必须也重写该函数， 使得equals()为真的两个对象，hashCode()的值也必须一致 （hashCode是hashMap的核心）。

## 2.2 lambda表达式

lambda表达式不可以对外界变量做修改

Example:

```java
import java.util.ArrayList; 

public class Main { 
	public static void main(String[] args) { 
		ArrayList<Integer> numbers = new ArrayList<Integer>(); 
		numbers.add(5); 
		numbers.add(9); 
		numbers.add(8); 
		numbers.add(1); 
		// Way 1
		numbers.forEach( (n) -> { System.out.println(n); } );
		// Way 2
		Consume<Interger> a = (n) -> {System.out.println(n);} 
		numbers.forEach(a);
	} 
}
```

- Java内置的存储lambda函数的变量类型： 
	- `Consumer<T>` 接受一个输入，不返回值 
	- `Supplier<T>` 不接受输入，返回一个值 
	- `Function<T, R>` 接受一个输入, 返回一个输出 
	- `BiFunction<T, U, R>` 接受两个输入，返回一个输出\
- 也可以自定义能够从出lambda表达式的变量
	- 前提是该变量的类型是只有一个方法的接口。Lambda 表达 式应该具有与该方法相同数量的参数和相同的返回类型

```java
interface Lambda{
	int display(int number);
}

Lambda lambda = number -> {
	return number * number;
};

System.out.println(lambda.display(20)); //此处的display即调用lambda表达式
```

柯里化:

```java
Function<Integer, Function<Integer, FUnction<Integer, Integer>>> curried = 
a -> b -> c -> (a + b + c);
```

调用:

```java
int result = curried.apply(1).apply(2).apply(3);
System.out.println("Result: " + result);
```

## 2.3 流

Java流是一种对集合进行链状流式的操作，但是操作不会改变原来的数据（函数式编程思想）

```java
List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl"); 

List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
```

流 + lambda表达式会大大减少代码量

**map**

```java
List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5); 

List<Integer> squaresList = numbers.stream().map( i -> i*i).collect(Collectors.toList()); 

squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList())
```

**filter**

```java
List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl"); 

long count = strings.stream().filter(string -> string.isEmpty()).count();
```

**sorted**

```java
List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5); 
List<Integer> sortedList = numbers.stream().sorted().collect(Collectors.toList()); 
List<Integer> sortedList = numbers.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()); 

numbers.stream().sorted((a, b) -> {return a.compareTo(b);}).forEach( n -> System.out.println(n) )
```

**reduce**

```java
int reduced = IntStream.range(1, 4).reduce((a, b) -> a + b).getAsInt()
```

**Iterate**

```java
Stream<Integer> stream = Stream.iterate(0, (x) -> x + 2).limit(6); 
stream.forEach(System.out::println);
```

**Match**

```java
ist<Integer> list = Arrays.asList(1, 2, 3, 4, 5); boolean allMatch = list.stream().allMatch(e -> e > 3); //false 
boolean anyMatch = list.stream().anyMatch(e -> e > 3); //true 
boolean noneMatch = list.stream().noneMatch(e -> e > 10); //true
```

**Min, Max**

```java
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5); 
Integer max = list.stream().max((a, b) -> a.compareTo(b)).get(); //5 
Integer min = list.stream().min(Integer::compareTo).get(); //1
```

### 并行流

- 串行流: 单线程
- 并行流: 多线程 (内部实现的ForkJoin框架), 将流分成多个子任务, 并在多个线程上并行执行

Example:

```java
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);

int sumOfSquares = list.parallelStream().map(i -> i * i).sum();
System.out.println("Sum of squares: " + sumOfSquares);
```

## 2.4 反射

反射是一种在运行时可以检视自身程序和操纵程序内部属性的一种语言特性

比如对于Java而言，反射可以使其运行时动态的加载类并获取类的详细信息，从而可以操作类和对象的属性和方法

```java
import java.lang.reflect.*;

public class DumpMethods{
	public staticvoid main(String args[]){
		try{
			Class c = Class.fotName("java.util.stack");
			Method m[] = c.getDeclaredMethods(); 
			for (int i = 0; i < m.length; i++) {
				System.out.println(m[i].toString());
			}
		}	
		catch(Throwable e){
			System.err.println(e);
		}
	}
}
```

反射有什么用:

- 反射是一种“动态”特性，其使的我们无需在编译阶段知道类的信 息，也可以在运行时加载类、调用这个类的方法、甚至修改和访问 类的字段 
- 这是实现Java很多框架和库的基础 
	- 反射是许多框架和库（如 Spring、Hibernate、JUnit）的基础
-  一个例子：你如何测试大家写的代码？ 没有反射。你需要提前知道大家写的函数，然后“显式”的调用他们，但是有了反射就轻松多了

## 2.5 注解

- 放在Java源码的类、方法、字段、参数 前的一种特殊“注释”
- 可以被编译器打 包进入class文件，因此，注解是一种用作标注的“元数据”
- Java标注可以通过反射来获取标注的内容

一些内部注解:

- `@Override` : 标记方法为重写父类方法, 编译器要对重写方法进行检查
- `@SupppressWarning` : 抑制编译器警告
- `@Deprecated` : 标记果实的方法, 类, 编译器发出警告
- `@Retention` : 元注解，可以用来自定义其他注解

## \*利用反射实现一个简易测试框架

定义两个注解: `@Test` 和 `@BeforeEach.@Tese` 用于标记测试方 法，`@BeforeEach` 用于标记在每个测试方法执行前运行的方法

```java
import java.lang.annotation.Retention; 
import java.lang.annotation.RetentionPolicy; 

@Retention(RetentionPolicy.RUNTIME) 
public @interface Test { } 

@Retention(RetentionPolicy.RUNTIME) 
public @interface BeforeEach { }
```

创建待测类, 用之前自定义的注解来标记你想要测的类

```java
public class MyTest{
	@BeforeEach
	public void setup(){
		...;
	}

	@Test
	public void test1(){
		...
	}
}
```

利用反射动态扫描测试类, 然后找到标记了 `@Test` 和`@BeforeEach` 的方法, 然后执行

```java
Class<?> testClass = Class.forName("MyTests");

Object testInstance = testClass.gettDeclaredConstructor().newInstance();

Method[] methods = testClass.getDeclaredMethods(); 
Method beforeEachMethod = null;
```

找到 `BeforeEach` 方法:

```java
for(var method: methods){
	if((method.isAnnotationPresent(BeforeEach.class)){
		beforeEachMethod = method; 
		break;
	}
}
```

运行标记了 `@Test` 的方法

```java
for(var method: methods){
	if((method.isAnnotationPresent(BeforeEach.class)){
		if(beforeEachMethod != null){
			beforeEachMethod.invoke(testInstance);
		}
		method.invoke(testInstance);
	}
}
```

# 常见Java库

![[../../00 Resource/res/Pasted image 20240520114026.png]]


#程序设计 #Java #基础教程 

> [!Notes]  Why I learn Java
> 软件工程与计算 I 会讲Java (我才知道), 所以需要学
> 本来不太想学来着 ()
> 感觉这语言就业挺卷的, 但其实也需要学习他的思想 (OOP && save)

A C-like language

# Introduction. Hello World

Java : OOP. Everything in Java are part of so-called "Java classes"

```java
public class Main{  
    public static void main(String [] args){  
        System.out.println("Hello World!\n");  
    }  
}
```

- `public` means any other class can access it
- when declaring a public class, we must declare it again with the same name in Main.java, or it will throw an error
- `static` means you can run the method without creating an instance of class `Main`
- `println()` can be used to print a line. Auto changes your line


# 1. Variables and types

All primitives in Java:

- `byte` (number, 1 byte)
- `short` (number, 2 bytes)
- `int` (number, 4 bytes)
- `long` (number, 8 bytes)
- `float` (float number, 4 bytes)
- `double` (float number, 8 bytes)
- `char` (a character, 2 bytes)
- `boolean` (true or false, 1 byte)

## 1.1 Numbers && Char

The syntax of declaring a variable in Java is the same as in [[../C/CPL进阶笔记.pdf|C]]. But the limit in Java is more than in C.

If you want to use `float`, you will have to write like this:

```java
// Way 1:
float f = (float) 4.5;
// Way 2:
float f = 4.5f;
```

In Java, `char` has its own type, so you cannot put an ascii number in it. 

```java
char shit = 'g';
```

## 1.2 String

In Java, `String` is not a primitive. It's a real type, but Java has special treatment for String.

```java
String s1 = new String("Fuck you");
// No difference with the first type, so there's no need to use the first one.
String s2 = "Fuck you"; 
String s3 = s1 + s2;
```

Java **doesn't** support operatro overloading, but the string is an exception. It's the only object that can use operator `+` 

You can also link a string with a number / something else using operator `+`:

```java
int a = 10;
string s = "You have" + a + "classes to take.";
```

## 1.3 boolean

In Java, this type is called `boolean`, **not `bool`**. ~~FUCK~~

The type `boolean` only have `true` and `false` 

```java
public class Main{  
    public static void main(String [] args){  
        boolean taking_shit = true;  
        if(taking_shit){  
            System.out.println("Taking...");  
            System.out.println("Finished!");  
        }  
    }  
}
```

The following line will give an error:

```java
boolean a = true;
boolean b = false;
boolean c = a + b; // error
```


# 2. Conditionals


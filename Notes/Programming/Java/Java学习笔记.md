#程序设计 #Java #基础教程 

> From [learnjavaonline.org](https://www.learnjavaonline.org/)

> [!Notes]  Why I learn Java
> 软件工程与计算 I 会讲Java (我才知道), 所以需要学
> 本来不太想学来着 ()
> 感觉这语言就业挺卷的, 但其实也需要学习他的思想 (OOP && save)
> 
> 2024/2/28更新: 感觉其实挺有意思的, 也有助于学习OOP, 要是先学Java再学Cpp可能对OOP有更好的了解. 但现在已经学完Cpp了, 感觉Java挺简单的确实


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

> if ... else ... 

the same as C

Also have `? :`


# 3. Arrays

Not like C, we can declare an array by using `[]`:

```java
int[] arr;
```

We just define an array now. If wu want to use the array, we need to create the size:

```java
int[] arr = new int[10];
```

We can check the length of the array by using the method `length`:

```java
System.out.println(arr.length);
```

The way to set the values is the same as C

```java
int[] arr = {1, 2, 3, 4, 5};
```

# 4. Loops

The same as [[../C/CPL进阶-翁恺|C]


# 5. Functions

## 5.1 Static Method

All function definations must be in classes. We also call functions methods.

```java
public class Main {
    public static void foo() {
        // Do something here
    }
}
```

**Arguments**:

If the arguments are values, the function get the copy of the arguments; If teh arguments are objects, it get the pointer (maybe?) of the object, which means we can change the object inside the function.

## 5.2 Non Static Method

Non static method can only be run on objects and not on the whole class:

```java
// Students.java
public class Students{  
    private String name;  
    public String get_name(){  
        return name;  
    }  
    public void set_name(String name){  
        this.name = name;  
    }  
}
```

When defining a public Java class, we need to move it to a new file, which has the same name as the class

when the name of the arguments is  the same as the name of a variable in the class, we can use `this` to identify which is the one inside the class.

Use the method through object:

```java
public class Main{  
    public static void main(String [] args){  
        Students s = new Students();  
        s.set_name("Genshin");  
  
        System.out.println(s.get_name());  
    }  
}
```

## Summary

- Every Java method has to be within a class
- Static methods belong to a class while non-static methods belong to objects
- All parameters to functions are passed by value, primitives content is copied, while objects are not copied and some would say 'passed by reference'


# 6. Objects

We can define our own constructer like in [[../C++/C++学习笔记#4.2 类的构造函数和析构函数|C++]]

```java
public class Students{  
    private String name;  
  
    Students(String name){  
        this.name = name;  
    }  
}
```

When we define our own constructer, we cannot use the default constructor anymore.

But we can define more than one constructors:

```java
public class Students{  
    private String name;  
  
    Students(String name){  
        this.name = name;  
    }  
    Students(){  
        name = "Default Name";  
    }
}
```

Or we can write `Students()` like this:

```java
Students(){
	this("Default Name");
}
```

`this` can also be used to call another constructor of this class (In order to reduce code duplication). In this example, `this` calls `Students(String name)`

It can only be the first line within the constructor.

If we don't add `private` before a variable in teh class, it will be **public**.


# 7. Inheritance

Inheritance is a way to make a copy of an existing class as the starting point for another. In addition to the term subclass, inherited classes are also called `derived` classes.

**Differences between Inheritance and Interfaces**:
Interfaces define only the structure of the class members while inherited classes include the actual code of the superclass. Additionally, inheritance (more accurately, the definition of a subclass) uses the `extends` keyword in the subclass declaration.

## Override

Overriding is the process of replacing (or argumenting) the original code with new code to suit the current purpose.


## Example

`Door.java`

```java
// Door.java
import java.util.Scanner;  
  
public class Door{  
    public void open(){  
        ;  
    }  
}  
  
class BankVaultDoor extends Door{  
    private String secret_code;  
    private boolean locked;  
  
    BankVaultDoor(String secret_code){  
        this.secret_code = secret_code;  
        locked = false;  
    }  
  
    BankVaultDoor(){  
        this.secret_code = "123456";  
        locked = false;  
    }
      
    public void open(){  
        if(enter_combination()){  
            System.out.println("Opened.");  
        }else{  
            System.out.println("Fail to open.");  
        }  
    }  
  
    public void reset_secret_code(String s){  
        if(s.length() != 6){  
            System.out.println("Invalid input.");  
            return;  
        }  
        if(enter_combination()){  
            this.secret_code = s;  
            System.out.println("Successfully reset your secret code.");  
        }  
    }  
  
    private boolean enter_combination(){  
        if(locked){  
            System.out.println("Your account is locked.");  
            return false;  
        }  
        Scanner scanner = new Scanner(System.in);  
        int counter = 0;  
  
        while(counter < 6){  
            System.out.print("Please enter your secret code:");  
            String s = scanner.next();  
            if(s.length() != 6) {   // Invalid input  
                System.out.println("Invalid input. Please enter again.");  
                continue;  
            }else if(s.equals(secret_code)){ // Right secret code  
                System.out.println("Success.");  
                return true;  
            }else{ // Wrong secret code  
                counter++;  
                if(counter < 2) {  
                    System.out.println("Wrong secret code. Please try again.");  
                }else{  
                    System.out.println("Wrong secret code. You have " + (6 - counter) + " times to try.");  
                }  
            }  
        }  
  
        System.out.println("Your account is locked.");  
        locked = true;  
        return false;  
    }  
}
```

`Main.java`

```java
public class Main{  
    public static void main(String [] args){  
        Door door = new BankVaultDoor("114514");  
        door.open();  
        door.open();  
    }  
}
```

The advantage of using inheritance is writing code that applies to a number of classes that extend a more general class.


# 8. Try and Catch

A way to handle error:

```java
try {
    //Code here
  } catch (ExceptionHere name) { // name should be included. For example: (Exception e)
        //Replace ExceptionHere with your exception and name with the name of your exception.
        //Code if exception "ExceptionHere" is thrown.
  }
```


# 9. Abstract Method

An Abstract class is a class which has `abstract` keyword prefixed to it. A class must be prefixed with `abstract` if it has one or more methods with abstract keyword. An abstract method is only declared but not implemented. An abstract class cannot be instanciated but can be inherited by another class. The inheriting class must implement all the abstract methods or else the subclass should also be declared as abstract.

Example: 

```java
abstract class abstractClass { 
    abstract void abstractMethod(); 
    void concreteMethod() { // concrete methods are still allowed in abstract classes 
        System.out.println("This is a concrete method."); 
    } 
}
```

## Abstract classes versus interface

Abstract classes can contain fields which are not `final` and `static` and can contain implemented methods as well but interfaces cannot. Abstract classes with only abstract methods should be defined as interfaces.


# 10. Interfaces

A template to create a class. 

Define methods for class by specifying the method name, the return type and the argument names and types. These definations are called signatures, and the method signature contains no code.

Example:

```java
// in Pet.java
interface Pet {  
    boolean feed(String food);  
    boolean groom(int time, String way);  
    void pet(int force, String place);  
}

//in Cat.java
public class Cat implements Pet{  
    boolean alive;  
  
    Cat(){  
        this.alive = true;  
    }  
  
    public boolean feed(String food){  
        if(food.equals("Cat Food")){  
            return true;  
        }  
        return false;  
    }  
  
    public boolean groom(int time, String way){  
        if(time < 0){  
            System.out.println("Invalid time. Set to 0.");  
            return false;  
        }  
        if(time < 10){  
            System.out.println("Too short time.");  
            return false;  
        } else if(time > 60){  
            System.out.println("Too long time.");  
            this.alive = false;  
            return false;  
        } else {  
            return true;  
        }  
    }  
  
    public void pet(int force, String place){  
        if(force > 100){  
            System.out.println("Too big force.");  
            this.alive = false;  
        } else {  
            System.out.println("You pet your cat's " + place);  
        }  
    }  
}
```


# 11. Using Generics

Generics provide compile-time type safety that allows programmers to catch invalid types at compile time.

Example:

```java
List<String> names = new ArrayList<>();
```

You can also use generics in classes:

```java
public class YourClass<Class1,Class2>{
    private Class1 bob1;
    private Class2 bob2;
    Abc(Class1 a, Class2 b){
        this.bob1 = a;
        this.bob2 = b;
    }
    public Class1 getBob1() {
        return (this.bob1);
    }
    public Class2 getBob2() {
        return (this.bob2);
    }
}
```


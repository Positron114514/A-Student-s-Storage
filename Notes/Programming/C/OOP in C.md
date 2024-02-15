#C #程序设计 #OOP
# 前言

这几天正在学习[[../C++/C++学习笔记|C++]], 学习了[[../Miscell/面向对象编程思想|面向对象编程]], 突然想到前几天写的期末项目, 自己就已经潜移默化的运用了面向对象的思想. 面向对象本身有着诸多优点

![[../Miscell/面向对象编程思想#优点|诸多优点]]

使其非常适合大型程序的开发. 于是就开始思考, 是不是在[[CPL进阶-翁恺|C语言]]中也能实现面向对象编程. C语言本身不支持面向对象, 这给实现过程带来了很多的麻烦, 但好在也能通过一些方法造出一个面向对象来. 


# 类和对象

## 1. 创建类

C++中, 类通过class来实现. 在C语言中, 可以通过结构体模拟class来实现类的包装, 我们以实现一个栈为例:

```c
typedef struct {
    int* content;
    int top;
    int size;
} Stack;
```

此时Stack结构体中包含了栈相关的数据

关于类方法, 由于结构体中不能放置函数, 因此首先想到的是函数指针, 以pop为例:

```c
void pop();

typedef struct{
	int *content;
	int top;
	int size;
	(void) *pop();
} Stack;
```

聪明的你应该发现了问题: pop() 函数的实现需要知道Stack中的`content`及`top`数据, 而直接通过`.`调用函数指针时, 函数无法知道是哪个Stack类型的变量调用了他, 因此必须通过参数的方式告知函数是哪个Stack调用了他, 因此这里不能像C++一样把方法也写在class内, 而是需要把方法写在结构体外面:

```c
typedef struct{
	int *content;
	int top;
	int size;
} Stack; 
// Stack.pop()
void stack_pop(Stack *s);
```

这里在方法名前加上`stack_`防止命名冲突. 

~~由于C语言中无法声明一个变量是private的还是public的, 因此就全靠程序员自觉了()~~

补全Stack相关的操作:

```c
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

// class Stack

// private
typedef struct {
    int* content;
    int top;
    int size;
} Stack;

// public
void stack_init(Stack* s, int size);

void stack_quit(Stack* s);

bool stack_push(Stack* s, int target);

bool stack_pop(Stack* s);

int stack_top(const Stack* s);

void stack_is_empty(const Stack* s);

void stack_is_full(const Stack* s);

// class
```

此处传入的Stack变量为指针形式, 在结构体较大时传入指针可以加快运行速度, 节省内存, 并使函数能够修改传入结构体的值.

在声明Stack类型对象后, 需要调用`stack_init`函数来初始化对象; 在对象不需要再使用时, 需要调用`stack_quit`函数来释放对象:

```c
int main(){
	Stack stack_1;
	stack_init(&stack_1, 100);
	
	...

	stack_quit(&stack_1);
}

void stack_init(Stack *s, int size){
	s -> content = (int *)malloc(sizeof(int) * size);
	s -> size = size;
	s -> top = 0;
}

void stack_quit(Stack *s){
	free(s -> content);
}
```

相较于C++, 有一个方便之处: 传入参数名不需要保证和类中变量名相同了. 这使得类中变量的命名更加自由了. 


# 继承

假设我们需要实现一个类, 能够实现栈的功能, 并记录每次更改的时间:

```c
// class timer

// private
typedef struct{
	Stack *stack;
	time_t time;
} Timer;

// public
void timer_init(Timer *t, int size);

void timer_get_time(Timer *t);

void timer_quit(Timer *t);

// class
```

可以看到, Timer类内包含Stack类的对象. 如果想要使用Stack类的方法, 只需要用`.`取出Timer中的stack, 对其使用Stack类的方法. 例如`timer_init`就可以使用`stack_init`方法来初始化其中的Stack对象:

```c
void timer_init(Timer *t, int size){
	t -> stack = (Stack *)malloc(sizeof(stack));
	stack_init(t -> stack, size);
	time = 0;
}

void timer_quit(Timer *t){
	stack_quit(t -> stack);
	free(t -> stack);
}
```

也可以使用Stack中的其他类方法:

```c
int main(){
	Timer timer_1;
	timer_init(&timer_1, 100);

	stack_push(timer_1.stack, 114514);
	timer_get_time(&timer_1);

	return 0;
}
```


# 多态

考虑Stack的另一个子类:

```c
// class

// private
typedef struct{
	Stack *stack;
	char *msg;
} Msger;

// class
```

如果想要实现一个函数, 能够打印出Msger中的msg和Timer中的time, 应该如何实现?

C语言中, 想要实现像这样的多态, 似乎只能通过声明多个函数:

```c
void timer_print(Timer *t){
	printf("Time = %ud", t -> time);
}

void msger_print(Msger *m){
	printf("Message: %s", m -> msg);
}
```

只能算勉强实现了多态. 
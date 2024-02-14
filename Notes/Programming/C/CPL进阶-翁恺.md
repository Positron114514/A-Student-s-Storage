#程序设计 #C #基础教程 



# C语言程序设计进阶笔记

​														//Created by llx11451 on 2023/11/21



>  What is 进阶 : C语言程序设计现代方法第二版第13章以后的内容 (不包括指针 字符串)
>
>  2023/11/28 注 : 妥协了 , 第四章是指针进阶



**目录 :**

[TOC]



## 1.结构类型

### 1.1 结构

#### 1.1.1 结构类型

**结构体**  **`struct`**

```c
#include <stdio.h>

struct date {
    int month;
    int date;
    int year;
};

int main() {
    struct date today;

    today.month = 11;
    today.date = 21;
    today.year = 2023;

    return 0;
}
```

- 有点像数组, 但可存储不同类型数据 

- 数组: `a[0]`     结构体: `today.month`

- 很像自己创造了一个数据类型

  

```c
struct{
    int first;
    int second;
}px,py;			//未命名的结构体
```



声明结构类型: 非实体, 赋值后为实体	



- 可以对整体赋值:

```c
px=(struct point){5,10};//px.first=5,px.second=10
px=py;//将py中每个值都赋给px
```



结构变量的名字**不是**结构变量的地址, 取地址需写`&`	

```c
scanf("%i",&px.first);
```

&优先级低于.



#### 1.1.2 结构与函数

- 结构可作为函数的参数值传入函数:

```c
int number(struct date d){
    ...
}
```

此时在函数内新建一个结构变量,并**复制**调用者的结构的值(和**变量**类似,和数组**完全不同**)

- 函数也可以返回一个结构
- 与数组完全不同

- 将结构体传入函数更好的方法是传入结构体的指针,尤其是大的结构体(avoid大量拷贝)

```c
struct date {
    int month;
    int date;
    int year;
} myday;

struct date* p = &myday;

p->month = 12;  //(*p).month=12
```

此处 `p->month` 为 "p所指的month" 



#### 1.1.3 结构中的结构

- 结构数组

```c
struct date dates[100];
struct date dates[] = {
    {4,5,2005},{2,4,2005}
};//dates[0] = {4,5,2005}; dates[1] = {2,4,2005}
```



- 结构中的结构

```c
struct point{
    int x;
    int y;
}pt1,pt2;

struct rectangle{
    struct point pt1;
    struct point pt2;
};
```



- 以下几种形式等价:

```c
struct rec r,*rp;
rp = &r;
//以下等价:
r.pt1.x;
rp->pt1.x;
(r.pt1).x;
(rp->pt1).x;

//but rp->pt1->x doesn't exist(no point)
```



- 可以套娃



### 1.2 联合

#### 1.2.1 类型定义

```c
typedef long int_64;//int_64等于long类型

typedef struct {
    int month;
    int day;
    int year;
} date;

date a = {11, 22, 2023};

typedef char* String[10];
```

- 前面的为原名称, 后面的为定义名称.



#### 1.2.2 联合

- 和 `struct` 很相似的 `union`

```c
union{
    int a;
    int c;
}st1,st2;

st1.a=0;
st1.c=0;
```

`struct` 中不同成员占用不同内存空间, `union` 中成员共同占用一份内存空间. 联合起来使用同一个空间. 

- 存储
  - 所有成员共享一个空间
  - 同一时间只有一个成员是有效的
  - union的大小是其最大的成员
- 初始化
  - 对第一个成员做初始化



```c
#include <stdio.h>

typedef union {
    int i;
    char ch[sizeof(int)];
} CHI;

int main() {
    CHI target;
    target.i = 1234;

    for (int i = 0; i < sizeof(int); i++) {
        printf("%02hhX", target.ch[i]);  // 输出两位16进制的数字，什么也别干
    }
    printf("\n");

    return 0;
}
// 输出：D2040000
// x86：小端机器，倒着存数字
// 正常结果为 00 00 04 D2
```

可得到一个整数内部存储的各个量



## 2 链表

### 2.1 可变数组

本章中将分章节完成一个在C99之前创建可变长数组的函数库

#### 2.1.1 可变数组

- C99中可以直接使用可变长数组:

```c
//C99 ONLY
int main() {
    int n;
    scanf("%d", &n);
    int a[n];
    ... 
    return 0;
}
```



- 如何在C99之前实现可变长数组 ?
- 创建一个函数库

```c
typedef struct {
    int array;
    int size;
} Array;

//创建可变长数组
Array ArrayCreate(int init_size) {
    Array a;
    a.array = (int*)malloc(sizeof(int) * init_size);
    a.size = init_size;
    return a;
}

//释放可变长数组内存空间
void ArrayFree(Array *a){
    free(a->array);
    a->size=0;
    a->array=NULL;
}
```



#### 2.1.2 可变长数组的数据访问

分装思想

```c
//分装
int ArraySize(const Array *a){
    return a->size;
}
```

- 虽然这里可以直接 print  `a->size` , 但使用这种方法便于后期维护 , 即分装思想



```c
//返回指针方便赋值
int* ArrayAt(Array* a, int index) {
    return &(a->array[index]);
}
```



- 总结

```c
#include <stdio.h>
#include <stdlib.h>

typedef struct {
    int* array;
    int size;
} Array;

Array ArrayCreate(int init_size) {
    Array a;
    a.array = (int*)malloc(sizeof(int) * init_size);
    a.size = init_size;
    return a;
}

void ArrayFree(Array* a) {
    free(a->array);
    a->size = 0;
    a->array = NULL;
}

// 分装
int ArraySize(const Array* a) {
    return a->size;
}

// 返回指针方便赋值
int* ArrayAt(Array* a, int index) {
    return &(a->array[index]);
}

int main() {
    int n;
    scanf("%d", &n);

    Array a = ArrayCreate(n);
    *ArrayAt(&a, 10) = 10;

    printf("%d", *ArrayAt(&a, 10));  // Output : 10
    ArrayFree(&a);

    return 0;
}
```

- 如果不想写 `*ArrayAt` , 可以分别将取值和赋值封装成两个函数 :

```c
int ArrayGet(const Array* a, int index) {
    return a->array[index];
}

void ArraySet(Array* a, int index, int num) {
    a->array[index] = num;
}
```



#### 2.1.3 可变长数组的自动增长

- `malloc` 出来的空间不能长大

- `malloc` 新的空间使其 "长大"

```c
void ArrayInflate(Array* a, int more_size) {
    int* p = (int*)malloc(sizeof(int) * (more_size + a->size));
    for (int i = 0; i < a->size; i++) {
        *p + i = *a->array + i;
    }
    free(*a);
    a->array = p;
    a->size += more_size;
}
```



- 更新 `ArrayAt` 函数

```c
int* ArrayAt(Array* a, int index) {
    if (index > a->size) {
        ArrayInflate(a, index - a->size + 1);
    }
    return &(a->array[index]);
}
```

- 这样做会导致每次都只涨一个 , 而每次增长都会复制一遍数组导致复杂度大大增加 , 因此可以考虑每次增长n个

```c
int* ArrayAt(Array* a, int index) {
    if (index > a->size) {
        ArrayInflate(a, (index / BLOCK_SIZE + 1) * BLOCK_SIZE - a->size);
    }
    return &(a->array[index]);
}
```



result :

```c
#define BLOCK_SIZE 20

typedef struct {
    int* array;
    int size;
} Array;

Array ArrayCreate(int init_size) {
    Array a;
    a.array = (int*)malloc(sizeof(int) * init_size);
    a.size = init_size;
    return a;
}

void ArrayFree(Array* a) {
    free(a->array);
    a->size = 0;
    a->array = NULL;
}

// 分装
int ArraySize(const Array* a) {
    return a->size;
}

int ArrayGet(const Array* a, int index) {
    return a->array[index];
}

void ArraySet(Array* a, int index, int num) {
    a->array[index] = num;
}

void ArrayInflate(Array* a, int more_size) {
    int* p = (int*)malloc(sizeof(int) * (more_size + a->size));
    for (int i = 0; i < a->size; i++) {
        *p + i = *a->array + i;
    }
    free(*a);
    a->array = p;
    a->size += more_size;
}

// 返回指针方便赋值
int* ArrayAt(Array* a, int index) {
    if (index > a->size) {
        ArrayInflate(a, (index / BLOCK_SIZE + 1) * BLOCK_SIZE - a->size);
    }
    return &(a->array[index]);
}
```



### 2.2 链表

#### 2.2.1 可变数组的缺陷

1. 数组大小增长导致拷贝过慢
2. 当数组大小过大时 , 明明能申请 , 却无法申请 (前面放不下,后面不够放) especially单片机



#### 2.2.2 链表

*head -> |数据 指针|->|数据 指针|-x

​					  节点1			 节点2

```c
typedef struct _node {
    int value;
    struct _node* next;	// 指针指向这个类型的数据
} Node;
```



- 用链表实现可变长数组

```c
#include <stdio.h>
#include <stdlib.h>

typedef struct _node {
    int value;
    struct _node* next;
} Node;

int main() {
    Node* head = NULL;
    int number;
    do {
        scanf("%d", &number);
        if (number != -1) {
            // add to linked-list
            Node* p = (Node*)malloc(sizeof(Node));
            p->value = number;
            p->next = NULL;
            // find the last
            Node* last = head;
            if (last) {
                while (last->next != NULL) {
                    last = last->next;
                }
                // attach
                last->next = p;
            } else {
                // the first one
                head = p;
            }
        }
    } while (number != -1);

    return 0;
}
```



#### 4.2.3 链表的函数

- 将上述 `do while` 循环内部封装成函数

```c
void AddElement(Node* Head, int element) {
    Node* p = (Node*)malloc(sizeof(Node));
    p->value = element;
    p->next = NULL;
    Node* last = Head;
    if (last) {
        while (last->next) {
            last = last->next;
        }
        last->next = p;
    } else {
        Head = p;
    }
}
```

此时 , 由于函数内部需要修改指针head的值 , 而修改的只是传入的head的值 , 需要改进 , 否则会出错

- Way1 : 指向指针的指针

```c
void AddElement(Node** pHead, int element) {
    Node* p = (Node*)malloc(sizeof(Node));
    p->value = element;
    p->next = NULL;
    Node* last = *pHead;
    if (last) {
        while (last->next) {
            last = last->next;
        }
        last->next = p;
    } else {
        *pHead = p;
    }
}
```

- Way2 : 创建 `struct _list`

```c
typedef struct _list {
    Node* head;
} List;

void AddElement(List* pList, int element) {
    Node* p = (Node*)malloc(sizeof(Node));
    p->value = element;
    p->next = NULL;
    Node* last = pList->head;
    if (last) {
        while (last->next) {
            last = last->next;
        }
        last->next = p;
    } else {
        pList->head = p;
    }
}
```



改进 `AddElement` 函数 :

```c
typedef struct _list {
    Node* head;
    Node* tail;
} List;

void AddElement2(List* pList, int element) {
    Node* p = (Node*)malloc(sizeof(Node));
    p->value = element;
    p->next = NULL;
    if(plist->head){
        plist->tail=p;
    }else{
        plist->head=p;
        plist->tail=p;
    }
}
```

去掉循环 , 提高效率

*不知道对错*



- 输出链表中的值

```c
void Print(List* pList) {
    Node* p;
    for (p = pList; p; p = p->next) { // 经典写法
        printf("%d ", &p->value);
    }
}
```



#### 2.2.4 链表的删除

此章节中采用改进之前[^1]的 `AddElement` 函数 :

- 删除链表中元素

```c
int Delete(List* pList, int number) {
    Node *p, *q;
    int success = 0;
    for (q = NULL, p = pList.head; p; q = p, p = p->next) {
        if (p->value == number) {
            if (q) {
                q->next = p->next;

            } else {
                pList->head = p->next;
            }
            free(p);
            success = 1;
            break;
        }
    }
    return success;
}
```

- 当有指针在等号左边时 , 检查其是否有可能等于 `NULL` , 如果有则需检查其安全性 , 对 NULL 情况做处理 . 



#### 2.2.5 链表的清除

此章节中采用改进之前的 `AddElement ` 函数 :

```c
void Clear(List* pList) {
    Node *p, *q;
    for (q = NULL, p = pList->head; p; p = q) {
        q = p->next;
        free(p);
    }
}
```

- 错误样例由于昨天敲这段的时候忘保存了 (这段也是今天现补的cmn我是啥必) 找不到了 
- 怎么错的 : 没用 `q` 存` p -> next` 就直接 `free(p)` 了 



## 3 全局变量 & 宏



### 3.1 全局变量

#### 3.1.1 What is 全局变量

- 全局变量 : 定义在函数外面的变量
- 全局变量会默认初始化为0 , 不像本地变量那样是随机值



#### 3.1.2 静态本地变量

在本地变量前加 `static` 使其成为静态本地变量 :

```c
int main(){
    static int a = 0;
    ...
    return 0;
}
```



- 静态本地变量会在函数退出后仍保留函数退出时的值
- 静态本地变量只会在第一次声明时做初始化 , 之后再次经过声明语句时不会初始化值

```c
#include <stdio.h>

void f();

int main() {
    f();
    f(); // 这次调用时 time 不会再被赋予初始值
    f();
    return 0;
}

void f() {
    static int time = 1;
    printf("Your Genshin has launched for %d times.\n", time);
    time *= 2;
}

/****************************************
 *OUTOUT:								*
 *Your Genshin has launched for 1 times.*
 *Your Genshin has launched for 2 times.*
 *Your Genshin has launched for 4 times.*
 ****************************************/
```



- 静态本地变量实际上是特殊的全局变量
- 其存储位置和全局变量相同 : 

```c
#include <stdio.h>

int gAll = 0;

int main() {
    int a = 0, b = 0;
    static int x = 0;
    printf("&gAll = %p , &x = %p\n", &gAll, &x);
    printf("&a = %p , &b = %p\n", &a, &b);
    return 0;
}

/****************************************************
 * OUTPUT:                                          *
 * &gAll = 00007FF7799890A0 , &x = 00007FF7799890A4 *
 * &a = 000000634A3FFABC , &b = 000000634A3FFAB8    *
 ****************************************************/
// 可见gAll和x是挨着存储的 , 即便中间声明了a和b
```

- 生存期 = 全局变量 , 作用域 = 本地变量 
- `static` 的意思是局部作用域 (局部可访问)



#### 3.1.3 全局变量小贴士

- 不要返回本地变量的指针

  - 返回本地变量的指针很危险 , 因为脱离其作用域后其位置可能会存储别的东西

  - 返回全局变量 / 静态本地变量的地址是安全的

  - 返回在函数内 malloc 的内存是安全的 , 但是容易造成问题    *~~(翁恺 : 不展开讲了)~~*

  - 最好返回传入的指针 

- 不要使用全局变量在函数间传递参数和结果 直接传不香吗

- C语言少使用全局变量



### 3.2 程序结构

#### 3.2.1 宏定义

- `#` 开头的的是编译预处理指令 , 比如 `#include`

- `#define` : 宏定义
- 在编译前会做编译预处理
- .c -> .i -> .s -> .o -> a.out

一直在用 , 所以不用举例子了吧 ()

- 宏定义的名字必须是单词
- 如果一个宏的名字中有其他宏的名字 , 也会被替换
- 后边可以跟注释

```c
#include <stdio.h>

#define GENSHIN                     \
    printf("Genshin , LAUNCH !\n"); \
    printf("Genshin , STOP !\n")  // 万元神玩的

int main() {
    GENSHIN;
    return 0;
}
```



- 预定义的宏:

```c
#include <stdio.h>

int main() {
    printf("%s : %d\n", __FILE__, __LINE__);
    printf("%s , %s\n", __DATE__, __TIME__);
    return 0;
}
```

输出 : 

Gnshin.c : 4
Nov 25 2023 , 11:55:37



#### 3.2.2 带参数的宏

像函数的宏 :

```c
#define cube(x) ((x)*(x)*(x))
```



- <u>**一定要加括号!!!**</u>

```c
#define max(x,y) (x) > (y) ? (x) : (y)	//OK
// 给所有变量和语句都加上括号,防止出现运算顺序问题
```

- 最好后边别加 `;`

- 这种宏运算速度快于函数



- `#` 运算符 和 `##` 运算符   *~~翁恺 : 我们这里就不讲了~~*

我教材上都有怎么可能不敲出来啊 ()

~~好吧没带教材 , 懒得下电子版了 , 明天敲吧~~

blablabla

```c
bla
    
    
   
    
```



- 宏没有类型检查

- 部分宏会被 `inline` 函数代替



### 3.3 大程序

#### 3.3.1 多个源代码文件

- 多个 `.c` 文件
  - `main` 中的代码太长 -> 函数
  - 一个源代码太长 -> 分成几个文件
- 项目: 放到一起 (CMakeList)  编译后链接到一起
- 有的IDE有分开的编译和构建 ; 前者只针对单个源码文件 , 后者对整个项目做链接



#### 3.3.2 头文件

~~我自己已经在用了所以...~~

- 把函数原型放到头文件中 ( `.h` 结尾) , 需要用时 `#include` 这个头文件 , 就能让编译器在百衲衣的时候知道这个函数的原型

```c
// 文件名：function.h

int max(int a,int b);
int min(int a,int b);
char* AddBigNums(char *a,char*b);
void swap(int* pa, int* pb);
...
    
// 即一些头文件告诉编译器函数是什么样子的
```

调用 :

```c
#include <stdio.h>
#include "function.h"

int main(){
    ...
    return 0;
}
```



- 其实可以直接把函数写到 `.h` 文件里 , for example 我自己写的函数库 ~~好吧事实证明这么写不好~~
- `#include` 把文件的全部内容原封不动的插入它所在的地方
- `.i` 文件中 `#` 是注释
- `" "` 要求编译器先在当前目录中找这个文件 , 如果没有 , 到编译器指定的目录去找
- `< >` 要求编译器只在指定的目录去寻找
- 环境变量和编译器命令行参数也可以指定寻找头文件的目录



- **`#include` 并非引入一个库 **
- `stdio.h` 里面只有 `printf` 等等的**函数原型, 没有源代码**
- 源代码在另外的地方 , 某个 `.lib` 里面
- 现在的C语言编译器会默认**引入所有的标准库** , `#include <stdio.h>` 只是为了让编译器知道函数原型是什么 , 保证你调用的时候用的是正确的类型
- 自己写多个 `.c` 时最好一个 `.c` 对应一个 `.h` , 把所有对外公开的函数原型和全局变量的声明都放进去
- 不对外公开的函数 : 即只有该编译单元能用的函数 : 在函数前面加 `static` 
- 在全局变量前加 `static` 会使其成为只能在该编译单元中用的全局变量



#### 3.3.3 声明 

- 全局变量的声明

```c
extern int Genshin;	//变量声明
...
int Genshin = 0;	//变量定义
```

- 在 `.h` 中加入全局变量的声明 , 使 main 也能用



- 声明 : 不产生代码的东西 : 编译器看到了并不会产生代码 , 只会默默的记下来

  - 函数原型
  - 结构声明
  - 宏
  - 变量声明
  - 枚举声明
  - 类型声明
  - `inline` 函数

  - ...

- 定义 : 产生代码的东西

- 小规则 : 只有声明可以被放在头文件中
  - rules ≠ laws
  - 否则会造成一个项目中多个编译单元里有重名的实体 ~~(我不写 .c 只写 .h 不就行了吗)~~

- so we get "标准头文件结构"

```c
#ifndef _BASIC_H_
#include "basic.h"
#define _BASIC_H_
#endif
// 防止重复定义
```



## 4 指针进阶

本来这个笔记不想写指针的 , 最后还是妥协了 

复习一下指针趴~~~



### 4.1 blablabla



### 4.2 函数指针及其应用

#### 4.2.1 函数指针

- printf 函数名 会发生什么 ? 

```c
#include <stdio.h>

int f() {
    return 1;
}

int main() {
    int a = 0;
    printf("pmain = %p\npf    = %p\npa    = %p", main, f, &a);
    return 0;
}

/****************************************************
 * OUTPUT:                                          *
 * pmain = 00007FF6975517DB                         * 
 * pf    = 00007FF6975517D0                         *
 * pa    = 000000FB599FF91C                         *
 ****************************************************/
```



- C语言中每个函数都有一个地址

- 指向函数的指针 

```c
void f(){
    
}

void (*pf)() = f;	// 指向函数的指针
```

格式 : `返回值类型 (变量名)(传入值类型) = 函数名` 

调用 : 

```c
f();
(*pf)();	// 通过指针调用函数
```



#### 4.2.2 函数指针的应用

- 根据参数值调用函数 : 

```c
// Way 1: Switch Case
void f(int i){
    printf("f(&d)\n",i);
}
void g(int i){
    printf("g(&d)\n",i);
}
...
    
int main(){
    int i;
    scanf("%d", &i);
    switch(i){
        case 1:	f(i);	break;
        case 2:	g(i);	break;
        default:	    break;
    }
    return 0;
}
```

```c
// A better way : 函数指针
// 当加入新函数时不需要再改变 switch case 的值

#include <stdio.h>

void f(int i) {
    printf("f(&d)\n", i);
}
void g(int i) {
    printf("g(&d)\n", i);
}
void h(int i) {
    printf("h(&d)\n", i);
}

int main() {
    int i;
    scanf("%d", &i);
    
    void (*fa[])(int) = {f, g, h};	// 函数指针数组

    if (i >= 0 && i < sizeof(fa) / sizeof(fa[0])) {
        fa[i](i);
    }

    return 0;
}
```



- 执行不同函数的结果

```c
#include <stdio.h>

double plus(double a, double b) {
    return a + b;
}

double minus(double a, double b) {
    return a - b;
}

double multiply(double a, double b) {
    return a * b;
}

void cal(double (*f)(double, double), double a, double b) {
    printf("%lf\n", (*f)(a, b));
}

int main() {
    double a, b;
    scanf("%lf%lf", &a, &b);

    cal(plus, a, b);
    cal(minus, a, b);
    cal(multiply, a, b);
    
    return 0;
}
```







[^1]:即结构 _List 没有加入 *tail 时


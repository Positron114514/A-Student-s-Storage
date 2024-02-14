# C++学习笔记

> created by lxx11451 on 2024/2/1

[TOC]

## 一. C++ 基础速通

```python
#include <iostream>

using namespace std;

int main(){
    cout << "Hello World" << endl;
    system("pause");
    return 0;
}
```

### 1. 数据类型

- `int`类型: 
  - `short`: 32768
- 浮点数:
  - `float`: 4字节
  - `double`: 8字节
  - 默认情况下输出小数显示六位有效数字



## 二. 函数



### 1. 内联函数

- 在函数前加 `lnline`
- 内联函数直接在程序相应位置将函数替换成代码, 减小了普通函数在调用过程中从main到函数再返回main过程所消耗的时间, 加速, 但会占用更多内存

```c++
#include <iostream>

using namespace std;
// 一般将内联函数的定义放在正常应该防函数头的位置, 即省略函数声明()
inline double max(double x, double y){
    return x > y ? x : y;
}

int main(){
    int x, y;
    cin  >> x >> y;
    
    cout << max(x, y);
    return 0;
}
```

- 内联函数不能递归
- 若函数过大则编译器可能不会同意将其设为内联函数
- 与宏定义的区别: 仍为参数传递, 不会改变传入参数在函数外部对应的值

- so 最好用inline替换一些宏

- <u>内联函数在**每个**使用其的文件中**都**需要**被定义一次**</u>



### 2. 引用变量

引用: 已经定义的变量的别名

#### 2.1 创建

```c++
int number = 0;
int & num = number;
// num为引用变量
```

- `int &`: 指向int的引用

- `&` 不是地址运算符! ! !
- 引用类似于const指针:

```c++ 
int & num_1 = number;
int *const num_2 = number;
// 调用num_1相当于(*num_2)
```



#### 2.2 将引用用作函数参数

> 按引用传递

```cpp
void swap(int & a, int & b){
    int temp = a;
    a = b;
    b = temp;
}
```

- in C:

```c
void swap(int *a, int *b){
    int temp = *a;
    *a = *b;
    *b = temp;
}
```

- 如果不想修改传入的原变量, 则最好使用按值传递
- 引用传递本质也是指针(?), 所以在传入较大参数时比较好(如结构)
- 不能将表达式传给引用变量(老版本编译器可能可以)

##### 2.2.1 临时变量

- 若引用参数是const, 则:
  - 实参类型正确, 但不是左值
  - 实参类型不正确, 但能被转换陈正确的类型

​		时会创建临时变量. 

- 若在函数内部改变了引用变量的值, 不会创建临时变量, 编译器会报错(or只给出警告信息)

- 如果不改变值最好用const

##### 2.2.2 右值引用:

```cpp
double && ans = pow(10, 2);      // ok
double && result = ans * 10 + 2; // ok

ans += 10;
```



#### 2.3 对象, 继承和引用

- 子类继承父类方法
- 函数如果以父类为参数, 则子类也可作为其参数

example:

```cpp
#include <cstdlib>
#include <fstream>
#include <iostream>

using namespace std;

void write(ostream& os, const char* content);

int main() {
    ofstream file;
    const char* path = "./file_1.txt";
    file.open(path);

    char genshin[20]{};

    cout << "Enter your string.\n";
    cin.getline(genshin, 20);

    write(cout, genshin);
    write(file, genshin);
    return 0;
}

void write(ostream& os, const char* content) {
    ios_base::fmtflags initial;
    initial = os.setf(ios_base::fixed);
    os.precision(0);
    os << content;
}
```



- ostream中的格式化方法:

- setf: 设置格式

  - ```cpp
    std::cout.setf(std::ios::fixed); // 将浮点数显示为固定小数点格式
    std::cout.setf(std::ios::showpoint); // 显示浮点数的小数点和尾随零
    std::cout.setf(std::ios::hex, std::ios::basefield); // 以十六进制输出整数
    std::cout.setf(std::ios::uppercase); // 使用大写字母显示十六进制数
    std::cout.setf(std::ios::scientific, std::ios::floatfield); // 科学计数法输出浮点数
    
    // 返回调用其之前所有有效的格式化设置
    ```

- precision: 设置显示几位小数

- width: 显示下一个字符所用的宽度, 只对下一个字符生效

​	

### 3. 默认参数

和python的一样

- 只需要在函数声明中给默认参数赋值即可, 函数定义中**不能**再写一遍(会Compile Error)



### 4. 函数重载

- 不同参数列表的同名函数



### 5. 函数模板

#### 5.1 What is 函数模板

```cpp
#include <cstdlib>
#include <fstream>
#include <iostream>

using namespace std;

template <typename Ty>
void Swap(Ty& a, Ty& b);

int main() {
    int a, b;
    cin >> a >> b;
    Swap(a, b);
    cout << "a = " << a << ", b = " << b << endl;
    return 0;
}

template <typename Ty>
void Swap(Ty& a, Ty& b) {
    Ty t = a;
    a = b;
    b = t;
}
```

- 编译器在检测到调用模板时会自动生成对应函数



#### 5.2 显示具体化

```cpp
using namespace std;

struct job {
    string name;
    int salary;
};

template <typename Ty>
void Swap(Ty& a, Ty& b) {
    Ty t = a;
    a = b;
    b = t;
}

template <> void Swap<job>(job &a, job &b){
    int salary = a.salary;
    a.salary = b.salary;
    b.salary = t;
}

// or: template <> void Swap(job &a, job &b);
```

- 优先级: 具体化优于常规优于显式具体化
- 显示实例化: 

```cpp
template void Swap<int>(int, int);
// 编译器会生成一个Swap模板的int版本
```

- 代码中强制生成:

```cpp
template <typename T>
T Max(T a, T b){
    return a > b ? a : b;
}

...

int main(){
    double x = 10.5;
    int y = 2;
    cout << Max<double>(x, y); // <- here
    return 0;
}
```



#### * C++对函数的选择

从高到低:

- 完全匹配，但常规函数优先于模板
- 提升转换（例如，char和shorts自动转换为int，float自动转换为 double）
- 标准转换（例如，int转换为char，long转换为double) 
- 用户定义的转换，如类声明中定义的转换

如果有很多个**完全匹配**的, 则使用**最佳匹配**的 (即所需的转换**最少**的)



#### 5.3 More 模板函数知识

- `decltype`

```cpp
template <typename T1, typename T2>
void func(T1 a, T2 b){
    ...
    decltype(a + b) ans = a + b; // make ans the same type as (a + b)
    ...
}
```



```cpp
decltype((xx)) ans = xx; // make xx the type of &type(xx)
```

- `auto`: 用于返回值类型未知

```cpp
auto shit(T1 x, T2 y){
    ...
    return x + y;
}
```



## 三. 内存模型和名称空间

- 三块内存空间, 用于存储静态变量, 自动变量(栈), 动态存储(堆) 

### 1. 作用域

- 代码块内的重名变量会隐藏代码块外的同名变量



### 2. 存储说明符

- register 
- static 
- extern 
- thread_local (C++11新增的) (存储期限和线程持续性相同)
- mutable: 即使结构为const, 其某个成员也可以被修改

- `thread_local`可以和其他说明符组合使用



### 3. cv限定符

- const
- volatile: 即使程序没对变量所在内存做修改, 变量的值也会发生变化, 并且使编译器在多次调用某个变量时不自动将其存储在寄存器中.

> 用const修饰全局变量时, 其会被设置为static的, 这样可以避免多次引入同一头文件导致的multi defination



### 4. 函数间的链接

- C 和 C++ 的链接器工作结果不同, 可在函数声明中指出用哪种方法进行链接

```cpp
extern "C" void function(int a);
extern "C++" void function_1(int a);
```



### 5. 动态存储空间

new

```cpp
int *genshin = new int (6); // *genshin = 6 now
doubel *shit = new double(99.999); // *shit = 99.999 now


struct Coord{
    int x;
    int y;
    int z;
};

Coord *coord = new Coord {10, 20, 30}; // *coord = {10, 20, 30} now
```

- defination of operator `new` :

```cpp
// 运算符重载语法

void *operator new(std::size_t);
void *operator new[](std::size_t);

void *operator delete(void *);
void *operator delete[](void *);


int a = new int;
// the same as:
int a = new(sizeof(int));
```



- 定位new运算符:

```cpp
#include <iostream>
#include <new>

using namespace std;

struct genshin{
    char target;
    int num;
};

char buffer_1[100];
char buffer_2[200];

int main(){
    genshin *p1, *p2;
    *p1 = new (buffer_1) genshin;
    *p2 = new (buffer_2) genshin;
    
    ...
    
    return 0;
}
```

```cpp
delete p1;  // error: cannot delete pointers created by 定位new
```



### 6. namespace

```cpp
namespace genshin{
    char name[20];
    int size;
    int yuanshi;
    struct game{...};
}
```

- global namespace: 全局名称空间
- 向名称空间中添加内容:

```cpp
namespace genshin{
    int time;
} 
// add time into namespace genshin
```

- function:

```cpp
// declare the function
namespace genshin{
    void launch();
}

// define the function
namespace genshin{
    void launch(){
        genshin::time --;
        if(genshin::time != 0){
            launch();
        }
    }
}
```

- 作用域解析:

```cpp
genshin::time = 10;
genshin::launch();
```



- using指令:

```cpp
// using声明
// 将using添加到所属区域的名称空间中
using genshin::launch;
// using编译指令
using namespace genshin
```



- 嵌套式名称空间:

```cpp
namespace genshin{
    namespace file{
        int flame;
        ...;
        
    }
    int launch_time;
}

using genshin::file::flame;

using namespace genshin::file
```



- 没有名字的名称空间:

```cpp
namespace{
    int genshin;
    int launch;
}

// 等价表述:

static int genshin;
static int launch;
```



namespace 使用规范:
- 使用在已命名的名称空间中声明的变量，而不是使用外部全局变量;
- 使用在已命名的名称空间中声明的变量，而不是使用静态全局变量;
-  如果开发了一个函数库或类库，将其放在一个名称空间中;
- C++当前提倡将标准函数库放在名称空间std中;
- 仅将编译指令using作为一种将旧代码转换为使用名称空间的权宜之计;
- 不要在头文件中使用using编译指令; 
- 导入名称时，首选使用作用域解析运算符或using声明的方法; 
- 对于using声明，首选将其作用域设置为局部而不是全局。



## 四. 对象和类

class

### 4.1 抽象和类

#### 4.1.1 类规范

类规范由两个部分组成：

- 类声明: 数据成员 + 成员函数, 放在头文件中
- 类方法定义: 描述如何实现类成员函数, 放在源代码文件中

类声明:

```cpp
class Stock {
   private: // 可以省略, C++默认class中的对象是private的
    std::string name;
    long shares;
    double share_val;
    double total_val;
    void set_total() { total_val = share_val * shares; } // 在类声明中定义的函数将成为inline函数

   public:
    void acquire(const std::string& co, long n, double pr);
    void buy(long num, double price);
    void sell(long num, double price);
    void update(double price);
    void show();
};
```



#### 4.1.2 实现类成员函数

- 使用 `::`来标识函数所属的类
- 类方法可以访问private widget

example:

```cpp
void Stock::show(){
    ...
}
// show具有类作用域
```

- 命令cout不显示科学计数法:

```cpp
std::cout.setf(std::ios_base::fixed, std::ios_base::floatfield);
```

example:

```cpp
void Stock::acquire(const std::string& co, long n, double pr) {
    name = co;
    shares = n;
    share_val = pr;
    set_total();
}

void Stock::show(int num) {
    using std::cout;
    using std::endl;
    using std::ios_base;

    ios_base::fmtflags orig = cout.setf(ios_base::fixed, ios_base::floatfield);
    std::streamsize prec = std::cout.precision(num);
    cout << "Name: " << name << endl;
    cout << " Shares: " << shares << endl;
    cout << " Share value: " << share_val << endl;
    cout << " Total value: " << total_val << endl;

    std::cout.precision(prec);
    cout.setf(orig, ios_base::floatfield);
}
```



### 4.2 类的构造函数和析构函数

**构造函数:** 

- 与类名称相同

- 构造函数无返回类型

example:

```cpp
#include <iostream>
#include <string>

class Stock {
   private:
    std::string name_;
    long shares_;
    double share_val_;
    double total_val_;
    void set_total() { total_val_ = share_val_ * shares_; }

   public:
    Stock(const std::string& name = "Default", long shares = 0, double share_value = 0); // 构造函数
    void buy(long num, double price);
    void sell(long num, double price);
    void update(double price);
    void show(int num = 5);
};

int main() {
    Stock shit("Genshin", 114514, 111.4514);
    shit.show();
    return 0;
}

Stock::Stock(const std::string& co, long n, double pr) { // 定义, 没有返回类型
    name_ = co;
    shares_ = n;
    share_val_ = pr;
    set_total();
}

void Stock::show(int num) {
    using std::cout;
    using std::endl;
    using std::ios_base;

    ios_base::fmtflags orig = cout.setf(ios_base::fixed, ios_base::floatfield);
    std::streamsize prec = std::cout.precision(num);
    cout << "Name: " << name_ << endl;
    cout << " Shares: " << shares_ << endl;
    cout << " Share value: " << share_val_ << endl;
    cout << " Total value: " << total_val_ << endl;

    std::cout.precision(prec);
    cout.setf(orig, ios_base::floatfield);
}
```

- 调用: 

```cpp
Stock shit(a, b, c);
// or
Stock shit = Stock(a, b, c);
// or 隐式调用
Stock shit;
// or 列表初始化
Stock shit {a, b, c};
Stock shit = {a, b, c};
```

- 若只有一个成员, 则可以用赋值运算符初始化

```cpp
class single{
    private:
    	int _number;
    public:
    	single(int number);
}

single shit = 10;
```

- 需有默认构造函数:

```cpp
// W1:
Stock::Stock(a = a_default, b = b_default, c = c_default);
// W2:
Stock::Stock(a, b, c);
Stock::Stock();  // 重载
```



**析构函数:**

变量脱离作用域后自动调用

~[class_name]

```cpp
Stock::~Stock(){
    ...
}
```

- 若构造函数使用了new, 则必须提供使用了delete的析构函数



**const成员函数:**

函数不改变class中参数的值, 则将其声明为const:

```cpp
void genshin() const;

void genshin_class::genshin() const{
    ...
}
```



### 4.3 this指针

this指针指向class本身的地址

```cpp
const Stock& top_val(const Stock& s) const;

const Stock& Stock::top_val(const Stock& s) const {
    if (s.total_val_ > total_val_) {
        return s;
    }
    return *this;
}
```


### 4.4 类作用域

外界调用共有成员函数, 必须通过对象调用
类内部调用时, 可以不加修饰直接调用

#### 4.4.1 作用域为类的常量

有时类的内部需要一些常量(如MAX_NUM = 100), 然而在类声明时不会给变量分配内存, 因此如下的写法不可行:

```c++
class Genshin{
	private:
		const int months = 12;
		double costs[months];  // false
		...
}
```


正确的实现方法:

- W1: 创建枚举常量

```cpp
class Genshin{
	private:
		enum {Months = 12};  // 声明枚举类型
		double costs[Months];
}
```

`ios_base` 中用了类似方法, 如`ios_base::fixed` 

- W2: `static`

```cpp
class Genshin{
	private:
		static const int Months = 12;
		double costs[Months];
		...
}
```

创建了一个常量, 该常量和其他static变量存储在一起, 而不是存储在对象中.

#### 4.4.2 作用域内枚举

```cpp
enum class egg {Small, Medium, Large, Jumbo};
enum class t_shirt {Small, Medium, Large, Jumbo};
```

or:

```cpp
enum struct egg {Small, Medium, Large, Jumbo};
```

use:

```cpp
egg_choice = egg::Large;
```

作用域内枚举**不能**隐式地转换为int, 但可以显式转换

C++11中枚举类型默认用int表示, 但可以自行做出选择:

```cpp
enum class :short pizza {Small, Medium, Large, XLarge};
```

### 4.5 抽象数据类型

example: 栈

```cpp
// 声明
// stack.h
#ifndef STACK_H_
#define STACK_H_

class Stack {
   private:
    int size_;
    int* item;
    int pointer;

   public:
    Stack(int size);
    bool push(int target);
    bool pop();
    int top();
    bool is_empty();
    bool is_full();
    ~Stack();
};

#endif
```

```cpp
// 定义
// stack.cpp
#include <iostream>
#include <stack.h>

Stack::Stack(int size) {
    size_ = size;
    item = new int[size];
    pointer = 0;
}

Stack::~Stack() {
    delete[] item;
}

bool Stack::is_empty() {
    if (pointer == 0) {
        return 1;
    }
    return 0;
}

bool Stack::is_full() {
    if (pointer == size_) {
        return 1;
    }
    return 0;
}

bool Stack::push(int target) {
    if (is_full()) {
        std::cout << "ERROR: Stack is full.\n";
        return false;
    }
    item[pointer++] = target;
    return true;
}

bool Stack::pop() {
    if (is_empty()) {
        std::cout << "ERROR: Stack is empty.\n";
        return false;
    }
	--pointer;
    return true;
}

int Stack::top() {
    if (is_empty()) {
        std::cout << "ERROR: Stack is empty.\n";
        return 0;
    }

    return item[pointer - 1];
}
```


## 五. 使用类

### 5.1 运算符重载

函数名: type operatorop(type a);

 a + b -> a.operator+(b)

example:
```cpp
Stack Stack::operator+(Stack& a) {
    while (!is_full() && !a.is_empty()) {
        push(a.top());
        a.pop();
    }
    return *this;
}
```


- a + b + c -> a.operator+(b.operator+(c))  **valid**

重载限制: 

1. 两个参数必须有一个是用户自己定义的类型(防止用户重载Cpp内含的运算符规则)
2. 使用运算符时不能违反原来的句法规则
3. 运算符遵循Cpp的优先级
4. 不能创建新运算符
5. 以下运算符不能重载: `sizeof`  `.` `.*` `::` `?:` `typeid` `const_cast` `dynamic_cast` `reinterpret_cast`  `static_cast`  (后四个为强制类型转换运算符) 
6. 以下四个运算符只能通过成员函数进行重载: `=` `()` `[]` `->`


### 5.2 友元

友元有一下三种: 
- 友元函数
- 友元类
- 友元成员函数

**创建友元**

将friend放在类中函数的前面:

```cpp
friend Time operator*(doublem, const Time &t);
```

> operator*() 不是成员函数, 不能用`.` 调用.
> operator*() 与成员函数的访问权限相同.

**编写函数定义**

```cpp
Time operator*(double m, const Time &t){
	Time result;
	long total_minutes = t.hours * mult * 60 + t.minutes * mult;
	result.hours = total_minutes / 60;
}
```

> 由于operator*() 不是类方法, 因此不需要写 Time::operator*()

- 用如下方法可以将该友元函数编写为非友元函数:

```cpp
Time operator*(double m, const Time &t){
	return t * m;  // use t.operator*(m)
}
```

解决了顺序问题.

**例子: 对 << 进行重载:**

对 << 进行重载, 使其能输出Stack中的所有元素

- W1: 将新函数定义添加到ostream类中
- W2: 通过Stack类声明让Time知道如何使用cout

example:

```cpp
class Stack {
   private:
    int size_;
    int* item;
    int pointer;

   public:
    Stack(int size);
    bool push(int target);
    bool pop();
    int top();
    bool is_empty();
    bool is_full();
    friend std::ostream &operator<<(std::ostream& os, Stack& target);
    Stack operator+(Stack& a);
    ~Stack();
};

int main(){
	using std::cout;
	
	Stack s{100};
	Stack t{100};
	for(int i = 1; i < 10; i++){
		s.push(i);
		t.push(i * i);
	}

	cout << s << t;
	return 0;
}

std::ostream &operator<<(std::ostream& os, Stack& target) {
    using std::endl;

    for (int i = target.pointer - 1; i >= 0; i--) {
        os << target.item[i] << endl;
    }

	return os;
}
```



## Pages that should be reread

### C++ Primer Plus

- P537
- P638
- search `constexpr` (P668)
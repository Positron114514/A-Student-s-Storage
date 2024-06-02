#CPP #python #程序设计 
# 添加python库到环境中

> 这里的 `.lib` 要根据实际安装的版本选择对应的名称
> YOUR_PYTHON_PATH: python安装路径

```cmake
include_directories(YOUR_PYTHON_PATH/include)  
link_libraries(YOUR_PYTHON_PATH/libs/YOUR_PYTHON_VERSION.lib)
```

添加头文件 `Python.h`

```cpp
#include <Python.h>
```

# Python 初始化


```cpp
#include <iostream>  
#include <Python.h>  
  
using namespace std;  
  
void initPython()  
{  
    Py_Initialize();  
  
    if(!Py_IsInitialized())  
    {  
        cout << "Fail to init python.\n";  
        return;  
    }   

    // 将当前位置添加到sys中(便于后续importMoudle)  
    // 如果python文件或 module 在项目的其他文件夹中, 需将文件夹路径添加到sys中(否则会无法导入文件)
    PyRun_SimpleString("import sys");  
    PyRun_SimpleString("sys.path.append('./')");  
    PyRun_SimpleString("sys.path.append(YOUR_PATH_HERE)")
}
```

由于后续获取 Python 文件中的函数时不会运行该文件的其他语句(包括 import), 因此需要将依赖的库一同import:

```cpp
void importPyModule(string moduleName)  
{  
    string pyRunStr = "import ";  
    pyRunStr += moduleName;  
    PyRun_SimpleString(pyRunStr.c_str());  
}  
  
void importNecessaryModule()  
{  
    // example
    importPyModule("os");   
    importPyModule("math");  
    // import module you used in .py file here
}
```

外部库需要将库文件粘贴到项目构建目录下, 库文件存储在 `.\Lib\site-packages` 中

# 引入 Python 文件

```python
int main()  
{  
    PyObject* hello = PyImport_ImportModule("hello");  
    if(!hello)  
    {  
        cout << "Fail to load 'hello.py'\n";  
        return 1;  
    }    
}
```

# 运行简单的 Python 语句

直接调用 `PyRun_SimpleString()` 函数

```cpp
PyRun_SimpleString("print('hello, world!')");
```

# 获取 Python 文件中的函数

以如下 python 文件为例:

```python
# hello.py
import math  

def my_op(*nums):
    ret = 0
    for num in nums:
        ret += math.sin(num)
    return ret

if __name__ == "__main__":
    print(my_op(1, 2, 3, 4, 5))
```

获取文件中的函数:

```cpp
PyObject* getPyFunction(PyObject* module, string name)  
{  
    PyObject* retObj = PyObject_GetAttrString(module, name.c_str());  
    if(!retObj)  
    {  
        cout << "Fail to load function: " << name << endl;  
    }  
    return retObj;  
}
```

获取变量等同理

代码如下:

```cpp
int main()  
{  
    PyObject* hello = PyImport_ImportModule("hello");  
    if(!hello)  
    {  
        cout << "Fail to load 'hello.py'\n";  
        return 1;  
    }  
  
    PyObject* myOpFromPy = getPyFunction(hello, "my_op");   
}
```

# 运行 Python 函数

- 调用无参函数: 

```cpp
PyObject* funcRet = PyObject_CallFunction(yourFunction, 0);
```

- 调用有参数函数: 
	- 声明一个新的 `PyTuple` , 参数为函数参数个数
	- 给 `PyTuple` 的每个索引 用 `PyObject` 赋值
	- 调用函数
	- 翻译返回值

```cpp
int main()  
{  
    // 初始化
    initPython();  
    importNecessaryModule();  

    // import hello 模块
    PyObject* hello = PyImport_ImportModule("hello");  
    if(!hello)  
    {  
        cout << "Fail to load 'hello.py'\n";  
        return 1;  
    }  

    // 获取 my_op 函数
    PyObject* myOpFromPy = getPyFunction(hello, "my_op");  

    double arr[10] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};  
    int len = 10; // arr.size()  
    // 声明一个新的 `PyTuple` , 参数为函数参数个数
    PyObject* args = PyTuple_New(len);  

    // 给 `PyTuple` 的每个索引 用 `PyObject` 赋值
    for(int i = 0; i < len; i++)  
    {  
        PyObject* curr = PyFloat_FromDouble(arr[i]);  
        PyTuple_SetItem(args, i, curr);  
    }  
    // 调用函数
    PyObject* funcRet = PyObject_CallObject(myOpFromPy, args);  
    // 翻译返回值
    double ans = 0;  
    PyArg_Parse(funcRet, "d", &ans);  
  
    cout << "my_op from python: output: " << ans << endl;  
    return 0;  
}
```

也可以使用可变参数写出一个调用 python 函数的函数

```cpp
PyObject* runPyFunction(PyObject* function, int argsNum, const char* format, ...)  
{  
    va_list args;  
    va_start(args, format);  
  
    PyObject* funcArgs = PyTuple_New(argsNum);  
    // current arg  
    PyObject* curr = NULL;  
  
    int counter = 0;  
    while (*format != '\0' && counter < argsNum) {  
        if (*format == '%') {  
            format++; // 移动到占位符的下一个字符  
  
            if (*format == 'd') {  
                curr = PyLong_FromLong(va_arg(args, int));  
                PyTuple_SetItem(funcArgs, counter, curr);  
            } else if (*format == 'f') {  
                curr = PyFloat_FromDouble(va_arg(args, float));  
                PyTuple_SetItem(funcArgs, counter, curr);  
            } else if (*format == 's') {  
                curr = PyUnicode_FromString(va_arg(args, char*));  
                PyTuple_SetItem(funcArgs, counter, curr);  
            }else {  
                printf("Unsupported format specifier: %c", *format);  
                return NULL;  
            }  
  
            counter++;  
        }  
        format++; // 移动到下一个字符  
    }  
  
    va_end(args);  
  
    PyObject* retObj = PyObject_CallObject(function, funcArgs);  
  
    return retObj;  
}
```

然后可以直接这样使用:

```cpp
PyObject* funcRet = runPyFunction(myOpFromPy, 4, "%d%d%d%d", 1, 2, 3, 4);  
  
double ans = 0;  
PyArg_Parse(funcRet, "d", &ans);  
  
cout << "my_op from python: output: " << ans << endl;
```
#程序设计 #python #数据分析 

> from [100天python](https://github.com/jackfrued/Python-100-Days/blob/master/Day66-80/68.NumPy%E7%9A%84%E5%BA%94%E7%94%A8-1.md)

# 1. prepare

`JupyterLab` 使用技巧

## 1.1 魔法指令

|魔法指令|功能说明|
|---|---|
|`%pwd`|查看当前工作目录|
|`%ls`|列出当前或指定文件夹下的内容|
|`%cat`|查看指定文件的内容|
|`%hist`|查看输入历史|
|`%matplotlib inline`|设置在页面中嵌入matplotlib输出的统计图表|
|`%config Inlinebackend.figure_format='svg'`|设置统计图表使用SVG格式（矢量图）|
|`%run`|运行指定的程序|
|`%load`|加载指定的文件到单元格中|
|`%quickref`|显示IPython的快速参考|
|`%timeit`|多次运行代码并统计代码执行时间|
|`%prun`|用`cProfile.run`运行代码并显示分析器的输出|
|`%who` / `%whos`|显示命名空间中的变量|
|`%xdel`|删除一个对象并清理所有对它的引用|
## 1.2 快捷键

命令模式下的快捷键：

|快捷键|功能说明|
|---|---|
|`Alt` + `Enter`|运行当前单元格并在下面插入新的单元格|
|`Shift` + `Enter`|运行当前单元格并选中下方的单元格|
|`Ctrl` + `Enter`|运行当前单元格|
|`j` / `k`、`Shift` + `j` / `Shift` + `k`|选中下方/上方单元格、连续选中下方/上方单元格|
|`a` / `b`|在下方/上方插入新的单元格|
|`c` / `x`|复制单元格 / 剪切单元格|
|`v` / `Shift` + `v`|在下方/上方粘贴单元格|
|`dd` / `z`|删除单元格 / 恢复删除的单元格|
|`Shift` + `l`|显示或隐藏当前/所有单元格行号|
|`Space` / `Shift` + `Space`|向下/向上滚动页面|


编辑模式下的快捷键：

|快捷键|功能说明|
|---|---|
|`Shift` + `Tab`|获得提示信息|
|`Ctrl` + `]`/ `Ctrl` + `[`|增加/减少缩进|
|`Alt` + `Enter`|运行当前单元格并在下面插入新的单元格|
|`Shift` + `Enter`|运行当前单元格并选中下方的单元格|
|`Ctrl` + `Enter`|运行当前单元格|
|`Ctrl` + `Left` / `Right`|光标移到行首/行尾|
|`Ctrl` + `Up` / `Down`|光标移动代码开头/结尾处|
|`Up` / `Down`|光标上移/下移一行或移到上/下一个单元格|

# 2. Numpy 篇

**用于快速处理任意维度的数组**。Numpy **支持常见的数组和矩阵操作**，对于同样的数值计算任务，使用 NumPy 不仅代码要简洁的多，而且 NumPy 在性能上也远远优于原生 Python，至少是一到两个数量级的差距，而且数据量越大，NumPy 的优势就越明显。

NumPy 最为核心的数据类型是`ndarray`，使用`ndarray`可以处理一维、二维和多维数组，该对象相当于是一个快速而灵活的大数据容器。

## 2.1 基础

### 2.1.1 创建数据对象

创建 `ndarray` 对象

**方法1**: 使用 `array` 根据列表创建

```python
array1 = np.array([1, 2, 3, 4, 5])
array1
```

**方法2**: 使用 `arange` 指定取值范围和跨度创建数组对象

```python
array3 = np.arange(0, 20, 2)
array3
```

```output
array([ 0,  2,  4,  6,  8, 10, 12, 14, 16, 18])
```

**方法3**: 使用`linspace`函数，用指定范围和元素个数创建数组对象，生成等差数列

```python
array2 = np.linespace(-1, 1, 11)
```

```output
array([-1. , -0.8, -0.6, -0.4, -0.2,  0. ,  0.2,  0.4,  0.6,  0.8,  1. ])
```

**方法4**: 使用`logspace`函数，生成等比数列

```python
array5 = np.logspace(1, 10, num=10, base=2)
array5
```

> **注意**：等比数列的起始值是$2^1$，等比数列的终止值是$2^{10}$，`num`是元素的个数，`base`就是底数。

```output
array([   2.,    4.,    8.,   16.,   32.,   64.,  128.,  256.,  512., 1024.])
```

**方法六**：通过`fromiter`函数从生成器（迭代器）中获取数据创建数组对象

```python
def fib(how_many):
    a, b = 0, 1
    for _ in range(how_many):
        a, b = b, a + b
        yield a


gen = fib(20)
array7 = np.fromiter(gen, dtype='i8')
array7
```

```output
array([   1,    1,    2,    3,    5,    8,   13,   21,   34,   55,   89,
        144,  233,  377,  610,  987, 1597, 2584, 4181, 6765])
```

**方法七**：使用`numpy.random`模块的函数生成随机数创建数组对象

产生10个$[0, 1)$范围的随机小数

```python
array8 = np.random.rand(10)
array8
```

```output
array([0.45556132, 0.67871326, 0.4552213 , 0.96671509, 0.44086463,
       0.72650875, 0.79877188, 0.12153022, 0.24762739, 0.6669852 ])
```

> 感觉这里和mxx上课讲的把 $R$ 映射到 $[0, 1)$ 搭上边了, 最后数据都要映射到 $[0, 1)$ 处理

产生10个 $[1, 100)$ 范围的随机整数

```python
array9 = np.random.randint(1, 100, 10)
array9
```

```output
array([29, 97, 87, 47, 39, 19, 71, 32, 79, 34])
```

产生20个$\small{\mu=50}$，$\small{\sigma=10}$的正态分布随机数

```python
array10 = np.random.normal(50, 10, 20)
array10
```

```output
array([55.04155586, 46.43510797, 20.28371158, 62.67884053, 61.23185964,
       38.22682148, 53.17126151, 43.54741592, 36.11268017, 40.94086676,
       63.27911699, 46.92688903, 37.1593374 , 67.06525656, 67.47269463,
       23.37925889, 31.45312239, 48.34532466, 55.09180924, 47.95702787])
```

产生$[0, 1)$范围的随机小数构成的3行4列的二维数组

```python
array11 = np.random.rand(3, 4)
array11
```

```output
array([[0.54017809, 0.46797771, 0.78291445, 0.79501326],
       [0.93973783, 0.21434806, 0.03592874, 0.88838892],
       [0.84130479, 0.3566601 , 0.99935473, 0.26353598]])
```

产生$[1, 100)$范围的随机整数构成的三维数组

```python
array12 = np.random.randint(1, 100, (3, 4, 5))
array12
```

```output
array([[[94, 26, 49, 24, 43],
        [27, 27, 33, 98, 33],
        [13, 73,  6,  1, 77],
        [54, 32, 51, 86, 59]],

       [[62, 75, 62, 29, 87],
        [90, 26,  6, 79, 41],
        [31, 15, 32, 56, 64],
        [37, 84, 61, 71, 71]],

       [[45, 24, 78, 77, 41],
        [75, 37,  4, 74, 93],
        [ 1, 36, 36, 60, 43],
        [23, 84, 44, 89, 79]]])
```

**方法八**：创建全0、全1或指定元素的数组

- 使用 `zeros` 函数

```python
array13 = np.zeros((3, 4))
array13
```

```output
array([[0., 0., 0., 0.],
       [0., 0., 0., 0.],
       [0., 0., 0., 0.]])
```

- 使用 `ones` 函数，代码：

```python
array14 = np.ones((3, 4))
array14
```

```output
array([[1., 1., 1., 1.],
       [1., 1., 1., 1.],
       [1., 1., 1., 1.]])
```

- 使用 `full` 函数，代码：

```python
array15 = np.full((3, 4), 10)
array15
```

```output
array([[10, 10, 10, 10],
       [10, 10, 10, 10],
       [10, 10, 10, 10]])
```

- 方法九：使用`eye`函数创建单位矩阵

```python
np.eye(4)
```

```output
array([[1., 0., 0., 0.],
       [0., 1., 0., 0.],
       [0., 0., 1., 0.],
       [0., 0., 0., 1.]])
```

- 方法十：读取图片获得对应的三维数组

```python
array16 = plt.imread('res/guido.jpg')
array16
```

```output
array([[[ 36,  33,  28],
        [ 36,  33,  28],
        [ 36,  33,  28],
        ...,
        [ 32,  31,  29],
        [ 32,  31,  27],
        [ 31,  32,  26]],

       [[ 37,  34,  29],
        [ 38,  35,  30],
        [ 38,  35,  30],
        ...,
        [ 31,  30,  28],
        [ 31,  30,  26],
        [ 30,  31,  25]],

       [[ 38,  35,  30],
        [ 38,  35,  30],
        [ 38,  35,  30],
        ...,
        [ 30,  29,  27],
        [ 30,  29,  25],
        [ 29,  30,  25]],

       ...,

       [[239, 178, 123],
        [237, 176, 121],
        [235, 174, 119],
        ...,
        [ 78,  68,  56],
        [ 75,  67,  54],
        [ 73,  65,  52]],

       [[238, 177, 120],
        [236, 175, 118],
        [234, 173, 116],
        ...,
        [ 82,  70,  58],
        [ 78,  68,  56],
        [ 75,  66,  51]],

       [[238, 176, 119],
        [236, 175, 118],
        [234, 173, 116],
        ...,
        [ 84,  70,  61],
        [ 81,  69,  57],
        [ 79,  67,  53]]], dtype=uint8)
```

> **说明**：上面的代码读取了当前路径下`res`目录中名为`guido.jpg` 的图片文件，计算机系统中的图片通常由若干行若干列的像素点构成，而每个像素点又是由红绿蓝三原色构成的，刚好可以用三维数组来表示。读取图片用到了`matplotlib`库的`imread`函数。


### 2.1.2 数组对象的属性

> not 函数, 是数据, 所以不加括号

- `size` : 获取元素个数 
- `shape` : 获取数组形状
- `dtype` : 元素的数据类型
- `ndim` : 数组的维度
- `itemsize` : 单个元素占据的字节数
- `nbytes` : 所有元素占据的字节数

Example: 

```python
from pyp_爬取百度图片 import get_picture  
import matplotlib.pyplot as plt  
  
  
def analyse_picture_from_baidu(picture_name="cat", number=1):  
    ret = []  
    get_picture(picture_name, number)  
  
    for i in range(1, number + 1):  
        catty_picture = plt.imread('./res/graph/' + picture_name + str(i) + '.png')  
        ret.append(catty_picture)  
  
    return ret  
  
  
def main():  
    picture = analyse_picture_from_baidu()[0]  
    print(picture.size)  
    print(picture.shape)  
    print(picture.dtype)  
    print(picture.ndim)  
    print(picture.itemsize)  
    print(picture.nbytes)  
  
  
if __name__ == '__main__':  
    main()
```

```output
Screenshot saved to: ./res/graph/cat1.png
1920000        # size
(600, 800, 4)  # shape
float32        # dtype
3              # ndim
4              # itemsize
7680000        # nbytes
```

### 2.1.3 索引

#### a) 切片索引

和[[python学习笔记#1. 列表|列表]]基本一致

![[../../00 Resource/res/ndarray-index.png]]


![[../../00 Resource/res/ndarray-slice.png]]

#### b) 花式索引

即用保存整数的一个数组充当索引

Example:

```python
# array = np.arange(1, 10)
array[[0, 1, 1, 2, -1, 0]]
```

```output
array([1, 2, 2, 3, 9, 1])
```

#### c) 布尔索引

即用 `True` `False` 表示数组中的数是否被索引

Example:

```python
array[[True, True, False, False, True, False, False, True, True]]
```

```output
array([1, 2, 5, 8, 9])
```

```python
array = np.arange(2, 11)
# > 5的元素设为真
array > 5
```

```output
array([False, False, False, False,  True,  True,  True,  True,  True])
```

可以对一个数组使用逻辑运算符, 结果为布尔数组

### Example: 处理图片

```python
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt


picture = plt.imread("./res/graph/genshin1.png")
plt.imshow(picture[::10, ::10])
```

## 2.2 数组对象的方法

### 2.2.1 获取描述统计信息

描述统计信息主要包括数据的集中趋势、离散程度和频数分析等，其中集中趋势主要看均值和中位数，离散程度可以看极值、方差、标准差等，详细的内容大家可以阅读[《统计思维系列课程01：解读数据》](https://zhuanlan.zhihu.com/p/595273755)

```python
array = np.random.randint(1, 100, 10)
```

#### 总和, 均值, 中位数

```python
array = np.random.randint(1, 100, 10)  
  
print("array = " + str(array))  
  
# 计算总和  
print(array.sum())  
# 算术平均值  
print(array.mean())  
# 中位数  
print(np.median(array))  
# 分位数  
print(np.quantile(array, 0.2)) # 20% 分位数
```

```output
array = [49 43 69  6 52 48  3 82  2 71]
425
42.5
48.5
5.4
```

#### **极值、极差 (全距) 和四分位距离**

> 四分位距: 第三四分位和第一四分位的差
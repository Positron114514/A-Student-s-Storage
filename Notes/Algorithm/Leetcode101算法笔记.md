#算法 #程序设计 

系[[../Algorithm/算法学习笔记|算法学习笔记]]实战版
# 一. 贪心算法

顾名思义，贪心算法或贪心思想采用贪心的策略，保证每次操作都是局部最优的，从而使最后得到的结果是全局最优的.

## 1. 分配问题

### T135. Candy

**题目描述:** 

一群孩子站成一排，每一个孩子有自己的评分。现在需要给这些孩子发糖果，规则是如果一 个孩子的评分比自己身旁的一个孩子要高，那么这个孩子就必须得到比身旁孩子更多的糖果；所 有孩子至少要有一个糖果。求解最少需要多少个糖果。 

**输入输出样例** 

输入是一个数组，表示孩子的评分。输出是最少糖果的数量。

C++:

```cpp
class Solution {
   public:
    int candy(vector<int>& ratings) {
        int size = ratings.size();
        if (size < 2) {
            return size;
        }

        vector<int> candys(size, 1);

        for (int i = 0; i < size - 1; i++) {
            if (ratings[i] < ratings[i + 1]) {
                candys[i + 1]++;
            }
        }

        for (int i = size - 1; i > 0; i--) {
            if (ratings[i - 1] > ratings[i]) {
                ratings[i - 1] = max(ratings[i] + 1, ratings[i]);
            }
        }

        return accumulate(candys.begin(), candys.end(), 0);
    }
};
```

C:

```c
#define MAX(a, b) (a) > (b) ? (a) : (b)

int candy(int* ratings, int ratingsSize) {
    if(ratingsSize < 2){
        return ratingsSize;
    }

    int candys[ratingsSize];

    candys[0] = 1;

    for(int i = 0; i < ratingsSize - 1; i++){
        if(ratings[i] < ratings[i + 1]){
            candys[i + 1] = candys[i] + 1;
        }else{
            candys[i + 1] = 1;
        }
    }

    int ret = candys[ratingsSize - 1];

    for(int i = ratingsSize - 1; i > 0; i--){
        if(ratings[i] < ratings[i - 1]){
            candys[i - 1] = MAX(candys[i] + 1, candys[i - 1]);
        }
        ret += candys[i - 1];
    }

    return ret;

}
```


## 2. 区间问题

### T435. Non-overlapping Intervals

**题目描述** 

给定多个区间，计算让这些区间互不重叠所需要移除区间的最少个数。起止相连不算重叠。 

**输入输出样例**

输入是一个数组，数组由多个长度固定为 2 的数组组成，表示区间的开始和结尾。输出一个 整数，表示需要移除的区间数量

C++:

```c++
class Solution {
   public:
    int eraseOverlapIntervals(vector<vector<int>>& intervals) {
        if(intervals.empty())   return 0;

        sort(intervals.begin(), intervals.end(),
             [](vector<int> &a, vector<int> &b) { return a[1] < b[1]; }); // 引用非常重要!

        int prev = intervals[0][1];
        int ret = 0;

        for (int i = 1; i < intervals.size(); i++) {
            if (prev > intervals[i][0]) {
                ret++;
            } else {
                prev = intervals[i][1];
            }
        }
        return ret;
    }
};
```

C:

```c
int cmp(const void* a, const void* b) {
	return (*(int**)a)[1] > (*(int**)b)[1];
}

int eraseOverlapIntervals(int** intervals,
                          int intervalsSize,
                          int* intervalsColSize) {
    qsort(intervals, intervalsSize, *intervalsColSize * sizeof(int), cmp);

    int* pre = intervals[0];
    int delete = 0;
    for (int i = 1; i < intervalsSize; i++) {
        if (pre[1] > intervals[i][0]) {
            delete ++;
        } else {
            pre = intervals[i];
        }
    }

    return delete;
}
```


## 练习

### T763 划分字母区间

最快解法(C++):

```cpp
class Solution {  
public:  
    // partitionLabels函数接收一个字符串s作为输入，并返回一个整数向量，表示按照特定规则分割后每个子串的长度。  
    vector<int> partitionLabels(string s) {  
        int n = s.size(); // 获取字符串s的长度  
        unordered_map<char, int> maxPos; // 创建一个无序映射，用于存储每个字符在字符串s中最后一次出现的位置  
  
        // 遍历字符串s，记录每个字符最后一次出现的位置  
        for(int i = 0; i < n; i++){  
            maxPos[s[i]] = i;  
        }  
  
        vector<int> res; // 创建一个整数向量，用于存储每个子串的长度  
        int reach = 0, start = 0; // reach表示当前子串的结束位置，start表示当前子串的起始位置  
  
        // 当start小于字符串s的长度时，继续循环  
        while(start < n){  
            // 将reach更新为从start到当前子串结束位置中，最后一次出现字符s[start]的位置  
            reach = maxPos[s[start]];  
  
            // 再次遍历从start到reach的范围，更新reach为这个范围内最后一次出现字符的位置  
            for(int i = start; i <= reach; i++){  
                reach = max(reach, maxPos[s[i]]);  
            }  
  
            // 将当前子串的长度添加到结果向量res中  
            res.emplace_back(reach - start + 1);  
  
            // 更新start为下一个子串的起始位置，即当前子串的结束位置的下一个位置  
            start = reach + 1;  
        }  
  
        return res; // 返回结果向量res  
    }  
};
```

我的解法:

```cpp
class Solution {
   public:
    vector<int> partitionLabels(string s) {
        int len = s.size();
        vector<vector<int>> a;
        int a_pointer = 0;
        bool exist[26] = {0};

        for (int i = 0; i < len; i++) {
            int num = get_num(s[i]);
            if (exist[num])
                continue;

            exist[num] = 1;
            a.push_back({2, i});
            ++a_pointer;
            for (int j = len - 1; j >= i; j--) {
                if (s[j] == s[i]) {
                    a[a_pointer][1] = j;
                    break;
                }
            }
        }

        vector<int> ret{0, 0};

        sort(a.begin(), a.end(),
             [](const vector<int>& a_, const vector<int>& b_) {
                 return a_[0] < b_[0];
             });

        int pre_left = a[0][0];
        int pre_right = a[0][1];

        for (int i = 1; i < a.size(); i++) {
            if (a[i][0] < pre_right) {
                pre_right = a[i][1];
            } else {
                ret.push_back(pre_right - pre_left + 1);
                pre_left = a[i][0];
                pre_right = a[i][1];
            }
        }

        return ret;
    }

    inline int get_num(char a) { return a - 'a'; }
};
```
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


### T406 Queue Reconstruction by Height

C++:

```cpp
class Solution {
public:
    /*
     * reconstructQueue 函数用于重建队列。
     * 输入参数：
     * - people: 一个二维向量，其中每个元素都是一个长度为2的向量，表示一个人的信息，第一个元素是身高，第二个元素是前面比他高的人数。
     * 返回值：
     * - 返回一个二维向量，表示按照题目要求排列的队列。
     */
    vector<vector<int>> reconstructQueue(vector<vector<int>>& people) {
        // 根据身高和前面比他高的人数对人员进行排序
        sort(people.begin(), people.end(), [](vector<int>& a, vector<int>& b) {
            return a[0] == b[0] ? a[1] < b[1] : a[0] > b[0];
        });

        int len = people.size(); // 人员总数

        vector<vector<int>> ret; // 用于存储重建后的队列
        for (int i = 0; i < len; i++) {
            // 在 ret 中按照每个人的前面比他高的人数找到合适的位置插入当前人员信息
            ret.insert(ret.begin() + people[i][1], people[i]);
        }

        return ret; // 返回重建的队列
    }
};

```

C:

```c
/*
 * cmp 函数是用作 qsort 函数的比较函数，用于对人员数组进行排序。
 * 这个比较函数按照两个标准来排序：
 * 1. 如果两个人的身高相同，则根据他们的位置信息（前面比他们高的人数）降序排列；
 * 2. 如果两个人的身高不同，则按照身高升序排列。
 */
int cmp(const void* _a, const void* _b) {
    int *a = *(int**)_a, *b = *(int**)_b;
    return a[0] == b[0] ? b[1] - a[1] : a[0] - b[0];
}

/*
 * reconstructQueue 函数用于重建队列。
 * 输入参数：
 * - people: 人员数组，其中每个人的信息表示为一个长度为2的数组，第一个元素是身高，第二个元素是位置信息（前面比他们高的人数）。
 * - peopleSize: 人员数组的长度。
 * - peopleColSize: 人员数组的列数，这里是固定的为2。
 * 输出参数：
 * - returnSize: 返回的二维数组的行数。
 * - returnColumnSizes: 返回的二维数组的每一行的列数。
 * 返回值：
 * - 返回一个二维数组，表示按照题目要求排列的队列。
 */
int** reconstructQueue(int** people, int peopleSize, int* peopleColSize, int* returnSize, int** returnColumnSizes) {
    // 根据身高和位置信息对人员数组进行排序
    qsort(people, peopleSize, sizeof(int*), cmp);
    // 分配返回的二维数组的空间
    int** ans = malloc(sizeof(int*) * peopleSize);
    // 设置返回的二维数组的行数
    *returnSize = peopleSize;
    // 分配并初始化返回的二维数组的每一行的列数
    *returnColumnSizes = malloc(sizeof(int) * peopleSize);
    memset(*returnColumnSizes, 0, sizeof(int) * peopleSize);
    // 遍历排序后的人员数组
    for (int i = 0; i < peopleSize; ++i) {
        int spaces = people[i][1] + 1;
        // 在返回的二维数组中找到合适的位置插入当前人员
        for (int j = 0; j < peopleSize; ++j) {
            if ((*returnColumnSizes)[j] == 0) {
                spaces--;
                if (!spaces) {
                    (*returnColumnSizes)[j] = 2;
                    ans[j] = malloc(sizeof(int) * 2);
                    ans[j][0] = people[i][0], ans[j][1] = people[i][1];
                    break;
                }
            }
        }
    }
    // 返回重建的队列
    return ans;
}
```


### T665 Non-decreasing Array 

C++

```cpp
class Solution {
public:
    /*
     * checkPossibility 函数用于检查数组是否可以通过修改一个元素变成非递减数组。
     * 输入参数：
     * - nums: 一个整数向量，表示待检查的数组。
     * 返回值：
     * - 返回一个布尔值，表示是否可以通过修改一个元素变成非递减数组。
     */
    bool checkPossibility(vector<int>& nums) {
        if(nums.size() <= 2) return true; // 如果数组长度小于等于2，则一定可以通过修改一个元素变成非递减数组
        bool changed = false; // 用于标记是否已经修改过一个元素

        for(int i = 0; i < nums.size() - 1; i++){
            if(nums[i] > nums[i + 1]){ // 如果发现递减的情况
                if(changed) return false; // 如果已经修改过一个元素，则返回 false
                changed = true; // 标记为已经修改过一个元素
                if(i == 0 || nums[i - 1] <= nums[i + 1]){ // 如果当前元素是第一个元素，或者当前元素的前一个元素小于等于后一个元素
                    nums[i] = nums[i + 1]; // 将当前元素的值修改为后一个元素的值
                }else{
                    nums[i + 1] = nums[i];  // 否则，将后一个元素的值修改为当前元素的值
                }
            }
        }

        return true; // 如果能够完成循环，则说明可以通过修改一个元素变成非递减数组，返回 true
    }
};

```


# 二. 双指针

 双指针主要用于遍历数组，两个指针指向不同的元素，从而协同完成任务。也可以延伸到多 个数组的多个指针。 
 
 - 若两个指针指向同一数组，遍历方向相同且不会相交，则也称为滑动窗口（两个指针包围的 区域即为当前的窗口），经常用于区间搜索。 
 - 若两个指针指向同一数组，但是遍历方向相反，则可以用来进行搜索，待搜索的数组往往是 排好序的


## 2.1 快慢指针

> 链表寻找环路

对于链表找环路的问题，有一个通用的解法——快慢指针（[[算法合集/Floyd判圈法|Floyd判圈法]]）。给定两个指针， 分别命名为 slow 和 fast，起始位置在链表的开头。每次 fast 前进两步，slow 前进一步。如果 fast 可以走到尽头，那么说明没有环路；如果 fast 可以无限走下去，那么说明一定有环路，且一定存 在一个时刻 slow 和 fast 相遇。当 slow 和 fast 第一次相遇时，我们将 fast 重新移动到链表开头，并 让 slow 和 fast 每次都前进一步。当 slow 和 fast 第二次相遇时，相遇的节点即为环路的开始点。

```c++
class Solution { 
	public: 
		ListNode *detectCycle(ListNode *head) { 
			ListNode *slow = head, *fast = head; 
			do{ 
				if(!fast || !fast ->next) return NULL; 
				slow = slow -> next; 
				fast = fast -> next ->next; 
			}while(slow != fast); 
			
			fast = head; 
			while(fast != slow){ 
				fast = fast ->next; 
				slow = slow -> next; 
			} 
			return fast; 
		} 
};
```


## 2.2 滑动窗口

### T76 Minimum Window Substring (Hard)

```c++
class Solution {
   public:
    string minWindow(string s, string t) {
        unordered_map<char, int> object;
        for (const char ch : t) {
            object[ch]++;
        }
        int min_len = s.size() + 1;
        int min_pointer = 0;
        int cnt = 0;
        int j = 0;
        for (int i = 0; i < s.size(); ++i) {
            if (object.find(s[i]) != object.end() && --object[s[i]] >= 0) {
                ++cnt;
            }
            while (cnt == t.size()) {
                if (i - j + 1 < min_len) {
                    min_len = i - j + 1;
                    min_pointer = j;
                }
                if (object.find(s[j]) != object.end() && ++object[s[j]] > 0) {
                    --cnt;
                }
                ++j;
            }
        }
        return min_len > s.size() ? "" : s.substr(min_pointer, min_len);
    }
};
```


## 练习

我***

![[../00 Resource/res/Pasted image 20240219224047.png]]

>[!Abstract] sb玩意


# 三. 二分查找

注意别写死循环了

写法1:

```c
while(l <= r){
	int mid = (l + r) / 2;
	if(mid < target){
		l = mid + 1;
	}else if(mid > target){
		r = mid - 1;
	}else{
		return true;
	}
}
```

## T81 旋转数组

```c++
class Solution {
   public:
    bool search(vector<int>& nums, int target) {
        int l = 0, r = nums.size() - 1;

        int mid;
        while (l <= r) {
            mid = (l + r) / 2;
            if (nums[mid] == target || nums[l] == target || nums[r] == target)
                return 1;
            else if (nums[mid] == nums[l]) {
                ++l;
            } else if (nums[mid] <= nums[r]) {  // 头等于中间, 则中间必定等于尾
                if (nums[mid] < target && nums[r] > target) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            } else {
                if (nums[mid] > target && nums[l] < target) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
        }

        return 0;
    }
};
```


## 练习

### [T540 数组中的单一元素](https://leetcode.cn/problems/single-element-in-a-sorted-array/)

C++

```cpp
class Solution {
   public:
    // 思路: 取中间, 算l和mid间距离的奇偶性 && mid和r间的奇偶性,
    // 哪个是奇数就在哪边
    int singleNonDuplicate(vector<int>& nums) {
        int l = 0, r = nums.size() - 1;

        int mid = 0;
        while (l <= r) {
            mid = (l + r) / 2;
            if ((mid == 0 || nums[mid - 1] != nums[mid]) &&
                (mid == nums.size() - 1 || nums[mid + 1] != nums[mid])) {
                return nums[mid];
            } else if (mid == 0 || nums[mid - 1] != nums[mid]) {
                if ((mid - l) % 2 == 1) {
                    r = mid - 1;
                } else {
                    l = mid + 2;
                }
            } else {
                if ((r - mid) % 2 == 1) {
                    l = mid + 1;
                } else {
                    r = mid - 2;
                }
            }
        }

        return r;
    }
};
```


# 四. 排序算法

C++: `std::sort`
C: `qsort`

## 4.1 常用排序算法

![[算法合集/常用排序算法合集|常用排序算法合集]]

## 快速选择算法

使用快速排序的算法, 找到第k大的数

> [[../Leetcode101算法笔记#2.1 快慢指针|快慢指针]]的应用

![[../../Excalidraw/Floyd Circle|Floyd Circle]]

# 用途

链表查找环路

# 内容

给定两个指针， 分别命名为 slow 和 fast，起始位置在链表的开头。每次 fast 前进两步，slow 前进一步。如果 fast 可以走到尽头，那么说明没有环路；如果 fast 可以无限走下去，那么说明一定有环路，且一定存 在一个时刻 slow 和 fast 相遇。当 slow 和 fast 第一次相遇时，我们将 fast 重新移动到链表开头，并 让 slow 和 fast 每次都前进一步。当 slow 和 fast 第二次相遇时，相遇的节点即为环路的开始点



# Example

**[T142 环形链表](https://leetcode.cn/problems/linked-list-cycle-ii/description/)**


```cpp
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
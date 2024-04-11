#软件工程与计算 #作业 

# 1. How to define Pow n m

$Pow = \lambda n. \lambda m. \lambda f. \lambda x. (m n) f x$

将 $m$ 作用于 $n$ 再用 $f$ $x$ 作用, 得到 $f^{n^m} x$ 

# 2. Pred && Sub

Pred n = $\lambda f. \lambda x.  n (\lambda g.\lambda h.h(g \space f))(\lambda u.x)(\lambda u.u)$

$n = \lambda f.\lambda x. nfx$

Sub m n  = $n$ Pred $m$ = $n(\lambda f. \lambda x.  m (\lambda g.\lambda h.h(g \space f))(\lambda u.x)(\lambda u.u))$ = $\lambda y.n(\lambda f. \lambda x.  m (\lambda g.\lambda h.h(g \space f))(\lambda u.x)(\lambda u.u))x$ = $\lambda y.n(\lambda z.  m (\lambda g.\lambda h.h(g \space x))(\lambda u.z)(\lambda u.u))$

问题: 若 $m < n$ 则会得到 0 而非一个负数 (无法表示负数), 即

$$
Sub \space n \space m =
\begin{cases}
    0 & \text{if } n \leq m, \\
    n - m & \text{if } n > m.
\end{cases}
$$
# 3. How to define Leq

 由于

$$
Sub \space n \space m =
\begin{cases}
    0 & \text{if } n \leq m, \\
    n - m & \text{if } n > m.
\end{cases}
$$
则有: 

Leq n m = if Iszero(Sub n m) the True else False 

Leq n m = $\lambda n. \lambda m.Iszero(Sub \space n \space m)$ 

# 4. 斐波那契数列

给出斐波那契数列 

F(0) = 0 

F(1) = 1 

F(n) = F(n − 1) + F(n − 2)

求 F

F(n) = $\lambda n. Add \space F(Sub \space n \space 1) F(Sub \space n \space 2)$ = $\lambda$f.$\lambda$n.Add f(Sub n 1) f(Sub n 2) F

即表达式 $\lambda$f.$\lambda$n.Add f(Sub n 1) f(Sub n 2) 的不动点

F = $\Theta$ ($\lambda$f.$\lambda$n.(Add f(Sub n 1) f(Sub n 2))), $\Theta$ 为图灵不动点
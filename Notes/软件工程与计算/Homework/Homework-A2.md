#软件工程与计算 #作业 

# 1. How to define Pow n m

$Pow = \lambda n. \lambda m. \lambda f. \lambda x. (m n) f x$

将 $m$ 作用于 $n$ 再用 $f$ $x$ 作用, 得到 $f^{n^m} x$ 

# 2. Pred && Sub

Pred n = $\lambda f. \lambda x.  n (\lambda g.\lambda h.h(g \space f))(\lambda u.x)(\lambda u.u)$

$n = \lambda f.\lambda x. nfx$

Sub m n  = $n$ Pred $m$ = $n(\lambda f. \lambda x.  m (\lambda g.\lambda h.h(g \space f))(\lambda u.x)(\lambda u.u))$ = $\lambda y.n(\lambda f. \lambda x.  m (\lambda g.\lambda h.h(g \space f))(\lambda u.x)(\lambda u.u))x$ = $\lambda y.n(\lambda z.  m (\lambda g.\lambda h.h(g \space x))(\lambda u.z)(\lambda u.u))$

问题: 若 $m < n$ 会出现问题
# 3. How to define Leq

Leq n m = if $ $ 

# 4. 斐波那契数列

给出斐波那契数列 

F(0) = 0 

F(1) = 1 

F(n) = F(n − 1) + F(n − 2)

求 F
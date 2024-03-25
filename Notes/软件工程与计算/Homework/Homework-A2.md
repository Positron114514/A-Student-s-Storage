#软件工程与计算 #作业 

# 1. How to define Pow n m

$Pow = \lambda n. \lambda m. \lambda f. \lambda x. (m n) f x$

将 $m$ 作用于 $n$ 再用 $f$ $x$ 作用, 得到 $f^{n^m} x$ 

# 2. Pred && Sub

Pred n = $\lambda f. \lambda x.  n (\lambda g.\lambda h.h(g \space f))(\lambda u.x)(\lambda u.u)$

$n = \lambda f.\lambda x. nfx$

得 Pred n = 

# 3. How to define Leq

Leq n m = if $ $ 
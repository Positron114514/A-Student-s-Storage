#Scheme #程序设计 #库 

> [!Notes] 
> 我自己写的一些 Scheme 函数
> PS: 这tm就是一个超级造轮子语言

# 1. Accumulate

## 1.1 accumulate

```scheme
(define (accumulate how_to_accumulate function start next_one end)
  (define (iter a result)
    (cond ((< a end) (iter (next_one a) (how_to_accumulate (function a) result)))
          ((= a end) (how_to_accumulate (function a) result))
          (else result)))
  (iter (next_one start) start))
```

## 1.2 pow

```scheme
(define (pow x time)
  (define (do_pow result time)
    (if (= 0 time)
      result
      (do_pow (* result x) (- time 1))))
  (do_pow 1 time))
```

## 1.3 cube

```scheme
(define (cube x) (* x x x))
```


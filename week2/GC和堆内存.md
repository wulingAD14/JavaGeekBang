串行GC：单线程执行，暂停时间较长。

并行GC：jdk1.8默认GC，多线程执行，较短的暂停时间，提升吞吐量。

CMS GC：多线程并发，初始标记和最终标记阶段需要暂停，存在内存空间碎片化的问题，易造成晋升失败。只有在触发FullGC的情况下会对堆空间进行压缩，需定期重启或者手工触发FullGC来触发碎片整理。比并行GC占用更多CPU，更少暂停时间。

G1 GC：jdk1.9以后默认GC，堆内存需大于4G。不区分年轻代和老年代，GC的同时会整理压缩堆空间。可能会退化成串行GC。
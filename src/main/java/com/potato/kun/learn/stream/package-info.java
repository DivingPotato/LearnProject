/**
 * @author DivingPotato
 * @description
 * 一、概述
 * Stream 是 Java8 中处理集合的关键抽象概念，它可以指定你希望对集合进行的操作，可以执行非常复杂的查找、过滤和映射数据等操作。
 * 使用Stream API 对集合数据进行操作，就类似于使用 SQL 执行的数据库查询。也可以使用 Stream API 来并行执行操作。
 * 简而言之，Stream API 提供了一种高效且易于使用的处理数据的方式。
 *
 * 特点：
 * 1 . 不是数据结构，不会保存数据。
 * 2. 不会修改原来的数据源，它会将操作后的数据保存到另外一个对象中。（保留意见：毕竟peek方法可以修改流中元素）
 * 3. 惰性求值，流在中间处理过程中，只是对操作进行了记录，并不会立即执行，需要等到执行终止操作的时候才会进行实际的计算。
 *
 * 二、分类
 * 1.无状态：指元素的处理不受之前元素的影响；
 * 2.有状态：指该操作只有拿到所有元素之后才能继续下去。
 * 3.非短路操作：指必须处理所有元素才能得到最终结果；
 * 4.短路操作：指遇到某些符合条件的元素就可以得到最终结果，如 A || B，只要A为true，则无需判断B的结果。
 *
 * 三、具体用法
 * 1. 流的中间操作
 * 1.1 筛选与切片
 * 　　filter：过滤流中的某些元素；
 * 　　limit(n)：获取n个元素；
 * 　　skip(n)：跳过n元素，配合limit(n)可实现分页；
 * 　　distinct：通过流中元素的 hashCode() 和 equals() 去除重复元素；
 * 1.2 映射
 *    map：接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
 *    flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
 * 1.3 排序
 *     sorted()：自然排序，流中元素需实现Comparable接口
 *     sorted(Comparator com)：定制排序，自定义Comparator排序器
 * 1.4 消费(不推荐使用)
 *     peek：如同于map，能得到流中的每一个元素。但map接收的是一个Function表达式，有返回值；而peek接收的是Consumer表达式，没有返回值。
 *     java.util.Stream.peek()“主要用于支持调试”。虽然这并不意味着不鼓励将peek()用于其他目的，但是在没有仔细考虑的情况下依赖peek()可能会导致容易出错的代码，比如:
 *     如果流管道不包含终端操作，则不会使用任何元素，并且根本不会调用peek()操作。
 *     只要流实现能够达到最后一步，它就可以自由地优化处理，只生成一些元素，甚至根本不生成元素(例如，依赖于其他集合方法来计数元素)。因此，peek()操作将为更少的元素调用，或者根本不调用。
 * 2. 流的终止操作
 * 2.1 匹配、聚合操作
 * 　　allMatch：接收一个 Predicate 函数，当流中每个元素都符合该断言时才返回true，否则返回false
 * 　　noneMatch：接收一个 Predicate 函数，当流中每个元素都不符合该断言时才返回true，否则返回false
 * 　　anyMatch：接收一个 Predicate 函数，只要流中有一个元素满足该断言则返回true，否则返回false
 * 　　findFirst：返回流中第一个元素
 * 　　findAny：返回流中的任意元素
 * 　　count：返回流中元素的总个数
 * 　　max：返回流中元素最大值
 * 　　min：返回流中元素最小值
 * 2.2 规约操作
 * 　　Optional<T> reduce(BinaryOperator<T> accumulator)：第一次执行时，accumulator函数的第一个参数为流中的第一个元素，第二个参数为流中元素的第二个元素；第二次执行时，第一个参数为第一次函数执行的结果，第二个参数为流中的第三个元素；依次类推。
 * 　　T reduce(T identity, BinaryOperator<T> accumulator)：流程跟上面一样，只是第一次执行时，accumulator函数的第一个参数为identity，而第二个参数为流中的第一个元素。
 * 　　<U> U reduce(U identity,BiFunction<U, ? super T, U> accumulator,BinaryOperator<U> combiner)：在串行流(stream)中，该方法跟第二个方法一样，即第三个参数combiner不会起作用。在并行流(parallelStream)中,我们知道流被fork join出多个线程进行执行，此时每个线程的执行流程就跟第二个方法reduce(identity,accumulator)一样，而第三个参数combiner函数，则是将每个线程的执行结果当成一个新的流，然后使用第一个方法reduce(accumulator)流程进行规约。
 * 2.3 收集操作
 * 　　collect：接收一个Collector实例，将流中元素收集成另外一个数据结构。
 * 　　Collector<T, A, R> 是一个接口，有以下5个抽象方法：
 * 　　Supplier<A> supplier()：创建一个结果容器A
 * 　　BiConsumer<A, T> accumulator()：消费型接口，第一个参数为容器A，第二个参数为流中元素T。
 * 　　BinaryOperator<A> combiner()：函数接口，该参数的作用跟上一个方法(reduce)中的combiner参数一样，将并行流中各 个子进程的运行结果(accumulator函数操作后的容器A)进行合并。
 * 　　Function<A, R> finisher()：函数式接口，参数为：容器A，返回类型为：collect方法最终想要的结果R。
 * 　　Set<Characteristics> characteristics()：返回一个不可变的Set集合，用来表明该Collector的特征。有以下三个特征：
 * 　　CONCURRENT：表示此收集器支持并发。（官方文档还有其他描述，暂时没去探索，故不作过多翻译）
 * 　　UNORDERED：表示该收集操作不会保留流中元素原有的顺序。
 * 　　IDENTITY_FINISH：表示finisher参数只是标识而已，可忽略。
 *
 * @since 2022/12/22
**/
package com.potato.kun.learn.stream;
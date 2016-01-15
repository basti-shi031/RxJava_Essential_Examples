# RxJava学习笔记

1. 创建Obervable
  * create() 基本方法
  ```java
  Observable.create(subscriber -> {
    //do something
  });
  ```

  * just() 接收多个对象
  ```java
  Observable.just(a,b,c...);
  ```

  * repeat() 重复发射数据
  ```java
  Observable.just(a,b,c).repeat(3);
  ```
  共发射了9个数据

  * defer() 声明了一个Observable但想推迟创建直到订阅者订阅
  ```java
  Observable<Integer> deferred = Observable.defer(this::getInt);
  button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              deferred.subscribe(number -> {
                  Log.i("TAG",String.valueOf(number));
              });
          }
      });
  ```

    ```java
    public Observable<Integer> getInt() {
          return Observable.create(subscriber -> {
              if (subscriber.isUnsubscribed()){
                  return ;
              }
              Log.i("TAG","GETINT");
              subscriber.onNext(50);
              subscriber.onCompleted();
          });
      }
    ```
    当Observble未被订阅时，creat()方法还没有调用
    ```java
    Log.i("TAG","GETINT");
    subscriber.onNext(50);
    subscriber.onCompleted();
    ```
    这一段代码，当被订阅时，Observable才开始发送数据。

  * interval() 每隔X秒发送消息，可用于轮询
  ```java
  Observable<Long> observable2 = Observable.interval(3, TimeUnit.SECONDS);
  observable2.subscribe(number -> {
     Log.i("TAG", String.valueOf(number));
  });
  ```

  * timer() 间隔时间
  间隔三秒钟发送
  ```java
  Observable<Long> observable3 = Observable.timer(3, TimeUnit.SECONDS);
  observable3.subscribe(number -> {
      Log.i("TAG", String.valueOf(number));
  });
  ```

    第一个3秒钟后发送，以后每5秒发送一个
```java
Observable<Long> observable3 = Observable.timer(3,5, TimeUnit.SECONDS);
observable3.subscribe(number -> {
    Log.i("TAG", String.valueOf(number));
});
```

  * filter() 过滤器
  ```java
  Observable<String> observable4 =  Observable.from(strings).filter(string -> string.length()==3);
  observable4.subscribe(string -> {
      Log.i("TAG",string);
  });
  ```
  发送长度为3的字符串

  * take() 前n个
  ```java
  Observable<String> observable5 = Observable.from(strings).take(3);
  observable5.subscribe(string -> {
      Log.i("TAG",string);
  });
  ```

  * takeLast()发送最后三个

    数据流为 1，2，3，4，5，6
    发送到的是 4 5 6
  ```java
  Observable<String> observable6 = Observable.from(strings).takeLast(3);
  observable6.subscribe(string -> {
      Log.i("TAG",string);
  });
  ```
  需要注意还有Skip and SkipLast两个方法和take takeLast相对应

  * distinct() 唯一
  ```java
  Observable<String> observable7 = Observable.from(strings).distinct();
  observable7.subscribe(string -> {
      Log.i("TAG",string);
  });
  ```
  需要注意的是：需要理解数据流是流的概念，举个例子，数据流为 a a b c d
  ```java
  Observable<String> observable7 = Observable.from(strings).distinct().take(3);
  observable7.subscribe(string -> {
      Log.i("TAG",string);
  });
  ```
  经过这段代码后，发送的数据为 a b c

    经过distinct()时，a被阻挡，a b c d通过，再经过take时，abcd中d被阻挡，abc通过。

    ```java
    Observable<String> observable7 = Observable.from(strings).take(3).distinct();
    observable7.subscribe(string -> {
        Log.i("TAG",string);
    });
    ```
  经过这段代码后，发送的数据为 a b

    经过take()时，a a b通过，cd被阻挡，aab通过distinct时ab通过，a被阻挡

  * dinstinctUtilChanged() 数据变化时发送
  ```java
  Observable.create(subscriber -> {
     subscriber.onNext(20);
     subscriber.onNext(20);
     subscriber.onNext(20);
     subscriber.onNext(21);
     subscriber.onNext(22);
     subscriber.onNext(21);
     subscriber.onNext(23);
     subscriber.onCompleted();
 }).distinctUntilChanged().subscribe(number -> Log.i("TAG",number+""));
  ```

  * elementAt() 选取第n个元素
    ```java
    Observable<String> observable8 = Observable.from(strings).elementAt(2);
    observable8.subscribe(string -> Log.i("TAG", string + ""));
    ```
    当index值>数组size值时，程序会报错，所以提供另一个方法  elementAtOrDefault
    ```java
    Observable<String> observable9 = Observable.from(strings).elementAtOrDefault(100,"test");
    observable9.subscribe(string -> Log.i("TAG", string + ""));
    ```

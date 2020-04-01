package com.quguang.springcloudfeign.command;


import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.quguang.springcloudfeign.vo.Product;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * 批量查询多个商品的command
 */
public class ObservableCommandHelloWorld extends HystrixObservableCommand<Product> {

    private String[] productIds;

    public ObservableCommandHelloWorld(String[] productIds) {
        super(HystrixCommandGroupKey.Factory.asKey("ObserableExampleGroup"));
        this.productIds = productIds;
    }

    @Override
    protected Observable<Product> construct() {
        return Observable.create(new Observable.OnSubscribe<Product>() {
            @Override
            public void call(Subscriber<? super Product> observer) {
                try {
//                    if (!observer.isUnsubscribed()) {
                        for (String id : productIds) {
                            //调用http请求，将结果放入onnext中
                            observer.onNext(new Product(id,"productName"));
                        }
                        observer.onCompleted();
//                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }
}


package soap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soap.service.BuyService;

/**
 * @ClassName: BuyController
 * @Description: 测试方法1000个线程同时请求一次
 * @author: 壹米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/10/29 10:24
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */
@RestController
public class BuyController {


    @Autowired
    private BuyService buyService;

    /**
     * 购买操作 - 1
     * 原始方式，多线程不安全 （请求时间2s数据不正常，会出现10条左右非法数据）
     */
    @RequestMapping(value = "buy")
    public String Buy() {
        return buyService.Buy();
    }

    /**
     * 购买操作 - 2
     * 使用synchronized关键字，线程安全，但是性能低下 （请求时间2s数据正常）
     * */
    @RequestMapping(value = "buySyn")
    public synchronized String BuySyn() {
        return buyService.Buy();
    }

    /**
     * 购买操作 - 3
     * 使用乐观锁，（请求时间2s数据正常，但是会造成很多请求失败）
     * */
    @RequestMapping(value = "buyOptimistic")
    public String BuyOptimistic() {
        return buyService.BuyOptimistic();
    }




}

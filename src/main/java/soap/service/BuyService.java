package soap.service;

/**
 * @ClassName: BuyService
 * @Description:
 * @author: 壹米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/10/29 10:18
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */
public interface BuyService {


    /**
     * 原始购买
     * @return
     */
    String Buy();

    /**
     * 使用乐观锁
     * @return
     */
    String BuyOptimistic();

    /**
     * 使用悲观锁
     * @return
     */
    String BuyPessimism();

    /**
     * 使用分布式锁
     * @return
     */
    String BuyRedis();
}

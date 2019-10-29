package soap.service.impl;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soap.mapper.OrderMapper;
import soap.mapper.ProductMapper;
import soap.service.BuyService;
import soap.vo.Order;
import soap.vo.Product;
import soap.vo.ProductExample;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName: BuyServiceImpl
 * @Description:
 * @author: 壹米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/10/29 10:19
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */
@Service
public class BuyServiceImpl implements BuyService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 购买商品操作
     */
    @Override
    public String Buy() {
        /** 先判断是否有库存 */
        Product product = productMapper.selectByPrimaryKey(1);
        if (product.getNumber() <= 0) {
            System.out.println("库存不足！");
            return "库存不足！";
        } else {
            /** 减库存 */
            int i =productMapper.reduceOrder();
            if (i == 1) {
                /** 生成订单 */
                Order order = new Order();
                order.setOrderId(UUID.randomUUID().toString());
                orderMapper.insertSelective(order);
                System.out.println("购买成功！");
                return "购买成功！";
            } else {
                System.out.println("购买失败！");
                return "购买失败！";
            }
        }
    }

    @Override
    public String BuyOptimistic() {
        /** 先判断是否有库存 */
        Product product = productMapper.selectByPrimaryKey(1);
        if (product.getNumber() <= 0) {
            System.out.println("库存不足！");
            return "库存不足！";
        } else {
            /** 减库存 */
            int i = productMapper.reduceOrderOptimistic(product.getVersion());
            if (i == 1) {
                /** 生成订单 */
                Order order = new Order();
                order.setOrderId(UUID.randomUUID().toString());
                orderMapper.insertSelective(order);
                System.out.println("购买成功！");
                return "购买成功！";
            } else {
                System.out.println("购买失败！");
                return "购买失败！";
            }
        }
    }

    @Transactional
    @Override
    public String BuyPessimism() {

        /** 先判断是否有库存 */
//        Product product = productMapper.selectByPrimaryKey(1);
        Product product = productMapper.selectWithPessimism(1);
        if (product.getNumber() <= 0) {
            System.out.println("库存不足！");
            return "库存不足！";
        } else {
            /** 减库存 */
            int i =productMapper.reduceOrder();
            if (i == 1) {
                /** 生成订单 */
                Order order = new Order();
                order.setOrderId(UUID.randomUUID().toString());
                orderMapper.insertSelective(order);
                System.out.println("购买成功！");
                return "购买成功！";
            } else {
                System.out.println("购买失败！");
                return "购买失败！";
            }
        }
    }

    /**
     * 使用Redis锁
     * @return
     */
    @Override
    public String BuyRedis() {
        String key = "key_product";
        RLock lock = redissonClient.getLock(key);
        try {
            lock.lock();
            /** 先判断是否有库存 */
            Product product = productMapper.selectByPrimaryKey(1);
            if (product.getNumber() <= 0) {
                System.out.println("库存不足！");
                return "库存不足！";
            } else {
                /** 减库存 */
                int i =productMapper.reduceOrder();
                if (i == 1) {
                    /** 生成订单 */
                    Order order = new Order();
                    order.setOrderId(UUID.randomUUID().toString());
                    orderMapper.insertSelective(order);
                    System.out.println("购买成功！");
                    return "购买成功！";
                } else {
                    System.out.println("购买失败！");
                    return "购买失败！";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return "";
    }
}

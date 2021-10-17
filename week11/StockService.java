package week11;

import com.sun.org.slf4j.internal.LoggerFactory;
import com.sun.org.slf4j.internal.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

@Service
public class StockService {

    Logger logger = LoggerFactory.getLogger(StockService.class);
    //锁键
    private String lock_key = "redis_lock";
    //锁过期
    protected long internalLockLeaseTime = 30000;
    //获取锁的超时时间
    private long timeout = 999999;

    //SET命令的参数
    SetParams params = SetParams.setParams().nx().px(internalLockLeaseTime);

    @Autowired
    JedisPool jedisPool;

    //初始化库存
    public void initStock(String id){
        Jedis jedis = jedisPool.getResource();
        jedis.set(lock_key, String.valueOf(1000));
    }


    //减库存
    public boolean reduceStock(String id, int num){
        Jedis jedis = jedisPool.getResource();
        long start = System.currentTimeMillis();
        try{
            String lock = jedis.set(lock_key, id, params);
            if("OK".equals(lock)){
                if ( Integer.parseInt(jedis.get(lock_key)) >= num ){
                    jedis.set(lock_key, String.valueOf(Integer.parseInt(jedis.get(lock_key)) - num));
                    return true;
                }
                else{
                    return false;
                }
            }
            //否则循环等待，在timeout时间内仍未获取到锁，则获取失败
            long l = System.currentTimeMillis() - start;
            if(l>=timeout){
                return false;
            }
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }finally {
            jedis.close();
        }
    }

    //解锁
    public boolean unlock(String id){
        Jedis jedis = jedisPool.getResource();
        String script =
                " if redis.call('get', KEYS[1]) == ARGV[1] then " +
                " return redis.call('del', KEYS[1]) " +
                " else" +
                "     return 0 " +
                " end";
        try{
            Object result = jedis.eval(script, Collections.singletonList(lock_key), Collections.singletonList(id));
            if("1".equals(result.toString())){
                return true;
            }
            return false;
        }finally {
            jedis.close();
        }
    }


}

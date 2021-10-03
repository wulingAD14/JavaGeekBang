package org.example.bank2;

import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.core.concurrent.threadlcal.HmilyTransactionContextLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Service
@Slf4j
public class AccountServiceImpl implements AccountService{

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountServiceDao accountServiceDao;

    @Override
    @HmilyTCC(confirmMethod = "swapConfrim", cancelMethod = "swapCancel")
    public void swap (String accountNo, String currency, Double amount) {

        //事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("******** Bank2 Service  begin try...  "+transId );
        int existTry = accountServiceDao.isExistTry(transId);
        //try幂等校验
        if(existTry>0){
            log.info("******** Bank2 Service 已经执行try，无需重复执行，事务id:{}  "+transId );
            return ;
        }
        //try悬挂处理
        if(accountServiceDao.isExistCancel(transId)>0 || accountServiceDao.isExistConfirm(transId)>0){
            log.info("******** Bank2 Service 已经执行confirm或cancel，悬挂处理，事务id:{}  "+transId );
            return ;
        }
        //从账户扣减
        if(accountServiceDao.subtractAccountRMBBalance(accountNo,amount )<=0){
            //扣减失败
            throw new HmilyRuntimeException("Bank2 exception，扣减失败，事务id:{}"+transId);
        }
        //增加本地事务try成功记录，用于幂等性控制标识
        accountServiceDao.addTry(transId);

        if(amount==10){//异常一定要抛在Hmily里面
            throw new RuntimeException("bank1 make exception  10");
        }
        log.info("******** Bank1 Service  end try...  "+transId );

    }

    @Transactional
    public void swapConfrim(String accountNo, String currency, Double amount) {
        String localTradeNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        logger.info("******** Bank2 Service begin commit..."+localTradeNo );
    }

    @Transactional
    public void swapCancel(String accountNo, String currency, Double amount) {
        String localTradeNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("******** Bank2 Service begin rollback...  " +localTradeNo);
        if(accountServiceDao.isExistTry(localTradeNo) == 0){ //空回滚处理，try阶段没有执行什么也不用做
            log.info("******** Bank2 try阶段失败... 无需rollback "+localTradeNo );
            return;
        }
        if(accountServiceDao.isExistCancel(localTradeNo) > 0){ //幂等性校验，已经执行过了，什么也不用做
            log.info("******** Bank2 已经执行过rollback... 无需再次rollback " +localTradeNo);
            return;
        }
        //再将金额加回账户
        accountServiceDao.addAccountUSDBalance(accountNo,amount);
        //添加cancel日志，用于幂等性控制标识
        accountServiceDao.addCancel(localTradeNo);
        log.info("******** Bank2 Service end rollback...  " +localTradeNo);
    }

}


}


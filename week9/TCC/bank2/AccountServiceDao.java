package org.example.bank2;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;

@Mapper
@Component
public interface AccountServiceDao {

    @Update("update account_info set USD_balance=USD_balance - #{amount} where USD_balance>#{amount} and account_no=#{accountNo} ")
    int subtractAccountRMBBalance(@Param("accountNo") String accountNo, @Param("amount") Double amount);
    @Update("update account_info set RMB_balance=RMB_balance + #{amount}/7 where account_no=#{accountNo} ")
    int addAccountUSDBalance(@Param("accountNo") String accountNo, @Param("amount") Double amount);

    /**
     * 增加某分支事务try执行记录
     * @param localTradeNo 本地事务编号
     * @return
     */
    @Insert("insert into local_try_log values(#{txNo},now());")
    int addTry(String localTradeNo);

    @Insert("insert into local_confirm_log values(#{txNo},now());")
    int addConfirm(String localTradeNo);

    @Insert("insert into local_cancel_log values(#{txNo},now());")
    int addCancel(String localTradeNo);

    /**
     * 查询分支事务try是否已执行
     * @param localTradeNo 本地事务编号
     * @return
     */
    @Select("select count(1) from local_try_log where tx_no = #{txNo} ")
    int isExistTry(String localTradeNo);
    /**
     * 查询分支事务confirm是否已执行
     * @param localTradeNo 本地事务编号
     * @return
     */
    @Select("select count(1) from local_confirm_log where tx_no = #{txNo} ")
    int isExistConfirm(String localTradeNo);
    /** * 查询分支事务cancel是否已执行
     * @param localTradeNo 本地事务编号
     * @return
     */
    @Select("select count(1) from local_cancel_log where tx_no = #{txNo} ")
    int isExistCancel(String localTradeNo);

}

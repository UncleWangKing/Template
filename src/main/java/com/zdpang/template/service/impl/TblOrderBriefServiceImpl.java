package com.zdpang.template.service.impl;

import com.zdpang.template.model.TblBetEntries;
import com.zdpang.template.model.TblOrderBrief;
import com.zdpang.template.dao.TblOrderBriefMapper;
import com.zdpang.template.service.TblBetEntriesService;
import com.zdpang.template.service.TblOrderBriefService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zdpang
 * @since 2019-06-25
 */
@Service
public class TblOrderBriefServiceImpl extends ServiceImpl<TblOrderBriefMapper, TblOrderBrief> implements TblOrderBriefService {
  @Autowired
  private TblBetEntriesService tblBetEntriesService;
  private int TOTAL_TIMES = 100;
  private int ORDER_COUNT_PER_TIME = 1000;
  private int BET_COUNT_PER_ORDER = 3;

  @Override
  public boolean batchInsert() {
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < TOTAL_TIMES; i++) {
      long currentTimeMillis = System.currentTimeMillis();
      /**
       * order数据写入
       */
      List<TblOrderBrief> orderList = getRandomOrderList(ORDER_COUNT_PER_TIME);
      saveBatch(orderList);
      /**
       * 日志
       */
      System.out.println("已完成" + (i + 1) * ORDER_COUNT_PER_TIME + "条order插入 耗时" + (System.currentTimeMillis() - currentTimeMillis) + "毫秒");
      currentTimeMillis = System.currentTimeMillis();
      /**
       * order对应的bet数据写入
       */
      List<TblBetEntries> betList = new LinkedList<>();
      for (TblOrderBrief order : orderList) {
        betList.addAll(getRandomBetList(order.getTransactionId(), BET_COUNT_PER_ORDER));
      }
      tblBetEntriesService.saveBatch(betList);
      /**
       * 日志
       */
      System.out.println("已完成" + (i + 1) * ORDER_COUNT_PER_TIME * BET_COUNT_PER_ORDER + "条bet插入 耗时" + (System.currentTimeMillis() - currentTimeMillis) + "毫秒");
      currentTimeMillis = System.currentTimeMillis();
      Date date = new Date(startTime + (currentTimeMillis - startTime) / (i + 1) * TOTAL_TIMES);
      System.out.println("预计完成时间:" + date);
    }
    return true;
  }

  private List<TblOrderBrief> getRandomOrderList(Integer num){
    List<TblOrderBrief> list = new LinkedList<>();
    for (int i = 0; i < num; i++) {
      list.add(getRandomOrder());
    }
    return list;
  }

  private List<TblBetEntries> getRandomBetList(String transactionId, Integer num){
    List<TblBetEntries> list = new LinkedList<>();
    for (int i = 0; i < num; i++) {
      list.add(getRandomBet(transactionId));
    }
    return list;
  }

  private TblBetEntries getRandomBet(String transactionId){
    TblBetEntries bet = new TblBetEntries();
    bet.setTransactionId(transactionId);
    bet.setGameUniqueId("HF_FFSSC");
    bet.setGameIssueNo("201906240905");
    bet.setGameplayMethod("DXDS");
    bet.setGameplayMethodName("大小单双-后二位");
    bet.setUserUniqueId(66666L);
    bet.setNumber("小|单");
    bet.setPricePerUnit(new BigDecimal(2));
    bet.setNumberOfUnits(1);
    bet.setAmount(new BigDecimal(2));
    bet.setReturnMoneyRatio(new BigDecimal(0));
    bet.setReturnMoneyAmount(new BigDecimal(0));
    bet.setOutcome(0);
    bet.setWinningAmount(new BigDecimal(0));
    return bet;
  }

  private TblOrderBrief getRandomOrder(){
    Random random = new Random();
    UUID uuid = new UUID(random.nextLong(), random.nextLong());
    TblOrderBrief orderBrief = new TblOrderBrief();
    orderBrief.setGameUniqueId("HF_FFSSC");
    orderBrief.setGameUniqueName("分分时时彩");
    orderBrief.setGameIssueNo("201906240905");
    orderBrief.setAgentId(3);
    orderBrief.setClientId(23333);
    orderBrief.setUserUniqueId(66666);
    orderBrief.setUsername("king");
    orderBrief.setNumberOfUnits(20);
    orderBrief.setRebate(new BigDecimal(0));
    orderBrief.setTransactionAmount(new BigDecimal(1153.3390));
    orderBrief.setWinAmount(new BigDecimal(0));
    orderBrief.setWinLoseAmount(new BigDecimal(-1153.3390));
    orderBrief.setTransactionStatus("PENDING");
    orderBrief.setBrand("106");
    orderBrief.setVersion(1);
    orderBrief.setRetryTime(0);
    orderBrief.setTotalCharge(new BigDecimal(1153.3390));
    orderBrief.setBalanceChangeId(uuid.toString());
    orderBrief.setPrizeSettings("{\"C33\":[{\"prizeType\":1,\"prizeAmount\":326.34,\"displayName\":\"头等奖\"}],\"DXDS\":[{\"prizeType\":1,\"prizeAmount\":3.92,\"displayName\":\"头等奖\"}],\"POKER_Z3\":[{\"prizeType\":269,\"prizeAmount\":98.0,\"displayName\":\"豹子\"},{\"prizeType\":270,\"prizeAmount\":16.3366,\"displayName\":\"顺子\"},{\"prizeType\":271,\"prizeAmount\":3.626,\"displayName\":\"对子\"},{\"prizeType\":272,\"prizeAmount\":2.7146,\"displayName\":\"半顺\"},{\"prizeType\":273,\"prizeAmount\":3.2634,\"displayName\":\"杂六\"}],\"C36\":[{\"prizeType\":1,\"prizeAmount\":163.66,\"displayName\":\"头等奖\"}],\"DXDS_Q3\":[{\"prizeType\":1,\"prizeAmount\":7.84,\"displayName\":\"头等奖\"}],\"DXDS_Q2\":[{\"prizeType\":1,\"prizeAmount\":3.92,\"displayName\":\"头等奖\"}],\"DN\":[{\"prizeType\":1,\"prizeAmount\":9.8,\"displayName\":\"头等奖\"}],\"D2\":[{\"prizeType\":1,\"prizeAmount\":98.0,\"displayName\":\"头等奖\"}],\"R2Z_HZ\":[{\"prizeType\":1,\"prizeAmount\":98.0,\"displayName\":\"头等奖\"}],\"C2\":[{\"prizeType\":1,\"prizeAmount\":44.1,\"displayName\":\"头等奖\"}],\"D3\":[{\"prizeType\":1,\"prizeAmount\":980.0,\"displayName\":\"头等奖\"}],\"DXDS_H3\":[{\"prizeType\":1,\"prizeAmount\":7.84,\"displayName\":\"头等奖\"}],\"TND_53\":[{\"prizeType\":268,\"prizeAmount\":9.31,\"displayName\":\"和\"},{\"prizeType\":266,\"prizeAmount\":1.96,\"displayName\":\"龙\"},{\"prizeType\":267,\"prizeAmount\":1.96,\"displayName\":\"虎\"}],\"D5\":[{\"prizeType\":1,\"prizeAmount\":98000.0,\"displayName\":\"头等奖\"}],\"C5\":[{\"prizeType\":1,\"prizeAmount\":20031.2,\"displayName\":\"头等奖\"},{\"prizeType\":2,\"prizeAmount\":215.6,\"displayName\":\"二等奖\"},{\"prizeType\":3,\"prizeAmount\":39.2,\"displayName\":\"三等奖\"},{\"prizeType\":4,\"prizeAmount\":19.6,\"displayName\":\"四等奖\"}],\"DOUNIU\":[{\"prizeType\":275,\"prizeAmount\":10.78,\"displayName\":\"牛一\"},{\"prizeType\":274,\"prizeAmount\":10.78,\"displayName\":\"牛牛\"},{\"prizeType\":276,\"prizeAmount\":10.78,\"displayName\":\"牛二\"},{\"prizeType\":277,\"prizeAmount\":10.78,\"displayName\":\"牛三\"},{\"prizeType\":278,\"prizeAmount\":10.78,\"displayName\":\"牛四\"},{\"prizeType\":279,\"prizeAmount\":10.78,\"displayName\":\"牛五\"},{\"prizeType\":280,\"prizeAmount\":10.78,\"displayName\":\"牛六\"},{\"prizeType\":281,\"prizeAmount\":10.78,\"displayName\":\"牛七\"},{\"prizeType\":282,\"prizeAmount\":10.78,\"displayName\":\"牛八\"},{\"prizeType\":283,\"prizeAmount\":10.78,\"displayName\":\"牛九\"},{\"prizeType\":284,\"prizeAmount\":2.156,\"displayName\":\"无牛\"}]}");
    orderBrief.setStopAfterWin(false);
    orderBrief.setOrderMultiplier(1);
    return orderBrief;
  }
}

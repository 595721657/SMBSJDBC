package com.dragon.dao.bill;

import java.sql.Connection;
import java.util.List;

import com.dragon.pojo.Bill;

public interface BillDao {
	/**
	 * 澧炲姞璁㈠崟
	 * @param connection
	 * @param bill
	 * @return
	 * @throws Exception
	 */
	public int add(Connection connection,Bill bill)throws Exception;


	/**
	 * 閫氳繃鏌ヨ鏉′欢鑾峰彇渚涘簲鍟嗗垪琛�-妯＄硦鏌ヨ-getBillList
	 * @param connection
	 * @param bill
	 * @return
	 * @throws Exception
	 */
	public List<Bill> getBillList(Connection connection,Bill bill)throws Exception;
	
	/**
	 * 閫氳繃delId鍒犻櫎Bill
	 * @param connection
	 * @param delId
	 * @return
	 * @throws Exception
	 */
	public int deleteBillById(Connection connection,String delId)throws Exception; 
	
	
	/**
	 * 閫氳繃billId鑾峰彇Bill
	 * @param connection
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Bill getBillById(Connection connection,String id)throws Exception; 
	
	/**
	 * 淇敼璁㈠崟淇℃伅
	 * @param connection
	 * @param bill
	 * @return
	 * @throws Exception
	 */
	public int modify(Connection connection,Bill bill)throws Exception;

	/**
	 * 鏍规嵁渚涘簲鍟咺D鏌ヨ璁㈠崟鏁伴噺
	 * @param connection
	 * @param providerId
	 * @return
	 * @throws Exception
	 */
	public int getBillCountByProviderId(Connection connection,String providerId)throws Exception;

}

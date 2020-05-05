package com.dragon.dao.provider;

import java.sql.Connection;
import java.util.List;
import com.dragon.pojo.Provider;

public interface ProviderDao {
	
	/**
	 * 澧炲姞渚涘簲鍟�
	 * @param connection
	 * @param provider
	 * @return
	 * @throws Exception
	 */
	public int add(Connection connection,Provider provider)throws Exception;


	/**
	 * 閫氳繃渚涘簲鍟嗗悕绉般�佺紪鐮佽幏鍙栦緵搴斿晢鍒楄〃-妯＄硦鏌ヨ-providerList
	 * @param connection
	 * @param proName
	 * @return
	 * @throws Exception
	 */
	public List<Provider> getProviderList(Connection connection,String proName,String proCode)throws Exception;
	
	/**
	 * 閫氳繃proId鍒犻櫎Provider
	 * @param delId
	 * @return
	 * @throws Exception
	 */
	public int deleteProviderById(Connection connection,String delId)throws Exception; 
	
	
	/**
	 * 閫氳繃proId鑾峰彇Provider
	 * @param connection
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Provider getProviderById(Connection connection,String id)throws Exception; 
	
	/**
	 * 淇敼鐢ㄦ埛淇℃伅
	 * @param connection
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int modify(Connection connection,Provider provider)throws Exception;
	
	
}

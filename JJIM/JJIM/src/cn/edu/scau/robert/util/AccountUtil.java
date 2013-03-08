/**
 * 
 */
package cn.edu.scau.robert.util;


/**
 * @author robert
 *账号生成算法
 */
public class AccountUtil {
	
	public AccountUtil(){
		while(account.length()!=9){
			long lAccount =  (long) (Math.random()*(1000000000));
			this.account = String.valueOf(lAccount);
		}
	
	}
	
	private String account = "0";

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
}

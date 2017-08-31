/**
 * 
 */
package canGit;

import java.awt.Dialog.ModalExclusionType;

/**
 * 快速幂取模 
 * 公式           a^b%c = (a%c)^b%c
 *
 */
public class FastPowMod {
	public static int fastPowMod(int a, int b,int mod){//求a的b次方，模c
		//和快速幂基本一致
		int res =1;
		while(b!=0){
			
			a=a%mod; //这一步不要结果也对
			if((b&1) ==1){
				res = res * a % mod;
			}
			b=b>>1;
		}
		return res;
	}
}

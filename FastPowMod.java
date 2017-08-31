/**
 * 
 */
package canGit;

import java.awt.Dialog.ModalExclusionType;

/**
 * ������ȡģ 
 * ��ʽ           a^b%c = (a%c)^b%c
 *
 */
public class FastPowMod {
	public static int fastPowMod(int a, int b,int mod){//��a��b�η���ģc
		//�Ϳ����ݻ���һ��
		int res =1;
		while(b!=0){
			
			a=a%mod; //��һ����Ҫ���Ҳ��
			if((b&1) ==1){
				res = res * a % mod;
			}
			b=b>>1;
		}
		return res;
	}
}

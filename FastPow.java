/**
 * 
 */
package canGit;

/**
 * �������ݣ����η����ܴ�ʱ���ÿ�����
 *
 */
public class FastPow {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(fastPow(3, 4));//���81

	}
	public static int fastPow(int base, int b){//��a��b�η�
		int res = 1;
		while(b!=0){
			if((b & 1)==1){res*=base;}//��Ϊ1ʱ��ʾ��������һλ�У�������г�
			base=base*base;//�ж��ٶ�����λ���м���ƽ�������һ��ʼ�յ���ǰ��һ��ƽ��
			b>>=1;//b=b>>2
		}
		return res;
	}

}

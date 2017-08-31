/**
 * 
 */
package canGit;

/**
 * （快速幂）当次方数很大时，用快速幂
 *
 */
public class FastPow {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(fastPow(3, 4));//结果81

	}
	public static int fastPow(int base, int b){//求a的b次方
		int res = 1;
		while(b!=0){
			if((b & 1)==1){res*=base;}//当为1时表示二进制那一位有，往结果中乘
			base=base*base;//有多少二进制位就有几个平方，后边一个始终等于前边一个平方
			b>>=1;//b=b>>2
		}
		return res;
	}

}

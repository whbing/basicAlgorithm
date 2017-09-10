package canGit;
/**
 * 快乐数（happy number）有以下的特性：在给定的进位制下，该数字所有数位(digits)的平方和，
 * 得到的新数再次求所有数位的平方和，如此重复进行，最终结果必为1。
 * 
 * 2 8 → 2²+8²=68 → 6²+8²=100 → 1²+0²+0²=1
   3 2 → 3²+2²=13 → 1²+3²=10 → 1²+0²=1
   3 7 → 3²+7²=58 → 5²+8²=89 → 8²+9²=145 → 1²+4²+5²=42 → 4²+2²=20 → 2²+0²=4 → 4²=16 → 1²+6²=37
 
 不是快乐数的数称为不快乐数（unhappy number），所有不快乐数的数位平方和计算，
 最後都会进入 4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4 的循环中。
 
 问题：求小于等于n的快乐数之和。
 
 */
import java.util.Scanner;

public class HappyNum {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		while(sc.hasNext()){
			int n=sc.nextInt();
			int sum=0;
			for(int i=1;i<=n;i++){
				if(isHappy(i)){
					sum+=i;
				}
			}
			System.out.println(sum);
		}
	}

	static boolean isHappy(int n) {
		if (n <= 0) {
			return false;
		}
		while (n != 1) {
			n = getsum(n);
			
			// 为false结果必为4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4的循环
			
			if (n == 4) {
				return false;
			}
		}
		return true;
	}

	// 获得各个位数的平方和
	static int getsum(int x) {
		int sum = 0;
		while (x > 0) {
			sum += (x % 10) * (x % 10);//平方，再加到sum中
			x = x / 10;
		}
		return sum;
	}

}

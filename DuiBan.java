package canGit;
/**
 * 【对半分】求和差最小（电信2018秋）
 * 有x个数（x为偶数），将其平均分成两组（每组x/2个数），求sum1，sum2为各自组内和，
 * 求和差最小值
 * 
 */
import java.util.Arrays;
import java.util.Scanner;

public class DuiBan {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = scanner.nextInt();
		}
		Arrays.sort(arr);

		int length = arr.length;
		int half_length = arr.length / 2;
		int sum1 = 0;
		int sum2 = 0;
		if (length % 4 == 0) {
			for (int i = 0; i < half_length; i++) {
				if (i % 2 != 0) {
					sum1 += arr[i] + arr[length - 1 - i];
				} else {
					sum2 += arr[i] + arr[length - 1 - i];
				}
			}
		} else {//不为4的倍数时，最后将中间的两个数分给sum1，sum2，大的分给小者，小的分给大者
			for (int i = 0; i < half_length - 1; i++) {
				if (i % 2 == 0) {
					sum1 += arr[i] + arr[length - 1 - i];
				} else {
					sum2 += arr[i] + arr[length - 1 - i];
				}
			}
			if (sum1 - sum2 > 0) {
				sum1 += arr[half_length - 1];
				sum2 += arr[half_length];
			} else {
				sum2 += arr[half_length - 1];
				sum1 += arr[half_length];
			}
		}
		System.out.println(Math.abs(sum2 - sum1));
	}

}

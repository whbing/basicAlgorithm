package canGit;
/**
 * ���԰�֡���Ͳ���С������2018�
 * ��x������xΪż����������ƽ���ֳ����飨ÿ��x/2����������sum1��sum2Ϊ�������ںͣ�
 * ��Ͳ���Сֵ
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
		} else {//��Ϊ4�ı���ʱ������м���������ָ�sum1��sum2����ķָ�С�ߣ�С�ķָ�����
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

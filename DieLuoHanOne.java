/**
 *��ţ������
���޺���һ����������Ϸ����Ϸ��һ����Ҫվ����һ���˵ļ���ϡ�ͬʱ����Ӧ����������˱�������˸���һ�㡣��֪�μ���Ϸ��ÿ���˵���ߣ�
���д�������ͨ��ѡ�������Ϸ���ˣ����Ƕ��ܵ����ٸ��ˡ�ע��������˶����Ⱥ󵽵ģ���ζ�Ųμ���Ϸ���˵��Ⱥ�˳����ԭ�����е�˳��Ӧ��һ�¡�
����һ��int����men��������������ÿ���˵���ߡ�ͬʱ����������n���뷵������ܵ�����������֤nС�ڵ���500��
����������
[1,6,2,5,3,4],6
���أ�4
����������ִ�����dp��
 */
package canGit;

/**
 * @author github.com/whbing
 */
public class DieLuoHanOne {

	public static void main(String[] args) {
		int[] a= {1,6,2,5,3,4};
		int r=maxSubStr(a);
		System.out.println(r);
	}
	static int maxSubStr(int[] a){//����a����ִ��ĳ���
		//��[��i�����β]����ִ��ĳ��� ���Դ�[��i-1��ǰÿһ��]�Ƴ���
		//����i��ֵ��ǰ��������ÿ�����ʱ���ͼ�1���������ǰһ��һ�������ȡ���ľ���
		//ע�⣺������i�����ֽ�β
		int[] dp = new int[a.length];
		int max= Integer.MIN_VALUE;
		for(int i=0;i<a.length;i++){
			dp[i]=1;
		}
		
		for(int i=1;i<dp.length;i++){
			for(int j=0;j<i;j++){
				if(a[i]>a[j]){
					dp[i]=Math.max(dp[j]+1,dp[i]);					
				}									
			}
			max=Math.max(dp[i], max);
		}
		return max;
	}

}

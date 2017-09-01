/**
 *【牛客网】
叠罗汉是一个著名的游戏，游戏中一个人要站在另一个人的肩膀上。同时我们应该让下面的人比上面的人更高一点。已知参加游戏的每个人的身高，
请编写代码计算通过选择参与游戏的人，我们多能叠多少个人。注意这里的人都是先后到的，意味着参加游戏的人的先后顺序与原序列中的顺序应该一致。
给定一个int数组men，代表依次来的每个人的身高。同时给定总人数n，请返回最多能叠的人数。保证n小于等于500。
测试样例：
[1,6,2,5,3,4],6
返回：4
【分析：最长字串问题dp】
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
	static int maxSubStr(int[] a){//返回a的最长字串的长度
		//以[第i个点结尾]的最长字串的长度 可以从[第i-1以前每一个]推出。
		//当第i个值比前边所有中每个点大时，就加1个，否则和前一次一样。最后取最大的就是
		//注意：必须以i个数字结尾
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

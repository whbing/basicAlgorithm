/**
 * 一棵树从根节点到叶子节点的连通线上，求连通节点的乘积的最大值。
 * int[] values =  {2,4,10,20,1}
 * int[][] edges={{1,2},{1,3},{2,4},{2,5}};
 *       
 *         1(2)
 *          /\
 *     2(4)  3(10)
 *       /\     
 *   4(20) 5(1)
 *   即2*4*20最大
 */
package canGit;

public class TreePathNodeMax {
	static int find(int numNodes, int rootNode, int[] values, int[][] edges) {
		int max = Integer.MIN_VALUE;
		int temMax = 1;
		for (int i = 0; i < edges.length; i++) {
			// 找到第1组
			temMax = 1;
			if (edges[i][0] == 1) {//改为==rootNode 当第i个为1才开始
				
				temMax = values[ edges[i][0]-1 ] * values[edges[i][1]-1];
				int t = edges[i][1];
				int j = i + 1;
				while (j < edges.length) {
					if (edges[j][0] == t) {// 连起来了
						temMax = temMax * values[ edges[j][1]-1 ];
						t = edges[j][1];
					}
					j++;
				}
			}
			max = Math.max(max, temMax);
		}
		return max;
	}
	public static void main(String[] args) {
		int[] values = {2,4,10,20,1};
		int[][] edges={{1,2},{1,3},{2,4},{2,5}};
		int res=find(0, 0, values, edges);
		System.out.println(res);
	}
}

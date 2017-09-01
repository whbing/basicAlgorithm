牛客网
**描述**
叠罗汉是一个著名的游戏，游戏中一个人要站在另一个人的肩膀上。为了使叠成的罗汉更稳固，我们应该让上面的人比下面的人更轻一点。现在一个马戏团要表演这个节目，为了视觉效果，我们还要求下面的人的身高比上面的人高。请编写一个算法，计算最多能叠多少人，注意这里所有演员都同时出现。

---

给定一个二维int的数组actors，每个元素有两个值，分别代表一个演员的身高和体重。同时给定演员总数n，请返回最多能叠的人数。保证总人数小于等于500。
---
测试样例：
[[1,2],[3,4],[5,6],[7,8]],4
返回：4
`java`
```java
public int getHeight(int[][] actors, int n) {
    // write code here
    int[] A=new int[n];
    int[] B=new int[n];
    for(int i=0;i<actors.length;i++){
        A[i]=actors[i][0];
        B[i]=actors[i][1];            
    }
    sort(A,B);//sort(B,A),再找A也一样
    int r=maxSubStr(B);
    return r;
}

//二维排序
public static void sort(int[] A,int[] B){//按A从小到大排序，同时B也跟着排序
	for(int i=1;i<A.length;i++){
		for(int j=0;j<A.length-i;j++){
			if(A[j+1]<A[j]){
				int t=A[j+1];
				A[j+1]=A[j];
				A[j]=t;
				//A换的同时换B
				t=B[j+1];
				B[j+1]=B[j];
				B[j]=t;
			}
		}
	}
}

static int maxSubStr(int[] a){//返回a的最长字串的长度
	
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

```
/**
 *  以下BookInfo类将msg字符串进行拆分验证，如果格式合理将能生成一个对象
 */
package thoughtwork;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WangHongbing whbing1991@gmail.com
 *
 */
public class BookInfo implements Comparable<BookInfo>{

	/**
	 * @param Uid :用户表示，msg分割的第一个字段	 * 
	 */
	String Uid;
	/**
	 * @param spot :场地
	 */
	char spot;
	/**
	 * @param dateStart :开始时间
	 */
	Date dateStart = new Date();
	/**
	 * @param dateEnd :结束时间
	 */
	Date dateEnd = new Date();
	/**
	 * @param hourStart :起始时间中的小时
	 */
	int hourStart;
	/**
	 * @param hourEnd :结束时间中的小时
	 */
	int hourEnd;
	
	int weekDay;//周日:0,周一:1,...周六:7-1,已经做了减1处理，可以对应
	
	String date;
	/**
	 * @param cost :该时间段内费用
	 */
	int cost;
	int costCancel=0;//收违约费，默认不收
	/**
	 * @param operate :B or C, B表示预定，C表示取消
	 */
	char operate;// B or C, B表示预定，C表示取消
	
	/**
	 * @param info :Uid+date+spot的标识
	 */
	String info;

	public BookInfo(String msg) throws Exception{
		// U001 2016-06-02 22:00~22:00 A
		Exception e=new Exception("> Error: the booking is invalid!");
		
		//1. 先形式判断是否合理
		String str = msg.trim();
		String pattern = "\\w*\\s*\\d{4}-\\d{1,2}-\\d{1,2}\\s*([0-1][0-9]|[2][0-4]):00~([0-1][0-9]|[2][0-4]):00\\s*(A|B|C|D|A\\s*C|B\\s*C|C\\s*C|D\\s*C)$";

		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		//System.out.println(m.matches());
		if(!m.matches()){
			throw e;
		}
		
		//2. 进一步判断时间是否合理
		String[] s = str.split("\\s");
		String time = s[1] + " " + s[2];
		String[] t = time.split("[\\s|\\-|:|~]");
		// 得到2016 06 02 22 00 22 00格式数组
		Uid = s[0];
		this.spot = s[3].charAt(0);

		if (s.length == 4) {// 预定
			this.operate = 'B';
		} else if(s.length == 5){// 长度为5，表示后边还有C，为取消
			this.operate = 'C';
		}else{
			throw e;
		}

		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		//设置month要-1
		calendarStart.set(Integer.valueOf(t[0]), Integer.valueOf(t[1])-1, Integer.valueOf(t[2]), Integer.valueOf(t[3]), 0,
				0);
		calendarEnd.set(Integer.valueOf(t[0]), Integer.valueOf(t[1])-1, Integer.valueOf(t[2]), Integer.valueOf(t[5]), 0,
				0);

		
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd ");
		SimpleDateFormat df2 = new SimpleDateFormat("HH");//HH24制，hh12制
		dateStart = calendarStart.getTime();
		dateEnd = calendarEnd.getTime();
		
		if(dateStart.getTime()>=dateEnd.getTime()){
			throw e;
		}
		
		hourStart = Integer.parseInt(df2.format(dateStart));
		hourEnd = Integer.parseInt(df2.format(dateEnd));
		
		if(hourStart<9||hourEnd>22){
			throw e;
		}
		//周日:0,周一:1,...周六:6,已经做了减1处理，可以对应
		weekDay = calendarStart.get(calendarStart.DAY_OF_WEEK)-1;		
		date = df1.format(dateStart).trim() + " " + df2.format(dateStart) + ":00~" + df2.format(dateEnd) + ":00";
		
		// date:2016-06-02 09:00~10:00
		
		int price1_5_9_12=30;
		int price1_5_13_18=50;
		int price1_5_19_20=80;
		int price1_5_21_22=60;
		int price6_7_9_12=40;
		int price6_7_13_18=50;
		int price6_7_19_22=60;

		float cancelRate1_5 =0.5f;
		float cancelRate6_7 =0.25f;
		
		/**
		 * 以下分类处理cost问题
		 */		 
		if(weekDay>=1&&weekDay<=5){//周1到周5
			//11点以前预定
			if(hourStart<=11){
				if(hourEnd<=12){//9~.~.~12
					cost = price1_5_9_12 * (hourEnd-hourStart);				
				}else if (hourEnd>=13&&hourEnd<=18) {//9~.~12~.~18
					cost = price1_5_9_12 * (12-hourStart)+price1_5_13_18*(hourEnd-12);				
				}else if(hourEnd>=19&&hourEnd<=20){//9~.~12~18~.~20结束
					cost = price1_5_9_12 * (12-hourStart)+price1_5_13_18*(18-12)
							+price1_5_19_20*(hourEnd-18);
				}else if(hourEnd>=21){
					cost = price1_5_9_12*(12-hourStart)+price1_5_13_18*(18-12)
							+price1_5_19_20*(20-18)+price1_5_21_22*(hourEnd-20);
				}
			}			
			
			//12点至17点预定
			if(hourStart>=12 && hourStart <=17){
				if(hourEnd<=18){
					cost = price1_5_13_18 * (hourEnd-hourStart);				
				}else if (hourEnd<=20) {
					cost = price1_5_13_18*(18-hourStart)+price1_5_19_20*(hourEnd-18);				
				}else if(hourEnd<=22){
					cost = price1_5_13_18 * (18-hourStart)+price1_5_19_20*(20-18)
							+price1_5_21_22*(hourEnd-20);
				}
			}		
			
			//18点到19点预定
			if(hourStart>=18 && hourStart <=19){
				if(hourEnd<=20){
					cost = price1_5_19_20 * (hourEnd-hourStart);				
				}else if (hourEnd<=22) {
					cost = price1_5_19_20*(20-hourStart)+price1_5_21_22*(hourEnd-20);				
				}
			}
			
			//20点到22预定
			if(hourStart>=20){
				cost = price1_5_21_22*(hourEnd-hourStart);				
			}
		}else{//周六周日
			//11点以前预定
			if(hourStart<=11){
				if(hourEnd<=12){//9~.~.~12
					cost = price6_7_9_12 * (hourEnd-hourStart);				
				}else if (hourEnd>=13&&hourEnd<=18) {//9~.~12~.~18
					cost = price6_7_9_12 * (12-hourStart)+price6_7_13_18*(hourEnd-12);				
				}else if(hourEnd>=19){
					cost = price6_7_9_12*(12-hourStart)+price6_7_13_18*(18-12)
							+price6_7_19_22*(hourEnd-18);
				}
			}			
			
			//12点至17点预定
			if(hourStart>=12 && hourStart <=17){
				if(hourEnd<=18){
					cost = price6_7_13_18 * (hourEnd-hourStart);				
				}else if (hourEnd<=22) {
					cost = price6_7_13_18*(18-hourStart)+price6_7_19_22*(hourEnd-18);				
				}
			}	
			//18点以后预定
			if(hourStart>=18){				
				cost = price6_7_19_22*(hourEnd-hourStart);			
			}	
			
		}
		/**
		 * END cost分类
		 */
		//判断是取消还是预定,预定B不用管
		if(this.operate=='C'){//取消
			if(weekDay>=1||weekDay<=5){
				costCancel = (int)(cost*cancelRate1_5);
				//cost=(int)(-cost*(1-cancelRate1_5)); //相当于本次加入负收入
			}else{
				costCancel = (int)(cost*cancelRate6_7);
				//cost=(int)(-cost*(1-cancelRate6_7)); //相当于本次加入负收入
			}
		}
		
		//用户id，时间，场地作为唯一标识（除了是否预订）
		info = Uid.trim()+" "+date.trim()+" "+spot;
	}

	public String getUid() {
		return Uid;
	}

	public void setUid(String uid) {
		Uid = uid;
	}

	public char getSpot() {
		return spot;
	}

	public void setSpot(char spot) {
		this.spot = spot;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public char getOperate() {
		return operate;
	}

	public void setOperate(char operate) {
		this.operate = operate;
	}
	
	@Override
	public int compareTo(BookInfo o) {
		// TODO Auto-generated method stub
		if(this.spot<o.spot){//从A到D排序
			return -1;
		}else if(this.spot>o.spot){
			return 1;
		}else{//spot相等时
			//比较时间
			if (this.dateStart.getTime()<o.dateStart.getTime()) {
				return -1;
			}else if(this.dateStart.getTime()>o.dateStart.getTime()){
				return 1;
			}else{
				return 0;
			}
		}
	}

	@Override
	public String toString() {		
		return this.info+" "+this.operate;
	}
}

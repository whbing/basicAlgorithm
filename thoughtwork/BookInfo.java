/**
 *  ����BookInfo�ཫmsg�ַ������в����֤�������ʽ����������һ������
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
	 * @param Uid :�û���ʾ��msg�ָ�ĵ�һ���ֶ�	 * 
	 */
	String Uid;
	/**
	 * @param spot :����
	 */
	char spot;
	/**
	 * @param dateStart :��ʼʱ��
	 */
	Date dateStart = new Date();
	/**
	 * @param dateEnd :����ʱ��
	 */
	Date dateEnd = new Date();
	/**
	 * @param hourStart :��ʼʱ���е�Сʱ
	 */
	int hourStart;
	/**
	 * @param hourEnd :����ʱ���е�Сʱ
	 */
	int hourEnd;
	
	int weekDay;//����:0,��һ:1,...����:7-1,�Ѿ����˼�1�������Զ�Ӧ
	
	String date;
	/**
	 * @param cost :��ʱ����ڷ���
	 */
	int cost;
	int costCancel=0;//��ΥԼ�ѣ�Ĭ�ϲ���
	/**
	 * @param operate :B or C, B��ʾԤ����C��ʾȡ��
	 */
	char operate;// B or C, B��ʾԤ����C��ʾȡ��
	
	/**
	 * @param info :Uid+date+spot�ı�ʶ
	 */
	String info;

	public BookInfo(String msg) throws Exception{
		// U001 2016-06-02 22:00~22:00 A
		Exception e=new Exception("> Error: the booking is invalid!");
		
		//1. ����ʽ�ж��Ƿ����
		String str = msg.trim();
		String pattern = "\\w*\\s*\\d{4}-\\d{1,2}-\\d{1,2}\\s*([0-1][0-9]|[2][0-4]):00~([0-1][0-9]|[2][0-4]):00\\s*(A|B|C|D|A\\s*C|B\\s*C|C\\s*C|D\\s*C)$";

		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		//System.out.println(m.matches());
		if(!m.matches()){
			throw e;
		}
		
		//2. ��һ���ж�ʱ���Ƿ����
		String[] s = str.split("\\s");
		String time = s[1] + " " + s[2];
		String[] t = time.split("[\\s|\\-|:|~]");
		// �õ�2016 06 02 22 00 22 00��ʽ����
		Uid = s[0];
		this.spot = s[3].charAt(0);

		if (s.length == 4) {// Ԥ��
			this.operate = 'B';
		} else if(s.length == 5){// ����Ϊ5����ʾ��߻���C��Ϊȡ��
			this.operate = 'C';
		}else{
			throw e;
		}

		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		//����monthҪ-1
		calendarStart.set(Integer.valueOf(t[0]), Integer.valueOf(t[1])-1, Integer.valueOf(t[2]), Integer.valueOf(t[3]), 0,
				0);
		calendarEnd.set(Integer.valueOf(t[0]), Integer.valueOf(t[1])-1, Integer.valueOf(t[2]), Integer.valueOf(t[5]), 0,
				0);

		
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd ");
		SimpleDateFormat df2 = new SimpleDateFormat("HH");//HH24�ƣ�hh12��
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
		//����:0,��һ:1,...����:6,�Ѿ����˼�1�������Զ�Ӧ
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
		 * ���·��ദ��cost����
		 */		 
		if(weekDay>=1&&weekDay<=5){//��1����5
			//11����ǰԤ��
			if(hourStart<=11){
				if(hourEnd<=12){//9~.~.~12
					cost = price1_5_9_12 * (hourEnd-hourStart);				
				}else if (hourEnd>=13&&hourEnd<=18) {//9~.~12~.~18
					cost = price1_5_9_12 * (12-hourStart)+price1_5_13_18*(hourEnd-12);				
				}else if(hourEnd>=19&&hourEnd<=20){//9~.~12~18~.~20����
					cost = price1_5_9_12 * (12-hourStart)+price1_5_13_18*(18-12)
							+price1_5_19_20*(hourEnd-18);
				}else if(hourEnd>=21){
					cost = price1_5_9_12*(12-hourStart)+price1_5_13_18*(18-12)
							+price1_5_19_20*(20-18)+price1_5_21_22*(hourEnd-20);
				}
			}			
			
			//12����17��Ԥ��
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
			
			//18�㵽19��Ԥ��
			if(hourStart>=18 && hourStart <=19){
				if(hourEnd<=20){
					cost = price1_5_19_20 * (hourEnd-hourStart);				
				}else if (hourEnd<=22) {
					cost = price1_5_19_20*(20-hourStart)+price1_5_21_22*(hourEnd-20);				
				}
			}
			
			//20�㵽22Ԥ��
			if(hourStart>=20){
				cost = price1_5_21_22*(hourEnd-hourStart);				
			}
		}else{//��������
			//11����ǰԤ��
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
			
			//12����17��Ԥ��
			if(hourStart>=12 && hourStart <=17){
				if(hourEnd<=18){
					cost = price6_7_13_18 * (hourEnd-hourStart);				
				}else if (hourEnd<=22) {
					cost = price6_7_13_18*(18-hourStart)+price6_7_19_22*(hourEnd-18);				
				}
			}	
			//18���Ժ�Ԥ��
			if(hourStart>=18){				
				cost = price6_7_19_22*(hourEnd-hourStart);			
			}	
			
		}
		/**
		 * END cost����
		 */
		//�ж���ȡ������Ԥ��,Ԥ��B���ù�
		if(this.operate=='C'){//ȡ��
			if(weekDay>=1||weekDay<=5){
				costCancel = (int)(cost*cancelRate1_5);
				//cost=(int)(-cost*(1-cancelRate1_5)); //�൱�ڱ��μ��븺����
			}else{
				costCancel = (int)(cost*cancelRate6_7);
				//cost=(int)(-cost*(1-cancelRate6_7)); //�൱�ڱ��μ��븺����
			}
		}
		
		//�û�id��ʱ�䣬������ΪΨһ��ʶ�������Ƿ�Ԥ����
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
		if(this.spot<o.spot){//��A��D����
			return -1;
		}else if(this.spot>o.spot){
			return 1;
		}else{//spot���ʱ
			//�Ƚ�ʱ��
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

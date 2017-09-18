package thoughtwork;

/**
 * @author WangHongbing whbing1991@gmail.com
 *
 */
public class POJOTest {
	/**
	 * ����ΪBookInfo�Ĳ��ԣ�ֻ�и�ʽ���ϲ�������BookInfo����
	*/
	public static void main(String[] args) {
		BookInfo b;
		try {
			b = new BookInfo("U001 2017-05-03 09:00~22:00 B C");
			System.out.println(b.dateStart);
			System.out.println(b.dateEnd);
			System.out.println(b.date);
			System.out.println(b.weekDay);
			System.out.println("cost:"+b.cost);
			System.out.println("info:"+b.info);
		} catch (Exception e) {
			System.out.println(e.getMessage());			
		}
	}	
}

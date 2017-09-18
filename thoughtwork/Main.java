/**
 * 1. �������ڵ����ֿ���ʱ��ӡ��Ϣ�����������BufferedReader��������Scanner��
 *    ����������ʱ������������Ϣ�� ���п���(��" ")ʱ��������Ϣ�������book.txt��ͬʱ����̨��ӡ
 *    
 * 2. �����ַ�����Ϣ��Ϊ��������BookInfo�Ĺ�������BookInfo���������Uid��ʱ��date������spot��������Ԥ��B��ȡ��C����
 *    Ψһʶ��info(info����Uid,date,spot,ֻ�����߶���ͬ(ʱ�䲿��Ϊ���ص�)������Ϊ��ͻ)��
 *    ��Ϣ��ȷ��BookInfo����������ɣ������ӡinvalid��Ϣ��
 *    ����cost��Ϊ���ӣ���Ϊ�漰���û���ʼʱ�䲻�̶�������ʱ�䲻�̶�����ʹ��ʱ�����̶����⣬����ͬʱ��η��ò�ͬ���ʶ��ֶδ���
 *    
 * 3. ����BookInfo�������list�С��ڼ�֮ǰ��Ҫ����Ψһ��ʶinfo���飬ʱ�����Ϊ�ж��Ƿ��غϡ����Ϸ�ʱ���������list��
 *    �����ӡ��Ӧ�Ĳ��Ϸ���Ϣ��
 *    
 * 4. ������ABCD�ֱ�����Ը��ķ�����Ϣ���ļ������ڿ���̨��ӡ��
 */
package thoughtwork;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author WangHongBing whbing1991@gmail.com
 *
 */
public class Main {
	/**
	 * @param listA
	 *            : ���A������Ϣ
	 * @param listB
	 *            : ���B������Ϣ
	 * @param listC
	 *            : ���C������Ϣ
	 * @param listD
	 *            : ���D������Ϣ
	 */
	static ArrayList<BookInfo> list = new ArrayList<BookInfo>();
	static ArrayList<BookInfo> listA = new ArrayList<BookInfo>();
	static ArrayList<BookInfo> listB = new ArrayList<BookInfo>();
	static ArrayList<BookInfo> listC = new ArrayList<BookInfo>();
	static ArrayList<BookInfo> listD = new ArrayList<BookInfo>();

	public static void main(String[] args) throws IOException {

		// �Ӽ��̽������룬ָ���������(�س�)
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String msg = null;
		while ((msg = br.readLine()) != null) {
			/*
			 * �����ֿ���ʱ��ʾ�ô�ӡ������Ϣ�ˣ�����saveFileItemsToTxt()�������棬����ӡ�����Ϣ
			 */
			if (msg.equals("")) {
				saveFileItemsToTxt();
				break;
			}

			/*
			 * �������ַ������浽BookInfo�����У�����ַ�����ʽ���ԣ���ӡ����
			 */
			try {
				BookInfo bookInfo = new BookInfo(msg);
				addList(bookInfo);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	/*
	 * ��֤bookinfo�е�ʱ�䡢Ԥ����ȡ�������Ƿ�Ϸ����Ϸ�����������
	 */
	static public void addList(BookInfo bookInfo) throws Exception {
		Exception eCancel = new Exception("> Error: the booking being cancelled does not exist!");
		Exception eBook = new Exception("> Error: the booking conflicts with existing bookings!");
		String okStr = "> Success: the booking is accepted!";

		if (list.isEmpty() && bookInfo.operate == 'B') {
			list.add(bookInfo);
			System.out.println(okStr);
			return;
		}

		if (bookInfo.operate == 'C') {// �������һ��ȡ������
			boolean f = false;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).info.equals(bookInfo.info) && list.get(i).operate == 'B') {// �û�id��ʱ�䣬�����������ʱ�������Ƿ�Ԥ����
					// �ҵ�����ǰ�д��ڵ�
					f = true;
					// ����ǰ��ɾ�����������β������list
					list.remove(i);
					list.add(bookInfo);
					System.out.println(okStr);
					break;
				}
			}
			// û���ҵ�Ҫȡ����
			if (!f) {
				throw eCancel;
			}
		} else if (bookInfo.operate == 'B') {// Ԥ��
			boolean flag = false;
			for (int i = 0; i < list.size() && list.get(i).operate == 'B'; i++) {// ��Ԥ����list��
				if (!(bookInfo.spot == list.get(i).spot && bookInfo.dateEnd.getTime() <= list.get(i).dateStart.getTime()
						|| bookInfo.dateStart.getTime() >= list.get(i).dateEnd.getTime())) {
					// ���ص�
					flag = true;
					throw eBook;
				}

			}
			if (!flag) {
				list.add(bookInfo);
				System.out.println(okStr);
			}

		}

	}
	
	/*
	 * ��list�еĶ���Ҫ��ĸ�ʽ���
	 */
	public static void saveFileItemsToTxt() {

		OutputStreamWriter outFile = null;
		FileOutputStream fileName;
		String fileNameStr = "book.txt";

		try {
			fileName = new FileOutputStream(fileNameStr);
			outFile = new OutputStreamWriter(fileName);

			Collections.sort(list);
			// ����4��list��A��B��C��D
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).spot == 'A') {
					listA.add(list.get(i));
				} else if (list.get(i).spot == 'B') {
					listB.add(list.get(i));
				} else if (list.get(i).spot == 'C') {
					listC.add(list.get(i));
				} else if (list.get(i).spot == 'D') {
					listD.add(list.get(i));
				}
			}
			int costSumA = 0;
			int costSumB = 0;
			int costSumC = 0;
			int costSumD = 0;

			// A����
			outFile.write("> �������" + "\r\n");
			outFile.write("> ---" + "\r\n");
			outFile.write("> ����A" + "\r\n");
			System.out.println("> �������");
			System.out.println("> ---");
			System.out.println("����:A");
			for (int i = 0; i < listA.size(); i++) {
				BookInfo b = listA.get(i);
				if (b.operate == 'B') {
					costSumA += b.cost;
					outFile.write("> " + b.date + " " + b.cost + "Ԫ" + "\r\n");
					System.out.println("> " + b.date + " " + b.cost + "Ԫ");
				} else {
					costSumA += b.costCancel;
					outFile.write("> " + b.date + " ΥԼ�� " + b.costCancel + "Ԫ" + "\r\n");
					System.out.println("> " + b.date + " ΥԼ�� " + b.costCancel + "Ԫ");
				}
			}
			outFile.write("> С��: " + costSumA + "Ԫ\r\n");
			System.out.println("> С��: " + costSumA + "Ԫ");

			// B����
			outFile.write("> \r\n");
			outFile.write("> ����:B\r\n");
			System.out.println(">");
			System.out.println("> ����:B");
			for (int i = 0; i < listB.size(); i++) {
				BookInfo b = listB.get(i);
				if (b.operate == 'B') {
					costSumB += b.cost;
					outFile.write("> " + b.date + " " + b.cost + "Ԫ" + "\r\n");
					System.out.println("> " + b.date + " " + b.cost + "Ԫ");
				} else {
					costSumB += b.costCancel;
					outFile.write("> " + b.date + " ΥԼ�� " + b.costCancel + "Ԫ" + "\r\n");
					System.out.println("> " + b.date + " ΥԼ�� " + b.costCancel + "Ԫ");
				}
			}
			outFile.write("> С��: " + costSumB + "Ԫ\r\n");
			System.out.println("> С��: " + costSumB + "Ԫ");

			// C����
			outFile.write("> \r\n");
			outFile.write("> ����:C\r\n");
			System.out.println(">");
			System.out.println("> ����:C");
			for (int i = 0; i < listC.size(); i++) {
				BookInfo b = listC.get(i);
				if (b.operate == 'B') {
					costSumC += b.cost;
					outFile.write("> " + b.date + " " + b.cost + "Ԫ" + "\r\n");
					System.out.println("> " + b.date + " " + b.cost + "Ԫ");
				} else {
					costSumC += b.costCancel;
					outFile.write("> " + b.date + " ΥԼ�� " + b.costCancel + "Ԫ" + "\r\n");
					System.out.println("> " + b.date + " ΥԼ�� " + b.costCancel + "Ԫ");
				}
			}
			outFile.write("> С��: " + costSumC + "Ԫ\r\n");
			System.out.println("> С��: " + costSumC + "Ԫ");

			// D����
			outFile.write("> \r\n");
			outFile.write("> ����:D\r\n");
			System.out.println(">");
			System.out.println("> ����:D");
			for (int i = 0; i < listD.size(); i++) {
				BookInfo b = listD.get(i);
				if (b.operate == 'B') {
					costSumD += b.cost;
					outFile.write("> " + b.date + " " + b.cost + "Ԫ" + "\r\n");
					System.out.println("> " + b.date + " " + b.cost + "Ԫ");
				} else {
					costSumD += b.costCancel;
					outFile.write("> " + b.date + " ΥԼ�� " + b.costCancel + "Ԫ" + "\r\n");
					System.out.println("> " + b.date + " ΥԼ�� " + b.costCancel + "Ԫ");
				}
			}
			outFile.write("> С��: " + costSumD + "Ԫ\r\n");
			System.out.println("> С��: " + costSumD + "Ԫ");

			// �ܼ�
			outFile.write("> ---\r\n");
			outFile.write("> �ܼ�: " + (costSumA + costSumB + costSumC + costSumD) + "Ԫ");
			System.out.println("> ---");
			System.out.println("> �ܼ�: " + (costSumA + costSumB + costSumC + costSumD) + "Ԫ");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outFile.flush();
				outFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

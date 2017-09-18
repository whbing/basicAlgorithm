/**
 * 1. 输入由于当出现空行时打印信息，故输入采用BufferedReader，不能用Scanner；
 *    当连续输入时，处理输入信息； 当有空行(即" ")时，汇总信息并输出到book.txt，同时控制台打印
 *    
 * 2. 输入字符串信息作为参数传给BookInfo的构造器，BookInfo包含输入的Uid，时间date，场地spot，操作（预定B或取消C），
 *    唯一识别info(info包含Uid,date,spot,只有三者都相同(时间部分为有重叠)，才认为冲突)。
 *    信息正确则BookInfo对象可以生成，否则打印invalid信息。
 *    消费cost较为复杂，因为涉及到用户开始时间不固定，结束时间不固定，及使用时长不固定问题，而不同时间段费用不同，故而分段处理。
 *    
 * 3. 将该BookInfo对象加入list中。在加之前，要进行唯一标识info检验，时间检验为判断是否重合。当合法时，将其加入list，
 *    否则打印对应的不合法信息。
 *    
 * 4. 按场地ABCD分别输出自个的费用信息到文件，并在控制台打印。
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
	 *            : 存放A场地信息
	 * @param listB
	 *            : 存放B场地信息
	 * @param listC
	 *            : 存放C场地信息
	 * @param listD
	 *            : 存放D场地信息
	 */
	static ArrayList<BookInfo> list = new ArrayList<BookInfo>();
	static ArrayList<BookInfo> listA = new ArrayList<BookInfo>();
	static ArrayList<BookInfo> listB = new ArrayList<BookInfo>();
	static ArrayList<BookInfo> listC = new ArrayList<BookInfo>();
	static ArrayList<BookInfo> listD = new ArrayList<BookInfo>();

	public static void main(String[] args) throws IOException {

		// 从键盘接受输入，指导键入空行(回车)
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String msg = null;
		while ((msg = br.readLine()) != null) {
			/*
			 * 当出现空行时表示该打印汇总信息了！调用saveFileItemsToTxt()方法保存，并打印输出信息
			 */
			if (msg.equals("")) {
				saveFileItemsToTxt();
				break;
			}

			/*
			 * 将输入字符串保存到BookInfo对象中，如何字符串格式不对，打印错误
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
	 * 验证bookinfo中的时间、预定或取消操作是否合法，合法则存入对象中
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

		if (bookInfo.operate == 'C') {// 如果这是一个取消操作
			boolean f = false;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).info.equals(bookInfo.info) && list.get(i).operate == 'B') {// 用户id，时间，场地作都相等时（除了是否预订）
					// 找到了以前有存在的
					f = true;
					// 将以前的删除掉，将本次操作存进list
					list.remove(i);
					list.add(bookInfo);
					System.out.println(okStr);
					break;
				}
			}
			// 没有找到要取消的
			if (!f) {
				throw eCancel;
			}
		} else if (bookInfo.operate == 'B') {// 预定
			boolean flag = false;
			for (int i = 0; i < list.size() && list.get(i).operate == 'B'; i++) {// 在预定的list中
				if (!(bookInfo.spot == list.get(i).spot && bookInfo.dateEnd.getTime() <= list.get(i).dateStart.getTime()
						|| bookInfo.dateStart.getTime() >= list.get(i).dateEnd.getTime())) {
					// 有重叠
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
	 * 将list中的对象按要求的格式输出
	 */
	public static void saveFileItemsToTxt() {

		OutputStreamWriter outFile = null;
		FileOutputStream fileName;
		String fileNameStr = "book.txt";

		try {
			fileName = new FileOutputStream(fileNameStr);
			outFile = new OutputStreamWriter(fileName);

			Collections.sort(list);
			// 定义4个list放A、B、C、D
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

			// A场地
			outFile.write("> 收入汇总" + "\r\n");
			outFile.write("> ---" + "\r\n");
			outFile.write("> 场地A" + "\r\n");
			System.out.println("> 收入汇总");
			System.out.println("> ---");
			System.out.println("场地:A");
			for (int i = 0; i < listA.size(); i++) {
				BookInfo b = listA.get(i);
				if (b.operate == 'B') {
					costSumA += b.cost;
					outFile.write("> " + b.date + " " + b.cost + "元" + "\r\n");
					System.out.println("> " + b.date + " " + b.cost + "元");
				} else {
					costSumA += b.costCancel;
					outFile.write("> " + b.date + " 违约金 " + b.costCancel + "元" + "\r\n");
					System.out.println("> " + b.date + " 违约金 " + b.costCancel + "元");
				}
			}
			outFile.write("> 小计: " + costSumA + "元\r\n");
			System.out.println("> 小计: " + costSumA + "元");

			// B场地
			outFile.write("> \r\n");
			outFile.write("> 场地:B\r\n");
			System.out.println(">");
			System.out.println("> 场地:B");
			for (int i = 0; i < listB.size(); i++) {
				BookInfo b = listB.get(i);
				if (b.operate == 'B') {
					costSumB += b.cost;
					outFile.write("> " + b.date + " " + b.cost + "元" + "\r\n");
					System.out.println("> " + b.date + " " + b.cost + "元");
				} else {
					costSumB += b.costCancel;
					outFile.write("> " + b.date + " 违约金 " + b.costCancel + "元" + "\r\n");
					System.out.println("> " + b.date + " 违约金 " + b.costCancel + "元");
				}
			}
			outFile.write("> 小计: " + costSumB + "元\r\n");
			System.out.println("> 小计: " + costSumB + "元");

			// C场地
			outFile.write("> \r\n");
			outFile.write("> 场地:C\r\n");
			System.out.println(">");
			System.out.println("> 场地:C");
			for (int i = 0; i < listC.size(); i++) {
				BookInfo b = listC.get(i);
				if (b.operate == 'B') {
					costSumC += b.cost;
					outFile.write("> " + b.date + " " + b.cost + "元" + "\r\n");
					System.out.println("> " + b.date + " " + b.cost + "元");
				} else {
					costSumC += b.costCancel;
					outFile.write("> " + b.date + " 违约金 " + b.costCancel + "元" + "\r\n");
					System.out.println("> " + b.date + " 违约金 " + b.costCancel + "元");
				}
			}
			outFile.write("> 小计: " + costSumC + "元\r\n");
			System.out.println("> 小计: " + costSumC + "元");

			// D场地
			outFile.write("> \r\n");
			outFile.write("> 场地:D\r\n");
			System.out.println(">");
			System.out.println("> 场地:D");
			for (int i = 0; i < listD.size(); i++) {
				BookInfo b = listD.get(i);
				if (b.operate == 'B') {
					costSumD += b.cost;
					outFile.write("> " + b.date + " " + b.cost + "元" + "\r\n");
					System.out.println("> " + b.date + " " + b.cost + "元");
				} else {
					costSumD += b.costCancel;
					outFile.write("> " + b.date + " 违约金 " + b.costCancel + "元" + "\r\n");
					System.out.println("> " + b.date + " 违约金 " + b.costCancel + "元");
				}
			}
			outFile.write("> 小计: " + costSumD + "元\r\n");
			System.out.println("> 小计: " + costSumD + "元");

			// 总计
			outFile.write("> ---\r\n");
			outFile.write("> 总计: " + (costSumA + costSumB + costSumC + costSumD) + "元");
			System.out.println("> ---");
			System.out.println("> 总计: " + (costSumA + costSumB + costSumC + costSumD) + "元");

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

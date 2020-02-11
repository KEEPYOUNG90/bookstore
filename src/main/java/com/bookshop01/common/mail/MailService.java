package com.bookshop01.common.mail;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

//�߰���
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.bookshop01.member.vo.MemberVO;
import com.bookshop01.order.vo.OrderVO;

@Service("mailService")
public class MailService {
	@Autowired
	private JavaMailSenderImpl mailSender;
	// private JavaMailSender mailSender;
	// @Autowired
	// private SimpleMailMessage preConfiguredMessage;

	@Async
	public void sendMail(Map receiverMap, List<OrderVO> myOrderList, MemberVO orderer) {
		Iterator<String> iterator = receiverMap.keySet().iterator();
		// �ݺ��ڸ� �̿��ؼ� ���
		while (iterator.hasNext()) {

			String key = (String) iterator.next(); // Ű ���
			System.out.print("key=" + key); // ���
			System.out.print("value=" + receiverMap.get(key)); // ���
		}

		String to, subject, body = "";
		String mailString = "";
		OrderVO orderVO = (OrderVO) myOrderList.get(0);

		// �����̰� ���� +�մ�� �� �ո����� ���� ����+
		to = find_Email(orderer.getEmail1(), orderer.getEmail2());

		subject = orderer.getMember_name() + "�� �ֹ� �����Դϴ�.";

		MimeMessage message = mailSender.createMimeMessage();
		System.out.println("mail test");
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			mailString = readHtmlFile(receiverMap, myOrderList, orderer);
			System.out.println(mailString);
			body = setOrderInfo(mailString, orderVO);
			messageHelper.setSubject(subject);
			messageHelper.setTo(to);
			messageHelper.setFrom("kimgipyung90@gmail.com", "��������");
			messageHelper.setText(body, true);
			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * create by choi 202020205 in MemberVO out String ������ ������ �����ּҸ� ����� ��ȯ�� �����̰� ����
	 * +�մ�� �� �ո����� �����帱 �غ� ������ ����+
	 **/
	private String find_Email(String email1, String email2) {
		String email = "";
		String[] emailSplit_result = email2.split(",");
		if (emailSplit_result[1].equals("non")) {
			email2 = emailSplit_result[0];
		} else {
			email2 = emailSplit_result[1];
		}

		email = email1 + "@" + email2;

		return email;
	}

	private String setOrderInfo(String mailString, OrderVO orderVO) {
		String regEx = "_order_id";
		Pattern pat = Pattern.compile(regEx);

		Matcher m = pat.matcher(mailString);
		mailString = m.replaceAll(Integer.toString(orderVO.getOrder_id()));

		regEx = "_goods_id";
		pat = Pattern.compile(regEx);
		m = pat.matcher(mailString);
		mailString = m.replaceAll(Integer.toString(orderVO.getGoods_id()));

		regEx = "_goods_fileName";
		pat = Pattern.compile(regEx);
		m = pat.matcher(mailString);
		mailString = m.replaceAll(orderVO.getGoods_fileName());

		regEx = "_goods_title";
		pat = Pattern.compile(regEx);
		m = pat.matcher(mailString);
		mailString = m.replaceAll(orderVO.getGoods_title());

		System.out.println(mailString);
		return mailString;
	}

	private String readHtmlFile(Map receiverMap, List<OrderVO> myOrderList, MemberVO orderer) {
		String mailString = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		FileInputStream fis = null;
		File file = null;
		String temp = null;
		StringBuilder sb = new StringBuilder();

		ClassPathResource resource = new ClassPathResource("template/mail.html");
		Resource resource2 = new ClassPathResource("template/mail.html");

		try {
			System.out.println("���ϻ�����::" + resource.getFile().length());
			System.out.println("����������+���ϸ�:" + resource.getURI().getPath().substring(1));
			System.out.println("URL ��ü" + resource.getURL()); // URL ��ü
			file = resource.getFile();

		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			// �Է� ��Ʈ�� ����
			FileReader filereader = new FileReader(file);
			// �Է� ���� ����
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";
			while ((line = bufReader.readLine()) != null) {
				System.out.println(line);
				if ((line.trim()).equals("<_order_list_>")) {
					sb.append(CreateOrderList(myOrderList));
					System.out.println("wwess");
					System.out.println(CreateOrderList(myOrderList));
				} else if (line.trim().equals("<_arrive_list_>")) {
					System.out.println("qqqqq");
					sb.append(createOrderInfoList(myOrderList));
					System.out.println(createOrderInfoList(myOrderList));
				} else if (line.trim().equals("<_pay_info_>")) {
					System.out.println("qqqqq");
					sb.append(createOrderPayList(myOrderList));
					System.out.println(createOrderPayList(myOrderList));
				}
				
				else {
					sb.append(line);
				}

			}
			// .readLine()�� ���� ���๮�ڸ� ���� �ʴ´�.
			bufReader.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} catch (IOException e) {
			System.out.println(e);
		}

		System.out.println("mailtest����");
		System.out.println(sb.toString());
		return sb.toString();
	}

	private String CreateOrderList(List<OrderVO> myOrderList) {
		StringBuilder sb = new StringBuilder();

		for (OrderVO orderVO : myOrderList) {
			sb//å
			.append("<TR>").append("<td>" + orderVO.getOrder_id() + "</td>").append("<TD class=\"goods_image\">")
					.append("<a href=\"http://localhost:8080/bookShop2/goods/goodsDetail.do?goods_id="
							+ orderVO.getGoods_id() + "\"/>")
					// .append("<img width=\"75\" alt=\"\"
					// src=\"http://localhost:8080/bookShop2/thumbnails.do?goods_id="+
					// orderVO.getGoods_id() + "&fileName=" + orderVO.getGoods_fileName() + "\"/>")

					.append("</a>").append("</TD>").append("<TD>").append("<h2>")
					.append("<A href=\"http://localhost:8080/bookShop2/goods/goodsDetail.do?goods_id="
							+ orderVO.getGoods_id() + "\">" + orderVO.getGoods_title() + "</A>")

					.append("</h2>").append("</TD>").append("<td>")
					.append("<h2>" + orderVO.getOrder_goods_qty() + "��<h2>").append("</td>")
					.append("<td><h2>" + (orderVO.getOrder_goods_qty() * orderVO.getGoods_sales_price())
							+ "�� (10% ����)</h2></td>")
					.append("<td><h2>0��</h2></td>")
					.append("<td><h2>" + (1500 * orderVO.getOrder_goods_qty()) + "��</h2></td>").append("<td>")
					.append("<h2>" + (orderVO.getOrder_goods_qty() * orderVO.getGoods_sales_price()) + "��</h2>")
					.append("</td>").append("<TR>");
		}
		return sb.toString();
	}
	
	private String createOrderInfoList(List<OrderVO> myOrderList) {
		StringBuilder sb = new StringBuilder();

			sb//å
			.append("<TBODY>").append("<TR class=\"dot_line\">")
			.append("<TD class=\"fixed_join\">��۹��</TD>")
			.append("<TD>").append(myOrderList.get(0).getDelivery_method()).append("</TD>")
			.append("</TR>")
			.append("<TR class=\"dot_line\">")
			.append("<TD class=\"fixed_join\">������ ��</TD>")
			.append("<TD>")
			.append(myOrderList.get(0).getReceiver_name())
			.append("</TD>")
			.append("</TR>")
			.append("<TR class=\"dot_line\">")
			.append("<TD class=\"fixed_join\">�޴�����ȣ</TD>")
			.append("<TD>")
			.append(myOrderList.get(0).getReceiver_hp1()+"-"+myOrderList.get(0).getReceiver_hp2()+"-"+myOrderList.get(0).getReceiver_hp3()+"</TD>")
			.append("</TR>")
			.append("<TR class=\"dot_line\">")
			.append("<TD class=\"fixed_join\">������ȭ(����)</TD>")
			.append("<TD>")
			.append(myOrderList.get(0).getReceiver_tel1()+"-"+myOrderList.get(0).getReceiver_tel2()+"-"+myOrderList.get(0).getReceiver_tel3()+"</TD>")
			.append("</TD>")
			.append("</TR>")
			.append("")
			.append("")
			.append("<TR class=\"dot_line\">")
			.append("<TD class=\"fixed_join\">�ּ�</TD>")
			.append("<td>")
			.append(myOrderList.get(0).getDelivery_address())
			.append("</td>>")
			.append("</TR>")
			.append("<TR class=\"dot_line\">")
			.append("<TD class=\"fixed_join\">��� �޽���</TD>")
			.append("<TD>")
			.append(myOrderList.get(0).getDelivery_message())
			.append("</TD>")
			.append("</TR>")
			.append("<TR class=\"dot_line\">")
			.append("<TD class=\"fixed_join\">���� ����</TD>")
			.append("<td>")
			.append(myOrderList.get(0).getGift_wrapping())
			.append("</td>")
			.append("</TR>")
			.append("<TBODY>");
		return sb.toString();
	}
	
	private String createOrderPayList(List<OrderVO> myOrderList) {
		StringBuilder sb = new StringBuilder();

			sb.append("<TBODY>")
			.append("<TR class=\"dot_line\">")
			.append("<TD class=\"fixed_join\">�������</TD>")
			.append("<TD>")
			.append(myOrderList.get(0).getPay_method())
			.append("</TD>")
			.append("</TR>")
			.append("<TR class=\"dot_line\">")
			.append("<TD class=\"fixed_join\">����ī��</TD>")
			.append("<TD>")
			.append(myOrderList.get(0).getCard_com_name())
			.append("</TD>")
			.append("</TR>")
			.append("<TR class=\"dot_line\">")
			.append("<TD class=\"fixed_join\">�ҺαⰣ</TD>")
			.append("<TD>")
			.append(myOrderList.get(0).getCard_pay_month())
			.append("</TD>")
			.append("</TR>")
			.append("</TBODY>");
		return sb.toString();
	}
}
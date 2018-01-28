package com.tangdi.production.mpapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ICBCSocketTest {

	public static void main(String[] args) throws UnknownHostException, IOException {
		//00503030303030303030413630303058413030343730323839323930353935353736434646363635334234463034394158333538343343303139443138433542363335383433433031394431384335423658
		//String hexString = "014330303030303030304D5330313131584441323032344231363037454144354135383532333846464239454541394534303038383032303037303230303443303230433039393131313936323132323631303031303130373835363133303030303030303030303030303030383030303030303031333032313030303132333733363332333133323332333633313330333033313330333133303337333833353336333133333434333233333330333833323332333033363333333533393339333933313336333033373339333933393339333933393337333733393339333933393339333933393339333933393339333933393338333933313335333642363335383137353434334246323446323630303030303030303030303030303030303030313030313433323332333033303330333033303331333033303330333533303330" ; 
		//732E631BFC8F199F 
		//4AA7197C0807F45B409B2534758615F4
		//00503030303030303030413630303058413030343730323839323930353935353736434646363635334234463034394158353531464441414136423135313134413136414638443034313645364638383558
		//219.146.70.118
		//00000000A6000XA00470289290595576CFF6653B4F049AX551FDAAA6B15114A16AF8D0416E6F885X
		//4AA7197C0807F45B409B2534758615F4
		//3030303030303030413630303058413030343730323839323930353935353736434646363635334234463034394158303038433439383030334642443932463030384334393830303346424439324658
		String head = "0050";
		String hexString = "3030303030303030413630304158413030343730323839323930353935353736434646363635334234463034394158434136353237323744374543433346464341363532373237443745434333464658";
		//00000000A6000XA00470289290595576CFF6653B4F049AXF8295E9F1D3B1A244EE2D9FE6A4B0DB1X
		//00000000A6000XA00470289290595576CFF6653B4F049AX24E93D7889A12ACC471305DADB5FCD10X
		//CA652727D7ECC3FFCA652727D7ECC3FF
		//F8295E9F1D3B1A244EE2D9FE6A4B0DB1
		//00000000A6000XA00470289290595576CFF6653B4F049AXCA652727D7ECC3FFCA652727D7ECC3FFX
		//690F73445965AAC5    "180.166.124.111", 8198
		//Socket socket = new Socket("180.166.124.111", 8198);
		Socket socket = new Socket("180.166.124.111", 8198);
		socket.setTcpNoDelay(true);
		//008C498003FBD92F008C498003FBD92F
		InputStream inputStream = socket.getInputStream();
		OutputStream outputStream = socket.getOutputStream();

		byte[] sendData = str2Bcd(head+hexString);
		byte[] headData = str2Bcd(head);

		byte[] receiveData = new byte[124];

		System.out.println("发送的报文为:  ");

		printHexStr(sendData);
	//	outputStream.write(headData);
		
		outputStream.write(sendData);
		outputStream.flush();

	/*	
		while(true){
			int tempRecvlen = inputStream.read(receiveData);
			if(tempRecvlen==-1){
//				System.out.println("未接收到报文");
				continue;
			}
			
			break;
		}
		System.out.println("接收的报文为:  ");
		printHexStr(receiveData);
		*/
		ByteArrayOutputStream buf = new ByteArrayOutputStream(1024);
		 byte[] recvbyte= new byte[1024];
         int tempRecvlen = 0, total = 0;
         InputStream ins = socket.getInputStream();
         while(true){
 			tempRecvlen = ins.read(recvbyte);
 			total = total + tempRecvlen;
 			if(tempRecvlen==-1){
 				continue;
 			}
 			break;
 		}
         buf.write(recvbyte, 0, total);
         byte[] data = buf.toByteArray();
         System.out.println("接收的报文为:  ");
         printHexStr(data);
		
	}

	public static void printHexStr(byte[] buf) {
		for (int i = 0; i < buf.length; i++) {
			System.out.printf("%02X ", buf[i]);
			if ((i + 1) % 16 == 0) {
				System.out.println();
			}
		}
		if (buf.length % 16 != 0) {
			System.out.println();
		}
	}

	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

}


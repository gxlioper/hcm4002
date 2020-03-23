package com.hjw.util;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
 
 
public class BMPWriter {
 
    public static void write(byte[] img, String out) {
        int fheader = 14;
        int infoheader = 40;
        int board = 0;
        int offset = fheader + infoheader + board;
        int length = img.length * 3 + offset;
        short frame = 1;
        short deep = 24;
        int fbl = 3800;
        DataOutputStream dos = null;
        try {
            FileOutputStream fos = new FileOutputStream(out);
            dos = new DataOutputStream(fos);
            dos.write('B');
            dos.write('M');// 1格式头
            wInt(dos, length);// 2-3文件大小
            wInt(dos, 0);// 4-5保留
            wInt(dos, offset);// 6-7偏移量
            wInt(dos, infoheader);// 8-9头信息
            wInt(dos, 10);// 10-11宽
            wInt(dos, 13);// 12-13高
            wShort(dos, frame);// 14 = 1帧数
            wShort(dos, deep);// 15 = 24位数
            wInt(dos, 0);// 16-17压缩
            wInt(dos, 4);// 18-19 size
            wInt(dos, fbl);// 20-21水平分辨率
            wInt(dos, fbl);// 22-23垂直分辨率
            wInt(dos, 0);// 24-25颜色索引 0为所有
            wInt(dos, 0);// 26-27重要颜色索引 0为所有
            // wInt(0);//28-35
            // wInt(0);
            // wInt(0);
            // wInt(0);//28-35彩色板
            dos.write(img);
            dos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    private static void wInt(DataOutputStream dos, int i) throws IOException {
        dos.write(i);
        dos.write(i >> 8);
        dos.write(i >> 16);
        dos.write(i >> 24);
    }
 
    private static void wShort(DataOutputStream dos, short i)
            throws IOException {
        dos.write(i);
        dos.write(i >> 8);
    }
 
}
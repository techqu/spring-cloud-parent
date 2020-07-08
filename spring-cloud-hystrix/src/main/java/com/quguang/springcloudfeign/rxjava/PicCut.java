package com.quguang.springcloudfeign.rxjava;

import org.apache.commons.codec.binary.Base64;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.ImageOutputStreamImpl;

public class PicCut {
    public void cut(double x_per,double y_per,double width_per,double height_per,String srcpath,String subpath) throws IOException {//裁剪方法
        FileInputStream is = null;
        ImageInputStream iis = null;
        try {
            is = new FileInputStream(srcpath); //读取原始图片
            String fileSuffix = getFileSuffix(srcpath);
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(fileSuffix); //ImageReader声称能够解码指定格式
            ImageReader reader = it.next();
            iis = ImageIO.createImageInputStream(is); //获取图片流
            reader.setInput(iis, true); //将iis标记为true（只向前搜索）意味着包含在输入源中的图像将只按顺序读取
            int x = (int) (reader.getWidth(0) * x_per);
            int y = (int)(reader.getHeight(0)* y_per);
            int width = (int)(reader.getHeight(0)* width_per);
            int height = (int)(reader.getHeight(0)* height_per);
            ImageReadParam param = reader.getDefaultReadParam(); //指定如何在输入时从 Java Image I/O框架的上下文中的流转换一幅图像或一组图像
            Rectangle rect = new Rectangle(x,y, width, height); //定义空间中的一个区域
            param.setSourceRegion(rect); //提供一个 BufferedImage，将其用作解码像素数据的目标。
            BufferedImage bi = reader.read(0, param); //读取索引imageIndex指定的对象
            ImageIO.write(bi, fileSuffix, new File(subpath)); //保存新图片


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bi, fileSuffix, outputStream); //保存新图片

            String base64str = Base64.encodeBase64String(outputStream.toByteArray());
            String img = "data:image/jpeg;base64,"+base64str;
            System.out.println(img);

        } finally {
            if (is != null)
                is.close();
            if (iis != null)
                iis.close();
        }
    }

    private String getFileSuffix(final String path) throws IOException {
        String result = "";
        String hex="";
        if (path != null) {
            File image=new File(path);
            InputStream is = new FileInputStream(image);
            byte[] bt = new byte[2];
            is.read(bt);
            System.out.println(bt+"\n"+bytesToHexString(bt));
            hex=bytesToHexString(bt);
            is.close();
            if(hex.equals("ffd8")){
                result="jpg";
            }else if(hex.equals("4749")){
                result="gif";
            }else if(hex.equals("8950")){
                result="png";
            }
        }

        return result;
    }
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    public static void main(String[] args) throws Exception {
        PicCut pc = new PicCut();
        pc.cut(0.5, 0.66, 0.5, 0.2,"/Users/quguang/Downloads/image.jpg","/Users/quguang/Downloads/image_sub.jpg");
        System.out.println("ok");
    }
}


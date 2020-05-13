package com.example.chat.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;

public class ImageUtil {
    // 宽度
    private static final int WIDTH = 42;
    // 高度
    private static final int HEIGHT = 42;
    // 双字节字符字体大小
    private static final int DOUBLE_FONT_SIZE = 26;
    // 单字节字符字体大小
    private static final int SINGLE_FONT_SIZE = 22;

    private static final Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");

    /**
     * 生成图片
     *
     * @throws IOException
     */
    public static void createImage(String str, String targetPath) throws IOException {
        OutputStream os = null;
        // 1.创建空白图片
        BufferedImage image = new BufferedImage(
                WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 2.获取图片画笔
        Graphics2D graphic = image.createGraphics();
        // 3.设置画笔颜色
        int r = (int) (Math.random() * 200);
        int g = (int) (Math.random() * 200);
        int b = (int) (Math.random() * 200);
        graphic.setColor(new Color(r, g, b));
        // 4.绘制矩形背景
        graphic.fillRect(0, 0, WIDTH, HEIGHT);
        // 5.绘制矩形边框
        graphic.setColor(Color.lightGray);
        graphic.drawRect(-1, -1, WIDTH + 1, HEIGHT + 1);

        graphic.setColor(Color.white);
//                    // 设置字体大小，标题需要加粗显示

        graphic.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        String s = str.charAt(0) + "";
        String s1 = str.charAt(1) + "";
        if (!pattern.matcher(s).matches() && !pattern.matcher(s1).matches()) {
            graphic.setFont(new Font(null, Font.BOLD, SINGLE_FONT_SIZE));
            graphic.drawString(s + s1, 8, 28);
        } else {
            graphic.setFont(new Font(null, Font.BOLD, DOUBLE_FONT_SIZE));
            graphic.drawString(s, 7, 30);
        }
        // 6.返回图片
        os = new FileOutputStream(targetPath);
        ImageIO.write(image, "JPG", os);
        os.close();
    }


    public static void main(String[] args) throws IOException {
        createImage("test",
                "D:\\javaProject\\chat\\src\\main\\resources/u2.jpg");
    }

}
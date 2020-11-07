package org.aliuselly.sms.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 绘制验证码图片
 */
public class CreateVerifyCodeImge {

    private static int WIDTH = 90;
    private static int HEIGHT = 35;
    private static int FONT_SZIE = 20;  // 字符大小
    private static char[] verifyCode;  // 验证码
    private static BufferedImage verifyCodeImage;  // 验证码图片

    /**
     * 获取验证码图片
     *
     * 不过呢，这个验证码呢，是没有倾斜的，是比较简单的那种
     * @return
     */
    public static BufferedImage getVerifyCodeImage()
    {
//        相当于声明，表明要画多大的图片，还有用什么颜料，但没画，是准备工作，是构想图
        verifyCodeImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);  // create a image
//        拿到了画笔，可以开始画了
        Graphics graphics = verifyCodeImage.getGraphics();

        verifyCode = generateCheckCode();
        drawBackground(graphics);
        drawRands(graphics, verifyCode);

        graphics.dispose();

        return verifyCodeImage;
    }

    /**
     * 随机生成验证码
     * @return
     */
    private static char[] generateCheckCode()
    {
        String chars = "0123456789" +
                "abcdefghijklmnopqrstuvwxyz" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] rands = new char[4];
        for(int i = 0; i < 4; i++)
        {
//            26 个字母和 10 个数字
            int rand = (int) (Math.random() * (10 + 26 * 2));
            rands[i] = chars.charAt(rand);
        }
        return rands;
    }

    /**
     * 绘制验证码
     * @param g
     * @param rands
     */
    private static void drawRands(Graphics g, char[] rands)
    {
//        https://www.cnblogs.com/zwjbb1021/p/7844539.html
//        第一个参数其实就是 familyName是字体类型，例如宋体、仿宋、Times New Roman等；其他的你懂的
        g.setFont(new Font("Console", Font.BOLD, FONT_SZIE));

        for(int i = 0; i < rands.length; i++)
        {
//            这个画笔除了可以画图形，还是可以写字的
            g.setColor(getRandomColor());
//            这个 x 的距离设置得不错，首先一开始是距离边界 10px，然后，第二个字体有两个 FONT_SIZE+10 绕过了第一个字体，还多 10px，总的是 70，还在 90 的范围内
            g.drawString("" + rands[i],
                    (i * FONT_SZIE) + 10, 25);
        }
    }

    /**
     * 绘制验证码图片背景
     * @param g
     */
    private static void drawBackground(Graphics g)
    {
//        设置画笔的颜色
        g.setColor(Color.WHITE);
//        想象一下 xy 轴即可明白了，开始画了，画了下面这么大的白色矩形
        g.fillRect(0, 0, WIDTH, HEIGHT);

//        绘制验证码干扰点
        for(int i = 0; i < 200; i++)
        {
//            random 相当于是百分比，0-1 的那种
            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);
            g.setColor(getRandomColor());
//            这里就是开始画椭圆了，然后呢，在这些点上，画一个 1×1 大小的椭圆，当然颜色也是随机的，由于上面已经画好了背景，这里是在画干扰点，在一张图上，这里还没有进行画验证码来
            g.drawOval(x, y, 1, 1);
        }
    }

    /**
     * 获取随机颜色
     * @return
     */
    private static Color getRandomColor()
    {
        Random random = new Random();
//        RGB 的值就是 3 个数字的代表的，只不过 rgb 最大值是 0-255 的，只不过这个是 bgr 而已
        return new Color(random.nextInt(220), random.nextInt(220), random.nextInt(220));
    }

    /**
     * 获取验证码
     * @return
     */
    public static char[] getVerifyCode()
    {
        return verifyCode;
    }
}

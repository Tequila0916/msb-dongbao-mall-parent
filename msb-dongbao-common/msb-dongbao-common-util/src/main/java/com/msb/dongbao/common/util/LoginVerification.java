package com.msb.dongbao.common.util;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 验证码工具类
 *
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 2020年05月27日 17时41分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Slf4j
public class LoginVerification {

    public static final String ATU_NAME = "validateRand";
    private static final  ThreadLocal<String> SRAND_THREAD_LOCAL = new ThreadLocal(){
        @Override
        protected Object initialValue() {
            return "";
        }
    };
    private static final ThreadLocalRandom THREAD_LOCAL_RANDOM = ThreadLocalRandom.current();

    public static void unloadThreadLocal(){
        SRAND_THREAD_LOCAL.remove();
    }


    //设置随机的背景颜色
    static Color getRandColor(int fc,int bc) {
        Random random = THREAD_LOCAL_RANDOM;
        if(fc>255){fc=255;}
        if(bc>255) {bc=255;}
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
    public static String getPicBase64(){
        BufferedImage pic = LoginVerification.getPic();
        //io流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //写入流中
        try {
            ImageIO.write(pic, "JPEG", baos);
        } catch (IOException e) {
            log.error("出现异常:{}",e);
        }
        //转换成字节
        byte[] bytes = baos.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        //转换成base64串
        String png_base64 = encoder.encodeBuffer(bytes).trim();
        log.info("生成的base64串 替换前:{}",png_base64);
        //删除 \r\n
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");
        log.info("生成的base64串 替换后:{}",png_base64);
        return png_base64;
    }

    public static BufferedImage getPic() {
        int width=200, height=40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g= image.getGraphics();
        Random random = THREAD_LOCAL_RANDOM;
        //设置验证码的文本
        String sRand="";

        //设置字体的大小
        Font mFont = new Font("Times New Roman", Font.PLAIN,28);

        g.setColor(getRandColor(200,250));
        g.fillRect(0, 0, width, height);
        g.setColor(new Color(102,102,102));
        g.drawRect(0, 0, width-1, height-1);
        g.setFont(mFont);

        //设置颜色
        g.setColor(getRandColor(160,200));
        for (int i=0;i<155;i++)
        {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g.drawLine(x,y,x + xl,y + yl);
        }
        for (int i = 0;i < 70;i++)
        {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(12) + 1;
            int yl = random.nextInt(6) + 1;
            g.drawLine(x,y,x - xl,y - yl);
        }
        //设置验证码的类型
        sRand = three(g);
        log.info("验证码:{}",sRand);
        SRAND_THREAD_LOCAL.set(sRand);
        g.dispose();

        return image;
    }


    /**
     * 获取验证码
     * @return
     */
    public static String getRand(){
        return SRAND_THREAD_LOCAL.get();
    }
    public static String getRandAndClear(){
        String rand = SRAND_THREAD_LOCAL.get();
        unloadThreadLocal();
        return rand;
    }

    /**
     * 英文字符加数字的验证码
     */

    private static String one(Graphics g){
        Random random = THREAD_LOCAL_RANDOM;
        String sRand = "";
        String[] letter = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z" };
        for (int i = 0; i < 4; i++) {
            String tempRand = "";
            if (random.nextBoolean()) {
                tempRand = String.valueOf(random.nextInt(10));
            } else {
                tempRand = letter[random.nextInt(25)];
                /* if (random.nextBoolean()) {// 随机将该字母变成小写
                     tempRand = tempRand.toLowerCase();
                 }  */
            }
            sRand += tempRand;
            g.setColor(new Color(20 + random.nextInt(10), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            g.drawString(tempRand,20 * i + 20, 30);
        }
        return sRand;
    }

    //英文
    private static String two(Graphics g){
        Random random = THREAD_LOCAL_RANDOM;
        String sRand = "";
        for (int i=0;i<5;i++)
        {
            int itmp = random.nextInt(26) + 65;
            char ctmp = (char)itmp;
            sRand += String.valueOf(ctmp);
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g.drawString(String.valueOf(ctmp),20*i+10,30);
        }
        return sRand;
    }
    //4个数
    private static String three(Graphics g){

        Random random = THREAD_LOCAL_RANDOM;
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand,20*i+10,30);
        }
        return sRand;
    }
    //中文 肆+？=24
    private static String four(Graphics g) {
        Random random = THREAD_LOCAL_RANDOM;

        String[] cn = {"壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾"};
        int x = random.nextInt(10) + 1;
        int y = random.nextInt(30);
        String sRand = String.valueOf(y);
        g.setFont(new Font("楷体", Font.PLAIN, 25));// 设定字体
        g.setColor(new Color(20 + random.nextInt(10), 20 + random.nextInt(110),
                20 + random.nextInt(110)));
        g.drawString(cn[x - 1], 1 * 1 + 6, 30);
        g.drawString("+", 33, 30);
        g.drawString("？", 50, 30);
        g.drawString("=", 62, 30);
        g.drawString(String.valueOf(x + y), 75, 30);
        return sRand;
    }
}

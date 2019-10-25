package com.watch.aiface.dispatch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageUtil {

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 判断图片是不是超过大小  1280x720
     * 这种方法不读取图片到java虚拟机内存
     *
     * @param f
     * @return
     */
    public static Boolean isLimit(File f, int wid, int hei, int size) {
        boolean canRead = false;
        FileInputStream is = null;
        try {
            is = new FileInputStream(f);
            canRead = processStream(is, wid, hei, size);
        } catch (IOException e) {
            return canRead;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                return canRead;
            }finally {
                return canRead;
            }
        }
    }

    private static Boolean processStream(FileInputStream is, int wid, int hei, int size) throws IOException {
        //大于规定大小也需要进行压缩
        if (is.getChannel().size() > size * 1048576) return true;
        int height;
        int width;
        String mimeType;
        int c1 = is.read();
        int c2 = is.read();
        int c3 = is.read();

        mimeType = null;
        width = height = -1;

        if (c1 == 'G' && c2 == 'I' && c3 == 'F') { // GIF
            is.skip(3);
            width = readInt(is, 2, false);
            height = readInt(is, 2, false);
            mimeType = "image/gif";
        } else if (c1 == 0xFF && c2 == 0xD8) { // JPG
            while (c3 == 255) {
                int marker = is.read();
                int len = readInt(is, 2, true);
                if (marker == 192 || marker == 193 || marker == 194) {
                    is.skip(1);
                    height = readInt(is, 2, true);
                    width = readInt(is, 2, true);
                    mimeType = "image/jpeg";
                    break;
                }
                is.skip(len - 2);
                c3 = is.read();
            }
        } else if (c1 == 137 && c2 == 80 && c3 == 78) { // PNG
            is.skip(15);
            width = readInt(is, 2, true);
            is.skip(2);
            height = readInt(is, 2, true);
            mimeType = "image/png";
        } else if (c1 == 66 && c2 == 77) { // BMP
            is.skip(15);
            width = readInt(is, 2, false);
            is.skip(2);
            height = readInt(is, 2, false);
            mimeType = "image/bmp";
        } else {
            int c4 = is.read();
            if ((c1 == 'M' && c2 == 'M' && c3 == 0 && c4 == 42)
                    || (c1 == 'I' && c2 == 'I' && c3 == 42 && c4 == 0)) { //TIFF
                boolean bigEndian = c1 == 'M';
                int ifd = 0;
                int entries;
                ifd = readInt(is, 4, bigEndian);
                is.skip(ifd - 8);
                entries = readInt(is, 2, bigEndian);
                for (int i = 1; i <= entries; i++) {
                    int tag = readInt(is, 2, bigEndian);
                    int fieldType = readInt(is, 2, bigEndian);
                    int valOffset;
                    if ((fieldType == 3 || fieldType == 8)) {
                        valOffset = readInt(is, 2, bigEndian);
                        is.skip(2);
                    } else {
                        valOffset = readInt(is, 4, bigEndian);
                    }
                    if (tag == 256) {
                        width = valOffset;
                    } else if (tag == 257) {
                        height = valOffset;
                    }
                    if (width != -1 && height != -1) {
                        mimeType = "image/tiff";
                        break;
                    }
                }
            }
        }

        if (mimeType == null) return false;
        if (width > wid || height > hei) return true;
        return false;
    }

    private static int readInt(InputStream is, int noOfBytes, boolean bigEndian) throws IOException {
        int ret = 0;
        int sv = bigEndian ? ((noOfBytes - 1) * 8) : 0;
        int cnt = bigEndian ? -8 : 8;
        for (int i = 0; i < noOfBytes; i++) {
            ret |= is.read() << sv;
            sv += cnt;
        }
        return ret;
    }

    /**
     * 等比例压缩算法：
     * 算法思想：根据压缩基数和压缩比来压缩原图，生产一张图片效果最接近原图的缩略图
     *
     * @param f      原图地址
     * @param width
     * @param height
     * @throws Exception
     */
    public static Double zipImageFile(File f, String destPath,
                                      int width, int height) {
        FileOutputStream deskImage = null;
        double scale = 1.0d;
        try {
            //读取图片
            Image src = ImageIO.read(f);
            //取差值较大的压缩
            int srcHeight = src.getHeight(null);
            int srcWidth = src.getWidth(null);
            //宽度缩略比例,高度缩略比例
            double widRatio = (double) (Math.round(width * 100 / srcWidth) / 100.0);
            double heiRatio = (double) (Math.round(height * 100 / srcHeight) / 100.0);
            //宽度缩略比例小于高度缩略比例，使用宽度缩略比例
            scale = widRatio < heiRatio ? widRatio : heiRatio;
            //缩小对应比例
            int deskHeight = new Double(srcHeight * scale).intValue();
            int deskWidth = new Double(srcWidth * scale).intValue();
            //写入新图片
            BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
            tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null); //绘制缩小后的图

            deskImage = new FileOutputStream(destPath); //输出到文件流
            String formatName = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf(".") + 1, f.getAbsolutePath().length());
            ImageIO.write(tag, formatName, new File(destPath));
        } catch (Exception e) {
            logger.info(f.getName() + "图片压缩失败!");
        } finally {
            if (deskImage != null) {
                try {
                    deskImage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return scale;
        }
    }
}

package io.github.chengscott.eigennote;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Adam on 2016/4/2.
 */
public class Findpoint {
    public ArrayList<Point> pointlist = new ArrayList();
    private int h, w;            //圖片長寬
    private Bitmap bmp;

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 0, baos);
        return baos.toByteArray();
    }

    public void FindPoint(Bitmap bitmap) {
        bmp = Bitmap.createBitmap(bitmap);
        Bitmap2Bytes(bmp);
        w = bmp.getWidth();
        h = bmp.getHeight();
        for (double start = 1.5; start < 150; start += 133.5) {
            for (int i = topix(2); i < h; i++) {
                int R = Color.red((bmp.getPixel(topix(start), i)));
                int G = Color.green((bmp.getPixel(topix(start), i)));
                int B = Color.blue((bmp.getPixel(topix(start), i)));
                int blevel = R + G + B;
                if (blevel < 400) {            //有點
                    R = getR(topix(start), i);
                    G = getG(topix(start), i);
                    B = getB(topix(start), i);
                    blevel = R + G + B;
                    if (blevel < 300) {       //點為黑色
                        Point point = new Point();
                        point.setColor(Point.Color.Black);
                        point.setX(23);
                        point.setY(i);        //傳回X,Y,寬
                        point.setWidth(topix(104));
                        int up = i;
                        for (i = up + topix(1.5); i < h; i++) {     //判斷下一個黑點
                            R = Color.red((bmp.getPixel(topix(start), i)));
                            G = Color.green((bmp.getPixel(topix(start), i)));
                            B = Color.blue((bmp.getPixel(topix(start), i)));
                            blevel = R + G + B;
                            if (blevel < 400) {
                                R = getR(topix(start), i);
                                G = getG(topix(start), i);
                                B = getB(topix(start), i);
                                blevel = R + G + B;
                            }
                            if (blevel < 300) {       //下一個黑色位置
                                point.setHeight((i - up));      //傳回高
                                pointlist.add(point);
                                i = i + topix(1.5);
                                break;
                            }
                        }
                    } else if (B > R && B > G) {      //點為藍色
                        Point point = new Point();
                        point.setColor(Point.Color.Blue);
                        point.setX(23);
                        point.setY(i);        //傳回X,Y,寬
                        point.setWidth(topix(104));
                        int up = i;
                        for (i = up + topix(1.5); i < h; i++) {     //判斷下一個藍點
                            R = Color.red((bmp.getPixel(topix(start), i)));
                            G = Color.green((bmp.getPixel(topix(start), i)));
                            B = Color.blue((bmp.getPixel(topix(start), i)));
                            blevel = R + G + B;
                            if (blevel < 400) {
                                R = getR(topix(start), i);
                                G = getG(topix(start), i);
                                B = getB(topix(start), i);
                                blevel = R + G + B;
                            }
                            if (B > R && B > G) {       //下一個藍色位置
                                point.setHeight((i - up));      //傳回高
                                pointlist.add(point);
                                i = i + topix(1.5);
                                break;
                            }
                        }
                    } else if (R > B && R > G) {      //點為紅色
                        Point point = new Point();
                        point.setColor(Point.Color.Red);
                        point.setX(23);
                        point.setY(i);        //傳回X,Y,寬
                        point.setWidth(topix(104));
                        int up = i;
                        for (i = up + topix(1.5); i < h; i++) {     //判斷下一個紅點
                            R = Color.red((bmp.getPixel(topix(start), i)));
                            G = Color.green((bmp.getPixel(topix(start), i)));
                            B = Color.blue((bmp.getPixel(topix(start), i)));
                            blevel = R + G + B;
                            if (blevel < 400) {
                                R = getR(topix(start), i);
                                G = getG(topix(start), i);
                                B = getB(topix(start), i);
                                blevel = R + G + B;
                            }
                            if (R > B && R > G) {       //下一個紅色位置
                                point.setHeight((i - up));          //傳回高
                                pointlist.add(point);
                                i = i + topix(1.5);
                                break;
                            }
                        }

                    }
                }
            }
        }
    }

    public int topix(double pix) {   //單位轉換
        return (int) (pix * 7.86);
    }

    public int topix(int pix) {   //單位轉換
        return (int) (((double) pix) * 7.86);
    }

    public int getR(int x, int y) {     //平均red值
        int sum = 0;
        for (int i = y; i > y - 5; i -= 1) {
            sum = sum + Color.red(bmp.getPixel(x, i));
        }
        return sum / 5;
    }

    public int getG(int x, int y) {       //平均green值
        int sum = 0;
        for (int i = y; i > y - 5; i -= 1) {
            sum = sum + Color.green(bmp.getPixel(x, i));
        }
        return sum / 5;
    }

    public int getB(int x, int y) {           //平均B值
        int sum = 0;
        for (int i = y; i > y - 5; i -= 1) {
            sum = sum + Color.blue(bmp.getPixel(x, i));
        }
        return sum / 5;
    }

}

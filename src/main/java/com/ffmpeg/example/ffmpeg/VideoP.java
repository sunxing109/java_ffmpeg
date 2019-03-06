package com.ffmpeg.example.ffmpeg;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 
/**
 * TODO：处理视频.（1.将视频提取成帧图片）
 *
 * @author ChenP
 */
public class VideoP {
	 
	 
    /**
     * TODO 将视频文件帧处理并以“jpg”格式进行存储。
     * 依赖FrameToBufferedImage方法：将frame转换为bufferedImage对象
     *
     * @param videoFileName
     */
    public static void grabberVideoFramer(String videoPath, String videoFileName) {
        //Frame对象
        Frame frame = null;
        //标识
        int flag = 0;
        /* 获取视频文件
         */
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoPath + "/" + videoFileName);
 
        try {
            fFmpegFrameGrabber.start();
            /*
            .getFrameRate()方法：获取视频文件信息,总帧数
             */
            int ftp = fFmpegFrameGrabber.getLengthInFrames();
            
            int keyFrame = (int) (fFmpegFrameGrabber.getFrameRate()/2);
//	            System.out.println(fFmpegFrameGrabber.grabKeyFrame());
            System.out.println("时长 " + ftp / fFmpegFrameGrabber.getFrameRate() / 60);
            System.out.println("帧率 " + fFmpegFrameGrabber.getFrameRate()+ " 帧数 ftp" + ftp);
            BufferedImage bImage = null;
 
            //视频帧图片存储路径
            File videoFramesPath = new File(videoPath+ "/" +videoFileName.substring(0, videoFileName.indexOf(".")));
            if(videoFramesPath.exists()) {
            	videoFramesPath.delete();
            } else {
            	videoFramesPath.mkdir();
            }
            
            while (flag <= ftp) {
            	
                //文件绝对路径+名字
                String fileName = videoFramesPath + "/" + String.valueOf(flag) + ".jpg";
                //文件储存对象
                File outPut = new File(fileName);
                //设置视频帧位置
                fFmpegFrameGrabber.setVideoFrameNumber(flag);
               
                
                //获取帧
                frame = fFmpegFrameGrabber.grabFrame();
                if (frame != null) {
                    ImageIO.write(FrameToBufferedImage(frame), "jpg", outPut);
                }
                flag +=12;
             }
            fFmpegFrameGrabber.stop();
        } catch (IOException E) {
        }
    }
    public static BufferedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }
    /*
        测试.....
     */
    public static void main(String[] args) {
    	 //视频文件路径
        String videoPath = "E:/testffmpeg";
        String videoFileName = "feidaou.mp4";
        grabberVideoFramer(videoPath, videoFileName);
    }


}

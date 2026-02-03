package com.an.helpers;

import com.an.driver.DriverManager;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.media.math.Rational;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class CaptureHelper extends ScreenRecorder {

    // Record with Monte Media library
    public static ScreenRecorder screenRecorder;
    private static volatile boolean isRecording = false;
    public String name;

    //Hàm xây dựng
    public CaptureHelper(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    //Hàm này bắt buộc để ghi đè custom lại hàm trong thư viên viết sẵn
    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {

        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        return new File(movieFolder, name + "-" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
    }

    // Start record video
    public static void startRecord(String methodName) {
        //Tạo thư mục để lưu file video vào
        File file = new File(SystemHelper.getCurrentDir() + PropertiesHelper.getValue("RECORDS_PATH"));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0, 0, width, height);

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        try {
            screenRecorder = new CaptureHelper(gc, captureSize, new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI), new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_MJPG, CompressorNameKey, ENCODING_AVI_MJPG, DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60), new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)), null, file, methodName);
            screenRecorder.start();
            isRecording = true;
        } catch (IOException | AWTException e) {
            isRecording = false;
            screenRecorder = null;
            System.out.println("[CaptureHelper] Failed to start recording: " + e.getMessage());
        }
    }

    // Stop record video
    public static void stopRecord() {
        if (screenRecorder == null || !isRecording) {
            return;
        }
        try {
            screenRecorder.stop();
        } catch (IOException e) {
            System.out.println("[CaptureHelper] Failed to stop recording: " + e.getMessage());
        } catch (RuntimeException re) {
            // Guard against potential NPE or illegal state inside the library
            System.out.println("[CaptureHelper] Recorder stop encountered an issue: " + re.getMessage());
        } finally {
            isRecording = false;
            screenRecorder = null;
        }
    }
    //Tạo format ngày giờ để xíu gắn vào name của screenshot hoặc record video không bị trùng tên (không bị ghi đè file)
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    public static String captureScreenshot(String screenshotName) {
        try {
            // Tạo tham chiếu đối tượng của TakesScreenshot với driver hiện tại
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            // Gọi hàm getScreenshotAs để chuyển hóa hình ảnh về dạng FILE
            File source = ts.getScreenshotAs(OutputType.FILE);
            //Kiểm tra folder nếu không tồn tại thì tạo folder
            File theDir = new File(SystemHelper.getCurrentDir() + PropertiesHelper.getValue("SCREENSHOT_PATH"));
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            // Đường dẫn file đích lưu ảnh
            String destPath = SystemHelper.getCurrentDir() + PropertiesHelper.getValue("SCREENSHOT_PATH")
                    + File.separator + screenshotName + "_" + dateFormat.format(new Date()) + ".png";
            FileHandler.copy(source, new File(destPath));
            System.out.println("Screenshot taken: " + screenshotName);
            System.out.println("Screenshot taken current URL: " + DriverManager.getDriver().getCurrentUrl());
            return destPath;
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
            return null;
        }
    }
}

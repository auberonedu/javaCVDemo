package io.github.auberonedu.javaCVDemo;

import org.bytedeco.javacv.*;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class App {
    public static void main(String[] args) {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        Java2DFrameConverter converter = new Java2DFrameConverter();

        try {
            grabber.start();

            JFrame window = new JFrame("Swing Webcam Inverted (RGB)");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel imageLabel = new JLabel();
            window.getContentPane().add(imageLabel);
            window.pack();
            window.setVisible(true);

            while (window.isDisplayable()) {
                Frame frame = grabber.grab();

                // Convert Frame -> BufferedImage (BGR)
                BufferedImage bgrImage = converter.convert(frame);

                // Create an RGB BufferedImage
                BufferedImage rgbImage = new BufferedImage(
                    bgrImage.getWidth(),
                    bgrImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB
                );
                rgbImage.getGraphics().drawImage(bgrImage, 0, 0, null);

                // Invert RGB pixels
                int width = rgbImage.getWidth();
                int height = rgbImage.getHeight();
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int pixel = rgbImage.getRGB(x, y);
                        int inverted = ~pixel;
                        rgbImage.setRGB(x, y, inverted);
                    }
                }

                // Show the updated image
                imageLabel.setIcon(new ImageIcon(rgbImage));
                window.pack();

                // Small sleep for CPU cooling
                Thread.sleep(30); // about 30 fps
            }

            grabber.stop();
            grabber.close();
            window.dispose();
            System.out.println("[INFO] Webcam and window closed cleanly.");
        } catch (Exception e) {
            System.err.println("[ERROR] Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

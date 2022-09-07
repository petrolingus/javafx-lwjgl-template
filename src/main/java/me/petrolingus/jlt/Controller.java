package me.petrolingus.jlt;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import me.petrolingus.jlt.lwjgl.Window;

import java.nio.ByteBuffer;

public class Controller {

    public Canvas canvas;

    public void initialize() {

        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();

        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
        graphicsContext2D.setFill(Color.BLACK);
        graphicsContext2D.fillRect(0, 0, width, height);

        Window window = new Window(width, height);
        new Thread(window::run).start();
        final WritableImage img = new WritableImage(width, height);

        new Thread(() -> {
            PixelWriter pw = img.getPixelWriter();
            int bpp = 4;
            int[] pixels = new int[width * height];

            while (true) {

                if (!window.isInit()) {
                    System.out.println("continue...");
                    continue;
                }

                ByteBuffer buffer = window.getScreen();
                for (int y = 0; y < width; y++) {
                    for (int x = 0; x < height; x++) {
                        int i = (x + (width * y)) * bpp;
                        int r = buffer.get(i) & 0xFF;
                        int g = buffer.get(i + 1) & 0xFF;
                        int b = buffer.get(i + 2) & 0xFF;
                        pixels[(height - (y + 1)) * width + x] = (0xFF << 24) | (r << 16) | (g << 8) | b;
                    }
                }

                pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);

            }
        }).start();

        new Thread(() -> {
            long start = System.nanoTime();
            while (true) {
                long stop = System.nanoTime();
                if (stop - start > 5_000_000) {
                    start = stop;
                    call(graphicsContext2D, img);
                }
            }
        }).start();
    }

    private void call(GraphicsContext graphicsContext, WritableImage img) {
        graphicsContext.drawImage(img, 0, 0);
    }

}

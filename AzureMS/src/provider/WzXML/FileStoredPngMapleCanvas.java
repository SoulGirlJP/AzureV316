package provider.WzXML;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import provider.MapleCanvas;

public class FileStoredPngMapleCanvas implements MapleCanvas {

    private File file;
    private int width;
    private int height;
    private BufferedImage image;

    public FileStoredPngMapleCanvas(int width, int height, File fileIn) {
        this.width = width;
        this.height = height;
        this.file = fileIn;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public BufferedImage getImage() {
        loadImageIfNecessary();
        return image;
    }

    private void loadImageIfNecessary() {
        if (image == null) {
            try {
                image = ImageIO.read(file);
                // replace the dimensions loaded from the wz by the REAL dimensions from the
                // image - should be equal tho
                width = image.getWidth();
                height = image.getHeight();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}

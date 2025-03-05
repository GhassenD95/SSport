package services.utilities;


import javafx.scene.image.Image;

import java.net.URL;

public class ImageLoader {
    private static final String BASE_DIRECTORY = "/images/";
    private static final String DEFAULT_IMAGE = "/images/notfound.jpg";

    public static Image loadImage(String subdirectory, String imageName) {
        if (imageName == null || imageName.trim().isEmpty()) {
            return loadDefaultImage();
        }

        String imagePath = BASE_DIRECTORY + subdirectory + "/" + imageName;
        URL imageUrl = ImageLoader.class.getResource(imagePath);

        // Check if the image exists; otherwise, return the default image
        if (imageUrl != null) {
            return new Image(imageUrl.toExternalForm());
        } else {
            return loadDefaultImage();
        }
    }

    private static Image loadDefaultImage() {
        URL defaultImageUrl = ImageLoader.class.getResource(DEFAULT_IMAGE);
        if (defaultImageUrl != null) {
            return new Image(defaultImageUrl.toExternalForm());
        } else {
            throw new RuntimeException("Default image not found: " + DEFAULT_IMAGE);
        }
    }
}


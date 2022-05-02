package services;

import android.graphics.Bitmap;

public class BitmapScaler {
    public static Bitmap scaleToFitWidth(Bitmap image, int width){
        float factor = width / (float)image.getWidth();
        return Bitmap.createScaledBitmap(image,width,(int)(image.getHeight()*factor),true);
    }
}

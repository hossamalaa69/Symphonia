package com.example.symphonia.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class SerialBitmap implements Serializable {

    private Bitmap bitmap;
    private transient Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
    private transient int compressQuality = 100;

    public SerialBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void recycle() {
        if (bitmap!=null && !bitmap.isRecycled()) bitmap.recycle();
    }
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(compressFormat, compressQuality, stream);

        byte[] byteArray = stream.toByteArray();

        out.writeInt(byteArray.length);
        out.write(byteArray);

    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {


        int bufferLength = in.readInt();

        byte[] byteArray = new byte[bufferLength];

        int pos = 0;
        do {
            int read = in.read(byteArray, pos, bufferLength - pos);

            if (read != -1) {
                pos += read;
            } else {
                break;
            }

        } while (pos < bufferLength);

        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, bufferLength);

    }

    public Bitmap.CompressFormat getCompressFormat() {
        return compressFormat;
    }

    public void setCompressFormat(Bitmap.CompressFormat compressFormat) {
        this.compressFormat = compressFormat;
    }

    public int getCompressQuality() {
        return compressQuality;
    }

    public void setCompressQuality(int compressQuality) {
        this.compressQuality = compressQuality;
    }
}
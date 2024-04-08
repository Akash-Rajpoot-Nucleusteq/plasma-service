package in.nucleusteq.plasma.payload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class CustomMultipartFile implements MultipartFile {
    private final byte[] imgContent;
    private final String imgName;

    public CustomMultipartFile(byte[] imgContent, String imgName) {
        this.imgContent = imgContent;
        this.imgName = imgName;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return imgName;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @SuppressWarnings("resource")
    @Override
    public void transferTo(File dest)
            throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }
}

package com.how2java.tmall.util;

import org.springframework.web.multipart.MultipartFile;

public class UnloadedImageFile {
    MultipartFile image;

    public MultipartFile getImage() {
        return image;
}

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}

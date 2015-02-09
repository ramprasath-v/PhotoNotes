package com.homeworkthree.photonotes;

public class Photos {
    private String imageName;
    private String imagePath;

    public Photos(String imageName, String imagePath) {
        this.imageName = imageName;
        this.imagePath = imagePath;
    }

    public String getImageName() {
        return imageName;
    }
    public String getImagePath() {
        return imagePath;
    }

}

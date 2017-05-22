package com.ilibed.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CloudStorage {
    private static final Cloudinary CLOUDINARY = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dko9cnthy",
            "api_key", "658716712468843",
            "api_secret", "I1JQ3sRz8wnoI56IXcHuJpSDXnQ"));


    public static String uploadFile(File file){
        try {
            Map result = CLOUDINARY.uploader().upload(file, ObjectUtils.asMap(
                    "resource_type", "auto"
            ));

            return result.get("url").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}

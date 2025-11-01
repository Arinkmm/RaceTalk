package com.racetalk.util;

import com.cloudinary.Cloudinary;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryUtil {
    private static Cloudinary cloudinary;

    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", System.getenv("CLOUDINARY_CLOUD_NAME"));
            config.put("api_key", System.getenv("CLOUDINARY_API_KEY"));
            config.put("api_secret", System.getenv("CLOUDINARY_API_SECRET"));
            cloudinary = new Cloudinary(config);
        }
        return cloudinary;
    }
}

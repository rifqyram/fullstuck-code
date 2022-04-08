package com.fullstuckcode.fullstuckcode.util;

import org.springframework.stereotype.Component;

@Component
public class Utility {

    public String toSlug(String input) {
        String slug = input.toLowerCase().trim();
        slug = slug.replaceAll("[^a-z0-9]", "-");
        slug = slug.replaceAll("-+", "-");
        slug = slug.replaceAll("^-|-$", "");
        return slug;
    }

}

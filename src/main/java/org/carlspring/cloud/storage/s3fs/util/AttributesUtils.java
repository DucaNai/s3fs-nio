package org.carlspring.cloud.storage.s3fs.util;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilities for transforming BasicFileAttributes to Map.
 */
public abstract class AttributesUtils {

    /**
     * Converts BasicFileAttributes to a Map with all attributes.
     *
     * @param attr BasicFileAttributes to transform
     * @return Map with attributes as key-value pairs
     */
    public static Map<String, Object> fileAttributeToMap(BasicFileAttributes attr) {
        return fileAttributeToMap(attr, getAllAttributes());
    }

    /**
     * Converts BasicFileAttributes to a Map containing specified attributes.
     *
     * @param attr    BasicFileAttributes to transform
     * @param filters Array of attribute names to include in the map
     * @return Map with specified attributes as key-value pairs
     */
    public static Map<String, Object> fileAttributeToMap(BasicFileAttributes attr, String[] filters) {
        Map<String, Object> result = new HashMap<>();

        for (String filter : filters) {
            filter = filter.replace("basic:", "").replace("posix:", "");
            switch (filter) {
                case "creationTime":
                    result.put("creationTime", attr.creationTime());
                    break;
                case "fileKey":
                    result.put("fileKey", attr.fileKey());
                    break;
                case "isDirectory":
                    result.put("isDirectory", attr.isDirectory());
                    break;
                case "isOther":
                    result.put("isOther", attr.isOther());
                    break;
                case "isRegularFile":
                    result.put("isRegularFile", attr.isRegularFile());
                    break;
                case "isSymbolicLink":
                    result.put("isSymbolicLink", attr.isSymbolicLink());
                    break;
                case "lastAccessTime":
                    result.put("lastAccessTime", attr.lastAccessTime());
                    break;
                case "lastModifiedTime":
                    result.put("lastModifiedTime", attr.lastModifiedTime());
                    break;
                case "size":
                    result.put("size", attr.size());
                    break;
                case "permissions":
                    if (attr instanceof PosixFileAttributes) {
                        result.put("permissions", ((PosixFileAttributes) attr).permissions());
                    }
                    break;
                case "group":
                    if (attr instanceof PosixFileAttributes) {
                        result.put("group", ((PosixFileAttributes) attr).group());
                    }
                    break;
                case "owner":
                    if (attr instanceof PosixFileAttributes) {
                        result.put("owner", ((PosixFileAttributes) attr).owner());
                    }
                    break;
            }
        }

        return result;
    }

    private static String[] getAllAttributes() {
        return new String[]{"creationTime", "fileKey", "isDirectory", "isOther", "isRegularFile",
                "isSymbolicLink", "lastAccessTime", "lastModifiedTime", "size", "permissions",
                "group", "owner"};
    }
}

package indi.qjw.mx.common.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @desc : I/O
 * @author: QJW
 * @date : 2022/10/15 11:55
 */
public class IOUtil {
    public static Properties getProperties(String path) throws Exception {
        File file = new File(path);
        try (FileInputStream fileInputStream = FileUtils.openInputStream(file)) {
            Properties pro = new Properties();
            pro.load(fileInputStream);
            return pro;
        }
    }
}

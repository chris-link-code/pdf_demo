package com.pdf;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * https://gitee.com/zherc/itext7-simple.git
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws IOException {
        log.info("java.version: " + System.getProperty("java.version"));
        log.info("java.vm.name: " + System.getProperty("java.vm.name"));
        log.info("os.arch: " + System.getProperty("os.arch"));

//        PdfUtil.createPdf();
        //PdfUtil.getPdf(new File("C:\\File\\temporary\\测试.txt"));

        String path = "C:\\Users\\chris\\Downloads\\txt";
        List<File> files = (List<File>) FileUtils.listFiles(new File(path), null, true);
        for (File f : files) {
            if (f.isFile()) {
                String originName = f.getName();
                String filename = originName;
                String cutName = filename
                        .replace(" ", "_")
                        .substring(filename.indexOf("【"), filename.length())
                        .substring(0, filename.indexOf("(") - 3)
                        .replaceAll("\\(", "")
                        .replaceAll("\\)", "")
                        .replaceAll("【", "")
                        .replaceAll("】", ".")
                        .replaceAll("\\._", ".")
                        .replaceAll("\\.\\.", ".");
                System.out.println(filename);
                System.out.println(cutName);
                /*if (originName.endsWith(".vdat")) {
                    filename = originName.replaceAll(".vdat", ".mp4");
                }
                if (originName.endsWith(".cnt")) {
                    filename = originName.replaceAll(".cnt", ".png");
                }

                if (originName.contains("K_") && originName.endsWith(".mp4")) {
                    //String filename = f.getName().replaceAll("(\\S{1,})m4a","");
                    String substring = originName;
                    try {
                        substring = originName.substring(originName.lastIndexOf('_'), originName.lastIndexOf('.'));
                        System.out.println(substring + "\r\n");
                    } catch (Exception e) {
                        System.out.println("ERROR" + originName);
                    }
                    filename = originName.replaceAll(substring, "");
                }
                System.out.println(filename + "\r\n");
                //对文件重命名
                File newFile = new File(path + File.separator + filename);
                f.renameTo(newFile);
                //输出文件改名前后变化
                //System.out.println(f.getName() + "==>" + newFile.getName());
                */
            }
        }

        log.info("end main()");
    }
}
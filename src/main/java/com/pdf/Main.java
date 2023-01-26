package com.pdf;

import com.pdf.util.PdfUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * https://gitee.com/zherc/itext7-simple.git
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws IOException {
        log.info("java.version: " + System.getProperty("java.version"));
        log.info("java.vm.name: " + System.getProperty("java.vm.name"));
        log.info("os.arch: " + System.getProperty("os.arch"));

        //PdfUtil.createPdf();
        //PdfUtil.getPdf(new File("C:\\File\\temporary\\测试.txt"));

        //String path = "C:\\Users\\chris\\Downloads\\mp3";
        //rename(path,".mp3");

        toPdf();

        log.info("end main()");
    }

    private static void toPdf() {
        long start = System.currentTimeMillis();
        int processor = Runtime.getRuntime().availableProcessors();
        log.info("processor: {}", processor);
        ExecutorService executor = Executors.newFixedThreadPool(processor);

        String path = "C:\\Users\\chris\\Downloads\\txt";
        String targetPath = "C:\\Users\\chris\\Downloads\\pdf";
        List<File> files = (List<File>) FileUtils.listFiles(new File(path), null, true);
        for (File f : files) {
            if (f.isFile()) {
                Runnable runnable = () -> {
                    try {
                        PdfUtil.getPdf(f, targetPath);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                };
                executor.execute(runnable);
            }
        }
        executor.shutdown();
        try {
            boolean awaitTermination = executor.awaitTermination(30, TimeUnit.MINUTES);
            log.info("ExecutorService awaitTermination: {}", awaitTermination);
            long end = System.currentTimeMillis();
            log.info("time: {}ms", end - start);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.info("ExecutorService isTerminated: {}", executor.isTerminated());
    }

    private static void rename(String path, String suffix) {
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
                        .replaceAll("\\._", ".")
                        .replaceAll("\\.\\.", ".");

                System.out.println(filename);
                System.out.println(cutName);

                //对文件重命名
                //File newFile = new File(path + File.separator + cutName + ".txt");
                File newFile = new File(path + File.separator + cutName + suffix);
                f.renameTo(newFile);
            }
        }
    }
}
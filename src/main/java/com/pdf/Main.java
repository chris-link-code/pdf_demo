package com.pdf;

import com.pdf.util.PdfUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * https://gitee.com/zherc/itext7-simple.git
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("java.version: " + System.getProperty("java.version"));
        log.info("java.vm.name: " + System.getProperty("java.vm.name"));
        log.info("os.arch: " + System.getProperty("os.arch"));

        PdfUtil.getPdf(new File("C:\\File\\temporary\\测试.txt"));

        log.info("end main()");
    }
}
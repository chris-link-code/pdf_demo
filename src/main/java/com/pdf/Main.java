package com.pdf;

import lombok.extern.slf4j.Slf4j;

/**
 * https://gitee.com/zherc/itext7-simple.git
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("java.version: " + System.getProperty("java.version"));
        log.info("java.vm.name: " + System.getProperty("java.vm.name"));
        log.info("os.arch: " + System.getProperty("os.arch"));


        log.info("end main()");
    }
}
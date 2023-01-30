package com.sics.sxt.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfUrl {

    private static String LIFE_URL;
    private static String TEST_LIFE_URL;
    private static String PC_URL;
    private static String TEST_PC_URL;
    private static String CURRENT_LIFE_URL;
    private static String CURRENT_PC_URL;


    @Value("${sics.life.serverAdd}")
    public void lifeUrlBuild(String LIFE_URL) {
        ConfUrl.LIFE_URL = LIFE_URL;
    }

    @Value("${sics.life.testServerAdd}")
    public void testLifeUrlBuild(String TEST_LIFE_URL) {
        ConfUrl.TEST_LIFE_URL = TEST_LIFE_URL;
    }

    /**
     * 默认生产环境
     * */
    @Value("${sics.life.serverAdd}")
    public void currentLifeBuild(String CURRENT_LIFE_URL) {
        ConfUrl.CURRENT_LIFE_URL = CURRENT_LIFE_URL;
    }


    public static String getLf() {
        return LIFE_URL;
    }
    public static String getLfTest() {
        return TEST_LIFE_URL;
    }

    public static void setCurrentLf(String CURRENT_LIFE_URL){
        ConfUrl.CURRENT_LIFE_URL = CURRENT_LIFE_URL;
    }
    public static String getCurrentLf() {
        return CURRENT_LIFE_URL;
    }

    public static String getPc() {
        return PC_URL;
    }
    public static String getPCTest() {
        return TEST_PC_URL;
    }

    public static void setCurrentPc(String CURRENT_PC_URL){
        ConfUrl.CURRENT_PC_URL = CURRENT_PC_URL;
    }
    public static String getCurrentPc() {
        return CURRENT_PC_URL;
    }


}

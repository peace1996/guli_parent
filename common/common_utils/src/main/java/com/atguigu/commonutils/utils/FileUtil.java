package com.atguigu.commonutils.utils;

import java.io.*;

public class FileUtil {

    //读取txt文件
    public void readLineTxt(String txtUrl){
        File file = new File(txtUrl);
        BufferedReader reader = null;
        String tempString = null;
        int line =1;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            while ((tempString = reader.readLine()) != null) {
                System.out.println("Line"+ line + ":" +tempString);
                line ++ ;
                //获取想要的数据
                //截取字符串获取电话号码和下载地址
                String phone = splitData(tempString, "电话号码：", "渠道号");

            }
            reader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public String splitData(String str, String strStart, String strEnd) {
         String tempStr;
         tempStr = str.substring(str.indexOf(strStart) + 1, str.lastIndexOf(strEnd));
         return tempStr;
    }
}

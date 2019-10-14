package test;

import com.avst.requestUtil.HttpRequest;

import java.util.Date;

public class Test1 {

    public static void main(String[] args) {

//        while(true){
//
//            long starttime=new Date().getTime();
//            String rr=HttpRequest.readContentFromGet_noencode("http://192.168.17.175:7382/ph/v1/xBOX_CheckStatus","unitCode=111111");
//            long endtime=new Date().getTime();
//            System.out.println(endtime-starttime+":worktime");
//            System.out.println(rr);
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

new Thread(new Runnable() {
    @Override
    public void run() {
        while(true){

            long starttime2=new Date().getTime();
            String rr2=HttpRequest.readContentFromGet_noencode("http://192.168.17.189:7382/ph/v1/phGetResult","unitCode=111111");
            long endtime2=new Date().getTime();
            System.out.println(endtime2-starttime2+":worktime");
            System.out.println(rr2);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){

                    long starttime3=new Date().getTime();
                    String rr3=HttpRequest.readContentFromGet_noencode("http://192.168.17.189:7382/ph/v1/phGetImage","unitCode=111111");
                    long endtime3=new Date().getTime();
                    System.out.println(endtime3-starttime3+":worktime");
                    System.out.println(rr3);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}

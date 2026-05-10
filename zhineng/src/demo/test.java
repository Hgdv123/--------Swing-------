package demo;

import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        // 尝试加载历史数据
        jiadian[] jds = DataManager.loadData();
        
        // 如果没有历史数据，使用默认配置
        if (jds == null) {
            jds = new jiadian[4];
            jds[0] = new tv("小米电视", true);
            jds[1] = new lamp("LED灯泡", true);
            jds[2] = new washma("美的洗衣机", false);
            jds[3] = new air("美的空调", false);
        }

   smartname smartname = demo.smartname.getInstance();
   //smartname.control(jds[0]);
        while(true) {
            smartname.printallstatus(jds);
            System.out.println("请选择控制的设备");
            Scanner sc = new Scanner(System.in);
            String command = sc.next();
            switch (command) {
                case "1":
                    smartname.control(jds[0]);
                    break;
                case "2":
                    smartname.control(jds[1]);
                    break;
                case "3":
                    smartname.control(jds[2]);
                    break;
                case "4":
                    smartname.control(jds[3]);
                    break;
                    case "exit":
                        System.out.println("退出app");
                        // 退出前保存数据
                        DataManager.saveData(jds);
                        return;
                default:
                    System.out.println("输入错误");
            }
        }
    }
}

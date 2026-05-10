package demo;

import java.io.*;

public class DataManager {
    private static final String DATA_FILE = "smart_home_data.dat";
    
    /**
     * 保存设备状态到文件
     * @param jds 设备数组
     */
    public static void saveData(jiadian[] jds) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(jds);
            System.out.println("设备状态已保存");
        } catch (IOException e) {
            System.err.println("保存数据失败: " + e.getMessage());
        }
    }
    
    /**
     * 从文件加载设备状态
     * @return 设备数组，如果文件不存在则返回null
     */
    public static jiadian[] loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("未找到历史数据，使用默认配置");
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            jiadian[] jds = (jiadian[]) ois.readObject();
            System.out.println("设备状态已加载");
            return jds;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("加载数据失败: " + e.getMessage());
            return null;
        }
    }
}

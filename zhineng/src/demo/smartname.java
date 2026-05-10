package demo;

public class smartname {
    private static final smartname INSTANCE = new smartname();
    private smartname(){}
    public static smartname getInstance(){
        return INSTANCE;
    }//单例模式
    public void control(jiadian jds){
        System.out.println(jds.getName()+"开关状态："+(jds.isStatus() ? "开" : "关"));
        System.out.println("开始您的操作");
        jds.press();
        System.out.println(jds.getName()+"开关状态已经是："+(jds.isStatus() ? "开" : "关"));
    }

public void printallstatus(jiadian[] jds){

        for (int i = 0; i < jds.length; i++){
            System.out.println((i+1)+","+jds[i].getName()+"开关状态："+(jds[i].isStatus() ? "开" : "关"));
        }
}

}

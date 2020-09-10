import java.io.*;
import java.util.*;
import java.lang.*;
public class PaperCheck {
    public static double getSimilarity(String s1,String s2){
        //计算两个句子字向量的余弦值
        Map<Character,int[]> ChMap=new HashMap<Character,int[]>();//按键排序
        for(int i=0;i<s1.length();i++){
            char c=s1.charAt(i);
            if(String.valueOf(c).matches("[\u4e00-\u9fa5]")){//是汉字
                int[] d=ChMap.get(c);//这个字在两个字符串中出现次数
                if(d!=null){
                    d[0]++;//第一个文本中字出现次数
                }else{//字第一次出现
                    d=new int[2];
                    d[0]=1;
                    d[1]=0;
                    ChMap.put(c,d);
                }
            }
        }//相同方式处理第二个字符串
        for(int i=0;i<s2.length();i++){
            char c=s2.charAt(i);
            if(String.valueOf(c).matches("[\u4e00-\u9fa5]")){
                int[] d=ChMap.get(c);
                if(d!=null){
                    d[1]++;//第二个文本中字出现次数
                }else{//字第一次出现
                    d=new int[2];
                    d[1]=1;
                    d[0]=0;
                    ChMap.put(c,d);
                }
            }
        }
        //算余弦
        double up=0,down1=0,down2=0;
        for(Map.Entry<Character,int[]>entry:ChMap.entrySet()){
            int[] d=entry.getValue();
            up+=d[0]*d[1];
            down1+=d[0]*d[0];
            down2+=d[1]*d[1];
        }
        down1=Math.sqrt(down1);
        down2=Math.sqrt(down2);
        double down=down1*down2;
        if(down==0||up==0)  return 0;
        return up/down;
    }
    public static void getSimilarity(){
        String s1,s2;
        int cnt=1;
        double sum=0;
        BufferedReader br1=null,br2=null;
        try{
            br1=new BufferedReader(new FileReader("orig.txt"));
            br2=new BufferedReader(new FileReader("orig_0.8_dis_7.txt"));
            s1=br1.readLine();
            while(s1.equals(""))  s1=br1.readLine();
            s2=br2.readLine();
            while(s2.equals(""))    s2=br2.readLine();
            sum+=getSimilarity(s1, s2);
            while(s1!=null){
                s1=br1.readLine();
                if(s1==null)    break;
                cnt++;
                while(s1.equals(""))  s1=br1.readLine();
                s2=br2.readLine();
                if(s2==null)    break;
                while(s2.equals(""))    s2=br2.readLine();
                sum+=getSimilarity(s1, s2);

            }
        }catch(IOException e){
            e.printStackTrace();
        }
        sum/=cnt;
        File f=new File("ans.txt");
        try{
            if(!f.exists()){
                f.createNewFile();
            }
            FileWriter fw=new FileWriter(f);
            fw.write("");
            fw.flush();
            fw.write(String.valueOf(sum));
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        getSimilarity();
    }
}

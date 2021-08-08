package week1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class XClassLoader extends ClassLoader{

    public static void main(String[] args){
        try{
            Object obj = new XClassLoader().findClass("Hello").newInstance();
            //obj.hello();
        }  catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (InstantiationException e){
            e.printStackTrace();
        }
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException{
        byte[] outbytes = new byte[1024];
        int len = 0;
        try{
            FileInputStream instr = new FileInputStream("Hello.xlass");
            //一次读取一个字符
            int inbyte = instr.read();
            while (inbyte != -1) {
                //System.out.println(inbyte);
                outbytes[len] = (byte) (255 - inbyte);
                inbyte = instr.read();
                len++;
            }
            instr.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();;
        } catch (IOException e){
            e.printStackTrace();;
        }
        return defineClass(name, outbytes, 0, len);
    }

}

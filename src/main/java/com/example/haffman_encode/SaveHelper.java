package com.example.haffman_encode;

import java.io.*;

public class SaveHelper {
    public static  <T>  void  save(String path, T obj) throws IOException {
        long n = System.currentTimeMillis();
        File file = new File(path);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(obj);

        oos.flush();
        System.out.println("saveFileLength: "+file.length()+" bytes");
        oos.close();
      //  System.out.println("save use time "+(System.currentTimeMillis()-n)+" mills");

    }
    @SuppressWarnings({"unchecked"})
    public static  <T>  T load(String path) throws IOException, ClassNotFoundException {
        long n = System.currentTimeMillis();
        File file = new File(path);
        System.out.println("loadFileLength: "+file.length()+" bytes");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        T t =(T)  ois.readObject();
        ois.close();
      //  System.out.println("toBytes use time "+(System.currentTimeMillis()-n)+" mills");
        return t;
    }

    public SaveHelper() {

    }

    public static  <T> byte[] toBytes(T obj)throws IOException {
        long n = System.currentTimeMillis();
        ByteArrayOutputStream opt = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(opt);
        oos.writeObject(obj);
        oos.flush();
        byte[] bytes = opt.toByteArray();
        oos.close();
     //   System.out.println("toBytes use time "+(System.currentTimeMillis()-n)+" mills");
        return bytes;
    }

    public static   <T>  T loadFromBytes(byte[]bytes) throws IOException, ClassNotFoundException {
        long n = System.currentTimeMillis();
        ByteArrayInputStream ipt = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(ipt);
        T t =(T)  ois.readObject();
        ois.close();
      //  System.out.println("loadFromBytes use time "+(System.currentTimeMillis()-n)+" mills");

        return t;
    }

}

package com.example.haffman_encode;

import com.example.haffman_encode.Pojo.CodeMapper;
import com.example.haffman_encode.Pojo.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class HaffmanEncodeApplicationTests {

    @Test
    void contextLoads() {
        String path = "C:\\Users\\SWQXDBA\\IdeaProjects\\haffman_encode\\src\\main\\resources\\static\\data.txt";
        String path2 = "C:\\Users\\SWQXDBA\\IdeaProjects\\haffman_encode\\src\\main\\resources\\static\\mapper.txt";
        String str = "问题 4) 序列化时,你希望某些成员不要序列化？你如何实现它" +
                "问题 4) 序列化时,你希望某些成员不要序列化？你如何实现它" +
                "问题 4) 序列化时,你希望某些成员不要序列化？你如何实现它" +
                "啊啊";
        System.out.println("长度: "+str.getBytes().length);
        long start = System.currentTimeMillis();
        Data data = new Data();
        data.encoder(str);
        CodeMapper mapper = data.getMapper();
        try {
            SaveHelper.save(path2,mapper);
            mapper = SaveHelper.load(path2);
            SaveHelper.save(path,data);
            System.out.println("save use " + (System.currentTimeMillis() - start) + " mills");

            start = System.currentTimeMillis();

            Data load = SaveHelper.load(path);
            load.setMapper(mapper);
       //     System.out.println("decode: "+load.decode());


            System.out.println("load use " + (System.currentTimeMillis() - start) + " mills");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
    @Test
    void contextLoads2() {
        String path = "C:\\Users\\SWQXDBA\\IdeaProjects\\haffman_encode\\src\\main\\resources\\static\\data.txt";
        String path2 = "C:\\Users\\SWQXDBA\\IdeaProjects\\haffman_encode\\src\\main\\resources\\static\\mapper.txt";



        try {


            CodeMapper mapper = SaveHelper.load(path2);
            Data load = SaveHelper.load(path);
            load.setMapper(mapper);
            //     System.out.println("decode: "+load.decode());

            System.out.println(load.decode());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}

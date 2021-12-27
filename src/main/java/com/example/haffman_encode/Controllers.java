package com.example.haffman_encode;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@RequestMapping
@Controller
public class Controllers {

    /**
     * 上传文件 压缩
     *
     * @return
     */
    @RequestMapping(value = "uploadAndCompress", method = RequestMethod.POST)
    public void upload(@RequestParam MultipartFile file, HttpServletResponse response) {
        String pathBase = "D:\\uploadFiles\\Files";
        if (!Files.exists(Path.of(pathBase))) {
            try {
                Files.createDirectory(Path.of(pathBase));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file == null || file.isEmpty()) {
            return;
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.endsWith(".txt")) {

            return;
        }


        try {
            //把上传的文件读出来 放到s中
            Path path = Path.of("D:\\uploadFiles\\Files\\" + originalFilename);
            StringWriter stringWriter = new StringWriter();
            FileReader reader = new FileReader(path.toString());
            reader.transferTo(stringWriter);


            String s = stringWriter.toString();
            //进行编码
            Data data = new Data();
            data.encoder(s);

            // 获取一个id 作为mapper的key 不考虑路径重复的问题
            long id = System.currentTimeMillis();
            data.mapKey = id + "";
            Path mapperPath = Path.of(pathBase, id + ".mapper");
            //保存mapper
            FileOutputStream fileOutputStream = new FileOutputStream(mapperPath.toString());
            fileOutputStream.write(SaveHelper.toBytes(data.getMapper()));
            fileOutputStream.close();

            //这个是压缩后的文件 需要写给用户
            byte[] dataBytes = SaveHelper.toBytes(data);

            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("压缩后的文件" + id + ".data", StandardCharsets.UTF_8));
            response.addHeader("Content-Length", "" + dataBytes.length);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");

            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(dataBytes);
            outputStream.close();
/*
            File file1 = new File(path.toString());
            FileInputStream inputStream = new FileInputStream(file1);



            response.getOutputStream().write(inputStream.readAllBytes());
            inputStream.close();*/

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

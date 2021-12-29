package com.example.haffman_encode;


import com.example.haffman_encode.Pojo.AjaxResult;
import com.example.haffman_encode.Pojo.CodeMapper;
import com.example.haffman_encode.Pojo.Data;
import com.example.haffman_encode.ViewModel.KmpResponseViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
@CrossOrigin
public class Controllers {
    String pathBase = "D:\\uploadFiles\\Files";
    @ResponseBody
    @RequestMapping(value = "kmp")
    public AjaxResult kmp(String str1,String str2) {
        KmpResponseViewModel model = new KmpResponseViewModel();
        model.next = KMP.kmpNext(str2);
        model.index = KMP.kmpSearch(str1, str2,model.next);
        return AjaxResult.success(model);

    }

    @ResponseBody
    @RequestMapping(value = "uploadAnddecompression")
    public AjaxResult decompression(@RequestParam MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return AjaxResult.error("不能上传空文件");
        }
        try {
            //对file反序列化
            System.out.println("开始解压");
            Data data = SaveHelper.loadFromBytes(file.getBytes());
            Path mapperPath = Path.of(pathBase, data.mapKey + ".mapper");
            //根据信息找到mapper
            File file1 = mapperPath.toFile();
            FileInputStream inputStream = new FileInputStream(file1);
            CodeMapper codeMapper = SaveHelper.loadFromBytes(inputStream.readAllBytes());
            inputStream.close();
            //设置mapper
            data.setMapper(codeMapper);
            //解压
            String decode = data.decode();
            System.out.println("解压成功");

            //保存解压后的文件到本地 返回url
            byte[] bytes = decode.getBytes(StandardCharsets.UTF_8);
            String deCompressedFileName = "解压后的文件" + data.mapKey + ".data";
            Path compressedFilePath = Path.of(pathBase, deCompressedFileName);
            FileOutputStream f = new FileOutputStream(compressedFilePath.toFile());
            f.write(bytes);
            f.close();

            return AjaxResult.success("成功", deCompressedFileName);



        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return AjaxResult.error("文件处理失败");
    }

    @RequestMapping(value = "downLoad")
    public void decompression(String downloadPath, HttpServletResponse response) {

        Path targetPath = Path.of(pathBase, downloadPath);
        if (!Files.exists(targetPath)) {
            return;
        }
        File file = targetPath.toFile();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = fileInputStream.readAllBytes();
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downloadPath, StandardCharsets.UTF_8));
            response.addHeader("Content-Length", "" + bytes.length);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");

            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.close();
            System.out.println("文件写回完成");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件 压缩
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "uploadAndCompress")
    public AjaxResult upload(@RequestParam MultipartFile file) {

        if (!Files.exists(Path.of(pathBase))) {
            try {
                Files.createDirectory(Path.of(pathBase));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file == null || file.isEmpty()) {
            return AjaxResult.error("不能上传空文件");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return AjaxResult.error("不能上传空文件");
        }


        try {
            //把上传的文件读出来 放到s中
            Path path = Path.of("D:\\uploadFiles\\Files\\" + originalFilename);
            StringWriter stringWriter = new StringWriter();
            InputStreamReader streamReader = new InputStreamReader(new ByteArrayInputStream(file.getBytes()));
            streamReader.transferTo(stringWriter);


            String s = stringWriter.toString();
            //进行编码
            Data data = new Data();
            data.encoder(s);
            data.fileName = file.getOriginalFilename();


            // 获取一个id 作为mapper的key 不考虑路径重复的问题
            long id = System.currentTimeMillis();
            data.mapKey = id + "";
            Path mapperPath = Path.of(pathBase, id + ".mapper");
            //保存mapper
            FileOutputStream fileOutputStream = new FileOutputStream(mapperPath.toString());
            fileOutputStream.write(SaveHelper.toBytes(data.getMapper()));
            fileOutputStream.close();
            //这个是压缩后的文件 先保存起来
            byte[] dataBytes = SaveHelper.toBytes(data);

            String compressedFileName = "压缩后的文件" + id + ".data";
            Path compressedFilePath = Path.of(pathBase, compressedFileName);
            FileOutputStream f = new FileOutputStream(compressedFilePath.toFile());
            f.write(dataBytes);
            f.close();
            return AjaxResult.success("成功", compressedFileName);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.error("文件处理失败");
    }

}

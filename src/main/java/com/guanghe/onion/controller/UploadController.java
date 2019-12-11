package com.guanghe.onion.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guanghe.onion.base.JsonUtil;
import com.guanghe.onion.dao.ApiJPA;
import com.guanghe.onion.entity.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadController {
    //    private final static Logger logger = LoggerFactory.getLogger(UploadController.class);
    @Autowired
    private ApiJPA apiJPA;

    @RequestMapping("/Uploadpage")
    public String index2() {
        return "Upload";
    }

    /**
     * 提取上传方法为公共方法
     * @param uploadDir 上传文件目录
     * @param file 上传对象
     * @throws Exception
     */
    private void executeUpload(String uploadDir,MultipartFile file) throws Exception
    {
        //文件后缀名
       // int a= file.getOriginalFilename().lastIndexOf(".");
       // String suffix = file.getOriginalFilename().substring(a);
        //上传文件名
       // String filename = UUID.randomUUID() + suffix;
        //服务器端保存的文件对象
        File serverFile = new File(uploadDir + file.getOriginalFilename());
        System.out.println("upload file: " + serverFile.getAbsolutePath());
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);
    }


    /**
     * 上传文件方法
     * @param file 前台上传的文件对象
     * @return
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public @ResponseBody String upload(HttpServletRequest request,MultipartFile file)
    {
        try {
            //上传目录地址
            //String uploadDir = request.getSession().getServletContext().getRealPath("/") +"upload/";

            //获取跟目录
            File path = new File(ResourceUtils.getURL("classpath:").getPath());

            //当前运行系统目录
            if(!path.exists()) {
                path = new File("");
            }


            File upload = new File(path.getAbsolutePath(),"upload");
            if(!upload.exists()) upload.mkdirs();
            String uploadDir =upload.getAbsolutePath()+"/";

            //调用上传方法
            executeUpload(uploadDir,file);

        }catch (Exception e)
        {
            //打印错误堆栈信息
            e.printStackTrace();
            return "上传失败";
        }

        return "上传成功";
    }
    /**
     * 解析上传的文件-- upload 页面的action指定这里。
     */
    @RequestMapping(value = "/uploadAndResolve",method = RequestMethod.POST)
    @ResponseBody
    public String uploadAndResolve(HttpSession session, MultipartFile file)
    {
        String creater = session.getAttribute("user").toString();
        String jsontext=multipartFileToText(file,"UTF-8");
        JSONObject fileObject = JSON.parseObject(jsontext);
        List<JSONObject> jsonlist=new ArrayList<JSONObject>();
        List<Api>  apiList=new ArrayList<>();
        JsonUtil.parseJsonToArray(fileObject,jsonlist);
        JsonUtil.toApiList(jsonlist, apiList, creater);

        Iterable<Api> iterable=apiList;
        apiJPA.save(iterable);
        return "上传成功";
    }

    public  String multipartFileToText(MultipartFile picFile, String charsetName) {
        // 判断是否为空
        StringBuffer text = new StringBuffer();
        if (picFile.isEmpty()) {
            return null;
        }
        try {
            InputStream inputStream = picFile.getInputStream();
            InputStreamReader is = new InputStreamReader(inputStream, charsetName);
            BufferedReader br = new BufferedReader(is);
            String tempString = null;
                while ((tempString=br.readLine()) != null) {
                    text.append(tempString);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return text.toString();
    }

   /* @PostMapping("/upload2")
    @ResponseBody
    public String singleFileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            String s= ResourceUtils.getURL("classpath:").getPath()+"upload/"+ file.getOriginalFilename();
            Path path = Paths.get(s );
            System.out.println("uploadDir:"+s);
            File dir = new File(ResourceUtils.getURL("classpath:").getPath()+"upload/");
            if(!dir.exists())
            {
                dir.mkdir();
            }
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        //return "redirect:/uploadStatus";
        return "ok";
    }
    */


    /**
     * 上传多个文件
     * @param request 请求对象
     * @param file 上传文件集合
     * @return
     */
    @RequestMapping(value = "/uploads",method = RequestMethod.POST)
    public @ResponseBody String uploads(HttpServletRequest request,MultipartFile[] file)
    {
        try {
            //上传目录地址
            String uploadDir = request.getSession().getServletContext().getRealPath("/") +"upload/";
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if(!dir.exists())
            {
                dir.mkdir();
            }
            //遍历文件数组执行上传
            for (int i =0;i<file.length;i++) {
                if(file[i] != null) {
                    //调用上传方法
                    executeUpload(uploadDir, file[i]);
                }
            }
        }catch (Exception e)
        {
            //打印错误堆栈信息
            e.printStackTrace();
            return "上传失败";
        }
        return "上传成功";
    }


}

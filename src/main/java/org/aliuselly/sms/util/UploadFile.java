package org.aliuselly.sms.util;

import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 上传文件工具类
 */
public class UploadFile {

//    存储文件上传失败的错误信息
    private static Map<String, Object> error_result = new HashMap<>();
//    存储头像的上传结果信息
    private static Map<String, Object> upload_result = new HashMap<>();

    /**
     * 校验所上传图片的大小以及格式等信息
     * @param photo
     * @param path
     * @return
     */
    private static Map<String, Object> uploadPhoto(MultipartFile photo, String path)
    {
//        限制头像大小(20M)
        int MAX_SIZE = 20971520;
//        获取图片的原始名称
        String originalName = photo.getOriginalFilename();
//        如果保存文件的路径不存在，则创建该目录
        File filePath = new File(path);
        if(!filePath.exists())
        {
            filePath.mkdirs();
        }
//        限制上传文件的大小
        if(photo.getSize() > MAX_SIZE)
        {
            error_result.put("success", false);
            error_result.put("msg", "上传的图片大小不能超过 20M ！");
            return error_result;
        }
//        限制上传的文件类型
        String[] suffixes = new String[]{".png", ".PNG", ".jpg", ".JPG", ".jpeg", ".JPEG", ".gif", ".GIF", ".bmp", ".BMP"};
        SuffixFileFilter suffixFileFilter = new SuffixFileFilter(suffixes);
        if(!suffixFileFilter.accept(new File(path + originalName)))
        {
            error_result.put("success", false);
            error_result.put("msg", "禁止上传此类型文件！请上传图片！");
            return error_result;
        }
        return null;
    }

    /**
     * (提取公共代码：提供代码的复用性)获取头像的上传结果信息
     * @param photo
     * @param dirPath
     * @param portraitPath
     * @return
     */
    public static Map<String, Object> getUploadResult(MultipartFile photo, String dirPath, String portraitPath)
    {
        if(!photo.isEmpty() && photo.getSize() > 0)
        {
//            获取图片的原始名称
            String originalName = photo.getOriginalFilename();
//            上传图片，error_result: 存储头像上传失败的错误信息
            Map<String, Object> error_result = uploadPhoto(photo, dirPath);
            if(error_result != null)
            {
                return error_result;
            }
//            使用 UUID 重命名图片名称(UUID_原始图片名称)
            String newPhotoName = UUID.randomUUID() + "_" + originalName;
//            将上传的文件保存到目标目录下
            try
            {
                photo.transferTo(new File(dirPath + newPhotoName));
                upload_result.put("success", true);
//                这个路径就是 dirPath 的那个路径来的，用来发送给前端，识别到保存图片的地址，然后，提交表单的时候，就是可以填进数据库了
                upload_result.put("portrait_path", portraitPath + newPhotoName);  // 将存储头像的项目路径返回给页面
            }
            catch (IOException e)
            {
                e.printStackTrace();
                upload_result.put("success", false);
                upload_result.put("msg", "上传失败！服务端发生异常！");
                return upload_result;
            }
        }
        else
        {
            upload_result.put("success", false);
            upload_result.put("msg", "头像上传失败！未找到指定图片！");
        }
//        这里做得不错，都是要返回的，那么就是一起返回
        return upload_result;
    }
}

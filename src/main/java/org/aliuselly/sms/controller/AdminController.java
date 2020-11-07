package org.aliuselly.sms.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.aliuselly.sms.domain.Admin;
import org.aliuselly.sms.service.AdminService;
import org.aliuselly.sms.util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器-管理管理员信息页面
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

//    注入业务对象
    @Autowired
    private AdminService adminService;

//    存储预返回页面的结果对象
    private Map<String, Object> result = new HashMap<>();

    /**
     * 跳转到管理员信息管理页面
     * @return
     */
    @GetMapping("/goAdminListView")
    public String getAdminList()
    {
        return "admin/adminList";
    }

    /**
     * 分页查询：根据管理员姓名获取指定/所有管理员信息列表
     * @param page  当前页码
     * @param rows  列表行数
     * @param username  管理员姓名
     * @return
     */
    @PostMapping("/getAdminList")
    @ResponseBody
    public Map<String, Object> getAdminList(Integer page, Integer rows, String username)
    {
//        获取查询的用户名
        Admin admin = new Admin();
        admin.setName(username);
//        设置每页的记录数    设置好开始页数，以及页面数据多少行
        PageHelper.startPage(page, rows);
//        根据姓名获取指定或所有管理员列表信息
        List<Admin> list = adminService.selectList(admin);
//        封装结果    封装好数据信息，泛型是 list 里面的类型
        PageInfo<Admin> pageInfo = new PageInfo<>(list);
//        获取总记录数
        long total = pageInfo.getTotal();
//        获取当前页面数据列表   根据 PageHelper 里面的信息，返回这页中的数据
        List<Admin> adminList = pageInfo.getList();
//        存储对象数据
        result.put("total", total);
        result.put("rows", adminList);

        return result;
    }

    /**
     * 添加管理员信息
     * @param admin
     * @return
     */
    @PostMapping("/addAdmin")
    @ResponseBody
    public Map<String, Object> addAdmin(Admin admin)
    {
//        判断用户名是否存在
        Admin user = adminService.findByName(admin.getName());
        if(user == null)
        {
            if(adminService.insert(admin) > 0)
            {
                result.put("success", true);
            }
            else
            {
                result.put("success", false);
                result.put("msg", "添加失败！服务端发生异常！");
            }
        }
        else
        {
            result.put("success", false);
            result.put("msg", "该用户名已存在！请修改后重试！");
        }
        return result;
    }

    /**
     * 更新管理员信息
     * @param admin
     * @return
     */
    @PostMapping("/editAdmin")
    @ResponseBody
    public Map<String, Object> editAdmin(Admin admin)
    {
//        需排除用户只修改账户名以外的信息  人话，不能重名
        Admin user = adminService.findByName(admin.getName());
//        解释下，这段代码，这里不能重名，上面通过 name 找到的 user 如果不是 null 的话，那么这个找出 user，有可能是自己，或者是已设置这个名字的 admin，那么看看是不是，就是通过唯一的 id 就可以看明
        if(user != null)
        {
            if(!(admin.getId().equals(user.getId())))
            {
                result.put("success", false);
                result.put("msg", "该用户名已存在！请修改后重试！");
                return result;
            }
        }
//        更新操作
        if(adminService.update(admin) > 0)
        {
            result.put("success", true);
        }
        else
        {
            result.put("success", false);
            result.put("msg", "更新失败！服务端发生异常！");
        }
        return result;
    }

    /**
     * 删除指定 id 的管理员信息
     * @param ids
     * @return
     */
    @PostMapping("/deleteAdmin")
    @ResponseBody
    public Map<String, Object> deleteAdmin(@RequestParam(value = "ids[]", required = true) Integer[] ids)
    {
        if(adminService.deleteById(ids) > 0)
        {
            result.put("success", true);
        }
        else
        {
            result.put("success", false);
        }
        return result;
    }

    /**
     * 上传头像-原理：将头像上传到项目发布目录中，通过读取数据库中的头像路径来获取头像
     *
     * 感觉这个上传图片不是很好
     * 要手动上传，而不是和表单一起提交，同时呢，上传逻辑是
     * 点击了上传，然后图片就到服务器上面了，但是呢，
     * 如果没有点击提交的话，图片还是在服务器上面
     * 同时，如果提交了，那么旧的图片没有删除
     * 就是相当于
     * @param photo
     * @param request
     * @return
     */
    @PostMapping("/uploadPhoto")
    @ResponseBody
    public Map<String, Object> uploadPhoto(MultipartFile photo, HttpServletRequest request)
    {
//        存储头像的本地目录
        final String dirPath = request.getServletContext().getRealPath("/upload/admin_portrait/");
//        存储头像的项目发布目录
        final String portraitPath = request.getServletContext().getContextPath() + "/upload/admin_portrait/";
//        返回头像的上传结果
        return UploadFile.getUploadResult(photo, dirPath, portraitPath);
    }
}

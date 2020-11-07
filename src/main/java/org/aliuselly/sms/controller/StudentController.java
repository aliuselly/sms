package org.aliuselly.sms.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.aliuselly.sms.domain.Student;
import org.aliuselly.sms.service.ClazzService;
import org.aliuselly.sms.service.StudentService;
import org.aliuselly.sms.util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器-管理学生信息页面
 */
@Controller
@RequestMapping("/student")
public class StudentController {

//    注入业务对象
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private StudentService studentService;

//    存储返回页面的数据对象
    private Map<String, Object> result = new HashMap<>();

    /**
     * 跳转到学生信息管理页面
     * @param modelAndView
     * @return
     */
    @GetMapping("/goStudentListView")
    public ModelAndView getStudentListView(ModelAndView modelAndView)
    {
//        向页面发送一个存储着 clazz 的 list 对象
        modelAndView.addObject("clazzList", clazzService.selectAll());
        modelAndView.setViewName("student/studentList");
        return modelAndView;
    }

    /**
     * 分页查询学生信息列表：根据学生与班级名查询指定/全部学生信息列表
     * @param page
     * @param rows
     * @param studetname
     * @param clazzname
     * @return
     */
    @PostMapping("/getStudentList")
    @ResponseBody
    public Map<String, Object> getStudentList(Integer page, Integer rows, String studetname, String clazzname)
    {
//        存储查询的 studentname, clazzname 信息
        Student student = new Student(studetname, clazzname);
//        设置每页的记录数
        PageHelper.startPage(page, rows);
//        根据班级与学生名获取指定或全部学生信息列表
        List<Student> list = studentService.selectList(student);
//        封装信息列表
        PageInfo<Student> pageInfo = new PageInfo<>(list);
//        获取总记录数
        long total = pageInfo.getTotal();
//        获取当前页数据列表
        List<Student> studentList = pageInfo.getList();
//        存储数据对象
        result.put("total", total);
        result.put("rows", studentList);

        return result;
    }

    /**
     * 添加学生信息
     * @param student
     * @return
     */
    @PostMapping("/addStudent")
    @ResponseBody
    public Map<String, Object> addStudent(Student student)
    {
//        判断学号是否已存在
        if(studentService.findBySno(student) != null)
        {
            result.put("success", false);
            result.put("msg", "该学号已存在！请修改后重试！");
        }
//        添加学生信息
        if(studentService.insert(student) > 0)
        {
            result.put("success", true);
        }
        else
        {
            result.put("success", false);
            result.put("msg", "添加失败！服务端发生异常！");
        }
        return result;
    }

    /**
     * 根据 id 修改指定学生信息
     * @param student
     * @return
     */
    @PostMapping("/editStudent")
    @ResponseBody
    public Map<String, Object> editStudent(Student student)
    {
//        由于数据库那边设置 学号是唯一键，因此呢，可以不用判断，不过感觉会出异常
        if(studentService.update(student) > 0)
        {
            result.put("success", true);
        }
        else
        {
            result.put("success", false);
            result.put("msg", "添加失败！服务端发生异常！");
        }
        return result;
    }

    /**
     * 根据 id 删除指定的学生信息
     * @param ids
     * @return
     */
    @PostMapping("/deleteStudent")
    @ResponseBody
    public Map<String, Object> deleteStudent(@RequestParam(value = "ids[]", required = true) Integer[] ids)
    {
        if(studentService.deleteById(ids) > 0)
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
     * @param photo
     * @param request
     * @return
     */
    @PostMapping("/uploadPhoto")
    @ResponseBody
    public Map<String, Object> uploadPhoto(MultipartFile photo, HttpServletRequest request)
    {
//        存储头像的本地目录
        final String dirPath = request.getServletContext().getRealPath("/upload/student_portrait/");
//        存储头像的项目发布目录
        final String portraitPath = request.getServletContext().getContextPath() + "/upload/student_portrait/";
//        返回头像的上传结果
        return UploadFile.getUploadResult(photo, dirPath, portraitPath);
    }
}

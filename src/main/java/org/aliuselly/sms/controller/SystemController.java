package org.aliuselly.sms.controller;

import org.aliuselly.sms.domain.Admin;
import org.aliuselly.sms.domain.LoginForm;
import org.aliuselly.sms.domain.Student;
import org.aliuselly.sms.domain.Teacher;
import org.aliuselly.sms.service.AdminService;
import org.aliuselly.sms.service.StudentService;
import org.aliuselly.sms.service.TeacherService;
import org.aliuselly.sms.util.CreateVerifyCodeImge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/system")
public class SystemController {

//    注入业务对象
    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

//    存储返回给页面的对象数据
    private Map<String, Object> result = new HashMap<>();

    /**
     * 跳转到用户登录页面
     * @return
     */
    @GetMapping("/goLogin")
    public String goLogin()
    {
        return "system/login";
    }

    /**
     * 获取并显示验证码图片
     * @param request
     * @param response
     */
    @GetMapping("/getVerifiCodeImage")
    public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response)
    {
//        验证码图片
        BufferedImage verifyCodeImage = CreateVerifyCodeImge.getVerifyCodeImage();
//        验证码
        String verifyCode = String.valueOf(CreateVerifyCodeImge.getVerifyCode());
//        将验证码图片输出到登录页面
        try
        {
            ImageIO.write(verifyCodeImage, "JPEG", response.getOutputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
//        存储验证码 session
        request.getSession().setAttribute("verifyCode", verifyCode);
    }

    /**
     * 验证用户登录信息
     * @param loginForm
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(LoginForm loginForm, HttpServletRequest request)
    {
//        校验验证码信息    证明了，getAttribute 如果没有的话，是返回 null 的
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
//        等写好了，测试下这里，如果没有的话，这个 verifyCode 应该是 null 的
//        不，看来是我错了，并不是 null 的，而是有的，因为呢，登录界面就是调用了上面的那个方法存好验证码了，同时呢，这个方法是 post 访问的，一定程度上减少了 null，但是呢，如果人家直接通过 post 来访问会空指针异常，等会，我试了，下面的 loginForm 这个空指针异常了，不过呢，如果按照正常访问的话，这个 verifyCode 不可能是 null 的，但是呢，如果是非正常访问的话，那么就是有点可能了，不过呢，非正常访问的话，一般都是 loginForm 这个 null 了，不过呢，session 中也是没有 verifyCode 存的，应该这个也会吧
//        最后的测试，果然出现了异常，在 verify 中
//        System.out.println(verifyCode + "---" + verifyCode.toString() + "---" + "".equals(verifyCode));
        /*if(verifyCode.equals(""))
        {
            System.out.println("哈哈");
        }*/
        if("".equals(verifyCode))
        {
//            注意，如果没有的话，正常情况下是 null 的，session 返回的是 null，而不是一个 "" 的字符串来的
            result.put("success", false);
            result.put("msg", "长时间未操作，会话已失效，请刷新页面后重试！");
            return result;
        }
        else if(!loginForm.getVerifyCode().equalsIgnoreCase(verifyCode))
        {
            result.put("success", false);
            result.put("msg", "验证码错误！");
            return result;
        }
//        登录完成后马上移除，不错
        request.getSession().removeAttribute("verifyCode");

//        校验用户登录信息   老实说，这里应该做下抽取，还有 mapper 中的登录方法也是，也是应该进行抽取
        switch(loginForm.getUserType())
        {
//            管理员身份
            case 1:
                try
                {
                    Admin admin = adminService.login(loginForm);  // 验证账户和密码是否存在
                    if(admin != null)
                    {
                        HttpSession session = request.getSession();   // 将用户信息存储到 session
                        session.setAttribute("userInfo", admin);
                        session.setAttribute("userType", loginForm.getUserType());
                        result.put("success", true);
                        return result;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    result.put("success", false);
                    result.put("msg", "登录失败！服务器发生异常！");
                    return result;
                }
                break;
//                学生身份
            case 2:
                try
                {
                    Student student = studentService.login(loginForm);
                    if(student != null)
                    {
                        HttpSession session = request.getSession();
                        session.setAttribute("userInfo", student);
                        session.setAttribute("userType", loginForm.getUserType());
                        result.put("success", true);
                        return result;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    result.put("success", false);
                    result.put("msg", "登录失败！服务器发生异常！");
                    return result;
                }
                break;
//               教师身份
            case 3:
                try
                {
                    Teacher teacher = teacherService.login(loginForm);
                    if(teacher != null)
                    {
                        HttpSession session = request.getSession();
                        session.setAttribute("userInfo", teacher);
                        session.setAttribute("userType", loginForm.getUserType());
                        result.put("success", true);
                        return result;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    result.put("success", false);
                    result.put("msg", "登录失败！服务器发生异常！");
                    return result;
                }
                break;
            default:
                break;
        }
//        登录失败
        result.put("success", false);
        result.put("msg", "用户名或密码错误！");
        return result;
    }

    /**
     * 跳转到系统主页面
     * @return
     */
    @GetMapping("/goSystemMainView")
    public String goSystemMainView()
    {
        return "system/main";
    }

    /**
     * 注销用户信息
     * @param request
     * @param response
     */
    @GetMapping("/loginOut")
    public void loginOut(HttpServletRequest request, HttpServletResponse response)
    {
        request.getSession().removeAttribute("userInfo");
        request.getSession().removeAttribute("userType");

//        注销后重定向到登录页面
        try
        {
            response.sendRedirect("../index.jsp");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
//        写到这里，我怀疑这哥们是不是没好好学 model 以及 return "redirect:../index.jsp" 这个方式啊
    }
}

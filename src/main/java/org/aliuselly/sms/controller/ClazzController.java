package org.aliuselly.sms.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.aliuselly.sms.domain.Clazz;
import org.aliuselly.sms.service.ClazzService;
import org.aliuselly.sms.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器-管理班级信息页面
 */
@Controller
@RequestMapping("/clazz")
public class ClazzController {

//    注入业务对象
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private GradeService gradeService;

//    存储预返回页面的数据对象
    private Map<String, Object> result = new HashMap<>();

    /**
     * 跳转到班级信息管理页面
     *
     * 对了，这个数据是用在选择年级那些数据的，并不是用来展示全部数据的
     * 而是用来作为数据的填充，以及分年级查询数据的
     * 不过呢，这里有一点做的不好，年级可以在班级里面随便进行添加
     * 仅靠约定来，而不是约束
     *
     * 我滴妈呀，他居然会用
     * @param modelAndView
     * @return
     */
    @GetMapping("/goClazzListView")
    public ModelAndView goClazzListPage(ModelAndView modelAndView)
    {
//        向页面发送一个存储着 grade 的 list 对象   注意，这个是 grade，并不是 class 这个
        modelAndView.addObject("gradeList", gradeService.selectAll());
        modelAndView.setViewName("clazz/clazzList");
        return modelAndView;
    }

    /**
     * 分页查询班级信息列表：根据班级与年级名查询指定/全部班级信息列表
     *
     * 这些数据才是用来展示使用的
     * @param page  当前页码
     * @param rows  列表行数
     * @param clazzname 班级名称
     * @param gradename 年级名称
     * @return
     */
    @PostMapping("/getClazzList")
    @ResponseBody
    public Map<String, Object> getClazzList(Integer page, Integer rows, String clazzname, String gradename)
    {
//        存储查询的 clazzName, gradeName 信息
        Clazz clazz = new Clazz(clazzname, gradename);
//        设置每页的记录数
        PageHelper.startPage(page, rows);
//        根据班级与年级名获取指定或全部班级信息列表
        List<Clazz> list = clazzService.selectList(clazz);
//        封装列表信息
        PageInfo<Clazz> pageInfo = new PageInfo<>(list);
//        获取总记录数
        long total = pageInfo.getTotal();
//        获取当前页面数据列表
        List<Clazz> clazzList = pageInfo.getList();
//        存储数据对象
        result.put("total", total);
        result.put("rows", clazzList);

        return result;
    }

    /**
     * 添加班级信息
     * @param clazz
     * @return
     */
    @PostMapping("/addClazz")
    @ResponseBody
    public Map<String, Object> addClazz(Clazz clazz)
    {
//        判断班级名是否存在   这里出现了异常，由于在后台乱改数据，这里数据库又没有做保险，即 name 是唯一的，导致明明是调用 selectOne 结果查出一堆数据，TooManyResultException 这个异常
        Clazz name = clazzService.findByName(clazz.getName());
        if(name == null)
        {
            if(clazzService.insert(clazz) > 0)
            {
                result.put("success", true);
            }
            else
            {
                result.put("success", false);
                result.put("msg", "添加失败！服务器发生异常！");
            }
        }
        else
        {
            result.put("success", false);
            result.put("msg", "班级名已存在！请修改后重试！");
        }

        return result;
    }

    /**
     * 根据 id 修改指定的班级信息
     * @param clazz
     * @return
     */
    @PostMapping("/editClazz")
    @ResponseBody
    public Map<String, Object> editClazz(Clazz clazz)
    {
//        需排除用户只修改班级名以外的信息  人话：看看修改后的班级名称有没有和别人的班级重名，注意，这里是在控制层进行控制的，数据库并没有进行限制
        Clazz c = clazzService.findByName(clazz.getName());
        if(c != null)
        {
            if(!(clazz.getId().equals(c.getId())))
            {
                result.put("success", false);
                result.put("msg", "该班级名称已存在！请修改后重试！");
                return result;
            }
        }
//        修改操作
        if(clazzService.update(clazz) > 0)
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
     * 删除指定 id 的班级信息
     *
     * 注意，这里要注意了
     * 这个 value 必须要带上 ids[] 这个 [] 的
     * 因为呢，人家前端那边是直接发送一个数组过来的，而不是字符串的
     *
     * 你可能想，上一个同样的项目没写 [] 也是没事啊，那个是因为呢，人家在前端转换成字符串了
     * 然后呢，你后端写成 Integer[] 这个，是因为 mvc 的转换器，帮你把字符串变成了数组
     *
     * 但是呢，当前端是数组了，后端接受的也是数组，那么后端的必须声明 [] 了，使用 @RequestParam 的话
     * 因为呢，这个对于 @RequestParam 是一个不能够识别的数据，因此呢，加上去了就能够识别了，然后呢，就是能够调用
     * 转换器之类的帮你封装了，这个是因为啊，之前的是参数组件管理的，但是呢，加上了 @RequestParam 之后呢
     * 是交给了 @RequestParam 管理了，因此呢，这个是必须的了
     * @param ids
     * @return
     */
    @PostMapping("/deleteClazz")
    @ResponseBody
    public Map<String, Object> deleteGrade(@RequestParam(value = "ids[]", required = true) Integer[] ids)
    {
        if(clazzService.deleteById(ids) > 0)
        {
            result.put("success", true);
        }
        else
        {
            result.put("success", false);
        }
        return result;
    }
}

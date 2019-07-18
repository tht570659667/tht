package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.UnloadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
//RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model,Page page){
        //设置每页显示几条，前面是起点，后面是每页总数，有了这句话才能实现分页！
        PageHelper.offsetPage(page.getStart(),page.getCount());
        //调用mapper中的方法，查询符合条件的category
        List<Category> cs= categoryService.list();
        //计算出category的总数(全部category)，把返回的category集合放入pageinfo计算总数
        int total = (int) new PageInfo<>(cs).getTotal();
        //把总数传给page
        page.setTotal(total);
        //将数据传给前端页面
        model.addAttribute("cs", cs);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, UnloadedImageFile uploadedImageFile) throws IOException {
        categoryService.add(c);
        /*得到工程路径 C:\Users\Administrator\IdeaProjects\tmall_ssm\target\tmall_ssm\img\category
        child:是文件名，前面是父路径后面是子路径
        */
        File file = new File(session.getServletContext().getRealPath("img/category"),c.getId()+".jpg");
        //如果父路径不存在就创建一个
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        //把文件传到指定路径
        uploadedImageFile.getImage().transferTo(file);
        //确保图片格式一定是jpg，而不仅仅是后缀名是jpg.
       // BufferedImage img = ImageUtil.change2jpg(file);
        /*调用ImageIO的静态方法，写入图片。
         这里的write和前面的transferTo并不矛盾，是因为
         1.把图片保存到服务器（transferto)
         2.将这张图片转化格式(change2jpg)转化为jpg格式，获得BufferedImage对象
         3.将转化的对象保存到服务器覆盖原有图片(write)。
         目的是为了保障目标图片一定是jpg格式
         ImageIO.write使用方法如下：
         ImageIO.write(RenderedImage,String,File)
         RenderedImage接口的子类是BufferedImage，
         因此在这里可以直接出传入BufferedImage的实例化对象，将BufferedImage对象直接写出指定输出流
         */
       // ImageIO.write(img, "jpg", file);
        //添加完成后重新查询数据并且回到首页将数据打印
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_delete")
    public String delete(int id,HttpSession session) throws IOException {
        categoryService.delete(id);
        //获得图片路径
        File file = new File(session.getServletContext().getRealPath("img/category"),id+".jpg");
        //删除图片
        file.delete();
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_edit")
    public String edit(int id,Model model) throws IOException {
        Category c= categoryService.get(id);
        model.addAttribute("c", c);
        return "admin/editCategory";
    }

    @RequestMapping("admin_category_update")
    public String update(Category c, HttpSession session, UnloadedImageFile uploadedImageFile) throws IOException {
        categoryService.update(c);
        MultipartFile image = uploadedImageFile.getImage();
        if(null!=image &&!image.isEmpty()){
            File imageFolder= new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder,c.getId()+".jpg");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }
        return "redirect:/admin_category_list";
    }

}
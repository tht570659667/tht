package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.how2java.tmall.comparator.ProductPriceComparator;
import com.how2java.tmall.comparator.ProductSaleCountComparator;
import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    NewsService newsService;

    //主页
    @RequestMapping("forehome")
    public String home(Model model) {
        List<Category> cs= categoryService.list();
        List<News> ns = newsService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
        model.addAttribute("cs", cs);
        model.addAttribute("ns",ns);
        return "fore/home";
    }

    //注册
    @RequestMapping("foreregister")
    public String register(Model model, User user, HttpServletRequest request) {
        String name =  user.getName();
        //解码转义，防止恶意注册，比如name为<script>alert("低俗内容")</script>
        name = HtmlUtils.htmlEscape(name);
        /*用request来获得注册输入的用户名也可以
        System.out.println(request.getParameter("name"));
        也可以用requestparam 如登录功能forlogin*/

        //将转义后的Name存放在user对象中。
        user.setName(name);
        //判断该用户在数据库中是否有相同名字的数据
        boolean exist = userService.isExist(name);
        if(exist){
            String m ="用户名已经被使用,不能使用";
            model.addAttribute("msg", m);
            model.addAttribute("user", null);
            return "fore/register";
        }
        userService.add(user);
        return "redirect:registerSuccessPage";
    }

    //登录
    @RequestMapping("forelogin")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password, Model model, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name,password);
        if(null==user){
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        }
        session.setAttribute("user", user);
        return "redirect:forehome";
    }

    //退出登录
    @RequestMapping("forelogout")
    public String logout( HttpSession session) {
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    //商品详细
    @RequestMapping("foreproduct")
    public String product( int pid, Model model) {
        Product p = productService.get(pid);
        //根据对象p，获取这个产品对应的单个图片集合
        List<ProductImage> productSingleImages = productImageService.list(p.getId(), ProductImageService.type_single);
        //根据对象p，获取这个产品对应的详情图片集合
        List<ProductImage> productDetailImages = productImageService.list(p.getId(), ProductImageService.type_detail);
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);
        //获取产品的所有属性值
        List<PropertyValue> pvs = propertyValueService.list(p.getId());
        //获取产品对应的所有的评价
        List<Review> reviews = reviewService.list(p.getId());
        //设置产品的销量和评价数量
        productService.setSaleAndReviewNumber(p);
        //把上述取值放在request属性上
        model.addAttribute("reviews", reviews);
        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
        return "fore/product";
    }

    //产品分类
    @RequestMapping("forecategory")
    public String category(int cid,String sort, Model model) {
        Category c = categoryService.get(cid);
        //为分类c填充商品
        productService.fill(c);
        //为产品填充销量和评价数据
        productService.setSaleAndReviewNumber(c.getProducts());
        /*
        * 获取参数sort
          如果sort==null，即不排序
          如果sort!=null，则根据sort的值，从2个Comparator比较器中选择一个对应的排序器进行排序
        * */
        if(null!=sort){
            switch(sort){
                case "saleCount" :
                    Collections.sort(c.getProducts(),new ProductSaleCountComparator());
                    break;

                case "price":
                    Collections.sort(c.getProducts(),new ProductPriceComparator());
                    break;
            }
        }
        model.addAttribute("c", c);
        return "fore/category";
    }

    //检查是否登录
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin( HttpSession session) {
        User user =(User)  session.getAttribute("user");
        if(null!=user)
            return "success";
        return "fail";
    }

    //将用户信息存储在session中
    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name") String name, @RequestParam("password") String password,HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name,password);

        if(null==user){
            return "fail";
        }
        session.setAttribute("user", user);
        return "success";
    }

    //产品搜索
    @RequestMapping("foresearch")
    public String search(String keyword,Model model)
    {
        PageHelper.offsetPage(0,20);
        //根据用户输入的关键字，获取满足条件的前20个产品
        List<Product> ps = productService.search(keyword);
        //为这些产品设置销量和评价数量
        productService.setSaleAndReviewNumber(ps);
        model.addAttribute("ps",ps);
        return "fore/searchResult";
    }


    //获得订单项ID，OrderItem(订单项),Order订单
    @RequestMapping("forebuyone")
    public String buyone(int pid, int num, HttpSession session) {
        //根据pid获得产品
        Product p = productService.get(pid);
        //订单项ID
        int oiid = 0;
        //从session中获得用户user
        User user =(User)  session.getAttribute("user");
        /*
        * 如果已经存在这个产品对应的OrderItem，并且还没有生成订单，即还在购物车中。 那么就应该在对应的OrderItem基础上，调整数量
          基于用户对象user，查询没有生成订单的订单项集合
          遍历这个集合
          如果产品是一样的话，就进行数量追加
          获取这个订单项的 id
        * */
        boolean found = false;
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            //如果订单项里的商品ID和用户选择的茶品ID相同，就只需要添加数量
            if(oi.getProduct().getId()==p.getId()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                //将参数设置为true，表示只添加数量即可。
                found = true;
                oiid = oi.getId();
                break;
            }
        }

        /*
        * 如果不存在对应的OrderItem,那么就新增一个订单项OrderIt
        * 生成新的订单项
          设置数量，用户和产品
          插入到数据库
         获取这个订单项的 id
        * */
        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            orderItemService.add(oi);
            oiid = oi.getId();
        }
        return "redirect:forebuy?oiid="+oiid;
    }

    //订单结算
    @RequestMapping("forebuy")
    public String buy( Model model,int[] oiid,HttpSession session){
        //因为在购物车页面cart.jsp连续传了几个oi.id，所以控制器会自动用数组来存储这些数据
        List<OrderItem> ois = new ArrayList<>();
        float total = 0;
        for (int strid : oiid) {
            OrderItem oi= orderItemService.get(strid);
            total +=oi.getProduct().getPromotePrice()*oi.getNumber();
            ois.add(oi);
        }
        session.setAttribute("ois", ois);
        model.addAttribute("total", total);
        return "fore/buy";
    }

    //订单修改
    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem( Model model,HttpSession session, int pid, int number) {
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return "fail";
        //获得订单项
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            //如果订单项的产品和该商品相同，就加减数量。
            if(oi.getProduct().getId()==pid){
                oi.setNumber(number);
                //订单项数据更新
                orderItemService.update(oi);
                break;
            }
        }
        return "success";
    }

    //加入购物车
    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num, Model model,HttpSession session) {
        Product p = productService.get(pid);
        User user =(User)  session.getAttribute("user");
        boolean found = false;
        //获得用户订单项
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            //如果用户订单项的商品和该商品是一致的，就加数量，并且把该订单项更新。
            if(oi.getProduct().getId()==p.getId()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                break;
            }
        }
        //如果订单项没有这个商品，就新建订单项。
        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            orderItemService.add(oi);
        }
        return "success";
    }

    //查看购物车
    @RequestMapping("forecart")
    public String cart( Model model,HttpSession session) {
        //获得用户信息
        User user =(User)  session.getAttribute("user");
        //获得用户订单项
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        model.addAttribute("ois", ois);
        return "fore/cart";
    }

    //创建订单
    @RequestMapping("forecreateOrder")
    public String createOrder( Model model,Order order,HttpSession session){
        //登录的时候会把用户信息存放在session
        User user =(User)  session.getAttribute("user");
        //生成订单编码，时间+随机数
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        //设置订单编码
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        //状态设置为等待支付
        order.setStatus(OrderService.waitPay);
        List<OrderItem> ois= (List<OrderItem>)  session.getAttribute("ois");
        //把订单添加到数据库
        float total =orderService.add(order,ois);
        return "redirect:forealipay?oid="+order.getId() +"&total="+total;
    }

    //填充订单项
    @RequestMapping("forebought")
    public String bought( Model model,HttpSession session) {
        User user =(User)  session.getAttribute("user");
        //获得未被删除的订单
        List<Order> os= orderService.list(user.getId(),OrderService.delete);
        //给订单填充订单项
        orderItemService.fill(os);
        model.addAttribute("os", os);
        return "fore/bought";
    }

    //等待确认收货
    @RequestMapping("foreconfirmPay")
    public String confirmPay( Model model,int oid) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        model.addAttribute("o", o);
        return "fore/confirmPay";
    }

    //确认收货成功
    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed( Model model,int oid) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.waitReview);
        o.setConfirmDate(new Date());
        orderService.update(o);
        return "fore/orderConfirmed";
    }

    //删除订单项
    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem( Model model,HttpSession session,int oiid){
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return "fail";
        orderItemService.delete(oiid);
        return "success";
    }

    //删除订单
    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder( Model model,int oid){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.delete);
        orderService.update(o);
        return "success";
    }

    //支付页面
    @RequestMapping("forepayed")
    public String payed(int oid, float total, Model model) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        model.addAttribute("o", order);
        return "fore/payed";
    }

    //评价
    @RequestMapping("forereview")
    public String review( Model model,int oid) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        Product p = o.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.list(p.getId());
        productService.setSaleAndReviewNumber(p);
        model.addAttribute("p", p);
        model.addAttribute("o", o);
        model.addAttribute("reviews", reviews);
        return "fore/review";
    }

    //提交评价
    @RequestMapping("foredoreview")
    public String doreview( Model model,HttpSession session,@RequestParam("oid") int oid,@RequestParam("pid") int pid,String content) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.finish);
        orderService.update(o);
        Product p = productService.get(pid);
        content = HtmlUtils.htmlEscape(content);
        User user =(User)  session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setPid(pid);
        review.setCreateDate(new Date());
        review.setUid(user.getId());
        reviewService.add(review);
        return "redirect:forereview?oid="+oid+"&showonly=true";
    }

    //跳转到新闻内容页
    @RequestMapping("forenews")
    public String news(Model model,@RequestParam int id)
    {
        News n =newsService.get(id);
        model.addAttribute("n",n);
        return "fore/news";
    }
}





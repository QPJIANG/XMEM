package demo.pqjiang.controller;

import demo.pqjiang.base.BaseController;
import demo.pqjiang.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexPageController extends BaseController {


    /**
     * uri: index
     * return type: view
     * parameter: empty
     *
     * */
    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("holder","qpjiang");
        return "index";
    }

    /**
     * uri: login
     * return type: view
     * parameter: empty
     *
     * */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("loginError", false);
        return "login";
    }
    /**
     * jump to index
     * */
    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }
    /**
     * login error jump
     *
     * */
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登陆失败，用户名或者密码错误！");
        return "login";
    }

}

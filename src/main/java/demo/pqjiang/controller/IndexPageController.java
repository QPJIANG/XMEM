package demo.pqjiang.controller;

import demo.pqjiang.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * index
 * login
 * logout
 * login error
 **/


@Controller
public class IndexPageController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(IndexPageController.class);

    /**
     * jump to index
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    /**
     * uri: index
     * return type: view
     * parameter: empty
     */

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("holder", "qpjiang");
        return "index";
    }

    /**
     * uri: login
     * return type: view
     * parameter: empty
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logout(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }


    /**
     * login error jump
     */
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登陆失败，用户名或者密码错误！");
        return "login";
    }

}

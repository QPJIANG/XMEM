package demo.pqjiang.controller;

import demo.pqjiang.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @RequestMapping("/")
    public String index(Model model) {
        return "user/index";
    }

}

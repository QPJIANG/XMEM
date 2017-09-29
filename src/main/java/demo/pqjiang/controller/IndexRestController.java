package demo.pqjiang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiu on 17-9-27.
 */
@RestController
public class IndexController {
    @RequestMapping("/indexjson")
    @ResponseBody
    public Map indexJson(){
        HashMap<String,Object> resultData= new HashMap<>();
        resultData.put("key_index","value_index");

        return resultData;
    }
}

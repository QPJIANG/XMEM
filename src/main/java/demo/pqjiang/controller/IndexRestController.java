package demo.pqjiang.controller;

import demo.pqjiang.base.BaseController;
import demo.pqjiang.service.IndexService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * some test json response
 *
 */
@RestController
//@EnableAutoConfiguration
public class IndexRestController  extends BaseController {


    @Autowired
    IndexService indexService;

    /**
     * uri: indexjson
     * return type: json
     * parameter: empty
     * */
    @RequestMapping("/indexjson")
    @ResponseBody
    public Map indexJson(){
        HashMap<String,Object> resultData= new HashMap<>();
        resultData.put("key_index","value_index");
        return resultData;
    }


    /**
     * uri: indexjson/{id}
     * return type: json
     * parameter: {id}
     * */
    @RequestMapping("/indexjson/{id}")
    @ResponseBody
    public Map indexJosn1(@PathVariable int id){
        HashMap<String,Object> resultData= new HashMap<>();
        resultData.put("parameter_id",id);
        resultData.put("data",indexService.UserList());
        return resultData;
    }
    /**
     * uri: indexjson/id
     * return type: json
     * parameter: id
     * */
    @RequestMapping("/indexjson/id")
    @ResponseBody
    public Map indexJosnB(int id){
        HashMap<String,Object> resultData= new HashMap<>();
        resultData.put("parameter_id",id);
        return resultData;
    }
    /**
     * uri: indexjson/id
     * return type: json
     * parameter: id
     * */
    @RequestMapping("/indexjson/getUser/{name}")
    @ResponseBody
    public Map indexJosnC(@PathVariable String name){
        HashMap<String,Object> resultData= new HashMap<>();
        resultData.put("parameter_name",name);
        resultData.put("data",indexService.getUserByName(name));
        return resultData;
    }

      /**
     * uri: sessionid
     * return type: json
     * parameter: empty
     * */
    @RequestMapping("/sessionid")
    @ResponseBody
    public Map sessionId(){
        HashMap<String,Object> resultData= new HashMap<>();
        resultData.put("session_id",request.getSession().getId());
        return resultData;
    }


}

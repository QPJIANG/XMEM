package demo.pqjiang.controller;

import demo.pqjiang.base.BaseController;
import demo.pqjiang.util.FileUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping("file")
public class FileController extends BaseController {

    private static final String FILEPATH="/tmp/";

    private Logger logger = LogManager.getLogger(FileController.class);

    @RequestMapping("/")
    public String index(Model model) {
        return "file/index";
    }

    /**
     * upload file by ajax
     * parameter :file
     * return : upload result
     */

    @RequestMapping(value = "upload")
    @ResponseBody
    public Map uploadFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {
        Map<String, Object> m = new HashMap<String, Object>();
        if(file.isEmpty()){
           m.put("success",false);
           m.put("message","empty file");
        }
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");

        String userName = securityContextImpl.getAuthentication().getName();

        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        logger.info(userName+"upload file:"+fileName+" ---->  file size:"+size+" B");

//      String path = request.getSession().getServletContext().getRealPath("upload");
//      String contentType = file.getContentType();
        String path = FILEPATH+userName ;
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ // is parent  path is exists
            dest.getParentFile().mkdir();
        }
        // save file
        try {
            file.transferTo(dest);
            m.put("success", true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            m.put("success", false);
            m.put("message",e.getMessage());
        }

        return m;
    }

    /**
     * download file
     * parameter :filename
     * return : file stream
     */

    @RequestMapping(value = "download")
    public ResponseEntity<byte[]> download(String filename,@RequestParam(value = "username", required = false)String username) {
        if (filename == null) {
            return null;
        }

        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");

        String userName = null ;
        if(username == null){
            userName = securityContextImpl.getAuthentication().getName();
        }else{
            userName = username;
        }

        if(userName ==null){
            return null;
        }

        String path = FILEPATH+userName ;
        filename=filename.replaceAll("../","");
        try {
            File file = new File(path + "/" + filename);
            if(!file.exists()){
                return null;
            }
            String dfileName = new String(filename.getBytes("utf-8"), "utf-8");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", dfileName);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }

    }


    @RequestMapping(value = "list")
    @ResponseBody
    public Map listFiles(){
        Map m = new HashMap<String,Object>();

        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        String username =  securityContextImpl.getAuthentication().getName();
        String path = FILEPATH+username+"/" ;
        try {
            File f=new File(path);
            if(f.isDirectory()){
                ArrayList files= new ArrayList();
                File[] fileArray=f.listFiles();
                for(File file:fileArray){
                    logger.info(file);
                    files.add(file.getName());
                }
                m.put("data",files);
            }
            m.put("success",true);
        }catch (Exception e){
            m.put("success",false);
            m.put("message",e.getMessage());
        }

        return m;

    }
}

package com.xcm.webservice.controller;

import com.xcm.business.UserBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 薛岑明 on 2017/3/13.
 */
@Controller
public class UploadController {
    @Autowired
    public UserBusiness userBusiness;

    @ResponseBody
    @RequestMapping(value = "/headerThumb", method = RequestMethod.POST)
    public String uploadHeaderThumb(@RequestParam(value = "file", required = true)
                                            MultipartFile file, HttpServletRequest request) {
        String imgUrl = userBusiness.uploadHeaderThumb(file);
        return imgUrl;
    }

    @ResponseBody
    @RequestMapping(value = "/enterprise/icon", method = RequestMethod.POST)
    public String uploadEnterpriseIcon(@RequestParam(value = "file", required = true)
                                               MultipartFile file, HttpServletRequest request) {
        String imgUrl = userBusiness.uploadEnterpriseIcon(file);
        return imgUrl;
    }
}

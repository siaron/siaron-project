package com.siraon.support;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xielongwang
 * @create 2019-05-1417:38
 * @email xielong.wang@nvr-china.com
 * @description
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //时间格式化
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    /**
     * ModelAttribute 默认值
     */
    @ModelAttribute
    public void globalModelAttribute(Model model) {
        model.addAttribute("attribute", "The Attribute");
        model.addAttribute("message", "this is from model attribute");
    }

    /**
     * 捕获CustomException
     *
     * @param e {@link GlobalException}
     * @return json格式类型
     */
    @ResponseBody
    @ExceptionHandler({GlobalException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> customExceptionHandler(GlobalException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", e.getCode());
        map.put("msg", e.getMsg());
        return map;
    }
}
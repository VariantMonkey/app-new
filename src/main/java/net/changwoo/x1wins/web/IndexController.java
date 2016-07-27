package net.changwoo.x1wins.web;

import net.changwoo.x1wins.service.BbsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
 
/**
 * Handles requests for the application home page.
 */
@Controller
public class IndexController {
     
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @SuppressWarnings("unused")
	private static String menu = "index";
    @Autowired
    private BbsService bbsService;  
    @SuppressWarnings({"rawtypes" })
	@RequestMapping(value = "/")
    public String doSignInGetMethod(Locale locale, Map model,HttpServletRequest request) {
    	Map noticeMode=new HashMap<>();
    	Map fgzdMode=new HashMap<>();
    	Map fwzcMode=new HashMap<>();
    	Map dcMode=new HashMap<>();
    	Map xfMode=new HashMap<>();
    	Map xzzqMode=new HashMap<>();
        try {
			bbsService.findListAndPaging(1, 1, 5, noticeMode, request);
			bbsService.findListAndPaging(8, 1, 5, fgzdMode, request);
			bbsService.findListAndPaging(6, 1, 5, fwzcMode, request);
			bbsService.findListAndPaging(12, 1, 5, dcMode, request);
			bbsService.findListAndPaging(13, 1, 5, xfMode, request);
			bbsService.findListAndPaging(9, 1, 5, xzzqMode, request);
			model.put("notice",noticeMode);
			model.put("fgzd",fgzdMode);
			model.put("fwzc",fwzcMode);
			model.put("dc",dcMode);
			model.put("xf",xfMode);
			model.put("xzzq",xzzqMode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "beidaIndex.tiles";
    }
    /**
     * 书记信箱
     * @return
     */
    @RequestMapping(value = "/sjxx", method = RequestMethod.GET)
    public String goSjxx() {
        return "sjxx.tiles";
    }
    /**
     * 校长信箱
     * @return
     */
    @RequestMapping(value = "/xzxx", method = RequestMethod.GET)
    public String goXzxx() {
        return "xzxx.tiles";
    }
    /**
     * 结构简介
     * @return
     */
    @RequestMapping(value = "/jgjj", method = RequestMethod.GET)
    public String goJgjj() {
        return "jgjj.tiles";
    }
    /**
     * 联系我们
     * @return
     */
    @RequestMapping(value = "/lxwm", method = RequestMethod.GET)
    public String goLxwm() {
        return "lxwm.tiles";
    }

    /**
     * 后台登录入口
     * @param request
     * @param response
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public void goAdmin(HttpServletRequest request, HttpServletResponse response){
        try {
            request.getRequestDispatcher("/bbs/1/list/1").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
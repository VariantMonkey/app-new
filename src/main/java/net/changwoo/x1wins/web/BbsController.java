package net.changwoo.x1wins.web;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.changwoo.x1wins.entity.Bbs;
import net.changwoo.x1wins.entity.File;
import net.changwoo.x1wins.entity.Reply;
import net.changwoo.x1wins.entity.Response;
import net.changwoo.x1wins.service.BbsService;
import net.changwoo.x1wins.service.FileService;
import net.changwoo.x1wins.service.ReplyService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/bbs")
public class BbsController {
     
    private static final Logger logger = LoggerFactory.getLogger(BbsController.class);
	private static int perPage = 10;
	private static String errorPage = "error.tiles";
	private static String menu = "bbs";
	private static String classname = "bbs";
     
    @Autowired
    private BbsService bbsService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ReplyService replyService;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{bbsnum}/form", method = RequestMethod.GET)
    public String showForm(@PathVariable("bbsnum") int bbsnum, Map model, HttpServletRequest request) {
    	
    	model.put("menu", menu);
		try {
			
			Bbs bbs = new Bbs();
			model.put("bbs", bbs);
			model.put("bbsnum", bbsnum);
			model.put("menu", menu);
			
			bbsService.validWrite(bbsnum, request);
			
		} catch (Exception e) {
			logger.debug(e.toString());
			model.put("message", e.toString());
			return errorPage;
		}
    	
    	
    	
    	return "bbs/form.tiles";
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{bbsnum}/form", method = RequestMethod.POST)
	public String processForm(@PathVariable("bbsnum") int bbsnum, @Valid Bbs bbs, BindingResult result,
			Map model, @RequestParam("file") MultipartFile mFiles[], HttpServletRequest request) {

    	model.put("menu", menu);
		try {
			
			logger.info(bbs.getClass()+"result.getAllErrors()"+result.getAllErrors());
			if (result.hasErrors()) {
				return "bbs/form.tiles";
			}
			
			bbsService.validWrite(bbsnum, request);
			
//			bbs.setBbsnum(bbsnum);
			bbsService.saveBbs(bbs, bbsnum, request);
			int snum = bbs.getNum();
			fileService.saveFiles(mFiles, snum, classname, request);

		} catch (Exception e) {
			logger.debug(e.toString());
			model.put("message", e.toString());
			return errorPage;
		}
		
		return "redirect:/bbs/"+bbsnum+"/list/1";
	}
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{bbsnum}/updateform/{num}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("bbsnum") int bbsnum, @PathVariable("num") int num, Locale locale, Map model, HttpServletRequest request) {
    	
    	model.put("menu", menu);
    	Bbs bbs;
		try {
			bbsService.validOwn(num, request);
			bbs = bbsService.findDetail(num);
			model.put("bbs", bbs);
			
			int snum = num;
			List fileList = fileService.findFileList(classname, bbsnum, snum, request);
    		model.put("filelist", fileList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug(e.toString());
			model.put("message", e.toString());
			return errorPage;
		}
    	
		model.put("bbsnum", bbsnum);
    	return "bbs/updateform.tiles";
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{bbsnum}/updateform/{num}", method = RequestMethod.POST)
	public String processUpdateForm(@PathVariable("bbsnum") int bbsnum, @PathVariable("num") int num, @Valid Bbs bbs, BindingResult result,
			Map model, @RequestParam("file") MultipartFile mFiles[], HttpServletRequest request) {
    	model.put("menu", menu);
		try {
			if (result.hasErrors()) {
				return "bbs/updateform.tiles";
			}
			bbsService.validOwn(num, request);
			logger.info(bbs.getClass()+"result.getAllErrors()"+result.getAllErrors());
			
			bbsService.updateBbs(bbs, bbsnum, request);
			int snum = bbs.getNum();
			fileService.saveFiles(mFiles, snum, classname, request);
		} catch (Exception e) {
			logger.debug(e.toString());
			model.put("message", e.toString());
			return errorPage;
		}
		
		return "redirect:/bbs/"+bbsnum+"/list/1";
	}
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{bbsnum}/delete/{num}", method = RequestMethod.GET)
    public String delete(@PathVariable("bbsnum") int bbsnum, @PathVariable("num") int num, Locale locale, Map model, HttpServletRequest request, HttpServletResponse response) {
    	
    	model.put("menu", menu);
    	try {
    		bbsService.validOwn(num, request);
    		List<File> fileList = fileService.findAllByProperty(classname, bbsnum, num, request);
    		if(fileList!=null&&fileList.size()>0){
    			for(File file:fileList){
    				fileService.delete(file.getNum(), classname, request);
    			}
    		}
    		bbsService.delete(bbsnum, num);
    	} catch (Exception e) {
    		logger.debug(e.toString());
    		model.put("message", e.toString());
    		return errorPage;
    	}
    	return "redirect:/bbs/"+bbsnum+"/list/1";
    }

	/**
	 * 管理端列表
	 * @param bbsnum
	 * @param pageNum
	 * @param locale
	 * @param model
	 * @param request
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{bbsnum}/list/{pagenum}", method = RequestMethod.GET)
    public String showBbsList(@PathVariable("bbsnum") int bbsnum, @PathVariable("pagenum") int pageNum, Locale locale, Map model, HttpServletRequest request) {
    	model.put("menu", menu);
		try {
			if (bbsService.validRead(bbsnum, request)) {
				bbsService.findListAndPaging(bbsnum,pageNum, perPage , model, request);
			}			
		} catch (Exception e) {
			logger.debug(e.toString());
			model.put("message", e.toString());
			return errorPage;
		}
        return "bbs/list.tiles";
    }

    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/data/{bbsnum}/list/{pagenum}.{type}", method = RequestMethod.GET)
	public ModelAndView showBbsListData(@PathVariable("bbsnum") int bbsnum,
			@PathVariable("pagenum") int pageNum,
			@PathVariable("type") String type, Locale locale,
			HttpServletRequest request) {
    	
    	Map resultMap = new HashMap();
    	Response response = new Response();
		try {
				bbsService.findListAndPaging(bbsnum,pageNum, perPage , resultMap, request);
				response.setStatus("SUCCESS");
				response.setResult(resultMap);
//			}
		} catch (Exception e) {
			logger.debug(e.toString());
			response.setStatus("FAIL");
		}
		ModelAndView modelAndView = getModelAndView(response, type);
		return modelAndView;
    }

	/**
	 * 前端详情页面
	 * @param bbsnum
	 * @param num
	 * @param locale
	 * @param model
	 * @param request
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{bbsnum}/detail/{num}", method = RequestMethod.GET)
    public String showBbsDetail(@PathVariable("bbsnum") int bbsnum, @PathVariable("num") int num, Locale locale, Map model, HttpServletRequest request) {

    	model.put("menu", menu);
    	Bbs detail = null;
    	try {
    		bbsService.validRead(bbsnum, request);
    		detail = bbsService.findDetail(num);
    		int snum = num;
    		List fileList = fileService.findFileList(classname, bbsnum, snum, request);
    		model.put("detail", detail);
    		model.put("bbsnum", bbsnum);
    		model.put("filelist", fileList);
    	} catch (Exception e) {
    		logger.debug(e.toString());
    		model.put("message", e.toString());
    		return errorPage;
    	}

    	return "beidaDetail.tiles";
    }

	/**
	 * 管理员看到的详情页面
	 * @param bbsnum
	 * @param num
	 * @param locale
	 * @param model
	 * @param request
     * @return
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/admin/{bbsnum}/detail/{num}", method = RequestMethod.GET)
	public String showBbsAdminDetail(@PathVariable("bbsnum") int bbsnum, @PathVariable("num") int num, Locale locale, Map model, HttpServletRequest request) {
		model.put("menu", menu);
		Bbs detail = null;
		try {
			bbsService.validRead(bbsnum, request);

			detail = bbsService.findDetail(num);
			int snum = num;
			List fileList = fileService.findFileList(classname, bbsnum, snum, request);
			model.put("detail", detail);
			model.put("bbsnum", bbsnum);
			model.put("filelist", fileList);
		} catch (Exception e) {
			logger.debug(e.toString());
			model.put("message", e.toString());
			return errorPage;
		}
		return "bbs/detail.tiles";
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/data/detail/{num}.{type}", method = RequestMethod.GET)
	public ModelAndView showBbsDetailData(@PathVariable("num") int num,
			@PathVariable("type") String type) {

		Bbs bbs = null;
		Map resultMap = new HashMap();
		try {
			bbs = bbsService.findDetail(num);
			resultMap.put("bbs", bbs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug(e.toString());
		}
		
		ModelAndView modelAndView = getModelAndView(resultMap, type);
		return modelAndView;
	}

	/**
	 * 
	 * @param resultMap
	 * @param type
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelAndView getModelAndView(Map resultMap, String type)
	{
		ModelAndView modelAndView = new ModelAndView();
		
		try{
			modelAndView.addAllObjects(resultMap);
			
			if(!type.equals("json")&&!type.equals("xml")){
				type = "json";
			}
			
			modelAndView.setViewName(type + "View");
			
		}catch(Exception e){
			logger.debug(e.toString());
		}
		return modelAndView;
	}
	public ModelAndView getModelAndView(Object attributeValue, String type)
	{
		ModelAndView modelAndView = new ModelAndView();
		try{
			modelAndView.addObject(attributeValue);
			
			if(!type.equals("json")&&!type.equals("xml")){
				type = "json";
			}
			
			modelAndView.setViewName(type + "View");
			
		}catch(Exception e){
			logger.debug(e.toString());
		}
		return modelAndView;
	}

    /**
     * 服务之窗
     * @param model
     * @param request
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
   	@RequestMapping(value = "/fwzc", method = RequestMethod.GET)
       public String showFwzc(Map model, HttpServletRequest request) { 	
       	model.put("menu", menu);
   		try {
   				bbsService.findListAndPaging(6,1, perPage , model, request);
   		} catch (Exception e) {
   			logger.debug(e.toString());
   			model.put("message", e.toString());
   			return errorPage;
   		}
           return "notice.tiles";
       }
    /**
     * 督查信息
     * @param model
     * @param request
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
   	@RequestMapping(value = "/dcxx", method = RequestMethod.GET)
       public String showDcxx(Map model, HttpServletRequest request) { 	
       	model.put("menu", menu);
   		try {
   				bbsService.findListAndPaging(12,1, perPage , model, request);
   		} catch (Exception e) {
   			logger.debug(e.toString());
   			model.put("message", e.toString());
   			return errorPage;
   		}
           return "notice.tiles";
       }
    /**
     * 信访信息
     * @param model
     * @param request
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
   	@RequestMapping(value = "/xfxx", method = RequestMethod.GET)
       public String showXfxx(Map model, HttpServletRequest request) { 	
       	model.put("menu", menu);
   		try {
   				bbsService.findListAndPaging(13,1, perPage , model, request);
   		} catch (Exception e) {
   			logger.debug(e.toString());
   			model.put("message", e.toString());
   			return errorPage;
   		}
           return "notice.tiles";
       }
	/**
	 * 前端列表页面
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/beida/list/{bbsnum}", method = RequestMethod.GET)
	public String showBeidaBbs(@PathVariable("bbsnum") int bbsnum,Map model, HttpServletRequest request) {
		model.put("menu", menu);
		int pageNum=1;
		String pageNumStr=request.getParameter("pageNum");
		if(pageNumStr!=null&&!pageNumStr.equals("")){
			pageNum=Integer.parseInt(pageNumStr);
		}
		try {
			bbsService.findListAndPaging(bbsnum,pageNum, perPage , model, request);
		} catch (Exception e) {
			logger.debug(e.toString());
			model.put("message", e.toString());
			return errorPage;
		}
		return "beida/bbs/list.tiles";
	}
}
package net.changwoo.x1wins.web;

import net.changwoo.x1wins.entity.Response;
import net.changwoo.x1wins.entity.Signin;
import net.changwoo.x1wins.entity.User;
import net.changwoo.x1wins.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/user")
// @SessionAttributes("user")
public class UserController {

    private static final Logger logger = LoggerFactory
            .getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "/list.{protocol}", method = RequestMethod.GET)
    public String doList(Map model, @PathVariable String protocol, HttpServletRequest request) {

        Response response = new Response();
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.setStatus("FAIL");
//				response.setResult(resultMap);
            } else {
                String userid = session.getAttribute("userid").toString();
                logger.info("session userid " + userid);
                logger.info("session getid() " + session.getId());
                List list = userService.findAll();
                model.put("list", list);
            }


        } catch (Exception e) {
            logger.debug(e.toString());
            response.setStatus("FAIL");
        }

        return protocol + "View";
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "/{usernum}/detail.{protocol}", method = RequestMethod.GET)
    public String doDetail(@PathVariable("usernum") int usernum, Map model, @PathVariable String protocol) {
        try {
            User user = userService.findUser(usernum);
            model.put("user", user);
        } catch (Exception e) {
            logger.debug(e.toString());
        }
        return protocol + "View";
    }

    /**
     * sign in
     *
     * @param
     * @param model
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    // Display the form on the get request
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String showSignInForm(Map model, HttpServletRequest request) {
        Signin signin = new Signin();
        model.put("signin", signin);
        model.put("menu", "signin");
        return "user/signin.tiles";
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String processSignInForm(@Valid Signin signin, BindingResult result,
                                    Map model, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (result.hasErrors()) {
                return "user/signin.tiles";
            }
            // Add the saved validationForm to the model
            if (userService.isValidSignin(signin) == false) {
                result.rejectValue("userid", "WrongIdPw", "warnning");
                return "user/signin.tiles";
            } else {
                userService.createSigninSession(request, signin.getUserid());
            }
            model.put("signin", signin);
            response.sendRedirect(request.getContextPath() + "/bbs/1/list/1");
        } catch (Exception e) {
            logger.debug(e.toString());
        }
        return null;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    @RequestMapping(value = "/signin.json", method = RequestMethod.POST)
    public ModelAndView processSignInData(@Valid Signin signin, BindingResult result, Map model, HttpServletRequest request) {
        Map resultMap = new HashMap();
        Response response = new Response();
        try {
            if (userService.isValidSignin(signin) == false) {
                result.rejectValue("userid", "WrongIdPw", "warnning");
                response.setStatus("FAIL");
                response.setResult(result);
            } else {
                //sign in success and creating session
                String sessionId = userService.createSigninSession(request, signin.getUserid());
                response.setStatus("SUCCESS");
                response.setResult(sessionId);
                String userid = signin.getUserid();
                String username = userService.findUsernameByUserid(userid).getName();
                signin.setUsername(username);
            }
            model.put("signin", signin);
        } catch (Exception e) {
            logger.debug(e.toString());
        }
        ModelAndView modelAndView = getModelAndView(response, "json");
        return modelAndView;
    }

    /**
     * sign out
     *
     * @param locale
     * @param model
     * @return
     */
    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    public String doSignOut(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("sign out " + locale.toString());
        try {
            userService.removeSignOutSession(request);
            response.sendRedirect(request.getContextPath() + "/admin");
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug(e.toString());
        }
        return null;
    }

    public ModelAndView getModelAndView(Object attributeValue, String type) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject(attributeValue);
            if (!type.equals("json") && !type.equals("xml")) {
                type = "json";
            }
            modelAndView.setViewName(type + "View");
        } catch (Exception e) {
            logger.debug(e.toString());
        }
        return modelAndView;
    }
}
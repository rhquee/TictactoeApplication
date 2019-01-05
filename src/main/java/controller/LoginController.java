package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import repository.User;
import repository.UserDTO;
import repository.UserLoginValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by kfrak on 15.12.2018.
 */
@Controller
public class LoginController {

    @Autowired
    private User user;

    @Autowired
    private UserLoginValidator userLoginValidator;

//    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
//    public ModelAndView index(HttpSession httpSession,
//                              Model model) {
//        ModelAndView modelAndView = new ModelAndView();
//        model.addAttribute("loginForm", new UserDTO()); //?
//        if (httpSession == null || httpSession.getAttribute("user") == null) {
//            modelAndView.setViewName("index");
//            return modelAndView;
//        } else {
//            modelAndView.setViewName("/login/success");
//            modelAndView.addObject("username", user.getUsername());
//            return modelAndView;
//        }
//    }

    @RequestMapping(value = {"/", "index"}, method = RequestMethod.POST)
    public ModelAndView login(HttpSession httpSession,
                              @ModelAttribute("loginForm") UserDTO loginForm,
                              Model model,
                              BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        userLoginValidator.validate(loginForm, bindingResult);

        //nie wchodzi do ifa
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("index");
            return modelAndView;
        }
        if (user.getUsername().equalsIgnoreCase(loginForm.getUsername()) && user.getPassword().equalsIgnoreCase(loginForm.getPassword())) {
            httpSession.setAttribute("user", user);
            model.addAttribute("username", user.getUsername());
            modelAndView.setViewName("/login/userpage");
            return modelAndView;
        } else {
            modelAndView.setViewName("index"); //zamiast faila to komunikat o błędzie?
            return modelAndView;
        }
    }

//    @RequestMapping(value = {"/login/success"}, method = RequestMethod.GET)
//    public ModelAndView successLogged(HttpSession httpSession) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("username", /*httpSession.getAttribute("user").toString()*/ user.getUsername());
//        modelAndView.setViewName("/login/success");
//        return modelAndView;
//    }

//    @RequestMapping(value = "logout", method = RequestMethod.POST)
//    public ModelAndView logout(HttpServletRequest httpServletRequest) {
//        ModelAndView modelAndView = new ModelAndView();
//        httpServletRequest.getSession().invalidate();
//        modelAndView.setViewName("/logout");
//        return modelAndView;
//    }

//    @RequestMapping(value = {"403page"}, method = RequestMethod.GET)
//    public ModelAndView page403() {
//        ModelAndView modelAndView = new ModelAndView();
////        modelAndView.addObject("username", /*httpSession.getAttribute("user").toString()*/ user.getUsername());
//        modelAndView.setViewName("403page");
//        return modelAndView;
//    }


}
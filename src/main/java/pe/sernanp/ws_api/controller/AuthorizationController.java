package pe.sernanp.ws_api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@Slf4j
@RequestMapping(value="/authorization")
public class AuthorizationController{

    @Value("${template.ui-url}")
    private String MYAPP_UI_URL;


    @RequestMapping(value = "/validate2", method = RequestMethod.POST,produces = "application/json; charset=UTF-8")
    public String validate2(@RequestParam("token") String token, Model model) throws IOException {
        try {
            model.addAttribute("ui_url", MYAPP_UI_URL);
            model.addAttribute("token", token);
            return "sessions";
        } catch (Exception ex) {
            return "Error";
        }
    }


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/validate/{token}", method = RequestMethod.GET ,produces = "application/json; charset=UTF-8")
    public ModelAndView validate(@PathVariable("token") String token , Model model) throws IOException {
            model.addAttribute("ui_url", MYAPP_UI_URL);
            model.addAttribute("token", token);
            ModelAndView modelAndView = new ModelAndView("sessions");
            return modelAndView;
    }
}

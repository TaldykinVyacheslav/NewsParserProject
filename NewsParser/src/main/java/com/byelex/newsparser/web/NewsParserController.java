package com.byelex.newsparser.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.byelex.newsparser.Models.*;
import com.byelex.newsparser.DBManager.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Taldykin V.S.
 * @version 1.00 31.01.14 13:40
 */
@Controller
public class NewsParserController {

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping("/")
    public String mainController(Map<String, Object> map) {
        return "redirect:/index";
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping("/index")
    public String indexController(Map<String, Object> map) {
        map.put("profile", new Profile());
        map.put("profileList", Get.listProfiles());
        if(map.get("currentProfile") == null) {
            map.put("currentProfile"
                    , Get.getProfile(1L));
        }
        if(map.get("selectedProfileID") == null) {
            map.put("selectedProfileID", 1);
        }
        return "main-form";
    }

    @RequestMapping(value = "/profile/{profileID}", method = RequestMethod.GET)
    public String getProfile(RedirectAttributes redirectAttributes
            , @PathVariable("profileID") int profileID) {
        redirectAttributes.addFlashAttribute("currentProfile"
                , Get.getProfile(Long.valueOf(profileID)));
        //redirectAttributes.addFlashAttribute("templateText", Get.getProfile(Long.valueOf(profileID)).getText());
        return "redirect:/index";
    }

    @RequestMapping(value="/add", method=RequestMethod.GET)
    public ModelAndView addTemplateGet() {
        Profile profile;
        List<Template> templates;
        ModelAndView modelAndView;

        profile = new Profile();
        templates = new ArrayList<Template>();
        modelAndView = new ModelAndView("add-profile-form");
        for(Event event : Get.listEvents()) {
            Template template = new Template();
            template.setEvent(event);
            template.setProfile(profile);
            template.setEventText("");
            templates.add(template);
        }

        profile.setTemplates(templates);
        modelAndView.addObject("profile", profile);
        modelAndView.addObject("eventList", Get.listEvents());
        modelAndView.addObject("tokenList", Get.listTokens());
        return modelAndView;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTemplatePost(@ModelAttribute Profile profile) {
        Get.addProfile(profile, Get.listEvents());
        for(Template template : profile.getTemplates()) {
            System.out.println("POST ADD METHOD EVENT: " + template.getEvent());
        }
        return "redirect:/index";
    }

    @RequestMapping(value = "/edit/{profileID}", method = RequestMethod.GET)
     public ModelAndView editTemplateGet(@PathVariable("profileID") Long profileID) {
        ModelAndView modelAndView = new ModelAndView("edit-profile-form");
        Profile profile = Get.getProfile(Long.valueOf(profileID));
        modelAndView.addObject("profileID", profileID);
        modelAndView.addObject("profile", profile);
        modelAndView.addObject("eventList", Get.listEvents());
        modelAndView.addObject("tokenList", Get.listTokens());

        return modelAndView;
    }

    @RequestMapping(value = "/edit/{profileID}", method = RequestMethod.POST)
    public String editTemplatePost(@ModelAttribute Profile profile
            , @PathVariable("profileID") Long profileID) {
        Get.updateProfile(profile, Get.listEvents(), profileID);
        return "redirect:/index";
    }

    @RequestMapping(value = "/delete/{profileID}"
            , method = RequestMethod.GET)
    public String deleteTemplate(RedirectAttributes redirectAttributes
                                 , @PathVariable("profileID") Long profileID) {
        if(profileID != 1) {
            Get.deleteProfile(profileID);
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "You cannot delete this profile!");
        }
        return "redirect:/index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam Map<String,String> requestParams, RedirectAttributes redirectAttributes) {
        int profileID;

        profileID = Integer.valueOf(requestParams.get("profileID"));
        redirectAttributes.addFlashAttribute("selectedProfileID", profileID);
        redirectAttributes.addFlashAttribute("resultText", Get.getFullReport(Get.getProfile((long)profileID)));
        System.out.println("SEARCH CONTROLLER! profileID = " + profileID);
        return "redirect:/index";
    }
}


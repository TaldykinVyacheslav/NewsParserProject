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
@RequestMapping("/settings")
public class ProfileSettingsController {

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping("/")
    public String mainSettingsController(Map<String, Object> map) {
        map.put("profile", new Profile());
        map.put("profileList", Get.listProfiles());
        if(map.get("currentProfile") == null) {
            map.put("currentProfile"
                    , Get.getProfile(1L));
        }
        return "profilesSettings";
    }

    @RequestMapping(value = "/profile/{profileID}", method = RequestMethod.GET)
    public String getProfile(RedirectAttributes redirectAttributes
            , @PathVariable("profileID") int profileID) {
        redirectAttributes.addFlashAttribute("currentProfile"
                , Get.getProfile(Long.valueOf(profileID)));
        //redirectAttributes.addFlashAttribute("templateText", Get.getProfile(Long.valueOf(profileID)).getText());
        return "redirect:/settings/";
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
            /*System.out.println("EVENT ID = " + event.getId());
            System.out.println("TEMPLATE PROFILE ID = " + template.getProfile().getId());
            System.out.println("TEMPLATE EVENT ID = " + template.getEvent().getId());*/
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
        return "redirect:/settings/";
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
        /*for(Template template : profile.getTemplates()) {
            System.out.println(template.getId());
        }*/
        //System.out.println("PROFILE NAME: " + profile.getName());
        return "redirect:/settings/";
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
        return "redirect:/settings/";
    }
}


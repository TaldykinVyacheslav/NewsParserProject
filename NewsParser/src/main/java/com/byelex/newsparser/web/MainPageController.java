package com.byelex.newsparser.web;

import com.byelex.newsparser.DBManager.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class MainPageController {

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping("/index")
    public String listProfiles(Map<String, Object> map) {
        map.put("profileList", Get.listProfiles());
        if(map.get("selectedProfileID") == null){
            map.put("selectedProfileID", 1);
        }
        return "search";
    }

    @RequestMapping(value = "/search")
    public String search(@RequestParam Map<String,String> requestParams, RedirectAttributes redirectAttributes) {
        StringBuilder report;
        String profileTemplate;
        Map<String, String> reportMap;
        int profileID;

        profileID = Integer.valueOf(requestParams.get("profileID"));
        /*profileTemplate = Get.getProfile(Long.valueOf(profileID)).getText();
        reportMap = Get.createReport(Get.getProfile(Long.valueOf(profileID)).getText());
        for(Map.Entry<String, String> reportEntry : reportMap.entrySet()) {
            if(reportEntry.getKey().equals("Robbery")) {
                report.append(reportEntry.getKey() + ":\n");
                report.append(reportEntry.getValue() + "\n");
            }
        }*/

        redirectAttributes.addFlashAttribute("selectedProfileID", profileID);

        redirectAttributes.addFlashAttribute("resultText", Get.getFullReport(Get.getProfile((long)profileID)));
        return "redirect:/index";
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }
}
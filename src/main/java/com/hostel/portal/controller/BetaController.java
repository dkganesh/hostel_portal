package com.hostel.portal.controller;

import com.hostel.portal.entity.pass_related.Pass;
import com.hostel.portal.model.PassModel;
import com.hostel.portal.model.PassTokenModel;
import com.hostel.portal.model.StudentPassTokenModel;
import com.hostel.portal.service.BetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/beta")
public class BetaController {

    @Autowired
    private BetaService service;

    @PostMapping("/applyPass")
    public String applyPass(@RequestBody PassModel model) throws ParseException {
        return service.applyPass(model);
    }

    @GetMapping("/getPassList/{email}")
    public List<PassModel> getPassList(@PathVariable("email") String email){
        return service.getPassList(email);
    }

    @PostMapping("/verifyPass")
    public String verifyPass(@RequestBody PassTokenModel model){
        return service.verifyPassToken(model.getToken());
    }

    @GetMapping("/approvePass/{id}")
    public String approvePass(@PathVariable("id") Integer id){
        return service.approvePass(id);
    }
    @GetMapping("/declinePass/{id}")
    public String declinePass(@PathVariable("id") Integer id){
        return service.declinePass(id);
    }

    @GetMapping("/getToken/{email}")
    public StudentPassTokenModel getToken(@PathVariable("email") String email){
        return service.getToken(email);
    }
}

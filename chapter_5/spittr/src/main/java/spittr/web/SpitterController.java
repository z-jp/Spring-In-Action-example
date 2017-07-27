package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spittr.Spitter;
import spittr.data.SpitterRepository;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

    @Autowired
    private SpitterRepository repository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String spittles() {
        return "home";
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String showUserProfile(@PathVariable String username, Model model) {
        Spitter spitter = repository.findSpitterByUsername(username);
        System.out.println(spitter == null);
        if (spitter == null)
            return "redirect:/register";
        model.addAttribute(spitter);
        return "profile";
    }
}

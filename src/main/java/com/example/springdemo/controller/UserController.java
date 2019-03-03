package com.example.springdemo.controller;

import com.example.springdemo.model.User;
import com.example.springdemo.repo.UserRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author Rafik Gasparyan 02/03/2019
 */

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * this method for view addUser
     *
     * @return "addUSer"
     */
    @GetMapping("/user/add")
    public String addUserGet() {
        return "addUser";
    }

    /**
     * this method for add new user
     *
     * @param user          set user data from addUser view to db
     * @param multipartFile set current user picture
     * @return redirect to home page
     * @throws IOException
     */
    @PostMapping("/user/add")
    public String addUser(@ModelAttribute User user, @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        uploadImage(user, multipartFile);
        return "redirect:/home";
    }

    /**
     * this method for get image in full screen
     *
     * @param response for set content image type
     * @param picUrl   current image id
     * @throws IOException
     */
    @GetMapping("/getImage")
    public void getImageAsByteArray(HttpServletResponse response, @RequestParam("picUrl") String picUrl) throws IOException {
        InputStream in = new FileInputStream("C:\\Users\\IP183-USER\\Desktop\\website\\springdemo\\picture\\" + picUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    /**
     * this method for delete current user
     *
     * @param id current user id
     * @return redirect to home page
     */
    @GetMapping("/user/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userRepository.deleteById(id);
        return "redirect:/home";
    }

    /**
     * this method for get current user info to userEdit page
     *
     * @param id    current user id
     * @param model send current user info to user edit page
     * @return if users is present return userEdit page else redirect home page
     */
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Optional<User> users = userRepository.findById(id);
        if (users.isPresent()) {
            User user = users.get();
            model.addAttribute("user", user);
        } else {
            return "redirect:/home";
        }
        return "userEdit";
    }

    /**
     * this method for post updated user info
     *
     * @param id            current updated user id
     * @param user          current updated user info
     * @param result        updated user result
     * @param model         send updated users info
     * @param multipartFile current user updated picture
     * @return if result error return userEdit page else return home page
     * @throws IOException
     */
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") int id, @Valid User user,
                             BindingResult result, Model model,
                             @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()) {
            user.setId(id);
            return "userEdit";
        }
        uploadImage(user, multipartFile);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/home";
    }

    /**
     * this method for update current user and picture
     *
     * @param user          updated user
     * @param multipartFile updated picture
     * @throws IOException
     */
    private void uploadImage(@Valid User user, @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        String name = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File file = new File("C:\\Users\\IP183-USER\\Desktop\\website\\springdemo\\picture\\" + name);
        multipartFile.transferTo(file);
        user.setPicUrl(name);
        userRepository.save(user);
    }
}
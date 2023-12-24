package com.pass.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pass.entites.ApplyForm;
import com.pass.entites.User;
import com.pass.repository.UserRepository;
import com.pass.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController 
{
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@ModelAttribute
	public void addCommnData(Principal p, Model m)
	{
		String email = p.getName();
		User user = userRepo.findByEmail(email);
		m.addAttribute("user", user);
	}

	@GetMapping("/")
	public String home() 
	{
		return "user/index";
	}

	@GetMapping("/applyForm")
	public String applyForm() 
	{
		return "user/apply_form";
	}

	@GetMapping("/myApplication")
	public String myApplication(Model m, Principal p) 
	{
		String email = p.getName();
		User user = userRepo.findByEmail(email);
		m.addAttribute("list", userService.getFormByUser(user.getId()));
		return "user/my_application";
	}

	@GetMapping("/viewApplication/{id}")
	public String viewApplication(@PathVariable long id, Model m)
	{
		m.addAttribute("f", userService.getFormById(id));
		return "user/view_application";
	}

	@GetMapping("/viewProfile")
	public String viewProfile() 
	{
		return "user/view_profile";
	}

	@GetMapping("/editProfile")
	public String editProfile() 
	{
		return "user/edit_profile";
	}

	@GetMapping("/changePassword")
	public String changePassword() 
	{
		return "user/change_password";
	}

	@GetMapping("/appointment")
	public String appointment()
	{
		return "user/appointment";
	}

	@PostMapping("/updateUser")
	public String createUse(@ModelAttribute User user, HttpSession session)
	{
		if (userService.updateUser(user) != null)
		{
			session.setAttribute("succMsg", "update sucessfully");
		}
		else 
		{
			session.setAttribute("errorMsg", "something wrong on server");
		}
		return "redirect:/user/editProfile";
	}

	@PostMapping("/saveApplyForm")
	public String saveApplyForm(@ModelAttribute ApplyForm ap, HttpSession session,
			@RequestParam("pan") MultipartFile pan, @RequestParam("photo") MultipartFile photo) 
	{

		ap.setPanFileName(pan.getOriginalFilename());
		ap.setImgFileName(photo.getOriginalFilename());
		// System.out.println(ap);

		if (userService.saveApplyForm(ap) != null) 
		{

			try
			{

				File panSaveFile = new ClassPathResource("static/panImg").getFile();
				File photoSaveFile = new ClassPathResource("static/photo").getFile();

				Path panPath = Paths.get(panSaveFile.getAbsolutePath() + File.separator + pan.getOriginalFilename());
				Path photoPath = Paths
						.get(photoSaveFile.getAbsolutePath() + File.separator + photo.getOriginalFilename());
				// System.out.println(panPath);

				Files.copy(pan.getInputStream(), panPath, StandardCopyOption.REPLACE_EXISTING);
				Files.copy(photo.getInputStream(), photoPath, StandardCopyOption.REPLACE_EXISTING);

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			session.setAttribute("succMsg", "Apply sucessfully");
		}
		else
		{
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/user/applyForm";
	}

	@GetMapping("/downloadPan/{pan}")
	public void downloadAssignment(@PathVariable String pan, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		OutputStream outStream = null;
		FileInputStream inputStream = null;
		try {
			File saveFile = new ClassPathResource("static/panImg").getFile();

			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + pan);

			File file = new File(path + "");

			if (file.exists()) {

				/**** Setting The Content Attributes For The Response Object ****/

				String mimeType = "application/octet-stream";
				response.setContentType(mimeType);

				/**** Setting The Headers For The Response Object ****/

				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
				response.setHeader(headerKey, headerValue);

				/**** Get The Output Stream Of The Response ****/

				outStream = response.getOutputStream();
				inputStream = new FileInputStream(file);
				byte[] buffer = new byte[1024 * 100000];
				int bytesRead = -1;

				/****
				 * Write Each Byte Of Data Read From The Input Stream Write Each Byte Of Data
				 * Read From The Input Stream Into The Output Stream
				 ***/
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}
			}
		} catch (IOException ioExObj) {
			System.out.println("Exception While Performing The I/O Operation?= " + ioExObj.getMessage());
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

			outStream.flush();
			if (outStream != null) {
				outStream.close();
			}
		}

	}

	@PostMapping("/changePsw")
	public String changePasw(Principal p, HttpSession session, @RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword)
	{

		String email = p.getName();
		User currentUser = userRepo.findByEmail(email);

		if (passwordEncoder.matches(oldPassword, currentUser.getPassword())) {

			currentUser.setPassword(passwordEncoder.encode(newPassword));
			userRepo.save(currentUser);

			session.setAttribute("succMsg", "password change sucessfully");
		} 
		else
		{
			session.setAttribute("errorMsg", "old password is incorrect");
		}

		return "redirect:/user/changePassword";
	}

}

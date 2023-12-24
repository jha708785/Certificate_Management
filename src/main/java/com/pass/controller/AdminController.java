package com.pass.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pass.entites.ApplyForm;
import com.pass.entites.Office;
import com.pass.entites.User;
import com.pass.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/")
	public String home() {
		return "admin/index";
	}

	@GetMapping("/allUser")
	public String allUser(Model m) {
		m.addAttribute("list", adminService.getAllUser());
		return "admin/all_user";
	}

	@GetMapping("/editUser/{id}")
	public String editUser(@PathVariable long id, Model m) {
		m.addAttribute("user", adminService.getUserById(id));
		return "admin/edit_user";
	}

	@PostMapping("/updateUser")
	public String updateUser(@ModelAttribute User user, HttpSession session) {

		if (adminService.updateUser(user) != null) {
			session.setAttribute("succMsg", "Update sucessfully");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}
		return "redirect:/admin/allUser";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable long id, HttpSession session) {

		if (adminService.deleteUser(id)) {
			session.setAttribute("succMsg", "User Delete sucessfully");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}
		return "redirect:/admin/allUser";
	}

	@GetMapping("/application")
	public String Application(Model m) {
		m.addAttribute("list", adminService.getAllForm());
		return "admin/application";
	}

	@GetMapping("/viewApplication/{id}")
	public String viewApplication(@PathVariable long id, Model m) {
		m.addAttribute("f", adminService.getFormById(id));
		return "admin/view_application";
	}

	@GetMapping("/message")
	public String message() {
		return "admin/message";
	}

	@GetMapping("/office")
	public String office(Model m) {
		m.addAttribute("list", adminService.getAllOffice());
		return "admin/office";
	}

	@GetMapping("/editoffice/{id}")
	public String editoffice(@PathVariable long id, Model m) {
		m.addAttribute("of", adminService.getOfficeById(id));
		return "admin/edit_office";
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

	@GetMapping("/updateStatus/{st}/{id}")
	public String updateStatus(@PathVariable String st, @PathVariable long id, HttpSession session) {

		if (adminService.updateStatus(st, id) != null) {
			session.setAttribute("succMsg", "Status Update sucessfully");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/viewApplication/" + id;
	}

	@GetMapping("/appointment/{id}")
	public String appointment(@PathVariable long id, Model m) {
		m.addAttribute("f", adminService.getFormById(id));
		m.addAttribute("list", adminService.getAllOffice());
		return "admin/appointment";
	}

	@PostMapping("/addOffice")
	public String addOffice(@ModelAttribute Office of, HttpSession session) {

		if (adminService.addOffice(of) != null) {
			session.setAttribute("succMsg", "Office added sucessfully");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/office";
	}

	@PostMapping("/updateOffice")
	public String updateOffice(@ModelAttribute Office of, HttpSession session) {

		if (adminService.addOffice(of) != null) {
			session.setAttribute("succMsg", "Office update sucessfully");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/office";
	}

	@GetMapping("/deleteOffice/{id}")
	public String deleteOffice(@PathVariable long id, HttpSession session) {

		if (adminService.deleteOffice(id)) {
			session.setAttribute("succMsg", "Office Delete sucessfully");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}
		return "redirect:/admin/office";
	}

	@PostMapping("/updateAppointment")
	public String updateAppointment(@ModelAttribute ApplyForm f, HttpSession session) {

		if (adminService.updateAppointment(f) != null) {
			session.setAttribute("succMsg", "Appointnment sucessfully");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}
		return "redirect:/admin/application";
	}

}

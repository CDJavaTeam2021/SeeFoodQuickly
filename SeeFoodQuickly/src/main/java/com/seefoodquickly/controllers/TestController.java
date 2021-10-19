package com.seefoodquickly.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.seefoodquickly.models.Picture;
import com.seefoodquickly.models.Product;
import com.seefoodquickly.services.OrderingService;

import io.jsonwebtoken.io.IOException;

@Controller
@RequestMapping("/test")
public class TestController {
	
	public static String FOLDERPATH = "src/main/resources/static/images/test/";
	public static String SHORTPATH = "/images/test/";
	
	@Autowired
	OrderingService oServ;
	
	@GetMapping("/upload")
	public String product(@ModelAttribute("newProduct") Product product, Model viewModel) {
		List<Picture> pictures = oServ.allPictures();
		viewModel.addAttribute("pictures", pictures);
		return "newproductTest.jsp";
	}
	
	@PostMapping("/upload")
	public String upload(@RequestParam("image") MultipartFile file) {
		Product product = oServ.getProductById((long)3);
		if(file.isEmpty()) {
			return "redirect:/test/upload";
		}
		try {
			System.out.println("getting the file");
			byte[] bytes = file.getBytes();
			Path path = Paths.get(FOLDERPATH + file.getOriginalFilename());
			Files.write(path, bytes);
			String url = SHORTPATH + file.getOriginalFilename();
			System.out.println(url);
			oServ.addPicture(url, "new pic", product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/test/upload";
	}
	
	

}

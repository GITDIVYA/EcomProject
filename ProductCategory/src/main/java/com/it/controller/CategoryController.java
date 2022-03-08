package com.it.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.it.dto.CategoryDto;
import com.it.service.CategoryService;

@RestController
@RequestMapping("/CategoryDetails")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	@PostMapping("/data")
	public ResponseEntity<CategoryDto> postData(@RequestBody CategoryDto dto){
		ResponseEntity<CategoryDto> data = service.saveCategoryData(dto);
		return data;
	}
	
	@GetMapping("/getData")
	public ResponseEntity<CategoryDto> getData(@RequestParam("companyName") String cmpName) {
		System.out.println(cmpName);
		ResponseEntity<CategoryDto> fetchData = service.getData(cmpName);
		return fetchData;
	}
	
	@PutMapping("/update/{name}")
	public ResponseEntity<CategoryDto> updateData(@RequestBody CategoryDto dto, @PathVariable("name") String name){
		ResponseEntity<CategoryDto> update = service.updateDate(name, dto);
		return update;
	}
	
	@DeleteMapping("/delete/{name}")
	public String deleteData(@PathVariable("name") String name){
		service.deleteCategory(name);
		return "Deleted succesfully";
	}
	
	@GetMapping("/getDataPage")
	public Page<CategoryDto> categoryList(Pageable p){
		return service.categoryList(p);
	}
	


}

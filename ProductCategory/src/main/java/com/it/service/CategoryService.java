package com.it.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.it.dto.CategoryDto;
import com.it.dto.ProductDto;
import com.it.entities.CategaryEntity;
import com.it.entities.ProductEntity;
import com.it.repo.CategoryRepository;
import com.it.repo.ProductRepository;

import lombok.experimental.var;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository catRepo;
	@Autowired
	private ProductRepository prodRepo;

	public ResponseEntity<CategoryDto> saveCategoryData(CategoryDto dto) {
		try {
			CategaryEntity cEntity = new CategaryEntity();
			cEntity.setName(dto.getName());
			cEntity.setCmpName(dto.getCmpName());
			List<ProductEntity> pList = new ArrayList<ProductEntity>();
			for (ProductDto pdto : dto.getProducts()) {
				ProductEntity pEntity = new ProductEntity();
				pEntity.setName(pdto.getName());
				pList.add(pEntity);
			}
			cEntity.setProducts(pList);
			catRepo.save(cEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResponseEntity<CategoryDto> getData(String cName) {
		try {
			CategoryDto dto = new CategoryDto();
			Optional<CategaryEntity> name = catRepo.findByCmpName(cName);
			CategaryEntity cEntity = name.get();
			List<ProductDto> subList = new ArrayList<ProductDto>();
			dto.setName(cEntity.getName());
			dto.setCmpName(cEntity.getCmpName());
			for (ProductEntity pEntity : cEntity.getProducts()) {
				ProductDto subDto = new ProductDto();
				subDto.setName(pEntity.getName());
				subList.add(subDto);
			}
			dto.setProducts(subList);
			return new ResponseEntity<CategoryDto>(dto, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<CategoryDto>(HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<CategoryDto> updateDate(String name, CategoryDto dto){
		try {
			Optional<CategaryEntity> findByname = catRepo.findByName(name);
			if (findByname.isPresent()) {
				CategaryEntity cEntity = findByname.get();
				if(dto.getName() != null && !dto.getName().isEmpty())
					cEntity.setName(dto.getName());
				if(dto.getCmpName() != null && !dto.getCmpName().isEmpty())
					cEntity.setCmpName(dto.getCmpName());
				catRepo.save(cEntity);
			}
			return null;
			
		} catch (Exception e) {
			return new ResponseEntity<CategoryDto>(HttpStatus.BAD_REQUEST);
		}
	}
	
	public void deleteCategory(String cname) {
		catRepo.deleteByName(cname);
	}
	
	public Page<CategoryDto> categoryList(Pageable p){
		return catRepo.findAll(p);
	}

}

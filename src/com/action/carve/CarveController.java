package com.action.carve;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.action.BaseAction;
import com.alibaba.fastjson.JSONObject;
import com.exception.common.ControllerException;
import com.po.carve.CarveType;
import com.po.carve.CarveUrl;
import com.service.carve.CarveTypeMapperService;
import com.service.carve.CarveUrlMapperService;
import com.util.DataHandle;
import com.vo.PageInfoVO;

@Controller
@RequestMapping("carve")
public class CarveController extends BaseAction{

	@Autowired
	CarveTypeMapperService carveTypeMapperService;
	
	@Autowired
	CarveUrlMapperService carveUrlMapperService;
	
	@RequestMapping("index")
	public String index(){
		return "carve/index";
	}
	
	@RequestMapping("view")
	public String view(Model model){
		CarveType carveType = new CarveType();
		carveType.setEnable(1);
		List<CarveType> carveTypeList = carveTypeMapperService.selectList(carveType);
		model.addAttribute("carveTypeList", carveTypeList);
		return "carve/view";
	}
	
	@RequestMapping("list")
	public void list(HttpServletRequest request,HttpServletResponse response){
		try{
			PageInfoVO pageInfoVO = returnPageInfo(request);
	        CarveType carveType = new CarveType();
	        carveType.setStartPage(pageInfoVO.getStartPage());
	        carveType.setPage(pageInfoVO.getPage());
	        carveType.setCondition(pageInfoVO.getOrderCol());
			List<CarveType> menuList = carveTypeMapperService.selectList(carveType);
			int count = carveTypeMapperService.selectCount(carveType);
			JSONObject json = new JSONObject();
			json.put("iTotalRecords", 10);  //本次查询记录数
			json.put("iTotalDisplayRecords", count); //记录总数
			json.put("aaData", menuList); 
			json.put("result", "success");
			writeResult(response, json);
		}catch(Exception e){
			throw new ControllerException(e);
		}
	}
	
	@RequestMapping("listDetail")
	public void listDetail(HttpServletRequest request,HttpServletResponse response){
		try{
			Integer carveTypeId = DataHandle.returnValueInt(request, "carveTypeId");
			PageInfoVO pageInfoVO = returnPageInfo(request);
			CarveUrl carveUrl = new CarveUrl();
			if(carveTypeId != -1){
				carveUrl.setCarveTypeId(carveTypeId);
			}
	        carveUrl.setStartPage(pageInfoVO.getStartPage());
	        carveUrl.setPage(pageInfoVO.getPage());
	        carveUrl.setCondition(pageInfoVO.getOrderCol());
			List<CarveUrl> menuList = carveUrlMapperService.selectList(carveUrl); 
			int count = carveUrlMapperService.selectCount(carveUrl);
			JSONObject json = new JSONObject();
			json.put("iTotalRecords", 10);  //本次查询记录数
			json.put("iTotalDisplayRecords", count); //记录总数
			json.put("aaData", menuList); 
			json.put("result", "success");
			writeResult(response, json);
		}catch(Exception e){
			throw new ControllerException(e);
		}
	}
	
	@RequestMapping("deletePattern")
	public void deletePattern(Integer[] ids,HttpServletResponse response){
		try{
			CarveType carveType = new CarveType();
			carveType.setIds(DataHandle.returnStringFromIntArray(ids));
			carveTypeMapperService.deleteByIds(carveType);
			JSONObject jsonObject = createJosnObject();
			jsonObject.put("message", "删除成功");
			writeResult(response, jsonObject);
		}catch(Exception e){
			throw new ControllerException(e);
		}
	}
	
	@RequestMapping("addOrUpdatePattern")
	public void addOrUpdatePattern(CarveType carveType,HttpServletResponse response){
		try{
			if(carveType.getCarveTypeId() == null){
				carveTypeMapperService.insert(carveType);
			}else{
				carveTypeMapperService.update(carveType);
			}
			writeResult(response, createJosnObject());
		}catch(Exception e){
			throw new ControllerException(e);
		}
	}
	
	@RequestMapping("returnCarveType")
	public void returnCarveType(CarveType carveType,HttpServletResponse response){
		try{
			carveType = carveTypeMapperService.select(carveType);
			net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
			jsonObject.put("object", carveType);
			writeResult(response, jsonObject);
		}catch(Exception e){
			throw new ControllerException(e);
		}
	}
	
	
}

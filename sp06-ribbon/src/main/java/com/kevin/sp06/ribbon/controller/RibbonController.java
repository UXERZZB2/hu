package com.kevin.sp06.ribbon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kevin.sp01.pojo.Item;
import com.kevin.sp01.pojo.Order;
import com.kevin.sp01.pojo.User;
import com.kevin.sp01.util.JsonResult;

@RestController
public class RibbonController {
	@Autowired
	private RestTemplate rt;
	
	@GetMapping(value = "/item-service/{orderId}",produces = { "application/json;charset=UTF-8" })
	public JsonResult<List<Item>> getItems(@PathVariable String orderId) {
	    //这里服务器路径用 service-id （item-service）代替，ribbon 会向服务的多台集群服务器分发请求
		return rt.getForObject("http://item-service/{1}", JsonResult.class, orderId);
	}

	@PostMapping(value = "/item-service/decreaseNumber",produces = { "application/json;charset=UTF-8" })
	public JsonResult decreaseNumber(@RequestBody List<Item> items) {
		return rt.postForObject("http://item-service/decreaseNumber", items, JsonResult.class);
	}

	/////////////////////////////////////////
	
	@GetMapping(value = "/user-service/{userId}",produces = { "application/json;charset=UTF-8" })
	public JsonResult<User> getUser(@PathVariable Integer userId) {
		return rt.getForObject("http://user-service/{1}", JsonResult.class, userId);
	}

	@GetMapping(value = "/user-service/{userId}/score",produces = { "application/json;charset=UTF-8" }) 
	public JsonResult addScore(
			@PathVariable Integer userId, Integer score) {
		return rt.getForObject("http://user-service/{1}/score?score={2}", JsonResult.class, userId, score);
	}
	
	/////////////////////////////////////////
	
	@GetMapping(value = "/order-service/{orderId}",produces = { "application/json;charset=UTF-8" })
	public JsonResult<Order> getOrder(@PathVariable String orderId) {
		return rt.getForObject("http://order-service/{1}", JsonResult.class, orderId);
	}

	@GetMapping(value = "/order-service",produces = { "application/json;charset=UTF-8" })
	public JsonResult addOrder() {
		return rt.getForObject("http://order-service/", JsonResult.class);
	}
}

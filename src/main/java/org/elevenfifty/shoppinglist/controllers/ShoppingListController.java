package org.elevenfifty.shoppinglist.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.elevenfifty.shoppinglist.beans.ShoppingList;
import org.elevenfifty.shoppinglist.beans.ShoppingListItem;
import org.elevenfifty.shoppinglist.beans.User;
import org.elevenfifty.shoppinglist.repositories.ShoppingListItemRepository;
import org.elevenfifty.shoppinglist.repositories.ShoppingListRepository;
import org.elevenfifty.shoppinglist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShoppingListController {

	@Autowired
	private ShoppingListRepository shoppingListRepo;

	@Autowired
	private ShoppingListItemRepository shoppingListItemRepo;

	@Autowired
	private UserRepository userRepo;

	@GetMapping("/")
	public String home(Model model) {
		return "redirect:/lists";
	}

	@GetMapping("/lists")
	public String lists(Model model, @RequestParam(name = "srch-term", required = false) String searchTerm) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User u = userRepo.findOneByEmail(email);
		if (searchTerm == null || "".equals(searchTerm)) {
			model.addAttribute("lists", shoppingListRepo.findAllByUser(u));
		} else {
			ArrayList<ShoppingList> userLists = new ArrayList<ShoppingList>();
			ArrayList<ShoppingList> lists = shoppingListRepo.findByCategoryContainsOrNameContainsAllIgnoreCase(searchTerm,
					searchTerm);
			for (ShoppingList list : lists) {
				if (list.getUser() == u) {
					userLists.add(list);
				}
			}
		model.addAttribute("lists", shoppingListRepo.findAllByUser(u));
		model.addAttribute("user", u);
		}
		return "lists";
	}

	@GetMapping("/lists/{listId}")
	public String list(Model model, @PathVariable(name = "listId") long listId,
			@RequestParam(name = "sort", required = false) String sort) {
		model.addAttribute("listId", listId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User u = userRepo.findOneByEmail(email);
		if (shoppingListRepo.findOne(listId).getUser().equals(u)) {
			model.addAttribute("user", u);
			ShoppingList s = shoppingListRepo.findOne(listId);
			if (sort == null)
				sort = "";
			ArrayList<ShoppingListItem> slis;
			switch (sort) {
			case "atoz":
				slis = shoppingListItemRepo.findByShoppingListIdOrderByNameAsc(listId);
				break;
			case "ztoa":
				slis = shoppingListItemRepo.findByShoppingListIdOrderByNameDesc(listId);
				break;
			case "priority":
				slis = shoppingListItemRepo.findByShoppingListIdOrderByShoppingListItemPriorityIdDesc(listId);
				break;
			default:
				slis = shoppingListItemRepo.findByShoppingListId(listId);
				break;
			}
			model.addAttribute("listItems", slis);
			model.addAttribute("shoppingList", s);
			return "listView";
		} else {
			return "redirect:/error";
		}
	}

	@GetMapping("/lists/add")
	public String listAdd(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User u = userRepo.findOneByEmail(email);
		model.addAttribute("user", u);
		ShoppingList shopList = new ShoppingList();
		shopList.setUser(u);
		model.addAttribute("list", shopList);
		return "listAdd";
	}

	@PostMapping("/lists/add")
	public String listAddSave(Model model, @RequestParam(name = "manualColor") String manualColor,
			@ModelAttribute @Valid ShoppingList list, BindingResult result) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User u = userRepo.findOneByEmail(email);
		model.addAttribute("user", u);
		if (result.hasErrors()) {
			model.addAttribute("list", list);
			return "listAdd";
		} else {
			if (!"".equals(manualColor)) {
				list.setColor(manualColor);
			}
			shoppingListRepo.save(list);
			return "redirect:/lists/" + list.getId();
		}
	}

	@GetMapping("/lists/{listId}/delete")
	public String shoppingListDelete(Model model, @PathVariable(name = "listId") long listId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User u = userRepo.findOneByEmail(email);
		if (shoppingListRepo.findOne(listId).getUser().equals(u)) {
			model.addAttribute("user", u);
			model.addAttribute("listId", listId);
			ShoppingList shopList = shoppingListRepo.findOne(listId);
			model.addAttribute("shoppingList", shopList);
			return "listDelete";
		} else {
			return "redirect:/error";
		}
	}

	@PostMapping("/lists/{listId}/delete")
	public String shoppingListDeleteSave(@PathVariable(name = "listId") long listId, Model model) {
		// FIXME
		// shoppingListRepo.delete(listId);
		return "redirect:/lists";
	}

}

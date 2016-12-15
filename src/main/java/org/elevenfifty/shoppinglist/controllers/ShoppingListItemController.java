package org.elevenfifty.shoppinglist.controllers;

import java.io.IOException;

import javax.validation.Valid;


import org.elevenfifty.shoppinglist.beans.ShoppingListItem;
import org.elevenfifty.shoppinglist.beans.ShoppingListItemImage;
import org.elevenfifty.shoppinglist.beans.ShoppingListItemNote;
import org.elevenfifty.shoppinglist.beans.User;
import org.elevenfifty.shoppinglist.repositories.ShoppingListItemNoteRepository;
import org.elevenfifty.shoppinglist.repositories.ShoppingListItemPriorityRepository;
import org.elevenfifty.shoppinglist.repositories.ShoppingListItemRepository;
import org.elevenfifty.shoppinglist.repositories.ShoppingListItemImageRepository;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ShoppingListItemController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	ShoppingListRepository shoppingListRepo;

	@Autowired
	ShoppingListItemRepository shoppingListItemRepo;

	@Autowired
	ShoppingListItemPriorityRepository shoppingListItemPriorityRepo;

	@Autowired
	ShoppingListItemNoteRepository shoppingListItemNoteRepo;
	
	@Autowired
	ShoppingListItemImageRepository shoppingListItemImageRepo;

	@GetMapping("/lists/{listId}/items/add")
	public String addListItem(Model model, @PathVariable(name = "listId") long listId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User u = userRepo.findOneByEmail(email);
		if (shoppingListRepo.findOne(listId).getUser().equals(u)) {
			model.addAttribute("user", u);
			model.addAttribute("listId", listId);
			ShoppingListItem sli = new ShoppingListItem();
			sli.setShoppingList(shoppingListRepo.findOne(listId));
			model.addAttribute("listItem", sli);
			model.addAttribute("priorities", shoppingListItemPriorityRepo.findAll());
			return "list_item_add";
		} else {
			return "redirect:/error";
		}
	}

	@PostMapping("/lists/{listId}/items/add")
	public String addListItemSave(Model model, @PathVariable(name = "listId") long listId,
			@RequestParam(name = "shoppingListItemNoteBody") String shoppingListItemNoteBody,
			@ModelAttribute @Valid ShoppingListItem listItem, BindingResult result) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User u = userRepo.findOneByEmail(email);
		if (shoppingListRepo.findOne(listId).getUser().equals(u)) {
			if (result.hasErrors()) {
				model.addAttribute("user", u);
				model.addAttribute("listId", listId);
				model.addAttribute("listItem", listItem);
				model.addAttribute("priorities", shoppingListItemPriorityRepo.findAll());
				return "list_item_edit";
			} else {
				ShoppingListItemNote slin = new ShoppingListItemNote();
				if (!"".equals(shoppingListItemNoteBody)) {
					slin.setBody(shoppingListItemNoteBody);
				} else {
					slin.setBody("");
				}
				shoppingListItemNoteRepo.save(slin);
				listItem.setShoppingListItemNote(slin);
				shoppingListItemRepo.save(listItem);
				return "redirect:/lists/" + listId;
			}
		} else {
			return "redirect:/error";
		}
	}

	@GetMapping("/lists/{listId}/items/{itemId}/edit")
	public String editListItem(Model model, @PathVariable(name = "listId") long listId,
			@PathVariable(name = "itemId") long itemId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User u = userRepo.findOneByEmail(email);
		if (shoppingListRepo.findOne(listId).getUser().equals(u)) {
			model.addAttribute("user", u);
			model.addAttribute("listId", listId);
			model.addAttribute("listItem", shoppingListItemRepo.findOne(itemId));
			model.addAttribute("priorities", shoppingListItemPriorityRepo.findAll());
			return "list_item_edit";
		} else {
			return "redirect:/error";
		}
	}

	@PostMapping("/lists/{listId}/items/{itemId}/edit")
	public String editListItemSave(Model model, @PathVariable(name = "listId") long listId,
			@PathVariable(name = "itemId") long itemId,
			@RequestParam(name = "shoppingListItemNoteId") Long shoppingListItemNoteId,
			@RequestParam(name = "shoppingListItemNoteBody") String shoppingListItemNoteBody,
			@RequestParam("file") MultipartFile file,
			@RequestParam(name = "removeImage", defaultValue = "false", required = false) boolean removeImage,
			@ModelAttribute @Valid ShoppingListItem listItem, BindingResult result) 
			{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User u = userRepo.findOneByEmail(email);
		if (shoppingListRepo.findOne(listId).getUser().equals(u)) {
			if (result.hasErrors()) {
				model.addAttribute("user", u);
				model.addAttribute("listId", listId);
				model.addAttribute("listItem", listItem);
				model.addAttribute("priorities", shoppingListItemPriorityRepo.findAll());
				return "list_item_edit";
			} else {
				if (shoppingListItemNoteId != null) {
					ShoppingListItemNote slin = shoppingListItemNoteRepo.findOne(shoppingListItemNoteId);
					slin.setBody(shoppingListItemNoteBody);
					shoppingListItemNoteRepo.save(slin);
					listItem.setShoppingListItemNote(slin);
				}else {
					if (removeImage) {
						// See if the user even has an user image
						ShoppingListItemImage image = shoppingListItemImageRepo. findByShoppingListItemId(listItem.getId());
						if (image != null) {
							// Removes if it exists
							shoppingListItemImageRepo.delete(image);
						}
					}
					// Check to see if there is an upload file
					else if (file != null && !file.isEmpty()) {

						try {
							// Load the file in the proper format(Spring does this!)

							// Load or create a UserImage
							ShoppingListItemImage image = shoppingListItemImageRepo. findByShoppingListItemId(listItem.getId());

							if (image == null) {
								image = new ShoppingListItemImage();
								image.setShoppingListItemId(listItem.getId());

							}
							image.setContentType(file.getContentType());
							image.setImage(file.getBytes());

							// Store in a Database
							shoppingListItemImageRepo.save(image);
						} catch (IOException e) {
						}
					}
				}
				listItem.setShoppingList(shoppingListRepo.findOne(listId));
				shoppingListItemRepo.save(listItem);
				return "redirect:/lists/" + listId;
			}
		} else {
			return "redirect:/error";
		}
	}

	@GetMapping("/lists/{listId}/items/{listItemId}")
	public String viewListItem(Model model, @PathVariable(name = "listId") long listId,
			@PathVariable(name = "listItemId") long listItemId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User u = userRepo.findOneByEmail(email);
		if (shoppingListRepo.findOne(listId).getUser().equals(u)) {
			model.addAttribute("user", u);
			model.addAttribute("listItem", shoppingListItemRepo.findOne(listItemId));
			return "list_item_view";
		} else {
			return "redirect:/error";
		}
	}

	@PostMapping("/lists/{listId}/items/{listItemId}/check")
	public String checkListItem(Model model, @PathVariable(name = "listId") long listId,
			@PathVariable(name = "listItemId") long listItemId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User u = userRepo.findOneByEmail(email);
		if (shoppingListRepo.findOne(listId).getUser().equals(u)) {
			ShoppingListItem sli = shoppingListItemRepo.findOne(listItemId);
			sli.setChecked(true);
			shoppingListItemRepo.save(sli);
			return "redirect:/lists/" + listId;
		} else {
			return "redirect:/error";
		}
	}

	@PostMapping("/lists/{listId}/items/{listItemId}/uncheck")
	public String uncheckListItem(Model model, @PathVariable(name = "listId") long listId,
			@PathVariable(name = "listItemId") long listItemId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User u = userRepo.findOneByEmail(email);
		if (shoppingListRepo.findOne(listId).getUser().equals(u)) {
			ShoppingListItem sli = shoppingListItemRepo.findOne(listItemId);
			sli.setChecked(false);
			shoppingListItemRepo.save(sli);
			return "redirect:/lists/" + listId;
		} else {
			return "redirect:/error";
		}
	}

	@PostMapping("/lists/{listId}/items/{listItemId}/delete")
	public String deleteListItem(Model model, @PathVariable(name = "listId") long listId,
			@PathVariable(name = "listItemId") long listItemId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User u = userRepo.findOneByEmail(email);
		if (shoppingListRepo.findOne(listId).getUser().equals(u)) {
			shoppingListItemRepo.delete(listItemId);
			return "redirect:/lists/" + listId;
		} else {
			return "redirect:/error";
		}
	}

}

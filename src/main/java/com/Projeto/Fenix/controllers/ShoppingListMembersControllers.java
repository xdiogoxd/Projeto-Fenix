package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.domain.shoppingList.ListMemberRoles;
import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import com.Projeto.Fenix.domain.shoppingList.ShoppingList;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.dtos.ListMembersDTO;
import com.Projeto.Fenix.services.ShoppingListMembersService;
import com.Projeto.Fenix.services.ShoppingListService;
import com.Projeto.Fenix.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingList/members")
public class ShoppingListMembersControllers {

    @Autowired
    UserService userService;

    @Autowired
    ShoppingListMembersService shoppingListMembersService;

    @Autowired
    ShoppingListService shoppingListService;

    @PostMapping()
    public ResponseEntity<String> addNewListMember(HttpServletRequest request, @RequestBody ListMembersDTO listMembersDTO) throws Exception {

        User requester = userService.findUserByToken(request);

        ShoppingList theList = shoppingListService.findShoppingListById(listMembersDTO.shoppingListId());

        shoppingListMembersService.validateUserAuthorization(requester,theList, ListMemberRoles.ADMIN);

        shoppingListMembersService.addListMember(requester, listMembersDTO.newMember(), listMembersDTO.role(), theList);

        return new ResponseEntity<>("Acesso do membro criado com sucesso!", HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<String> updateMemberAccess(HttpServletRequest  request, @RequestBody ListMembersDTO  listMembersDTO) throws Exception {

        User requester = userService.findUserByToken(request);

        ShoppingList theList = shoppingListService.findShoppingListById(listMembersDTO.shoppingListId());

        shoppingListMembersService.validateUserAuthorization(requester,theList, ListMemberRoles.ADMIN);

        shoppingListMembersService.updateListMemberAccess(listMembersDTO.member(), theList, listMembersDTO.role());

        return new ResponseEntity<>("Acesso do membro atualizado com sucesso!", HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> listMembersByList(HttpServletRequest request, @RequestBody ListMembersDTO listMembersDTO) throws Exception {
        User requester = userService.findUserByToken(request);

        ShoppingList theList = shoppingListService.findShoppingListById(listMembersDTO.shoppingListId());

        shoppingListMembersService.validateUserAuthorization(requester,theList, ListMemberRoles.ADMIN);

        List<User> theMembers = shoppingListMembersService.listAllMembersByList(theList);

        return new ResponseEntity<>(theMembers, HttpStatus.OK);
    }

    @GetMapping("/member")
    public ResponseEntity<List<ListMembers>> listAllListsByMember(HttpServletRequest request) throws Exception {
        User requester = userService.findUserByToken(request);

        List<ListMembers> theLists = shoppingListMembersService.listAllListsByMembers(requester);

        return new ResponseEntity<>(theLists, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteMemberAccess(HttpServletRequest request, @RequestBody ListMembersDTO listMembersDTO) throws Exception {

        User requester = userService.findUserByToken(request);

        ShoppingList theList = shoppingListService.findShoppingListById(listMembersDTO.shoppingListId());

        shoppingListMembersService.validateUserAuthorization(requester,theList, ListMemberRoles.ADMIN);

        shoppingListMembersService.deleteListMemberAccess(listMembersDTO.member(), theList);

        return new ResponseEntity<>("Acesso deletado com sucesso", HttpStatus.OK);
    }

}

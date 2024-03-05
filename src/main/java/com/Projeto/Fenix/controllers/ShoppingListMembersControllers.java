package com.Projeto.Fenix.controllers;

import com.Projeto.Fenix.domain.shoppingList.ListMembers;
import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.dtos.ListMembersDTO;
import com.Projeto.Fenix.services.ShoppingListMembersService;
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

    @PostMapping()
    public ResponseEntity<String> addNewListMember(HttpServletRequest request, @RequestBody ListMembersDTO listMembersDTO) throws Exception {

        User theOwner = userService.findUserByToken(request);

        shoppingListMembersService.addListMember(theOwner, listMembersDTO.newMember(), listMembersDTO.role(), listMembersDTO.shoppingListId());

        return new ResponseEntity<String>("Acesso do membro criado com sucesso!", HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<String> updateMemberAccess(HttpServletRequest  request, @RequestBody ListMembersDTO  listMembersDTO){

        User theOwner = userService.findUserByToken(request);

        shoppingListMembersService.updateListMemberAccess(theOwner, listMembersDTO.member(), listMembersDTO.role());

        return new ResponseEntity<String>("Acesso do membro atualizado com sucesso!", HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ListMembers>> listMembersByList(HttpServletRequest request, @RequestBody ListMembersDTO listMembersDTO){
        User requester = userService.findUserByToken(request);

        List<ListMembers> theMembers = shoppingListMembersService.listAllMembersByList(requester, listMembersDTO.shoppingListId());

        return new ResponseEntity<List<ListMembers>>(theMembers, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ListMembers>> listAllListsByMember(HttpServletRequest request, @RequestBody ListMembersDTO listMembersDTO){
        User requester = userService.findUserByToken(request);

        List<ListMembers> theLists = shoppingListMembersService.listAllListsByMembers(requester, listMembersDTO.member());

        return new ResponseEntity<List<ListMembers>>(theLists, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteMemberAccess(HttpServletRequest request, @RequestBody ListMembersDTO listMembersDTO){

        User theOwner = userService.findUserByToken(request);

        shoppingListMembersService.deleteListMemberAccess(theOwner, listMembersDTO.member());

        return new ResponseEntity<String>("Acesso deletado com sucesso", HttpStatus.OK);
    }

}

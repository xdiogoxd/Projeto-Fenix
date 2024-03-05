package com.Projeto.Fenix.dtos;

import com.Projeto.Fenix.domain.shoppingList.ListMemberRoles;

import java.util.UUID;

public record ListMembersDTO (UUID shoppingListId, UUID newMember, UUID member, ListMemberRoles role){
}

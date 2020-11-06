package com.example.demo.services;

public class DeleteService {

    CandidateService candidateService;
    ChatService chatService;
    CheckUserService checkUserService;
    ProfileHandler profileHandler;
    PhotoHandler photoHandler;

    public DeleteService(CandidateService candidateService, ChatService chatService, CheckUserService checkUserService, ProfileHandler profileHandler, PhotoHandler photoHandler) {
        this.candidateService = candidateService;
        this.chatService = chatService;
        this.checkUserService = checkUserService;
        this.profileHandler = profileHandler;
        this.photoHandler = photoHandler;
    }

    public void deleteAllUserData(){
        //Delegates to services
    }

    public void deleteConversationAndLikeRelation(){
        //2 scenarios: remove like or remove conversation
    }
}

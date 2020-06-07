package com.project.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

import javax.inject.Named;

import lombok.AllArgsConstructor;
import lombok.Data;

@Named
@AllArgsConstructor
@ApplicationScoped
@Data
public class MessageHolder implements Serializable{

    
    private static final long serialVersionUID = 1974510449199395815L;  

    
    private List<MessageDTO> responseMessagesList = new ArrayList<>();

    public MessageHolder() {

    }

    
}

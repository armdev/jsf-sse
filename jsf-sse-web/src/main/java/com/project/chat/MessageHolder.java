package com.project.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@AllArgsConstructor
@ApplicationScoped
public class MessageHolder implements Serializable{

    
    private static final long serialVersionUID = 1974510449199395815L;  

    @Getter
    @Setter
    private List<MessageDTO> responseMessagesList = new ArrayList<>();

    public MessageHolder() {

    }

    
}

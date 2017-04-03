package com.project.chat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author armdev
 */
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class MessageDTO {
    
    @Setter
    @Getter
    private String message;    
    
    
}

package com.scm.scm20.helper;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private String content;
    private MessageType type = MessageType.green;

    @Override
    public String toString() {
        return content;
    }
}

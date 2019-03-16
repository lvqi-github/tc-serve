package com.tcxx.serve.wechat.model.message.output;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("xml")
public class TextOutputMessage {

    /**
     * 文本消息
     */
    @XStreamAlias("Content")
    private String content;
}

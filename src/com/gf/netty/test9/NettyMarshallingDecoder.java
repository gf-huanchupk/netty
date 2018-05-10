package com.gf.netty.test9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

public class NettyMarshallingDecoder extends MarshallingDecoder{    
    
    public NettyMarshallingDecoder(UnmarshallerProvider provider) {    
        super(provider);    
    }    
        
    public NettyMarshallingDecoder(UnmarshallerProvider provider, int maxObjectSize) {    
        super(provider, maxObjectSize);    
    }    
        
    @Override    
    public Object decode(ChannelHandlerContext arg0, ByteBuf arg1)    
            throws Exception {    
        return super.decode(arg0, arg1);    
    } 

}

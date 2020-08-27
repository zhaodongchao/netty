/*
 * Copyright 2020 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel.uring;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.DefaultSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import io.netty.channel.uring.AbstractIOUringStreamChannel.IOUringStreamUnsafe;


import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Collections;

public final class IOUringSocketChannel extends AbstractIOUringStreamChannel implements SocketChannel {
    private final IOUringSocketChannelConfig config;
    //private volatile Collection<InetAddress> tcpMd5SigAddresses = Collections.emptyList();

    public IOUringSocketChannel() {
       super(null, LinuxSocket.newSocketStream(), false);
       this.config = new IOUringSocketChannelConfig(this);
    }

    IOUringSocketChannel(final Channel parent, final LinuxSocket fd) {
        super(parent, fd);
        this.config = new IOUringSocketChannelConfig(this);
    }

   IOUringSocketChannel(Channel parent, LinuxSocket fd, InetSocketAddress remoteAddress) {
        super(parent, fd, remoteAddress);
        this.config = new IOUringSocketChannelConfig(this);

//        if (parent instanceof IOUringSocketChannel) {
//            tcpMd5SigAddresses = ((IOUringSocketChannel) parent).tcpMd5SigAddresses();
//        }
    }

    @Override
    public ServerSocketChannel parent() {
        return (ServerSocketChannel) super.parent();
    }

    @Override
    protected AbstractUringUnsafe newUnsafe() {
        return new IOUringStreamUnsafe();
    }

    @Override
    public IOUringSocketChannelConfig config() {
        return config;
    }

    @Override
    public FileDescriptor fd() {
        return super.fd();
    }

    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }
}

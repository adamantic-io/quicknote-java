package io.adamantic.quicknote.amqp;

import io.adamantic.quicknote.Quicknote;
import io.adamantic.quicknote.exceptions.ChannelNotFound;

public class Main {
    public static void main(String[] args) throws ChannelNotFound {
        Quicknote qn = new Quicknote();
        var snd = qn.getSender("events/uuid");
        System.out.printf("Got sender {%s}\n", snd);
    }
}

package me.rowwyourboat.doublelife_v2.records.ui;

import me.rowwyourboat.doublelife_v2.enums.EActionType;

import java.util.List;

public record RAction(
        EActionType actionType,

        String expect,
        List<String> prompt,

        String destination
) {

    public RAction(EActionType actionType, String expect, List<String> prompt, String destination) {
        this.actionType = actionType;
        this.expect = expect;
        this.prompt = prompt;
        this.destination = destination;
    }

    public RAction(EActionType actionType) {
        this(actionType, null, null, null);
    }

    public RAction(EActionType actionType, String expect, List<String> prompt) {
        this(actionType, expect, prompt, null);
    }

    public RAction(EActionType actionType, String destination) {
        this(actionType, null, null, destination);
    }

}
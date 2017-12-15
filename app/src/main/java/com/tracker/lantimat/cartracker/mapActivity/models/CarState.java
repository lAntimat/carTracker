package com.tracker.lantimat.cartracker.mapActivity.models;

/**
 * Created by GabdrakhmanovII on 15.12.2017.
 */

public class CarState {

    String stateName;
    String stateValue;

    public CarState(String stateName, String stateValue) {
        this.stateName = stateName;
        this.stateValue = stateValue;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateValue() {
        return stateValue;
    }

    public void setStateValue(String stateValue) {
        this.stateValue = stateValue;
    }
}

/*
 * Copyright (c) 2018, E. G. Bland
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.muneyamaru.lightcontroller.philips;

import net.muneyamaru.lightcontroller.ControllableLight;
import org.json.simple.JSONObject;

/**
 *
 * @author E. G. Bland
 */
public class HueLight implements ControllableLight {
    private final String id;
    private final String user;
    private final String bridgeIP;
    private final HueAPIHandler handler;
    private final String base;
    
    private String name = "";
    private JSONObject state = new JSONObject();

    public HueLight(String id, JSONObject lightJson, String user, String bridgeIP, HueAPIHandler handler) {
        this.id = id;
        this.user = user;
        this.bridgeIP = bridgeIP;
        this.handler = handler;
        this.base = "http://" + this.bridgeIP + "/api/" + this.user + "/" + this.id;
        
        update(lightJson);
    }
    
    public void update() {
        JSONObject obj = handler.apiCall("lights/"+this.id,new JSONObject(),"GET");
        //System.out.println(obj.toJSONString());
        update((JSONObject)obj.get("response"));
    }
    
    private void update(JSONObject json) {
        this.name = (String)json.get("name");
        this.state = (JSONObject)json.get("state");
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void turnOn() {
        JSONObject json = new JSONObject();
        json.put("on",true);
        //System.out.println(json.toJSONString());
        JSONObject response = handler.apiCall("lights/" + id + "/state",json,"PUT");
        //System.out.println(response.toJSONString());
    }

    @Override
    public void turnOff() {
        JSONObject json = new JSONObject();
        json.put("on",false);
        //System.out.println(json.toJSONString());
        JSONObject response = handler.apiCall("lights/" + id + "/state",json,"PUT");
        //System.out.println(response.toJSONString());
    }

    @Override
    public boolean isOn() {
        //System.out.println(state.toJSONString());
        return (boolean)state.get("on");
    }
    
    @Override
    public boolean isReachable() {
        return (boolean)state.get("reachable");
    }

    @Override
    public void setBrightness(int brightness) {
        if(brightness<getMinBrightness() || brightness>getMaxBrightness()) throw new IllegalArgumentException("Brightness out of range.");
        JSONObject json = new JSONObject();
        json.put("bri",brightness);
        //System.out.println(json.toJSONString());
        JSONObject response = handler.apiCall("lights/" + id + "/state",json,"PUT");
        //System.out.println(response.toJSONString());
    }

    @Override
    public int getBrightness() {
        return (int)((long)state.get("bri"));
    }

    @Override
    public int getMinBrightness() {
        return 0;
    }

    @Override
    public int getMaxBrightness() {
        return 254;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public void setHue(int hue) {
        if(hue<getMinHue() || hue>getMaxHue()) throw new IllegalArgumentException("Hue out of range. (" + hue + ")");
        JSONObject json = new JSONObject();
        json.put("hue",hue);
        //System.out.println(json.toJSONString());
        JSONObject response = handler.apiCall("lights/" + id + "/state",json,"PUT");
        //System.out.println(response.toJSONString());
    }

    @Override
    public void setSat(int sat) {
        if(sat<getMinSat() || sat>getMaxSat()) throw new IllegalArgumentException("Saturation out of range.");
        JSONObject json = new JSONObject();
        json.put("sat",sat);
        //System.out.println(json.toJSONString());
        JSONObject response = handler.apiCall("lights/" + id + "/state",json,"PUT");
        //System.out.println(response.toJSONString());
    }

    @Override
    public int getHue() {
        return (int)((long)state.get("hue"));
    }

    public String getHueColorName() {
        int hue = getHue();
        hue = (int)(hue * (360.0f/(float)getMaxHue()));
        if(hue<=14 || hue>=345) return "RED";
        if(hue==15) return "REDDISH";
        if(hue>=16&&hue<=45) return "ORANGE";
        if(hue>=46&&hue<=70) return "YELLOW";
        if(hue>=71&&hue<=79) return "LIME";
        if(hue>=80&&hue<=163) return "GREEN";
        if(hue>=164&&hue<=193) return "CYAN";
        if(hue>=194&&hue<=240) return "BLUE";
        if(hue>=241&&hue<=260) return "INDIGO";
        if(hue>=261&&hue<=270) return "VIOLET";
        if(hue>=271&&hue<=291) return "PURPLE";
        if(hue>=292&&hue<=327) return "MAGENTA";
        if(hue>=328&&hue<=344) return "ROSE";
        
        return "UNKNOWN";
    }
    
    @Override
    public int getSat() {
        return (int)((long)state.get("sat"));
    }

    @Override
    public int getMinHue() {
        return 0;
    }

    @Override
    public int getMaxHue() {
        return 65534;
    }

    @Override
    public int getMinSat() {
        return 0;
    }

    @Override
    public int getMaxSat() {
        return 254;
    }

    @Override
    public void setCanonicalBrightness(float bri) {
        this.setBrightness((int)(bri * getMaxBrightness()));
    }

    @Override
    public float getCanonicalBrightness() {
        return (float)getBrightness()/getMaxBrightness();
    }

    @Override
    public void setCanonicalHue(float hue) {
        this.setHue((int)(hue * getMaxHue()));
    }

    @Override
    public float getCanonicalHue() {
        return (float)getHue()/getMaxHue();
    }

    @Override
    public void setCanonicalSat(float sat) {
        this.setSat((int)sat * getMaxSat());
    }

    @Override
    public float getCanonicalSat() {
        return (float)getSat()/getMaxSat();
    }
}
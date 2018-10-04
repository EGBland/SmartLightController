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

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.muneyamaru.lightcontroller.ControllableLight;
import org.json.simple.JSONObject;

/**
 *
 * @author E. G. Bland
 */
public class PhilipsHueController {
    private final Map<String,HueLight> lights = new TreeMap<>();
    private final String user;
    private final String bridgeIP;
    
    private final HueAPIHandler handler;
    
    public PhilipsHueController(String user, String bridgeIP) {
        this.user = user;
        this.bridgeIP = bridgeIP;
        
        this.handler = new HueAPIHandler(this.user,this.bridgeIP);
    }
    
    public List<HueLight> getLights() {
        List<HueLight> ret = new ArrayList<>();
        JSONObject response = handler.apiCall("lights", new JSONObject(), "GET");
        System.out.println(response.toJSONString());
        JSONObject data = (JSONObject)response.get("response");
        
        data.entrySet().forEach((pair) -> {
            Map.Entry cast = (Map.Entry)pair;
            ret.add(new HueLight(String.valueOf(cast.getKey()),(JSONObject)cast.getValue(),user,bridgeIP,handler));
        });
        return ret;
    }
}

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
package net.muneyamaru.fortniteapi;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import net.muneyamaru.lightcontroller.philips.HueAPIHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author E. G. Bland
 */
public class FortniteAPIHandler {
    private static transient final String API_KEY = "4c81d74ba3696916a76cefad719e8bbe";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final int HTTP_BUFFER_LENGTH = 1024;
    
    private final String apiBase = "https://fortnite-public-api.theapinetwork.com/prod09";
    
    public JSONObject apiCall(String resource, JSONObject body, String method) {
        JSONObject obj = new JSONObject();
        try {
            System.out.println("Requesting " + this.apiBase + "/" + resource + " with method " + method);
            final URL url = new URL(this.apiBase + "/" + resource);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            
            // fortnite auth stuff
            connection.setRequestProperty("Authorization",API_KEY);
            connection.setRequestProperty("X-Fortnite-API-Version","v1.1");
            
            connection.setRequestProperty("User-Agent",USER_AGENT);   
            connection.setRequestMethod(method);
            if(method.equals("PUT") || method.equals("POST")) {
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Length",Integer.toString(body.toJSONString().length()));
                connection.getOutputStream().write(body.toJSONString().getBytes("UTF-8"));
            }
            
            InputStream is = connection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[HTTP_BUFFER_LENGTH];
            int n;
            
            while((n = is.read(buf)) != -1) baos.write(buf,0,n);
            
            JSONParser parser = new JSONParser();
            Object response = parser.parse(new String(baos.toByteArray())); // TODO localise
            obj.put("response",response);
            
            return obj;
        } catch(Exception ex) { // TODO narrower exception types, handle
            ex.printStackTrace();
            return obj;
        }
    }
}

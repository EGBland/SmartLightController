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
package net.muneyamaru.lightcontroller;

import java.util.List;
import net.muneyamaru.lightcontroller.philips.PhilipsHueController;
import net.muneyamaru.lightcontroller.philips.HueLight;

/**
 *
 * @author E. G. Bland
 */
public class Main {
    public static final String HUE_USER = "SEHTES3WusOrQ1b30PvxTmvYSPsFaWHAtGEKwWBh";
    public static final String HUE_BRIDGE_IP = "192.168.1.173";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        PhilipsHueController controller = new PhilipsHueController(HUE_USER,HUE_BRIDGE_IP);
        List<HueLight> lights = controller.getLights();
        lights.forEach((l) -> {
            System.out.println(l.getId() + "\t" + l.getName() + "\t" + (l.isOn()?"ON":"OFF"));
            
        });
        
        HueLight light1 = lights.get(2),light2=lights.get(3);
        /*
        // FLASH RED
        light1.setHue(0);
        light1.setSat(255);
        light1.setBrightness(0);
        light2.setHue(0);
        light2.setSat(255);
        light2.setBrightness(0);
        light1.turnOn();
        light2.turnOn();
        //System.out.println(light.getId());
        while(true) {
            light1.setBrightness(255);
            light2.setBrightness(255);
            Thread.sleep(500);
            light1.setBrightness(0);
            light2.setBrightness(0);
            Thread.sleep(500);
        }*/
        
        light1.setHue(0);
        light1.setSat(255);
        light1.setBrightness(255);
        light2.setHue(0);
        light2.setSat(255);
        light2.setBrightness(255);
        light1.turnOn();
        light2.turnOn();
        
        for(int i=0;i<65000;i+=1000) {
            light1.setHue(i);
            light2.setHue(i);
            Thread.sleep(500);
        }
        
        light1.turnOff();
        light2.turnOff();
    }
    
}

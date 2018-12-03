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

/**
 *
 * @author E. G. Bland
 */
public interface ControllableLight {
    public String getId();
    public String getName();
    
    public void turnOn();
    public void turnOff();
    public boolean isOn();
    public boolean isReachable();
    
    public void setBrightness(int brightness);
    public int getBrightness();
    public int getMinBrightness();
    public int getMaxBrightness();
    public void setCanonicalBrightness(float bri);
    public float getCanonicalBrightness();
    
    public void setHue(int hue);
    public int getHue();
    public int getMinHue();
    public int getMaxHue();
    public void setCanonicalHue(float hue);
    public float getCanonicalHue();
    
    public void setSat(int sat);
    public int getSat();
    public int getMinSat();
    public int getMaxSat();
    public void setCanonicalSat(float sat);
    public float getCanonicalSat();
    
    public void setTransitionTime(long ttmillis);
    public long getTransitionTime();
    
    public void setLive(boolean isLive);
    public boolean getLive();
    public void flush();
}

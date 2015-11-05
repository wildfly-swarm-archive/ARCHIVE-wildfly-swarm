/*
 * Copyright 2015 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wildfly.swarm.webservices;

public class HostInfo {
    private String wsdlHost = null;
    private String wsdlPort = null;
    private UriScheme wsdlUriScheme = UriScheme.UNDEFINED;

    public enum UriScheme {
        HTTP,HTTPS,UNDEFINED
    }

    public HostInfo(String hostName, String port, UriScheme uriScheme) {
        this.wsdlHost = hostName;
        this.wsdlPort = port;
        this.wsdlUriScheme = uriScheme;
    }

    public HostInfo() {
    }

    public HostInfo http() {
        this.wsdlUriScheme = UriScheme.HTTP;
        return this;
    }

    public HostInfo https() {
        this.wsdlUriScheme = UriScheme.HTTPS;
        return this;
    }

    public HostInfo port(String port) {
        this.setWsdlPort(port);
        return this;
    }

    public HostInfo host(String wsdlHost) {
        this.setWsdlHost(wsdlHost);
        return this;
    }

    public String getWsdlHost() {
        return wsdlHost;
    }

    public void setWsdlHost(String wsdlHost) {
        this.wsdlHost = wsdlHost;
    }

    public String getWsdlPort() {
        return wsdlPort;
    }

    public void setWsdlPort(String wsdlPort) {
        this.wsdlPort = wsdlPort;
    }

    public UriScheme getWsdlUriScheme() {
        return wsdlUriScheme;
    }

    public void setWsdlUriScheme(UriScheme wsdlUriScheme) {
        this.wsdlUriScheme = wsdlUriScheme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HostInfo hostInfo = (HostInfo) o;

        if (wsdlHost != null ? !wsdlHost.equals(hostInfo.wsdlHost) : hostInfo.wsdlHost != null) return false;
        if (wsdlPort != null ? !wsdlPort.equals(hostInfo.wsdlPort) : hostInfo.wsdlPort != null) return false;
        return wsdlUriScheme == hostInfo.wsdlUriScheme;

    }

    @Override
    public int hashCode() {
        int result = wsdlHost != null ? wsdlHost.hashCode() : 0;
        result = 31 * result + (wsdlPort != null ? wsdlPort.hashCode() : 0);
        result = 31 * result + (wsdlUriScheme != null ? wsdlUriScheme.hashCode() : 0);
        return result;
    }
}

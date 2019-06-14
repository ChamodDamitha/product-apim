/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.am.integration.test.utils.bean;

import org.json.JSONObject;

public class Operation {
    private String uritemplate;
    private String httpVerb;
    private String throttlingPolicy;
    private String authType;

    public Operation(String uritemplate, String httpVerb, String throttlingPolicy, String authType) {
        this.uritemplate = uritemplate;
        this.httpVerb = httpVerb;
        this.throttlingPolicy = throttlingPolicy;
        this.authType = authType;
    }

    public String getUritemplate() {
        return uritemplate;
    }

    public String getHttpVerb() {
        return httpVerb;
    }

    public String getThrottlingPolicy() {
        return throttlingPolicy;
    }

    public String getAuthType() {
        return authType;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uritemplate", uritemplate);
        jsonObject.put("httpVerb", httpVerb);
        jsonObject.put("throttlingPolicy", throttlingPolicy);
        jsonObject.put("authType", authType);
        return jsonObject;
    }
}

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.wso2.am.integration.test.utils.APIManagerIntegrationTestException;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;


public class APIRequestRestAPI {

    private static final Log log = LogFactory.getLog(APIRequestRestAPI.class);

    private String name;
    private String description = "description";
    private String context;
    private String version = "1.0.0";
    private String provider = "admin";
    private String[] transport = {"http","https"};
    private String[] tags = {"tags"};
    private String[] policies = {"Unlimited"};
    private String[] securityScheme = {"oauth2"};
    private String visibility = "PUBLIC";
    private String[] gatewayEnvironments = {"Production and Sandbox"};
    private JSONArray endpoint;
    private ArrayList<Operation> operations = new ArrayList<>();

    public String generateRequestPayloadJson() {
        JSONObject payload = new JSONObject();
        payload.put("name", name);
        payload.put("description", getDescription());
        payload.put("context", context);
        payload.put("version", getVersion());
        payload.put("provider", getProvider());
        payload.put("transport", getTransport());
        payload.put("tags", getTags());
        payload.put("policies", getPolicies());
        payload.put("securityScheme", getSecurityScheme());
        payload.put("visibility", getVisibility());
        payload.put("gatewayEnvironments", getGatewayEnvironments());
        payload.put("endpoint", getEndpoint());

        JSONArray operationArr = new JSONArray();
        for (Operation o : operations) {
            operationArr.put(o.toJSON());
        }
        payload.put("operations", operationArr);
        return payload.toString();
    }

    /**
     * This method will create API request.
     *
     * @param apiName     - Name of the API
     * @param context     - API context
     * @param endpointUrl - API endpoint URL
     * @throws APIManagerIntegrationTestException - Throws if API request cannot be generated.
     */
    public APIRequestRestAPI(String apiName, String context, URL endpointUrl) throws APIManagerIntegrationTestException {
        this.name = apiName;
        this.context = context;

        JSONArray endpoints = new JSONArray();
        endpoints.put(new JSONObject("{\"inline\": {\"endpointConfig\": {\"list\": [{\"url\": \"" + endpointUrl + "\"," +
                "\"timeout\": \"1000\"}],\"endpointType\": \"SINGLE\"},\"type\": \"http\"},\"type\": \"production_endpoints\"}"));

        this.endpoint = endpoints;
    }

    public APIRequestRestAPI(String apiName, String context, URI productionEndpointUri, URI sandboxEndpointUri)
            throws APIManagerIntegrationTestException {
        this.name = apiName;
        this.context = context;

        JSONArray endpoints = new JSONArray();
        endpoints.put(new JSONObject("{\"inline\": {\"endpointConfig\": {\"list\": [{\"url\": \"" + productionEndpointUri + "\"," +
                "\"timeout\": \"1000\"}],\"endpointType\": \"SINGLE\"},\"type\": \"http\"},\"type\": \"production_endpoints\"}"));
        endpoints.put(new JSONObject("{\"inline\": {\"endpointConfig\": {\"list\": [{\"url\": \"" + sandboxEndpointUri + "\"," +
                "\"timeout\": \"1000\"}],\"endpointType\": \"SINGLE\"},\"type\": \"http\"},\"type\": \"sandbox_endpoints\"}"));

        this.endpoint = endpoints;
    }


    /**
     * This method will create API request.
     *
     * @param apiName               - Name of the API
     * @param context               - API context
     * @param productionEndpointUrl - API endpoint URL
     * @throws APIManagerIntegrationTestException - Throws if API request cannot be generated.
     */
    public APIRequestRestAPI(String apiName, String context, URL productionEndpointUrl, URL sandboxEndpointUrl) throws APIManagerIntegrationTestException {
        this.name = apiName;
        this.context = context;

        JSONArray endpoints = new JSONArray();
        endpoints.put(new JSONObject("{\"inline\": {\"endpointConfig\": {\"list\": [{\"url\": \"" + productionEndpointUrl + "\"," +
                "\"timeout\": \"1000\"}],\"endpointType\": \"SINGLE\"},\"type\": \"http\"},\"type\": \"production_endpoints\"}"));
        endpoints.put(new JSONObject("{\"inline\": {\"endpointConfig\": {\"list\": [{\"url\": \"" + sandboxEndpointUrl + "\"," +
                "\"timeout\": \"1000\"}],\"endpointType\": \"SINGLE\"},\"type\": \"http\"},\"type\": \"sandbox_endpoints\"}"));

        this.endpoint = endpoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String[] getTransport() {
        return transport;
    }

    public void setTransport(String[] transport) {
        this.transport = transport;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getPolicies() {
        return policies;
    }

    public JSONArray getEndpoint() {
        return endpoint;
    }

    public void setPolicies(String[] policies) {
        this.policies = policies;
    }

    public String[] getSecurityScheme() {
        return securityScheme;
    }

    public void setSecurityScheme(String[] securityScheme) {
        this.securityScheme = securityScheme;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String[] getGatewayEnvironments() {
        return gatewayEnvironments;
    }

    public void setGatewayEnvironments(String[] gatewayEnvironments) {
        this.gatewayEnvironments = gatewayEnvironments;
    }

    public void addOperation(Operation operation) {
        this.operations.add(operation);
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}

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

package org.wso2.am.integration.test.utils.clients;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.ws.security.util.Base64;
import org.json.JSONObject;
import org.wso2.am.integration.test.utils.APIManagerIntegrationTestException;
import org.wso2.am.integration.test.utils.bean.*;
import org.wso2.am.integration.test.utils.http.HTTPSClientUtils;
import org.wso2.carbon.automation.test.utils.http.client.HttpResponse;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIPublisherRestClient2 {
    private static final Log log = LogFactory.getLog(APIPublisherRestClient2.class);
    private final String REST_API_VERSION = "v0.14";
    private String backendURL;
    private String publisherRestApiBaseUrl;
    private String accessToken;
    private String refreshToken;

    /**
     * construct of API rest client
     *
     * @param backendURL - backend URL of the publisher Jaggery app
     */
    public APIPublisherRestClient2(String backendURL) {
        this.backendURL = backendURL;
        publisherRestApiBaseUrl = backendURL + "api/am/publisher/" + REST_API_VERSION + "/";
    }

    /**
     * register to publisher app
     *
     * @param userName - provided user name
     * @param password - password
     * @return HTTP response object
     * @throws APIManagerIntegrationTestException - Throws if user cannot register to the publisher
     */
    public HttpResponse login(String userName, String password)
            throws APIManagerIntegrationTestException {
        HttpResponse response;
        log.info("Register a client to Publisher " + backendURL + " as the user " + userName);

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", "Basic " + Base64.encode((userName + ":" + password).getBytes()));
        String payload = "{" +
                "\"callbackUrl\": \"www.google.lk\"," +
                "\"clientName\": \"rest_api_publisher\"," +
                "\"owner\": \"admin\"," +
                "\"grantType\": \"password refresh_token\"," +
                "\"saasApp\": true" +
                "}";
        requestHeaders.put("Content-Type", "application/json");

        try {
            response = HTTPSClientUtils.doPost(
                    backendURL + "client-registration/v0.14/register", requestHeaders, payload);
        } catch (Exception e) {
            throw new APIManagerIntegrationTestException("Unable to register to the publisher app ", e);
        }

        JSONObject responseJson = new JSONObject(response.getData());

        requestHeaders.put("Authorization", "Basic " +
                Base64.encode((responseJson.get("clientId") + ":" + responseJson.get("clientSecret")).getBytes()));
        requestHeaders.put("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("grant_type", "password"));
        urlParameters.add(new BasicNameValuePair("username", userName));
        urlParameters.add(new BasicNameValuePair("password", password));
        urlParameters.add(new BasicNameValuePair("scope", "apim:api_create apim:api_publish"));

        try {
            response = HTTPSClientUtils.doPost(
                    "https://localhost:8743/token", requestHeaders, urlParameters);
        } catch (Exception e) {
            throw new APIManagerIntegrationTestException("Unable to register to the publisher app ", e);
        }

        JSONObject tokenResponseJson = new JSONObject(response.getData());
        accessToken = tokenResponseJson.getString("access_token");
        refreshToken = tokenResponseJson.getString("refresh_token");

        return response;
    }

    /**
     * Facilitate add API into publisher node
     *
     * @return http response object
     * @throws APIManagerIntegrationTestException - Throws if API addition fails
     */
    public HttpResponse addAPI(APIRequestRestAPI apiRequest) throws APIManagerIntegrationTestException {
        try {
            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("Content-Type", "application/json");
            requestHeaders.put("Authorization", "Bearer " + accessToken);

            HttpResponse httpResponse = HTTPSClientUtils.doPost(
                    new URL("https://localhost:9943/api/am/publisher/v1.0/apis"),
                    requestHeaders, apiRequest.generateRequestPayloadJson());

            return httpResponse;
        } catch (Exception e) {
            throw new APIManagerIntegrationTestException("Unable to add API. Error: " + e.getMessage(), e);
        }
    }

    /**
     * change status of a created API
     *
     * @return http response object
     * @throws APIManagerIntegrationTestException - throws if change lifecycle state fails
     */
    public HttpResponse  changeAPILifeCycleStatus(String apiId, String action)
            throws APIManagerIntegrationTestException {
        try {
            Thread.sleep(1000); // this is to make sure timestamps of current and next lifecycle states are different
            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("Authorization", "Bearer " + accessToken);
            requestHeaders.put("Content-Type", "application/json");

            String urlParams = "apiId=" + apiId + "&action=" + action;

            return HTTPSClientUtils.doPost( publisherRestApiBaseUrl + "apis/change-lifecycle?" + urlParams,
                    requestHeaders, "");
        } catch (Exception e) {
            throw new APIManagerIntegrationTestException("Unable to API lifecycle. Error: " + e.getMessage(), e);
        }

    }

//    /**
//     * delete API
//     *
//     * @param apiName  - API name
//     * @param version  - API version
//     * @param provider - name of the provider
//     * @return http response object
//     * @throws APIManagerIntegrationTestException - Throws if API delete fails
//     */
//    public HttpResponse deleteAPI(String apiName, String version, String provider)
//            throws APIManagerIntegrationTestException {
//        try {
//            Map<String, String> requestHeaders = new HashMap<>();
//            requestHeaders.put("Content-Type", "application/json");
//            requestHeaders.put("Authorization", "Bearer " + accessToken);
//
//            HttpResponse httpResponse = HTTPSClientUtils.doPost(
//                    new URL("https://localhost:9943/api/am/publisher/v1.0/apis"),
//                    requestHeaders, apiRequest.generateRequestPayloadJson());
//            return httpResponse;
//        } catch (Exception e) {
//            throw new APIManagerIntegrationTestException("Unable to add API. Error: " + e.getMessage(), e);
//        }
//    }


}

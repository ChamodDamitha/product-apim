# WSO2 API Manager Performance Test Results

During each release, we execute various automated performance test scenarios and publish the results.

| Test Scenarios | Description |
| --- | --- |
| Passthrough | A secured API, which directly invokes the back-end service. |
| Mediation | A secured API, which has a mediation extension to modify the message. |

Our test client is [Apache JMeter](https://jmeter.apache.org/index.html). We test each scenario for a fixed duration of
time. We split the test results into warmup and measurement parts and use the measurement part to compute the
performance metrics.

Test scenarios use a [Netty](https://netty.io/) based back-end service which echoes back any request
posted to it after a specified period of time.

We run the performance tests under different numbers of concurrent users, message sizes (payloads) and back-end service
delays.

The main performance metrics:

1. **Throughput**: The number of requests that the WSO2 API Manager processes during a specific time interval (e.g. per second).
2. **Response Time**: The end-to-end latency for an operation of invoking an API. The complete distribution of response times was recorded.

In addition to the above metrics, we measure the load average and several memory-related metrics.

The following are the test parameters.

| Test Parameter | Description | Values |
| --- | --- | --- |
| Scenario Name | The name of the test scenario. | Refer to the above table. |
| Heap Size | The amount of memory allocated to the application | 1G |
| Concurrent Users | The number of users accessing the application at the same time. | 1, 50, 100, 200 |
| Message Size (Bytes) | The request payload size in Bytes. | 50, 1024 |
| Back-end Delay (ms) | The delay added by the back-end service. | 0, 30 |

The duration of each test is **120 seconds**. The warm-up period is **60 seconds**.
The measurement results are collected after the warm-up period.

A [**c5.large** Amazon EC2 instance](https://aws.amazon.com/ec2/instance-types/) was used to install WSO2 API Manager.

The following are the measurements collected from each performance test conducted for a given combination of
test parameters.

| Measurement | Description |
| --- | --- |
| Error % | Percentage of requests with errors |
| Average Response Time (ms) | The average response time of a set of results |
| Standard Deviation of Response Time (ms) | The “Standard Deviation” of the response time. |
| 99th Percentile of Response Time (ms) | 99% of the requests took no more than this time. The remaining samples took at least as long as this |
| Throughput (Requests/sec) | The throughput measured in requests per second. |
| Average Memory Footprint After Full GC (M) | The average memory consumed by the application after a full garbage collection event. |

The following is the summary of performance test results collected for the measurement period.

|  Scenario Name | Heap Size | Concurrent Users | Message Size (Bytes) | Back-end Service Delay (ms) | Error % | Throughput (Requests/sec) | Average Response Time (ms) | Standard Deviation of Response Time (ms) | 99th Percentile of Response Time (ms) | WSO2 API Manager GC Throughput (%) | Average WSO2 API Manager Memory Footprint After Full GC (M) |
|---|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|
|  mediation | 1G | 1 | 50 | 0 | 0 | 942.11 | 1.03 | 3.09 | 3 | 96.52 | 83.035 |
|  mediation | 1G | 1 | 50 | 30 | 0 | 16.8 | 59.36 | 100.91 | 603 | 97.81 | 83.891 |
|  mediation | 1G | 1 | 1024 | 0 | 0 | 826.49 | 1.17 | 1.64 | 2 | 96.56 | 81.057 |
|  mediation | 1G | 1 | 1024 | 30 | 0 | 19.28 | 51.73 | 73 | 559 | 97.97 | 84.75 |
|  mediation | 1G | 50 | 50 | 0 | 0 | 1776.76 | 28.09 | 40.5 | 113 | 93.98 | 165.416 |
|  mediation | 1G | 50 | 50 | 30 | 0 | 1380.44 | 36.15 | 24.93 | 72 | 95.27 | 119.535 |
|  mediation | 1G | 50 | 1024 | 0 | 0 | 1575.96 | 31.65 | 46.14 | 114 | 93.52 | 173.156 |
|  mediation | 1G | 50 | 1024 | 30 | 0 | 1255.14 | 39.75 | 26.13 | 75 | 95.35 | 125.899 |
|  mediation | 1G | 100 | 50 | 0 | 0 | 1913.7 | 52.17 | 88.14 | 175 | 92.11 | 255.753 |
|  mediation | 1G | 100 | 50 | 30 | 0 | 1973.86 | 50.55 | 49.35 | 113 | 93.79 | 175.195 |
|  mediation | 1G | 100 | 1024 | 0 | 0 | 1633.91 | 61.06 | 56.37 | 186 | 93.85 | 157.373 |
|  mediation | 1G | 100 | 1024 | 30 | 0 | 1525.08 | 65.42 | 60.19 | 139 | 93.44 | 174.436 |
|  mediation | 1G | 200 | 50 | 0 | 0 | 1892.6 | 105.43 | 135.33 | 323 | 91.19 | 297.123 |
|  mediation | 1G | 200 | 50 | 30 | 0 | 1911.52 | 104.37 | 114.14 | 231 | 91.98 | 252.317 |
|  mediation | 1G | 200 | 1024 | 0 | 0 | 1590.52 | 125.52 | 83.95 | 315 | 93.51 | 159.13 |
|  mediation | 1G | 200 | 1024 | 30 | 0 | 0.04 | 62976 | 12128.3 | 80383 | 97.95 | 86.664 |
|  passthrough | 1G | 1 | 50 | 0 | 0 | 1201.02 | 0.8 | 3.84 | 2 | 96.73 | 83.142 |
|  passthrough | 1G | 1 | 50 | 30 | 0 | 17.03 | 58.54 | 86.53 | 535 | 98.11 | 84.338 |
|  passthrough | 1G | 1 | 1024 | 0 | 0 | 512.92 | 1.91 | 7.79 | 11 | 96.58 | 83.696 |
|  passthrough | 1G | 1 | 1024 | 30 | 0 | 15.49 | 64.4 | 102.51 | 635 | 97.88 | 82.921 |
|  passthrough | 1G | 50 | 50 | 0 | 0 | 2640.18 | 18.88 | 51.87 | 89 | 91.5 | 281.282 |
|  passthrough | 1G | 50 | 50 | 30 | 0 | 1492.78 | 33.42 | 17.48 | 74 | 95.73 | 112.248 |
|  passthrough | 1G | 50 | 1024 | 0 | 0 | 2256.5 | 22.09 | 57.54 | 89 | 92.73 | 250.11 |
|  passthrough | 1G | 50 | 1024 | 30 | 0 | 1490.25 | 33.48 | 18.51 | 72 | 95.5 | 115.608 |
|  passthrough | 1G | 100 | 50 | 0 | 0 | 2465.6 | 40.47 | 96.8 | 151 | 89.19 | 354.011 |
|  passthrough | 1G | 100 | 50 | 30 | 0 | 1795.11 | 55.62 | 105.89 | 182 | 92.23 | 171.639 |
|  passthrough | 1G | 100 | 1024 | 0 | 0 | 2316.52 | 43.08 | 80.6 | 130 | 92.47 | 257.248 |
|  passthrough | 1G | 100 | 1024 | 30 | 0 | 2329.09 | 42.85 | 72.6 | 105 | 92.67 | 237.859 |
|  passthrough | 1G | 200 | 50 | 0 | 0 | 2049.9 | 96.7 | 184.46 | 1415 | 86.68 | 447.747 |
|  passthrough | 1G | 200 | 50 | 30 | 0 | 2566.48 | 77.78 | 101.25 | 181 | 91.3 | 288.296 |
|  passthrough | 1G | 200 | 1024 | 0 | 0 | 2558.1 | 78.05 | 91.58 | 201 | 91.89 | 253.14 |
|  passthrough | 1G | 200 | 1024 | 30 | 0 | 2348.87 | 85.03 | 89.5 | 183 | 92.6 | 246.278 |

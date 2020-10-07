Running the test results in these metrics:
````
[MeterId{name='http.server.requests', tags=[tag(exception=none),tag(method=POST),tag(status=200),tag(uri=/create/{id})]}]
[MeterId{name='http.server.requests', tags=[tag(exception=none),tag(method=OPTIONS),tag(status=200),tag(uri=/create/3)]}, MeterId{name='http.server.requests', tags=[tag(exception=none),tag(method=OPTIONS),tag(status=200),tag(uri=/create/7)]}]
````
The OPTIONS metric should have a single templatized `uri` tag
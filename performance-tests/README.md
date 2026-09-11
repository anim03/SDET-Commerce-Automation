# k6 Performance Testing

Performance testing module for SDET-Commerce-Automation.

## Current Scenarios

### Smoke Test

Purpose:

Verify that critical APIs work correctly under minimal load before running larger performance tests.

Configuration:

- 1 virtual user
- 5 iterations
- Login performed during setup
- JWT reused during test execution
- Products API validated
- Product Search API validated

Run:

PERF_USER_EMAIL="your-user-email" \
PERF_USER_PASSWORD="your-user-password" \
k6 run scenarios/smoke.js


### Load Test

Purpose:

Validate application behaviour under expected concurrent traffic.

Current profile:

- Ramp to 5 users
- Ramp to 10 users
- Ramp down to 0

Run:

PERF_USER_EMAIL="your-user-email" \
PERF_USER_PASSWORD="your-user-password" \
k6 run scenarios/load.js


### Stress Test

Purpose:

Increase load beyond the normal expected level and observe system behaviour.

Current profile:

- Ramp to 10 users
- Ramp to 20 users
- Ramp to 30 users
- Ramp down to 0

Run:

PERF_USER_EMAIL="your-user-email" \
PERF_USER_PASSWORD="your-user-password" \
k6 run scenarios/stress.js


## Environment Configuration

Default backend:

http://localhost:8080

Override it using:

BASE_URL=https://your-environment.example.com


## Thresholds

Performance tests validate:

- HTTP failure rate
- p95 response time
- check success rate

Smoke and load target:

http_req_failed < 1%

p95 response time < 1000 ms

checks > 99%

Stress target:

http_req_failed < 5%

p95 response time < 1500 ms

checks > 95%


## Security

Never commit:

- real usernames
- passwords
- JWT tokens
- environment secrets

Credentials must be supplied through environment variables.


## Performance Testing Principle

Functional testing asks:

Does the system work?

Performance testing additionally asks:

How does the system behave under load?

Important measurements include:

- response time
- throughput
- request rate
- failure rate
- concurrent virtual users
- percentile response times

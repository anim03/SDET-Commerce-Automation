import http from 'k6/http';
import { check, sleep } from 'k6';
import { config } from '../config/config.js';
import { login } from '../config/auth.js';

export const options = {
  stages: [
    { duration: '15s', target: 10 },
    { duration: '30s', target: 20 },
    { duration: '30s', target: 30 },
    { duration: '15s', target: 0 },
  ],

  thresholds: {
    http_req_failed: ['rate<0.05'],
    http_req_duration: ['p(95)<1500'],
    checks: ['rate>0.95'],
  },
};

export function setup() {
  console.log(`Running k6 stress test against: ${config.baseUrl}`);

  const token = login();

  if (!token) {
    throw new Error('Unable to obtain JWT token');
  }

  return {
    token,
  };
}

export default function (data) {
  const params = {
    headers: {
      Authorization: `Bearer ${data.token}`,
    },
  };

  const productsResponse = http.get(
    `${config.baseUrl}/api/products`,
    {
      ...params,
      tags: {
        name: 'GET /api/products',
      },
    }
  );

  check(productsResponse, {
    'products status is 200': (r) => r.status === 200,
  });

  const searchResponse = http.get(
    `${config.baseUrl}/api/products/search?name=${encodeURIComponent('test')}`,
    {
      ...params,
      tags: {
        name: 'GET /api/products/search',
      },
    }
  );

  check(searchResponse, {
    'search status is 200': (r) => r.status === 200,
  });

  sleep(1);
}

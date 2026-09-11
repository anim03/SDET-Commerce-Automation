import http from 'k6/http';
import { check } from 'k6';
import { config } from './config.js';

export function login() {
  if (!config.userEmail || !config.userPassword) {
    throw new Error(
      'PERF_USER_EMAIL and PERF_USER_PASSWORD must be provided'
    );
  }

  const payload = JSON.stringify({
    email: config.userEmail,
    password: config.userPassword,
  });

  const response = http.post(
    `${config.baseUrl}/api/users/login`,
    payload,
    {
      headers: {
        'Content-Type': 'application/json',
      },
      tags: {
        name: 'POST /api/users/login',
      },
    }
  );

  const loginPassed = check(response, {
    'login status is 200': (r) => r.status === 200,
    'login response contains token': (r) => {
      try {
        return Boolean(r.json('token'));
      } catch (_) {
        return false;
      }
    },
  });

  if (!loginPassed) {
    console.error(
      `Login failed. Status=${response.status} Body=${response.body}`
    );
    return null;
  }

  return response.json('token');
}

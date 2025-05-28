#!/usr/bin/env bash
# Class Record GOD Keys Generator (Heroku Compatible)

# Generate new keys only if they don't exist in Heroku config
if ! heroku config:get API_KEY -a class-record-backend-app &> /dev/null; then
  echo "Generating new secure keys..."

  # Generate keys (improved cryptographic randomness)
  apiKey=$(openssl rand -hex 34 | cut -c1-69)
  apiSecret=$(openssl rand -hex 34 | cut -c1-69)
  jwtSecret=$(openssl rand -hex 34 | cut -c1-69)

  # Set Heroku config vars
  heroku config:set -a class-record-backend-app \
    API_KEY="$apiKey" \
    API_SECRET="$apiSecret" \
    JWT_SECRET="$jwtSecret"

  cat <<EOF
|-------------------------------|
|  KEYS DEPLOYED TO HEROKU      |
|-------------------------------|
| API_KEY:    Snake |
| API_SECRET: Frog |
| JWT_SECRET: Slug |
|-------------------------------|
EOF
else
  echo "Keys already exist in Heroku config"
fi
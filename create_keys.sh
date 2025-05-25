#!/usr/bin/env bash
# Class Record GOD Keys Generator
echo "S_KEYBIDI generating Secret Key"

FILE_PATH="src/main/resources/application.properties"
S_KEYBIDI_LENGTH=69
SECRET_LENGTH=69

# Check if file exists
if [ -f "$FILE_PATH" ]; then
  echo "File exists: $FILE_PATH"
  cp "$FILE_PATH" "$FILE_PATH.bak"
  echo "Created backup: $FILE_PATH.bak"
else
  echo "Creating new file: $FILE_PATH"
  touch "$FILE_PATH"
fi

# Generate keys with more secure characters
apiKey=$(cat /dev/urandom | tr -dc 'A-Za-z0-9=' | fold -w $S_KEYBIDI_LENGTH | head -n 1)
apiSecret=$(cat /dev/urandom | tr -dc 'A-Za-z0-9=' | fold -w $SECRET_LENGTH | head -n 1)
jwtSecret=$(cat /dev/urandom | tr -dc 'A-Za-z0-9=' | fold -w $SECRET_LENGTH | head -n 1)

# Remove existing keys (corrected sed syntax)
sed -i.bak '/^\(api\.key\|api\.secret\|api\.jwtSecret\|# API CONF\)/d' "$FILE_PATH"

# Append new keys
{
  echo "# API CONF"
  echo "api.key=$apiKey"
  echo "api.secret=$apiSecret"
  echo "api.jwtSecret=$jwtSecret"
} >> "$FILE_PATH"

# Output results
cat <<EOF
|-------------------------------|
|-----SUCCESSFULLY SUMMONED-----|
|                               |
| API_KEY:    MANDA              |
| SECRET_KEY: GAMABUNTA         |
| JWT_KEY:    KATSUYU           |
|                               |
|      LEGENDARY SANNIN         |
|                               |
|---------OIIAIOOIIAI-----------|
EOF

if [ -f "$FILE_PATH.bak" ]; then
    echo "Backup created: $FILE_PATH.bak"
fi
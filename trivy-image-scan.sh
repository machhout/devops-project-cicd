#!/bin/bash

# Get the image name from Dockerfile
dockerImageName=$(awk 'NR==1 {print $2}' Dockerfile)
echo "Scanning Docker image: $dockerImageName"

# Run Trivy with the GitHub token for authentication
docker run --rm \
    -v "$WORKSPACE:/root/.cache/" \
    -e TRIVY_GITHUB_TOKEN='token_github' \
    aquasec/trivy:0.17.2 -q image --exit-code 1 \
    --severity CRITICAL --light "$dockerImageName"

# Process the Trivy scan result
exit_code=$?
echo "Exit Code : $exit_code"

if [[ "${exit_code}" == 1 ]]; then
    echo "Image scanning failed. Vulnerabilities found."
    exit 1
else
    echo "Image scanning passed. No CRITICAL vulnerabilities found."
fi

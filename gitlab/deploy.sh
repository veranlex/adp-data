#!/bin/sh

set -e
set -x

export APP_VERSION=$(grep "gradle.ext.VERSION" settings.gradle | cut -d "=" -f 2 | cut -d "'" -f 2)

echo "$APP_VERSION"

docker build -t "$IMAGE_NAME":"$APP_VERSION"."$CI_PIPELINE_IID" . \
  --label git-pipeline-id="${CI_PIPELINE_ID}" \
  --label git-pipeline-iid="${CI_PIPELINE_IID}" \
  --label git-project="$CI_PROJECT_NAMESPACE"/"$CI_PROJECT_NAME" \
  --label git-commit-msg="$CI_COMMIT_MESSAGE" \
  --label git-user_name="$GITLAB_USER_NAME"

docker tag "$IMAGE_NAME":"$APP_VERSION"."$CI_PIPELINE_IID" "$IMAGE_NAME":latest
docker push "$IMAGE_NAME":"$APP_VERSION"."$CI_PIPELINE_IID"
docker push "$IMAGE_NAME":latest

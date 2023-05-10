#!/usr/bin/env bash

set -euxf -o pipefail

PRODUCT_NAME=java-profiler-boost
LAST_TAG=${1:-v1.0.12}
PROFILER_HOME=${HOME}/java-profiler-boost

check_permission() {
  if [ ! -w "${HOME}" ]; then
    echo "permission denied, ${HOME} is not writable." && exit 1
  fi
}

download_jar() {

  if [ ! -d "${PROFILER_HOME}" ]; then
    mkdir -p "${PROFILER_HOME}"
  fi

  download_url="https://github.com/linyimin-bupt/${PRODUCT_NAME}/releases/download/${LAST_TAG}/${PRODUCT_NAME}.tar.gz"

  echo "Download java-profiler-boost from: ${download_url}"

  curl -#Lkf \
      --connect-timeout 60 \
      -o "${PROFILER_HOME}/${PRODUCT_NAME}.tar.gz" \
      "${download_url}" \
    || (echo "Download java-profiler-boost from: ${download_url} error, please install manually!!!" && exit 1)

}

extract_jar() {
  if [ ! -f "${PROFILER_HOME}/${PRODUCT_NAME}.tar.gz" ]; then
    echo "${PROFILER_HOME}/${PRODUCT_NAME}.tar.gz does not exist, please install manually!!!" && exit 1
  fi

  cd "${PROFILER_HOME}" || (echo "cd ${PROFILER_HOME} error." && exit 1)

  tar -zxvf "${PROFILER_HOME}/${PRODUCT_NAME}.tar.gz"

}

main() {

  check_permission

  download_jar

  extract_jar

  echo "${PRODUCT_NAME} install success. install directory: ${PROFILER_HOME}"

}

main

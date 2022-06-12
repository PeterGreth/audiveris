#!/bin/sh
set -e

verify_arguments_given() {
  if [ $# -eq 0 ]; then
    echo "No arguments supplied. Run e.g. install-tessdata.sh eng deu. Aborting."
    exit 1
  fi
}

verify_tessdata_environment_variable() {
  if [ -z "${TESSDATA_PREFIX}" ]; then
    echo "Environment variable TESSDATA_PREFIX is not set. Aborting."
    exit 2
  fi
}

install_tessdata_languages() {
  echo "Installing $# languages into ${TESSDATA_PREFIX}..."
  for language in "$@"; do
    filename="${language}.traineddata"
    wget -q "https://github.com/tesseract-ocr/tessdata/raw/3.04.00/${filename}"
    mv "$filename" "$TESSDATA_PREFIX"
    echo "Installed $filename"
  done
}

verify_arguments_given "$@"
verify_tessdata_environment_variable
install_tessdata_languages "$@"

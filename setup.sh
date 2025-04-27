#!/usr/bin/env bash
set -euo pipefail

# determine project name (current directory) and current year
NAME="$(basename "$PWD")"
YEAR="$(date +%Y)"

# if README.md already exists, delete it
if [ -f "README.md" ]; then
  rm "README.md"
fi

# generate the project (no prompts)
python -m cookiecutter \
  https://github.com/auberonedu/cookiecutter-java.git \
  --no-input \
  project_name="$NAME" \
  year="$YEAR"

# remove the now-empty subfolder
rm -r "$NAME"

# delete this script
rm -- "$0"


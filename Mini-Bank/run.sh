#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
if ! command -v javac >/dev/null 2>&1; then
  echo "JDK 17 or later is required; javac was not found." >&2
  exit 1
fi
javac_path="$(command -v javac)"
java_path="$(dirname "$javac_path")/java"
if [ ! -x "$java_path" ]; then java_path="$(command -v java)"; fi
jdk_version="$($javac_path -version 2>&1 | sed -E 's/^javac ([0-9]+).*/\1/')"
if [ -z "$jdk_version" ] || [ "$jdk_version" -lt 17 ]; then
  echo "JDK 17 or later is required; found $jdk_version." >&2
  exit 1
fi
rm -rf build
mkdir -p build
find src/minibank -name '*.java' -print0 | xargs -0 "$javac_path" -encoding UTF-8 -d build
exec "$java_path" -cp build minibank.Main

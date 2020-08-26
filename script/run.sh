#!/bin/zsh -euo pipefail
cd `dirname $0`/..

bundle exec jekyll serve --host 0.0.0.0 $@

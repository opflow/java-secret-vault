#!/usr/bin/env bash

set -e

actions=(download install)

# check for help flag
if [ -n "$1" ] && [[ ! ${actions[*]} =~ "$1" ]]; then
  echo "Usage: curl https://raw.githubusercontent.com/opflow/java-secret-vault/master/download.sh | bash" 1>&2;
  exit 1;
fi

# determine the action
task_action=download
if [ -n "$1" ] && [[ ${actions[*]} =~ "$1" ]]; then
  task_action="$1"
fi

# determine current working dir
task_cwd=`pwd`

# determine if the current user has write permission on current working dir
if [ $task_action == 'download' ] && [ ! -w $task_cwd ]; then
  echo "Error: the current user has not write permission on [$task_cwd]"
  exit 5
fi

# create tmp directory and move to it
tmp_dir=`mktemp -d 2>/dev/null || mktemp -d -t 'java-secret-vault'`; cd $tmp_dir

# get the latest version
version=`curl -s https://api.github.com/repos/opflow/java-secret-vault/releases/latest | sed -n 's/.*"tag_name":.*"\([^"]*\)".*/\1/p'`

if [ -z $version ]; then
  echo "Error: unable to determine the latest version of java-secret-vault"
  exit 5
fi

# build download link
jar_file="java-secret-vault.jar"
download_link="https://github.com/opflow/java-secret-vault/releases/download/$version/$jar_file"

curl -L -O $download_link

footer_note="Check https://github.com/opflow/java-secret-vault for more details."

cp java-secret-vault.jar $task_cwd
printf "\n${version} has been successfully downloaded."
printf "\n${footer_note}\n\n"

# execute program
cd $task_cwd

exit 0

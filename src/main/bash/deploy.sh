#! /bin/bash

cd $PWD/../../../..
mvn install -DperformRelease=true -DcreateChecksum=true

TEST=`mount | grep /srv`
[ -z "$TEST" ] && {
	echo "Yggdrasill is not mounted"
	exit 1
}

M2_LOCAL=/home/sanke/.m2/repository
M2_DIST=/srv/var/maven

# CLEAN
rm -rf $M2_DIST/maven2/com/xs

mkdir -p $M2_DIST/maven2/com/xs
cp -r $M2_LOCAL/com/xs/* $M2_DIST/maven2/com/xs/ 

for prj in "$M2_DIST/maven2/com/xs/"*; do
	if [ -d "$prj" ]; then
		cd $prj
		mv maven-metadata-local.xml maven-metadata.xml
		md5sum maven-metadata.xml > maven-metadata.xml.md5
		sha1sum maven-metadata.xml > maven-metadata.xml.sha1
		rm maven-metadata-local.xml.md5
		rm maven-metadata-local.xml.sha1
	fi
done

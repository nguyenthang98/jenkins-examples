#!/bin/bash


# Starting
cat <<EOF >index.html
<!DOCTYPE html>
<html>
<head>
<style>
body {background-color: powderblue;}
h1   {color: blue;}
p    {color: red;}
</style>
</head>
<body>
EOF
# Body Section

cat <<EOF >>index.html
<h1>HEADING 1</h1>
EOF

total_build_duration=0
number_of_builds=0
builds_avg_durations=0
line_index=0

while IFS= read -r line
do
        echo $line
        arr=(`echo $line | sed 's/\ /\n/g'`)
        if [ $line_index -eq 0 ];
        then
                number_of_builds=${arr[0]}
                total_build_duration=${arr[1]}
                builds_avg_durations=${arr[2]}
        else
                echo "<div>${arr[0]} with duration ${arr[1]}</div>" >> index.html
        fi
        line_index=$((line_index+1))
done < builds_info

echo "<div><strong>Total Duration:</strong> $total_build_duration</div>" >> index.html
echo "<div><strong>Number of Builds:</strong> $number_of_builds</div>" >> index.html
echo "<div><strong>Avg of Builds Duration:</strong> $builds_avg_durations</div>" >> index.html

# Ending
cat <<EOF >>index.html
</body>
</html>
EOF

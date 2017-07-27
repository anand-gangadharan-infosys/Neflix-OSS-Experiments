s1=localhost:11211  # shard 1
s2=localhost:11212  # shard 2
s3=localhost:11213  # shard 3
s4=localhost:11214  # shard 4
r1=$s1,$s2          # replica 1 is shards 1 and 2
r2=$s3,$s4          # replica 2 is shards 3 and 4
export EVC_SAMPLE_DEPLOYMENT="SERVERGROUP1=$r1;SERVERGROUP2=$r2"

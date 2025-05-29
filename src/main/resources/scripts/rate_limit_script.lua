local key = KEYS[1]
local current = tonumber(redis.call('GET', key))
local limitCount = tonumber(ARGV[2])

if not current then
    redis.call('SET', key, 1, 'EX', ARGV[1])
    return 1
end

if current >= limitCount then
    return -1
else
    redis.call('INCR', key)
    return current + 1
end

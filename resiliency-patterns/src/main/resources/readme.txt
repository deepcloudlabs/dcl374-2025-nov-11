1. Circuit Breaker
   BFF/Gateway -> [failing] service
   service ->  [failing] service
   service ->  [failing] 3rd-party service
   
2. Retry -> idempotent/stateless

3. RateLimiter ->  enforce service's capacity or constract's limit
   gateway -> routing -> [burst] service

4. TimeLimiter -> Async -> 3sec -> 5sec
   gateway -> routing -> [time critical, time budget] service
   
5. Bulhead -> prevents exhausting all [slow] threads/connections 
   SEMAPHORE -> default: reactive/non-blocking flow
   THREADPOOL -> blocking i/o
   per resource -> DB/3rd party service/...)  
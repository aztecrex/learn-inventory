# Refactorings to Consider

- ~~Don't pass item to calculator. By doing that, the calculator
  can invoke the order computation, possibly leading to infinite
  recursion~~
- ~~Don't pass services to the calculator. This just has the bad
  smell that calculation is being mixed with service invocation.
  Since service invocation is more likely to include unreliable
  or long-running operations such as network access, it might be
  better to isolate dealing with those to some place that can
  provide an appropriate resonse to timeouts and failures.~~


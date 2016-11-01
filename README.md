# ssj-issue

We are trying to use the SSJ library to fit time series using B-splines.
So far the results are not very good, for a reasonable number of control points. 
The behaviour we are seeing, moving from left to right along the time axis, is that the fit is good initially but then it degrades rapidly. It is almost as if the fitting algorithm "runs out of" control points as it moves along the time axis, as opposed to distributing the control points evenly along the entire time domain. The only way to avoid this is to specify an unreasonably large number of control points (N minus one, where N is the length of the input vector). 

Either we are using the library incorrectly, or there is a bug.
This project shows how to reproduce the issue (or the mis-usage of the library, as the case may be). 

The plot below shows the original response vector in red, with the fitted B-spline curve in blue.
We see this behaviour consistently with all our input data, not just this particular case.

![Example plot of original vs. fitted curve](https://raw.githubusercontent.com/EXPEkmajewski/ssj-issue/master/ssh-issue.png)

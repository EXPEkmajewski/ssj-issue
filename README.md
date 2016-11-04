# ssj-issue

We are trying to use the SSJ library to fit time series data using B-splines.
The goal is simply to reduce the number of data points we need to store: instead of storing each data point in the time series, we want to store a B-spline approximation. For this to make sense, the number of coefficients of the B-spline must be significantly smaller than the number of data points in the original time series.

So far the results are not very good. We are not sure if this is a weakness of SSJ, or if we are using SSJ incorrectly.
The goal of this Github repository is to showcase the behaviour we are seeing, with the hope that the SSJ authors can point us in the right direction (or confirm that it's a bug). 

We are calling ```BSpline.createApproxBSpline(predictor, response, B_SPLINE_DEGREE, NUM_CONTROL_POINTS)```.
In this example, ```predictor``` and ```response``` are vectors of length 48, ```B_SPLINE_DEGREE``` is 2, and ```NUM_CONTROL_POINTS``` is 11. 
What we expect is that the control points will be distributed more or less uniformly along the entire time domain of the series.
What we see, moving from left to right along the time axis, is that the fit is good initially but then it degrades. 
It is almost as if the fitting algorithm "runs out of" control points prematurely, as it moves along the time axis. 
The only way to avoid this is to specify an unreasonably large number of control points (N minus one, where N is the length of the input vector), or to use ```BSpline.createInterpBSpline``` instead of ```BSpline.createApproxBSpline```. However, both of those workarounds defeat the purpose of data reduction.

The plot below shows the original response vector in red, with the fitted B-spline curve in blue.
We see this behaviour consistently with all our input data, not just this particular case.

Please see ```ReproduceIssue.java``` for more details.

![Example plot of original vs. fitted curve](https://raw.githubusercontent.com/EXPEkmajewski/ssj-issue/master/ssh-issue.png)

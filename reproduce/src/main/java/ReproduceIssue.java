import umontreal.ssj.functionfit.BSpline;

public class ReproduceIssue {

    private static final double[] response = {
        60.784054,
        63.699608,
        70.09501,
        83.46073,
        95.98937,
        106.94308,
        115.591675,
        122.17974,
        126.40732,
        129.29819,
        130.75278,
        132.67824,
        133.77184,
        135.15007,
        134.47699,
        133.58612,
        131.12141,
        128.48383,
        124.42616,
        120.02176,
        114.85236,
        110.70014,
        107.3949,
        106.037636,
        105.18014,
        105.4258,
        105.327644,
        105.10827,
        103.16745,
        99.35878,
        92.274956,
        82.2671,
        70.60536,
        59.641438,
        50.29073,
        42.304214,
        35.414463,
        29.605185,
        25.185568,
        21.957987,
        19.89746,
        18.868565,
        18.84073,
        19.930826,
        22.164654,
        25.366472,
        27.53218,
        29.819633
    };

    private static final int intervalMinutes = 5;
    private static final int B_SPLINE_DEGREE = 2;
    private static final int NUM_CONTROL_POINTS = 11;

    private static double[] predictor;

    private static double[] bsplineFit;

    private static BSpline bspline;

    public static void main(String[] args) {
        final int N = response.length;
        predictor = createTimeAxis(intervalMinutes, N);
        bspline = BSpline.createApproxBSpline(predictor, response, B_SPLINE_DEGREE, NUM_CONTROL_POINTS);
        bsplineFit = evaluateFit(N);
        System.out.println("Mean squared error sanity check (should be zero): " + mse(N, response, response));
        System.out.println("Mean squared error with " + NUM_CONTROL_POINTS + " control points: " + mse(N, response, bsplineFit));

        bspline = BSpline.createApproxBSpline(predictor, response, B_SPLINE_DEGREE, N - 1);
        bsplineFit = evaluateFit(N);
        System.out.println("Mean squared error with N-1 control points: " + mse(N, response, bsplineFit));

        bspline = BSpline.createInterpBSpline(predictor, response, B_SPLINE_DEGREE);
        bsplineFit = evaluateFit(N);
        System.out.println("Mean squared error with interpolating B-Spline: " + mse(N, response, bsplineFit));
    }

    private static double mse(int N, double[] original, double[] fitted) {
        double result = 0;
        for (int bin = 0; bin < N; bin++) {
            double originalValue = original[bin];
            double fittedValue = fitted[bin];
            double delta = originalValue - fittedValue;
            result += delta * delta;
        }
        result = result / (double) N;
        return result;
    }

    private static double[] evaluateFit(int N) {
        double[] result = new double[N];
        for (int bin = 0; bin < N; bin++) {
            result[bin] = getApproximateResponseForTimeBin(bin);
        }
        return result;
    }

    private static float getApproximateResponseForTimeBin(int bin) {
        double x = predictor[bin];
        return (float) bspline.evaluate(x);
    }

    private static double[] createTimeAxis(int intervalMinutes, int totalN) {
        double instant = 0;
        final double[] result = new double[totalN];
        for (int i = 0; i < totalN; i++) {
            result[i] = instant;
            instant += (double) intervalMinutes;
        }
        return result;
    }
}

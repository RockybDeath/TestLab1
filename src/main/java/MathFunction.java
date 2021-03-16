public class MathFunction {
    public double arctgInTow(double x){
        if(Math.abs(x) > 1) return Double.NaN;
        double arg = Math.abs(x);
        int n = 1;
        double prev = 0;
        double sum = arg;
        while(Math.abs(sum - prev) > 0.0001){
            prev = sum;
            sum = sum + (Math.pow(-1, n)*Math.pow(arg, 2*n+1))/(2*n+1);
            n+=1;
        }
        if(x < 0 ) sum = -sum;
        return sum;
    }
}

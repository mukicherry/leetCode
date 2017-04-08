package mqy.api.javaBasicTest;

import java.util.Calendar;

/**
 * Created by maoqiyun on 15/12/27.
 */
public class CalculateDays {

    public static void main(String[] args) {
//        Calendar currentDate = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println("Today is " + sdf.format(currentDate.getTime()));
//        System.out.println("is week " + calculateWeek(currentDate));
//        Calendar theDateIcomeToBJ = Calendar.getInstance();
//        theDateIcomeToBJ.clear();
//        theDateIcomeToBJ.set(2015, 7, 7);
//        calculateDays(theDateIcomeToBJ, currentDate);


        for(int i = -5; i < 5; i ++) {
            try {
                double x = 10/i;
                System.out.println("i = "+i+","+"x=" + x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static long calculateDays(Calendar startDate, Calendar endDate) {
        long startTime = startDate.getTimeInMillis();
        long endTime = endDate.getTimeInMillis();
        long diff = endTime - startTime;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        System.out.println("You've been in Beijin for " + diffDays + " Days.");

        return diffDays;
    }

    public static int calculateWeek(Calendar todayDate) {
        todayDate.setFirstDayOfWeek(Calendar.MONDAY);
        return todayDate.get(Calendar.WEEK_OF_YEAR);

    }

//    public static long calculateMeituanDays(Calendar startDate, Calendar endDate) {
//
//    }


}

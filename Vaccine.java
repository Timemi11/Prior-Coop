import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Vaccine {

    private static LocalDate parseDate(String dateString) {
        // ทำการแปลงค่าตัวเลขที่ใส่เข้ามาในรูปแบบเช่น 1 มิถุนายน พ.ศ.2522 ==> 2522-6-1

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM พ.ศ.yyyy", new Locale("th", "TH"));
        return LocalDate.parse(dateString, formatter);
    }

    private static String formatDate(LocalDate date) {
        // ทำการแปลงค่าตัวเลขที่ใส่เข้ามาในรูปแบบเช่น 2522-6-1 ==> 1 มิถุนายน พ.ศ.2522
        int yyyy = date.getYear() + 543;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM พ.ศ." + yyyy, new Locale("th", "TH"));

        return date.format(formatter);
    }

    public static void main(String[] args) {

        // ====================ส่วน input และ กำหนดวันเข้ารับวัคซีน ====================
        Scanner in = new Scanner(System.in);
        String start_service = "1 มิถุนายน พ.ศ.2021";
        String end_service = "31 สิงหาคม พ.ศ.2021";
        String startservice = "1 มิถุนายน พ.ศ.2021";
        String endservice = "31 สิงหาคม พ.ศ.2021";
        String eligible_flag = "N";
        String notify = "";
        // int ageDays = 0;
        // int ageMonths = 0;
        // int ageYears = 0;

        // ====================จบส่วนinput และกำหนดวันเข้ารับวัคซีน ====================

        System.out.println("----โปรดกรอกข้อมูล [เช่น \"หญิง เกิดวันเสาร์ที่ 10 มีนาคม พ.ศ.2545\"]----");
        System.out.println("ช่วงให้บริการรับวัคซีน" + " " + formatDate(parseDate(start_service)) + " - "
                + formatDate(parseDate(end_service)));

        // ==================== ส่วนเช็คค่า input ที่ใส่เข้ามา ====================
        while (notify.equals("")) {
            String gender = in.next();
            String dateofbirth = in.nextLine();
            String dd, MMMM, yyyy;
            String[] words = dateofbirth.split("\\s");
            // รูปแบบที่ต้องใส่วันเกิด
            java.util.regex.Pattern pattern = java.util.regex.Pattern
                    .compile("^(\\d{1,2}) ([ก-๙]+) พ.ศ.(\\d{4})$");

            try {
                if (words.length > 1 && (gender.equals("หญิง") || gender.equals("ชาย"))) {
                    // เช็คว่า array มีเนื่อหาข้างในอยู่แน่ๆแล้วมี gender ใส่มาแล้ว
                    dd = words[2];
                    MMMM = words[3];
                    yyyy = words[4];

                    if (pattern.matcher(dd + " " + MMMM + " " + yyyy).matches()) {
                        // เช็ครูปแบบของผู้ใช้ว่าตรงกับรูปแบบที่กำหนดไหม
                        int yyy = Integer.parseInt(yyyy.substring(4)) - 543; // แปลงให้กลายเป็นปี พ.ศ.
                        yyyy = "พ.ศ." + yyy;

                        if (2564 - (yyy + 543) >= 120) {
                            throw new Exception("กรุณากรอกวันที่ให้ถูกต้อง วันเกิดคนที่กรอกมีมากกว่า 120 ปี");
                        }
                        if (2564 - (yyy + 543) < 0) {
                            throw new Exception(
                                    "กรุณากรอกวันที่ให้ถูกต้อง อายุคนที่กรอกมีมากกว่าปีหรือช่วงที่รับวัคซีน");
                        }
                        if (Integer.parseInt(dd) > 31) {
                            throw new Exception("กรุณากรอกวันที่ให้ถูกต้อง ไม่มีวันที่มากกว่า 31 ในแต่ละเดือน");
                        }
                        if (Integer.parseInt(dd) > 30
                                && (MMMM.equals("เมษายน") || MMMM.equals("มิถุนายน") || MMMM.equals("กันยายน"))
                                || MMMM.equals("พฤศจิกายน")) {
                            throw new Exception("กรุณากรอกวันที่ให้ถูกต้อง เดือนนี้ไม่มีวันที่ 31");
                        }
                        if (Integer.parseInt(dd) >= 29 && MMMM.equals("กุมภาพันธ์")
                                && !((yyy % 4 == 0 && yyy % 100 != 0) || (yyy % 400 == 0))) {
                            throw new Exception("กรุณากรอกวันที่ให้ถูกต้อง ไม่ใช่ปีที่มีวันมากกว่า 28 กุมภาพันธ์");
                        }
                        dateofbirth = dd + " " + MMMM + " " + yyyy;
                    } else {
                        throw new Exception("กรุณากรอกวันที่ให้ถูกต้อง");
                    }
                } else {
                    throw new Exception("กรุณากรอกข้อมูลให้ถูกต้อง");
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
                continue;
            }

            // ============== จบส่วนเช็คค่า input ที่ใส่เข้ามา ========================

            // ค่าที่ใส่เป็นปีที่มี 29 กุมภาพันธ์
            // boolean leapyear = date1.isLeapYear();
            // System.out.println(leapyear);

            // ageDays = period.getDays();
            // ageMonths = period.getMonths();
            // ageYears = period.getYears();

            // เช็คอายุตั้งแต่เดือนมิถุนายน [debug]
            // System.out.println("อายุใน 1 มิถุนายน 2564");
            // System.out.println(period.getDays() + "วัน");
            // System.out.println(period.getMonths() + "เดือน");
            // System.out.println(period.getYears() + "ปี");

            // ================================ส่วนกำหนดค่าตัวแปรที่นำไปใช้คำนวณ================================

            // นำ ว/ด/ปี มาเก็บเอาไว้ในตัวแปร ซึ่งอยู่ในรูปแบบตัวเลข เช่น 2002-09-02
            LocalDate date1 = parseDate(dateofbirth);// แปลงวันเป็นรูปแบบ yyyy-mm-dd เพื่อใช้คำนวณต่อ
            LocalDate date2 = parseDate(startservice);
            LocalDate date3 = parseDate(endservice);

            Period period = Period.between(date1, date2);// อายุเมื่อถึงเดือนมิถุนายน

            // ================================ส่วนกำหนดค่าตัวแปรที่นำไปใช้คำนวณ================================

            // ================================ส่วนเช็ควันเข้ารับวัคซีน================================

            if (period.getYears() >= 1) {
                // หากมีอายุตั้งแต่ 1 ปีขึ้นไป
                startservice = formatDate(date2);
                endservice = formatDate(date3);
                eligible_flag = "Y";
                notify = "เข้ารับบริการได้ตั้งแต่วันที่ " + startservice + " - " + endservice;
                if (period.getYears() >= 65) {
                    // อายุ 65 ปีขึ้นไป
                    startservice = formatDate(date2);
                    endservice = formatDate(date3);
                    eligible_flag = "Y";
                    notify = "เข้ารับบริการได้ตั้งแต่วันที่ " + startservice + " - " + endservice;
                    break;
                } else if ((period.getYears() == 1 && period.getMonths() == 10) // เข้าเงื่อนไขเด็กที่อายุกำลังหมดเกณฑ์ในช่วงรับวัคซีน
                        || (period.getYears() == 1 && period.getMonths() == 11)) {
                    // บวกปีเพิ่มขึ้นไปจนอายุครบ 2 ปี จากนั่นเช็คว่ายังอยู่ในช่วงรับวัคซีนหรือไม่
                    date1 = date1.plusYears(2);
                    if (date1.isBefore(date3) && date1.isAfter(date2)) {
                        date3 = date1;
                        eligible_flag = "Y";
                        startservice = formatDate(date2);
                        endservice = formatDate(date3);
                        notify = "เข้ารับบริการได้ตั้งแต่วันที่ " + startservice + " - " + endservice;
                        break;
                    }
                } else if ((period.getYears() == 64 && period.getMonths() == 10) // ถ้าอายุ64ปี
                                                                                 // ที่เกิดเดือนมิถุนายนขึ้นไปเข้าเงื่อนไขคนที่อายุกำลังเข้าเกณฑ์ในช่วงรับวัคซีน
                        || (period.getYears() == 64 && period.getMonths() == 11)) {

                    // บวกปีเพิ่มขึ้นไปจนอายุครบ 65 ปี จากนั่นเช็คว่ายังอยู่ในช่วงรับวัคซีนหรือไม่
                    date1 = date1.plusYears(65);
                    if (date1.isBefore(date3) && date1.isAfter(date2)) {
                        date2 = date1;
                        eligible_flag = "Y";
                        startservice = formatDate(date2);
                        endservice = formatDate(date3);
                        notify = "เข้ารับบริการได้ตั้งแต่วันที่ " + startservice + " - " + endservice;
                        break;
                    }
                } else if (period.getYears() >= 2 && period.getYears() < 65) {
                    // อายุระหว่าง 2 ปี ถึง 65 ปี
                    if (period.getYears() == 2 && period.getMonths() == 0 && period.getDays() == 0) {
                        // อายุเท่ากับ 2ปี 0เดือน 0วัน
                        date1 = date1.plusYears(2); // บวกปีเพิ่มขึ้นไปเพื่อเซ็ตวันที่รับและหมดช่วงรับวัคซีน
                        startservice = formatDate(date1);
                        endservice = formatDate(date1);
                        eligible_flag = "Y";
                        notify = "เข้ารับบริการได้ตั้งแต่วันที่ " + startservice + " - " + endservice;
                        break;
                    }
                    date1 = date1.plusYears(65);
                    eligible_flag = "N";
                    startservice = null;
                    endservice = null;
                    notify = "ไม่สามารถเข้ารับบริการได้ เนื่องจากอายุจะครบ 65 ปี ใน " + formatDate(date1);
                    break;
                }
            } else if (period.getYears() == 0 && period.getMonths() >= -2) {
                // อายุ 0 ปีขึ้นไปเข้าเงื่อนไขนี้อายุไม่ถึงปี คิดเป็นเดือน
                if (period.getMonths() >= 6) {
                    eligible_flag = "Y";
                    startservice = formatDate(date2);
                    endservice = formatDate(date3);
                    notify = "เข้ารับบริการได้ตั้งแต่วันที่ " + startservice + " - " + endservice;
                    break;
                } else if (period.getMonths() >= 3 && period.getMonths() <= 5) {
                    // ในเดือนมิถุนายน เด็กน้อยอายุใกล้เข้าเกณฑ์ในช่วงรับวัคซีนพอดี
                    date1 = date1.plusMonths(6);
                    // บวกเดือนเพิ่มจนอายุ 6 เดือน
                    if (date1.isAfter(date2) && date1.isBefore(date3)) {
                        // จากนั่นก็เช็คว่ายังไม่หมดช่วงรับวัคซีนเมื่อถึงอายุ6เดือนแล้ว
                        eligible_flag = "Y";
                        startservice = formatDate(date1);
                        endservice = formatDate(date3);
                        notify = "เข้ารับบริการได้ตั้งแต่วันที่ " + startservice + " - " + endservice;
                        break;
                    } else {
                        // พอบวกอายุเดือนเพิ่มแล้วแต่ว่าเกินช่วงรับไปแล้วเช่น มีนาคม 2564
                        // เพราะบวกไปแล้วอยู่ในเดือนกันยายน
                        eligible_flag = "N";
                        startservice = null;
                        endservice = null;
                        notify = "ไม่สามารถเข้ารับบริการได้ เนื่องจากอายุจะครบ 6 เดือน ใน"
                                + formatDate(date1);
                        break;
                    }
                } else {
                    // ไม่เข้าเกณฑ์ในช่วงรับวัคซีน เช่น เมษายน ถึง สิงหาคม 2564
                    date1 = date1.plusMonths(6);
                    eligible_flag = "N";
                    startservice = null;
                    endservice = null;
                    notify = "ไม่สามารถเข้ารับบริการได้ เนื่องจากอายุจะครบ 6 เดือน ใน " + formatDate(date1);
                    break;
                }
            } else {
                // เช่น เกิดเลยช่วง มิถุนายน - สิงหาคม 2564
                eligible_flag = "N";
                startservice = null;
                endservice = null;
                notify = "ไม่สามารถเข้ารับบริการได้ เนื่องจากวันเกิดเลยช่วงรับวัคซีน";
                break;
            }
        }

        // ================================จบส่วนเช็ควันเข้ารับวัคซีน================================

        // ================================ส่วนแสดงข้อมูลวันที่รับวัคซีนและวันที่เข้ารับบริการ================================
        if (eligible_flag.equals("Y")) {
            System.out.println(notify);
            // System.out.println(
            // "อายุปัจจุบันตอนเดือนมิถุนายน " + ageYears + " ปี " + ageMonths + " เดือน " +
            // ageDays + " วัน");
        } else if (eligible_flag.equals("N")) {
            System.out.println(notify);
            // System.out.println(
            // "อายุปัจจุบันตอนเดือนมิถุนายน " + ageYears + " ปี " + ageMonths + " เดือน " +
            // ageDays + " วัน");
        }
        // ================================จบส่วนแสดงข้อมูลวันที่รับวัคซีนและวันที่เข้ารับบริการ================================

        in.close();

    }
}
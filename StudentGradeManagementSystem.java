import java.util.*;

public class StudentGradeManagementSystem {
    static class Student {
        private String id;
        private String name;
        private double math;
        private double english;
        private double computer;

        public Student(String id, String name, double math, double english, double computer) {
            this.id = id;
            this.name = name;
            this.math = math;
            this.english = english;
            this.computer = computer;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public double getMath() { return math; }
        public void setMath(double math) { this.math = math; }
        public double getEnglish() { return english; }
        public void setEnglish(double english) { this.english = english; }
        public double getComputer() { return computer; }
        public void setComputer(double computer) { this.computer = computer; }
        
        public double getTotal() { return math + english + computer; }
        public double getAverage() { return getTotal() / 3.0; }

        @Override
        public String toString() {
            return String.format("学号: %s | 姓名: %-6s | 数学: %5.1f | 英语: %5.1f | 计算机: %5.1f | 总分: %6.1f | 平均分: %5.1f",
                    id, name, math, english, computer, getTotal(), getAverage());
        }
    }

    private ArrayList<Student> students = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void addStudent() {
        System.out.print("请输入学号: ");
        String id = scanner.nextLine();
        if (findStudentById(id) != null) {
            System.out.println("学号已存在！");
            return;
        }
        System.out.print("请输入姓名: ");
        String name = scanner.nextLine();
        System.out.print("请输入数学成绩: ");
        double math = scanner.nextDouble();
        System.out.print("请输入英语成绩: ");
        double english = scanner.nextDouble();
        System.out.print("请输入计算机成绩: ");
        double computer = scanner.nextDouble();
        scanner.nextLine();
        
        students.add(new Student(id, name, math, english, computer));
        System.out.println("添加成功！");
    }

    public void deleteStudent() {
        System.out.print("请输入要删除的学生学号: ");
        String id = scanner.nextLine();
        Student student = findStudentById(id);
        if (student != null) {
            students.remove(student);
            System.out.println("删除成功！");
        } else {
            System.out.println("未找到该学生！");
        }
    }

    public void updateStudent() {
        System.out.print("请输入要修改的学生学号: ");
        String id = scanner.nextLine();
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("未找到该学生！");
            return;
        }
        System.out.print("请输入新姓名(不修改按回车): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) student.setName(name);
        
        System.out.print("请输入新数学成绩(不修改输入-1): ");
        double math = scanner.nextDouble();
        if (math != -1) student.setMath(math);
        
        System.out.print("请输入新英语成绩(不修改输入-1): ");
        double english = scanner.nextDouble();
        if (english != -1) student.setEnglish(english);
        
        System.out.print("请输入新计算机成绩(不修改输入-1): ");
        double computer = scanner.nextDouble();
        if (computer != -1) student.setComputer(computer);
        scanner.nextLine();
        
        System.out.println("修改成功！");
    }

    public void queryStudent() {
        System.out.print("请输入要查询的学生学号: ");
        String id = scanner.nextLine();
        Student student = findStudentById(id);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("未找到该学生！");
        }
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("暂无学生信息！");
            return;
        }
        System.out.println("\n========== 所有学生信息 ==========");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    public void sortByTotal() {
        students.sort((s1, s2) -> Double.compare(s2.getTotal(), s1.getTotal()));
        System.out.println("已按总分从高到低排序！");
        displayAllStudents();
    }

    public void sortById() {
        students.sort(Comparator.comparing(Student::getId));
        System.out.println("已按学号排序！");
        displayAllStudents();
    }

    public void statistics() {
        if (students.isEmpty()) {
            System.out.println("暂无学生信息！");
            return;
        }
        double maxMath = 0, minMath = 100, sumMath = 0;
        double maxEng = 0, minEng = 100, sumEng = 0;
        double maxComp = 0, minComp = 100, sumComp = 0;
        
        for (Student s : students) {
            // 数学统计
            if (s.getMath() > maxMath) maxMath = s.getMath();
            if (s.getMath() < minMath) minMath = s.getMath();
            sumMath += s.getMath();
            
            // 英语统计
            if (s.getEnglish() > maxEng) maxEng = s.getEnglish();
            if (s.getEnglish() < minEng) minEng = s.getEnglish();
            sumEng += s.getEnglish();
            
            // 计算机统计
            if (s.getComputer() > maxComp) maxComp = s.getComputer();
            if (s.getComputer() < minComp) minComp = s.getComputer();
            sumComp += s.getComputer();
        }
        
        int count = students.size();
        System.out.println("\n========== 成绩统计 ==========");
        System.out.println("科目\t最高分\t最低分\t平均分");
        System.out.printf("数学\t%.1f\t%.1f\t%.1f\n", maxMath, minMath, sumMath/count);
        System.out.printf("英语\t%.1f\t%.1f\t%.1f\n", maxEng, minEng, sumEng/count);
        System.out.printf("计算机\t%.1f\t%.1f\t%.1f\n", maxComp, minComp, sumComp/count);
    }

    public void passRate() {
        if (students.isEmpty()) {
            System.out.println("暂无学生信息！");
            return;
        }
        int passAll = 0;
        for (Student s : students) {
            if (s.getMath() >= 60 && s.getEnglish() >= 60 && s.getComputer() >= 60) {
                passAll++;
            }
        }
        System.out.printf("\n全科及格人数: %d/%d\n", passAll, students.size());
        System.out.printf("全科及格率: %.1f%%\n", passAll * 100.0 / students.size());
    }

    private Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) return s;
        }
        return null;
    }

    public void run() {
        while (true) {
            System.out.println("\n======== 学生成绩管理系统 ========");
            System.out.println("1. 添加学生");
            System.out.println("2. 删除学生");
            System.out.println("3. 修改学生");
            System.out.println("4. 查询学生");
            System.out.println("5. 显示所有学生");
            System.out.println("6. 按总分排序");
            System.out.println("7. 按学号排序");
            System.out.println("8. 成绩统计");
            System.out.println("9. 及格率统计");
            System.out.println("0. 退出系统");
            System.out.print("请选择操作: ");
            
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("输入无效，请重新输入！");
                scanner.nextLine();
                continue;
            }
            
            switch (choice) {
                case 1: addStudent(); break;
                case 2: deleteStudent(); break;
                case 3: updateStudent(); break;
                case 4: queryStudent(); break;
                case 5: displayAllStudents(); break;
                case 6: sortByTotal(); break;
                case 7: sortById(); break;
                case 8: statistics(); break;
                case 9: passRate(); break;
                case 0: 
                    System.out.println("感谢使用！再见！");
                    return;
                default:
                    System.out.println("无效选择，请重试！");
            }
        }
    }

    public static void main(String[] args) {
        new StudentGradeManagementSystem().run();
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CleanFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author DucTM
 */
public class CleanDesktop {

    public static void doiTenFile() {
        Scanner sc = new Scanner(System.in);
        String path = nhapDuongDanFolder();
        File[] listOfFiles = new File(path).listFiles();
        System.out.println("Folder hiện tại: " + path);
        System.out.println("Số file trong folder: " + listOfFiles.length);
        System.out.print("Nhập tiền tố file bạn muốn thay đổi: ");
        String fileName = sc.nextLine();
        int count = 1;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                file.renameTo(new File(path + "/" + fileName +"_"+ count + "." + getFileExtension(file)));
                count++;
            }
        }
        System.out.println("Đổi tên file thành công !\n");
    }

    public static void donDepThuMuc() throws IOException {
        String path = nhapDuongDanFolder();
        File[] listOfFiles = new File(path).listFiles();
        System.out.println("Folder hiện tại: " + path);
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (file.isFile()) {
                if (getFileExtension(file).equals("pdf") || getFileExtension(file).equals("xlsx") || getFileExtension(file).equals("docx")) {
                    File fileDoc = new File(path + "/documents");
                    if (!fileDoc.exists()) {
                        fileDoc.mkdir();
                    }
                    // /Users/duc/Desktop/test/abc.pdf -> /Users/duc/Desktop/test/documents/abc.pdf
                    Files.move(Paths.get(file.getPath()), Paths.get(fileDoc.getPath() + "/" + file.getName()));
                } else if (getFileExtension(file).equals("jpg") || getFileExtension(file).equals("jpeg") || getFileExtension(file).equals("gif") || getFileExtension(file).equals("png")) {
                    File fileIm = new File(path + "/images");
                    if (!fileIm.exists()) {
                        fileIm.mkdir();
                    }
                    Files.move(Paths.get(file.getPath()), Paths.get(fileIm.getPath() + "/" + file.getName()));
                } else {
                    File fileO = new File(path + "/others");
                    if (!fileO.exists()) {
                        fileO.mkdir();
                    }
                    Files.move(Paths.get(file.getPath()), Paths.get(fileO.getPath() + "/" + file.getName()));
                }
            }
        }
        System.out.println("Dọn dẹp thành công !\n");
    }

    public static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf + 1);
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Chào mừng bạn đến với tiện ích quản lý file");
        int choose;
        do {
            System.out.println("====================TIỆN ÍCH QUẢN LÝ FILE=================");
            System.out.println("+       [1]. Tìm kiếm file và hiển thị TT file bất kì    +");
            System.out.println("==========================================================");
            System.out.println("+       [2]. Đổi tên file hàng loạt trong 1 folder       +");
            System.out.println("==========================================================");
            System.out.println("+       [3]. Dọn dẹp thư mục với sau một nút ENTER       +");
            System.out.println("==========================================================");
            System.out.println("+       [4]. Thoát                                       +");
            System.out.println("==========================================================");
            System.out.print("Mời bạn chọn chức năng: ");
            choose = sc.nextInt();
            switch (choose) {
                case 1: {
                    System.out.println("Bạn đang sử dụng chức năng tìm kiếm thông tin file");
                    timKiemFileTheoTen();
                    break;
                }
                case 2: {
                    System.out.println("Bạn đang sử dụng chức năng đổi tên file hàng loạt");
                    doiTenFile();
                    break;
                }
                case 3: {
                    System.out.println("Bạn đang sử dụng chức năng Dọn dẹp thư mục");
                    donDepThuMuc();
                    break;
                }
                case 4: {
                    System.out.println("\nCảm ơn bạn đã sử dụng dịch vụ !");
                    System.exit(0);
                }
                default: {
                    System.out.println("Bạn chọn sai lựa chọn !");
                }
            }
        } while (choose != 4);
    }

    public static String nhapDuongDanFolder() {
        Scanner sc = new Scanner(System.in);
        File folder;
        String pathFolder = "";
        do {
            System.out.print("Mời bạn nhập đường dẫn thư mục: ");
            pathFolder = sc.nextLine();
            folder = new File(pathFolder);
            if (!(folder.exists() && folder.isDirectory())) {
                System.err.println("Đường dẫn thư mục không hợp lệ ! Vui lòng thử lại ...");
            }
        } while (!(folder.exists() && folder.isDirectory()));
        return pathFolder;
    }

    public static void timKiemFileTheoTen() {
        Scanner sc = new Scanner(System.in);
        String path = nhapDuongDanFolder();
        File[] listFile = new File(path).listFiles();
        System.out.println("Folder hiện tại: " + path);
        System.out.println("Số file trong folder: " + listFile.length);
        System.out.print("Nhập tên file cần tìm kiếm: ");
        String fileName = sc.nextLine();
        System.out.println("+========================================================THÔNG TIN CHI TIẾT FILE=====================================================================+");
        System.out.println("+                   Name            +                       Đường dẫn                 +         Kích thước         +         Chỉnh sửa lần cuối      +");
        System.out.println("+===================================+=================================================+============================+=================================+");
        for (int i = 0; i < listFile.length; i++) {
            if (listFile[i].getName().contains(fileName)) {
                System.out.printf("|%35s|%49s|%28d|%33s|\n", listFile[i].getName(), listFile[i].getAbsolutePath(), listFile[i].length(), new Date(listFile[i].lastModified()).toString());
                System.out.println("+===================================+=================================================+============================+=================================+");
            }
        }
        System.out.println("");
    }
}

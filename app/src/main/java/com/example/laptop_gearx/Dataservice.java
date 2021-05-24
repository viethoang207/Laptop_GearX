package com.example.laptop_gearx;





import com.example.laptop_gearx.Models.ChiTietDonDatHang;
import com.example.laptop_gearx.Models.ChiTietGioHang;
import com.example.laptop_gearx.Models.ChiTietHoaDonBan;
import com.example.laptop_gearx.Models.DonDatHang;
import com.example.laptop_gearx.Models.HoaDonBan;
import com.example.laptop_gearx.Models.Laptop;
import com.example.laptop_gearx.Models.NguoiDung;
import com.example.laptop_gearx.Models.NhomThuongHieu;
import com.example.laptop_gearx.Models.TaiKhoan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Dataservice {
    @GET ("api/taikhoan/get-all-taikhoan")
    Call<List<TaiKhoan>>getTaiKhoan();

    @GET ("api/laptop/get-all-laptop")
    Call<List<Laptop>>getAllLaptop();

    @GET ("api/laptop/get-by-nth/{nhomthuonghieu}")
    Call<List<Laptop>>getLaptopByNhomThuongHieu(@Path("nhomthuonghieu") String nhomthuonghieu);

    @GET ("api/nhomthuonghieu/get-all-nhomthuonghieu")
    Call<List<NhomThuongHieu>>getAllNhomThuongHieu();

    @POST("api/laptop/add-laptop")
    Call<Laptop> themLaptop(@Body Laptop lt);

    @POST("api/laptop/update-laptop")
    Call<Laptop> updateLaptop(@Body Laptop lt);

    @DELETE("api/laptop/delete-laptop/{item_id}")
    Call<Void> deleteLaptop(@Path("item_id") int item_id);


    //giỏ hàng
    @GET ("api/chitietgiohang/get-by-taikhoan/{tenTK}")
    Call<List<ChiTietGioHang>>getGioHangByTaiKhoan(@Path("tenTK") String tenTK);

    @POST("api/chitietgiohang/minus-sl/{tenTK}/{maLaptop}")
    Call<Void> GiamSLGioHang(@Path("tenTK") String tenTK,@Path("maLaptop") int maLaptop);

    @POST("api/chitietgiohang/plus-sl/{tenTK}/{maLaptop}")
    Call<Void> TangSLGioHang(@Path("tenTK") String tenTK,@Path("maLaptop") int maLaptop);

    @DELETE("api/chitietgiohang/delete-chitietgiohang/{tenTK}/{maLaptop}")
    Call<Void> deleteCTGH(@Path("tenTK") String tenTK,
                          @Path("maLaptop") int maLaptop
                          );

    @POST("api/chitietgiohang/add-giohang")
    Call<ChiTietGioHang> themGioHang(@Body ChiTietGioHang ct);


    //api người dùng
    @GET ("api/nguoidung/get-by-tenTK/{tenTK}")
    Call<List<NguoiDung>>getNguoidungByTenTK(@Path("tenTK") String tenTK);


    //ĐƠN ĐẶT HÀNG

    //tạo mới đơn đặt hàng
    @POST("api/dondathang/add-dondathang")
    Call<DonDatHang> themDonDatHang(@Body DonDatHang donDatHang);

    //lấy về tất cả đơn đặt hàng
    @GET ("api/dondathang/get-all-dondathang")
    Call<List<DonDatHang>>getAllDonDatHang();


    //tạo mới chi tiết đơn đặt hàng
    @POST("api/chitietdondathang/add-ctdondathang")
    Call<ChiTietDonDatHang> themCTDonDatHang(@Body ChiTietDonDatHang ctdonDatHang);

    //lấy về chi tiết đơn đặt hàng theo mã đơn đặt hàng
    @GET ("api/chitietdondathang/get-by-maDH/{maDonDatHang}")
    Call<List<ChiTietDonDatHang>>getCTDonDatHangByMaDonDatHang(@Path("maDonDatHang") String maDonDatHang);


    //lấy về đơn đặt hàng theo tên tài khoản
    @GET ("api/dondathang/get-by-tenTK/{tenTK}")
    Call<List<DonDatHang>>getDonDatHangByTenTK(@Path("tenTK") String tenTK);

    //lấy về các đơn hàng  đã xác nhận
    @GET ("api/dondathang/get-all-dondathang-xacnhan")
    Call<List<DonDatHang>>getDonHangXacNhan();

    //lấy về các đơn hàng đã thanh toán
    @GET ("api/dondathang/get-all-dondathang-thanhtoan")
    Call<List<DonDatHang>>getDonHangThanhToan();

    //lấy về các đơn hàng đã hủy
    @GET ("api/dondathang/get-all-dondathang-huy")
    Call<List<DonDatHang>>getDonHangHuy();

    //xóa 1 đơn đặt hàng
    @DELETE("api/dondathang/delete-dondathang/{maDonDatHang}")
    Call<Void> deleteDonDatHang(@Path("maDonDatHang") String maDonDatHang);

    //xác nhận đơn hàng
    @POST("api/dondathang/xacnhan/{maDonDatHang}")
    Call<Void> XacNhanDonHang(@Path("maDonDatHang") String maDonDatHang);

    //hủy 1 hóa đơn
    @POST("api/dondathang/huydon/{maDonDatHang}")
    Call<Void> HuyDonHang(@Path("maDonDatHang") String maDonDatHang);

    //xác nhận thanh toán đơn hàng
    @POST("api/dondathang/thanhtoan/{maDonDatHang}")
    Call<Void> ThanhToanDonHang(@Path("maDonDatHang") String maDonDatHang);




    //HÓA ĐƠN BÁN HÀNG

    //tạo 1 hóa đơn mới
    @POST("api/hoadonban/add-hoadonban")
    Call<HoaDonBan> themHoaDonBan(@Body HoaDonBan hd);

    //tạo 1 chi tiết hóa đơn mới
    @POST("api/cthoadonban/add-cthoadonban")
    Call<ChiTietHoaDonBan> themCTHoaDonBan(@Body ChiTietHoaDonBan ct);
}

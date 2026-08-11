package com.tungduong.pawnmanagement.model.enums;

public enum PaymentStatus {
    PENDING,            // Đang chờ xử lý (Khách vừa quét mã QR hoặc tạo lệnh chuyển khoản, tiền chưa về)
    PROCESSING,         // Đang thực hiện (Hệ thống đang kết nối với ngân hàng/cổng thanh toán để đối soát)

    // Giai đoạn kết thúc thành công
    SUCCESS,            // Thanh toán thành công (Tiền đã nổi trong tài khoản công ty, hợp lệ)

    // Giai đoạn thất bại hoặc sự cố
    FAILED,             // Thanh toán thất bại (Tài khoản khách không đủ tiền, lỗi cổng thanh toán, v.v.)
    CANCELLED,          // Đã hủy (Khách chủ động bấm hủy lệnh thanh toán trước khi chuyển tiền)
    TIMEOUT,            // Hết thời gian chờ (Khách tạo mã QR thanh toán nhưng quá 15 phút không chuyển)

    // Giai đoạn xử lý sau thanh toán
    REFUNDED,           // Đã hoàn tiền (Khách chuyển nhầm, chuyển thừa hoặc hệ thống lỗi nên đã trả lại tiền)
    REVERSED
}

package com.tungduong.pawnmanagement.model.enums;

public enum PaymentScheduleStatus {
    NOT_YET_DUE,        // Chưa đến hạn (Kỳ hạn này ở tương lai, khách chưa cần trả)

    // Giai đoạn đến hạn và thanh toán
    DUE,                // Đến hạn (Đang trong ngày hoặc trong tuần phải thanh toán)
    PAID,               // Đã thanh toán (Khách đã trả đúng và đủ số tiền của kỳ này)
    PARTIALLY_PAID,     // Đã thanh toán một phần (Khách mới trả được gốc hoặc một ít lãi, vẫn nợ lại)

    // Giai đoạn quá hạn
    OVERDUE,            // Quá hạn (Đã qua ngày đáo hạn nhưng khách chưa trả đủ)

    // Các trường hợp đặc biệt
    WAIVED,             // Được miễn giảm (Kỳ này được xóa nợ/miễn trả do chính sách đặc biệt)
    REFUNDED
}

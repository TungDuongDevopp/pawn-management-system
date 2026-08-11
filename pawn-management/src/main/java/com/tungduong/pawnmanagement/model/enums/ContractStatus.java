package com.tungduong.pawnmanagement.model.enums;

public enum ContractStatus {
    // Giai đoạn Khởi tạo & Kiểm tra hồ sơ
    DRAFT,                  // Nháp (Khách hàng hoặc Sales mới tạo, chưa nộp)
    PENDING_PROCESSING,     // Chờ lọc hồ sơ (Đang chờ Loan Processor kiểm tra giấy tờ)
    PROCESSING_REJECTED,    // Bị từ chối ở bước lọc (Thiếu giấy tờ, hồ sơ giả mạo...)

    // Giai đoạn Thẩm định tài sản
    PENDING_APPRAISAL,      // Chờ thẩm định giá (Đã chuyển sang cho Appraiser)
    APPRAISING,             // Đang thẩm định (Appraiser đang định giá, khảo sát tài sản)

    // Giai đoạn Soạn thảo & Phê duyệt
    PENDING_DRAFTING,       // Chờ soạn thảo hợp đồng (Chờ Drafter lên điều khoản)
    PENDING_APPROVAL,       // Chờ phê duyệt (Đã gửi lên cho Approver/Director)
    REJECTED,               // Bị từ chối duyệt (Hội đồng hoặc Giám đốc không thông qua)

    // Giai đoạn Thực thi & Giải ngân
    APPROVED,               // Đã phê duyệt (Chờ khách hàng ký và chờ giải ngân)
    PENDING_DISBURSEMENT,   // Chờ giải ngân (Kế toán đang xử lý lệnh chi tiền)

    // Giai đoạn Vận hành sau giải ngân (Active)
    ACTIVE,                 // Đang hoạt động (Khách hàng đã nhận tiền, hợp đồng có hiệu lực)
    OVERDUE,                // Quá hạn (Khách hàng trễ hạn trả lãi/gốc)
    BAD_DEBT,               // Nợ xấu (Quá hạn quá lâu, chuyển cho bộ phận thu hồi nợ/xử lý tài sản)
    LIQUIDATING,            // Đang thanh lý tài sản (Đang bán đấu giá tài sản cầm cố để thu hồi vốn)

    // Giai đoạn Kết thúc
    CLOSED,                 // Đã tất toán (Khách hàng đã trả hết tiền, nhận lại tài sản)
    CANCELLED
}

package com.tungduong.pawnmanagement.model.enums;

public enum AssetStatus {
    UNDER_REVIEW,       // Đang thẩm định (Tài sản đang được Appraiser kiểm tra, định giá)
    REJECTED,           // Bị từ chối (Tài sản không đủ điều kiện nhận cầm cố, ví dụ: đồ giả, giấy tờ không chính chủ)
    APPROVED,           // Đã duyệt (Tài sản đủ điều kiện, chờ khách ký hợp đồng và bàn giao)

    // Giai đoạn lưu kho / Niêm phong (Khoản vay đang hoạt động)
    IN_STORAGE,         // Đang lưu kho (Tài sản đã được niêm phong và cất vào kho an toàn của công ty)
    IN_USE,             // Khách đang giữ (Dùng cho hình thức cho vay bằng Cavet/Đăng ký xe: Khách vẫn được chạy xe, công ty chỉ giữ giấy tờ)

    // Giai đoạn xử lý khi có sự cố nợ xấu
    PENDING_LIQUIDATION,// Chờ thanh lý (Hợp đồng bị nợ xấu quá hạn, tài sản bị chuyển trạng thái chuẩn bị đem bán)
    LIQUIDATING,        // Đang thanh lý (Tài sản đang được đăng bán, đấu giá hoặc chuyển cho bên thứ ba)
    LIQUIDATED,         // Đã thanh lý (Đã bán xong tài sản để thu hồi vốn cho khoản vay)

    // Giai đoạn hoàn trả hoặc xử lý đặc biệt
    RETURNED,           // Đã trả lại khách (Khách đã tất toán hợp đồng, nhận lại nguyên vẹn tài sản)
    DAMAGED_LOST,       // Bị hư hỏng/mất mát (Trường hợp rủi ro kho bãi, tài sản bị hỏng hoặc mất trong quá trình công ty lưu giữ)
    CONFISCATED       // Bị thu hồi
}

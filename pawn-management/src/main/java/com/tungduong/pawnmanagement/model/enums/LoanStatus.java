package com.tungduong.pawnmanagement.model.enums;

public enum LoanStatus {
    NONE,                   // Chưa phát sinh (Khoản vay chưa được giải ngân)

    // Giai đoạn đang trong hạn (Theo tiêu chuẩn phân loại nợ CIC)
    CURRENT,                // Nợ nhóm 1: Nợ đủ tiêu chuẩn (Khách trả gốc/lãi đúng hạn hoặc trễ dưới 10 ngày)
    SPECIAL_MENTION,        // Nợ nhóm 2: Nợ cần chú ý (Trễ hạn từ 10 đến 90 ngày)

    // Giai đoạn nợ xấu (Non-Performing Loan - NPL)
    SUBSTANDARD,            // Nợ nhóm 3: Nợ dưới tiêu chuẩn (Trễ hạn từ 91 đến 180 ngày)
    DOUBTFUL,               // Nợ nhóm 4: Nợ nghi ngờ mất vốn (Trễ hạn từ 181 đến 360 ngày)
    LOSS,                   // Nợ nhóm 5: Nợ có khả năng mất vốn (Trễ hạn trên 360 ngày)

    // Giai đoạn xử lý tài sản / Thu hồi
    WRITTEN_OFF,            // Đã xóa nợ khỏi bảng cân đối (Chuyển sang theo dõi ngoại bảng hoặc đã bán nợ)
    RECOVERY,               // Đang trong quá trình thu hồi nợ/gốc sau khi quá hạn

    // Giai đoạn kết thúc
    PAID_OFF,               // Đã tất toán hoàn toàn (Khách hàng đã trả hết sạch gốc, lãi, phạt)
    FORGIVEN                // Được miễn giảm/xoá nợ (Trường hợp đặc biệt được duyệt miễn trừ)
}

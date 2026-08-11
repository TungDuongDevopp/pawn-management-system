package com.tungduong.pawnmanagement.model.enums;

public enum Department {

    SALES,               // Phòng kinh doanh / Tiếp cận khách hàng
    LOAN_PROCESSOR,       // Phòng tiếp nhận và lọc hồ sơ

    // Nhóm Thẩm định và Xử lý pháp lý
    APPRAISER,           // Phòng thẩm định giá tài sản
    DRAFTER,             // Phòng soạn thảo hợp đồng
    LEGAL,               // Phòng pháp chế (nếu cần kiểm tra sâu)

    // Nhóm Quyết định và Thực thi
    APPROVER,            // Cấp phê duyệt (Ban giám đốc / Hội đồng tín dụng)
    DISBURSEMENT,        // Phòng kế toán / Giải ngân

    DEBT_COLLECTION
}


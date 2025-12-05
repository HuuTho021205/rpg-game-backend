package com.example.game.enums;

public enum JobClass {
    WARRIOR("Chiến binh","Lớp nhân vật mạnh mẽ, chuyên về tấn công cận chiến"),
    MAGE("Pháp sư","Lớp nhân vật sử dụng ma pháp, gây sát thương cao từ xa nhưng phòng thủ yếu "),
    ACHOR("Cung thủ", "Lớp nhân vật linh hoạt, gây sát thương từ xa có có độ chình xác cao");

    private final String displayName;
    private final String description;

    JobClass(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}

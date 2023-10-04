# MobDev_INT3120_20

NOTE trước khi đọc code

Việc tự tạo database và truy vấn đối với database tự tạo bị bug nên thay vì sử dụng database tự tạo thì mình sẽ dùng database có sẵn của các app trong Android 
Thí dụ: Database dữ liệu thông tin các contact của app Phone, Database dữ liệu thông tin lịch sử SMS của app SMS

Do đó trong code sẽ tạm chưa có việc chỉnh sửa thông tin nội bộ, hoặc xoá hay thêm các trường ( update code sau )

Code demo Content Provider bằng cách truy vấn và hiển thị các thông tin có sẵn trong các app

Thi dụ với app phone: Lấy được toàn bộ thông tin danh bạ và hiển thị lên log, với SMS hiển thị toàn bộ lịch sử tin nhắn lên Log
